/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2015 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.ui.importer.ibiz.impl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.role.ui.BoundProperty;
import it.tidalwave.util.ui.UserNotificationWithFeedback.Feedback;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import it.tidalwave.accounting.commons.AccountingOpenRequest;
import it.tidalwave.accounting.commons.ImportRequest;
import it.tidalwave.accounting.importer.ibiz.IBizImporter;
import it.tidalwave.accounting.importer.ibiz.IBizImporterBuilderFactory;
import it.tidalwave.accounting.ui.importer.ibiz.IBizImporterPresentation;
import it.tidalwave.accounting.ui.importer.ibiz.IBizImporterPresentationControl;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.util.ui.UserNotificationWithFeedback.*;
import static it.tidalwave.accounting.role.Saveable.Saveable;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultIBizImporterPresentationControl implements IBizImporterPresentationControl
  {
    @Inject @Named("applicationMessageBus") @Nonnull
    private MessageBus messageBus;
    
    @Inject @Nonnull
    private IBizImporterBuilderFactory importerBuilderFactory;
    
    private final BoundProperty<Path> iBizFolder = new BoundProperty<>();

    @Inject @Nonnull
    private IBizImporterPresentation presentation;
    
    @VisibleForTesting void onImportRequest (final @Nonnull @ListensTo ImportRequest request)
      throws IOException
      {
        log.info("onImportRequest({})", request);
        presentation.bind(iBizFolder);
        iBizFolder.set(Paths.get(System.getProperty("user.home") + "/Settings/iBiz"));
        presentation.chooseFolder(notificationWithFeedback().withFeedback(new Feedback()
          {
            @Override
            public void onConfirm() 
              {
                // TODO: warn for overwriting data, ask for confirmation
                // FIXME: this gets executed in JavaFX thread
                try
                  {
                    presentation.lock();
                    final IBizImporter accounting = importerBuilderFactory.newBuilder()
                                                                          .withPath(iBizFolder.get())
                                                                          .create();
                    accounting.importAll();
                    accounting.as(Saveable).save();
                    messageBus.publish(new AccountingOpenRequest());

                    // TODO: use a progress bar during the import process
                  }
                catch (Exception e)
                  {
                    log.error("", e);
                    presentation.notifyError();  
                  }
                finally
                  {
                    presentation.unlock();
                  }
              }
          }));
      }
  }
