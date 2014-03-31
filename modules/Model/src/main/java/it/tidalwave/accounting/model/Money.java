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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * This class models an amount of money.
 * 
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable
@RequiredArgsConstructor
public class Money
  {
    public static final Money ZERO = new Money(BigDecimal.ZERO, "EUR");

    @Getter @Nonnull
    private final BigDecimal amount;

    @Getter @Nonnull
    private final String currency;

    public Money (final long amount, final @Nonnull String currency)
      {
        this(BigDecimal.valueOf(amount), currency);
      }

    @Nonnull
    public static Money parse (final @Nonnull String string) 
      throws ParseException 
      {
        final String[] parts = string.split(" ");
        return new Money((BigDecimal)getFormat().parse(parts[0]), parts[1]);
      }
    
    @Override @Nonnull
    public String toString()
      {
        return String.format("%s %s", getFormat().format(amount), currency);
      }
    
    @Nonnull
    public static DecimalFormat getFormat()
      {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        final String pattern = "###0.00";
        final DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        
        return decimalFormat;
      }
  }
