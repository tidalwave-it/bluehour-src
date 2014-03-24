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

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.tidalwave.util.FinderStreamSupport;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.InvoiceRegistry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class DefaultInvoiceRegistry implements InvoiceRegistry
  {
    private final Map<Id, Invoice> invoiceMapById = new HashMap<>();

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @NoArgsConstructor @AllArgsConstructor
    class DefaultInvoiceFinder extends FinderStreamSupport<Invoice, InvoiceRegistry.Finder>
                              implements InvoiceRegistry.Finder
      {
        @CheckForNull
        private Id id;

        @Override @Nonnull
        public InvoiceRegistry.Finder withId (final @Nonnull Id id)
          {
            final DefaultInvoiceFinder clone = (DefaultInvoiceFinder)super.clone();
            clone.id = id;
            return clone;
          }

        @Override
        protected List<? extends Invoice> computeResults()
          {
            if (id != null)
              {
                final Invoice invoice = invoiceMapById.get(id);
                return (invoice != null) ? Collections.singletonList(invoice) : Collections.<Invoice>emptyList();
              }
            else
              {
                return new ArrayList<>(invoiceMapById.values());
              }
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
        return new DefaultInvoiceFinder();
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
