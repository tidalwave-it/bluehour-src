/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2023 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;
import it.tidalwave.accounting.model.types.Money;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public interface InvoiceRegistry
  {
    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    public static interface Finder extends it.tidalwave.util.Finder<Invoice>, ExtendedFinderSupport<Invoice, InvoiceRegistry.Finder>
      {
        @Nonnull
        public Finder withId (@Nonnull Id id);

        @Nonnull
        public Finder withProject (@Nonnull Project project);

        @Nonnull
        public Money getEarnings();
      }

    /*******************************************************************************************************************
     *
     * Returns a {@link Finder} for finding {@link Invoice}s.
     *
     * @return  the finder
     *
     ******************************************************************************************************************/
    @Nonnull
    public Finder findInvoices();

    /*******************************************************************************************************************
     *
     * Returns an {@link Invoice.Builder} for adding a {@link Invoice} to the registry.
     *
     * @return  the builder
     *
     ******************************************************************************************************************/
    @Nonnull
    public Invoice.Builder addInvoice();
  }
