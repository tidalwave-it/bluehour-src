/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - hg clone https://bitbucket.org/tidalwave/bluehour-src
 * %%
 * Copyright (C) 2013 - 2014 Tidalwave s.a.s. (http://tidalwave.it)
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
import java.time.LocalDate;
import java.util.Collections;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class FlatJobEvent extends JobEvent
  {
    @Getter @Nonnull
    private final LocalDate date;

    @Getter @Nonnull
    private final Money earnings;

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    protected FlatJobEvent (final @Nonnull Builder builder)
      {
        super(builder);
        this.date = builder.getStartDateTime().toLocalDate();
        this.earnings = builder.getEarnings();
      }
    
    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public JobEvent.Builder asBuilder()
      {
        return new Builder(id, Builder.Type.FLAT, date.atStartOfDay(), null,
                           name, description, earnings, null, Collections.<JobEvent>emptyList());
      }
  }
