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
package it.tidalwave.accounting.ui.customerexplorer.impl.javafx;

import org.springframework.stereotype.Component;
import it.tidalwave.accounting.ui.customerexplorer.CustomerExplorerPresentation;
import it.tidalwave.ui.javafx.NodeAndDelegate;
import lombok.Getter;
import lombok.experimental.Delegate;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Component
public class JavaFxCustomerExplorerPresentation implements CustomerExplorerPresentation
  {
    @Getter
    private final NodeAndDelegate<JavaFxCustomerExplorerPresentation> nad = NodeAndDelegate.of(JavaFxCustomerExplorerPresentation.class);

    @Delegate
    private final CustomerExplorerPresentation delegate = nad.getDelegate();
  }
