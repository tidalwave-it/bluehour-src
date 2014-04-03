/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.model.spi;

import javax.annotation.Nonnull;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.util.FinderStream;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface JobEventGroupSpi extends JobEventGroup, JobEventSpi
  {
    @Override @Nonnull
    public FinderStream<JobEvent> findChildren();
  }
