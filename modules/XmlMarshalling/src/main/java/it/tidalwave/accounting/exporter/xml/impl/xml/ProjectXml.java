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
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.Money;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.exporter.xml.impl.adapters.MoneyAdapter;
import it.tidalwave.accounting.exporter.xml.impl.adapters.LocalDateAdapter;
import it.tidalwave.accounting.exporter.xml.impl.adapters.IdAdapter;
import lombok.Getter;
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
@XmlRootElement(name = "project") @XmlAccessorType(FIELD) @XmlAccessorOrder(ALPHABETICAL)
public class ProjectXml 
  {
    @Getter // FIXME   
    @XmlAttribute(name = "id")
    @XmlID
    @XmlJavaTypeAdapter(IdAdapter.class)
    private Id id;
    
    @XmlElement(name = "customer")
    @XmlIDREF
    private CustomerXml customerXml;
    
    @XmlElement(name = "name")
    private String name;
    
    @XmlElement(name = "number")
    private String number;
    
    @XmlElement(name = "description")
    private String description;
    
    @XmlElement(name = "notes")
    private String notes;
    
    @XmlElement(name = "hourlyRate")
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money hourlyRate;
    
    @XmlElement(name = "amount")
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money amount;
    
    @XmlElement(name = "startDate")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    
    @XmlElement(name = "endDate")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
    
    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    private List<JobEventXml> jobEventsXml; 
    
    public ProjectXml (final @Nonnull Project project)
      {
        final Project.Builder builder = project.asBuilder();
        this.id = builder.getId();
        this.customerXml = new CustomerXml(builder.getCustomer());
        this.name = builder.getName();
        this.number = builder.getNumber();
        this.description = builder.getDescription();
        this.notes = builder.getNotes();
        this.hourlyRate = builder.getHourlyRate();
        this.amount = builder.getAmount();
        this.startDate = builder.getStartDate();
        this.endDate = builder.getEndDate();
        this.jobEventsXml = project.findChildren().map(jobEvent -> new JobEventXml(jobEvent)).collect(toList());
      }
    
    @Nonnull
    public Project.Builder toBuilder (final @Nonnull Accounting accounting)
      {
        try 
          {
            final Customer customer = accounting.getCustomerRegistry().findCustomers().withId(customerXml.getId()).result();
            return new Project.Builder().withId(id)
                                        .withCustomer(customer) 
                                        .withName(name)
                                        .withNumber(number)
                                        .withDescription(description)
                                        .withNotes(notes)
                                        .withHourlyRate(hourlyRate)
                                        .withAmount(amount)
                                        .withStartDate(startDate)
                                        .withEndDate(endDate)
                                        .withEvents(JobEventXml.toJobEvents(jobEventsXml));
          } 
        catch (NotFoundException e) 
          {
            throw new RuntimeException(e);
          }
      }
  }
