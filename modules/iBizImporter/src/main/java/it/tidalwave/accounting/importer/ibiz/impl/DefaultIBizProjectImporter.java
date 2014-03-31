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
import java.util.List;
import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import it.tidalwave.util.Id;
import it.tidalwave.util.NotFoundException;
import it.tidalwave.accounting.importer.ibiz.spi.IBizProjectImporter;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;

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
     * Imports the projects.
     *
     ******************************************************************************************************************/
    @Override
    public void importProjects()
      throws IOException
      {
        Files.walkFileTree(path.resolve("Projects"), new SimpleFileVisitor<Path>()
          {
            @Override
            public FileVisitResult visitFile (final @Nonnull Path file, final @Nonnull BasicFileAttributes attrs)
              throws IOException
              {
                importProject(file);
                return FileVisitResult.CONTINUE;
              }
          });
      }
    
    /*******************************************************************************************************************
     *
     * @throws  IOException  in case of error
     * 
     ******************************************************************************************************************/
    private void importProject (final @Nonnull Path file)
      throws IOException
      {
        try
          {
            final ConfigurationDecorator config = IBizUtils.loadConfiguration(file);
            importProject(config).withEvents(importJobEvents(config.getStream("jobEvents"))).create();
          }
        catch (NotFoundException e)
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
    private List<JobEvent> importJobEvents (final @Nonnull Stream<ConfigurationDecorator> jobEventsConfig)
      {
        return jobEventsConfig.map(jobEventConfig -> importJobEvent(jobEventConfig)).collect(toList());
      }

    /*******************************************************************************************************************
     *
     * Imports a single job event.
     *
     ******************************************************************************************************************/
    @Nonnull
    private JobEvent importJobEvent (final @Nonnull ConfigurationDecorator jobEventConfig)
      {
//        log.debug(">>>> properties: {}", toList(jobEvent.getKeys()));

//        final boolean checkedOut = jobEvent.getBoolean("checkedout");
//        final boolean isExpense = jobEvent.getBoolean("isExpense");
//        final boolean nonBillable = jobEvent.getBoolean("nonBillable");
//        final int taxable = jobEvent.getInt("taxable");
//        final DateTime lastModifiedDate = jobEvent.getDateTime("lastModifiedDate");
//        final int paid = jobEvent.getInt("jobEventPaid");
        final IBizJobEventType type = IBizJobEventType.values()[jobEventConfig.getInt("jobEventType")];

        return JobEvent.builder().withId(jobEventConfig.getId("uniqueIdentifier"))
                                 .withType(type.getMappedType())
                                 .withStartDateTime(jobEventConfig.getDateTime("jobEventStartDate"))
                                 .withEndDateTime(jobEventConfig.getDateTime("jobEventEndDate"))
                                 .withName(jobEventConfig.getString("jobEventName"))
                                 .withDescription(jobEventConfig.getString("jobEventNotes"))
                                 .withRate(jobEventConfig.getMoney("jobEventRate"))
                                 .withEarnings(jobEventConfig.getMoney("jobEventEarnings"))
                                 .withEvents(importJobEvents(jobEventConfig.getStream("children")))
                                 .create();
        /*
                                        <key>tax1</key>
                                        <real>22</real>

         */
      }
  }
