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
package it.tidalwave.accounting.importer.ibiz;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.accounting.model.ProjectRegistry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;
import static lombok.AccessLevel.PRIVATE;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class IBizImporter
  {
    private final IBizCustomerImporter customerImporter = new IBizCustomerImporter();

    private final IBizProjectImporter projectImporter;

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @AllArgsConstructor(access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        private final Path path;

        @Nonnull
        public IBizImporter.Builder withPath2 (final @Nonnull String path) // FIXME: rename
          {
            return withPath(Paths.get(path));
          }

        @Nonnull
        public IBizImporter create()
          {
            return new IBizImporter(this);
          }
      }

    @Nonnull
    private final Path path;

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public static IBizImporter.Builder builder()
      {
        return new IBizImporter.Builder(null); // FIXME: null
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    protected IBizImporter (final @Nonnull IBizImporter.Builder builder)
      {
        this.path = builder.getPath();
        final Path path2 = path.resolve("Projects/4D2263D4-9043-40B9-B162-2C8951F86503.ibiz"); // FIXME
        projectImporter = new IBizProjectImporter(path2, customerImporter.getCustomerRegistry());
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public ProjectRegistry getProjectRegistry()
      {
        return projectImporter.getProjectRegistry();
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public void run()
      throws Exception
      {
        customerImporter.run();
        projectImporter.run();
      }
  }