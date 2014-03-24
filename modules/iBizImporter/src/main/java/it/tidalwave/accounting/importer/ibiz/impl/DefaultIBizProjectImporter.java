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
package it.tidalwave.accounting.importer.ibiz.impl;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import it.tidalwave.util.Id;
import it.tidalwave.util.NotFoundException;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Money;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.importer.ibiz.spi.IBizProjectImporter;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j @RequiredArgsConstructor
public class DefaultIBizProjectImporter implements IBizProjectImporter
  {
    @Nonnull
    private final CustomerRegistry customerRegistry;

    @Nonnull
    private final ProjectRegistry projectRegistry;

    @Nonnull
    private final Path path;

    /*******************************************************************************************************************
     *
     * @throws  IOException  in case of error
     * 
     ******************************************************************************************************************/
    @Override @Nonnull
    public void run()
      throws IOException
      {
        try
          {
            final ConfigurationDecorator config = IBizUtils.loadConfiguration(path);
            importProject(config).withEvents(importJobEvents(config.getList("jobEvents"))).create();
          }
        catch (ConfigurationException | NotFoundException e)
          {
            throw new IOException(e);
          }
      }

    /*******************************************************************************************************************
     *
     * Imports a project from the given configuration object.
     * 
     * @param  projectConfig  the configuration object
     *
     ******************************************************************************************************************/
    @Nonnull
    private Project.Builder importProject (final @Nonnull ConfigurationDecorator projectConfig)
      throws NotFoundException
      {
        final Id customerId = projectConfig.getId("clientIdentifier");
        final Customer customer = customerRegistry.findCustomers().withId(customerId).result();
        return projectRegistry.addProject().withId(projectConfig.getId("uniqueIdentifier"))
                                           .withAmount(projectConfig.getMoney("projectEstimate"))
                                           .withCustomer(customer)
                                           .withName(projectConfig.getString("projectName"))
//                                           .withDescription("description of project 1")
                                           .withStartDate(projectConfig.getDate("projectStartDate"))
                                           .withEndDate(projectConfig.getDate("projectDueDate"))
                                           .withNotes(projectConfig.getString("projectNotes"))
                                           .withNumber(projectConfig.getString("projectNumber"))
                                           .withHourlyRate(projectConfig.getMoney("projectRate"));
      }

    /*******************************************************************************************************************
     *
     * Imports the job events.
     *
     ******************************************************************************************************************/
    @Nonnull
    private List<JobEvent> importJobEvents (final @Nonnull List<Object> jobEvents)
      throws ConfigurationException
      {
        final List<JobEvent> result = new ArrayList<>();

        for (final Object j : jobEvents)
          {
            final Configuration jobEvent = (Configuration)j;
            result.add(importJobEvent(jobEvent));
          }

        return result;
      }

    /*******************************************************************************************************************
     *
     * Imports a single job event.
     *
     ******************************************************************************************************************/
    @Nonnull
    private JobEvent importJobEvent (final @Nonnull Configuration jobEvent2)
      throws ConfigurationException
      {
        final ConfigurationDecorator jobEvent = new ConfigurationDecorator(jobEvent2);

//        log.debug(">>>> properties: {}", toList(jobEvent.getKeys()));

//        final boolean checkedOut = jobEvent.getBoolean("checkedout");
//        final boolean isExpense = jobEvent.getBoolean("isExpense");
//        final boolean nonBillable = jobEvent.getBoolean("nonBillable");
//        final int taxable = jobEvent.getInt("taxable");
        final Money earnings = jobEvent.getMoney("jobEventEarnings");
        final LocalDateTime startDate = jobEvent.getDateTime("jobEventStartDate");
        final LocalDateTime endDate = jobEvent.getDateTime("jobEventEndDate");
//        final DateTime lastModifiedDate = jobEvent.getDateTime("lastModifiedDate");
        final String name =jobEvent.getString("jobEventName");
        final String notes = jobEvent.getString("jobEventNotes");
//        final int paid = jobEvent.getInt("jobEventPaid");
        final IBizJobEventType type = IBizJobEventType.values()[jobEvent.getInt("jobEventType")];
        final Money rate = jobEvent.getMoney("jobEventRate");

        return JobEvent.builder().withType(type.getMappedType())
                                         .withStartDateTime(startDate)
                                         .withEndDateTime(endDate)
                                         .withName(name)
                                         .withDescription(notes)
                                         .withRate(rate)
                                         .withEarnings(earnings)
                                         .withEvents(importJobEvents(jobEvent.getList("children")))
                                         .create();
        /*
                                        <key>tax1</key>
                                        <real>22</real>
                                        <key>uniqueIdentifier</key>
                                        <string>E4EA6321-75FE-45A9-AB1F-CB456E918293</string>

         */
      }
  }
