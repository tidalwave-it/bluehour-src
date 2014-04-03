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
package it.tidalwave.accounting.test.util;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.AsDelegateProvider;
import it.tidalwave.util.spi.EmptyAsDelegateProvider;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.types.Address;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.accounting.model.spi.TimedJobEventSpi;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public final class ScenarioFactory 
  {
    private static int nextId = 1;
    
    @Test
    public void consistencyTest()
      {
        createScenarios();
      }
    
    @Nonnull
    public static Map<String, Accounting> createScenarios()
      {
        final Map<String, Accounting> map = new HashMap<>();
        map.put("Empty", createEmptyAccounting());
        map.put("Scenario1", createScenario1());
        return map;
      }
    
    @DataProvider(name = "projects")
    public static Object[][] projectProvider()
      {
        return ScenarioFactory.createScenarios().entrySet().stream()
                .flatMap(entry -> entry.getValue().getProjectRegistry().findProjects()
                        .map(project -> new Object[] { entry.getKey(), project }))
                .collect(Collectors.toList())
                .toArray(new Object[0][0]); 
      }
    
    @DataProvider(name = "accountings")
    public static Object[][] accountingProvider()
      {
        return ScenarioFactory.createScenarios().entrySet().stream()
                .map(entry -> new Object[] { entry.getKey(), entry.getValue() })
                .collect(Collectors.toList())
                .toArray(new Object[0][0]); 
      }
    
    @Nonnull
    public static Accounting createEmptyAccounting()
      {
        AsDelegateProvider.Locator.set(new EmptyAsDelegateProvider());
        final Accounting accounting = Accounting.createNew();
        return accounting;
      }
    
    @Nonnull
    public static Accounting createScenario1()
      {
        AsDelegateProvider.Locator.set(new EmptyAsDelegateProvider());
        nextId = 1;
        
        final Accounting accounting = Accounting.createNew();
        final CustomerRegistry customerRegistry = accounting.getCustomerRegistry();
        final ProjectRegistry projectRegistry = accounting.getProjectRegistry();
        final InvoiceRegistry invoiceRegistry = accounting.getInvoiceRegistry();
        
        final Customer acmeConsulting = 
                customerRegistry.addCustomer()
                    .withId(new Id("" + nextId++))
                    .withName("ACME Consulting")
                    .withBillingAddress(Address.builder().withStreet("Corso Italia 10")
                                                         .withCity("Genova")
                                                         .withState("GE") 
                                                         .withZip("16145")
                                                         .withCountry("Italy")
                                                         .create())
                    .withVatNumber("IT6546034963")
                    .create();
        final Customer acmeFinancing = 
                customerRegistry.addCustomer()
                    .withId(new Id("" + nextId++))
                    .withName("ACME Financing")
                    .withBillingAddress(Address.builder().withStreet("Corso Magenta 20")
                                                         .withCity("Milano")
                                                         .withState("MI") 
                                                         .withZip("20100")
                                                         .withCountry("Italy")
                                                         .create())
                    .withVatNumber("IT3465346092")
                  .  create();
        
        final LocalDate project1StartDate = LocalDate.parse("2013-04-02");
        final LocalDate project1EndDate = LocalDate.parse("2013-07-04");
        final LocalDate project2StartDate = LocalDate.parse("2013-05-03");
        final LocalDate project2EndDate = LocalDate.parse("2013-09-11");
        final LocalDate project3StartDate = LocalDate.parse("2014-01-22");
        final LocalDate project3EndDate = LocalDate.parse("2014-03-10");
        final LocalDate project4StartDate = LocalDate.parse("2014-02-17");
        final LocalDate project4EndDate = LocalDate.parse("2014-06-21");
        
        final Money rate1 = new Money(120, "EUR");
        final Money rate2 = new Money(150, "EUR");
        final Money rate3 = new Money(210, "EUR");
        final Money rate4 = new Money(240, "EUR");
        
        final List<JobEvent> je1 = createJobEvents(project1StartDate, project1EndDate, rate1);
        final List<JobEvent> je2 = createJobEvents(project2StartDate, project2EndDate, rate2);
        final List<JobEvent> je3 = createJobEvents(project3StartDate, project3EndDate, rate3);
        final List<JobEvent> je4 = createJobEvents(project4StartDate, project4EndDate, rate4);
        
        final Project acmeConsultingProject1 =
                projectRegistry.addProject()
                    .withId(new Id("" + nextId++))
                    .withCustomer(acmeConsulting)
                    .withDescription("Acme Consulting Project 1")
                    .withName("Acme Consulting Project 1")
                    .withStartDate(project1StartDate)
                    .withEndDate(project1EndDate)
                    .withNumber("PRJ ACME-1")
                    .withEvents(je1)
                    .withHourlyRate(rate1)
                    .withBudget(new Money(123456, "EUR"))
                    .create();
        final Project acmeConsultingProject2 =
                projectRegistry.addProject()
                    .withId(new Id("" + nextId++))
                    .withCustomer(acmeConsulting)
                    .withDescription("Acme Consulting Project 2")
                    .withName("Acme Consulting Project 2")
                    .withStartDate(project2StartDate)
                    .withEndDate(project2EndDate)
                    .withNumber("PRJ ACME-2")
                    .withEvents(je2)
                    .withHourlyRate(rate2)
                    .withBudget(new Money(234567, "EUR"))
                    .create();
        final Project acmeFinancingProject1 =
                projectRegistry.addProject()
                    .withId(new Id("" + nextId++))
                    .withCustomer(acmeFinancing)
                    .withDescription("Acme Financing Project 1")
                    .withName("Acme Financing Project 1")
                    .withStartDate(project3StartDate)
                    .withEndDate(project3EndDate)
                    .withNumber("PRJ ACME-3")
                    .withHourlyRate(rate3)
                    .withEvents(je3)
                    .withBudget(new Money(345678, "EUR"))
                    .create();
        final Project acmeFinancingProject2 =
                projectRegistry.addProject()
                    .withId(new Id("" + nextId++))
                    .withCustomer(acmeFinancing)
                    .withDescription("Acme Financing Project 2")
                    .withName("Acme Financing Project 2")
                    .withStartDate(project4StartDate)
                    .withEndDate(project4EndDate)
                    .withNumber("PRJ ACME-4")
                    .withHourlyRate(rate4)
                    .withEvents(je4)
                    .withBudget(new Money(456789, "EUR"))
                    .create();
        
                
        createInvoices(invoiceRegistry, acmeConsultingProject1, 3, "ACP1-");
        createInvoices(invoiceRegistry, acmeConsultingProject2, 4, "ACP2-");
        createInvoices(invoiceRegistry, acmeFinancingProject1, 2,  "ACP3-");
        createInvoices(invoiceRegistry, acmeFinancingProject2, 6,  "ACP4-");
                
        return accounting;
      }
    
    @Nonnull
    private static List<JobEvent> createJobEvents (final @Nonnull LocalDate startDate,
                                                   final @Nonnull LocalDate endDate,
                                                   final @Nonnull Money rate)
      {
        final List<JobEvent> result = new ArrayList<>();
        final long days = startDate.until(endDate, ChronoUnit.DAYS);
        
        for (int i = 1; i <= days; i++)
          {
            final LocalDateTime s = startDate.plusDays(i - 1).atTime(8, 0).plusMinutes(i * 3);
            final LocalDateTime e = s.plusMinutes(60 + i * 4);
            final double hours = s.until(e, ChronoUnit.MINUTES) / 60.0;
            final BigDecimal earnings = rate.getAmount().multiply(new BigDecimal(hours)); 
            result.add(JobEvent.builder().withId(new Id("" + nextId++))
                                         .withName("Event #" + i)
                                         .withDescription("Description of Event #" + i)
                                         .withType(JobEvent.Type.TIMED)
                                         .withEarnings(new Money(earnings, "EUR"))
                                         .withStartDateTime(s)
                                         .withEndDateTime(e)
                                         .withHourlyRate(rate)
                                         .create());
          }
        
        return result;
      }

    private static void createInvoices (final @Nonnull InvoiceRegistry invoiceRegistry,
                                        final @Nonnull Project project,
                                        final @Nonnegative int invoiceCount,
                                        final @Nonnull String prefix)
      {
        List<? extends JobEvent> jobEvents = project.findChildren().results();
        int x = jobEvents.size() / (invoiceCount + 1);
        
//        log.info("jobEvents: {} - invoiceCount: {}", jobEvents.size(), invoiceCount);
        
        for (int i = 0; i < invoiceCount; i++)
          {
            List<JobEvent> eventsSubList = new ArrayList<>(jobEvents.subList(i * x, i * x + x));
            // FIXME: hack!
            List temp = eventsSubList;
            List<TimedJobEventSpi> timedEventsSubList = temp;
            final TimedJobEventSpi lastEvent = timedEventsSubList.get(eventsSubList.size() - 1);
            final LocalDate lastDate = lastEvent.getStartDateTime().toLocalDate();
            final double earnings = timedEventsSubList.stream()
                    .collect(Collectors.summingDouble(ev -> ev.getEarnings().getAmount().doubleValue()));
            final double taxRate = 0.20d;
            
            invoiceRegistry.addInvoice().withId(new Id("" + nextId++))
                                        .withDate(lastDate)
                                        .withDueDate(lastDate.plusMonths(2))
                                        .withEarnings(new Money(new BigDecimal(earnings), "EUR"))
                                        .withJobEvents(eventsSubList)
                                        .withNumber(prefix + (i + 1))
                                        .withProject(project)
                                        .withTax(new Money(new BigDecimal(earnings * taxRate), "EUR"))
                                        .create();
          }
      }
  }
