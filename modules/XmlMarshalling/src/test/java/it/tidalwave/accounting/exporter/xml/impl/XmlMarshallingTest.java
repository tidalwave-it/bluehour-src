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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import it.tidalwave.accounting.importer.ibiz.IBizImporter;
import it.tidalwave.accounting.importer.ibiz.impl.DefaultIBizImporter;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class XmlMarshallingTest
  {
    @Test
    public void testExport()
      throws Exception
      {
        final Path iBizFolder = Paths.get("/Users/fritz/Settings/iBiz/"); // FIXME
        final Path testFolder = Paths.get("target/test-results");
        Files.createDirectories(testFolder);

        final IBizImporter importer = DefaultIBizImporter.builder()
                                                         .withPath(iBizFolder)
                                                         .create();
        importer.importAll();
        
        final JAXBContext jaxbc = JAXBContext.newInstance(AccountingXml.class);
        final List<CustomerXml> customers = importer.getCustomerRegistry().findCustomers()
                                                                          .map(customer -> new CustomerXml(customer))
                                                                          .collect(Collectors.toList());
        final List<ProjectXml> projects = importer.getProjectRegistry().findProjects()
                                                                       .map(project -> new ProjectXml(project))
                                                                       .collect(Collectors.toList());
        final List<InvoiceXml> invoices = importer.getInvoiceRegistry().findInvoices()
                                                                       .map(invoice -> new InvoiceXml(invoice))
                                                                       .collect(Collectors.toList());
        final AccountingXml accountingXml = new AccountingXml(customers, projects, invoices);
        final Marshaller marshaller = jaxbc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(accountingXml, System.err);
      }
  }
