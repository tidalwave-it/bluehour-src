/*
 *
 * PROJECT NAME
 * PROJECT COPYRIGHT
 *
 ***********************************************************************************************************************
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
 ***********************************************************************************************************************
 *
 * WWW: PROJECT URL
 * SCM: PROJECT SCM
 *
 */

package it.tidalwave.accounting.reporting.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.util.test.FileComparisonUtils;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.test.util.ScenarioFactory;
import org.testng.annotations.Test;

/**
 *
 * @author fritz
 */
public class HourlyReportGeneratorTest
  {
    @Test
    public void test()
      throws IOException
      {
        final String scenarioName = "Scenario1";
        final Accounting accounting = ScenarioFactory.createScenario1();
        
        final Path expectedResultsFolder = Paths.get("src/test/resources/expected-results");
        final Path testFolder = Paths.get("target/test-results");
        Files.createDirectories(testFolder);
        
        accounting.getProjectRegistry().findProjects().forEach(project -> 
          {
            try
              {
                final String name = scenarioName + "-" + project.getName() + ".txt";
                final Path actualResult = testFolder.resolve(name);
                final Path expectedResult = expectedResultsFolder.resolve(name);
                try (final OutputStream os = new FileOutputStream(actualResult.toFile()))
                  {
                    new HourlyReportGenerator(project).makeReport(os);
                    os.close();
                    FileComparisonUtils.assertSameContents(expectedResult.toFile(), actualResult.toFile());
                  }
              }
            catch (Exception e)
              {
                throw new RuntimeException(e);
              }
          });
        
      }
  }
