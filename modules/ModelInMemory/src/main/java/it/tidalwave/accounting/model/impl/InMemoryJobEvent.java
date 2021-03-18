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
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.Duration;
import java.time.LocalDateTime;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.AsSupport;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.types.Money;
import lombok.experimental.Delegate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***********************************************************************************************************************
 *
 * This class models a single job event.
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode @ToString(exclude = {"asSupport"})
public abstract class InMemoryJobEvent implements JobEvent
  {
    @Delegate
    private final AsSupport asSupport = new AsSupport(this);

    @Getter @Nonnull
    protected final Id id;
    
    @Getter @Nonnull
    protected final String name;

    @Getter @Nonnull
    protected final String description;

    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public static JobEvent.Builder builder()
      {
        return new JobEvent.Builder(); // FIXME: avoid nulls
      }

    /*******************************************************************************************************************
     *
     * @param builder
     * 
     ******************************************************************************************************************/
    protected InMemoryJobEvent (final @Nonnull Builder builder)
      {
        this.id = builder.getId();
        this.name = builder.getName();
        this.description = builder.getDescription();
      }
    
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public abstract LocalDateTime getDateTime(); 
    
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public abstract Duration getDuration(); 
    
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public abstract Money getEarnings();
    
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public abstract JobEvent.Builder toBuilder();
  }
