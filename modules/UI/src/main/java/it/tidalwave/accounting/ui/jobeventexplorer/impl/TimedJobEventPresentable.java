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
import it.tidalwave.role.Displayable;
import it.tidalwave.accounting.model.TimedJobEvent;
import java.time.LocalTime;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class TimedJobEventPresentable extends JobEventPresentable
  {
    @Nonnull
    private final TimedJobEvent timedJobEvent;
    
    public TimedJobEventPresentable (final @Nonnull TimedJobEvent timedJobEvent)
      {
        super(timedJobEvent);
        this.timedJobEvent = timedJobEvent;
      }
    
    @Override @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder() 
      {
        final AggregatePresentationModelBuilder builder = super.aggregateBuilder();
        
        builder.add("Date",   (Displayable) () -> timedJobEvent.getStartDateTime().toLocalDate().toString());
        builder.add("Time",   (Displayable) () -> computeDuration());
        builder.add("Rate",   (Displayable) () -> timedJobEvent.getRate().toString());
        builder.add("Amount", (Displayable) () -> timedJobEvent.getEarnings().toString());
        
        return builder;
      }

    @Nonnull
    private String computeDuration() 
      {
        final Duration duration = Duration.between(timedJobEvent.getStartDateTime(), timedJobEvent.getEndDateTime());
        return "" + duration;
      }
  }
