/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import java.util.List;
import it.tidalwave.util.Finder;
import it.tidalwave.util.spi.SimpleFinderSupport;
import it.tidalwave.role.SimpleComposite;
import javax.annotation.concurrent.Immutable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode(callSuper = true) @ToString(exclude = { "events" }, callSuper = true)
public class JobEventGroup extends AbstractJobEvent implements SimpleComposite<AbstractJobEvent>
  {
    @Nonnull
    private final List<AbstractJobEvent> events; // FIXME: immutable

    /*******************************************************************************************************************
     *
     * 
     * 
     ******************************************************************************************************************/
    protected JobEventGroup (final @Nonnull Builder builder)
      {
        super(builder);
        this.events = builder.getEvents();
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public Finder<AbstractJobEvent> findChildren()
      {
        return new SimpleFinderSupport<AbstractJobEvent>()
          {
            @Override
            protected List<? extends AbstractJobEvent> computeResults()
              {
                return events;
              }
          };
      }
  }
