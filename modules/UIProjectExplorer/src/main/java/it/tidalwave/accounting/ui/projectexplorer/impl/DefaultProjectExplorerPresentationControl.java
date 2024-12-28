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
package it.tidalwave.accounting.ui.projectexplorer.impl;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import it.tidalwave.accounting.commons.CustomerSelectedEvent;
import it.tidalwave.accounting.commons.ProjectSelectedEvent;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import it.tidalwave.accounting.ui.projectexplorer.ProjectExplorerPresentation;
import it.tidalwave.util.annotation.VisibleForTesting;
import it.tidalwave.ui.core.role.PresentationModel;
import it.tidalwave.ui.core.role.Selectable;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.Comparator.*;
import static it.tidalwave.ui.core.role.Presentable._Presentable_;
import static it.tidalwave.ui.core.role.spi.PresentationModelCollectors.toCompositePresentationModel;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Component @RequiredArgsConstructor @DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultProjectExplorerPresentationControl
  {
    @Nonnull
    private final MessageBus messageBus;

    @Nonnull
    private final ProjectExplorerPresentation presentation;

    /***********************************************************************************************************************************************************
     * Reacts to the notification that a {@link Customer} has been selected by populating the presentation with
     * his projects.
     * 
     * @param  event  the notification event
     **********************************************************************************************************************************************************/
    @VisibleForTesting void onCustomerSelectedEvent (@Nonnull @ListensTo final CustomerSelectedEvent event)
      {
        log.info("onCustomerSelectedEvent({})", event);
        presentation.populate(event.getCustomer().findProjects()
                                                 .stream()
                                                 .map(project -> (ProjectSpi)project)
                                                 .sorted(comparing(ProjectSpi::getName))
                                                 .map(this::createPresentationModelFor)
                                                 .collect(toCompositePresentationModel()));
      }
    
    /***********************************************************************************************************************************************************
     * Creates a {@link PresentationModel} for a {@link Project} injecting a {@link Selectable} role which fires a
     * {@link ProjectSelectedEvent} on selection.
     * 
     * @param  project      the {@code Project}
     * @return              the {@code PresentationModel}
     **********************************************************************************************************************************************************/
    @Nonnull
    @VisibleForTesting PresentationModel createPresentationModelFor (@Nonnull final Project project)
      {
        final Selectable publishEventOnSelection = () -> messageBus.publish(new ProjectSelectedEvent(project));
        return project.as(_Presentable_).createPresentationModel(publishEventOnSelection);
      }
  }
