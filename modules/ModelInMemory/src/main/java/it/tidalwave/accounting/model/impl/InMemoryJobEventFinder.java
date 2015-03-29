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
import java.util.ArrayList;
import java.util.List;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.model.ProjectRegistry;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class InMemoryJobEventFinder extends InMemoryJobEventFinderSupport
  {
    private static final long serialVersionUID = 1L;
    
    @Nonnull
    private final ProjectRegistry.ProjectFinder projectFinder;
    
    // FIXME: very inefficient
    @Override @Nonnull
    protected List<InMemoryJobEvent> findAll() 
      {
        final List<InMemoryJobEvent> result = new ArrayList<>();
            
        projectFinder.results().forEach(project -> 
          {
            project.findChildren().stream().map(jobEvent -> (InMemoryJobEvent)jobEvent).forEach(jobEvent -> 
              {
                result.add(jobEvent);

                // FIXME: should be recursive
                if (jobEvent instanceof JobEventGroup)
                  {
                    result.addAll((List<? extends InMemoryJobEvent>)((JobEventGroup)jobEvent).findChildren().results());  
                  }
              });
          });
            
        return result;
      }
  }
    