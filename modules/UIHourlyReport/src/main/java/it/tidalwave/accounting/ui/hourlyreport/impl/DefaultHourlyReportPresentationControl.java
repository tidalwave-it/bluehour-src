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
package it.tidalwave.accounting.ui.hourlyreport.impl;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import it.tidalwave.accounting.commons.ProjectHourlyReportRequest;
import it.tidalwave.accounting.ui.hourlyreport.HourlyReportPresentation;
import it.tidalwave.accounting.ui.hourlyreport.HourlyReportPresentationControl;
import it.tidalwave.util.annotation.VisibleForTesting;
import it.tidalwave.role.PlainTextRenderable;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.dci.annotation.DciContext;
import it.tidalwave.messagebus.annotation.ListensTo;
import it.tidalwave.messagebus.annotation.SimpleMessageSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.accounting.model.HourlyReportGenerator._HourlyReportGenerator_;
import static it.tidalwave.util.ui.UserNotificationWithFeedback.notificationWithFeedback;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Component @RequiredArgsConstructor @DciContext @SimpleMessageSubscriber @Slf4j
public class DefaultHourlyReportPresentationControl implements HourlyReportPresentationControl
  {
    @Nonnull
    private final HourlyReportPresentation presentation;
    
    @VisibleForTesting void onProjectHourlyReportRequest (@Nonnull @ListensTo final ProjectHourlyReportRequest request)
      {
        final var report = request.getProject().as(_HourlyReportGenerator_).createReport();
        presentation.bind();
        presentation.showUp(notificationWithFeedback().withCaption("Project Hourly Report"));
        presentation.populate(PresentationModel.of("???", (PlainTextRenderable) (args) -> report.asString()));
      }
  }
