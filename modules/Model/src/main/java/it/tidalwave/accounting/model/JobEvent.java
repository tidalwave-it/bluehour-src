/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import org.joda.time.DateTime;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class JobEvent extends AbstractJobEvent
  {
    @Nonnull
    private final DateTime startDateTime;

    @Nonnull
    private final DateTime endDateTime;

    @Nonnull
    private final Money earnings;

    @Nonnull
    private final Money rate;

    protected JobEvent (final @Nonnull Builder builder)
      {
        super(builder);
        this.startDateTime = builder.getStartDateTime();
        this.endDateTime = builder.getEndDateTime();
        this.earnings = builder.getEarnings();
        this.rate = builder.getRate();
      }
  }
