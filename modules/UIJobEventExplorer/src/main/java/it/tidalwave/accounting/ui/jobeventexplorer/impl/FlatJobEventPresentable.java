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
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.role.ui.Displayable;
import it.tidalwave.accounting.model.spi.FlatJobEventSpi;
import it.tidalwave.role.ui.Displayable2;
import it.tidalwave.role.ui.spi.PresentationModelAggregate;
import static it.tidalwave.accounting.commons.Styleables.RIGHT_ALIGNED;
import static it.tidalwave.accounting.model.spi.util.Formatters.*;
import static it.tidalwave.role.ui.spi.PresentationModelAggregate.r;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
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
    protected PresentationModelAggregate presentationModelAggregate()
      {
        return super.presentationModelAggregate()
                .withPmOf(DATE,        r(Displayable2.of(DATE_FORMATTER, jobEvent.getDate())))
                .withPmOf(TIME,        r(Displayable.of("")))
                .withPmOf(HOURLY_RATE, r(Displayable.of("")))
                .withPmOf(AMOUNT ,     r(Displayable2.of(MONEY_FORMATTER, jobEvent.getEarnings()),
                                           RIGHT_ALIGNED, new RedStyleForNegativeMoney(jobEvent::getEarnings)));
      }

    @Override @Nonnull
    protected Collection<String> getStyles()
      {
        return Arrays.asList("flat-job-event");
      }
  }
