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
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.spi.util.FinderWithIdMapSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor @Slf4j
public class InMemoryCustomerRegistry implements CustomerRegistry
  {
    private static final long serialVersionUID = 1L;
    
    @Nonnull
    private final Accounting accounting;
    
    private final Map<Id, InMemoryCustomer> customerMapById = new HashMap<>();

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    class InMemoryCustomerFinder extends FinderWithIdMapSupport<Customer, InMemoryCustomer, CustomerRegistry.Finder>
                                 implements CustomerRegistry.Finder
      {
        private static final long serialVersionUID = 1L;
    
        InMemoryCustomerFinder()
          {
            super(customerMapById);  
          }
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public CustomerRegistry.Finder findCustomers()
      {
        return new InMemoryCustomerFinder();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Customer.Builder addCustomer()
      {
        return new Customer.Builder(customer -> 
          {
            final InMemoryCustomer inMemoryCustomer = (InMemoryCustomer)customer;
            inMemoryCustomer.setAccounting(accounting);
            customerMapById.put(customer.getId(), inMemoryCustomer);
          });
      }
  }