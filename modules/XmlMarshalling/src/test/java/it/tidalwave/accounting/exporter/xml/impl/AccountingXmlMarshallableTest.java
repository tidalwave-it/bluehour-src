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
package it.tidalwave.accounting.exporter.xml.impl;

import javax.annotation.Nonnull;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.util.spi.AsDelegateProvider;
import it.tidalwave.util.spi.EmptyAsDelegateProvider;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.importer.ibiz.IBizImporter;
import it.tidalwave.accounting.importer.ibiz.impl.DefaultIBizImporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import it.tidalwave.util.test.FileComparisonUtils;
import it.tidalwave.accounting.test.util.ScenarioFactory;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class AccountingXmlMarshallableTest
  {
    @BeforeMethod
    public void installEmptyAsSupport()
      {
        AsDelegateProvider.Locator.set(new EmptyAsDelegateProvider());
      }
    
    @Test
    public void consistencyTest()
      {
        ScenarioFactory.createScenarios();
      }
    
    @Test
    public void must_properly_marshall_iBiz()
      throws Exception
      {
        final Path iBizFolder = Paths.get("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private/iBiz"); // FIXME
//        final Path iBizFolder = Paths.get("/Users/fritz/Settings/iBiz"); // FIXME
        
        if (Files.exists(iBizFolder))
          {
            final Path expectedResultsFolder = Paths.get("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private");
            final Path testFolder = Paths.get("target/test-results");
            Files.createDirectories(testFolder);
            final Path actualResult = testFolder.resolve("iBizImportMarshalled.xml");
            final Path expectedResult = expectedResultsFolder.resolve("iBizImportMarshalled.xml");

            final IBizImporter importer = DefaultIBizImporter.builder()
                                                             .withPath(iBizFolder)
                                                             .create();
            importer.importAll();

            final AccountingXmlMarshallable fixture = new AccountingXmlMarshallable(importer);

            try (final OutputStream os = new FileOutputStream(actualResult.toFile())) 
              {
                fixture.marshal(os);
              }

            FileComparisonUtils.assertSameContents(expectedResult.toFile(), actualResult.toFile());
          }
      }
    
    @Test(dataProvider = "accountings", dataProviderClass = ScenarioFactory.class)
    public void must_properly_marshall (final @Nonnull String scenarioName, final @Nonnull Accounting scenario)
      throws Exception
      {
        final Path expectedResultsFolder = Paths.get("src/test/resources/expected-results");
        final Path testFolder = Paths.get("target/test-results");
        Files.createDirectories(testFolder);
        final Path actualResult = testFolder.resolve(scenarioName + ".xml");
        final Path expectedResult = expectedResultsFolder.resolve(scenarioName + ".xml");

        final AccountingXmlMarshallable fixture = new AccountingXmlMarshallable(scenario);

        try (final OutputStream os = new FileOutputStream(actualResult.toFile())) 
          {
            fixture.marshal(os);
          }
        
        FileComparisonUtils.assertSameContents(expectedResult.toFile(), actualResult.toFile());
      }
  }
