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
package it.tidalwave.application.javafx;

import javax.annotation.Nonnull;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import it.tidalwave.application.spi.ToolBarModelSupport;
import it.tidalwave.role.ui.Displayable;
import it.tidalwave.role.ui.javafx.JavaFXBinder;
import static it.tidalwave.role.ui.Displayable._Displayable_;
import static it.tidalwave.role.ui.UserActionProvider._UserActionProvider_;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public class JavaFXToolBarModel extends ToolBarModelSupport
  {
    @Override
    public void populate (@Nonnull final Object binder, @Nonnull final Object toolBar)
      {
        as(_UserActionProvider_).getActions().stream().map((action) ->
          {
            final var button = new Button();
            // FIXME: move to JavaFXBinder
            button.setText(action.maybeAs(_Displayable_).map(Displayable::getDisplayName).orElse("???"));
            ((JavaFXBinder)binder).bind(button, action);
            return button;
          })
        .forEach(button -> ((ToolBar)toolBar).getItems().add(button));
      }
  }
