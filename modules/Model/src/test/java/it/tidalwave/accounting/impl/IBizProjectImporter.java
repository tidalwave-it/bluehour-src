/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - hg clone https://bitbucket.org/tidalwave/bluehour-src
 * %%
 * Copyright (C) 2013 - 2013 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.impl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.joda.time.DateTime;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration;
import it.tidalwave.util.Id;
import it.tidalwave.util.NotFoundException;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Money;
import it.tidalwave.accounting.model.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;
import static lombok.AccessLevel.PRIVATE;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class IBizProjectImporter
  {
    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @AllArgsConstructor(access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        private final Path path;

        private final CustomerRegistry customerRegistry;

        @Nonnull
        public Builder withPath2 (final @Nonnull String path) // FIXME: rename
          {
            return withPath(Paths.get(path));
          }

        @Nonnull
        public IBizProjectImporter create()
          {
            return new IBizProjectImporter(this);
          }
      }

    @Nonnull
    private final Path path;

    @Nonnull
    private final CustomerRegistry customerRegistry;

    private Project project;

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public static IBizProjectImporter.Builder builder()
      {
        return new IBizProjectImporter.Builder(null, null); // FIXME: null
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    protected IBizProjectImporter (final @Nonnull Builder builder)
      {
        this.path = builder.getPath();
        this.customerRegistry = builder.getCustomerRegistry();
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    public Project run()
      throws IOException
      {
        try
          {
            final File file = path.toFile();
            final ConfigurationDecorator c = new ConfigurationDecorator(new XMLPropertyListConfiguration(file));
    //        final ProjectRegistry projectRegistry = new DefaultProjectRegistry();

            project = importProject(c);
            return project.withEvents(importJobEvents(c.getList("jobEvents")));
          }
        catch (ConfigurationException | NotFoundException e)
          {
            throw new IOException(e);
          }
      }

    @Nonnull
    private Project importProject (final @Nonnull ConfigurationDecorator c)
      throws NotFoundException
      {
        final Id customerId = new Id(c.getString("clientIdentifier"));
        final Customer customer = customerRegistry.findCustomer().withId(customerId).result();
        final Project p = Project.builder().withAmount(c.getMoney("projectEstimate"))
                                            .withCustomer(customer)
                                            .withName(c.getString("projectName"))
                                            .withDescription("description of project 1")
                                            .withStartDate(c.getDate("projectStartDate"))
                                            .withEndDate(c.getDate("projectDueDate"))
                                            .withNotes(c.getString("projectNotes"))
                                            .withNumber(c.getString("projectNumber"))
                                            .withHourlyRate(c.getMoney("projectRate"))
                                            .create();
        return p;
/*       <key>lastModifiedDate</key>
        <date>2014-03-10T11:45:22Z</date>
        <key>projectEarnings</key>
        <real>8560</real>
        <key>projectStatus</key>
        <integer>1</integer>
 */
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    private List<JobEvent> importJobEvents (final @Nonnull List<Object> jobEvents)
      throws ConfigurationException
      {
        final List<JobEvent> result = new ArrayList<>();

        for (final Object j : jobEvents)
          {
            final XMLPropertyListConfiguration jobEvent = (XMLPropertyListConfiguration)j;
            result.add(importJobEvent(jobEvent));
          }

        return result;
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    private JobEvent importJobEvent (final @Nonnull XMLPropertyListConfiguration jobEvent2)
      throws ConfigurationException
      {
        final ConfigurationDecorator jobEvent = new ConfigurationDecorator(jobEvent2);

//        log.debug(">>>> properties: {}", toList(jobEvent.getKeys()));

//        final boolean checkedOut = jobEvent.getBoolean("checkedout");
//        final boolean isExpense = jobEvent.getBoolean("isExpense");
//        final boolean nonBillable = jobEvent.getBoolean("nonBillable");
//        final int taxable = jobEvent.getInt("taxable");
        final Money earnings = jobEvent.getMoney("jobEventEarnings");
        final DateTime startDate = jobEvent.getDateTime("jobEventStartDate");
        final DateTime endDate = jobEvent.getDateTime("jobEventEndDate");
//        final DateTime lastModifiedDate = jobEvent.getDateTime("lastModifiedDate");
        final String name =jobEvent.getString("jobEventName");
        final String notes = jobEvent.getString("jobEventNotes");
//        final int paid = jobEvent.getInt("jobEventPaid");
        final ImportTest.IBizJobEventType type = ImportTest.IBizJobEventType.values()[jobEvent.getInt("jobEventType")];
        final Money rate = jobEvent.getMoney("jobEventRate");

        JobEvent event = JobEvent.builder().withProject(project)
                                           .withStartDateTime(startDate)
                                           .withEndDateTime(endDate)
                                           .withName(name)
                                           .withDescription(notes)
                                           .withRate(rate)
                                           .withEarnings(earnings)
                                           .create();
//        log.info("TYPE {}: {}", type, event);
        event = event.withEvents(importJobEvents(jobEvent.getList("children")));

        return event;
        /*
                                        <key>tax1</key>
                                        <real>22</real>
                                        <key>uniqueIdentifier</key>
                                        <string>E4EA6321-75FE-45A9-AB1F-CB456E918293</string>

         */
      }

    /*******************************************************************************************************************
     *
     *
     *
     ******************************************************************************************************************/
    @Nonnull
    private static <T> List<T> toList (final @Nonnull Iterator<T> i)
      {
        final List<T> list = new ArrayList<>();

        while (i.hasNext())
          {
            list.add(i.next());
          }

        return list;
      }
  }
