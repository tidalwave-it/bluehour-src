/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - hg clone https://bitbucket.org/tidalwave/bluehour-src
 * %%
 * Copyright (C) 2013 - 2013 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.impl;

import javax.annotation.Nonnull;
import java.util.List;
import it.tidalwave.accounting.model.JobEvent;
import it.tidalwave.accounting.model.Project;
import org.testng.annotations.Test;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Slf4j
public class ImportTest
  {
    enum IBizJobEventType
      {
        EVENT, FIXED, UNKNOWN2, UNKNOWN3, UNKNOWN4, GROUP
      }

    @Test
    public void testImport()
      throws Exception
      {
        // FIXME: use a Builder
        final IBizCustomerImporter customerImporter = new IBizCustomerImporter();

        final String path = "/Users/fritz/Settings/iBiz/Projects/4D2263D4-9043-40B9-B162-2C8951F86503.ibiz"; // FIXME
        final IBizProjectImporter importer = IBizProjectImporter.builder()
                                                                .withPath2(path)
                                                                .withCustomerRegistry(customerImporter.getCustomerRegistry())
                                                                .create();

        customerImporter.run();
        final Project project = importer.run();
        log.info("PROJECT: {}", project);
        dump(project.findChildren().results(), "");

        // TODO: assertions; but we must first anonymize the data
      }

    private static void dump (final @Nonnull List<? extends JobEvent> events, final @Nonnull String prefix)
      {
        for (final JobEvent event : events)
          {
            dump(event, prefix);
          }
      }

    private static void dump (final @Nonnull JobEvent event, final @Nonnull String prefix)
      {
        log.info("{}{}", prefix, event);
        dump(event.findChildren().results(), prefix + "  ");
      }
  }
