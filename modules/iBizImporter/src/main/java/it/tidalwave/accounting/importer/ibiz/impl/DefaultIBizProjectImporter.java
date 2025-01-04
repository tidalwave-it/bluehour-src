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
package it.tidalwave.accounting.importer.ibiz.impl;

import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import it.tidalwave.accounting.importer.ibiz.spi.IBizProjectImporter;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.spi.TimedJobEventSpi;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.*;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Slf4j @RequiredArgsConstructor
public class DefaultIBizProjectImporter implements IBizProjectImporter
  {
    @Nonnull
    private final CustomerRegistry customerRegistry;

    @Nonnull
    private final ProjectRegistry projectRegistry;

    @Nonnull
    private final Path path;

    /***********************************************************************************************************************************************************
     * Imports the projects.
     **********************************************************************************************************************************************************/
    @Override
    public void importProjects()
      throws IOException
      {
        log.debug("importProjects()");
        Files.walkFileTree(path.resolve("Projects"), new SimpleFileVisitor<>()
          {
            @Override
            public FileVisitResult visitFile (@Nonnull final Path file, @Nonnull final BasicFileAttributes attrs)
                    throws IOException
              {
                if (!".DS_Store".equals(file.toFile().getName()))
                  {
                    importProject(file);
                  }

                return FileVisitResult.CONTINUE;
              }
          });
      }
    
    /***********************************************************************************************************************************************************
     * @throws  IOException  in case of error
     **********************************************************************************************************************************************************/
    private void importProject (@Nonnull final Path file)
      throws IOException
      {
        try
          {
            importProject(IBizUtils.loadConfiguration(file));
          }
        catch (NotFoundException e)
          {
            throw new IOException(e);
          }
      }

    /***********************************************************************************************************************************************************
     * Imports a project from the given configuration object.
     * 
     * @param  projectConfig  the configuration object
     **********************************************************************************************************************************************************/
    private void importProject (@Nonnull final ConfigurationDecorator projectConfig)
      throws NotFoundException
      {
        final var customerId = projectConfig.getId("clientIdentifier");
        final var customer =
                customerRegistry.findCustomers().withId(customerId).optionalResult().orElseThrow(NotFoundException::new);
        final var status = IBizProjectStatus.values()[projectConfig.getInt("projectStatus")];

        if (status.getMappedStatus() == null)
          {
            log.warn("IGNORING PROJECT {} with status {}", projectConfig.getString("projectName"), status);  
          }
        else
          {
            final var jobEvents = importJobEvents(projectConfig.getStream("jobEvents"));
            projectRegistry.addProject().withId(projectConfig.getId("uniqueIdentifier"))
                                        .withBudget(projectConfig.getMoney("projectEstimate"))
                                        .withCustomer(customer)
                                        .withName(projectConfig.getString("projectName"))
    //                                           .withDescription("description of project 1")
                                        .withStartDate(projectConfig.getDate("projectStartDate"))
                                        .withEndDate(projectConfig.getDate("projectDueDate"))
                                        .withNotes(projectConfig.getString("projectNotes"))
                                        .withNumber(projectConfig.getString("projectNumber"))
                                        .withStatus(status.getMappedStatus())
                                        .withHourlyRate(getHourlyRate(projectConfig, jobEvents))
                                        .withEvents(jobEvents)
                                        .create();
          }
      }

    /***********************************************************************************************************************************************************
     * Retrieves the hourly rates - if missing from the project description, tries to recover it from the first 
     * meaningful job event.
     **********************************************************************************************************************************************************/
    @Nonnull
    private Money getHourlyRate(final ConfigurationDecorator projectConfig, final List<? extends JobEvent> jobEvents)
            throws NotFoundException
      {
        var hourlyRate = projectConfig.getMoney("projectRate");
      
        if ((hourlyRate.compareTo(Money.ZERO) == 0) && !jobEvents.isEmpty())
            // don't use equals() - see http://stackoverflow.com/questions/6787142/bigdecimal-equals-versus-compareto
                                        {
                                          var event = jobEvents.get(0);
            
            while ((event instanceof JobEventGroup) && ((JobEventGroup)event).findChildren().count() > 0)
              {
                event = ((JobEventGroup)event).findChildren().optionalFirstResult().orElseThrow(NotFoundException::new);
              }
            
            if (event instanceof TimedJobEventSpi)
              {
                hourlyRate = ((TimedJobEventSpi)event).getHourlyRate();
              }
          }
        return hourlyRate;
      }

    /***********************************************************************************************************************************************************
     * Imports the job events.
     **********************************************************************************************************************************************************/
    @Nonnull
    private List<JobEvent> importJobEvents (@Nonnull final Stream<? extends ConfigurationDecorator> jobEventsConfig)
      {
        return jobEventsConfig.map(this::importJobEvent).collect(toList());
      }

    /***********************************************************************************************************************************************************
     * Imports a single job event.
     **********************************************************************************************************************************************************/
    @Nonnull
    private JobEvent importJobEvent (@Nonnull final ConfigurationDecorator jobEventConfig)
      {
//        log.debug(">>>> properties: {}", toList(jobEvent.getKeys()));

//        final boolean checkedOut = jobEvent.getBoolean("checkedout");
//        final boolean isExpense = jobEvent.getBoolean("isExpense");
//        final boolean nonBillable = jobEvent.getBoolean("nonBillable");
//        final int taxable = jobEvent.getInt("taxable");
//        final DateTime lastModifiedDate = jobEvent.getDateTime("lastModifiedDate");
//        final int paid = jobEvent.getInt("jobEventPaid");
        final var type = IBizJobEventType.values()[jobEventConfig.getInt("jobEventType")];

        return JobEvent.builder().withId(jobEventConfig.getId("uniqueIdentifier"))
                                 .withType(type.getMappedType())
                                 .withStartDateTime(jobEventConfig.getDateTime("jobEventStartDate"))
                                 .withEndDateTime(jobEventConfig.getDateTime("jobEventEndDate"))
                                 .withName(jobEventConfig.getString("jobEventName"))
                                 .withDescription(jobEventConfig.getString("jobEventNotes"))
                                 .withHourlyRate(jobEventConfig.getMoney("jobEventRate"))
                                 .withEarnings(jobEventConfig.getMoney("jobEventEarnings"))
                                 .withEvents(importJobEvents(jobEventConfig.getStream("children")))
                                 .create();
        /*
                                        <key>tax1</key>
                                        <real>22</real>

         */
      }
  }
