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
package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import java.util.Collection;
import it.tidalwave.role.ui.AggregatePresentationModelBuilder;
import it.tidalwave.role.ui.Displayable;
import it.tidalwave.role.ui.Displayable2;
import it.tidalwave.role.ui.Presentable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.Styleable;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.commons.Styleables.RIGHT_ALIGNED;
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

    @Nonnull
    protected final TYPE jobEvent;

    @Override
    public PresentationModel createPresentationModel (final @Nonnull Object... instanceRoles)
      {
        // FIXME: doesn't concat instanceRoles
        return PresentationModel.of("", aggregateBuilder().create(), Styleable.of(getStyles()));
      }

    @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder()
      {
        // FIXME: uses the column header names, should be an internal id instead
        return AggregatePresentationModelBuilder.newInstance()
                .with(JOB_EVENT, Displayable.of(jobEvent.getName()))
                .with(NOTES,     Displayable.of(jobEvent.getDescription()))
        // FIXME: this is dynamically computed, can be slow - should be also cached
                .with(AMOUNT, Displayable2.of(MONEY_FORMATTER, jobEvent.getEarnings()), RIGHT_ALIGNED);
      }

    @Nonnull
    protected abstract Collection<String> getStyles();
  }
