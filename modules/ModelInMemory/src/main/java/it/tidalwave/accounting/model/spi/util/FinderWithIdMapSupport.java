/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinder8Support;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * An implementation of {@link FinderWithIdSupport} based on a {@link Map}.
 * 
 * @param <TYPE>     the product abstract type
 * @param <IMPLTYPE> the product concrete type
 * @param <FINDER>   the {@code Finder} type
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class FinderWithIdMapSupport<TYPE, IMPLTYPE extends TYPE, FINDER extends ExtendedFinder8Support<TYPE, FINDER>>
  extends FinderWithIdSupport<TYPE, IMPLTYPE, FINDER>
  {
    private static final long serialVersionUID = 1L;

    @Nonnull
    private final Map<Id, IMPLTYPE> mapById;

    public FinderWithIdMapSupport()
      {
        mapById = Collections.emptyMap();
      }
    
    public FinderWithIdMapSupport (final @Nonnull FinderWithIdMapSupport<TYPE, IMPLTYPE, FINDER> other,
                                   final @Nonnull Object override) 
      {
        super(other, override);
        final FinderWithIdMapSupport<TYPE, IMPLTYPE, FINDER> source = getSource(FinderWithIdMapSupport.class, other, override);
        mapById = new HashMap<>(source.mapById);
      }
    
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
   }

