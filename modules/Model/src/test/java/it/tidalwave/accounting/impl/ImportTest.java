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

import javax.annotation.Nonnull;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import org.joda.time.DateTime;
import corny.addressbook.NativeAddressBook;
import corny.addressbook.data.Contact;
import corny.addressbook.data.MultiValue;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import it.tidalwave.accounting.model.Address;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Money;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.impl.DefaultCustomerRegistry;
import it.tidalwave.accounting.model.impl.DefaultProjectRegistry;
import org.testng.annotations.Test;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class ImportTest
  {
    enum IBizJobEventType
      {
        EVENT, FIXED, BOH2, BOH3, BOH4, GROUP
      }

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

            final Customer customer = customerRegistry.addCustomer().withName(firstName)
                                                                    .withBillingAddress(addressBuilder.create())
                                                                    .withVatNumber(vat)
                                                                    .create();
            log.info("{}", customer);
          }

        // TODO: assertions; but we must first anonymize the data
      }

    @Test
    public void importProjects()
      throws ConfigurationException
      {
        final File file = new File("/Users/fritz/Settings/iBiz/Projects/4D2263D4-9043-40B9-B162-2C8951F86503.ibiz"); // FIXME
        final XMLPropertyListConfiguration x = new XMLPropertyListConfiguration(file);

//        final ProjectRegistry projectRegistry = new DefaultProjectRegistry();

        final List<Object> jobEvents = x.getList("jobEvents");

        for (final Object j : jobEvents)
          {
            final XMLPropertyListConfiguration jobEvent = (XMLPropertyListConfiguration)j;
            importJobEvent(jobEvent);
          }
      }

    private void importJobEvent (final @Nonnull XMLPropertyListConfiguration jobEvent)
      throws ConfigurationException
      {
        final MathContext rounding = new MathContext(6);

        final List<Object> childEvents = jobEvent.getList("children");

        for (final Object c : childEvents)
          {
            final XMLPropertyListConfiguration childEvent = (XMLPropertyListConfiguration)c;
            importJobEvent(childEvent);
          }

//        log.debug(">>>> properties: {}", toList(jobEvent.getKeys()));

        final boolean checkedOut = jobEvent.getBoolean("checkedout");
        final boolean isExpense = jobEvent.getBoolean("isExpense");
        final boolean nonBillable = jobEvent.getBoolean("nonBillable");
//        final int taxable = jobEvent.getInt("taxable");
        final Money earnings = new Money(jobEvent.getBigDecimal("jobEventEarnings").round(rounding), "EUR");
        final DateTime startDate = new DateTime((Date)jobEvent.getProperty("jobEventStartDate"));
        final DateTime endDate = new DateTime((Date)jobEvent.getProperty("jobEventEndDate"));
        final DateTime lastModifiedDate = new DateTime((Date)jobEvent.getProperty("lastModifiedDate"));
        final String name =jobEvent.getString("jobEventName");
        final String notes = jobEvent.getString("jobEventNotes");
        final int paid = jobEvent.getInt("jobEventPaid");
        final IBizJobEventType type = IBizJobEventType.values()[jobEvent.getInt("jobEventType")];
        final Money rate = new Money(jobEvent.getBigDecimal("jobEventRate").round(rounding), "EUR");

        final JobEvent event = JobEvent.builder().withStartDateTime(startDate)
                                                 .withEndDateTime(endDate)
                                                 .withName(name)
                                                 .withDescription(notes)
                                                 .withRate(rate)
                                                 .withEarnings(earnings)
                                                 .create();
        log.info("TYPE {}: {}", type, event);
        /*
                                        <key>tax1</key>
                                        <real>22</real>
                                        <key>uniqueIdentifier</key>
                                        <string>E4EA6321-75FE-45A9-AB1F-CB456E918293</string>

         */
      }

    @Nonnull
    private static <T> List<T> toList (final @Nonnull Iterator<T> i)
      {
        final List<T> list = new ArrayList<T>();

        while (i.hasNext())
          {
            list.add(i.next());
          }

        return list;
      }
  }
