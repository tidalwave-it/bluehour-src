/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2023 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.ui.projectexplorer.impl;

import javax.annotation.Nonnull;
import java.util.Collection;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.role.Aggregate;
import it.tidalwave.role.ui.Displayable;
import it.tidalwave.role.ui.Presentable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.Styleable;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.CustomerSpi;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import it.tidalwave.role.ui.PresentationModelAggregate;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.commons.Styleables.RIGHT_ALIGNED;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;
import static it.tidalwave.util.Parameters.r;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@DciRole(datumType = ProjectSpi.class)
@RequiredArgsConstructor
public class ProjectPresentable implements Presentable
  {
    @Nonnull
    private final ProjectSpi project;

    @Override @Nonnull
    public PresentationModel createPresentationModel (@Nonnull final Collection<Object> instanceRoles)
      {
        return PresentationModel.of(project, r(aggregatePresentationModel(), instanceRoles));
      }

    @Nonnull
    protected Aggregate<PresentationModel> aggregatePresentationModel()
      {
        final Money budget           = project.getBudget();
        // FIXME: these are dynamically computed, can be slow - should be also cached? Here or in the data objects?
        final Money earnings         = project.getEarnings();
        final Money invoicedEarnings = project.getInvoicedEarnings();

        // FIXME: uses the column header names, should be an internal id instead
        return PresentationModelAggregate.newInstance()
                .withPmOf("Client",     r(Displayable.of(((CustomerSpi)project.getCustomer()).getName())))
                .withPmOf("Status",     r(Displayable.of(project.getStatus().name())))
                .withPmOf("#",          r(Displayable.of(project::getNumber)))
                .withPmOf("Name",       r(Displayable.of(project::getName)))
                .withPmOf("Start Date", r(Displayable.of(DATE_FORMATTER::format, project.getStartDate()),
                                                 RIGHT_ALIGNED))
                .withPmOf("Due Date",   r(Displayable.of(DATE_FORMATTER::format, project.getEndDate()),
                                                 RIGHT_ALIGNED))
                .withPmOf("Notes",      r(Displayable.of(project::getNotes)))
                .withPmOf("Time",       r(Displayable.of(DURATION_FORMATTER::format, project.getDuration()),
                                                 RIGHT_ALIGNED))
                .withPmOf("Budget",     r(Displayable.of(MONEY_FORMATTER::format, budget),
                      Styleable.of("right-aligned", budget.isEqualTo(Money.ZERO) ? "alerted" : "")))
                .withPmOf("Earnings",   r(Displayable.of(MONEY_FORMATTER::format, earnings),
                      Styleable.of("right-aligned", earnings.greaterThan(budget) ? "alerted" : "",
                                                    earnings.isEqualTo(budget) ? "green" : "")))
                .withPmOf("Invoiced",   r(Displayable.of(MONEY_FORMATTER::format, invoicedEarnings),
                      Styleable.of("right-aligned", invoicedEarnings.greaterThan(earnings) ? "alerted" :
                                                    invoicedEarnings.isEqualTo(earnings) ? "green" : "")));
      }
  }
