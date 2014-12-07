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
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import it.tidalwave.util.Id;
import it.tidalwave.util.NotFoundException;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import it.tidalwave.accounting.model.spi.util.FinderWithIdSupport;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public abstract class InMemoryJobEventFinderSupport extends FinderWithIdSupport<JobEvent, ProjectRegistry.JobEventFinder>
                                                    implements ProjectRegistry.JobEventFinder
  {
    private static final long serialVersionUID = 1L;
    
    @Override @Nonnull
    protected Optional<JobEvent> findById (final @Nonnull Id id) 
      {
        return ((Stream<JobEvent>)findAll().stream()).filter(item -> item.getId().equals(id)).findFirst();
        // FIXME: very inefficient
//        final Map<Id, JobEvent> map = stream.collect(Collectors.toMap(JobEvent::getId, item -> item));
//        final Map<Id, JobEvent> map = stream().collect(Collectors.toMap(JobEvent::getId, item -> item));
//        return Optional.ofNullable(map.get(id));
      }  

    @Override @Nonnull
    public Duration getDuration() 
      {
        return map(jobEvent -> ((JobEventSpi)jobEvent).getDuration()).reduce(Duration.ZERO, Duration::plus);
      }

    @Override @Nonnull
    public Money getEarnings() 
      {
        return map(jobEvent -> ((JobEventSpi)jobEvent).getEarnings()).reduce(Money.ZERO, Money::add);
      }
  }
