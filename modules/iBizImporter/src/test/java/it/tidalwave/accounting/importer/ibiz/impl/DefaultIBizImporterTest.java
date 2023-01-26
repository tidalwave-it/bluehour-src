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
package it.tidalwave.accounting.importer.ibiz.impl;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.testng.annotations.Test;
import it.tidalwave.accounting.test.util.Dumper;
import it.tidalwave.role.spi.OwnerRoleFactoryProvider;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.util.test.FileComparisonUtils.assertSameContents;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@Slf4j
public class DefaultIBizImporterTest
  {
    @Test(enabled = false)
    public void must_properly_import()
      throws Exception
      {
        OwnerRoleFactoryProvider.set(OwnerRoleFactoryProvider.emptyRoleFactory());
        
//        final Path iBizFolder = Path.of("/Users/fritz/Settings/iBiz"); // FIXME
        final var iBizFolder = Path.of("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private/iBiz"); // FIXME
        
        if (Files.exists(iBizFolder))
          {
            final var
                    expectedResultsFolder = Path.of("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private");
            final var testFolder = Path.of("target/test-results");
            Files.createDirectories(testFolder);
            final var actualResult = testFolder.resolve("iBizImportDump.txt");
            final var expectedResult = expectedResultsFolder.resolve("iBizImportDump.txt");

            final var importer = DefaultIBizImporter.builder()
                                                    .withPath(iBizFolder)
                                                    .create();
            importer.importAll();

            try (final var pw = new PrintWriter(Files.newBufferedWriter(actualResult)))
              {
                new Dumper(importer, pw).dumpAll();
              }

            assertSameContents(expectedResult, actualResult);
          }
      }
  }
