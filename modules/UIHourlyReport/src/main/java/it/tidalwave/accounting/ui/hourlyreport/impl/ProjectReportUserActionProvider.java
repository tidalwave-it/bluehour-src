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
import java.util.Collection;
import java.util.Collections;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.role.spi.DefaultDisplayable;
import it.tidalwave.role.ui.UserAction;
import it.tidalwave.role.ui.spi.DefaultUserActionProvider;
import it.tidalwave.role.ui.spi.UserActionSupport;
import it.tidalwave.accounting.model.Project;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.accounting.model.HourlyReportGenerator.HourlyReportGenerator;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = Project.class) @RequiredArgsConstructor
public class ProjectReportUserActionProvider extends DefaultUserActionProvider
  {
    @Nonnull
    private final Project project;
    
    @Override @Nonnull
    public Collection<? extends UserAction> getActions() 
      {
        return Collections.singletonList(new UserActionSupport(new DefaultDisplayable("Create time report...")) 
          {
            @Override
            public void actionPerformed() 
              {
                System.out.println(project.as(HourlyReportGenerator).createReport().asString());
              }
          });
      }
  }
