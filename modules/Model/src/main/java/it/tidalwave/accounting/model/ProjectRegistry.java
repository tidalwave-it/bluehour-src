/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2025 by Tidalwave s.a.s. (http://tidalwave.it)
 *
 * *************************************************************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 *
 * *************************************************************************************************************************************************************
 *
 * git clone https://bitbucket.org/tidalwave/bluehour-src
 * git clone https://github.com/tidalwave-it/bluehour-src
 *
 * *************************************************************************************************************************************************************
 */
package it.tidalwave.accounting.model;

import jakarta.annotation.Nonnull;
import java.time.Duration;
import it.tidalwave.accounting.model.Project.Builder;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.Finder;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public interface ProjectRegistry
  {
    public static final Class<ProjectRegistry> CustomerRegistry = ProjectRegistry.class;

    /***********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/
    public static interface ProjectFinder extends Finder<Project>, ExtendedFinderSupport<Project, ProjectFinder>
      {
        @Nonnull
        public ProjectFinder withId (@Nonnull Id id);
      }

    /***********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/
    public static interface JobEventFinder extends Finder<JobEvent>, ExtendedFinderSupport<JobEvent, JobEventFinder>
      {
        @Nonnull
        public JobEventFinder withId (@Nonnull Id id);

        @Nonnull
        public Duration getDuration();

        @Nonnull
        public Money getEarnings();
      }

    /***********************************************************************************************************************************************************
     * Returns a {@link ProjectFinder} for finding {@link Project}s.
     *
     * @return  the finder
     **********************************************************************************************************************************************************/
    @Nonnull
    public ProjectFinder findProjects();

    /***********************************************************************************************************************************************************
     * Returns a {@link ProjectFinder} for finding {@link JobEvent}s.
     *
     * @return  the finder
     **********************************************************************************************************************************************************/
    @Nonnull
    public JobEventFinder findJobEvents();

    /***********************************************************************************************************************************************************
     * Returns a {@link Builder} for adding a {@link Project} to the registry.
     *
     * @return  the builder
     **********************************************************************************************************************************************************/
    @Nonnull
    public Project.Builder addProject();
  }
