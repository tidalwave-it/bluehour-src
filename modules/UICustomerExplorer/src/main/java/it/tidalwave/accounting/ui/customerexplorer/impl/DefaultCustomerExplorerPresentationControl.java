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
package it.tidalwave.accounting.ui.customerexplorer.impl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.Selectable;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import it.tidalwave.accounting.commons.AccountingOpenRequest;
import it.tidalwave.accounting.commons.AccountingOpenedEvent;
import it.tidalwave.accounting.commons.CustomerSelectedEvent;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.ui.customerexplorer.CustomerExplorerPresentation;
import it.tidalwave.accounting.ui.customerexplorer.CustomerExplorerPresentationControl;
import lombok.extern.slf4j.Slf4j;
import static java.util.Comparator.*;
import static it.tidalwave.role.ui.Presentable.Presentable;
import static it.tidalwave.role.ui.spi.PresentationModelCollectors.toCompositePresentationModel;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultCustomerExplorerPresentationControl implements CustomerExplorerPresentationControl
  {
    @Inject @Named("applicationMessageBus") @Nonnull
    private MessageBus messageBus;

    @Inject @Nonnull
    private CustomerExplorerPresentation presentation;

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @Override
    public void initialize()
      {
        log.info("initialize()");  
        messageBus.publish(new AccountingOpenRequest());
      }
    
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @VisibleForTesting void onAccountingOpenedEvent (final @Nonnull @ListensTo AccountingOpenedEvent event)
      {
        log.info("onAccountingOpenedEvent({})", event);
        presentation.populate(event.getAccounting().getCustomerRegistry().findCustomers()
                                .sorted(comparing(Customer::getName))
                                .map(customer -> createPresentationModelFor(customer))
                                .collect(toCompositePresentationModel()));
      }

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @Nonnull
    @VisibleForTesting PresentationModel createPresentationModelFor (final @Nonnull Customer customer)
      {
        final Selectable selectable = () -> messageBus.publish(new CustomerSelectedEvent(customer));
        return customer.as(Presentable).createPresentationModel(selectable);
      }
  }
