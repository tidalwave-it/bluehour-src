/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.spi.PresentationModelCollectors;
import it.tidalwave.role.spi.DefaultDisplayable;
import it.tidalwave.accounting.model.JobEventGroup;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public class JobEventGroupPresentable extends JobEventPresentable
  {
    @Nonnull
    private final JobEventGroup jobEventGroup;
    
    public JobEventGroupPresentable (final @Nonnull JobEventGroup jobEventGroup)
      {
        super(jobEventGroup);
        this.jobEventGroup = jobEventGroup;
      }
    
    @Override @Nonnull
    public PresentationModel createPresentationModel (final @Nonnull Object... instanceRoles) 
      {
        return jobEventGroup.findChildren()
                .map(e -> PMFactory.createPresentationModelFor(e))
                .collect(PresentationModelCollectors.toContainerPresentationModel(aggregateBuilder().create()));
        // FIXME: use SimpleCompositePresentable?
      }

    @Override @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder() 
      {
        final AggregatePresentationModelBuilder builder = super.aggregateBuilder();
        builder.add("Rate",   new DefaultDisplayable(""));
        builder.add("Time",   new DefaultDisplayable("")); // FIXME: compute sum
        builder.add("Amount", new DefaultDisplayable("")); // FIXME: compute sum
        
        return builder;
      }
  }
