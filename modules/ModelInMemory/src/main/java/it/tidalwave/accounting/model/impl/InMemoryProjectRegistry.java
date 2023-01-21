/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2023 Tidalwave s.a.s. (http://tidalwave.it)
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
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.FinderWithIdMapSupport;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor @Slf4j
public class InMemoryProjectRegistry implements ProjectRegistry
  {
    private static final long serialVersionUID = 1L;

    @Nonnull
    private final Accounting accounting;

    private final Map<Id, ProjectSpi> projectMapById = new HashMap<>();

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    static class InMemoryProjectFinder extends FinderWithIdMapSupport<Project, ProjectSpi, ProjectFinder>
                                implements ProjectRegistry.ProjectFinder
      {
        private static final long serialVersionUID = 1L;

        public InMemoryProjectFinder (@Nonnull final InMemoryProjectFinder other, @Nonnull final Object override)
          {
            super(other, override);  
          }
        
        InMemoryProjectFinder (@Nonnull final Map<Id, ProjectSpi> projectMapById)
          {
            super(projectMapById);
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
        return new InMemoryProjectFinder(projectMapById);
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public ProjectRegistry.JobEventFinder findJobEvents()
      {
        return new InMemoryJobEventFinder(findProjects());
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Project.Builder addProject()
      {
        return new Project.Builder(project ->
          {
            final InMemoryProject inMemoryProject = (InMemoryProject)project;
            projectMapById.put(project.getId(), inMemoryProject);
            inMemoryProject.setAccounting(accounting);
          });
      }
  }
