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
package it.tidalwave.accounting.importer.ibiz.impl;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import java.math.MathContext;
import org.apache.commons.configuration2.Configuration;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.Id;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import static java.util.stream.Collectors.*;

/***************************************************************************************************************************************************************
 *
 * Decorates Apache Commons' {@link Configuration} to provide extra methods for specific data types.
 * 
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@RequiredArgsConstructor
public class ConfigurationDecorator implements Configuration
  {
    private static final MathContext ROUNDING = new MathContext(6);

    @Nonnull @Delegate
    private final Configuration delegate;

    @Nonnull
    public Id getId (@Nonnull final String key)
      {
        return new Id(delegate.getString(key).replace(":ABPerson", ""));
      }

    @Nonnull
    public Money getMoney (@Nonnull final String key)
      {
        return Money.of(delegate.getBigDecimal(key).round(ROUNDING), "EUR");
      }

    @Nonnull
    public LocalDateTime getDateTime (@Nonnull final String key)
      {
        return LocalDateTime.ofInstant(((Date)delegate.getProperty(key)).toInstant(), ZoneId.systemDefault());
      }

    @Nonnull
    public LocalDate getDate (@Nonnull final String key)
      {
        return getDateTime(key).toLocalDate();
      }

    @Nonnull
    public Stream<ConfigurationDecorator> getStream (@Nonnull final String name)
      {
        return delegate.getList(name).stream().map(o -> new ConfigurationDecorator((Configuration)o));
      }

    @Nonnull
    public List<Id> getIds (@Nonnull final String key)
      {
        return delegate.getList(key).stream().map(Id::new).collect(toList());
      }
  }
