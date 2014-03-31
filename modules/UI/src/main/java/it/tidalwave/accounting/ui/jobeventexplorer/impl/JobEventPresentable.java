/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.spi.MapAggregate;
import it.tidalwave.role.ui.Presentable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import it.tidalwave.accounting.model.JobEvent;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class JobEventPresentable implements Presentable
  {
    @Nonnull
    private final JobEvent jobEvent;

    @Override
    public PresentationModel createPresentationModel (final @Nonnull Object... instanceRoles) 
      {
        return new DefaultPresentationModel("", new MapAggregate<>(createMap()));
      }
    
    @Nonnull
    protected Map<String, PresentationModel> createMap ()
      {
        final Map<String, PresentationModel> map = new HashMap<>();
//        final Selectable selectable = new Selectable() 
//          {
//            @Override
//            public void select() 
//              {
////                messageBus.publish(new ProjectSelectedEvent(jobEvent));
//              }
//          };
        
        // FIXME: uses the column header names, should be an internal id instead
        map.put("Job Event", new DefaultPresentationModel((Displayable) () -> jobEvent.getName()));
        map.put("Notes",     new DefaultPresentationModel((Displayable) () -> jobEvent.getDescription()));

        return map;
      }
  }
