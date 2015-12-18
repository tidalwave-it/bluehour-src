/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2015 Tidalwave s.a.s. (http://tidalwave.it)
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
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.util.test.FileComparisonUtils;
import it.tidalwave.accounting.model.HourlyReport;
import it.tidalwave.accounting.model.spi.ProjectSpi;
import it.tidalwave.accounting.test.util.ScenarioFactory;
import org.testng.annotations.Test;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class DefaultHourlyReportGeneratorTest
  {
    @Test(dataProvider = "projects", dataProviderClass = ScenarioFactory.class)
    public void must_properly_generate_report (final @Nonnull String scenarioName, final @Nonnull ProjectSpi project) 
      throws IOException
      {
        final Path expectedResultsFolder = Paths.get("src/test/resources/expected-results");
        final Path testFolder = Paths.get("target/test-results");
        Files.createDirectories(testFolder);

        final String name = scenarioName + "-" + project.getName() + ".txt";
        final Path actualResult = testFolder.resolve(name);
        final Path expectedResult = expectedResultsFolder.resolve(name);
        
        final HourlyReport report = new DefaultHourlyReportGenerator(project).createReport();
        
        try (final PrintWriter pw = new PrintWriter(actualResult.toFile()))
          {
            pw.print(report.asString());
          }

        FileComparisonUtils.assertSameContents(expectedResult.toFile(), actualResult.toFile());
      }
  }
