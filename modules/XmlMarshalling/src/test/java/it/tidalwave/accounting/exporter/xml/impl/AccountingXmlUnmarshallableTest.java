/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2023 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.exporter.xml.impl;

import javax.annotation.Nonnull;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.test.util.Dumper;
import it.tidalwave.role.spi.SystemRoleFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static it.tidalwave.util.test.FileComparisonUtils.assertSameContents;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public class AccountingXmlUnmarshallableTest
  {
    @BeforeMethod
    public void installEmptyAsSupport()
      {
        SystemRoleFactory.reset();
      }

    @Test(enabled = false)
    public void must_properly_unmarshall_iBiz()
      throws Exception
      {
        final var
                expectedResultsFolder = Path.of("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private");
        
        if (Files.exists(expectedResultsFolder))
          {
            final var testFolder = Path.of("target/test-results");
            Files.createDirectories(testFolder);
            final var actualResult = testFolder.resolve("unmarshalledDump.txt");
            final var expectedResult = expectedResultsFolder.resolve("iBizImportDump.txt");
            final var importFile = expectedResultsFolder.resolve("iBizImportMarshalled.xml");

            final var accounting = Accounting.createNew();
            final var fixture = new AccountingXmlUnmarshallable(accounting);

            try (final var is = Files.newInputStream(importFile))
              {
                fixture.unmarshal(is);
              }

            try (final var pw = new PrintWriter(Files.newBufferedWriter(actualResult)))
              {
                new Dumper(accounting, pw).dumpAll();
              }

            assertSameContents(expectedResult, actualResult);
          }
      }
    
    @Test(dataProvider = "scenarios")
    public void must_properly_unmarshall (@Nonnull final String scenarioName)
      throws Exception
      {
        final var expectedResultsFolder = Path.of("src/test/resources/expected-results");
        final var testFolder = Path.of("target/test-results");
        Files.createDirectories(testFolder);
        final var importFile = Path.of("src/test/resources/scenarios").resolve(scenarioName + ".xml");
        final var actualResult = testFolder.resolve(scenarioName + ".txt");
        final var expectedResult = expectedResultsFolder.resolve(scenarioName + ".txt");

        final var accounting = Accounting.createNew();
        final var fixture = new AccountingXmlUnmarshallable(accounting);

        try (final var is = Files.newInputStream(importFile))
          {
            fixture.unmarshal(is);
          }
        
        try (final var pw = new PrintWriter(actualResult.toFile()))
          {
            new Dumper(accounting, pw).dumpAll();
          }
        
        assertSameContents(expectedResult, actualResult);
      }
    
    @DataProvider(name = "scenarios")
    private static Object[][] scenarios()
      {
        return new Object[][]
          {
            { "XmlEmpty"     },
            { "XmlScenario1" }
          };
      }
  }
