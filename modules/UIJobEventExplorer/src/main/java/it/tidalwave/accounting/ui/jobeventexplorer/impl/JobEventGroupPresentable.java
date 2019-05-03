/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2019 Tidalwave s.a.s. (http://tidalwave.it)
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
import java.util.Arrays;
import java.util.Collection;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.ui.AggregatePresentationModelBuilder;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.Styleable;
import it.tidalwave.role.spi.DefaultDisplayable;
import it.tidalwave.role.ui.spi.DefaultStyleable;
import it.tidalwave.accounting.model.spi.JobEventGroupSpi;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import static java.util.Comparator.comparing;
import static it.tidalwave.role.ui.Presentable.Presentable;
import static it.tidalwave.role.ui.spi.PresentationModelCollectors.*;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = JobEventGroupSpi.class)
public class JobEventGroupPresentable extends JobEventPresentable<JobEventGroupSpi>
  {
    public JobEventGroupPresentable (final @Nonnull JobEventGroupSpi jobEventGroup)
      {
        super(jobEventGroup);
      }

    @Override @Nonnull
    public PresentationModel createPresentationModel (final @Nonnull Object... instanceRoles)
      {
        final Styleable styleable = new DefaultStyleable(getStyles());
        return jobEvent.findChildren()
                       .stream()
                       .map(jobEvent -> (JobEventSpi)jobEvent)
                       .sorted(comparing(JobEventSpi::getDateTime))
                       .map(jobEvent -> jobEvent.as(Presentable).createPresentationModel())
                       .collect(toCompositePresentationModel(aggregateBuilder().create(), styleable));
        // FIXME: use SimpleCompositePresentable?
      }

    @Override @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder()
      {
        return super.aggregateBuilder()
                .with(DATE,        (Displayable) () -> DATE_FORMATTER.format(jobEvent.getDateTime().toLocalDate()))
                .with(HOURLY_RATE, new DefaultDisplayable(""))
                .with(TIME,        (Displayable) () -> DURATION_FORMATTER.format(jobEvent.getDuration()),
                                 STYLE_RIGHT_ALIGNED);
      }

    @Override @Nonnull
    protected Collection<String> getStyles()
      {
        return Arrays.asList("job-event-group");
      }
  }
