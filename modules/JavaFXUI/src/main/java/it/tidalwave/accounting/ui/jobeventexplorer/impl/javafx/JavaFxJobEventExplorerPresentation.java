/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
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
package it.tidalwave.accounting.ui.jobeventexplorer.impl.javafx;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javafx.application.Platform;
import javafx.scene.control.TreeTableView;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.javafx.JavaFXBinder;
import it.tidalwave.accounting.ui.jobeventexplorer.JobEventExplorerPresentation;
import org.springframework.beans.factory.annotation.Configurable;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Configurable
public class JavaFxJobEventExplorerPresentation implements JobEventExplorerPresentation
  {
    @Inject @Nonnull
    private JavaFXBinder binder;
    
    private TreeTableView<PresentationModel> ttvJobEventExplorer;

    public void bind (final @Nonnull TreeTableView<PresentationModel> ttvJobEventExplorer) 
      {
        this.ttvJobEventExplorer = ttvJobEventExplorer;
        ttvJobEventExplorer.setShowRoot(false);
      }
    
    @Override
    public void populate (final @Nonnull PresentationModel pm) 
      {
        // FIXME: runLater should be provided by the infrastructure
        Platform.runLater(() ->
          {
            binder.bind(ttvJobEventExplorer, pm);
            ttvJobEventExplorer.getRoot().setExpanded(true);
          });
      }
  }
