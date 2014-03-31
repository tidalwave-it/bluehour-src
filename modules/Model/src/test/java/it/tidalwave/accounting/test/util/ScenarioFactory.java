/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.test.util;

import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Address;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Money;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.TimedJobEvent;
import it.tidalwave.accounting.model.impl.DefaultAccounting;
import it.tidalwave.util.Id;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@NoArgsConstructor(access = AccessLevel.PRIVATE) @Slf4j
public final class ScenarioFactory 
  {
    private static int nextId = 1;
    
    public static void main (String ... args) 
      {
        createEmptyAccounting();
        createScenario1();
      }
    
    @Nonnull
    public static Accounting createEmptyAccounting()
      {
        final Accounting accounting = new DefaultAccounting();
        return accounting;
      }
    
    @Nonnull
    public static Accounting createScenario1()
      {
        nextId = 1;
        
        final Accounting accounting = new DefaultAccounting();
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
        
        final List<JobEvent> je1 = createJobEvents(project1StartDate, project1EndDate, new Money(120, "EUR"));
        final List<JobEvent> je2 = createJobEvents(project2StartDate, project2EndDate, new Money(150, "EUR"));
        final List<JobEvent> je3 = createJobEvents(project3StartDate, project3EndDate, new Money(210, "EUR"));
        final List<JobEvent> je4 = createJobEvents(project4StartDate, project4EndDate, new Money(240, "EUR"));
        
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
                    .withEvents(je3)
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
                    .withEvents(je4)
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
                                         .withType(JobEvent.Builder.Type.TIMED)
                                         .withEarnings(new Money(earnings, "EUR"))
                                         .withStartDateTime(s)
                                         .withEndDateTime(e)
                                         .withRate(rate)
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
            List<TimedJobEvent> timedEventsSubList = temp;
            final TimedJobEvent lastEvent = timedEventsSubList.get(eventsSubList.size() - 1);
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
