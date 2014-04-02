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
package it.tidalwave.accounting.reporting.impl;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.OutputStream;
import java.io.PrintWriter;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
import it.tidalwave.accounting.model.Project;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.commons.Formatters.DF;
import static it.tidalwave.accounting.commons.Formatters.DUF;
import static it.tidalwave.accounting.commons.Formatters.MF;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class HourlyReportGenerator 
  {
    @Nonnull
    private final Project project;
    
    public void makeReport (final @Nonnull OutputStream os) 
      {
        final PrintWriter pw = new PrintWriter(os);
        System.err.println("CREATE REPORT " + project);
        final List<JobEvent> jobEvents = new ArrayList<>();
        addAll(jobEvents, project.findChildren().results());
        
        // TODO: quick and dirty - refactor with visitor, closures
        final List<JobEvent> r = jobEvents.stream().sorted(Comparator.comparing(JobEvent::getDateTime))
                                                   .collect(Collectors.toList());
        
        pw.printf("===============================================================\n");
        pw.printf("%14s %-30s %6s  %9s\n", 
                "Date", "Description", "Time", "Cost"
                );
        pw.printf("===============================================================\n");
        r.forEach(e -> 
                pw.printf("%14s %-30s %6s  %9s\n", 
                        DF.format(e.getDateTime()),
                        e.getName(),
                        DUF.format(e.getDuration()),
                        MF.format(e.getEarnings())
                       ));
        pw.printf("===============================================================\n");
        pw.printf("%14s %-30s %6s  %9s\n", 
                "", "", 
                DUF.format(project.getDuration()),
                MF.format(project.getEarnings())
                );
        pw.flush();
      }

    
    private void addAll (final @Nonnull List<JobEvent> results,
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
                results.add(jobEvent);
              }
          }
      }
  }
