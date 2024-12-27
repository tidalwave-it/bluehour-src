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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/***************************************************************************************************************************************************************
 *
 * This class models an amount of money.
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Immutable @RequiredArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class Money implements Comparable<Money>
  {
    public static final Money ZERO = Money.of(BigDecimal.ZERO, "EUR");

    @Nonnull
    private final BigDecimal amount;

    @Nonnull
    private final String currency;

    private Money (final long amount, @Nonnull final String currency)
      {
        this(BigDecimal.valueOf(amount), currency);
      }

    @Nonnull
    public static Money of (final BigDecimal amount, @Nonnull final String currency)
      {
        return new Money(amount, currency);
      }

    @Nonnull
    public static Money of (final long amount, @Nonnull final String currency)
      {
        return new Money(amount, currency);
      }

    @Nonnull
    public static Money parse (@Nonnull final String string)
      throws ParseException
      {
        final var parts = string.split(" ");
        return of((BigDecimal)getFormat().parse(parts[0]), parts[1]);
      }

    @Nonnull
    public Money add (@Nonnull final Money other)
      {
        checkCurrencies(other);
        return of(amount.add(other.amount), currency);
      }

    @Nonnull
    public Money subtract (@Nonnull final Money other)
      {
        checkCurrencies(other);
        return of(amount.subtract(other.amount), currency);
      }

    @Nonnegative
    public double divided (@Nonnull final Money other)
      {
        checkCurrencies(other);
        // Can fail with ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.
//        return amount.divide(other.amount).doubleValue();
        return amount.doubleValue() / other.amount.doubleValue();
      }

    @Nonnull
    public static DecimalFormat getFormat()
      {
        final var symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        final var pattern = "###0.00";
        final var decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        return decimalFormat;
      }

    @Override
    public int compareTo (@Nonnull final Money other)
      {
        checkCurrencies(other);
        return this.amount.compareTo(other.amount);
      }

    public boolean isEqualTo (@Nonnull final Money other)
      {
        return compareTo(other) == 0;
      }

    public boolean greaterThan (@Nonnull final Money other)
      {
        return compareTo(other) > 0;
      }

    public boolean lowerThan (@Nonnull final Money other)
      {
        return compareTo(other) < 0;
      }

    @Override @Nonnull
    public String toString()
      {
        return String.format("%s %s", getFormat().format(amount), currency);
      }

    private void checkCurrencies (@Nonnull final Money other)
      {
        if (!this.currency.equals(other.currency))
          {
            throw new IllegalArgumentException(String.format("Currency mismatch: %s vs %s", this.currency, other.currency));
          }
      }
  }
