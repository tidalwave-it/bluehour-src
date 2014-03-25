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
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.Money;
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
@NoArgsConstructor
@XmlRootElement(name = "invoice") @XmlAccessorType(FIELD) @XmlAccessorOrder(ALPHABETICAL)
public class InvoiceXml 
  {
    @XmlAttribute(name = "id")
    @XmlID
    @XmlJavaTypeAdapter(IdAdapter.class)
    private Id id;
    
    @XmlElement(name = "number")
    private String number;
    
    @XmlElement(name = "project")
    @XmlIDREF
    private ProjectXml project;
    
//    private List<JobEvent> jobEvents; // FIXME: immutablelist
    @XmlElement(name = "date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
    
    private int daysUntilDue; // FIXME
    
    @XmlElement(name = "dueDate")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dueDate;
    
    @XmlElement(name = "earnings")
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money earnings;
    
    @XmlElement(name = "tax")
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money tax;
    
    public InvoiceXml (final @Nonnull Invoice invoice)
      {
        final Invoice.Builder b = invoice.asBuilder();
        this.id = b.getId();
        this.number = b.getNumber();
        this.project = new ProjectXml(b.getProject());
        this.date = b.getDate();
        this.daysUntilDue = b.getDaysUntilDue();
        this.dueDate = b.getDueDate();
        this.earnings = b.getEarnings();
        this.tax = b.getTax();
      }
  }
