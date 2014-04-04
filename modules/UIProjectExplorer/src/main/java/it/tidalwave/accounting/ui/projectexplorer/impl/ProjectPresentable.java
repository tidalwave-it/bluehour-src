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
package it.tidalwave.accounting.ui.projectexplorer.impl;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.ui.Presentable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import it.tidalwave.role.ui.spi.DefaultStyleable;
import it.tidalwave.accounting.util.AggregatePresentationModelBuilder;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;

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
        final List<Object> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(instanceRoles));
        temp.add(aggregateBuilder().create());
        return new DefaultPresentationModel(project, temp.toArray());
      }
    
    @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder()
      {
        final AggregatePresentationModelBuilder builder = new AggregatePresentationModelBuilder();
        // FIXME: uses the column header names, should be an internal id instead
        builder.put("Client",     (Displayable) () -> project.getCustomer().getName());
        builder.put("Status",     (Displayable) () -> project.getStatus().name());
        builder.put("#",          (Displayable) () -> project.getNumber());
        builder.put("Name",       (Displayable) () -> project.getName());
        builder.put("Start Date", (Displayable) () -> DATE_FORMATTER.format(project.getStartDate()),
                                  new DefaultStyleable("right-aligned"));
        builder.put("Due Date",   (Displayable) () -> DATE_FORMATTER.format(project.getEndDate()),
                                  new DefaultStyleable("right-aligned"));
        builder.put("Budget",     (Displayable) () -> MONEY_FORMATTER.format(project.getBudget()),
                                  new DefaultStyleable("right-aligned"));
        builder.put("Notes",      (Displayable) () -> project.getNotes());
        
        // FIXME: these are dynamically computed, can be slow - should be also cached? Here or in the data objects?
        builder.put("Earnings",   (Displayable) () -> MONEY_FORMATTER.format(project.getEarnings()),
                                  new DefaultStyleable("right-aligned"));
        builder.put("Time",       (Displayable) () -> DURATION_FORMATTER.format(project.getDuration()),
                                  new DefaultStyleable("right-aligned"));
        builder.put("Invoiced",   (Displayable) () -> MONEY_FORMATTER.format(project.getInvoicedEarnings()),
                                  new DefaultStyleable("right-aligned"));

        return builder;
      }
  }
