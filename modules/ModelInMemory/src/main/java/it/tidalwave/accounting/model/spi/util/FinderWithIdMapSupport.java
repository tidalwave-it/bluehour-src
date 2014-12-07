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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @param <TYPE>
 * @param <IMPLTYPE>
 * @param <FINDER>
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class FinderWithIdMapSupport<TYPE, IMPLTYPE extends TYPE, FINDER extends ExtendedFinderSupport<TYPE, FINDER>>
  extends FinderWithIdSupport<TYPE, IMPLTYPE, FINDER>
  {
    private static final long serialVersionUID = 1L;

    @Nonnull
    private final Map<Id, IMPLTYPE> mapById;

    @Override @Nonnull
    protected List<IMPLTYPE> findAll()
      {
        return new ArrayList<>(mapById.values());
      }

    @Override @Nonnull
    protected Optional<IMPLTYPE> findById (final @Nonnull Id id)
      {
        return Optional.ofNullable(mapById.get(id));
      }
    
    @Nonnull
    public Stream<IMPLTYPE> implStream()
      {
        return (Stream<IMPLTYPE>)super.stream();
      }
  }

