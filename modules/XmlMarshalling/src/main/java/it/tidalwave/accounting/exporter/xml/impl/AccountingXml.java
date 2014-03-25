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
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import static javax.xml.bind.annotation.XmlAccessOrder.ALPHABETICAL;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
//@Mutable
@NoArgsConstructor @AllArgsConstructor
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
  }
