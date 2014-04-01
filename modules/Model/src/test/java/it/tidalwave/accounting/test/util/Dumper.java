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

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;
import java.io.IOException;
import java.io.PrintWriter;
import it.tidalwave.util.FinderStream;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.util.spi.AsSupport;
import lombok.RequiredArgsConstructor;
import static java.util.Comparator.comparing;
import java.util.function.Predicate;
import static java.util.stream.Collectors.joining;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class Dumper 
  {
    private final static Comparator<? super Project> PROJECT_COMPARATOR = (Project p1, Project p2) -> 
      {
        int n = p1.getName().compareTo(p2.getName());
        
        if (n == 0)
          {
            n = p1.getStartDate().compareTo(p2.getStartDate());
          }
        
        return n;
      };
    
    private static final String INDENT = "    ";
    
    @Nonnull
    private final Accounting accounting;

    @Nonnull
    private final PrintWriter pw;
    
    public void dumpAll()
      throws IOException
      {
        dumpCustomers(accounting.getCustomerRegistry().findCustomers());
        dumpProjects(accounting.getProjectRegistry().findProjects());
        dumpInvoices(accounting.getInvoiceRegistry().findInvoices());
      }
    
    private void dumpCustomers (final @Nonnull FinderStream<Customer> customers)
      {
        customers.sorted(comparing(Customer::getName)).forEach(customer -> pw.printf("%s\n", toString(customer)));
      }
    
    private void dumpInvoices (final @Nonnull FinderStream<Invoice> invoices)
      throws IOException
      {
        invoices.sorted(comparing(Invoice::getNumber)).forEach(invoice -> dump(invoice));
      }
    
    private void dumpProjects (final @Nonnull FinderStream<Project> projects)
      throws IOException
      {
        projects.sorted(PROJECT_COMPARATOR).forEach(project -> dump(project));
      }
    
    private void dump (final @Nonnull Project project)
      {
        pw.printf("%s\n", toString(project));
        dump(project.findChildren(), INDENT);
      }
    
    private void dump (final @Nonnull Invoice invoice)
      {
        pw.printf("%s\n", toString(invoice));
        dump(invoice.findJobEvents(), INDENT); 
      }

    private void dump (final @Nonnull FinderStream<JobEvent> events, final @Nonnull String prefix)
      {
        events.forEach((event) -> dump(event, prefix));
      }

    private void dump (final @Nonnull JobEvent event, final @Nonnull String prefix)
      {
        pw.printf("%s%s\n", prefix, toString(event));

        if (event instanceof JobEventGroup)
          {
            dump(((JobEventGroup)event).findChildren(), prefix + INDENT);
          }
      }
    
    @Nonnull
    public static String toString (final @Nonnull Object event)
      {
        final String s = Stream.concat(Arrays.asList(event.getClass().getDeclaredFields()).stream(),
                                       Arrays.asList(event.getClass().getSuperclass().getDeclaredFields()).stream())
                                        .sorted(comparing(Field::getName))
                                        .filter(excludeUnwantedFields)
                                        .peek(field -> field.setAccessible(true))
                                        .map(field -> field.getName() + "=" + safeGet(field, event))
                                        .collect(joining(", "));

        return String.format("%s(%s)", event.getClass().getSimpleName(), s);
      }
    
    private final static Predicate<? super Field> excludeUnwantedFields = field ->
      {
        final Class<?> type = field.getType();
        return !Collection.class.isAssignableFrom(type)
               && !Accounting.class.isAssignableFrom(type) 
               && !AsSupport.class.isAssignableFrom(type);
      };
    
    @CheckForNull
    private static Object safeGet (final @Nonnull Field field, final @Nonnull Object object)
      {
        try
          {
            Object value = field.get(object);
            
            if (value instanceof Customer)
              {
                value = ((Customer)value).getName();
              }
            else if (value instanceof Project)
              {
                value = ((Project)value).getName();
              }
            
            return value;
          }
        catch (IllegalArgumentException | IllegalAccessException e)
          {
            throw new RuntimeException(e);
          }
      }
  }
