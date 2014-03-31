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
package it.tidalwave.accounting.ui.customerexplorer.impl.javafx;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Configurable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.javafx.JavaFXBinder;
import it.tidalwave.accounting.ui.customerexplorer.CustomerExplorerPresentation;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Configurable
public class JavaFxCustomerExplorerPresentation implements CustomerExplorerPresentation
  {
    @Inject @Nonnull
    private JavaFXBinder binder;
    
    private ListView<PresentationModel> lvCustomerExplorer;
    
    public void bind (final @Nonnull ListView<PresentationModel> lvCustomers)
      {
        this.lvCustomerExplorer = lvCustomers;
      }
    
    @Override
    public void populate (final @Nonnull PresentationModel pm)
      {
          binder.bind(lvCustomerExplorer, pm);
      }
  }