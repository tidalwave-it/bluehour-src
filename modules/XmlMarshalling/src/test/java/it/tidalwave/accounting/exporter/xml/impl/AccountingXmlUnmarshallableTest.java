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

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.accounting.importer.ibiz.impl.Dumper;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.impl.DefaultAccounting;
import it.tidalwave.util.test.FileComparisonUtils;
import org.testng.annotations.Test;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class AccountingXmlUnmarshallableTest
  {
    @Test
    public void must_properly_unmarshall()
      throws Exception
      {
        final Path expectedResultsFolder = Paths.get("/Users/fritz/Business/Tidalwave/Projects/WorkAreas/blueHour/private");
        final Path testFolder = Paths.get("target/test-results");
        Files.createDirectories(testFolder);
        final Path actualResult = testFolder.resolve("unmarshalledDump.txt");
        final Path expectedResult = expectedResultsFolder.resolve("iBizImportDump.txt");
        final Path importFile = expectedResultsFolder.resolve("iBizImportMarshalled.xml");
        
        final Accounting accounting = new DefaultAccounting();
        
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
