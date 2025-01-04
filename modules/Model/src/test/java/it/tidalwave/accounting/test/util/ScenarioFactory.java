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
package it.tidalwave.accounting.test.util;

// import javax.annotation.Nonnegative;
import jakarta.annotation.Nonnull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.spi.TimedJobEventSpi;
import it.tidalwave.accounting.model.types.Address;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.Id;
import it.tidalwave.role.spi.SystemRoleFactory;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@SuppressWarnings("NewClassNamingConvention") @Slf4j
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
                .flatMap(entry -> entry.getValue().getProjectRegistry().findProjects().stream()
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
        SystemRoleFactory.reset();
        return Accounting.createNew();
      }

    @Nonnull
    public static Accounting createScenario1()
      {
        SystemRoleFactory.reset();
        nextId = 1;

        final var accounting = Accounting.createNew();
        final var customerRegistry = accounting.getCustomerRegistry();
        final var projectRegistry = accounting.getProjectRegistry();
        final var invoiceRegistry = accounting.getInvoiceRegistry();

        final var acmeConsulting =
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
        final var acmeFinancing =
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

        final var project1StartDate = LocalDate.parse("2013-04-02");
        final var project1EndDate = LocalDate.parse("2013-07-04");
        final var project2StartDate = LocalDate.parse("2013-05-03");
        final var project2EndDate = LocalDate.parse("2013-09-11");
        final var project3StartDate = LocalDate.parse("2014-01-22");
        final var project3EndDate = LocalDate.parse("2014-03-10");
        final var project4StartDate = LocalDate.parse("2014-02-17");
        final var project4EndDate = LocalDate.parse("2014-06-21");

        final var rate1 = Money.of(120, "EUR");
        final var rate2 = Money.of(150, "EUR");
        final var rate3 = Money.of(210, "EUR");
        final var rate4 = Money.of(240, "EUR");

        final var je1 = createJobEvents(project1StartDate, project1EndDate, rate1);
        final var je2 = createJobEvents(project2StartDate, project2EndDate, rate2);
        final var je3 = createJobEvents(project3StartDate, project3EndDate, rate3);
        final var je4 = createJobEvents(project4StartDate, project4EndDate, rate4);

        final var acmeConsultingProject1 =
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
                    .withBudget(Money.of(123456, "EUR"))
                    .create();
        final var acmeConsultingProject2 =
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
                    .withBudget(Money.of(234567, "EUR"))
                    .create();
        final var acmeFinancingProject1 =
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
                    .withBudget(Money.of(345678, "EUR"))
                    .create();
        final var acmeFinancingProject2 =
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
                    .withBudget(Money.of(456789, "EUR"))
                    .create();


        createInvoices(invoiceRegistry, acmeConsultingProject1, 3, "ACP1-");
        createInvoices(invoiceRegistry, acmeConsultingProject2, 4, "ACP2-");
        createInvoices(invoiceRegistry, acmeFinancingProject1, 2,  "ACP3-");
        createInvoices(invoiceRegistry, acmeFinancingProject2, 6,  "ACP4-");

        return accounting;
      }

    @Nonnull
    private static List<JobEvent> createJobEvents (@Nonnull final LocalDate startDate,
                                                   @Nonnull final LocalDate endDate,
                                                   @Nonnull final Money rate)
      {
        final List<JobEvent> result = new ArrayList<>();
        final var days = startDate.until(endDate, ChronoUnit.DAYS);

        for (var i = 1; i <= days; i++)
          {
            final var s = startDate.plusDays(i - 1).atTime(8, 0).plusMinutes(i * 3L);
            final var e = s.plusMinutes(60 + i * 4L);
            final var hours = s.until(e, ChronoUnit.MINUTES) / 60.0;
            final var earnings = rate.getAmount().multiply(new BigDecimal(hours));
            result.add(JobEvent.builder().withId(new Id("" + nextId++))
                                         .withName("Event #" + i)
                                         .withDescription("Description of Event #" + i)
                                         .withType(JobEvent.Type.TIMED)
                                         .withEarnings(Money.of(earnings, "EUR"))
                                         .withStartDateTime(s)
                                         .withEndDateTime(e)
                                         .withHourlyRate(rate)
                                         .create());
          }

        return result;
      }

    private static void createInvoices (@Nonnull final InvoiceRegistry invoiceRegistry,
                                        @Nonnull final Project project,
                                        /* @Nonnegative */ final int invoiceCount,
                                        @Nonnull final String prefix)
      {
        final var jobEvents = project.findChildren().results();
        final var x = jobEvents.size() / (invoiceCount + 1);

        for (var i = 0; i < invoiceCount; i++)
          {
            final List<? extends JobEvent> eventsSubList = new ArrayList<>(jobEvents.subList(i * x, i * x + x));
            final var timedEventsSubList = (List<TimedJobEventSpi>)eventsSubList;
            final var lastEvent = timedEventsSubList.get(eventsSubList.size() - 1);
            final var lastDate = lastEvent.getStartDateTime().toLocalDate();
            final var earnings = timedEventsSubList.stream()
                                                   .mapToDouble(ev -> ev.getEarnings().getAmount().doubleValue()).sum();
            final var taxRate = 0.20d;

            invoiceRegistry.addInvoice().withId(new Id("" + nextId++))
                                        .withDate(lastDate)
                                        .withDueDate(lastDate.plusMonths(2))
                                        .withEarnings(Money.of(new BigDecimal(earnings), "EUR"))
                                        .withJobEvents(eventsSubList)
                                        .withNumber(prefix + (i + 1))
                                        .withProject(project)
                                        .withTax(Money.of(new BigDecimal(earnings * taxRate), "EUR"))
                                        .create();
          }
      }
  }
