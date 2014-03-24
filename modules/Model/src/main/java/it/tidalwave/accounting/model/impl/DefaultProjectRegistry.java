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

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import it.tidalwave.util.Id;
import it.tidalwave.util.FinderStreamSupport;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class DefaultProjectRegistry implements ProjectRegistry
  {
    private final Map<Id, Project> projectMapByNumber = new HashMap<>();
    
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @NoArgsConstructor @AllArgsConstructor
    class DefaultProjectFinder extends FinderStreamSupport<Project, ProjectRegistry.Finder>
                               implements ProjectRegistry.Finder
      {
        @CheckForNull
        private Id id;

        @Override @Nonnull
        public Finder withId (final @Nonnull Id id)
          {
            final DefaultProjectFinder clone = (DefaultProjectFinder)super.clone();
            clone.id = id;
            return clone;
          }

        @Override
        protected List<? extends Project> computeResults()
          {
            if (id != null)
              {
                final Project project = projectMapByNumber.get(id);
                return (project != null) ? Collections.singletonList(project) : Collections.<Project>emptyList();
              }
            else
              {
                return new ArrayList<>(projectMapByNumber.values());
              }
          }
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public ProjectRegistry.Finder findProjects()
      {
        return new DefaultProjectFinder();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Project.Builder addProject()
      {
        return new Project.Builder((project) -> projectMapByNumber.put(project.getId(), project));
      }
  }
