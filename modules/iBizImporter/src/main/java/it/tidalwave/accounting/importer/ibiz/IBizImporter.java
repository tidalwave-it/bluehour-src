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
package it.tidalwave.accounting.importer.ibiz;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.nio.file.Path;
import it.tidalwave.accounting.importer.ibiz.impl.DefaultIBizImporter;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.ProjectRegistry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface IBizImporter 
  {
    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @AllArgsConstructor // FIXME (access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        private final Path path;

        @Nonnull
        public IBizImporter create()
          {
            return new DefaultIBizImporter(this);
          }
      }

    /*******************************************************************************************************************
     *
     * @throws  IOException in case of error
     * 
     ******************************************************************************************************************/
    @Nonnull
    public void run()
      throws IOException;
    
    /*******************************************************************************************************************
     *
     * @return  the {@link CustomerRegistry} containing the imported data
     *
     ******************************************************************************************************************/
    @Nonnull
    public CustomerRegistry getCustomerRegistry();
    
    /*******************************************************************************************************************
     *
     * @return  the {@link ProjectRegistry} containing the imported data
     *
     ******************************************************************************************************************/
    @Nonnull
    public ProjectRegistry getProjectRegistry();
  }