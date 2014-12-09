/*
 * #%L
 * *********************************************************************************************************************
 *
 * NorthernWind - lightweight CMS
 * http://northernwind.tidalwave.it - hg clone https://bitbucket.org/tidalwave/northernwind-src
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
package it.tidalwave.accounting.ui.impl.javafx;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeTableView;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.application.ToolBarModel;
import it.tidalwave.accounting.ui.customerexplorer.CustomerExplorerPresentationControl;
import it.tidalwave.accounting.ui.customerexplorer.impl.javafx.JavaFxCustomerExplorerPresentation;
import it.tidalwave.accounting.ui.jobeventexplorer.impl.javafx.JavaFxJobEventExplorerPresentation;
import it.tidalwave.accounting.ui.projectexplorer.ProjectExplorerPresentationControl;
import it.tidalwave.accounting.ui.projectexplorer.impl.javafx.JavaFxProjectExplorerPresentation;
import org.springframework.beans.factory.annotation.Configurable;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Configurable @Slf4j
public class JavaFXApplicationPresentationDelegate
  {
    @Inject @Nonnull
    private CustomerExplorerPresentationControl customerExplorerPresentationControl;

    @Inject @Nonnull
    private JavaFxCustomerExplorerPresentation javaFxCustomerExplorerPresentation;
            
    @Inject @Nonnull
    private ProjectExplorerPresentationControl projectExplorerPresentationControl;

    @Inject @Nonnull
    private JavaFxProjectExplorerPresentation javaFxProjectExplorerPresentation;
            
    @Inject @Nonnull
    private ProjectExplorerPresentationControl jobEventExplorerPresentationControl;

    @Inject @Nonnull
    private JavaFxJobEventExplorerPresentation javaFxJobEventExplorerPresentation;
    
    @Inject @Nonnull
    private ToolBarModel toolBarModel;
    
    @FXML
    private ToolBar tbToolBar;
    
    @FXML
    private ListView<PresentationModel> lvCustomerExplorer;
    
    @FXML
    private TableView<PresentationModel> tvProjectExplorer;
    
    @FXML
    private TreeTableView<PresentationModel> ttvJobEventExplorer;

    public void initialize()
      throws IOException
      {
        // FIXME: controllers can't initialize in postconstruct
        // Too bad because with PAC+EventBus we'd get rid of the control interfaces
        customerExplorerPresentationControl.initialize();
        projectExplorerPresentationControl.initialize();
        jobEventExplorerPresentationControl.initialize();
        javaFxCustomerExplorerPresentation.bind(lvCustomerExplorer);
        javaFxProjectExplorerPresentation.bind(tvProjectExplorer);
        javaFxJobEventExplorerPresentation.bind(ttvJobEventExplorer);
        
        toolBarModel.populate(tbToolBar);
      }    
  }
