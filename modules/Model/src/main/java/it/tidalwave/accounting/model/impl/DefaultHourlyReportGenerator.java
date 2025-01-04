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
package it.tidalwave.accounting.model.impl;

import jakarta.annotation.Nonnull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import it.tidalwave.accounting.model.HourlyReport;
import it.tidalwave.accounting.model.HourlyReportGenerator;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import it.tidalwave.dci.annotation.DciRole;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;
import static java.util.Comparator.*;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@DciRole(datumType = ProjectSpi.class) @RequiredArgsConstructor
public class DefaultHourlyReportGenerator implements HourlyReportGenerator
  {
    private static final String SEPARATOR = "===========================================================================";

    private static final String PATTERN = "| %-12s | %-30s | %-8s | %-12s |%n";
    private static final String PATTERN2 = "| %12s | %-30s | %8s | %12s |%n";
    private static final String PATTERN3 = "  %12s   %-30s   %8s   %12s  %n";

    @Nonnull
    private final ProjectSpi project;

    @Override @Nonnull
    public HourlyReport createReport()
      {
        final var sw = new StringWriter();
        makeReport(sw);
        return new HourlyReport(sw.toString());
      }

    private void makeReport (@Nonnull final Writer w)
      {
        final var pw = new PrintWriter(w);
        System.err.println("CREATE REPORT " + project);

        pw.print(SEPARATOR + "\n");
        pw.printf(PATTERN, "Date", "Description", "Time", "Cost");
        pw.print(SEPARATOR + "\n");

        // TODO: quick and dirty - refactor with visitor, lambdas
        final List<JobEventSpi> jobEvents = new ArrayList<>();
        addAll(jobEvents, project.findChildren().results());
        jobEvents.stream().sorted(comparing(JobEventSpi::getDateTime))
                          .forEach(event -> pw.printf(PATTERN2, DATE_FORMATTER.format(event.getDateTime()),
                                                                event.getName(),
                                                                DURATION_FORMATTER.format(event.getDuration()),
                                                                MONEY_FORMATTER.format(event.getEarnings())));
        pw.print(SEPARATOR + "\n");
        pw.printf(PATTERN3, "", "", DURATION_FORMATTER.format(project.getDuration()),
                                    MONEY_FORMATTER.format(project.getEarnings()));

        // FIXME: rename getAmount() -> getBudget()
        // FIXME: introduce getBudgetDuration()
        final var duration = Duration.ofHours((long)project.getBudget().divided(project.getHourlyRate()));
        pw.printf("BUDGET:           %s%n", MONEY_FORMATTER.format(project.getBudget()));
        pw.printf("HOURLY RATE:      %s%n", MONEY_FORMATTER.format(project.getHourlyRate()));
        pw.printf("DURATION:         %s%n", DURATION_FORMATTER.format(duration));
        pw.printf("REMAINING BUDGET: %s%n",
                  MONEY_FORMATTER.format(project.getBudget().subtract(project.getEarnings())));
        pw.printf("REMAINING TIME:   %s%n", DURATION_FORMATTER.format(duration.minus(project.getDuration())));
        pw.flush();
      }

    private void addAll (@Nonnull final List<? super JobEventSpi> results,
                         @Nonnull final List<? extends JobEvent> jobEvents)
      {
        for (final var jobEvent : jobEvents)
          {
            if (jobEvent instanceof JobEventGroup)
              {
                addAll(results, ((JobEventGroup)jobEvent).findChildren().results());
              }
            else
              {
                results.add((JobEventSpi)jobEvent);
              }
          }
      }
  }
