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
package it.tidalwave.accounting.importer.ibiz;

import it.tidalwave.accounting.model.Money;
import java.math.MathContext;
import java.util.Date;
import javax.annotation.Nonnull;
import lombok.Delegate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.configuration.Configuration;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

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
    public Money getMoney (final @Nonnull String key)
      {
        return new Money(delegate.getBigDecimal(key).round(ROUNDING), "EUR");
      }

    @Nonnull
    public DateTime getDateTime (final @Nonnull String key)
      {
        return new DateTime((Date)delegate.getProperty(key));
      }

    @Nonnull
    public DateMidnight getDate (final @Nonnull String key)
      {
        return new DateTime((Date)delegate.getProperty(key)).toDateMidnight();
      }
  }
