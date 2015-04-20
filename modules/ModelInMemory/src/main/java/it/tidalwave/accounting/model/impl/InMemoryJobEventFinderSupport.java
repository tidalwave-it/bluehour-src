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
import java.util.Optional;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.util.FinderWithIdSupport;
import lombok.NoArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@NoArgsConstructor
public class InMemoryJobEventFinderSupport extends FinderWithIdSupport<JobEvent, InMemoryJobEvent, ProjectRegistry.JobEventFinder>
                                                    implements ProjectRegistry.JobEventFinder
  {
    private static final long serialVersionUID = 1L;

    public InMemoryJobEventFinderSupport (final @Nonnull InMemoryJobEventFinderSupport other,
                                          final @Nonnull Object override) 
      {
        super(other, override);
      } 
    
    @Override @Nonnull
    protected Optional<InMemoryJobEvent> findById (final @Nonnull Id id) 
      {
        return findAll().stream().filter(item -> item.getId().equals(id)).findFirst();
      }  

    @Override @Nonnull
    public Duration getDuration() 
      {
        return streamImpl().map(jobEvent -> jobEvent.getDuration()).reduce(Duration.ZERO, Duration::plus);
      }

    @Override @Nonnull
    public Money getEarnings() 
      {
        return streamImpl().map(jobEvent -> jobEvent.getEarnings()).reduce(Money.ZERO, Money::add);
      }
  }
