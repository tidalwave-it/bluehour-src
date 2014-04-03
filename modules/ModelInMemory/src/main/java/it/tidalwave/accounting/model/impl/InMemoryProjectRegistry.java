/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - hg clone https://bitbucket.org/tidalwave/bluehour-src
 * %%
 * Copyright (C) 2013 - 2014 Tidalwave s.a.s. (http://tidalwave.it)
 * %%
 * *********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * *********************************************************************************************************************
 *
 * $Id$
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.spi.util.FinderWithIdMapSupport;
import it.tidalwave.accounting.model.spi.util.FinderWithIdSupport;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class InMemoryProjectRegistry implements ProjectRegistry
  {
    private final Map<Id, Project> projectMapById = new HashMap<>();
    
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    class InMemoryProjectFinder extends FinderWithIdMapSupport<Project, ProjectRegistry.ProjectFinder>
                                implements ProjectRegistry.ProjectFinder
      {
        InMemoryProjectFinder()
          {
            super(projectMapById);  
          }
      }

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    class InMemoryJobEventFinder extends FinderWithIdSupport<JobEvent, ProjectRegistry.JobEventFinder>
                                 implements ProjectRegistry.JobEventFinder
      {
        // FIXME: very inefficient
        @Override @Nonnull
        protected Collection<? extends JobEvent> findAll() 
          {
            final List<JobEvent> result = new ArrayList<>();
            
            findProjects().forEach(project -> 
              {
                project.findChildren().forEach(jobEvent -> 
                  {
                    result.add(jobEvent);
                    
                    // FIXME: should be recursive
                    if (jobEvent instanceof InMemoryJobEventGroup)
                      {
                        result.addAll(((InMemoryJobEventGroup)jobEvent).findChildren().results());  
                      }
                  });
              });
            
            return result;
          }

        @Override @Nonnull
        protected JobEvent findById (final @Nonnull Id id) 
          {
            // FIXME: very inefficient
            final Map<Id, JobEvent> map = findAll().stream().collect(Collectors.toMap(JobEvent::getId, item -> item));
            
            // FIXME: should not happen
            if (!map.containsKey(id))
              {
                return JobEvent.builder().withId(id).withName("DUMMY!").create();
              }
            
            return map.get(id);
          }  
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public ProjectRegistry.ProjectFinder findProjects()
      {
        return new InMemoryProjectFinder();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public ProjectRegistry.JobEventFinder findJobEvents()
      {
        return new InMemoryJobEventFinder();
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Project.Builder addProject()
      {
        return new Project.Builder(project -> projectMapById.put(project.getId(), project));
      }
  }
