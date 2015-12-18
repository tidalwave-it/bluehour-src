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
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.spi.CustomerSpi;
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
     * Requests the opening of an {@link Accounting} during initialization.
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
     * Reacts to the notification that an {@link Accounting} has been opened by populating the presentation with
     * the customers.
     * 
     * @param  event  the notification event
     *
     ******************************************************************************************************************/
    @VisibleForTesting void onAccountingOpenedEvent (final @Nonnull @ListensTo AccountingOpenedEvent event)
      {
        log.info("onAccountingOpenedEvent({})", event);
        presentation.populate(event.getAccounting().getCustomerRegistry().findCustomers().stream()
                                   .map(customer -> (CustomerSpi)customer)
                                   .sorted(comparing(CustomerSpi::getName))
                                   .map(customer -> createPresentationModelFor(customer))
                                   .collect(toCompositePresentationModel()));
      }

    /*******************************************************************************************************************
     *
     * Creates a {@link PresentationModel} for a {@link Customer} injecting a {@link Selectable} role which fires a
     * {@link CustomerSelectedEvent} on selection.
     * 
     * @param  customer     the {@code Customer}
     * @return              the {@code PresentationModel}
     *
     ******************************************************************************************************************/
    @Nonnull
    @VisibleForTesting PresentationModel createPresentationModelFor (final @Nonnull Customer customer)
      {
        final Selectable publishEventOnSelection = () -> messageBus.publish(new CustomerSelectedEvent(customer));
        return customer.as(Presentable).createPresentationModel(publishEventOnSelection);
      }
  }
