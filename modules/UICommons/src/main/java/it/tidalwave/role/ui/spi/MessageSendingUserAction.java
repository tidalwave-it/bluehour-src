/*
 * #%L
 * *********************************************************************************************************************
 *
 * NorthernWind - lightweight CMS
 * http://northernwind.tidalwave.it - hg clone https://bitbucket.org/tidalwave/northernwind-src
 * %%
 * Copyright (C) 2013 - 2025 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.role.ui.spi;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.function.Supplier;
import it.tidalwave.util.Parameters;
import it.tidalwave.role.ui.UserAction;
import it.tidalwave.messagebus.MessageBus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import static it.tidalwave.util.Parameters.r;

/***************************************************************************************************************************************************************
 *
 * TODO: move to TheseFoolishThings?
 * 
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageSendingUserAction
  {
    @Nonnull
    public static UserAction of (@Nonnull final MessageBus messageBus,
                                 @Nonnull final Supplier<Object> messageSupplier,
                                 @Nonnull final Collection<Object> roles)
      {
        return UserAction.of(() -> messageBus.publish(messageSupplier.get()), roles);
      }

    @Nonnull
    public static UserAction of (@Nonnull final MessageBus messageBus,
                                 @Nonnull final Supplier<Object> messageSupplier,
                                 @Nonnull final Object role)
      {
        Parameters.mustNotBeArrayOrCollection(role, "role");
        return of(messageBus, messageSupplier, r(role));
      }
  }
