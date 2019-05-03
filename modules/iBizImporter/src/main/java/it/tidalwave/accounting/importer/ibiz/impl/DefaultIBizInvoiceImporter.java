/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2019 Tidalwave s.a.s. (http://tidalwave.it)
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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import it.tidalwave.util.Id;
import it.tidalwave.util.NotFoundException;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.importer.ibiz.spi.IBizInvoiceImporter;
import it.tidalwave.accounting.model.types.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;

/***********************************************************************************************************************
 *
 * @author Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
@RequiredArgsConstructor
public class DefaultIBizInvoiceImporter implements IBizInvoiceImporter 
  {
    private static final Set<FileVisitOption> NO_OPTIONS = Collections.<FileVisitOption>emptySet();
    
    @Nonnull
    private final InvoiceRegistry invoiceRegistry;

    @Nonnull
    private final ProjectRegistry projectRegistry;
    
    @Nonnull
    private final Path path;

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    public void importInvoices()
      throws IOException
      {
        log.debug("importInvoices()");
        Files.walkFileTree(path.resolve("Invoices"), NO_OPTIONS, 1, new SimpleFileVisitor<Path>()
          {
            @Override
            public FileVisitResult visitFile (final @Nonnull Path path, final @Nonnull BasicFileAttributes attrs)
              throws IOException
              {
                if (path.getFileName().toString().endsWith(".invoice"))
                  {
                    importInvoice(path.resolve("Attributes"));
                  }
                
                return FileVisitResult.CONTINUE;
              }
          });
      }
    
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    private void importInvoice (final @Nonnull Path documentFile)
      throws IOException 
      {
        try
          {
            final ConfigurationDecorator configuration = IBizUtils.loadConfiguration(documentFile);
            final Id projectId = configuration.getIds("projectIDs").get(0);
            final Project project = projectRegistry.findProjects().withId(projectId).result();
            final Stream<Id> eventIds = configuration.getIds("jobEventIDs").stream();
            // FIXME: could just search events in project (but needs recursive operations in project.findJobEvents())
            final List<JobEvent> events = eventIds.flatMap(id -> projectRegistry.findJobEvents().withId(id).stream())
                                                  .collect(toList());
            // FIXME: iBiz duplicates events that are already inside a group - filter them away
            final Money tax = configuration.getMoney("taxes1");
            final Money earnings = configuration.getMoney("invoiceAmount");

            invoiceRegistry.addInvoice().withId(configuration.getId("uniqueIdentifier"))
                                        .withNumber(configuration.getString("invoiceNumber"))
                                        .withDate(configuration.getDate("date"))
                                        .withDueDate(configuration.getDate("dueDate"))
                                        .withEarnings(earnings.subtract(tax))
                                        .withTax(tax)
                                        .withProject(project)
                                        .withJobEvents(events)
                                        .create();
          }
        catch (NotFoundException e)
          {
            throw new RuntimeException(e);
          }
      }
  }
