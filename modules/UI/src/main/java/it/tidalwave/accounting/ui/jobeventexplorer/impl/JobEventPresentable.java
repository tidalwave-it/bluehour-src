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
package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import it.tidalwave.accounting.commons.DurationFormat;
import it.tidalwave.accounting.commons.MoneyFormat;
import javax.annotation.Nonnull;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.ui.Presentable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.role.spi.DefaultStyleable;
import it.tidalwave.role.ui.Styleable;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class JobEventPresentable implements Presentable
  {
    protected static final DateTimeFormatter DTF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    protected static final DateTimeFormatter DTTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    protected static final MoneyFormat MF = new MoneyFormat();
    protected static final DurationFormat DF = new DurationFormat();
    
    @Nonnull
    private final JobEvent jobEvent;

    @Override
    public PresentationModel createPresentationModel (final @Nonnull Object... instanceRoles) 
      {
        return new DefaultPresentationModel("", aggregateBuilder().create());
      }
    
    @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder()
      {
        final AggregatePresentationModelBuilder builder = new AggregatePresentationModelBuilder();
        // FIXME: uses the column header names, should be an internal id instead
        builder.add("Job Event", (Displayable) () -> jobEvent.getName());
        builder.add("Notes",     (Displayable) () -> jobEvent.getDescription());
        
        // FIXME: this is dynamically computed, can be slow - should be also cached
        builder.add("Amount",    (Displayable) () -> MF.format(jobEvent.getEarnings()));

        return builder;
      }
  }
