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
package it.tidalwave.accounting.exporter.xml.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import it.tidalwave.accounting.importer.ibiz.IBizImporter;
import it.tidalwave.accounting.importer.ibiz.impl.DefaultIBizImporter;
import it.tidalwave.util.test.FileComparisonUtils;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class AccountingXmlMarshallableTest
  {
    @Test
    public void must_properly_marshall()
      throws Exception
      {
        final Path iBizFolder = Paths.get("/Users/fritz/Settings/iBiz/"); // FIXME
        final Path expectedResultsFolder = Paths.get("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private");
        final Path testFolder = Paths.get("target/test-results");
        Files.createDirectories(testFolder);
        final Path actualResult = testFolder.resolve("accouting.xml");
        final Path expectedResult = expectedResultsFolder.resolve("accouting.xml");

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
