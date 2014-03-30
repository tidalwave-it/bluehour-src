/*
 * #%L
 * *********************************************************************************************************************
 *
 * NorthernWind - lightweight CMS
 * http://northernwind.tidalwave.it - hg clone https://bitbucket.org/tidalwave/northernwind-src
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
package it.tidalwave.accounting.ui.impl.javafx;

import javax.annotation.Nonnull;
import javafx.application.Platform;
import java.io.File;
import it.tidalwave.ui.javafx.JavaFXSpringApplication;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class Main extends JavaFXSpringApplication
  {
    public static void main (final @Nonnull String ... args)
      {
        try
          {
            // FIXME: Mac OS X only
            final File logfolder = new File(System.getProperty("user.home") + "/Library/Application Support/blueHour/logs");
            final String logFolderPath = logfolder.getAbsolutePath();
            System.err.println("Logging folder: " + logFolderPath);
            logfolder.mkdirs();
            System.setProperty("it.tidalwave.northernwind.bluehour.logFolder", logFolderPath);
            Platform.setImplicitExit(true);
            launch(args);
          }
        catch (Throwable t)
          {
            // Don't use logging facilities here, they could be not initialized
            t.printStackTrace();
            System.exit(-1);
          }
      }
  }