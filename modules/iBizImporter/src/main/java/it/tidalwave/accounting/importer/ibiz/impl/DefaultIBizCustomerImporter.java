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
package it.tidalwave.accounting.importer.ibiz.impl;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import it.tidalwave.accounting.importer.ibiz.spi.IBizCustomerImporter;
import it.tidalwave.accounting.model.CustomerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
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
    public void importCustomers()
      throws IOException
      {
        log.debug("importCustomers()");
/*        final NativeAddressBook addressBook = NativeAddressBook.instance();
        IBizUtils.loadConfiguration(path.resolve("clients")).getStream("clients").forEach(customerConfig -> 
          {
            final String firstName = trim(customerConfig.getString("firstName"));
            final String clientCompany = customerConfig.getString("clientCompany");
            final Contact contact = getContact(addressBook, firstName, clientCompany);

            customerRegistry.addCustomer().withId(customerConfig.getId("addressBookId"))
                                          .withName(firstName)
                                          .withBillingAddress(getAddress(contact))
                                          .withVatNumber(getVatNumber(contact))
                                          .create();
          });*/
      }

//    @Nonnull
//    private String getVatNumber (final @Nonnull Contact contact)
//      {
//        final MultiValue<String> phone = contact.getPhone();
//        final MultiValue<String> email = contact.getEmail();
//        String vat = "";
//
//        if (email != null)
//          {
//            vat = email.getFirstHomeValue(); // VAT is also there in my address book...
//          }
//
//        if (((vat == null) || vat.equals("")) && (phone != null))
//          {
//            vat = phone.getFirstHomeValue(); // VAT is also there in my address book...
//          }
//
//        return vat;
//      }

//    @Nonnull
//    private Address getAddress (final @Nonnull Contact contact)
//      {
//        Address.Builder addressBuilder = Address.builder();
//
//        if (contact.getAddress() != null)
//          {
//            final corny.addressbook.data.Address addr = contact.getAddress().getFirstHomeValue();
//            addressBuilder = addressBuilder.withCity(addr.getCity())
//                                           .withState(addr.getCountry())
//                                           .withStreet(addr.getStreet())
//                                           .withZip("" + addr.getZip());
//          }
//
//        return addressBuilder.create();
//    }

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
//    @Nonnull
//    private Contact getContact (final @Nonnull NativeAddressBook addressBook,
//                                final @Nonnull String firstName,
//                                final @Nonnull String clientCompany)
//      {
//        List<Contact> contacts = addressBook.getContactsWithFirstName(firstName);
//
//        if (contacts.isEmpty())
//          {
//            contacts = addressBook.getContactsWithSomeAttribute(clientCompany);
//          }
//
//        return contacts.get(0);
//      }
    
    @Nonnull
    private static String trim (@CheckForNull final String string)
      {
        return (string == null) ? "" : string.trim();
      }
  }
