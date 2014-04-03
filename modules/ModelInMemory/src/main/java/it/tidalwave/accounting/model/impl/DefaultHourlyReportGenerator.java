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
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.accounting.model.HourlyReportGenerator;
import it.tidalwave.accounting.model.HourlyReport;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.model.spi.JobEventSpi;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = ProjectSpi.class) @RequiredArgsConstructor
public class DefaultHourlyReportGenerator implements HourlyReportGenerator
  {
    private static final String SEPARATOR = "===========================================================================";
    
    private static final String PATTERN = "| %-12s | %-30s | %-8s | %-12s |\n";
    private static final String PATTERN2 = "| %12s | %-30s | %8s | %12s |\n";
    private static final String PATTERN3 = "  %12s   %-30s   %8s   %12s  \n";
    
    @Nonnull
    private final ProjectSpi project;
    
    @Override @Nonnull
    public HourlyReport createReport()
      {
        final StringWriter sw = new StringWriter();
        makeReport(sw);
        return new HourlyReport(sw.toString());
      }
    
    private void makeReport (final @Nonnull Writer w) 
      {
        final PrintWriter pw = new PrintWriter(w);
        System.err.println("CREATE REPORT " + project);
        final List<JobEventSpi> jobEvents = new ArrayList<>();
        addAll(jobEvents, project.findChildren().results());
        
        // TODO: quick and dirty - refactor with visitor, closures
        final List<JobEventSpi> r = jobEvents.stream().sorted(Comparator.comparing(JobEvent::getDateTime))
                                                      .collect(Collectors.toList());
        
        pw.printf(SEPARATOR + "\n");
        pw.printf(PATTERN, "Date", "Description", "Time", "Cost");
        pw.printf(SEPARATOR + "\n");
        r.forEach(e -> pw.printf(PATTERN2, DF.format(e.getDateTime()),
                                          e.getName(),
                                          DUF.format(e.getDuration()),
                                          MF.format(e.getEarnings())));
        pw.printf(SEPARATOR + "\n");
        pw.printf(PATTERN3, "", "", DUF.format(project.getDuration()), MF.format(project.getEarnings()));
        
        // FIXME: rename getAmount() -> getBudget()
        // FIXME: introduce getBudgetDuration()
        final Duration duration = Duration.ofHours((long)project.getBudget().divided(project.getHourlyRate()));
        pw.printf("BUDGET:           %s\n", MF.format(project.getBudget()));
        pw.printf("HOURLY RATE:      %s\n", MF.format(project.getHourlyRate()));
        pw.printf("DURATION:         %s\n", DUF.format(duration));
        pw.printf("REMAINING BUDGET: %s\n", MF.format(project.getBudget().subtract(project.getEarnings())));
        pw.printf("REMAINING TIME:   %s\n", DUF.format(duration.minus(project.getDuration())));
        pw.flush();
      }
    
    private void addAll (final @Nonnull List<JobEventSpi> results,
                         final @Nonnull List<? extends JobEvent> jobEvents) 
      {
        for (final JobEvent jobEvent : jobEvents)
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
