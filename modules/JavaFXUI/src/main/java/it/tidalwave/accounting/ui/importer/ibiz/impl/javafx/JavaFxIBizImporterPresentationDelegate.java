/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2023 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.ui.importer.ibiz.impl.javafx;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import it.tidalwave.util.ui.UserNotificationWithFeedback;
import it.tidalwave.role.ui.BoundProperty;
import it.tidalwave.role.ui.javafx.JavaFXBinder;
import it.tidalwave.accounting.ui.importer.ibiz.IBizImporterPresentation;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
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
