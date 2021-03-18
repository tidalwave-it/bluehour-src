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
package it.tidalwave.accounting.model.spi.util;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import it.tidalwave.util.Finder;
import it.tidalwave.util.spi.FinderSupport;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;
import static java.util.Collections.*;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * A support class for implementing a {@link Finder} that provides filtering by id.
 * 
 * @param <TYPE>     the product abstract type
 * @param <IMPLTYPE> the product concrete type
 * @param <FINDER>   the {@code Finder} type
 * 
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class FinderWithIdSupport<TYPE, IMPLTYPE extends TYPE, FINDER extends ExtendedFinderSupport<TYPE, FINDER>>
                                extends FinderSupport<TYPE, FINDER>
                                implements ExtendedFinderSupport<TYPE, FINDER>,
                                           Finder<TYPE>
  {
    private static final long serialVersionUID = 2L;
    
    @Nonnull
    /* package */ final Optional<Id> id;

    public FinderWithIdSupport()
      {
        id = Optional.empty();
      }
    
    public FinderWithIdSupport (final @Nonnull FinderWithIdSupport<TYPE, IMPLTYPE, FINDER> other, 
                                final @Nonnull Object override) 
      {
        super(other, override);
        final FinderWithIdSupport<TYPE, IMPLTYPE, FINDER> source = getSource(FinderWithIdSupport.class, other, override);
        this.id = source.id;
      }
    
    @Nonnull
    public FINDER withId (final @Nonnull Id id)
      {
        return clone(new FinderWithIdSupport<>(Optional.of(id)));
      }

    @Override @Nonnull
    protected List<IMPLTYPE> computeResults()
      {
        return id.map(id -> findById(id).map(item -> singletonList(item)).orElse(emptyList()))
                 .orElse(findAll());
      }
    
    @Nonnull
    protected List<IMPLTYPE> findAll()
      {
        throw new UnsupportedOperationException("Must be overridden");
      }
    
    @Nonnull
    protected Optional<IMPLTYPE> findById (@Nonnull Id id)
      {
        throw new UnsupportedOperationException("Must be overridden");
      }
    
    @Nonnull
    protected Stream<IMPLTYPE> streamImpl()
      {
        return (Stream<IMPLTYPE>)stream();
      }
  }

