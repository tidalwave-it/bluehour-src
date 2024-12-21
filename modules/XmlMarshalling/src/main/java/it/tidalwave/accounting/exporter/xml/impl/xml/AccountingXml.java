/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2024 by Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.exporter.xml.impl.xml;

import javax.annotation.Nonnull;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import it.tidalwave.accounting.model.Accounting;
import lombok.NoArgsConstructor;
import static javax.xml.bind.annotation.XmlAccessOrder.ALPHABETICAL;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
//@Mutable
@NoArgsConstructor
@XmlRootElement(name = "accounting") @XmlAccessorType(FIELD) @XmlAccessorOrder(ALPHABETICAL)
public class AccountingXml 
  {
    @XmlElementWrapper(name = "customers")
    @XmlElement(name = "customer")
    private List<CustomerXml> customersXml;
    
    @XmlElementWrapper(name = "projects")
    @XmlElement(name = "project")
    private List<ProjectXml> projectsXml;
    
    @XmlElementWrapper(name = "invoices")
    @XmlElement(name = "invoice")
    private List<InvoiceXml> invoicesxml;
    
    public AccountingXml (@Nonnull final Accounting accounting)
      {
        customersXml = accounting.getCustomerRegistry().findCustomers().stream().map(CustomerXml::new)
                .sorted(comparing(CustomerXml::getId)).collect(toList());
        projectsXml = accounting.getProjectRegistry().findProjects().stream().map(ProjectXml::new)
                .sorted(comparing(ProjectXml::getId)).collect(toList());
        invoicesxml = accounting.getInvoiceRegistry().findInvoices().stream().map(InvoiceXml::new)
                .sorted(comparing(InvoiceXml::getId)).collect(toList());
      }

    public void fill (@Nonnull final Accounting accounting)
      {
        final var customerRegistry = accounting.getCustomerRegistry();
        final var projectRegistry = accounting.getProjectRegistry();
        final var invoiceRegistry = accounting.getInvoiceRegistry();
        customersXml.forEach(customer -> customerRegistry.addCustomer().with(customer.toBuilder()).create());
        projectsXml.forEach(project -> projectRegistry.addProject().with(project.toBuilder(accounting)).create());
        invoicesxml.forEach(invoice -> invoiceRegistry.addInvoice().with(invoice.toBuilder(accounting)).create());
      }
  }
