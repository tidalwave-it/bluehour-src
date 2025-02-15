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

import jakarta.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import it.tidalwave.accounting.model.spi.JobEventGroupSpi;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import it.tidalwave.ui.core.role.Displayable;
import it.tidalwave.ui.core.role.PresentationModel;
import it.tidalwave.ui.core.role.PresentationModelAggregate;
import it.tidalwave.ui.core.role.Styleable;
import it.tidalwave.dci.annotation.DciRole;
import static it.tidalwave.accounting.commons.Styleables.RIGHT_ALIGNED;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;
import static java.util.Comparator.*;
import static it.tidalwave.util.Parameters.r;
import static it.tidalwave.ui.core.role.spi.PresentationModelCollectors.toCompositePresentationModel;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@DciRole(datumType = JobEventGroupSpi.class)
public class JobEventGroupPresentable extends JobEventPresentable<JobEventGroupSpi>
  {
    public JobEventGroupPresentable (@Nonnull final JobEventGroupSpi jobEventGroup)
      {
        super(jobEventGroup);
      }

    @Override @Nonnull
    public PresentationModel createPresentationModel (@Nonnull final Collection<Object> instanceRoles)
      {
        return jobEvent.findChildren()
                       .stream()
                       .map(jobEvent -> (JobEventSpi)jobEvent)
                       .sorted(comparing(JobEventSpi::getDateTime))
                       .map(jobEvent -> jobEvent.as(_Presentable_).createPresentationModel())
                       .collect(toCompositePresentationModel(r(presentationModelAggregate(), Styleable.of(getStyles()))));
        // FIXME: use SimpleCompositePresentable?
      }

    @Override @Nonnull
    protected PresentationModelAggregate presentationModelAggregate()
      {
        return super.presentationModelAggregate()
                .withPmOf(DATE,        r(Displayable.of(DATE_FORMATTER::format, jobEvent.getDateTime().toLocalDate())))
                .withPmOf(HOURLY_RATE, r(Displayable.of("")))
                .withPmOf(TIME,        r(Displayable.of(DURATION_FORMATTER::format, jobEvent.getDuration()), RIGHT_ALIGNED));
      }

    @Override @Nonnull
    protected Collection<String> getStyles()
      {
        return List.of("job-event-group");
      }
  }
