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
package it.tidalwave.accounting.ui.importer.ibiz.impl.javafx;

import jakarta.annotation.Nonnull;
import java.nio.file.Path;
import it.tidalwave.accounting.ui.importer.ibiz.IBizImporterPresentation;
import it.tidalwave.ui.core.UserNotificationWithFeedback;
import it.tidalwave.ui.core.BoundProperty;
import it.tidalwave.ui.javafx.JavaFXBinder;
import lombok.RequiredArgsConstructor;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@RequiredArgsConstructor
public class JavaFxIBizImporterPresentationDelegate implements IBizImporterPresentation
  {
    @Nonnull
    private final JavaFXBinder binder;

    private BoundProperty<Path> iBizFolder;

    @Override
    public void bind (@Nonnull final BoundProperty<Path> iBizFolder)
      {
        this.iBizFolder = iBizFolder;
      }

    @Override
    public void lock()
      {
        // TODO
      }

    @Override
    public void unlock()
      {
        // TODO
      }

    @Override
    public void chooseFolder (@Nonnull final UserNotificationWithFeedback feedback)
      {
        binder.openDirectoryChooserFor(feedback, iBizFolder);
      }

    @Override
    public void notifyError()
      {
        // TODO
      }
  }
