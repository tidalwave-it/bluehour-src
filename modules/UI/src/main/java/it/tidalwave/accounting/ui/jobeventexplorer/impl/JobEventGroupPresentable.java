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
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.spi.DefaultDisplayable;
import it.tidalwave.role.spi.MapAggregate;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import java.util.Map;

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
                .collect(PresentationModelCollectors.toContainerPresentationModel(new MapAggregate<>(createMap())));
        // FIXME: use SimpleCompositePresentable?
      }

    @Override @Nonnull
    protected Map<String, PresentationModel> createMap() 
      {
        final Map<String, PresentationModel> map = super.createMap();
        map.put("Rate", new DefaultPresentationModel(new DefaultDisplayable("")));
        map.put("Time", new DefaultPresentationModel(new DefaultDisplayable(""))); // FIXME: compute sum
        map.put("Amount", new DefaultPresentationModel(new DefaultDisplayable(""))); // FIXME: compute sum
        
        return map;
      }
  }
