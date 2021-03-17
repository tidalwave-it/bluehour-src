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
 * $Id$
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import it.tidalwave.accounting.commons.ProjectSelectedEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import it.tidalwave.accounting.ui.jobeventexplorer.JobEventExplorerPresentation;
import it.tidalwave.accounting.ui.jobeventexplorer.JobEventExplorerPresentationControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.Comparator.comparing;
import static it.tidalwave.role.ui.Presentable.Presentable;
import static it.tidalwave.role.ui.spi.PresentationModelCollectors.toCompositePresentationModel;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor @DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultJobEventExplorerPresentationControl implements JobEventExplorerPresentationControl
  {
    @Nonnull
    private final MessageBus messageBus;

    @Nonnull
    private final JobEventExplorerPresentation presentation;

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @Override
    public void initialize()
      {
        log.info("initialize()");  
      }
    
    /*******************************************************************************************************************
     *
     * Reacts to the notification that a {@link Project} has been selected by populating the presentation with
     * its job events.
     * 
     * @param  event  the notification event
     *
     ******************************************************************************************************************/
    @VisibleForTesting void onProjectSelectedEvent (final @Nonnull @ListensTo ProjectSelectedEvent event)
      {
        log.info("onProjectSelectedEvent({})", event);
        presentation.populate(event.getProject().findChildren()
                                                .stream()
                                                .map(jobEvent -> (JobEventSpi)jobEvent)
                                                .sorted(comparing(JobEventSpi::getDateTime))
                                                .map(jobEvent -> jobEvent.as(Presentable).createPresentationModel())
                                                .collect(toCompositePresentationModel()));
      }
  }
