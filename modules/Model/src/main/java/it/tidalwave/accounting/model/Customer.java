/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - hg clone https://bitbucket.org/tidalwave/bluehour-src
 * %%
 * Copyright (C) 2013 - 2013 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import it.tidalwave.util.Id;
import it.tidalwave.role.Identifiable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;
import static lombok.AccessLevel.PRIVATE;

/***********************************************************************************************************************
 *
 * This class models a customer.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @Getter @EqualsAndHashCode @ToString
public class Customer implements Identifiable
  {
    @AllArgsConstructor(access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        public static interface Callback // Lombok @Wither doesn't support builder subclasses
          {
            public void register (final @Nonnull Customer customer);

            public static final Callback DEFAULT = new Callback()
              {
                @Override
                public void register (final @Nonnull Customer customer)
                  {
                  }
              };
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

        public Builder (final @Nonnull Callback callback)
          {
            this(new Id(""), "", Address.EMPTY, "", callback);
          }


        @Nonnull
        public Customer create()
          {
            final Customer customer = new Customer(this);
            callback.register(customer);
            return customer;
          }
      }

    @Nonnull
    private final Id id;

    @Nonnull
    private final String name;

    @Nonnull
    private final Address billingAddress;

    @Nonnull
    private final String vatNumber;

    @Nonnull
    public static Builder builder()
      {
        return new Builder();
      }

    protected Customer (final @Nonnull Builder builder)
      {
        this.id = builder.getId();
        this.name = builder.getName();
        this.billingAddress = builder.getBillingAddress();
        this.vatNumber = builder.getVatNumber();
      }
  }
