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
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import it.tidalwave.util.Finder;
import it.tidalwave.util.FinderStream;
import it.tidalwave.util.FinderStreamSupport;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.AsSupport;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Money;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.Project.Builder;
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
public class InMemoryProject implements Project
  {
    @Delegate
    private final AsSupport asSupport = new AsSupport(this);

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
    public /* FIXME protected */ InMemoryProject (final @Nonnull Builder builder)
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
    @Override @Nonnull
    public Money getEarnings()
      {
        return findChildren().map(jobEvent -> jobEvent.getEarnings()).reduce(Money.ZERO, Money::add);
      }
    
    /*******************************************************************************************************************
     *
     * 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public Duration getDuration()
      {
        return findChildren().map(jobEvent -> jobEvent.getDuration()).reduce(Duration.ZERO, Duration::plus);
      }
    
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public Builder asBuilder()
      {
        return new Builder(id, customer, name, number, description, notes, status, hourlyRate, amount, 
                           startDate, endDate, events, Project.Builder.Callback.DEFAULT);
      }
  }
