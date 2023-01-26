/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2023 Tidalwave s.a.s. (http://tidalwave.it)
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
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import it.tidalwave.accounting.model.ProjectRegistry.JobEventFinder;
import it.tidalwave.accounting.model.spi.ObjectFactory;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.As;
import it.tidalwave.util.Id;
import it.tidalwave.role.Identifiable;
import it.tidalwave.role.SimpleComposite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

/***********************************************************************************************************************
 *
 * This class models a project.
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@Immutable
public interface Project extends SimpleComposite<JobEvent>, Identifiable, As
  {
    public enum Status { OPEN, CLOSED }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @AllArgsConstructor // FIXME (access = PROTECTED)
    @Immutable @With @Getter @ToString
    public static class Builder
      {
        public static interface Callback // Lombok @With doesn't support builder subclasses
          {
            public void register (@Nonnull final Project project);

            public static final Callback DEFAULT = (project) -> {};
          }

        private final Id id;
        private final Customer customer;
        private final String name;
        private final String number;
        private final String description;
        private final String notes;
        private final Status status;
        private final Money hourlyRate;
        private final Money budget;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final List<? extends JobEvent> events; // FIXME: immutable
        private final Callback callback;

        public Builder()
          {
            this(Callback.DEFAULT);
          }

        public Builder (@Nonnull final Callback callback)
          {
             // FIXME: avoid null
            this(new Id(""), null, "", "", "", "", Status.OPEN, Money.ZERO, Money.ZERO, null, null,
                 Collections.emptyList(), callback);
          }

        @Nonnull
        public Builder with (@Nonnull final Builder builder)
          {
            return builder.withCallback(callback);
          }

        @Nonnull
        public Project create()
          {
            final var project = ObjectFactory.getInstance().createProject(this);
            callback.register(project);
            return project;
          }
      }

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
    @Nonnull
    public Customer getCustomer();

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Nonnull
    public JobEventFinder findChildren();

    /*******************************************************************************************************************
     *
     * Returns a builder pre-populated with all the attributes.
     *
     * @return  the builder
     *
     ******************************************************************************************************************/
    @Nonnull
    public Builder toBuilder();
  }
