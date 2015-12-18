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
package it.tidalwave.application.javafx;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import it.tidalwave.util.AsException;
import it.tidalwave.role.ui.javafx.JavaFXBinder;
import it.tidalwave.application.spi.ToolBarModelSupport;
import static it.tidalwave.role.Displayable.Displayable;
import static it.tidalwave.role.ui.UserActionProvider.UserActionProvider;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class JavaFXToolBarModel extends ToolBarModelSupport
  {
    @Inject @Nonnull
    private JavaFXBinder binder;
    
    @Override
    public void populate (final @Nonnull Object toolBar) 
      {
        as(UserActionProvider).getActions().stream().map((action) -> 
          {
            final Button button = new Button();
            
            try // FIXME: move to JavaFXBinder
              {
                button.setText(action.as(Displayable).getDisplayName());
              }
            catch (AsException e)
              {
                button.setText("???");
              }
            
            binder.bind(button, action);
            return button;
          })
        .forEach((button) -> 
          {
            ((ToolBar)toolBar).getItems().add(button);
          });
      }
  }
