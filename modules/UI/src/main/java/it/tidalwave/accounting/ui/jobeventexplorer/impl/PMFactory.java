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
package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.model.TimedJobEvent;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
// FIXME: replace with as()
public class PMFactory 
  {
    @VisibleForTesting static PresentationModel createPresentationModelFor (final @Nonnull JobEvent jobEvent)
      {
        if (jobEvent instanceof JobEventGroup)
          {
            return new JobEventGroupPresentable((JobEventGroup)jobEvent).createPresentationModel();
          }
        
        if (jobEvent instanceof TimedJobEvent)
          {
            return new TimedJobEventPresentable((TimedJobEvent)jobEvent).createPresentationModel();
          }
        
        return new JobEventPresentable(jobEvent).createPresentationModel();
      }
  }
