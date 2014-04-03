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
package it.tidalwave.accounting.model.spi;

import javax.annotation.Nonnull;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface ObjectFactory 
  {
    @Nonnull
    public static ObjectFactory getInstance() // FIXME: getDefault()
      {
        try
          {
            // FIXME: use Singleton
            return (ObjectFactory)Class.forName("it.tidalwave.accounting.model.impl.InMemoryObjectFactory").newInstance();
//        return new InMemoryObjectFactory();
          } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) 
          {
            throw new RuntimeException(e);
          }
      }

    @Nonnull
    public Customer createCustomer (@Nonnull Customer.Builder builder);
    
    @Nonnull
    public Invoice createInvoice (@Nonnull Invoice.Builder builder);

    @Nonnull 
    public Project createProject (@Nonnull Project.Builder builder);

    @Nonnull
    public JobEvent createJobEvent (@Nonnull JobEvent.Builder builder);
  }
