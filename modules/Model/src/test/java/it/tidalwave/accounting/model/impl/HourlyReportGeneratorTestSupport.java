/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2024 Tidalwave s.a.s. (http://tidalwave.it)
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
import it.tidalwave.accounting.model.spi.ProjectSpi;
import it.tidalwave.accounting.test.util.ScenarioFactory;
import org.testng.annotations.Test;
import static it.tidalwave.util.test.FileComparisonUtils.assertSameContents;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public class HourlyReportGeneratorTestSupport // Can't work here, no implementations; subclassed
  {
    @Test(dataProvider = "projects", dataProviderClass = ScenarioFactory.class)
    public void must_properly_generate_report (@Nonnull final String scenarioName, @Nonnull final ProjectSpi project)
      throws IOException
      {
        final var expectedResultsFolder = Path.of("../Model/src/test/resources/expected-results");
        final var testFolder = Path.of("target/test-results");
        Files.createDirectories(testFolder);

        final var name = scenarioName + "-" + project.getName() + ".txt";
        final var actualResult = testFolder.resolve(name);
        final var expectedResult = expectedResultsFolder.resolve(name);

        final var report = new DefaultHourlyReportGenerator(project).createReport();

        try (final var pw = new PrintWriter(Files.newBufferedWriter(actualResult)))
          {
            pw.print(report.asString());
          }

        assertSameContents(expectedResult, actualResult);
      }
  }
