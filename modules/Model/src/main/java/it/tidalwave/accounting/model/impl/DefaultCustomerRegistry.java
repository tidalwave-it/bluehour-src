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
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class DefaultCustomerRegistry implements CustomerRegistry
  {
    private final Map<Id, Customer> customerMapById = new HashMap<>();

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    class DefaultCustomerFinder extends FinderWithIdSupport<Customer, CustomerRegistry.Finder>
                                implements CustomerRegistry.Finder
      {
        DefaultCustomerFinder()
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
        return new DefaultCustomerFinder();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Customer.Builder addCustomer()
      {
        return new Customer.Builder(customer -> customerMapById.put(customer.getId(), customer));
      }
  }
