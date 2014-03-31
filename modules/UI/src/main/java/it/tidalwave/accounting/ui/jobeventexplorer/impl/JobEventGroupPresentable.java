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
package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.spi.DefaultDisplayable;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.accounting.model.JobEventGroup;
import static it.tidalwave.role.ui.Presentable.Presentable;
import static it.tidalwave.role.ui.spi.PresentationModelCollectors.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = JobEventGroup.class)
public class JobEventGroupPresentable extends JobEventPresentable
  {
    @Nonnull
    private final JobEventGroup jobEventGroup;
    
    public JobEventGroupPresentable (final @Nonnull JobEventGroup jobEventGroup)
      {
        super(jobEventGroup);
        this.jobEventGroup = jobEventGroup;
      }
    
    @Override @Nonnull
    public PresentationModel createPresentationModel (final @Nonnull Object... instanceRoles) 
      {
        return jobEventGroup.findChildren()
                .map(jobEvent -> jobEvent.as(Presentable).createPresentationModel())
                .collect(toContainerPresentationModel(aggregateBuilder().create()));
        // FIXME: use SimpleCompositePresentable?
      }

    @Override @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder() 
      {
        final AggregatePresentationModelBuilder builder = super.aggregateBuilder();
        builder.add("Rate",   new DefaultDisplayable(""));
        builder.add("Time",   new DefaultDisplayable("")); // FIXME: compute sum
        builder.add("Amount", new DefaultDisplayable("")); // FIXME: compute sum
        
        return builder;
      }
  }
