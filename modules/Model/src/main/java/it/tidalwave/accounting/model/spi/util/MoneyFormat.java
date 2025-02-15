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
package it.tidalwave.accounting.model.spi.util;

import jakarta.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import it.tidalwave.accounting.model.types.Money;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public class MoneyFormat
  {
    private static final Map<String, String> CURRENCY_SYMBOL_MAP = new HashMap<>();

    static
      {
        CURRENCY_SYMBOL_MAP.put("EUR", "€");
        CURRENCY_SYMBOL_MAP.put("USD", "$");
      }

    @Nonnull
    public String format (@Nonnull final Money amount)
      {
        final var currency = amount.getCurrency();
        return String.format("%s %s", Money.getFormat().format(amount.getAmount()),
                                      CURRENCY_SYMBOL_MAP.getOrDefault(currency, currency));
      }
  }
