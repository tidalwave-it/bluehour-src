/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.List;
import it.tidalwave.util.Finder;
import it.tidalwave.util.FinderStream;
import it.tidalwave.util.FinderStreamSupport;
import it.tidalwave.role.SimpleComposite;
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
    public FinderStream<AbstractJobEvent> findChildren()
      {
        return new FinderStreamSupport<AbstractJobEvent, Finder<AbstractJobEvent>>()
          {
            @Override @Nonnull
            protected List<? extends AbstractJobEvent> computeResults()
              {
                return Collections.unmodifiableList(events);
              }
          };
      }
  }