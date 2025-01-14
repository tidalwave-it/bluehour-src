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
package it.tidalwave.accounting.model.types.impl;

import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.Id;
import it.tidalwave.role.spi.SystemRoleFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static it.tidalwave.accounting.model.types.impl.TestUtils.parseDateTime;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public class JobEventTest
  {
    @BeforeMethod
    public void installEmptyAsSupport()
      {
        SystemRoleFactory.reset();
      }
    
    @Test
    public void toString_must_be_properly_computed()
      {
//        final Address a1 = Address.builder().withStreet("Foo Bar rd 20")
//                                            .withCity("San Francisco")
//                                            .withZip("12345")
//                                            .withState("CA")
//                                            .withCountry("USA")
//                                            .create();
//        final Customer c1 = Customer.builder().withName("Acme Corp.")
//                                              .withVatNumber("1233455345")
//                                              .withBillingAddress(a1)
//                                              .create();
//        final Project p = Project.builder().withAmount(Money.of(10500, "EUR"))
//                                            .withCustomer(c1)
//                                            .withName("Project 1")
//                                            .withDescription("description of project 1")
//                   //                         .withStartDate(null)
//                   //                         .withEndDate(null)
//                                            .withNotes("Notes for project 1")
//                                            .withNumber("1")
//                                            .withHourlyRate(Money.of(43, "EUR"))
//                                            .create();
        final var j1 = JobEvent.builder().withId(new Id("1"))
                               .withName("Consultancy")
                               .withDescription("Consultancy description")
                               .withStartDateTime(parseDateTime("2014-01-05T12:34:56.0"))
                               .withEndDateTime(parseDateTime("2014-01-05T13:45:34.0"))
                               .withHourlyRate(Money.of(48, "EUR"))
                               .withEarnings(Money.of(430, "EUR"))
                               .create();

        assertThat(j1.toString(), is("InMemoryTimedJobEvent("
                                   + "super=InMemoryJobEvent(id=1, name=Consultancy, description=Consultancy description), "
                                   + "startDateTime=2014-01-05T12:34:56, "
                                   + "endDateTime=2014-01-05T13:45:34, "
                                   + "earnings=430.00 EUR, hourlyRate=48.00 EUR)"));
      }
  }