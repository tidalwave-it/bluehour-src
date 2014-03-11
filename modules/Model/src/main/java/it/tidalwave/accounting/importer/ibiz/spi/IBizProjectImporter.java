/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - hg clone https://bitbucket.org/tidalwave/bluehour-src
 * %%
 * Copyright (C) 2013 - 2013 Tidalwave s.a.s. (http://tidalwave.it)
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
import java.io.IOException;
import it.tidalwave.accounting.model.AbstractJobEvent;
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
        EVENT(AbstractJobEvent.Builder.Type.TIMED),
        FIXED(AbstractJobEvent.Builder.Type.FLAT),
        UNKNOWN2(AbstractJobEvent.Builder.Type.TIMED),
        UNKNOWN3(AbstractJobEvent.Builder.Type.TIMED),
        UNKNOWN4(AbstractJobEvent.Builder.Type.TIMED),
        GROUP(AbstractJobEvent.Builder.Type.FLAT);

        @Getter @Nonnull
        private final AbstractJobEvent.Builder.Type mappedType;
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public void run()
      throws IOException;
  }
