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

import javax.annotation.Nonnull;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.ProjectRegistry;
import lombok.NoArgsConstructor;
import static java.util.stream.Collectors.toList;
import static javax.xml.bind.annotation.XmlAccessOrder.ALPHABETICAL;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
//@Mutable
@NoArgsConstructor
@XmlRootElement(name = "accounting") @XmlAccessorType(FIELD) @XmlAccessorOrder(ALPHABETICAL)
public class AccountingXml 
  {
    @XmlElementWrapper(name = "customers")
    @XmlElement(name = "customer")
    private List<CustomerXml> customers;
    
    @XmlElementWrapper(name = "projects")
    @XmlElement(name = "project")
    private List<ProjectXml> projects;
    
    @XmlElementWrapper(name = "invoices")
    @XmlElement(name = "invoice")
    private List<InvoiceXml> invoices;
    
    public AccountingXml (final @Nonnull Accounting accounting)
      {
        customers = accounting.getCustomerRegistry().findCustomers().map(customer -> new CustomerXml(customer))
                                                                    .collect(toList());
        projects = accounting.getProjectRegistry().findProjects().map(project -> new ProjectXml(project))
                                                                 .collect(toList());
        invoices = accounting.getInvoiceRegistry().findInvoices().map(invoice -> new InvoiceXml(invoice))
                                                                 .collect(toList());
      }

    public void fill (final @Nonnull Accounting accounting) 
      {
        final CustomerRegistry customerRegistry = accounting.getCustomerRegistry();
        final ProjectRegistry projectRegistry = accounting.getProjectRegistry();
        final InvoiceRegistry invoiceRegistry = accounting.getInvoiceRegistry();
        customers.forEach(customer -> customerRegistry.addCustomer().with(customer.toBuilder()).create());
        projects.forEach(project -> projectRegistry.addProject().with(project.toBuilder(accounting)).create());
        invoices.forEach(invoice -> invoiceRegistry.addInvoice().with(invoice.toBuilder(accounting)).create());
      }
  }
