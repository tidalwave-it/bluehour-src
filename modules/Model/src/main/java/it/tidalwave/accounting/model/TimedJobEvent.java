/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - hg clone https://bitbucket.org/tidalwave/bluehour-src
 * %%
 * Copyright (C) 2013 - 2013 Tidalwave s.a.s. (http://tidalwave.it)
 * %%
 * *********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * *********************************************************************************************************************
 *
 * $Id$
 *
 * *********************************************************************************************************************
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
public class TimedJobEvent extends AbstractJobEvent
  {
    @Nonnull
    private final DateTime startDateTime;

    @Nonnull
    private final DateTime endDateTime;

    @Nonnull
    private final Money earnings;

    @Nonnull
    private final Money rate;

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    protected TimedJobEvent (final @Nonnull Builder builder)
      {
        super(builder);
        this.startDateTime = builder.getStartDateTime();
        this.endDateTime = builder.getEndDateTime();
        this.earnings = builder.getEarnings();
        this.rate = builder.getRate();
      }
  }
