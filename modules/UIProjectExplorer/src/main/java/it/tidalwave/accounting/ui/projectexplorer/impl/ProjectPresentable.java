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
 * $Id$
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.ui.projectexplorer.impl;

import javax.annotation.Nonnull;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.role.Aggregate;
import it.tidalwave.role.ui.AggregatePresentationModelBuilder;
import it.tidalwave.role.ui.Displayable;
import it.tidalwave.role.ui.Displayable2;
import it.tidalwave.role.ui.Presentable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.Styleable;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.CustomerSpi;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.commons.Styleables.RIGHT_ALIGNED;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;
import static it.tidalwave.role.ui.PresentationModel.concat;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = ProjectSpi.class)
@RequiredArgsConstructor
public class ProjectPresentable implements Presentable
  {
    @Nonnull
    private final ProjectSpi project;

    @Override
    public PresentationModel createPresentationModel (final @Nonnull Object... instanceRoles)
      {
        return PresentationModel.of(project, concat(aggregatePresentationModel(), instanceRoles));
      }

    @Nonnull
    protected Aggregate<PresentationModel> aggregatePresentationModel()
      {
        final Money budget           = project.getBudget();
        // FIXME: these are dynamically computed, can be slow - should be also cached? Here or in the data objects?
        final Money earnings         = project.getEarnings();
        final Money invoicedEarnings = project.getInvoicedEarnings();

        // FIXME: uses the column header names, should be an internal id instead

        return AggregatePresentationModelBuilder.newInstance()
                .with("Client",     Displayable.of(((CustomerSpi)project.getCustomer()).getName()))
                .with("Status",     Displayable.of(project.getStatus().name()))
                .with("#",          Displayable2.of(project::getNumber))
                .with("Name",       Displayable2.of(project::getName))
                .with("Start Date", Displayable2.of(DATE_FORMATTER, project.getStartDate()), RIGHT_ALIGNED)
                .with("Due Date",   Displayable2.of(DATE_FORMATTER, project.getEndDate()),   RIGHT_ALIGNED)
                .with("Notes",      Displayable2.of(project::getNotes))
                .with("Time",       Displayable2.of(DURATION_FORMATTER, project.getDuration()), RIGHT_ALIGNED)
                .with("Budget",     Displayable2.of(MONEY_FORMATTER, budget),
                      Styleable.of("right-aligned", budget.isEqualTo(Money.ZERO) ? "alerted" : ""))
                .with("Earnings",   Displayable2.of(MONEY_FORMATTER, earnings),
                      Styleable.of("right-aligned", earnings.greaterThan(budget) ? "alerted" : "",
                                                    earnings.isEqualTo(budget) ? "green" : ""))
                .with("Invoiced",   Displayable2.of(MONEY_FORMATTER, invoicedEarnings),
                      Styleable.of("right-aligned", invoicedEarnings.greaterThan(earnings) ? "alerted" :
                                                    invoicedEarnings.isEqualTo(earnings) ? "green" : ""))
                .create();
      }
  }
