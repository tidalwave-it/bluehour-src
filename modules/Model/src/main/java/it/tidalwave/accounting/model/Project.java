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
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import it.tidalwave.util.As;
import it.tidalwave.util.Finder;
import it.tidalwave.util.FinderStream;
import it.tidalwave.util.FinderStreamSupport;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.AsSupport;
import it.tidalwave.role.Identifiable;
import it.tidalwave.role.SimpleComposite;
import lombok.AllArgsConstructor;
import lombok.Delegate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;
import static lombok.AccessLevel.PRIVATE;

/***********************************************************************************************************************
 *
 * This class models a project.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @Wither
@AllArgsConstructor(access = PRIVATE) @EqualsAndHashCode @ToString(exclude = {"events", "asSupport"})
public class Project implements SimpleComposite<JobEvent>, Identifiable, As
  {
    @Delegate
    private final AsSupport asSupport = new AsSupport(this);

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @AllArgsConstructor(access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        public enum Status { OPEN, CLOSED };

        public static interface Callback // Lombok @Wither doesn't support builder subclasses
          {
            public void register (final @Nonnull Project project);

            public static final Callback DEFAULT = (project) -> {};
          }

        private final Id id;
        private final Customer customer;
        private final String name;
        private final String number;
        private final String description;
        private final String notes;
        private final Status status;
        private final Money hourlyRate;
        private final Money amount;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final List<JobEvent> events; // FIXME: immutable
        private final Callback callback;

        public Builder()
          {
            this(Callback.DEFAULT);
          }

        public Builder (final @Nonnull Callback callback)
          {
             // FIXME: avoid null
            this(new Id(""), null, "", "", "", "", Status.OPEN, Money.ZERO, Money.ZERO, null, null, 
                 Collections.<JobEvent>emptyList(), callback);
          }

        @Nonnull
        public Builder with (final @Nonnull Builder builder)
          {
            return builder.withCallback(callback);
          }

        @Nonnull
        public Project create()
          {
            final Project project = new Project(this);
            callback.register(project);
            return project;
          }
      }

    @Getter @Nonnull
    private final Id id;
    
    @Getter @Nonnull
    private final Customer customer;

    @Getter @Nonnull
    private final String name;

    @Getter @Nonnull
    private final String number;

    @Getter @Nonnull
    private final String description;

    @Getter @Nonnull
    private final String notes;
    
    @Getter
    private final Builder.Status status;

    @Getter @Nonnull
    private final Money hourlyRate;

    @Getter @Nonnull
    private final Money amount;

    @Getter @Nonnull
    private final LocalDate startDate;

    @Getter @Nonnull
    private final LocalDate endDate;

    @Nonnull
    private final List<JobEvent> events; // FIXME: immutable

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @Nonnull
    public static Project.Builder builder()
      {
        return new Project.Builder();
      }

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    protected Project (final @Nonnull Builder builder)
      {
        this.id = builder.getId();
        this.customer = builder.getCustomer();
        this.name = builder.getName();
        this.number = builder.getNumber();
        this.description = builder.getDescription();
        this.notes = builder.getNotes();
        this.status = builder.getStatus();
        this.hourlyRate = builder.getHourlyRate();
        this.amount = builder.getAmount();
        this.startDate = builder.getStartDate();
        this.endDate = builder.getEndDate();
        this.events = builder.getEvents();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public FinderStream<JobEvent> findChildren()
      {
        return new FinderStreamSupport<JobEvent, Finder<JobEvent>>()
          {
            @Override @Nonnull
            protected List<? extends JobEvent> computeResults()
              {
                return Collections.unmodifiableList(events);
              }
          };
      }
    
    /*******************************************************************************************************************
     *
     * 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public Money getEarnings()
      {
        return findChildren().map(jobEvent -> jobEvent.getEarnings()).reduce(Money.ZERO, Money::add);
      }
    
    /*******************************************************************************************************************
     *
     * 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public Duration getDuration()
      {
        return findChildren().map(jobEvent -> jobEvent.getDuration()).reduce(Duration.ZERO, Duration::plus);
      }
    
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public Builder asBuilder()
      {
        return new Builder(id, customer, name, number, description, notes, status, hourlyRate, amount, 
                           startDate, endDate, events, Project.Builder.Callback.DEFAULT);
      }
  }
