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
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import it.tidalwave.accounting.model.Money;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class MoneyCollector implements Collector<Money, MoneyAccumulator, Money>
  {
    @Override @Nonnull 
    public Supplier<MoneyAccumulator> supplier() 
      {
        return MoneyAccumulator::new;
      } 

    @Override @Nonnull 
    public BiConsumer<MoneyAccumulator, Money> accumulator() 
      {
        return MoneyAccumulator::accumulate;
      }

    @Override @Nonnull 
    public BinaryOperator<MoneyAccumulator> combiner() 
      {
        return MoneyAccumulator::combine;
      }

    @Override
    public Function<MoneyAccumulator, Money> finisher() 
      {
        return MoneyAccumulator::getValue;
      }
    
    @Override @Nonnull 
    public Set<Characteristics> characteristics() 
      {
        return Collections.singleton(Characteristics.CONCURRENT);
      }
  }
