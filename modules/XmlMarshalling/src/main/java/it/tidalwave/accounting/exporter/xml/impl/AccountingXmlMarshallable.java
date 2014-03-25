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
package it.tidalwave.accounting.exporter.xml.impl;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.role.Marshallable;
import lombok.RequiredArgsConstructor;
import static java.util.stream.Collectors.toList;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class AccountingXmlMarshallable implements Marshallable
  {
    @Nonnull
    private final Accounting accounting;

    @Override
    public void marshal (final @Nonnull OutputStream os)
      throws IOException 
      {
        try 
          {
            final List<CustomerXml> customers = accounting.getCustomerRegistry().findCustomers()
                    .map(customer -> new CustomerXml(customer))
                    .collect(toList());
            final List<ProjectXml> projects = accounting.getProjectRegistry().findProjects()
                    .map(project -> new ProjectXml(project))
                    .collect(toList());
            final List<InvoiceXml> invoices = accounting.getInvoiceRegistry().findInvoices()
                    .map(invoice -> new InvoiceXml(invoice))
                    .collect(toList());
            final AccountingXml accountingXml = new AccountingXml(customers, projects, invoices);
            
            final JAXBContext jaxbc = JAXBContext.newInstance(AccountingXml.class);
            final Marshaller marshaller = jaxbc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            final PrintWriter pw = new PrintWriter(os);
            marshaller.marshal(accountingXml, pw);
            pw.flush();
          } 
        catch (JAXBException e) 
          {
            throw new IOException(e);
          }
      }
  }
