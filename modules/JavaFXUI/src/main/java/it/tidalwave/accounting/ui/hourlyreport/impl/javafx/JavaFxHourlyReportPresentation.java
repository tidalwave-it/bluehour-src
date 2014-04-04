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
package it.tidalwave.accounting.ui.hourlyreport.impl.javafx;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javafx.application.Platform;
import javafx.scene.Node;
import java.io.IOException;
import it.tidalwave.util.ui.UserNotificationWithFeedback;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.javafx.JavaFXBinder;
import it.tidalwave.accounting.ui.hourlyreport.HourlyReportPresentation;
import it.tidalwave.ui.javafx.JavaFXSafeProxyCreator.NodeAndDelegate;
import static it.tidalwave.ui.javafx.JavaFXSafeProxyCreator.createNodeAndDelegate;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class JavaFxHourlyReportPresentation implements HourlyReportPresentation
  {
    @Inject @Nonnull
    private JavaFXBinder binder;
    
    // @Delegate
    private HourlyReportPresentation delegate;
    
    private Node node;

    public JavaFxHourlyReportPresentation() 
      throws IOException 
      {
        if (node == null)
          {
            final NodeAndDelegate nad = createNodeAndDelegate(getClass(), "HourlyReportPresentation.fxml");
            node = nad.getNode();
            delegate = nad.getDelegate();
          }
      }
    
    @Override
    public void bind() 
      {
      }  
    
    @Override
    public void showUp (final @Nonnull UserNotificationWithFeedback notification) 
      {
        assert Platform.isFxApplicationThread();

        binder.showInModalDialog(node, notification);
      }

    @Override
    public void populate (final @Nonnull PresentationModel pm) 
      {
        delegate.populate(pm);
      }
  }
