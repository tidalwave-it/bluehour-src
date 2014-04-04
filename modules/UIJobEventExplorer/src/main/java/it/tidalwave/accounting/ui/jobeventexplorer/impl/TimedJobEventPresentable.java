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

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.ui.spi.DefaultStyleable;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.accounting.util.AggregatePresentationModelBuilder;
import it.tidalwave.accounting.model.spi.TimedJobEventSpi;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = TimedJobEventSpi.class)
public class TimedJobEventPresentable extends JobEventPresentable<TimedJobEventSpi>
  {
    public TimedJobEventPresentable (final @Nonnull TimedJobEventSpi timedJobEvent)
      {
        super(timedJobEvent);
      }
    
    @Override @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder() 
      {
        final AggregatePresentationModelBuilder builder = super.aggregateBuilder();
        builder.put(DATE,        (Displayable) () -> DATETIME_FORMATTER.format(jobEvent.getStartDateTime()));
        builder.put(TIME,        (Displayable) () -> DURATION_FORMATTER.format(computeDuration()),
                                 new DefaultStyleable("right-aligned"));
        builder.put(HOURLY_RATE, (Displayable) () -> MONEY_FORMATTER.format(jobEvent.getHourlyRate()),
                                 new DefaultStyleable("right-aligned"));
        return builder;
      }

    @Nonnull
    private Duration computeDuration() 
      {
        return Duration.between(jobEvent.getStartDateTime(), jobEvent.getEndDateTime());
      }

    @Override @Nonnull
    protected Collection<String> getStyles() 
      {
        return Arrays.asList("timed-job-event");
      }
  }
