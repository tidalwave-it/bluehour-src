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
package it.tidalwave.accounting.importer.ibiz.impl;

import javax.annotation.Nonnull;
import java.util.List;
import java.io.IOException;
import java.nio.file.Path;
import corny.addressbook.NativeAddressBook;
import corny.addressbook.data.Contact;
import corny.addressbook.data.MultiValue;
import it.tidalwave.accounting.importer.ibiz.spi.IBizCustomerImporter;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.Address;
import it.tidalwave.accounting.model.CustomerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor @Slf4j
public class DefaultIBizCustomerImporter implements IBizCustomerImporter
  {
    @Nonnull
    private final CustomerRegistry customerRegistry;

    @Nonnull
    private final Path path;

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    public void run()
      throws IOException
      {
        final NativeAddressBook addressBook = NativeAddressBook.instance();
        IBizUtils.loadConfiguration(path).getStream("clients").forEach((customerConfig) -> 
          {
            final String clientCompany = customerConfig.getString("clientCompany");
            final String firstName = customerConfig.getString("firstName").trim();
            final Id addressBookId = customerConfig.getId("addressBookId");

            List<Contact> contacts = addressBook.getContactsWithFirstName(firstName);

            if (contacts.isEmpty())
              {
                contacts = addressBook.getContactsWithSomeAttribute(clientCompany);
              }

            final Contact contact = contacts.get(0);
            final MultiValue<String> phone = contact.getPhone();
            final MultiValue<String> email = contact.getEmail();

            Address.Builder addressBuilder = Address.builder();

            if (contact.getAddress() != null)
              {
                final corny.addressbook.data.Address addr = contact.getAddress().getFirstHomeValue();
                addressBuilder = addressBuilder.withCity(addr.getCity())
                                               .withState(addr.getCountry())
                                               .withStreet(addr.getStreet())
                                               .withZip("" + addr.getZip());
              }

            String vat = "";

            if (email != null)
              {
                vat = email.getFirstHomeValue(); // VAT is also there in my address book...
              }

            if (vat.equals("") && (phone != null))
              {
                vat = phone.getFirstHomeValue(); // VAT is also there in my address book...
              }

            customerRegistry.addCustomer().withId(addressBookId)
                                          .withName(firstName)
                                          .withBillingAddress(addressBuilder.create())
                                          .withVatNumber(vat)
                                          .create();
          });
      }
  }
