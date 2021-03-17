/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2021 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.role.ui;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import it.tidalwave.accounting.model.spi.util.DurationFormat;
import it.tidalwave.accounting.model.spi.util.MoneyFormat;
import it.tidalwave.accounting.model.types.Money;

public interface Displayable2 extends Displayable
  {
    @Nonnull
    public static Displayable of (final @Nonnull Supplier<String> supplier)
      {
        return () -> supplier.get();
      }

    @Nonnull
    public static Displayable of (final @Nonnull DateTimeFormatter dtf, final @Nonnull TemporalAccessor ta)
      {
        return () -> dtf.format(ta);
      }

    @Nonnull
    public static Displayable of (final @Nonnull MoneyFormat mf, final @Nonnull Money money)
      {
        return () -> mf.format(money);
      }

    @Nonnull
    public static Displayable of (final @Nonnull DurationFormat mf, final @Nonnull Duration money)
      {
        return () -> mf.format(money);
      }

    @Nonnull
    public static <T> Displayable of (final @Nonnull Function<T, String> function, final @Nonnull T object)
      {
        return () -> function.apply(object);
      }
  }
