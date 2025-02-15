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
package it.tidalwave.accounting.model.impl;

import jakarta.annotation.Nonnull;
// import javax.annotation.concurrent.Immutable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.spi.FlatJobEventSpi;
import it.tidalwave.accounting.model.types.Money;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
/* @Immutable */ @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class InMemoryFlatJobEvent extends InMemoryJobEvent implements FlatJobEventSpi
  {
    @Getter @Nonnull
    private final LocalDate date;

    @Getter @Nonnull
    private final Money earnings;

    /***********************************************************************************************************************************************************
     * 
     **********************************************************************************************************************************************************/
    public /* FIXME protected */ InMemoryFlatJobEvent (@Nonnull final Builder builder)
      {
        super(builder);
        this.date = builder.getStartDateTime().toLocalDate();
        this.earnings = builder.getEarnings();
      }
    
    /***********************************************************************************************************************************************************
     * {@inheritDoc} 
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public JobEvent.Builder toBuilder()
      {
        return new Builder(id, Type.FLAT, date.atStartOfDay(), null,
                           name, description, earnings, null, Collections.emptyList());
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc} 
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public LocalDateTime getDateTime()
      {
        return date.atStartOfDay();
      }

    /***********************************************************************************************************************************************************
     * {@inheritDoc} 
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public Duration getDuration() 
      {
        return Duration.ZERO;
      }
  }
