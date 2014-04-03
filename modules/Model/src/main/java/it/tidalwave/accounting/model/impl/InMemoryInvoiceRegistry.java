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
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.spi.util.FinderWithIdMapSupport;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class InMemoryInvoiceRegistry implements InvoiceRegistry
  {
    private final Map<Id, Invoice> invoiceMapById = new HashMap<>();

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    class InMemoryInvoiceFinder extends FinderWithIdMapSupport<Invoice, InvoiceRegistry.Finder>
                                implements InvoiceRegistry.Finder
      {
        InMemoryInvoiceFinder()
          {
            super(invoiceMapById);  
          }
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public InvoiceRegistry.Finder findInvoices()
      {
        return new InMemoryInvoiceFinder();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Invoice.Builder addInvoice()
      {
        return new Invoice.Builder(invoice -> invoiceMapById.put(invoice.getId(), invoice));
      }
  }
