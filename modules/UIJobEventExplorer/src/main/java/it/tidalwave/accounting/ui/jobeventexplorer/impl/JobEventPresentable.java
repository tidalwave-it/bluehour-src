/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2015 Tidalwave s.a.s. (http://tidalwave.it)
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

import javax.annotation.Nonnull;
import java.util.Collection;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.ui.Presentable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.Styleable;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import it.tidalwave.role.ui.spi.DefaultStyleable;
import it.tidalwave.accounting.util.AggregatePresentationModelBuilder;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public abstract class JobEventPresentable<TYPE extends JobEventSpi> implements Presentable
  {
    protected static final String JOB_EVENT = "Job Event";
    protected static final String NOTES = "Notes";
    protected static final String AMOUNT = "Amount";
    protected static final String HOURLY_RATE = "HourlyRate";
    protected static final String TIME = "Time";
    protected static final String DATE = "Date";

    protected static final DefaultStyleable STYLE_RIGHT_ALIGNED = new DefaultStyleable("right-aligned");
        
    @Nonnull
    protected final TYPE jobEvent;

    @Override
    public PresentationModel createPresentationModel (final @Nonnull Object... instanceRoles) 
      {
        final Styleable styleable = new DefaultStyleable(getStyles());
        return new DefaultPresentationModel("", aggregateBuilder().create(), styleable);
      }
    
    @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder()
      {
        final AggregatePresentationModelBuilder builder = new AggregatePresentationModelBuilder();
        // FIXME: uses the column header names, should be an internal id instead
        builder.put(JOB_EVENT, (Displayable) () -> jobEvent.getName());
        builder.put(NOTES,     (Displayable) () -> jobEvent.getDescription());
        
        // FIXME: this is dynamically computed, can be slow - should be also cached
        builder.put(AMOUNT,    (Displayable) () -> MONEY_FORMATTER.format(jobEvent.getEarnings()),
                               STYLE_RIGHT_ALIGNED);

        return builder;
      }
    
    @Nonnull
    protected abstract Collection<String> getStyles();
  }
