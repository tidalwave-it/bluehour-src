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
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import it.tidalwave.util.Finder;
import it.tidalwave.util.FinderStream;
import it.tidalwave.util.FinderStreamSupport;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;
import it.tidalwave.role.Identifiable;
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
public class Invoice implements Identifiable
  {
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @AllArgsConstructor(access = PRIVATE)
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
        private final int daysUntilDue;
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
            this(new Id(""), "", null, null, null, 0, null, Money.ZERO, Money.ZERO, callback);
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
            
            final Invoice invoice = new Invoice(this);
            callback.register(invoice);
            return invoice;
          }        
      }

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    class JobEventFinder extends FinderStreamSupport<JobEvent, JobEventFinder>
                         implements ExtendedFinderSupport<JobEvent, JobEventFinder>, 
                                    FinderStream<JobEvent>, 
                                    Finder<JobEvent>
      {
        @Override @Nonnull
        protected List<? extends JobEvent> computeResults() 
          {
            return new ArrayList<>(jobEvents);
          }
      }
    
    @Getter @Nonnull
    private final Id id;
    
    @Getter
    private final String number;
    
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
        this.id = builder.getId();
        this.number = builder.getNumber();
        this.project = builder.getProject();
        this.jobEvents = builder.getJobEvents();
        this.date = builder.getDate();
        this.daysUntilDue = builder.getDaysUntilDue();
        this.dueDate = builder.getDueDate(); // FIXME: round to the end of the month
        this.earnings = builder.getEarnings();
        this.tax = builder.getTax();
      }
    
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public FinderStream<JobEvent> findJobEvents()
      {
        return new JobEventFinder();
      }
    
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public Builder asBuilder()
      {
        return new Builder(id, number, project, jobEvents, date, daysUntilDue, dueDate, 
                           earnings, tax, Builder.Callback.DEFAULT);
      }
  }
