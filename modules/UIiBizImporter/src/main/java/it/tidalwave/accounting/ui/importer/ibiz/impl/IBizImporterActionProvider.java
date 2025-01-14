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
package it.tidalwave.accounting.ui.importer.ibiz.impl;

import javax.annotation.Nonnull;
import it.tidalwave.accounting.commons.ImportRequest;
import it.tidalwave.role.ui.Displayable;
import it.tidalwave.role.ui.ToolBarModel;
import it.tidalwave.role.ui.UserAction;
import it.tidalwave.role.ui.spi.DefaultUserActionProvider2;
import it.tidalwave.role.ui.spi.MessageSendingUserAction;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.messagebus.MessageBus;
import lombok.RequiredArgsConstructor;

/***************************************************************************************************************************************************************
 *
 * This role provides a "Import..." action for the ToolBar.
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@RequiredArgsConstructor @DciRole(datumType = ToolBarModel.class)
public class IBizImporterActionProvider extends DefaultUserActionProvider2
  {
    @Nonnull
    private final MessageBus messageBus;

    @Override @Nonnull
    protected UserAction getSingleAction()
      {
        return MessageSendingUserAction.of(messageBus, ImportRequest::new, Displayable.of("Import..."));
      }
  }
