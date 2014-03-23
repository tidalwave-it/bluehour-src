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
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import it.tidalwave.util.FinderStream;
import it.tidalwave.accounting.model.AbstractJobEvent;
import it.tidalwave.accounting.model.JobEventGroup;
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
        importer.getProjectRegistry().findProjects().forEach((project) -> dump(project));

        // TODO: assertions; but we must first anonymize the data
      }

    private static void dump (final @Nonnull Project project)
      {
        log.info("{}", project);
        dump(project.findChildren(), INDENT);
      }

    private static void dump (final @Nonnull FinderStream<AbstractJobEvent> events, final @Nonnull String prefix)
      {
        events.forEach((event) -> dump(event, prefix));
      }

    private static void dump (final @Nonnull AbstractJobEvent event, final @Nonnull String prefix)
      {
        log.info("{}{}", prefix, toString(event));

        if (event instanceof JobEventGroup)
          {
            dump(((JobEventGroup)event).findChildren(), prefix + INDENT);
          }
      }

    @Nonnull
    private static String toString (final @Nonnull AbstractJobEvent event)
      {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getClass().getSimpleName()).append("(");

        final List<Field> fields = new ArrayList<>(Arrays.asList(event.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(event.getClass().getSuperclass().getDeclaredFields()));

        final AtomicReference<String> separator = new AtomicReference<>("");

        fields.stream().sorted((field1, field2) -> field1.getName().compareTo(field2.getName()))
                       .filter((field)          -> !Collection.class.isAssignableFrom(field.getType()))
                       .peek((field)            -> field.setAccessible(true))
                       .forEach((field)         -> builder.append(separator.getAndSet(", "))
                                                          .append(field.getName())
                                                          .append("=")
                                                          .append(safeGet(field, event)));

        return builder.append(")").toString();
      }
    
    private static Object safeGet (final @Nonnull Field field, final @Nonnull Object object)
      {
        try
          {
            return field.get(object);
          }
        catch (IllegalArgumentException | IllegalAccessException e)
          {
            throw new RuntimeException(e);
          }
      }
  }
