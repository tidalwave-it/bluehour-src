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
import java.util.Date;
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
@Immutable @EqualsAndHashCode @ToString
public class Project
  {
    @AllArgsConstructor(access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        private final Customer customer;
        private final String name;
        private final String number;
        private final String description;
        private final String notes;
        private final Money hourlyRate;
        private final Money amount;
        private final Date startDate;
        private final Date endDate;

        @Nonnull
        public Project create()
          {
            return new Project(this);
          }
      }

    @Nonnull
    private final Customer customer;

    @Nonnull
    private final String name;

    @Nonnull
    private final String number;

    @Nonnull
    private final String description;

    @Nonnull
    private final String notes;

    @Nonnull
    private final Money hourlyRate;

    @Nonnull
    private final Money amount;

    @Nonnull
    private final Date startDate;

    @Nonnull
    private final Date endDate;

    @Nonnull
    public static Project.Builder builder()
      {
        return new Project.Builder(null, "", "", "", "", Money.ZERO, Money.ZERO, null, null);
      }

    public Project (final @Nonnull Builder builder)
      {
        this.customer = builder.getCustomer();
        this.name = builder.getName();
        this.number = builder.getNumber();
        this.description = builder.getDescription();
        this.notes = builder.getNotes();
        this.hourlyRate = builder.getHourlyRate();
        this.amount = builder.getAmount();
        this.startDate = builder.getStartDate();
        this.endDate = builder.getEndDate();
      }
  }
