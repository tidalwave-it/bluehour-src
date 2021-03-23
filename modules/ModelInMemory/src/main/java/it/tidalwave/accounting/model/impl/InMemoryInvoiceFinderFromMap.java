/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2021 Tidalwave s.a.s. (http://tidalwave.it)
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
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.util.FinderWithIdMapSupport;
import lombok.RequiredArgsConstructor;
import static java.util.stream.Collectors.toList;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class InMemoryInvoiceFinderFromMap extends FinderWithIdMapSupport<Invoice, InMemoryInvoice, InvoiceRegistry.Finder>
                                          implements InvoiceRegistry.Finder
  {
    private static final long serialVersionUID = 1L;
    
    @Nullable
    private final Project project;

    public InMemoryInvoiceFinderFromMap (@Nonnull final Map<Id, InMemoryInvoice> invoiceMapById)
      {
        super(invoiceMapById);  
        this.project = null;
      }
    
    public InMemoryInvoiceFinderFromMap (@Nonnull final InMemoryInvoiceFinderFromMap other,
                                         @Nonnull final Object override)
      {
        super(other, override);
        final InMemoryInvoiceFinderFromMap source = getSource(InMemoryInvoiceFinderFromMap.class, other, override);
        this.project = source.project;
      }

    @Override @Nonnull
    public InvoiceRegistry.Finder withProject (@Nonnull final Project project)
      {
        return clone(new InMemoryInvoiceFinderFromMap(project));
      }

    @Override @Nonnull
    protected List<InMemoryInvoice> computeResults()
      {
        Stream<InMemoryInvoice> stream = super.computeResults().stream();

        if (project != null)
          {
            stream = stream.filter(invoice -> invoice.getProject().equals(project));
          }
        
        return stream.collect(toList());
      }

    @Override @Nonnull
    public Money getEarnings() 
      {
        return streamImpl().map(InMemoryInvoice::getEarnings).reduce(Money.ZERO, Money::add);
      }
  }
