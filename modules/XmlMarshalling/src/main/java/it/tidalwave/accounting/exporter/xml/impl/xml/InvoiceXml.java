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
package it.tidalwave.accounting.exporter.xml.impl.xml;

import javax.annotation.Nonnull;
import java.util.List;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import it.tidalwave.util.Id;
import it.tidalwave.util.NotFoundException;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.exporter.xml.impl.adapters.IdAdapter;
import it.tidalwave.accounting.exporter.xml.impl.adapters.LocalDateAdapter;
import it.tidalwave.accounting.exporter.xml.impl.adapters.MoneyAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static java.util.stream.Collectors.toList;
import static javax.xml.bind.annotation.XmlAccessOrder.ALPHABETICAL;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
//@Mutable
@NoArgsConstructor
@XmlRootElement(name = "invoice") @XmlAccessorType(FIELD) @XmlAccessorOrder(ALPHABETICAL)
public class InvoiceXml 
  {
    @Getter
    @XmlAttribute(name = "id")
    @XmlID
    @XmlJavaTypeAdapter(IdAdapter.class)
    private Id id;
    
    @XmlElement(name = "number")
    private String number;
    
    @XmlElement(name = "project")
    @XmlIDREF
    private ProjectXml projectXml;
    
    @XmlElement(name = "date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
    
    @XmlElement(name = "dueDate")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dueDate;
    
    @XmlElement(name = "earnings")
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money earnings;
    
    @XmlElement(name = "tax")
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money tax;
    
    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    @XmlIDREF
    private List<JobEventXml> jobEventsXml; 
    
    public InvoiceXml (@Nonnull final Invoice invoice)
      {
        final var builder = invoice.toBuilder();
        this.id = builder.getId();
        this.number = builder.getNumber();
        this.projectXml = new ProjectXml(builder.getProject());
        this.date = builder.getDate();
        this.dueDate = builder.getDueDate();
        this.earnings = builder.getEarnings();
        this.tax = builder.getTax();
        this.jobEventsXml = builder.getJobEvents().isEmpty() 
                    ? null
                    : builder.getJobEvents().stream().map(JobEventXml::new).collect(toList());
      }

    @Nonnull
    public Invoice.Builder toBuilder (@Nonnull final Accounting accounting)
      {
        try 
          {
            final var customer = accounting.getProjectRegistry().findProjects().withId(projectXml.getId()).result();
            return new Invoice.Builder().withId(id)
                                        .withNumber(number)
                                        .withProject(customer)
                                        .withDate(date)
                                        .withDueDate(dueDate)
                                        .withEarnings(earnings)
                                        .withTax(tax)
                                        .withJobEvents(JobEventXml.toJobEvents(jobEventsXml));
          } 
        catch (NotFoundException e) 
          {
            throw new RuntimeException(e);
          }
      }
  }
