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
 * $Id$
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.ui.impl.javafx;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import javafx.application.Platform;
import org.springframework.context.ApplicationContext;
import it.tidalwave.util.PreferencesHandler;
import it.tidalwave.ui.javafx.JavaFXSpringApplication;
import it.tidalwave.accounting.ui.customerexplorer.CustomerExplorerPresentationControl;
import it.tidalwave.accounting.ui.projectexplorer.ProjectExplorerPresentationControl;

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
            System.setProperty(PreferencesHandler.PROP_APP_NAME, "blueHour");
            final PreferencesHandler preferenceHandler = PreferencesHandler.getInstance();
            final Path logFolder = preferenceHandler.getLogFolder();
            System.setProperty("it.tidalwave.northernwind.bluehour.logFolder", logFolder.toString());
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

    /*******************************************************************************************************************
     *
     * {@inheritDoc}
     *
     ******************************************************************************************************************/
    @Override
    protected void onStageCreated (final @Nonnull ApplicationContext applicationContext)
      {
        // FIXME: controllers can't initialize in postconstruct
        // Too bad because with PAC+EventBus we'd get rid of the control interfaces
        // Could be fixed by firing a PowerOnNotification, such as in blueMarine II.
        applicationContext.getBean(CustomerExplorerPresentationControl.class).initialize();
        applicationContext.getBean(ProjectExplorerPresentationControl.class).initialize();
        applicationContext.getBean(ProjectExplorerPresentationControl.class).initialize();
      }
  }