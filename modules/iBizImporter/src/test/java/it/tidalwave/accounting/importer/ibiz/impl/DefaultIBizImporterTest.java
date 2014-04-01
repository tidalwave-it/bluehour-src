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
package it.tidalwave.accounting.importer.ibiz.impl;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.util.test.FileComparisonUtils;
import it.tidalwave.accounting.importer.ibiz.IBizImporter;
import org.testng.annotations.Test;
import it.tidalwave.accounting.test.util.Dumper;
import it.tidalwave.util.spi.AsDelegateProvider;
import it.tidalwave.util.spi.EmptyAsDelegateProvider;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class DefaultIBizImporterTest
  {
    @Test
    public void must_properly_import()
      throws Exception
      {
        AsDelegateProvider.Locator.set(new EmptyAsDelegateProvider());
        final Path iBizFolder = Paths.get("/Users/fritz/Settings/iBiz/"); // FIXME
        
        if (Files.exists(iBizFolder))
          {
            final Path expectedResultsFolder = Paths.get("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private");
            final Path testFolder = Paths.get("target/test-results");
            Files.createDirectories(testFolder);
            final Path actualResult = testFolder.resolve("iBizImportDump.txt");
            final Path expectedResult = expectedResultsFolder.resolve("iBizImportDump.txt");

            final IBizImporter importer = DefaultIBizImporter.builder()
                                                             .withPath(iBizFolder)
                                                             .create();
            importer.importAll();

            try (final PrintWriter pw = new PrintWriter(actualResult.toFile())) 
              {
                new Dumper(importer, pw).dumpAll();
              }

            FileComparisonUtils.assertSameContents(expectedResult.toFile(), actualResult.toFile());
          }
      }
  }
