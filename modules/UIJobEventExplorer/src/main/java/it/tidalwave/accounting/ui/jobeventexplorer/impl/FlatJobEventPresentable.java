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
import java.util.Arrays;
import java.util.Collection;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.role.Displayable;
import it.tidalwave.role.spi.DefaultDisplayable;
import it.tidalwave.role.ui.spi.DefaultStyleable;
import it.tidalwave.accounting.model.spi.FlatJobEventSpi;
import it.tidalwave.accounting.util.AggregatePresentationModelBuilder;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = FlatJobEventSpi.class)
public class FlatJobEventPresentable extends JobEventPresentable<FlatJobEventSpi>
  {
    public FlatJobEventPresentable (final @Nonnull FlatJobEventSpi flatJobEvent)
      {
        super(flatJobEvent);
      }
    
    @Override @Nonnull
    protected AggregatePresentationModelBuilder aggregateBuilder() 
      {
        final AggregatePresentationModelBuilder builder = super.aggregateBuilder();
        builder.put(DATE,        (Displayable) () -> DATE_FORMATTER.format(jobEvent.getDate()));
        builder.put(TIME,        new DefaultDisplayable(""));
        builder.put(HOURLY_RATE, new DefaultDisplayable(""));
        builder.put(AMOUNT ,     (Displayable) () -> MONEY_FORMATTER.format(jobEvent.getEarnings()),
                                 new DefaultStyleable("right-aligned"),
                                 new RedStyleForNegativeMoney(jobEvent::getEarnings));
        return builder;
      }
    
    @Override @Nonnull
    protected Collection<String> getStyles() 
      {
        return Arrays.asList("flat-job-event");
      }
  }