/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2021 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.time.LocalDate;
import it.tidalwave.util.Finder;
import it.tidalwave.util.F8;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.AsSupport;
import it.tidalwave.accounting.model.Invoice.Builder;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.InvoiceSpi;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.experimental.Delegate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode @ToString(exclude = {"asSupport"})
public class InMemoryInvoice implements InvoiceSpi
  {
    private static final long serialVersionUID = 1L;

    @Delegate
    private final AsSupport asSupport = new AsSupport(this);

    @Getter @Nonnull
    private final Id id;

    @Getter
    private final String number;

    @Getter @Nonnull
    private final Project project;

    @Nonnull
    private final List<JobEvent> jobEvents; // FIXME: immutablelist

    @Nonnull
    private final LocalDate date;

    @Nonnull
    private final LocalDate dueDate;

    @Getter @Nonnull
    private final Money earnings;

    @Getter @Nonnull
    private final Money tax;

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    public /* FIXME private */ InMemoryInvoice (@Nonnull final Builder builder)
      {
        this.id = builder.getId();
        this.number = builder.getNumber();
        this.project = builder.getProject();
        this.jobEvents = builder.getJobEvents();
        this.date = builder.getDate();
        this.dueDate = builder.getDueDate(); // FIXME: round to the end of the month?
        this.earnings = builder.getEarnings();
        this.tax = builder.getTax();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Finder<JobEvent> findJobEvents()
      {
        return F8.ofComputeResults(f -> new CopyOnWriteArrayList<>(jobEvents));
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override @Nonnull
    public Builder toBuilder()
      {
        return new Builder(id, number, project, jobEvents, date, dueDate, earnings, tax, Builder.Callback.DEFAULT);
      }
  }
