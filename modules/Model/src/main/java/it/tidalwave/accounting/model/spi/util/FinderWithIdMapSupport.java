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
package it.tidalwave.accounting.model.spi.util;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @param <TYPE>
 * @param <FINDER>
 * 
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class FinderWithIdMapSupport<TYPE, FINDER extends ExtendedFinderSupport<TYPE, FINDER>> 
  extends FinderWithIdSupport<TYPE, FINDER>
  {
    @Nonnull
    private final Map<Id, TYPE> mapById;
    
    @Override @Nonnull
    protected Collection<? extends TYPE> findAll()
      {
        return mapById.values();
      }
    
    @Override @CheckForNull
    protected TYPE findById (final @Nonnull Id id)
      {
        return mapById.get(id);
      }
  }

