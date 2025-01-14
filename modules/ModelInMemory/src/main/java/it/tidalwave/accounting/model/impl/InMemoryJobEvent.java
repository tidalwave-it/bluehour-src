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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.Duration;
import java.time.LocalDateTime;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.As;
import it.tidalwave.util.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;

/***************************************************************************************************************************************************************
 *
 * This class models a single job event.
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Immutable @EqualsAndHashCode @ToString(exclude = "as")
public abstract class InMemoryJobEvent implements JobEvent
  {
    @Delegate
    private final As as = As.forObject(this);

    @Getter @Nonnull
    protected final Id id;
    
    @Getter @Nonnull
    protected final String name;

    @Getter @Nonnull
    protected final String description;

    /***********************************************************************************************************************************************************
     * Creates a new builder.
     *
     * @return  the builder
     **********************************************************************************************************************************************************/
    @Nonnull
    public static JobEvent.Builder builder()
      {
        return new JobEvent.Builder(); // FIXME: avoid nulls
      }

    /***********************************************************************************************************************************************************
     * Creates a new instance from a builder.
     *
     * @param builder   the builder
     **********************************************************************************************************************************************************/
    protected InMemoryJobEvent (@Nonnull final Builder builder)
      {
        this.id = builder.getId();
        this.name = builder.getName();
        this.description = builder.getDescription();
      }
    
    /***********************************************************************************************************************************************************
     * Returns the datetime of the event.
     *
     * @return    the date
     **********************************************************************************************************************************************************/
    @Nonnull
    public abstract LocalDateTime getDateTime(); 
    
    /***********************************************************************************************************************************************************
     * Returns the duration of the event.
     *
     * @return    the duration
     **********************************************************************************************************************************************************/
    @Nonnull
    public abstract Duration getDuration(); 
    
    /***********************************************************************************************************************************************************
     * Returns the earnings for the event.
     *
     * @return    the earnings
     **********************************************************************************************************************************************************/
    @Nonnull
    public abstract Money getEarnings();
    
    /***********************************************************************************************************************************************************
     * Returns a builder pre-populated with all the attributes.
     *
     * @return  the builder
     **********************************************************************************************************************************************************/
    @Nonnull
    public abstract JobEvent.Builder toBuilder();
  }
