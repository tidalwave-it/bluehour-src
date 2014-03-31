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
package it.tidalwave.accounting.ui.projectexplorer.impl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.spi.MapAggregate;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.Selectable;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import it.tidalwave.accounting.commons.CustomerSelectedEvent;
import it.tidalwave.accounting.commons.ProjectSelectedEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.ui.projectexplorer.ProjectExplorerPresentation;
import it.tidalwave.accounting.ui.projectexplorer.ProjectExplorerPresentationControl;
import lombok.extern.slf4j.Slf4j;
import static java.util.Comparator.comparing;
import static it.tidalwave.role.ui.spi.PresentationModelCollectors.toContainerPresentationModel;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultProjectExplorerPresentationControl implements ProjectExplorerPresentationControl
  {
    @Inject @Named("applicationMessageBus") @Nonnull
    private MessageBus messageBus;

    @Inject @Nonnull
    private ProjectExplorerPresentation presentation;

    @Override
    public void initialize() 
      {
      }

    @VisibleForTesting void onCustomerSelectedEvent (final @Nonnull @ListensTo CustomerSelectedEvent event)
      {
        log.info("onCustomerSelectedEvent({})", event);
        presentation.populate(event.getCustomer().findProjects()
                .sorted(comparing(Project::getName))
                .map(project -> createPresentationModelFor(project))
                .collect(toContainerPresentationModel()));
      }
    
    @VisibleForTesting PresentationModel createPresentationModelFor (final @Nonnull Project project)
      {
        final Map<String, PresentationModel> map = new HashMap<>();
        final Selectable selectable = () -> messageBus.publish(new ProjectSelectedEvent(project));
        
        // FIXME: uses the column header names, should be an internal id instead
        map.put("Client",     new DefaultPresentationModel((Displayable) () -> project.getCustomer().getName()));
        map.put("Status",     new DefaultPresentationModel((Displayable) () -> "?Status?"));
        map.put("#",          new DefaultPresentationModel((Displayable) () -> project.getNumber()));
        map.put("Name",       new DefaultPresentationModel((Displayable) () -> project.getName()));
        map.put("Start Date", new DefaultPresentationModel((Displayable) () -> project.getStartDate().toString()));
        map.put("Due Date",   new DefaultPresentationModel((Displayable) () -> project.getEndDate().toString()));
        map.put("Time",       new DefaultPresentationModel((Displayable) () -> "?Time?"));
        map.put("Earnings",   new DefaultPresentationModel((Displayable) () -> "?Earnings?"));
        map.put("Estimate",   new DefaultPresentationModel((Displayable) () -> project.getAmount().toString()));
        map.put("Notes",      new DefaultPresentationModel((Displayable) () -> project.getNotes()));
        
//        map.put("name", new DefaultPresentationModel((Displayable)() -> project.getName()));
//        map.put("customer", new DefaultPresentationModel((Displayable)() -> project.getCustomer().getName()));
        return new DefaultPresentationModel(project, selectable, new MapAggregate<>(map));
      }
  }
