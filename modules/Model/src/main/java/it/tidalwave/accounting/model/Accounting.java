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
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import it.tidalwave.util.As;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface Accounting extends As
  {
    // FIXME: replace with a Factory
    @Nonnull
    public static Accounting createNew()
      {
        try
          {
            return (Accounting)Class.forName("it.tidalwave.accounting.model.impl.InMemoryAccounting").newInstance();
          } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) 
          {
            throw new RuntimeException(e);
          }
      }

    /*******************************************************************************************************************
     *
     * @return  the {@link CustomerRegistry}
     *
     ******************************************************************************************************************/
    @Nonnull
    public CustomerRegistry getCustomerRegistry();
    
    /*******************************************************************************************************************
     *
     * @return  the {@link ProjectRegistry}
     *
     ******************************************************************************************************************/
    @Nonnull
    public ProjectRegistry getProjectRegistry();
    
    /*******************************************************************************************************************
     *
     * @return  the {@link InvoiceRegistry}
     *
     ******************************************************************************************************************/
    @Nonnull
    public InvoiceRegistry getInvoiceRegistry();
  }
