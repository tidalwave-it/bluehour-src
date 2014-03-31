/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import java.util.Map;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.accounting.model.TimedJobEvent;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import java.time.Duration;

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
        
        map.put("Date", new DefaultPresentationModel(new Displayable() 
          {
            @Override
            public String getDisplayName() 
              {
                return timedJobEvent.getStartDateTime().toLocalDate().toString(); // FIXME
              }
          }));
        map.put("Time", new DefaultPresentationModel(new Displayable() 
          {
            @Override
            public String getDisplayName() 
              {
                return "" + Duration.between(timedJobEvent.getStartDateTime(), 
                                             timedJobEvent.getEndDateTime()).toMinutes();
              }
          }));
        map.put("Rate", new DefaultPresentationModel(new Displayable() 
          {
            @Override
            public String getDisplayName() 
              {
                return timedJobEvent.getRate().toString();
              }
          }));
        map.put("Amount", new DefaultPresentationModel(new Displayable() 
          {
            @Override
            public String getDisplayName() 
              {
                return timedJobEvent.getEarnings().toString();
              }
          }));
        
        return map;
      }
  }
