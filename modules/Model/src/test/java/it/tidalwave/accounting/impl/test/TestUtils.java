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
package it.tidalwave.accounting.impl.test;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class TestUtils
  {
    public static final DateTimeFormatter DATETIME_FORMATTER =
            new DateTimeFormatterBuilder().appendYear(4, 4)
                                          .appendLiteral("-")
                                          .appendMonthOfYear(2)
                                          .appendLiteral("-")
                                          .appendDayOfMonth(2)
                                          .appendLiteral("T")
                                          .appendHourOfDay(2)
                                          .appendLiteral(":")
                                          .appendMinuteOfHour(2)
                                          .appendLiteral(":")
                                          .appendSecondOfMinute(2)
                                          .appendLiteral(".")
                                          .appendMillisOfSecond(3)
                                          .appendTimeZoneOffset("", true, 2, 2)
                                          .toFormatter();

    public static final DateTimeFormatter DATE_FORMATTER =
            new DateTimeFormatterBuilder().appendYear(4, 4)
                                          .appendLiteral("-")
                                          .appendMonthOfYear(2)
                                          .appendLiteral("-")
                                          .appendDayOfMonth(2)
                                          .toFormatter();
  }
