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
package it.tidalwave.accounting.impl;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import it.tidalwave.accounting.commons.AccountingOpenRequest;
import it.tidalwave.accounting.commons.AccountingOpenedEvent;
import it.tidalwave.accounting.model.Accounting;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.accounting.role.Loadable.Loadable;

/***********************************************************************************************************************
 *
 * @stereotype Controller
 * 
 * This business controller manages the life cycle of the {@link Accounting} instance, loading it during the 
 * initialization.
 * 
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultAccountingController 
  {
    @Inject @Named("applicationMessageBus")
    private MessageBus messageBus;
    
    private Accounting accounting;

    /*******************************************************************************************************************
     *
     * Loads the {@link Accounting} at initialization.
     *
     ******************************************************************************************************************/
    @PostConstruct
    @VisibleForTesting void initialize()
      {
        try
          {
            log.info("initialize()");
            accounting = Accounting.createNew().as(Loadable).load();
            messageBus.publish(new AccountingOpenedEvent(accounting));
          }
        catch (IOException e)
          {
            throw new RuntimeException(e);
          }
      }
    
    /*******************************************************************************************************************
     *
     * Reply with a message carrying a reference to the {@link Accounting} instance. 
     *
     ******************************************************************************************************************/
    @VisibleForTesting void onAccountingOpenRequest (final @Nonnull @ListensTo AccountingOpenRequest request)
      {
        // already done at this point, just send a response
        messageBus.publish(new AccountingOpenedEvent(accounting));
      }
  }
