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
package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import java.util.Collection;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import it.tidalwave.role.ui.Displayable;
import it.tidalwave.role.ui.Presentable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.PresentationModelAggregate;
import it.tidalwave.role.ui.Styleable;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.commons.Styleables.RIGHT_ALIGNED;
import static it.tidalwave.accounting.model.spi.util.Formatters.MONEY_FORMATTER;
import static it.tidalwave.util.Parameters.r;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
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

    @Override @Nonnull
    public PresentationModel createPresentationModel (@Nonnull final Collection<Object> instanceRoles)
      {
        return PresentationModel.of(jobEvent, r(presentationModelAggregate(), Styleable.of(getStyles()), instanceRoles));
      }

    @Nonnull
    protected PresentationModelAggregate presentationModelAggregate()
      {
        // FIXME: uses the column header names, should be an internal id instead
        return PresentationModelAggregate.newInstance()
                .withPmOf(JOB_EVENT,  r(Displayable.of(jobEvent.getName())))
                .withPmOf(NOTES,      r(Displayable.of(jobEvent.getDescription())))
        // FIXME: this is dynamically computed, can be slow - should be also cached
                .withPmOf(AMOUNT,     r(Displayable.of(MONEY_FORMATTER::format, jobEvent.getEarnings()),
                                        RIGHT_ALIGNED));
      }

    @Nonnull
    protected abstract Collection<String> getStyles();
  }
