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

import it.tidalwave.role.Identifiable;
import it.tidalwave.util.Id;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import static lombok.AccessLevel.PRIVATE;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;

/***********************************************************************************************************************
 *
 * This class models a single job event.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode @ToString
public abstract class JobEvent implements Identifiable
  {
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @AllArgsConstructor(access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        public enum Type { TIMED, FLAT };

        private final Id id;
        private final Type type;
        private final LocalDateTime startDateTime;
        private final LocalDateTime endDateTime;
        private final String name;
        private final String description;
        private final Money earnings;
        private final Money rate;
        private final List<JobEvent> events; // FIXME: immutable

        public Builder()
          {
            this(new Id(""), Type.TIMED, null, null, "", "", Money.ZERO, Money.ZERO, Collections.<JobEvent>emptyList());
          }

        @Nonnull
        public JobEvent create()
          {
            if ((events != null) && !events.isEmpty())
              {
                return new JobEventGroup(this);
              }
            else if (type == Type.TIMED)
              {
                return new TimedJobEvent(this);
              }
            else
              {
                return new FlatJobEvent(this);
              }
          }
      }

    @Getter @Nonnull
    protected final Id id;
    
    @Nonnull
    protected final String name;

    @Nonnull
    protected final String description;

    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public static JobEvent.Builder builder()
      {
        return new JobEvent.Builder(); // FIXME: avoid nulls
      }

    /*******************************************************************************************************************
     *
     * @param builder
     * 
     ******************************************************************************************************************/
    protected JobEvent (final @Nonnull Builder builder)
      {
        this.id = builder.getId();
        this.name = builder.getName();
        this.description = builder.getDescription();
      }
  }
