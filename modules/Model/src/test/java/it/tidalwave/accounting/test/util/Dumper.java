/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.io.IOException;
import java.io.PrintWriter;
import it.tidalwave.util.Finder8;
import it.tidalwave.util.spi.AsSupport;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.Invoice;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.spi.CustomerSpi;
import it.tidalwave.accounting.model.spi.InvoiceSpi;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import lombok.RequiredArgsConstructor;
import static java.util.Comparator.comparing;
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
    
    private void dumpCustomers (final @Nonnull Finder8<Customer> customers)
      {
        customers.stream().map(customer -> (CustomerSpi)customer)
                          .sorted(comparing(CustomerSpi::getName))
                          .forEach(customer -> pw.printf("%s\n", toString(customer)));
      }
    
    private void dumpInvoices (final @Nonnull Finder8<Invoice> invoices)
      throws IOException
      {
        invoices.stream().map(invoice -> (InvoiceSpi)invoice)
                         .sorted(comparing(InvoiceSpi::getNumber))
                         .forEach(invoice -> dump(invoice));
      }
    
    private void dumpProjects (final @Nonnull Finder8<Project> projects)
      throws IOException
      {
        projects.stream().map(project -> (ProjectSpi)project)
                         .sorted(comparing(ProjectSpi::getName).thenComparing(ProjectSpi::getStartDate))
                         .forEach(project -> dump(project));
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

    private void dump (final @Nonnull Finder8<JobEvent> events, final @Nonnull String prefix)
      {
        events.stream().forEach(event -> dump(event, prefix));
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
        
        String className = "?";
        
        Class<?>[] interfaces = event.getClass().getInterfaces();
        
        if (interfaces.length > 0) // assumes the business interface is the first
          {
            className = interfaces[0].getSimpleName();
          }
        else // FIXME
          {
            className = event.getClass().getSimpleName().replaceFirst("^InMemory", "");
          }
        
        className = className.replaceAll("Spi$", "");
        return String.format("%s(%s)", className, s);
      }
    
    private final static Predicate<? super Field> excludeUnwantedFields = field ->
      {
        final Class<?> type = field.getType();
        return !Modifier.isStatic(field.getModifiers())
               && !Collection.class.isAssignableFrom(type)
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
                value = ((CustomerSpi)value).getName();
              }
            else if (value instanceof Project)
              {
                value = ((ProjectSpi)value).getName();
              }
            
            return value;
          }
        catch (IllegalArgumentException | IllegalAccessException e)
          {
            throw new RuntimeException(e);
          }
      }
  }
