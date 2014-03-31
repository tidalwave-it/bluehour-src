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
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.SimpleComposite;
import it.tidalwave.role.spi.ArrayListSimpleComposite;
import it.tidalwave.role.spi.MapAggregate;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.Selectable;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import it.tidalwave.role.ui.spi.PresentationModelCollectors;
import it.tidalwave.role.ui.spi.SimpleCompositePresentable;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import it.tidalwave.accounting.commons.ProjectSelectedEvent;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.ui.jobeventexplorer.JobEventExplorerPresentation;
import it.tidalwave.accounting.ui.jobeventexplorer.JobEventExplorerPresentationControl;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.role.ui.spi.PresentationModelCollectors.toContainerPresentationModel;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultJobEventExplorerPresentationControl implements JobEventExplorerPresentationControl
  {
    @Inject @Named("applicationMessageBus") @Nonnull
    private MessageBus messageBus;

    @Inject @Nonnull
    private JobEventExplorerPresentation presentation;

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
     * 
     *
     ******************************************************************************************************************/
    @VisibleForTesting void onProjectSelectedEvent (final @Nonnull @ListensTo ProjectSelectedEvent event)
      {
        log.info("onProjectSelectedEvent({})", event);
        presentation.populate(event.getProject().findChildren()
//                .sorted(comparing(JobEvent::getDate))
                .map(jobEvent -> createPresentationModelFor(jobEvent))
                .collect(toContainerPresentationModel()));
      }
    
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @VisibleForTesting PresentationModel createPresentationModelFor (final @Nonnull JobEvent jobEvent)
      {
        final Map<String, PresentationModel> map = new HashMap<>();
        final Selectable selectable = new Selectable() 
          {
            @Override
            public void select() 
              {
//                messageBus.publish(new ProjectSelectedEvent(jobEvent));
              }
          };
        
        // FIXME: uses the column header names, should be an internal id instead
//        map.put("Date", new DefaultPresentationModel(new Displayable() 
//          {
//            @Override
//            public String getDisplayName() 
//              {
//                return jobEvent.get;
//              }
//          }));
        map.put("Job Event", new DefaultPresentationModel(new Displayable() 
          {
            @Override
            public String getDisplayName() 
              {
                return jobEvent.getName();
              }
          }));
//        map.put("Time", new DefaultPresentationModel(new Displayable() 
//          {
//            @Override
//            public String getDisplayName() 
//              {
//                return jobEvent.getNumber();
//              }
//          }));
//        map.put("Rate", new DefaultPresentationModel(new Displayable() 
//          {
//            @Override
//            public String getDisplayName() 
//              {
//                return jobEvent.getName();
//              }
//          }));
//        map.put("Amount", new DefaultPresentationModel(new Displayable() 
//          {
//            @Override
//            public String getDisplayName() 
//              {
//                return jobEvent.getStartDate().toString();
//              }
//          }));
        map.put("Notes", new DefaultPresentationModel(new Displayable() 
          {
            @Override
            public String getDisplayName() 
              {
                return jobEvent.getDescription();
              }
          }));
        
//        if (jobEvent instanceof JobEventGroup) // FIXME
//          {
//            return ((JobEventGroup)jobEvent).findChildren()
//                    .map(e -> createPresentationModelFor(e))
//                    .collect(PresentationModelCollectors.toContainerPresentationModel(selectable, new MapAggregate<>(map)));
//            // FIXME: use SimpleCompositePresentable?
//          }
        
//        map.put("name", new DefaultPresentationModel((Displayable)() -> project.getName()));
//        map.put("customer", new DefaultPresentationModel((Displayable)() -> project.getCustomer().getName()));
        // FIXME: passing null as the owner fails in RoleManagerSupport:341
        return new DefaultPresentationModel("", selectable, new MapAggregate<>(map));
      }
  }
