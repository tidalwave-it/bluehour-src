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
package it.tidalwave.accounting.ui.hourlyreport.impl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.role.ui.UserAction;
import it.tidalwave.role.ui.spi.DefaultUserActionProvider2;
import it.tidalwave.role.ui.spi.MessageSendingUserAction;
import it.tidalwave.messagebus.MessageBus;
import it.tidalwave.accounting.commons.ProjectHourlyReportRequest;
import it.tidalwave.accounting.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;

/***********************************************************************************************************************
 *
 * This role provides a "Create time report..." action for {@link Project} objects.
 * 
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = Project.class) @Configurable @RequiredArgsConstructor
public class ProjectReportUserActionProvider extends DefaultUserActionProvider2
  {
    @Nonnull
    private final Project project;
    
    @Inject @Named("applicationMessageBus") @Nonnull
    private MessageBus messageBus;

    @Override @Nonnull
    protected UserAction getSingleAction() 
      {
        return new MessageSendingUserAction(messageBus,
                                            "Create time report...", 
                                            () -> new ProjectHourlyReportRequest(project));
      }
  }
