/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2024 by Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.exporter.xml.impl;

import javax.annotation.Nonnull;
import java.nio.file.Files;
import java.nio.file.Path;
import it.tidalwave.accounting.importer.ibiz.impl.DefaultIBizImporter;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.test.util.ScenarioFactory;
import it.tidalwave.role.spi.SystemRoleFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static it.tidalwave.util.test.FileComparisonUtils.assertSameContents;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public class AccountingXmlMarshallableTest
  {
    @BeforeMethod
    public void installEmptyAsSupport()
      {
        SystemRoleFactory.reset();
      }
    
    @Test
    public void consistencyTest()
      {
        ScenarioFactory.createScenarios();
      }
    
    @Test(enabled = false)
    public void must_properly_marshall_iBiz()
      throws Exception
      {
        final var iBizFolder = Path.of("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private/iBiz"); // FIXME
//        final Path iBizFolder = Path.of("/Users/fritz/Settings/iBiz"); // FIXME
        
        if (Files.exists(iBizFolder))
          {
            final var
                    expectedResultsFolder = Path.of("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private");
            final var testFolder = Path.of("target/test-results");
            Files.createDirectories(testFolder);
            final var actualResult = testFolder.resolve("iBizImportMarshalled.xml");
            final var expectedResult = expectedResultsFolder.resolve("iBizImportMarshalled.xml");

            final var importer = DefaultIBizImporter.builder()
                                                    .withPath(iBizFolder)
                                                    .create();
            importer.importAll();

            final var fixture = new AccountingXmlMarshallable(importer);

            try (final var os = Files.newOutputStream(actualResult))
              {
                fixture.marshal(os);
              }

            assertSameContents(expectedResult, actualResult);
          }
      }
    
    @Test(dataProvider = "accountings", dataProviderClass = ScenarioFactory.class)
    public void must_properly_marshall (@Nonnull final String scenarioName, @Nonnull final Accounting scenario)
      throws Exception
      {
        final var expectedResultsFolder = Path.of("src/test/resources/expected-results");
        final var testFolder = Path.of("target/test-results");
        Files.createDirectories(testFolder);
        final var actualResult = testFolder.resolve(scenarioName + ".xml");
        final var expectedResult = expectedResultsFolder.resolve(scenarioName + ".xml");

        final var fixture = new AccountingXmlMarshallable(scenario);

        try (final var os = Files.newOutputStream(actualResult))
          {
            fixture.marshal(os);
          }
        
        assertSameContents(expectedResult, actualResult);
      }
  }
