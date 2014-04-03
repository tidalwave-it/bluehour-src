/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.model.spi;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import it.tidalwave.accounting.model.FlatJobEvent;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface FlatJobEventSpi extends FlatJobEvent, JobEventSpi
  {
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public LocalDate getDate(); 
  }
