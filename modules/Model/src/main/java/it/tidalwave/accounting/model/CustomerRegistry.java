/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
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
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import it.tidalwave.util.Finder8;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinder8Support;
import it.tidalwave.accounting.model.Project.Builder;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface CustomerRegistry
  {
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    public static interface Finder extends Finder8<Customer>, 
                                           ExtendedFinder8Support<Customer, CustomerRegistry.Finder>
      {
        @Nonnull
        public Finder withId (@Nonnull Id id);
      }

    /*******************************************************************************************************************
     *
     * Returns a {@link Finder} for finding {@link Customer}s.
     * 
     * @return  the finder
     *
     ******************************************************************************************************************/
    @Nonnull
    public Finder findCustomers();

    /*******************************************************************************************************************
     *
     * Returns a {@link Builder} for adding a {@link Customer} to the registry.
     * 
     * @return  the builder
     *
     ******************************************************************************************************************/
    @Nonnull
    public Customer.Builder addCustomer();
  }
