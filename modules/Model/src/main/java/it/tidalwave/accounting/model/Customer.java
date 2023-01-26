/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2023 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import it.tidalwave.accounting.model.spi.ObjectFactory;
import it.tidalwave.accounting.model.types.Address;
import it.tidalwave.util.As;
import it.tidalwave.util.Id;
import it.tidalwave.role.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

/***********************************************************************************************************************
 *
 * This class models a customer.
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public interface Customer extends Identifiable, As
  {
    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @AllArgsConstructor // FIXME (access = PROTECTED)
    @Immutable @With @Getter @ToString
    public static class Builder
      {
        public static interface Callback // Lombok @With doesn't support builder subclasses
          {
            public void register (@Nonnull final Customer customer);

            public static final Callback DEFAULT = (customer) -> {};
          }

        private final Id id;
        private final String name;
        private final Address billingAddress;
        private final String vatNumber;
        private final Callback callback;

        public Builder()
          {
            this(Callback.DEFAULT);
          }

        public Builder (@Nonnull final Callback callback)
          {
            this(new Id(""), "", Address.EMPTY, "", callback);
          }

        @Nonnull
        public Builder with (@Nonnull final Builder builder)
          {
            return builder.withCallback(callback);
          }

        @Nonnull
        public Customer create()
          {
            final var customer = ObjectFactory.getInstance().createCustomer(this);
            callback.register(customer);
            return customer;
          }
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public static Builder builder()
      {
        return new Builder();
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public ProjectRegistry.ProjectFinder findProjects();

    /*******************************************************************************************************************
     *
     * Returns a builder pre-populated with all the attributes.
     *
     * @return  the builder
     *
     ******************************************************************************************************************/
    @Nonnull
    public Builder toBuilder();
  }
