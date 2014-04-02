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
import it.tidalwave.util.As;
import it.tidalwave.util.spi.AsSupport;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.impl.InMemoryCustomerRegistry;
import it.tidalwave.accounting.model.impl.InMemoryInvoiceRegistry;
import it.tidalwave.accounting.model.impl.InMemoryProjectRegistry;
import it.tidalwave.accounting.importer.ibiz.IBizImporter;
import lombok.Delegate;
import lombok.Getter;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class DefaultIBizImporter implements IBizImporter
  {
    @Delegate
    private final As asSupport = new AsSupport(this);

    @Getter
    private final CustomerRegistry customerRegistry = new InMemoryCustomerRegistry(this);

    @Getter
    private final ProjectRegistry projectRegistry = new InMemoryProjectRegistry();
    
    @Getter
    private final InvoiceRegistry invoiceRegistry = new InMemoryInvoiceRegistry();

    @Nonnull
    private final Path path;

    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public static IBizImporter.Builder builder()
      {
        return new DefaultIBizImporter.Builder(null); // FIXME: null
      }

    /*******************************************************************************************************************
     *
     * @param   builder
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
    public void importAll()
      throws IOException
      {
        new DefaultIBizCustomerImporter(customerRegistry, path).importCustomers();
        new DefaultIBizProjectImporter(customerRegistry, projectRegistry, path).importProjects();
        new DefaultIBizInvoiceImporter(invoiceRegistry, projectRegistry, path).importInvoices();
      }
  }
