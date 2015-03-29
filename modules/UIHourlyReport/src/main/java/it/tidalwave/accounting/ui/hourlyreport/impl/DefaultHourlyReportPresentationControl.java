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
package it.tidalwave.accounting.ui.hourlyreport.impl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import com.google.common.annotations.VisibleForTesting;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import it.tidalwave.accounting.model.HourlyReport;
import it.tidalwave.accounting.commons.ProjectHourlyReportRequest;
import it.tidalwave.accounting.ui.hourlyreport.HourlyReportPresentation;
import it.tidalwave.accounting.ui.hourlyreport.HourlyReportPresentationControl;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import it.tidalwave.role.ui.spi.PlainTextRenderableSupport8;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.util.ui.UserNotificationWithFeedback.*;
import static it.tidalwave.accounting.model.HourlyReportGenerator.HourlyReportGenerator;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultHourlyReportPresentationControl implements HourlyReportPresentationControl
  {
    @Inject @Nonnull
    private HourlyReportPresentation presentation;
    
    @VisibleForTesting void onProjectHourlyReportRequest (final @Nonnull @ListensTo ProjectHourlyReportRequest request)
      {
        final HourlyReport report = request.getProject().as(HourlyReportGenerator).createReport();
        presentation.bind();
        presentation.showUp(notificationWithFeedback().withCaption("Project Hourly Report"));
        presentation.populate(new DefaultPresentationModel(null, 
                (PlainTextRenderableSupport8) (args) -> report.asString()));
      }
  }