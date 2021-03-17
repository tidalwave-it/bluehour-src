/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
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
package it.tidalwave.role.ui;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import it.tidalwave.role.Aggregate;
import lombok.NoArgsConstructor;

/***********************************************************************************************************************
 *
 * A builder for an {@link Aggregate} of {@link PresentationModel}s.
 *
 * @stereotype  role factory, builder
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@NoArgsConstructor(staticName = "newInstance")
public class AggregatePresentationModelBuilder
  {
    private final Map<String, PresentationModel> map = new ConcurrentHashMap<>();

    /*******************************************************************************************************************
     *
     * Adds another {@link PresentationModel} with the given roles, associated to the given name.
     *
     * @param   name    the name of the {@code PresentationModel}
     * @param   roles   the roles
     * @return          the new {@code PresentationModel}
     *
     ******************************************************************************************************************/
    @Nonnull
    public AggregatePresentationModelBuilder with (final @Nonnull String name, final @Nonnull Object ... roles)
      {
        map.put(name, PresentationModel.of("", roles));
        return this;
      }

    /*******************************************************************************************************************
     *
     * Creates the {@link Aggregate} from the previously accumulated items.
     *
     * @return  the {@code Aggregate}
     *
     ******************************************************************************************************************/
    @Nonnull
    public Aggregate<PresentationModel> create()
      {
        return Aggregate.of(map);
      }
  }