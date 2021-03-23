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
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.JobEventGroupSpi;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode(callSuper = true) @ToString(exclude = { "events" }, callSuper = true)
public class InMemoryJobEventGroup extends InMemoryJobEvent implements JobEventGroupSpi
  {
    @Nonnull
    private final List<InMemoryJobEvent> events; // FIXME: immutable

    /*******************************************************************************************************************
     *
     * 
     * 
     ******************************************************************************************************************/
    public /* FIXME protected */ InMemoryJobEventGroup (@Nonnull final Builder builder)
      {
        super(builder);
        this.events = (List<InMemoryJobEvent>)builder.getEvents();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public JobEvent.Builder toBuilder()
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
        return findChildren().stream().map(jobEvent -> (JobEventSpi)jobEvent)
                                      .map(JobEventSpi::getDateTime)
                                      .reduce(min).get();
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public Money getEarnings()
      {
        return findChildren().getEarnings();
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public Duration getDuration() 
      {
        return findChildren().getDuration();
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public ProjectRegistry.JobEventFinder findChildren()
      {
        return new InMemoryJobEventFinderFromList(events);  
      }
  }  