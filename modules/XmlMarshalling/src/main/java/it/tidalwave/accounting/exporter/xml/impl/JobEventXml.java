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
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Money;
import lombok.NoArgsConstructor;
import lombok.ToString;
import static java.util.stream.Collectors.toList;
import static javax.xml.bind.annotation.XmlAccessOrder.ALPHABETICAL;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@NoArgsConstructor @ToString
@XmlRootElement(name = "event") @XmlAccessorType(FIELD) @XmlAccessorOrder(ALPHABETICAL)
public class JobEventXml 
  {
    @XmlAttribute(name = "id") 
    @XmlID
    @XmlJavaTypeAdapter(IdAdapter.class)
    private Id id;
    
    @XmlElement(name = "type")
    @XmlJavaTypeAdapter(EventTypeAdapter.class)
    private JobEvent.Builder.Type type;
    
    @XmlElement(name = "startDateTime")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startDateTime;
    
    @XmlElement(name = "endDateTime")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endDateTime;
    
    @XmlElement(name = "name")
    private String name;
    
    @XmlElement(name = "description")
    private String description;
    
    @XmlElement(name = "earnings")
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money earnings;
    
    @XmlElement(name = "rate")
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money rate;
    
    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    private List<JobEventXml> events; 
    
    public JobEventXml (final @Nonnull JobEvent.Builder builder)
      {
        this.id = builder.getId();
        this.type = builder.getType();
        this.startDateTime = builder.getStartDateTime();
        this.endDateTime = builder.getEndDateTime();
        this.name = builder.getName();
        this.description = builder.getDescription();
        this.earnings = builder.getEarnings();
        this.rate = builder.getRate();
        this.events = builder.getEvents().isEmpty() 
                            ? null
                            : builder.getEvents().stream().map(jobEvent -> new JobEventXml(jobEvent.asBuilder()))
                                                          .collect(toList());
      }
  }
