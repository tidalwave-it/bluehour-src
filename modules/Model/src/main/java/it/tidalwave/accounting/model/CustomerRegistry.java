/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2025 by Tidalwave s.a.s. (http://tidalwave.it)
 *
 * *************************************************************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 *
 * *************************************************************************************************************************************************************
 *
 * git clone https://bitbucket.org/tidalwave/bluehour-src
 * git clone https://github.com/tidalwave-it/bluehour-src
 *
 * *************************************************************************************************************************************************************
 */
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import it.tidalwave.accounting.model.Project.Builder;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public interface CustomerRegistry
  {
    /***********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/
    public static interface Finder extends it.tidalwave.util.Finder<Customer>,
                                           ExtendedFinderSupport<Customer, CustomerRegistry.Finder>
      {
        @Nonnull
        public Finder withId (@Nonnull Id id);
      }

    /***********************************************************************************************************************************************************
     * Returns a {@link Finder} for finding {@link Customer}s.
     *
     * @return  the finder
     **********************************************************************************************************************************************************/
    @Nonnull
    public Finder findCustomers();

    /***********************************************************************************************************************************************************
     * Returns a {@link Builder} for adding a {@link Customer} to the registry.
     *
     * @return  the builder
     **********************************************************************************************************************************************************/
    @Nonnull
    public Customer.Builder addCustomer();
  }
