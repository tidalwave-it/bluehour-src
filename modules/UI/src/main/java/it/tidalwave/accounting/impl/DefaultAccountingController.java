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
package it.tidalwave.accounting.impl;

import jakarta.annotation.Nonnull;
import java.io.IOException;
import org.springframework.stereotype.Component;
import it.tidalwave.accounting.commons.AccountingOpenRequest;
import it.tidalwave.accounting.commons.AccountingOpenedEvent;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.ui.core.message.PowerOnEvent;
import it.tidalwave.util.annotation.VisibleForTesting;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.accounting.role.Loadable._Loadable_;

/***************************************************************************************************************************************************************
 *
 * @stereotype Controller
 * 
 * This business controller manages the life cycle of the {@link Accounting} instance, loading it during the 
 * initialization.
 * 
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Component @RequiredArgsConstructor @DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultAccountingController 
  {
    @Nonnull
    private final MessageBus messageBus;

    // Don't use @Nonnull or Lombok will try to initialize it in the constructor
    private Accounting accounting;

    /***********************************************************************************************************************************************************
     * Loads the {@link Accounting} at initialization.
     **********************************************************************************************************************************************************/
    @VisibleForTesting void onInitialize (@ListensTo final PowerOnEvent event)
      {
        try
          {
            log.info("onInitialize({})", event);
            accounting = Accounting.createNew().as(_Loadable_).load();
            messageBus.publish(new AccountingOpenedEvent(accounting));
          }
        catch (IOException e)
          {
            throw new RuntimeException(e);
          }
      }
    
    /***********************************************************************************************************************************************************
     * Reply with a message carrying a reference to the {@link Accounting} instance. 
     **********************************************************************************************************************************************************/
    @VisibleForTesting void onAccountingOpenRequest (@Nonnull @ListensTo final AccountingOpenRequest request)
      {
        // already done at this point, just send a response
        messageBus.publish(new AccountingOpenedEvent(accounting));
      }
  }
