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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.ui.Presentable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import it.tidalwave.accounting.commons.DurationFormat;
import it.tidalwave.accounting.commons.MoneyFormat;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.ui.jobeventexplorer.impl.AggregatePresentationModelBuilder;
import it.tidalwave.role.ui.spi.DefaultStyleable;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = Project.class)
@RequiredArgsConstructor
public class ProjectPresentable implements Presentable
  {
    protected static final DateTimeFormatter DTF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    protected static final MoneyFormat MF = new MoneyFormat();
    protected static final DurationFormat DF = new DurationFormat();
    
    @Nonnull
    private final Project project;

    @Override
    public PresentationModel createPresentationModel (final @Nonnull Object... instanceRoles) 
      {
        final List<Object> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(instanceRoles));
        temp.add(aggregateBuilder().create());
//          System.err.printf("\n\n***** ROLES %s\n\n\n", temp);
        return new DefaultPresentationModel("", temp.toArray());
      }
    
    @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder()
      {
        final AggregatePresentationModelBuilder builder = new AggregatePresentationModelBuilder();
        // FIXME: uses the column header names, should be an internal id instead
        builder.add("Client",     (Displayable) () -> project.getCustomer().getName());
        builder.add("Status",     (Displayable) () -> project.getStatus().name());
        builder.add("#",          (Displayable) () -> project.getNumber());
        builder.add("Name",       (Displayable) () -> project.getName());
        builder.add("Start Date", (Displayable) () -> DTF.format(project.getStartDate()),
                                  new DefaultStyleable("right-aligned"));
        builder.add("Due Date",   (Displayable) () -> DTF.format(project.getEndDate()),
                                  new DefaultStyleable("right-aligned"));
        builder.add("Estimate",   (Displayable) () -> MF.format(project.getAmount()),
                                  new DefaultStyleable("right-aligned"));
        builder.add("Notes",      (Displayable) () -> project.getNotes());
        
        // FIXME: this is dynamically computed, can be slow - should be also cached
        builder.add("Earnings",   (Displayable) () -> MF.format(project.getEarnings()),
                                  new DefaultStyleable("right-aligned"));
        builder.add("Time",       (Displayable) () -> DF.format(project.getDuration()),
                                  new DefaultStyleable("right-aligned"));

        return builder;
      }
  }
