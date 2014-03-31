/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
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
        return new DefaultPresentationModel("", aggregateBuilder().create());
      }
    
    @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder()
      {
        final AggregatePresentationModelBuilder builder = new AggregatePresentationModelBuilder();
//        final Selectable selectable = new Selectable() 
//          {
//            @Override
//            public void select() 
//              {
////                messageBus.publish(new ProjectSelectedEvent(jobEvent));
//              }
//          };
        
        // FIXME: uses the column header names, should be an internal id instead
        builder.add("Job Event", (Displayable) () -> jobEvent.getName());
        builder.add("Notes",     (Displayable) () -> jobEvent.getDescription());

        return builder;
      }
  }
