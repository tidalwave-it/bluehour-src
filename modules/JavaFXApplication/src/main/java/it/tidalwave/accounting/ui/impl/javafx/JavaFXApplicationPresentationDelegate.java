/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2024 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.ui.impl.javafx;

import javax.annotation.Nonnull;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import it.tidalwave.role.ui.ToolBarModel;
import it.tidalwave.role.ui.javafx.JavaFXBinder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author Fabrizio Giudici
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor @Slf4j @Getter
public class JavaFXApplicationPresentationDelegate
  {
    @Nonnull
    private final JavaFXBinder binder;

    @FXML
    private ToolBar tbToolBar;

    @FXML
    private AnchorPane pnCustomerExplorer;

    @FXML
    private AnchorPane pnProjectExplorer;

    @FXML
    private AnchorPane pnJobEventExplorer;

    public void assemble (@Nonnull final ToolBarModel toolBarModel,
                          @Nonnull final Node ndCustomerExplorer,
                          @Nonnull final Node ndProjectExplorer,
                          @Nonnull final Node ndJobEventExplorer)
      {
        toolBarModel.populate(binder, tbToolBar);
        put(pnCustomerExplorer, ndCustomerExplorer);
        put(pnProjectExplorer, ndProjectExplorer);
        put(pnJobEventExplorer, ndJobEventExplorer);
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
