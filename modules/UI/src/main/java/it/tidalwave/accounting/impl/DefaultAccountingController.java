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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import it.tidalwave.accounting.commons.AccountingOpenRequest;
import it.tidalwave.accounting.commons.AccountingOpenedEvent;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.impl.DefaultAccounting;
import it.tidalwave.accounting.util.PreferencesHandler;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.role.Unmarshallable.Unmarshallable;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultAccountingController 
  {
    @Inject @Named("applicationMessageBus") @Nonnull
    private MessageBus messageBus;
    
    @Inject @Nonnull
    private PreferencesHandler preferencesHandler;
    
    private Accounting accounting = new DefaultAccounting();

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @PostConstruct
    @VisibleForTesting void initialize()
      {
        log.info("initialize()");
        
        try
          {
            loadData();
          }
        catch (IOException e)
          {
            throw new RuntimeException(e);
          }
        
        messageBus.publish(new AccountingOpenedEvent(accounting));
      }
    
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @VisibleForTesting void onAccountingOpenRequest (final @Nonnull @ListensTo AccountingOpenRequest request)
      {
        // already done at this point, just send a response
        messageBus.publish(new AccountingOpenedEvent(accounting));
      }
    
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @VisibleForTesting void loadData()
      throws IOException
      {
        final Path appFolder = preferencesHandler.getAppFolder();
        final Path dataFile = appFolder.resolve("blueHour.xml");
        log.info(">>>> loading data from {}...", dataFile);

        try (final InputStream is = new FileInputStream(dataFile.toFile()))
          {
            accounting = accounting.as(Unmarshallable).unmarshal(is);
          }
      }
  }
