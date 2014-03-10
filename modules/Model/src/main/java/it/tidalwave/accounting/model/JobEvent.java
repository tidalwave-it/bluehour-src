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

import java.math.BigDecimal;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import org.joda.time.DateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;
import static lombok.AccessLevel.PRIVATE;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @EqualsAndHashCode @ToString(exclude = { "project" })
public class JobEvent
  {
    @AllArgsConstructor(access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        private final Project project;
        private final DateTime startDateTime;
        private final DateTime endDateTime;
        private final String name;
        private final String description;
        private final Money earnings;
        private final Money rate;

        @Nonnull
        public JobEvent create()
          {
            return new JobEvent(this);
          }
      }

    @Nonnull
    private final Project project;

    @Nonnull
    private final String name;

    @Nonnull
    private final String description;

    @Nonnull
    private final DateTime startDateTime;

    @Nonnull
    private final DateTime endDateTime;

    @Nonnull
    private final Money earnings;

    @Nonnull
    private final Money rate;

    @Nonnull
    public static JobEvent.Builder builder()
      {
        return new JobEvent.Builder(null, null, null, "", "", Money.ZERO, Money.ZERO); // FIXME: avoid nulls
      }

    protected JobEvent (final @Nonnull Builder builder)
      {
        this.project = builder.getProject();
        this.startDateTime = builder.getStartDateTime();
        this.endDateTime = builder.getEndDateTime();
        this.name = builder.getName();
        this.description = builder.getDescription();
        this.earnings = builder.getEarnings();
        this.rate = builder.getRate();
      }
  }
