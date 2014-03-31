/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import java.time.Duration;
import it.tidalwave.role.Displayable;
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
    protected AggregatePresentationModelBuilder aggregateBuilder() 
      {
        final AggregatePresentationModelBuilder builder = super.aggregateBuilder();
        
        builder.add("Date",   (Displayable) () -> timedJobEvent.getStartDateTime().toLocalDate().toString());
        builder.add("Time",   (Displayable) () -> 
                "" + Duration.between(timedJobEvent.getStartDateTime(), 
                timedJobEvent.getEndDateTime()).toMinutes());
        builder.add("Rate",   (Displayable) () -> timedJobEvent.getRate().toString());
        builder.add("Amount", (Displayable) () -> timedJobEvent.getEarnings().toString());
        
        return builder;
      }
  }
