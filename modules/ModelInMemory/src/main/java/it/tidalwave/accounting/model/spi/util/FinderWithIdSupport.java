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
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import it.tidalwave.util.Finder;
import it.tidalwave.util.Finder8;
import it.tidalwave.util.Finder8Support;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;
import static java.util.Collections.*;

/***********************************************************************************************************************
 *
 * A support class for implementing a {@link Finder} that provides filtering by id.
 * 
 * @param <TYPE>     the product abstract type
 * @param <IMPLTYPE> the product concrete type
 * @param <FINDER>   the {@code Finder} type
 * 
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public abstract class FinderWithIdSupport<TYPE, IMPLTYPE extends TYPE, FINDER extends ExtendedFinderSupport<TYPE, FINDER>> 
                                extends Finder8Support<TYPE, FINDER>
                                implements ExtendedFinderSupport<TYPE, FINDER>, 
                                           Finder8<TYPE>
  {
    private static final long serialVersionUID = 2L;
    
    @Nonnull
    /* package */ Optional<Id> id = Optional.empty();
    
    @Nonnull
    public FINDER withId (final @Nonnull Id id)
      {
        final FinderWithIdSupport<TYPE, IMPLTYPE, FINDER> clone = (FinderWithIdSupport<TYPE, IMPLTYPE, FINDER>)super.clone();
        clone.id = Optional.of(id);
        return (FINDER)clone;
      }

    @Override @Nonnull
    protected List<IMPLTYPE> computeResults()
      {
        return id.map(id -> findById(id).map(item -> singletonList(item)).orElse(emptyList()))
                 .orElse(findAll());
      }
    
    @Nonnull
    protected abstract List<IMPLTYPE> findAll();
    
    @Nonnull
    protected abstract Optional<IMPLTYPE> findById (@Nonnull Id id);
    
    @Nonnull
    protected Stream<IMPLTYPE> streamImpl()
      {
        return (Stream<IMPLTYPE>)stream();
      }
  }
