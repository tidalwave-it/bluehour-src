/*
 * #%L
 * *********************************************************************************************************************
 *
 * NorthernWind - lightweight CMS
 * http://northernwind.tidalwave.it - hg clone https://bitbucket.org/tidalwave/northernwind-src
 * %%
 * Copyright (C) 2013 - 2021 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.role.ui.spi;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import it.tidalwave.role.ui.Displayable;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.role.ui.UserAction;

/***********************************************************************************************************************
 *
 * FIXME: move to TheseFoolishThings.
 * 
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class MessageSendingUserAction
  {
    @Nonnull
    public static UserAction of (final @Nonnull MessageBus messageBus,
                                 final @Nonnull String displayName,
                                 final @Nonnull Supplier<Object> messageSupplier)
      {
        return UserAction.of(() -> messageBus.publish(messageSupplier.get()), Displayable.of(displayName));
      }
  }

