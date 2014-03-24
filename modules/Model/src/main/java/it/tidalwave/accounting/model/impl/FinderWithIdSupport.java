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
package it.tidalwave.accounting.model.impl;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import it.tidalwave.util.Finder;
import it.tidalwave.util.FinderStream;
import it.tidalwave.util.FinderStreamSupport;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.ExtendedFinderSupport;

/***********************************************************************************************************************
 *
 * @param <TYPE>
 * @param <FINDER>
 * 
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public abstract class FinderWithIdSupport<TYPE, FINDER extends ExtendedFinderSupport<TYPE, FINDER>> 
                                extends FinderStreamSupport<TYPE, FINDER>
                                implements ExtendedFinderSupport<TYPE, FINDER>, FinderStream<TYPE>, Finder<TYPE>
  {
    @CheckForNull
    /* package */ Id id;
    
    @Nonnull
    public FINDER withId (final @Nonnull Id id)
      {
        final FinderWithIdSupport clone = (FinderWithIdSupport)super.clone();
        clone.id = id;
        return (FINDER)clone;
      }

    @Override
    protected List<? extends TYPE> computeResults()
      {
        if (id != null)
          {
            final TYPE item = findById(id);
            return (item != null) ? Collections.singletonList(item) : Collections.<TYPE>emptyList();
          }
        else
          {
            return new ArrayList<>(findAll());
          }
      }
    
    @Nonnull
    protected abstract Collection<? extends TYPE> findAll();
    
    @CheckForNull
    protected abstract TYPE findById (@Nonnull Id id);
  }

