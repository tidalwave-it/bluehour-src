/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2025 by Tidalwave s.a.s. (http://tidalwave.it)
 *
 * *************************************************************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 *
 * *************************************************************************************************************************************************************
 *
 * git clone https://bitbucket.org/tidalwave/bluehour-src
 * git clone https://github.com/tidalwave-it/bluehour-src
 *
 * *************************************************************************************************************************************************************
 */
package it.tidalwave.accounting.model;

import jakarta.annotation.Nonnull;
// import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import it.tidalwave.accounting.model.spi.ObjectFactory;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.As;
import it.tidalwave.util.Id;
import it.tidalwave.role.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

/***************************************************************************************************************************************************************
 *
 * This class models a single job event.
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
///* @Immutable */
public interface JobEvent extends Identifiable, As
  {
    public enum Type { TIMED, FLAT }

    /***********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/
    @AllArgsConstructor// FIXME (access = PROTECTED)
    /* @Immutable */ @With @Getter @ToString
    public static class Builder
      {
        private final Id id;
        private final Type type;
        private final LocalDateTime startDateTime;
        private final LocalDateTime endDateTime;
        private final String name;
        private final String description;
        private final Money earnings;
        private final Money hourlyRate;
        private final List<? extends JobEvent> events; // FIXME: immutable

        public Builder()
          {
            this(new Id(""), Type.TIMED, null, null, "", "", Money.ZERO, Money.ZERO, Collections.emptyList());
          }

        @Nonnull
        public JobEvent create()
          {
            return ObjectFactory.getInstance().createJobEvent(this);
          }
      }

    /***********************************************************************************************************************************************************
     * Creates a builder for a job event.
     *
     * @return  the builder
     **********************************************************************************************************************************************************/
    @Nonnull
    public static JobEvent.Builder builder()
      {
        return new JobEvent.Builder(); // FIXME: avoid nulls
      }

    /***********************************************************************************************************************************************************
     * Returns a builder pre-populated with all the attributes.
     *
     * @return  the builder
     **********************************************************************************************************************************************************/
    @Nonnull
    public JobEvent.Builder toBuilder();
  }
