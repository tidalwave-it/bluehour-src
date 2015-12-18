/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
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
package it.tidalwave.accounting.importer.ibiz.spi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface IBizProjectImporter
  {
    @AllArgsConstructor
    enum IBizJobEventType
      {
        EVENT(JobEvent.Type.TIMED),    // 0
        FIXED(JobEvent.Type.FLAT),     // 1
        UNKNOWN2(JobEvent.Type.TIMED), // 2
        UNKNOWN3(JobEvent.Type.TIMED), // 3
        UNKNOWN4(JobEvent.Type.TIMED), // 4
        GROUP(JobEvent.Type.FLAT);     // 5

        @Getter @Nonnull
        private final JobEvent.Type mappedType;
      }
    
    @AllArgsConstructor
    enum IBizProjectStatus
      {
        OPENED(Project.Status.OPEN),    // 0
        CLOSED(Project.Status.CLOSED),  // 1
        ON_HOLD(Project.Status.CLOSED), // 2  
        DELETED(null);                  // 3
        
        @Getter @Nullable
        private final Project.Status mappedStatus;
      }

    /*******************************************************************************************************************
     *
     * @throws  IOException     in case of error
     * 
     ******************************************************************************************************************/
    @Nonnull
    public void importProjects()
      throws IOException;
  }
