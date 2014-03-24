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
package it.tidalwave.accounting.importer.ibiz.impl;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.stream.Stream;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.apache.commons.configuration.Configuration;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.Money;
import lombok.Delegate;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class ConfigurationDecorator implements Configuration
  {
    private static final MathContext ROUNDING = new MathContext(6);

    @Nonnull @Delegate
    private final Configuration delegate;

    @Nonnull
    public Id getId (final @Nonnull String key)
      {
        return new Id(delegate.getString(key).replace(":ABPerson", ""));
      }

    @Nonnull
    public Money getMoney (final @Nonnull String key)
      {
        return new Money(delegate.getBigDecimal(key).round(ROUNDING), "EUR");
      }

    @Nonnull
    public LocalDateTime getDateTime (final @Nonnull String key)
      {
        return LocalDateTime.ofInstant(((Date)delegate.getProperty(key)).toInstant(), ZoneId.systemDefault());
      }

    @Nonnull
    public LocalDate getDate (final @Nonnull String key)
      {
        return getDateTime(key).toLocalDate();
      }

    @Nonnull
    public Stream<ConfigurationDecorator> getStream (final @Nonnull String name)
      {
        return delegate.getList(name).stream().map((o) -> new ConfigurationDecorator((Configuration)o));
      }
  }
