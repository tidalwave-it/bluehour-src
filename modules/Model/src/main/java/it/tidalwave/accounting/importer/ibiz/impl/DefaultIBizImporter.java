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
package it.tidalwave.accounting.importer.ibiz.impl;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.impl.DefaultCustomerRegistry;
import it.tidalwave.accounting.model.impl.DefaultProjectRegistry;
import it.tidalwave.accounting.importer.ibiz.IBizImporter;
import lombok.Getter;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class DefaultIBizImporter implements IBizImporter
  {
    @Getter
    private final CustomerRegistry customerRegistry = new DefaultCustomerRegistry();

    @Getter
    private final ProjectRegistry projectRegistry = new DefaultProjectRegistry();

    @Nonnull
    private final Path path;

    /*******************************************************************************************************************
     *
     *
     *
     * @return 
     ******************************************************************************************************************/
    @Nonnull
    public static IBizImporter.Builder builder()
      {
        return new DefaultIBizImporter.Builder(null); // FIXME: null
      }

    /*******************************************************************************************************************
     *
     *
     * @param builder
     *
     ******************************************************************************************************************/
    public DefaultIBizImporter (final @Nonnull IBizImporter.Builder builder) // FIXME: protected
      {
        this.path = builder.getPath();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     * 
     ******************************************************************************************************************/    
    @Override @Nonnull
    public void run()
      throws IOException
      {
        importCustomers();
        importProjects();
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    private void importCustomers()
      throws IOException
      {
        final Path customersPath = path.resolve("clients");
        new DefaultIBizCustomerImporter(customerRegistry, customersPath).run();
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    private void importProjects()
      throws IOException
      {
        final Path projectsPath = path.resolve("Projects");
        final AtomicReference<IOException> exception = new AtomicReference<>();

        Files.walkFileTree(projectsPath, new SimpleFileVisitor<Path>()
          {
            @Override
            public FileVisitResult visitFile (final @Nonnull Path file, final @Nonnull BasicFileAttributes attrs)
              throws IOException
              {
                new DefaultIBizProjectImporter(customerRegistry, projectRegistry, file).run();
                return FileVisitResult.CONTINUE;
              }

            @Override
            public FileVisitResult visitFileFailed (final @Nonnull Path file, final @Nonnull IOException e)
              {
                exception.set(new IOException("Fatal error visiting " + file));
                return FileVisitResult.TERMINATE;
              }
          });

        if (exception.get() != null)
          {
            throw exception.get();
          }
      }
  }
