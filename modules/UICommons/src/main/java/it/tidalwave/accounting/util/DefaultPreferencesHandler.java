/*
 * #%L
 * *********************************************************************************************************************
 *
 * NorthernWind - lightweight CMS
 * http://northernwind.tidalwave.it - hg clone https://bitbucket.org/tidalwave/northernwind-src
 * %%
 * Copyright (C) 2013 - 2019 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Getter;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class DefaultPreferencesHandler implements PreferencesHandler
  {
    @Getter
    private final Path appFolder;
    
    @Getter
    private final Path logFolder;
    
    public DefaultPreferencesHandler()
      {
        try 
          {
            // FIXME: Mac OS X only
            appFolder = new File(System.getProperty("user.home") + "/Library/Application Support/blueHour/").toPath();
            logFolder = appFolder.resolve("logs");
            Files.createDirectories(logFolder);
            final String logFolderPath = logFolder.toFile().getAbsolutePath();
            System.err.println("Logging folder: " + logFolderPath);
          } 
        catch (IOException e) 
          {
            throw new RuntimeException(e);
          }
      }
  }
