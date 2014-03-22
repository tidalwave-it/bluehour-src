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

import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static it.tidalwave.accounting.importer.ibiz.impl.TestUtils.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class ProjectTest
  {
    @Test
    public void toString_must_be_properly_computed()
      {
        final Address a1 = Address.builder().withStreet("Foo Bar rd 20")
                                            .withCity("San Francisco")
                                            .withZip("12345")
                                            .withState("CA")
                                            .withCountry("USA")
                                            .create();
        final Customer c1 = Customer.builder().withName("Acme Corp.")
                                              .withVatNumber("1233455345")
                                              .withBillingAddress(a1)
                                              .create();
        final Project p = Project.builder().withAmount(new Money(10500, "EUR"))
                                            .withCustomer(c1)
                                            .withName("Project 1")
                                            .withDescription("description of project 1")
                                            .withStartDate(parseDate("2014-01-03"))
                                            .withEndDate(parseDate("2014-02-12"))
                                            .withNotes("Notes for project 1")
                                            .withNumber("1")
                                            .withHourlyRate(new Money(43, "EUR"))
                                            .create();
        assertThat(p.toString(), is("Project(customer=Customer(id=, name=Acme Corp., billingAddress="
                                  + "Address(street=Foo Bar rd 20, city=San Francisco, state=CA, country=USA, zip=12345), "
                                  + "vatNumber=1233455345), name=Project 1, number=1, "
                                  + "description=description of project 1, notes=Notes for project 1, hourlyRate=43 EUR, "
                                  + "amount=10500 EUR, "
                                  + "startDate=2014-01-03T00:00:00.000+01:00, endDate=2014-02-12T00:00:00.000+01:00)"));
      }
  }