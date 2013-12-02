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
package it.tidalwave.accounting.impl;

import java.util.List;
import java.io.File;
import corny.addressbook.NativeAddressBook;
import corny.addressbook.data.Contact;
import corny.addressbook.data.MultiValue;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration;
import it.tidalwave.accounting.model.Address;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.impl.DefaultCustomerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class ImportTest
  {
    @Test
    public void importFromAddressBookTest()
      throws Exception
      {
        final NativeAddressBook addressBook = NativeAddressBook.instance();

        final File file = new File("/Users/fritz/Settings/iBiz/clients"); // FIXME
        final XMLPropertyListConfiguration x = new XMLPropertyListConfiguration(file);
        final List<Object> clients = x.getList("clients");

        final CustomerRegistry customerRegistry = new DefaultCustomerRegistry();

        for (final Object c : clients)
          {
            final XMLPropertyListConfiguration client = (XMLPropertyListConfiguration)c;
            final String clientCompany = client.getString("clientCompany");
            final String firstName = client.getString("firstName").trim();
//            final String addressBookId = client.getString("addressBookId");

            List<Contact> contacts = addressBook.getContactsWithFirstName(firstName);

            if (contacts.isEmpty())
              {
                contacts = addressBook.getContactsWithSomeAttribute(clientCompany);
              }

            final Contact contact = contacts.get(0);
            final MultiValue<String> phone = contact.getPhone();
            final MultiValue<String> email = contact.getEmail();

            String vat = "";

            if (email != null)
              {
                vat = email.getFirstHomeValue(); // VAT is also there in my address book...
              }

            if (vat.equals("") && (phone != null))
              {
                vat = phone.getFirstHomeValue(); // VAT is also there in my address book...
              }

            final corny.addressbook.data.Address addr = contact.getAddress().getFirstHomeValue();
            final Address address = Address.builder().withCity(addr.getCity())
                                                     .withState(addr.getCountry())
                                                     .withStreet(addr.getStreet())
                                                     .withZip("" + addr.getZip())
                                                     .create();
            final Customer customer = customerRegistry.addCustomer().withName(firstName)
                                                                    .withBillingAddress(address)
                                                                    .withVatNumber(vat)
                                                                    .create();
            log.info("{}", customer);
          }
      }
  }
