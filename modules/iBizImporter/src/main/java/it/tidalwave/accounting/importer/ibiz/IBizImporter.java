/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2024 by Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.importer.ibiz;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.nio.file.Path;
import it.tidalwave.accounting.importer.ibiz.impl.DefaultIBizImporter;
import it.tidalwave.accounting.model.Accounting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

/***************************************************************************************************************************************************************
 *
 * An object capable to import data from iBiz.
 * 
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public interface IBizImporter extends Accounting
  {
    /***********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/
    @AllArgsConstructor // FIXME (access = PRIVATE)
    @Immutable @With @Getter @ToString
    public static class Builder
      {
        private final Path path;

        @Nonnull
        public IBizImporter create()
          {
            return new DefaultIBizImporter(this);
          }
      }

    /***********************************************************************************************************************************************************
     * Runs a full import round.
     * 
     * @throws  IOException in case of error
     **********************************************************************************************************************************************************/
    public void importAll()
      throws IOException;
  }