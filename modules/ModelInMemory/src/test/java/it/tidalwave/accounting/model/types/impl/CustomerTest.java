/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2015 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.model.types.impl;

import it.tidalwave.accounting.model.Customer;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.AsDelegateProvider;
import it.tidalwave.util.spi.EmptyAsDelegateProvider;
import it.tidalwave.accounting.model.types.Address;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class CustomerTest
  {
    @BeforeMethod
    public void installEmptyAsSupport()
      {
        AsDelegateProvider.Locator.set(new EmptyAsDelegateProvider());
      }
    
    @Test
    public void toString_must_return_all_the_fields()
      {
        final Address a1 = Address.builder().withStreet("Foo Bar rd 20")
                                            .withCity("San Francisco")
                                            .withZip("12345")
                                            .withState("CA")
                                            .withCountry("USA")
                                            .create();
        final Customer c1 = Customer.builder().withId(new Id("the id"))
                                              .withName("Acme Corp.")
                                              .withVatNumber("1233455345")
                                              .withBillingAddress(a1)
                                              .create();

        assertThat(c1.toString(), is("InMemoryCustomer(id=the id, name=Acme Corp., billingAddress=Address(street=Foo Bar rd 20, "
                                   + "city=San Francisco, state=CA, country=USA, zip=12345), vatNumber=1233455345)"));
      }
  }