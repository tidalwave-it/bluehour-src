/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - hg clone https://bitbucket.org/tidalwave/bluehour-src
 * %%
 * Copyright (C) 2013 - 2014 Tidalwave s.a.s. (http://tidalwave.it)
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
import java.util.List;
import java.time.LocalDate;
import it.tidalwave.util.Finder8;
import it.tidalwave.util.As;
import it.tidalwave.util.Id;
import it.tidalwave.role.Identifiable;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.ObjectFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
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
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        public static interface Callback // Lombok @Wither doesn't support builder subclasses
          {
            public void register (final @Nonnull Invoice invoice);

            public static final Callback DEFAULT = (invoice) -> {};
          }

        private final Id id;
        private final String number;
        private final Project project;
        private final List<JobEvent> jobEvents; // FIXME: immutablelist
        private final LocalDate date;
        private final LocalDate dueDate;
        private final Money earnings;
        private final Money tax;
        private final Callback callback;

        public Builder()
          {
            this(Callback.DEFAULT);
          }

        public Builder (final @Nonnull Callback callback)
          {
            this(new Id(""), "", null, null, null, null, Money.ZERO, Money.ZERO, callback);
          }

        @Nonnull
        public Builder with (final @Nonnull Builder builder)
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
            
            final Invoice invoice = ObjectFactory.getInstance().createInvoice(this);
            callback.register(invoice);
            return invoice;
          }        
      }

    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public Finder8<JobEvent> findJobEvents();
        
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public Builder toBuilder();
  }
