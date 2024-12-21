/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2024 by Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.model.types;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import static lombok.AccessLevel.PRIVATE;

/***************************************************************************************************************************************************************
 *
 * This class models the address of a customer.
 *
 * @author Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Immutable @Getter @EqualsAndHashCode @ToString
public class Address
  {
    public static final Address EMPTY = builder().create();

    @AllArgsConstructor(access = PRIVATE)
    @Immutable @With @Getter @ToString
    public static class Builder
      {
        private final String street;
        private final String city;
        private final String zip;
        private final String state;
        private final String country;

        @Nonnull
        public Address create()
          {
            return new Address(this);
          }
      }

    @Nonnull
    private final String street;

    @Nonnull
    private final String city;

    @Nonnull
    private final String state;

    @Nonnull
    private final String country;

    @Nonnull
    private final String zip;

    /***********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/
    @Nonnull
    public static Builder builder()
      {
        return new Builder("", "", "", "", "");
      }

    /***********************************************************************************************************************************************************
     *
     **********************************************************************************************************************************************************/
    protected Address (@Nonnull final Builder builder)
      {
        this.street = builder.getStreet();
        this.city = builder.getCity();
        this.zip = builder.getZip();
        this.state = builder.getState();
        this.country = builder.getCountry();
      }
  }
