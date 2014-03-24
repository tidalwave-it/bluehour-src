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
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import it.tidalwave.accounting.model.Project.Builder;
import it.tidalwave.util.FinderStream;
import it.tidalwave.util.Id;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface ProjectRegistry
  {
    public static final Class<ProjectRegistry> CustomerRegistry = ProjectRegistry.class;

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    public static interface Finder extends FinderStream<Project>
      {
        @Nonnull
        public Finder withId (@Nonnull Id id);
      }

    /*******************************************************************************************************************
     *
     * Returns a {@link Finder} for finding {@link Project}s.
     * 
     * @return  the finder
     *
     ******************************************************************************************************************/
    @Nonnull
    public Finder findProjects();

    /*******************************************************************************************************************
     *
     * Returns a {@link Builder} for adding a {@link Project} to the registry.
     * 
     * @return  the builder
     *
     ******************************************************************************************************************/
    @Nonnull
    public Project.Builder addProject();
  }
