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
package it.tidalwave.accounting.importer.ibiz.impl;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import it.tidalwave.util.Id;
import it.tidalwave.util.NotFoundException;
import it.tidalwave.accounting.importer.ibiz.spi.IBizInvoiceImporter;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * *********************************************************************************************************************
 *
 * @author Fabrizio Giudici
 * @version $Id$
 *
 *********************************************************************************************************************
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultIBizInvoiceImporter implements IBizInvoiceImporter 
  {
    @Nonnull
    private final InvoiceRegistry invoiceRegistry;

    @Nonnull
    private final ProjectRegistry projectRegistry;
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public void run (final @Nonnull Path documentPath)
      throws IOException 
      {
        try
          {
            final ConfigurationDecorator configuration = IBizUtils.loadConfiguration(documentPath.resolve("Attributes"));
            final Id projectId = new Id((String)configuration.getList("projectIDs").get(0));
            final Project project = projectRegistry.findProjects().withId(projectId).result();

            invoiceRegistry.addInvoice().withId(configuration.getId("uniqueIdentifier"))
                                        .withNumber(configuration.getString("invoiceNumber"))
                                        .withProject(project)
                                        .withDate(configuration.getDate("date"))
                                        .withDueDate(configuration.getDate("dueDate"))
    //                                            .withDaysUntilDue(configuration.getInt(""))
                                        .withEarnings(configuration.getMoney("invoiceAmount"))
                                        .withTax(configuration.getMoney("taxes1"))
                                        .create();
          }
        catch (NotFoundException e)
          {
            throw new IOException(e);
          }
      }
  }
