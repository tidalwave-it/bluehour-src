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
package it.tidalwave.accounting.ui.projectexplorer.impl.javafx;

import jakarta.annotation.Nonnull;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import it.tidalwave.accounting.ui.projectexplorer.ProjectExplorerPresentation;
import it.tidalwave.ui.core.role.PresentationModel;
import it.tidalwave.ui.javafx.JavaFXBinder;
import lombok.RequiredArgsConstructor;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@RequiredArgsConstructor
public class JavaFxProjectExplorerPresentationDelegate implements ProjectExplorerPresentation
  {
    @Nonnull
    private final JavaFXBinder binder;

    @FXML
    private TableView<PresentationModel> tvProjectExplorer;

    @Override
    public void populate (@Nonnull final PresentationModel pm)
      {
        binder.bind(tvProjectExplorer, pm);
      }
  }
