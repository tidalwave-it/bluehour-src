/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2024 by Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.ui.importer.ibiz.impl;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import it.tidalwave.accounting.commons.AccountingOpenRequest;
import it.tidalwave.accounting.commons.ImportRequest;
import it.tidalwave.accounting.importer.ibiz.IBizImporterBuilderFactory;
import it.tidalwave.accounting.ui.importer.ibiz.IBizImporterPresentation;
import it.tidalwave.accounting.ui.importer.ibiz.IBizImporterPresentationControl;
import it.tidalwave.util.annotation.VisibleForTesting;
import it.tidalwave.role.ui.BoundProperty;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.accounting.role.Saveable._Saveable_;
import static it.tidalwave.util.ui.UserNotificationWithFeedback.*;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@RequiredArgsConstructor @DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultIBizImporterPresentationControl implements IBizImporterPresentationControl
  {
    @Nonnull
    private final MessageBus messageBus;
    
    @Nonnull
    private final IBizImporterBuilderFactory importerBuilderFactory;
    
    private final BoundProperty<Path> iBizFolder = new BoundProperty<>();

    @Nonnull
    private final IBizImporterPresentation presentation;
    
    @VisibleForTesting void onImportRequest (@Nonnull @ListensTo final ImportRequest request)
      throws IOException
      {
        log.info("onImportRequest({})", request);
        presentation.bind(iBizFolder);
        iBizFolder.set(Path.of(System.getProperty("user.home") + "/Settings/iBiz"));
        presentation.chooseFolder(notificationWithFeedback().withFeedback(feedback().withOnConfirm(this::onConfirm)));
      }

    private void onConfirm()
      {
        // TODO: warn for overwriting data, ask for confirmation
        // FIXME: this gets executed in JavaFX thread
        try
          {
            presentation.lock();
            final var accounting = importerBuilderFactory.newBuilder()
                                                         .withPath(iBizFolder.get())
                                                         .create();
            accounting.importAll();
            accounting.as(_Saveable_).save();
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
  }
