/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2021 Tidalwave s.a.s. (http://tidalwave.it)
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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
public final class IBizUtils
  {
    /*******************************************************************************************************************
     *
     * @param <T>  the type of elements
     * @param i    the iterator
     * @return     the list
     * 
     ******************************************************************************************************************/
    @Nonnull
    public static <T> List<T> toList (final @Nonnull Iterator<T> i)
      {
        final List<T> list = new ArrayList<>();

        while (i.hasNext())
          {
            list.add(i.next());
          }

        return list;
      }

    /*******************************************************************************************************************
     *
     * @param   path         the path of the configuration file
     * @return               the configuration object
     * @throws  IOException  in case of error
     * 
     ******************************************************************************************************************/
    @Nonnull
    public static ConfigurationDecorator loadConfiguration (final @Nonnull Path path)
      throws IOException
      {
        try
          {
            return new ConfigurationDecorator(new XMLPropertyListConfiguration(path.toFile()));
          }
        catch (ConfigurationException e)
          {
            throw new IOException(e);
          }
      }
  }
