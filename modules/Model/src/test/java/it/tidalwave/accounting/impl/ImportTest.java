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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.role.Composite;
import it.tidalwave.role.SimpleComposite;
import it.tidalwave.accounting.model.AbstractJobEvent;
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
        final Path path = Paths.get("/Users/fritz/Settings/iBiz/"); // FIXME
        final IBizImporter importer = IBizImporter.builder()
                                                  .withPath(path)
                                                  .create();

        importer.run();

        for (final Project project : importer.getProjectRegistry().findProjects().results())
          {
            log.info("PROJECT: {}", project);
            dump(project.findChildren().results(), "");
          }

        // TODO: assertions; but we must first anonymize the data
      }

    private static void dump (final @Nonnull List<? extends AbstractJobEvent> events, final @Nonnull String prefix)
      {
        for (final AbstractJobEvent event : events)
          {
            dump(event, prefix);
          }
      }

    private static void dump (final @Nonnull AbstractJobEvent event, final @Nonnull String prefix)
      {
        log.info("{}{}", prefix, toString(event));

        if (event instanceof Composite)
          {
            dump(((SimpleComposite<AbstractJobEvent>)event).findChildren().results(), prefix + "  ");
          }
      }

    @Nonnull
    private static String toString (final @Nonnull AbstractJobEvent event)
      {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getClass().getSimpleName()).append("(");

        final List<Field> fields = new ArrayList<>(Arrays.asList(event.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(event.getClass().getSuperclass().getDeclaredFields()));
        Collections.sort(fields, new Comparator<Field>()
          {
            @Override
            public int compare (final @Nonnull Field f1, final @Nonnull Field f2)
              {
                return f1.getName().compareTo(f2.getName());
              }
          });

        String separator = "";

        for (final Field field : fields)
          {
            if (!Collection.class.isAssignableFrom(field.getType()))
              {
                try
                  {
                    field.setAccessible(true);
                    builder.append(separator);
                    builder.append(String.format("%s=%s", field.getName(), field.get(event)));
                    separator = ", ";
                  }
                catch (IllegalArgumentException | IllegalAccessException e)
                  {
                    throw new RuntimeException(e);
                  }
              }
          }

        return builder.append(")").toString();
      }
  }
