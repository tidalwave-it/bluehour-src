/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2021 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.exporter.xml.impl;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.util.spi.AsDelegateProvider;
import it.tidalwave.util.spi.EmptyAsDelegateProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import it.tidalwave.util.test.FileComparisonUtils;
import it.tidalwave.accounting.test.util.Dumper;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class AccountingXmlUnmarshallableTest
  {
    @BeforeMethod
    public void installEmptyAsSupport()
      {
        AsDelegateProvider.Locator.set(new EmptyAsDelegateProvider());
      }
    
    @Test
    public void must_properly_unmarshall_iBiz()
      throws Exception
      {
        final Path expectedResultsFolder = Paths.get("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private");
        
        if (Files.exists(expectedResultsFolder))
          {
            final Path testFolder = Paths.get("target/test-results");
            Files.createDirectories(testFolder);
            final Path actualResult = testFolder.resolve("unmarshalledDump.txt");
            final Path expectedResult = expectedResultsFolder.resolve("iBizImportDump.txt");
            final Path importFile = expectedResultsFolder.resolve("iBizImportMarshalled.xml");

            final Accounting accounting = Accounting.createNew();
            final AccountingXmlUnmarshallable fixture = new AccountingXmlUnmarshallable(accounting);

            try (final InputStream is = new FileInputStream(importFile.toFile())) 
              {
                fixture.unmarshal(is);
              }

            try (final PrintWriter pw = new PrintWriter(actualResult.toFile())) 
              {
                new Dumper(accounting, pw).dumpAll();
              }

            FileComparisonUtils.assertSameContents(expectedResult.toFile(), actualResult.toFile());
          }
      }
    
    @Test(dataProvider = "scenarios")
    public void must_properly_unmarshall (final @Nonnull String scenarioName)
      throws Exception
      {
        final Path expectedResultsFolder = Paths.get("src/test/resources/expected-results");
        final Path testFolder = Paths.get("target/test-results");
        Files.createDirectories(testFolder);
        final Path importFile = Paths.get("src/test/resources/scenarios").resolve(scenarioName + ".xml");
        final Path actualResult = testFolder.resolve(scenarioName + ".txt");
        final Path expectedResult = expectedResultsFolder.resolve(scenarioName + ".txt");

        final Accounting accounting = Accounting.createNew();
        final AccountingXmlUnmarshallable fixture = new AccountingXmlUnmarshallable(accounting);

        try (final InputStream is = new FileInputStream(importFile.toFile())) 
          {
            fixture.unmarshal(is);
          }
        
        try (final PrintWriter pw = new PrintWriter(actualResult.toFile())) 
          {
            new Dumper(accounting, pw).dumpAll();
          }
        
        FileComparisonUtils.assertSameContents(expectedResult.toFile(), actualResult.toFile());
      }
    
    @DataProvider(name = "scenarios")
    private Object[][] scenarios()
      {
        return new Object[][]
          {
            { "XmlEmpty"     },
            { "XmlScenario1" }
          };
      }
  }
