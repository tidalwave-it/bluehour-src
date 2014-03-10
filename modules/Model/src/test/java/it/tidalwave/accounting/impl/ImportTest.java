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
import it.tidalwave.accounting.importer.ibiz.IBizImporter;
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
    @Test
    public void testImport()
      throws Exception
      {
        final String path = "/Users/fritz/Settings/iBiz/"; // FIXME
        final IBizImporter importer = IBizImporter.builder()
                                                  .withPath2(path)
                                                  .create();

        importer.run();

        for (final Project project : importer.getProjectRegistry().findProjects().results())
          {
            log.info("PROJECT: {}", project);
            dump(project.findChildren().results(), "");
          }

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
