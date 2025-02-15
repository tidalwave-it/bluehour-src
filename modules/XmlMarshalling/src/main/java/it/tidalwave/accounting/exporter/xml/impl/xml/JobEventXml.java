/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2025 by Tidalwave s.a.s. (http://tidalwave.it)
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

import jakarta.annotation.Nonnull;
// import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import it.tidalwave.accounting.exporter.xml.impl.adapters.EventTypeAdapter;
import it.tidalwave.accounting.exporter.xml.impl.adapters.IdAdapter;
import it.tidalwave.accounting.exporter.xml.impl.adapters.LocalDateTimeAdapter;
import it.tidalwave.accounting.exporter.xml.impl.adapters.MoneyAdapter;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.Id;
import lombok.NoArgsConstructor;
import lombok.ToString;
import static javax.xml.bind.annotation.XmlAccessOrder.ALPHABETICAL;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static java.util.stream.Collectors.*;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
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
    private JobEvent.Type type;
    
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
    
    @XmlElement(name = "hourlyRate")
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money hourlyRate;
    
    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    private List<JobEventXml> jobEventsXml; 
    
    public JobEventXml (@Nonnull final JobEvent jobEvent)
      {
        final var builder = jobEvent.toBuilder();
        this.id = builder.getId();
        this.type = builder.getType();
        this.startDateTime = builder.getStartDateTime();
        this.endDateTime = builder.getEndDateTime();
        this.name = builder.getName();
        this.description = builder.getDescription();
        this.earnings = builder.getEarnings();
        this.hourlyRate = builder.getHourlyRate();
        this.jobEventsXml = builder.getEvents().isEmpty() 
                    ? null
                    : builder.getEvents().stream().map(JobEventXml::new).collect(toList());
      }
    
    @Nonnull
    public JobEvent.Builder toBuilder()
      {
        return JobEvent.builder().withId(id)
                                 .withType(type)
                                 .withStartDateTime(startDateTime)
                                 .withEndDateTime(endDateTime)
                                 .withName(name)
                                 .withDescription(description)
                                 .withEarnings(earnings)
                                 .withHourlyRate(hourlyRate)
                                 .withEvents(toJobEvents(jobEventsXml));
      }
    
    @Nonnull
    public static List<JobEvent> toJobEvents (/* @Nullable */ final List<? extends JobEventXml> jobEventsXml)
      {
        return (jobEventsXml == null) 
                ? Collections.emptyList()
                : jobEventsXml.stream().map(jobEventXml ->  jobEventXml.toBuilder().create()).collect(toList());
      }    
  }
