/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2024 by Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.ui.impl.javafx;

import javax.annotation.Nonnull;
import javafx.application.Platform;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import it.tidalwave.accounting.impl.DefaultAccountingController;
import it.tidalwave.accounting.ui.customerexplorer.CustomerExplorerPresentationControl;
import it.tidalwave.accounting.ui.projectexplorer.ProjectExplorerPresentationControl;
import it.tidalwave.ui.javafx.JavaFXSpringAnnotationApplication;
import it.tidalwave.util.PreferencesHandler;
import it.tidalwave.messagebus.spi.SimpleMessageBus;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Configuration
@EnableSpringConfigured
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "it.tidalwave")
public class Main extends JavaFXSpringAnnotationApplication
  {
    public static void main (@Nonnull final String ... args)
      {
        try
          {
            PreferencesHandler.setAppName("blueHour");
            final var preferenceHandler = PreferencesHandler.getInstance();
            final var logFolder = preferenceHandler.getLogFolder();
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

    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override
    protected void onStageCreated (@Nonnull final ApplicationContext applicationContext)
      {
        // FIXME: controllers can't initialize in postconstruct
        // Too bad because with PAC+EventBus we'd get rid of the control interfaces
        // Could be fixed by firing a PowerOnNotification, such as in blueMarine II.
        applicationContext.getBean(CustomerExplorerPresentationControl.class).initialize();
        applicationContext.getBean(ProjectExplorerPresentationControl.class).initialize();
        applicationContext.getBean(ProjectExplorerPresentationControl.class).initialize();
        applicationContext.getBean(DefaultAccountingController.class).initialize();
      }

    @Bean
    public SimpleMessageBus applicationMessageBus()
      {
        final var executor = new ThreadPoolTaskExecutor();
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.setThreadNamePrefix("messageBus-");
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.afterPropertiesSet();
        return new SimpleMessageBus(executor);
      }
  }