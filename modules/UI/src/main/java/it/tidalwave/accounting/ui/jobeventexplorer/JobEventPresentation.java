/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.ui.jobeventexplorer;

import it.tidalwave.role.ui.PresentationModel;
import javax.annotation.Nonnull;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface JobEventPresentation 
  {
    public void populate (@Nonnull PresentationModel pm);
  }
