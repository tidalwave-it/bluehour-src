/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.model.spi;

import javax.annotation.Nonnull;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.types.Money;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public interface InvoiceSpi extends Invoice
  {
    @Nonnull
    public Project getProject();
    
    @Nonnull
    public Money getEarnings();
    
    @Nonnull
    public Money getTax();
  }
