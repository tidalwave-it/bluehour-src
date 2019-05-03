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
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.time.Duration;
import java.time.LocalDateTime;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEvent.Builder;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.TimedJobEventSpi;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class InMemoryTimedJobEvent extends InMemoryJobEvent implements TimedJobEventSpi
  {
    @Getter @Nonnull
    private final LocalDateTime startDateTime;

    @Getter @Nonnull
    private final LocalDateTime endDateTime;

    @Getter @Nonnull
    private final Money earnings;

    @Getter @Nonnull
    private final Money hourlyRate;

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    public /* FIXME protected */ InMemoryTimedJobEvent (final @Nonnull Builder builder)
      {
        super(builder);
        this.startDateTime = builder.getStartDateTime();
        this.endDateTime = builder.getEndDateTime();
        this.earnings = builder.getEarnings();
        this.hourlyRate = builder.getHourlyRate();
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public JobEvent.Builder toBuilder()
      {
        return new Builder(id, Type.TIMED, startDateTime, endDateTime, name, description, 
                           earnings, hourlyRate, Collections.<JobEvent>emptyList());
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public LocalDateTime getDateTime()
      {
        return startDateTime;
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public Duration getDuration() 
      {
        return Duration.between(startDateTime, endDateTime);
      }
  }
