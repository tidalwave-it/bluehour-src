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
package it.tidalwave.role.ui.spi;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import it.tidalwave.role.Aggregate;
import it.tidalwave.role.ui.PresentationModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * A specialisation of {@link Aggregate<PresentationModel>} which offers a convenience method for aggregating
 * its contained objects.
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PresentationModelAggregate implements Aggregate<PresentationModel>
  {
    /*******************************************************************************************************************
     *
     * A convenience method for transforming a varargs of roles to a {@link Collection}.
     *
     * @param   roles   the roles
     * @return          the collection
     * @it.tidalwave.experimental
     *
     ******************************************************************************************************************/
    @Nonnull
    public static Collection<Object> r (final @Nonnull Object ... roles)
      {
        return Arrays.asList(roles);
      }

    @Nonnull
    private final Aggregate<PresentationModel> delegate;

    /*******************************************************************************************************************
     *
     * Creates a new, empty instance.
     *
     * @return          the new instance
     *
     ******************************************************************************************************************/
    @Nonnull
    public static PresentationModelAggregate newInstance()
      {
        return new PresentationModelAggregate(it.tidalwave.role.Aggregate.newInstance());
      }

    /*******************************************************************************************************************
     *
     * Adds another {@link PresentationModel} with the given roles, associated to the given name. With a plain
     * {@link Aggregate<PresentationModel>} the code would be:
     *
     * <pre>
     *   Aggregate&lt;PresentationModel&gt; aggregate = Aggregate.newInstance()
     *            .with("name", PresentationModel.of("", r(role1, role2, role3));
     * </pre>
     *
     * The simplified code is:
     *
     * <pre>
     *   Aggregate&lt;PresentationModel&gt; aggregate = PresentationModelAggregate.newInstance()
     *            .withPmOf("name", r(role1, role2, role3));
     * </pre>
     *
     * @param   name    the name of the {@code PresentationModel}
     * @param   roles   the roles
     * @return          the new {@code PresentationModel}
     *
     ******************************************************************************************************************/
    @Nonnull
    public PresentationModelAggregate withPmOf (final @Nonnull String name, final @Nonnull Collection<Object> roles)
      {
         return new PresentationModelAggregate(delegate.with(name, PresentationModel.of("", roles.toArray())));
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Optional<PresentationModel> getByName (final @Nonnull String name)
      {
        return delegate.getByName(name);
      }
  }