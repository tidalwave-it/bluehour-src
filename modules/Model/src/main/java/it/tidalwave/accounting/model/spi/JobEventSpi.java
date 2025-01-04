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
package it.tidalwave.accounting.model.spi;

import jakarta.annotation.Nonnull;
import java.time.Duration;
import java.time.LocalDateTime;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.types.Money;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public interface JobEventSpi extends JobEvent
  {
    /***********************************************************************************************************************************************************
     * Returns the name of this event.
     *
     * @return  the name
     **********************************************************************************************************************************************************/
    @Nonnull
    public String getName();

    /***********************************************************************************************************************************************************
     * Returns the description of this event.
     *
     * @return  the description
     **********************************************************************************************************************************************************/
    @Nonnull
    public String getDescription();

    /***********************************************************************************************************************************************************
     * Returns the duration of this event.
     *
     * @return  the duration
     **********************************************************************************************************************************************************/
    @Nonnull
    public Duration getDuration();

    /***********************************************************************************************************************************************************
     * Returns the earnings of this event.
     *
     * @return  the earnings
     **********************************************************************************************************************************************************/
    @Nonnull
    public Money getEarnings();

    /***********************************************************************************************************************************************************
     * Returns the datetime of this event.
     *
     * @return  the datetime
     **********************************************************************************************************************************************************/
    @Nonnull
    public LocalDateTime getDateTime();
  }
