/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Map;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import it.tidalwave.accounting.model.TimedJobEvent;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public class TimedJobEventPresentable extends JobEventPresentable
  {
    @Nonnull
    private final TimedJobEvent timedJobEvent;
    
    public TimedJobEventPresentable (final @Nonnull TimedJobEvent timedJobEvent)
      {
        super(timedJobEvent);
        this.timedJobEvent = timedJobEvent;
      }
    
    @Override @Nonnull
    protected Map<String, PresentationModel> createMap() 
      {
        final Map<String, PresentationModel> map = super.createMap();
        
        map.put("Date", new DefaultPresentationModel((Displayable) () -> timedJobEvent.getStartDateTime().toLocalDate().toString()));
        map.put("Time", new DefaultPresentationModel((Displayable) () -> 
                "" + Duration.between(timedJobEvent.getStartDateTime(), 
                timedJobEvent.getEndDateTime()).toMinutes()));
        map.put("Rate", new DefaultPresentationModel((Displayable) () -> timedJobEvent.getRate().toString()));
        map.put("Amount", new DefaultPresentationModel((Displayable) () -> timedJobEvent.getEarnings().toString()));
        
        return map;
      }
  }
