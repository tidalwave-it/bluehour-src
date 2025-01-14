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
package it.tidalwave.accounting.ui.impl.javafx;

import javax.annotation.Nonnull;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import it.tidalwave.ui.javafx.JavaFXSpringAnnotationApplication;
import it.tidalwave.role.ui.annotation.EnableMessageBus;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Configuration
@EnableSpringConfigured
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "it.tidalwave")
@EnableMessageBus
public class Main extends JavaFXSpringAnnotationApplication
  {
    public static void main (@Nonnull final String ... args)
      {
        launch(Main.class,
               params().withArgs(args)
                       .withApplicationName("blueHour")
                       .withLogFolderPropertyName("it.tidalwave.northernwind.bluehour.logFolder")
                       .withProperty(K_INITIAL_SIZE, 0.8));
      }
  }