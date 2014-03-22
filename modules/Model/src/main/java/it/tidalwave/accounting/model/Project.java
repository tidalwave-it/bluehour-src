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
import java.util.Collections;
import java.util.List;
import org.joda.time.DateMidnight;
import it.tidalwave.util.Finder;
import it.tidalwave.util.spi.SimpleFinderSupport;
import it.tidalwave.role.SimpleComposite;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;
import static lombok.AccessLevel.PRIVATE;

/***********************************************************************************************************************
 *
 * This class models a project.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @Wither
@AllArgsConstructor(access = PRIVATE) @EqualsAndHashCode @ToString(exclude = {"events"})
public class Project implements SimpleComposite<AbstractJobEvent>
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
        public static interface Callback // Lombok @Wither doesn't support builder subclasses
          {
            public void register (final @Nonnull Project project);

            public static final Callback DEFAULT = (final @Nonnull Project project) ->
              {
              };
          }

        private final Customer customer;
        private final String name;
        private final String number;
        private final String description;
        private final String notes;
        private final Money hourlyRate;
        private final Money amount;
        private final DateMidnight startDate;
        private final DateMidnight endDate;
        private final List<AbstractJobEvent> events; // FIXME: immutable
        private final Callback callback;

        public Builder()
          {
            this(Callback.DEFAULT);
          }

        public Builder (final @Nonnull Callback callback)
          {
             // FIXME: avoid null
            this(null, "", "", "", "", Money.ZERO, Money.ZERO, null, null, Collections.<AbstractJobEvent>emptyList(), callback);
          }

        @Nonnull
        public Project create()
          {
            final Project project = new Project(this);
            callback.register(project);
            return project;
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
    private final DateMidnight startDate;

    @Nonnull
    private final DateMidnight endDate;

    @Nonnull
    private final List<AbstractJobEvent> events; // FIXME: immutable

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @Nonnull
    public static Project.Builder builder()
      {
        return new Project.Builder();
      }

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    protected Project (final @Nonnull Builder builder)
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
