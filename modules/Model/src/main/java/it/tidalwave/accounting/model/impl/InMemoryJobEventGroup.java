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
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import it.tidalwave.util.Finder;
import it.tidalwave.util.FinderStream;
import it.tidalwave.util.FinderStreamSupport;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Money;
import it.tidalwave.accounting.model.spi.JobEventGroupSpi;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode(callSuper = true) @ToString(exclude = { "events" }, callSuper = true)
public class InMemoryJobEventGroup extends InMemoryJobEvent implements JobEventGroupSpi
  {
    @Nonnull
    private final List<JobEvent> events; // FIXME: immutable

    /*******************************************************************************************************************
     *
     * 
     * 
     ******************************************************************************************************************/
    public /* FIXME protected */ InMemoryJobEventGroup (final @Nonnull Builder builder)
      {
        super(builder);
        this.events = builder.getEvents();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public FinderStream<JobEvent> findChildren()
      {
        return new FinderStreamSupport<JobEvent, Finder<JobEvent>>()
          {
            @Override @Nonnull
            protected List<? extends JobEvent> computeResults()
              {
                return Collections.unmodifiableList(events);
              }
          };
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public JobEvent.Builder asBuilder()
      {
        return new Builder(id, null, null, null, name, 
                           description, null, null, new ArrayList<>(findChildren().results()));
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public LocalDateTime getDateTime()
      {
//        return findChildren().sorted(comparing(JobEvent::getDateTime)).findFirst().get().getDateTime();  
        final BinaryOperator<LocalDateTime> min = (a, b) -> (a.compareTo(b) > 0) ? b : a;
        return findChildren().map(jobEvent -> jobEvent.getDateTime()).reduce(min).get();
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public Money getEarnings()
      {
        return findChildren().map(jobEvent -> ((JobEventSpi)jobEvent).getEarnings()).reduce(Money.ZERO, Money::add);
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public Duration getDuration() 
      {
        return findChildren().map(jobEvent -> ((JobEventSpi)jobEvent).getDuration()).reduce(Duration.ZERO, Duration::plus);
      }
  }