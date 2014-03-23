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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;
import static lombok.AccessLevel.PRIVATE;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode @ToString
public class Invoice
  {
    @AllArgsConstructor(access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        private final Project project;
        private final List<JobEvent> jobEvents; // FIXME: immutablelist
        private final LocalDate date;
        private final int daysUntilDue;
        private final LocalDate dueDate;
        private final Money earnings;
        private final Money tax;

        @Nonnull
        public Invoice create()
          {
            return new Invoice(this);
          }
      }

    @Nonnull
    private final Project project;

    @Nonnull
    private final List<JobEvent> jobEvents; // FIXME: immutablelist

    @Nonnull
    private final LocalDate date;

    @Nonnegative
    private final int daysUntilDue;

    @Nonnull // not necessarily date + daysUntilDue because of rounding
    private final LocalDate dueDate;

    @Nonnull
    private final Money earnings;

    @Nonnull
    private final Money tax;

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    private Invoice (final @Nonnull Builder builder)
      {
        this.project = builder.getProject();
        this.jobEvents = builder.getJobEvents();
        this.date = builder.getDate();
        this.daysUntilDue = builder.getDaysUntilDue();
        this.dueDate = builder.getDate(); // FIXME: round to the end of the month
        this.earnings = builder.getEarnings();
        this.tax = builder.getTax();
      }
  }
