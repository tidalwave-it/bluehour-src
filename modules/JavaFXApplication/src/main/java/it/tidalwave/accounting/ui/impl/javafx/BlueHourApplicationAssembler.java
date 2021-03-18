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
package it.tidalwave.accounting.ui.impl.javafx;

import javax.annotation.Nonnull;
import it.tidalwave.application.ToolBarModel;
import it.tidalwave.role.ui.javafx.ApplicationPresentationAssembler;
import it.tidalwave.accounting.ui.customerexplorer.impl.javafx.JavaFxCustomerExplorerPresentation;
import it.tidalwave.accounting.ui.jobeventexplorer.impl.javafx.JavaFxJobEventExplorerPresentation;
import it.tidalwave.accounting.ui.projectexplorer.impl.javafx.JavaFxProjectExplorerPresentation;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici (Fabrizio.Giudici@tidalwave.it)
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class BlueHourApplicationAssembler
        implements ApplicationPresentationAssembler<JavaFXApplicationPresentationDelegate>
  {
    @Nonnull
    private final JavaFxCustomerExplorerPresentation javaFxCustomerExplorerPresentation;

    @Nonnull
    private final JavaFxProjectExplorerPresentation javaFxProjectExplorerPresentation;

    @Nonnull
    private final JavaFxJobEventExplorerPresentation javaFxJobEventExplorerPresentation;

    @Nonnull
    private final ToolBarModel toolBarModel;

    @Override
    public void assemble (final @Nonnull JavaFXApplicationPresentationDelegate applicationPresentation)
      {
        applicationPresentation.assemble(toolBarModel,
                                         javaFxCustomerExplorerPresentation.getNad().getNode(),
                                         javaFxProjectExplorerPresentation.getNad().getNode(),
                                         javaFxJobEventExplorerPresentation.getNad().getNode());
      }
  }
