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
import java.util.concurrent.atomic.AtomicReference;
import java.time.LocalDateTime;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
     * Imports the projects.
     *
     ******************************************************************************************************************/
    @Override
    public void importProjects()
      throws IOException
      {
        final Path projectsPath = path.resolve("Projects");
        final AtomicReference<IOException> exception = new AtomicReference<>();

        Files.walkFileTree(projectsPath, new SimpleFileVisitor<Path>()
          {
            @Override
            public FileVisitResult visitFile (final @Nonnull Path file, final @Nonnull BasicFileAttributes attrs)
              throws IOException
              {
                importProject(file);
                return FileVisitResult.CONTINUE;
              }

            @Override
            public FileVisitResult visitFileFailed (final @Nonnull Path file, final @Nonnull IOException e)
              {
                exception.set(new IOException("Fatal error visiting " + file));
                return FileVisitResult.TERMINATE;
              }
          });
        
        final IOException e = exception.get();

        if (e != null)
          {
            throw e;
          }
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
            result.add(importJobEvent(new ConfigurationDecorator(jobEvent)));
          }

        return result;
      }

    /*******************************************************************************************************************
     *
     * Imports a single job event.
     *
     ******************************************************************************************************************/
    @Nonnull
    private JobEvent importJobEvent (final @Nonnull ConfigurationDecorator jobEvent)
      throws ConfigurationException
      {
//        log.debug(">>>> properties: {}", toList(jobEvent.getKeys()));

//        final boolean checkedOut = jobEvent.getBoolean("checkedout");
//        final boolean isExpense = jobEvent.getBoolean("isExpense");
//        final boolean nonBillable = jobEvent.getBoolean("nonBillable");
//        final int taxable = jobEvent.getInt("taxable");
//        final DateTime lastModifiedDate = jobEvent.getDateTime("lastModifiedDate");
//        final int paid = jobEvent.getInt("jobEventPaid");
        final IBizJobEventType type = IBizJobEventType.values()[jobEvent.getInt("jobEventType")];

        return JobEvent.builder().withType(type.getMappedType())
                                          .withStartDateTime(jobEvent.getDateTime("jobEventStartDate"))
                                          .withEndDateTime(jobEvent.getDateTime("jobEventEndDate"))
                                          .withName(jobEvent.getString("jobEventName"))
                                          .withDescription(jobEvent.getString("jobEventNotes"))
                                          .withRate(jobEvent.getMoney("jobEventRate"))
                                          .withEarnings(jobEvent.getMoney("jobEventEarnings"))
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
