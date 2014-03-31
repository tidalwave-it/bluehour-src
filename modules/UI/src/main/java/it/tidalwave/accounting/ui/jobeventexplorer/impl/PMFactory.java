/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.model.TimedJobEvent;
import it.tidalwave.role.ui.PresentationModel;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
// FIXME: replace with as()
public class PMFactory 
  {
    @VisibleForTesting static PresentationModel createPresentationModelFor (final @Nonnull JobEvent jobEvent)
      {
        if (jobEvent instanceof JobEventGroup)
          {
            return new JobEventGroupPresentable((JobEventGroup)jobEvent).createPresentationModel();
          }
        
        if (jobEvent instanceof TimedJobEvent)
          {
            return new TimedJobEventPresentable((TimedJobEvent)jobEvent).createPresentationModel();
          }
        
        return new JobEventPresentable(jobEvent).createPresentationModel();
      }
  }
