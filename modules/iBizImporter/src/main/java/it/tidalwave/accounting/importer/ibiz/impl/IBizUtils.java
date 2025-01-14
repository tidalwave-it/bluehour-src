/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2025 by Tidalwave s.a.s. (http://tidalwave.it)
 *
 * *************************************************************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 *
 * *************************************************************************************************************************************************************
 *
 * git clone https://bitbucket.org/tidalwave/bluehour-src
 * git clone https://github.com/tidalwave-it/bluehour-src
 *
 * *************************************************************************************************************************************************************
 */
package it.tidalwave.accounting.importer.ibiz.impl;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public final class IBizUtils
  {
    /***********************************************************************************************************************************************************
     * @param <T>  the type of elements
     * @param i    the iterator
     * @return     the list
     **********************************************************************************************************************************************************/
    @Nonnull
    public static <T> List<T> toList (@Nonnull final Iterator<? extends T> i)
      {
        final List<T> list = new ArrayList<>();

        while (i.hasNext())
          {
            list.add(i.next());
          }

        return list;
      }

    /***********************************************************************************************************************************************************
     * @param   path         the path of the configuration file
     * @return               the configuration object
     * @throws  IOException  in case of error
     **********************************************************************************************************************************************************/
    @Nonnull
    public static ConfigurationDecorator loadConfiguration (@Nonnull final Path path)
      throws IOException
      {
        try
          {
            final var configs = new Configurations();
            return new ConfigurationDecorator(new XMLPropertyListConfiguration(configs.xml(path.toFile())));
          }
        catch (ConfigurationException e)
          {
            throw new IOException(e);
          }
      }
  }
