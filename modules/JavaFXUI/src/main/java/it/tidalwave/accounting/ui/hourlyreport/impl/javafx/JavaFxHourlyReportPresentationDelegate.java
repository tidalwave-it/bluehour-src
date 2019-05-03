/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
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
package it.tidalwave.accounting.ui.hourlyreport.impl.javafx;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import it.tidalwave.accounting.ui.hourlyreport.HourlyReportPresentation;
import it.tidalwave.util.ui.UserNotificationWithFeedback;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.javafx.JavaFXBinder;
import static it.tidalwave.role.PlainTextRenderable.PlainTextRenderable;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public class JavaFxHourlyReportPresentationDelegate implements HourlyReportPresentation
  {
    @FXML
    private Pane pnHourlyReport;

    @FXML
    private TextArea taReport;

    @Inject
    private JavaFXBinder binder;

    @Override
    public void bind()
      {
      }

    @Override
    public void showUp (final @Nonnull UserNotificationWithFeedback notification)
      {
        binder.showInModalDialog(pnHourlyReport, notification);
      }

    @Override
    public void populate (final @Nonnull PresentationModel pm)
      {
        taReport.setText(pm.as(PlainTextRenderable).render());
      }
  }
