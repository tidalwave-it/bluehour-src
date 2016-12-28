/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2016 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.ui.jobeventexplorer.impl.javafx;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javafx.scene.control.TreeTableView;
import javafx.fxml.FXML;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.javafx.JavaFXBinder;
import it.tidalwave.accounting.ui.jobeventexplorer.JobEventExplorerPresentation;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class JavaFxJobEventExplorerPresentationDelegate implements JobEventExplorerPresentation
  {
    @Inject
    private JavaFXBinder binder;

    @FXML
    private TreeTableView<PresentationModel> ttvJobEventExplorer;

    @Override
    public void populate (final @Nonnull PresentationModel pm)
      {
        binder.bind(ttvJobEventExplorer, pm);
        ttvJobEventExplorer.getRoot().setExpanded(true);
      }
  }
