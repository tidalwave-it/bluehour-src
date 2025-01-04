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
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.spi.InvoiceSpi;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.As;
import it.tidalwave.util.Finder;
import it.tidalwave.util.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
/* @Immutable */ @EqualsAndHashCode @ToString(exclude = "as")
public class InMemoryInvoice implements InvoiceSpi
  {
    private static final long serialVersionUID = 1L;

    @Delegate
    private final As as = As.forObject(this);

    @Getter @Nonnull
    private final Id id;

    @Getter
    private final String number;

    @Getter @Nonnull
    private final Project project;

    @Nonnull
    private final List<InMemoryJobEvent> jobEvents; // FIXME: immutablelist

    @Nonnull
    private final LocalDate date;

    @Nonnull
    private final LocalDate dueDate;

    @Getter @Nonnull
    private final Money earnings;

    @Getter @Nonnull
    private final Money tax;

    /***********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/
    public /* FIXME private */ InMemoryInvoice (@Nonnull final Builder builder)
      {
        this.id = builder.getId();
        this.number = builder.getNumber();
        this.project = builder.getProject();
        this.jobEvents = (List<InMemoryJobEvent>)builder.getJobEvents();
        this.date = builder.getDate();
        this.dueDate = builder.getDueDate(); // FIXME: round to the end of the month?
        this.earnings = builder.getEarnings();
        this.tax = builder.getTax();
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public Finder<JobEvent> findJobEvents()
      {
        return Finder.ofSupplier(() -> new CopyOnWriteArrayList<>(jobEvents));
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public Builder toBuilder()
      {
        return new Builder(id, number, project, jobEvents, date, dueDate, earnings, tax, Builder.Callback.DEFAULT);
      }
  }
