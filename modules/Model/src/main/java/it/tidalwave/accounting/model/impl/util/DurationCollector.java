/*
 * #%L
 * *********************************************************************************************************************
 *
 * NorthernWind - lightweight CMS
 * http://northernwind.tidalwave.it - hg clone https://bitbucket.org/tidalwave/northernwind-src
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
package it.tidalwave.accounting.model.impl.util;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class DurationCollector implements Collector<Duration, DurationAccumulator, Duration>
  {
    @Override @Nonnull 
    public Supplier<DurationAccumulator> supplier() 
      {
        return DurationAccumulator::new;
      } 

    @Override @Nonnull 
    public BiConsumer<DurationAccumulator, Duration> accumulator() 
      {
        return DurationAccumulator::accumulate;
      }

    @Override @Nonnull 
    public BinaryOperator<DurationAccumulator> combiner() 
      {
        return DurationAccumulator::combine;
      }

    @Override
    public Function<DurationAccumulator, Duration> finisher() 
      {
        return DurationAccumulator::getValue;
      }
    
    @Override @Nonnull 
    public Set<Characteristics> characteristics() 
      {
        return Collections.singleton(Characteristics.CONCURRENT);
      }
  }
