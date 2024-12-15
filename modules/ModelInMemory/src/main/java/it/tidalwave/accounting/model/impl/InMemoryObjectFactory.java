/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2024 Tidalwave s.a.s. (http://tidalwave.it)
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
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.spi.ObjectFactory;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public class InMemoryObjectFactory implements ObjectFactory
  {
    @Override @Nonnull 
    public Customer createCustomer (@Nonnull final Customer.Builder builder)
      {
        return new InMemoryCustomer(builder);
      }

    @Override @Nonnull 
    public Invoice createInvoice (@Nonnull final Invoice.Builder builder)
      {
        return new InMemoryInvoice(builder);
      }

    @Override @Nonnull 
    public Project createProject (@Nonnull final Project.Builder builder)
      {
        return new InMemoryProject(builder);
      }

    @Override @Nonnull 
    public JobEvent createJobEvent (@Nonnull final JobEvent.Builder builder)
      {
        final var events = builder.getEvents();
        
        if ((events != null) && !events.isEmpty())
          {
            return new InMemoryJobEventGroup(builder);
          }
        else if (builder.getType() == JobEvent.Type.TIMED)
          {
            return new InMemoryTimedJobEvent(builder);
          }
        else
          {
            return new InMemoryFlatJobEvent(builder);
          }
      }
  }
