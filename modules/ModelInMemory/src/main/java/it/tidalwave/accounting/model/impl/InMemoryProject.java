/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2025 by Tidalwave s.a.s. (http://tidalwave.it)
 *
 * *************************************************************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 *
 * *************************************************************************************************************************************************************
 *
 * git clone https://bitbucket.org/tidalwave/bluehour-src
 * git clone https://github.com/tidalwave-it/bluehour-src
 *
 * *************************************************************************************************************************************************************
 */
package it.tidalwave.accounting.model.impl;

import jakarta.annotation.Nonnull;
// import javax.annotation.concurrent.Immutable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry.JobEventFinder;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.As;
import it.tidalwave.util.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.With;
import lombok.experimental.Delegate;
import static lombok.AccessLevel.PRIVATE;

/***************************************************************************************************************************************************************
 *
 * This class models a project.
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
/* @Immutable */ @With
@AllArgsConstructor(access = PRIVATE) @EqualsAndHashCode @ToString(exclude = {"events", "as", "accounting"})
public class InMemoryProject implements ProjectSpi
  {
    @Setter // FIXME
    private Accounting accounting;
    
    @Delegate
    private final As as = As.forObject(this);

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
    private final Status status;

    @Getter @Nonnull
    private final Money hourlyRate;

    @Getter @Nonnull
    private final Money budget;

    @Getter @Nonnull
    private final LocalDate startDate;

    @Getter @Nonnull
    private final LocalDate endDate;

    @Nonnull
    private final List<InMemoryJobEvent> events; // FIXME: immutable
    
    /***********************************************************************************************************************************************************
     * 
     **********************************************************************************************************************************************************/
    public /* FIXME protected */ InMemoryProject (@Nonnull final Builder builder)
      {
        this.id = builder.getId();
        this.customer = builder.getCustomer();
        this.name = builder.getName();
        this.number = builder.getNumber();
        this.description = builder.getDescription();
        this.notes = builder.getNotes();
        this.status = builder.getStatus();
        this.hourlyRate = builder.getHourlyRate();
        this.budget = builder.getBudget();
        this.startDate = builder.getStartDate();
        this.endDate = builder.getEndDate();
        this.events = (List<InMemoryJobEvent>)builder.getEvents();
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public JobEventFinder findChildren()
      {
        return new InMemoryJobEventFinderFromList(events);
      }
    
    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public Money getEarnings()
      {
        return findChildren().getEarnings();
      }
    
    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public Duration getDuration()
      {
        return findChildren().getDuration();
      }
    
    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public Money getInvoicedEarnings()
      {
        return accounting.getInvoiceRegistry().findInvoices().withProject(this).getEarnings();
      }
    
    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public Builder toBuilder()
      {
        return new Builder(id, customer, name, number, description, notes, status, hourlyRate, budget, 
                           startDate, endDate, events, Project.Builder.Callback.DEFAULT);
      }
  }
