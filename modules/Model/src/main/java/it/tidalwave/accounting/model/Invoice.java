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
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.LocalDate;
import java.util.List;
import it.tidalwave.accounting.model.spi.ObjectFactory;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.As;
import it.tidalwave.util.Finder;
import it.tidalwave.util.Id;
import it.tidalwave.role.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@Immutable
public interface Invoice extends Identifiable, As
  {
    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @AllArgsConstructor// FIXME (access = PRIVATE)
    @Immutable @With @Getter @ToString
    public static class Builder
      {
        public static interface Callback // Lombok @With doesn't support builder subclasses
          {
            public void register (@Nonnull final Invoice invoice);

            public static final Callback DEFAULT = (invoice) -> {};
          }

        private final Id id;
        private final String number;
        private final Project project;
        private final List<? extends JobEvent> jobEvents; // FIXME: immutablelist
        private final LocalDate date;
        private final LocalDate dueDate;
        private final Money earnings;
        private final Money tax;
        private final Callback callback;

        public Builder()
          {
            this(Callback.DEFAULT);
          }

        public Builder (@Nonnull final Callback callback)
          {
            this(new Id(""), "", null, null, null, null, Money.ZERO, Money.ZERO, callback);
          }

        @Nonnull
        public Builder with (@Nonnull final Builder builder)
          {
            return builder.withCallback(callback);
          }

        @Nonnull
        public Invoice create()
          {
              // TODO
//            if (!jobEvents.stream().allMatch(jobEvent -> jobEvent.getProject() == project))
//              {
//                // FIXME: better diagnostics
//                throw new IllegalArgumentException("Illegal project for jobEvent");
//              }

            final var invoice = ObjectFactory.getInstance().createInvoice(this);
            callback.register(invoice);
            return invoice;
          }
      }

    /*******************************************************************************************************************
     *
     * Creates a finder for job events.
     *
     * @return    the finder
     *
     ******************************************************************************************************************/
    @Nonnull
    public Finder<JobEvent> findJobEvents();

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
