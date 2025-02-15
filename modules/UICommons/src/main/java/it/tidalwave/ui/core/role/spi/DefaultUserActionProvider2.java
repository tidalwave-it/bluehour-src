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
package it.tidalwave.ui.core.role.spi;

import jakarta.annotation.Nonnull;
import java.util.List;
import it.tidalwave.ui.core.role.UserAction;

/***************************************************************************************************************************************************************
 *
 * FIXME: merge to DefaultUserActionProvider
 * 
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public class DefaultUserActionProvider2 extends DefaultUserActionProvider
  {
    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public List<UserAction> getActions()
      {
        return List.of(getSingleAction());
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Nonnull
    protected UserAction getSingleAction()
      {
        throw new UnsupportedOperationException();
      }
  }

