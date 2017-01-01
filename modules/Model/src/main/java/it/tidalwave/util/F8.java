/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2017 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.util;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import it.tidalwave.util.spi.ExtendedFinder8Support;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici (Fabrizio.Giudici@tidalwave.it)
 * @version $Id$
 *
 **********************************************************************************************************************/
// TODO: merge to Finder8
public interface F8<TYPE, EXTENDED_FINDER extends Finder8<TYPE>> extends ExtendedFinder8Support<TYPE, EXTENDED_FINDER>
  {
    static class LambdaFinder<TYPE, EXTENDED_FINDER extends Finder8<TYPE>>
            extends Finder8Support<TYPE, EXTENDED_FINDER>
            implements F8<TYPE, EXTENDED_FINDER>
      {
        private static final long serialVersionUID = 2688397754942922706L;

        @Nonnull
        private final Optional<Function<F8<? extends TYPE, EXTENDED_FINDER>, List<TYPE>>> computeResults;

        @Nonnull
        private final Optional<Function<F8<? extends TYPE, EXTENDED_FINDER>, List<TYPE>>> computeNeededResults;

        private LambdaFinder (final @Nonnull Optional<Function<F8<? extends TYPE, EXTENDED_FINDER>, List<TYPE>>> computeResults,
                              final @Nonnull Optional<Function<F8<? extends TYPE, EXTENDED_FINDER>, List<TYPE>>> computeNeededResults)
          {
            this.computeResults = computeResults;
            this.computeNeededResults = computeNeededResults;

            if (!this.computeResults.isPresent() && !this.computeNeededResults.isPresent())
              {
                throw new ExceptionInInitializerError("One of computeResults or computeNeededResults must be present");
              }
          }

        public LambdaFinder (final @Nonnull LambdaFinder<TYPE, EXTENDED_FINDER> other, final @Nonnull Object override)
          {
            super(other, override);
            final LambdaFinder<TYPE, EXTENDED_FINDER> source = getSource(LambdaFinder.class, other, override);
            this.computeResults = source.computeResults;
            this.computeNeededResults = source.computeNeededResults;
          }

        @Override @Nonnull
        protected final List<? extends TYPE> computeResults()
          {
            return computeResults.isPresent() ? computeResults.get().apply(this) : super.computeResults();
//            return computeResults.flatMap(f -> f.apply(this)).orElse((List)super.computeResults());
          }

        @Override @Nonnull
        protected final List<? extends TYPE> computeNeededResults()
          {
            return computeNeededResults.isPresent() ? computeNeededResults.get().apply(this) : super.computeNeededResults();
//            return computeNeededResults.map(f -> f.apply(this)).orElse((List)super.computeNeededResults());
          }
      }

    @Nonnull
    public static <TYPE, EXTENDED_FINDER extends Finder8<TYPE>> F8<TYPE, EXTENDED_FINDER> ofComputeResults (
            final @Nonnull Function<F8<? extends TYPE, EXTENDED_FINDER>, List<TYPE>> computeResults)
      {
        return new LambdaFinder(Optional.of(computeResults), Optional.empty());
      }

    @Nonnull
    public static <TYPE, EXTENDED_FINDER extends Finder8<TYPE>> F8<TYPE, EXTENDED_FINDER> ofComputeNeededResults (
            final @Nonnull Function<F8<? extends TYPE, EXTENDED_FINDER>, List<TYPE>> computeNeededResults)
      {
        return new LambdaFinder(Optional.empty(), Optional.of(computeNeededResults));
      }
  }
