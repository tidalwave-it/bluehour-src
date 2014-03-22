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

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.role.Composite;
import it.tidalwave.role.SimpleComposite;
import it.tidalwave.accounting.model.AbstractJobEvent;
import it.tidalwave.accounting.importer.ibiz.IBizImporter;
import java.util.concurrent.atomic.AtomicReference;
import org.testng.annotations.Test;
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
    private static final String INDENT = "    ";

    @Test
    public void testImport()
      throws Exception
      {
        final Path path = Paths.get("/Users/fritz/Settings/iBiz/"); // FIXME
        final IBizImporter importer = DefaultIBizImporter.builder()
                                                         .withPath(path)
                                                         .create();
        importer.run();

        importer.getProjectRegistry().findProjects().results().stream().forEach((project) ->
          {
            log.info("{}", project);
            dump(project.findChildren().results(), INDENT);
          });

        // TODO: assertions; but we must first anonymize the data
      }

    private static void dump (final @Nonnull List<? extends AbstractJobEvent> events, final @Nonnull String prefix)
      {
        events.stream().forEach((event) -> 
          {
            dump(event, prefix);
          });
      }

    private static void dump (final @Nonnull AbstractJobEvent event, final @Nonnull String prefix)
      {
        log.info("{}{}", prefix, toString(event));

        if (event instanceof Composite)
          {
            dump(((SimpleComposite<AbstractJobEvent>)event).findChildren().results(), prefix + INDENT);
          }
      }

    @Nonnull
    private static String toString (final @Nonnull AbstractJobEvent event)
      {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getClass().getSimpleName()).append("(");

        final List<Field> fields = new ArrayList<>(Arrays.asList(event.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(event.getClass().getSuperclass().getDeclaredFields()));
        Collections.sort(fields, (Field f1, Field f2) -> f1.getName().compareTo(f2.getName()));

        final AtomicReference<String> separator = new AtomicReference<>("");

        fields.stream().forEach((field) ->
          {
            if (!Collection.class.isAssignableFrom(field.getType()))
              {
                try
                  {
                    field.setAccessible(true);
                    builder.append(separator.getAndSet(", "));
                    builder.append(String.format("%s=%s", field.getName(), field.get(event)));
                  }
                catch (IllegalArgumentException | IllegalAccessException e)
                  {
                    throw new RuntimeException(e);
                  }
              }
          });

        return builder.append(")").toString();
      }
  }