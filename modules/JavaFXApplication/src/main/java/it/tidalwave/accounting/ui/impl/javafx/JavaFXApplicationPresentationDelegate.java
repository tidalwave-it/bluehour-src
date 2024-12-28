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
package it.tidalwave.accounting.ui.impl.javafx;

import javax.annotation.Nonnull;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import it.tidalwave.accounting.ui.customerexplorer.impl.javafx.JavaFxCustomerExplorerPresentation;
import it.tidalwave.accounting.ui.jobeventexplorer.impl.javafx.JavaFxJobEventExplorerPresentation;
import it.tidalwave.accounting.ui.projectexplorer.impl.javafx.JavaFxProjectExplorerPresentation;
import it.tidalwave.ui.core.annotation.Assemble;
import it.tidalwave.ui.core.annotation.PresentationAssembler;
import it.tidalwave.ui.javafx.JavaFXBinder;
import it.tidalwave.ui.javafx.JavaFXToolBarControl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***************************************************************************************************************************************************************
 *
 * @author Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@PresentationAssembler @RequiredArgsConstructor @Slf4j @Getter
public class JavaFXApplicationPresentationDelegate
  {
    @FXML
    private ToolBar tbToolBar;

    @FXML
    private AnchorPane pnCustomerExplorer;

    @FXML
    private AnchorPane pnProjectExplorer;

    @FXML
    private AnchorPane pnJobEventExplorer;

    @Assemble
    public void assemble (@Nonnull final JavaFXBinder binder,
                          @Nonnull final JavaFXToolBarControl toolBarControl,
                          @Nonnull final JavaFxCustomerExplorerPresentation customerExplorer,
                          @Nonnull final JavaFxProjectExplorerPresentation projectExplorer,
                          @Nonnull final JavaFxJobEventExplorerPresentation jobEventExplorer)
      {
        toolBarControl.populate(binder, tbToolBar);
        put(pnCustomerExplorer, customerExplorer.getNad().getNode());
        put(pnProjectExplorer, projectExplorer.getNad().getNode());
        put(pnJobEventExplorer, jobEventExplorer.getNad().getNode());
      }

    private static void put (@Nonnull final AnchorPane anchorPane, @Nonnull final Node node)
      {
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        anchorPane.getChildren().add(node);
      }
  }
