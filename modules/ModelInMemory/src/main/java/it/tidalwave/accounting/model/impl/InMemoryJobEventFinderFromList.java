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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class InMemoryJobEventFinderFromList extends InMemoryJobEventFinderSupport
  {
    private static final long serialVersionUID = 1L;
    
    @Nonnull
    private final List<? extends InMemoryJobEvent> events;
    
    public InMemoryJobEventFinderFromList()
      {
        events = Collections.emptyList();
      }
     
    public InMemoryJobEventFinderFromList (final @Nonnull InMemoryJobEventFinderFromList other,
                                           final @Nonnull Object override) 
      {
        super(other, override);
        this.events = new ArrayList<>(other.events);
      } 
    
    @Override @Nonnull
    protected List<InMemoryJobEvent> findAll() 
      {
        return new CopyOnWriteArrayList<>(events);
      }
  }
