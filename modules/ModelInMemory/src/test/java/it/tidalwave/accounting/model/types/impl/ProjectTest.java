/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2023 Tidalwave s.a.s. (http://tidalwave.it)
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
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.model.types.impl;

import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.AsDelegateProvider;
import it.tidalwave.accounting.model.types.Address;
import it.tidalwave.accounting.model.types.Money;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static it.tidalwave.accounting.model.types.impl.TestUtils.parseDate;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public class ProjectTest
  {
    @BeforeMethod
    public void installEmptyAsSupport()
      {
        AsDelegateProvider.Locator.set(AsDelegateProvider.empty());
      }
    
    @Test
    public void toString_must_be_properly_computed()
      {
        final var a1 = Address.builder().withStreet("Foo Bar rd 20")
                              .withCity("San Francisco")
                              .withZip("12345")
                              .withState("CA")
                              .withCountry("USA")
                              .create();
        final var c1 = Customer.builder().withId(new Id("1"))
                               .withName("Acme Corp.")
                               .withVatNumber("1233455345")
                               .withBillingAddress(a1)
                               .create();
        final var p = Project.builder().withId(new Id("2"))
                             .withBudget(new Money(10500, "EUR"))
                             .withCustomer(c1)
                             .withName("Project 1")
                             .withDescription("description of project 1")
                             .withStartDate(parseDate("2014-01-03"))
                             .withEndDate(parseDate("2014-02-12"))
                             .withNotes("Notes for project 1")
                             .withNumber("1")
                             .withHourlyRate(new Money(43, "EUR"))
                             .create();
        assertThat(p.toString(), is("InMemoryProject(id=2, customer=InMemoryCustomer(id=1, name=Acme Corp., billingAddress="
                                  + "Address(street=Foo Bar rd 20, city=San Francisco, state=CA, country=USA, zip=12345), "
                                  + "vatNumber=1233455345), name=Project 1, number=1, "
                                  + "description=description of project 1, notes=Notes for project 1, "
                                  + "status=OPEN, hourlyRate=43.00 EUR, budget=10500.00 EUR, "
                                  + "startDate=2014-01-03, endDate=2014-02-12)"));
      }
  }