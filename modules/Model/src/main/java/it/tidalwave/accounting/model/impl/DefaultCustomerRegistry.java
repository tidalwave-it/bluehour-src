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
package it.tidalwave.accounting.model.impl;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;
import it.tidalwave.util.spi.FinderSupport;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
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
public class DefaultCustomerRegistry implements CustomerRegistry
  {
    private final Map<Id, Customer> customerMapById = new HashMap<>();

    @NoArgsConstructor @AllArgsConstructor
    class DefaultCustomerFinder extends FinderSupport<Customer, CustomerRegistry.Finder>
                                implements CustomerRegistry.Finder, ExtendedFinderSupport<Customer, CustomerRegistry.Finder>
      {
        @CheckForNull
        private Id id;

        @Override @Nonnull
        public CustomerRegistry.Finder withId (final @Nonnull Id id)
          {
            final DefaultCustomerFinder clone = (DefaultCustomerFinder)super.clone();
            clone.id = id;
            return clone;
          }

        @Override
        protected List<? extends Customer> computeResults()
          {
            if (id != null)
              {
                final Customer customer = customerMapById.get(id);
                return (customer != null) ? Collections.singletonList(customer) : Collections.<Customer>emptyList();
              }
            else
              {
                return new ArrayList<>(customerMapById.values());
              }
          }
      }

    @Override @Nonnull
    public CustomerRegistry.Finder findCustomer()
      {
        return new DefaultCustomerFinder();
      }

    @Override @Nonnull
    public Customer.Builder addCustomer()
      {
//        return Customer.builder();
        return new Customer.Builder(new Customer.Builder.Callback()
          {
            @Override
            public void register (final @Nonnull Customer customer)
              {
                log.info("{}: {}", customer.getId(), customer);
                customerMapById.put(customer.getId(), customer);
              }
          });
      }
  }
