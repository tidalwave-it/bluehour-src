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
package it.tidalwave.util;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import it.tidalwave.util.spi.ExtendedFinderSupport;
import it.tidalwave.util.spi.FinderSupport;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public class FinderStreamSupport<TYPE, EXTENDED_FINDER extends Finder<TYPE>> 
                                extends FinderSupport<TYPE, EXTENDED_FINDER>
                                implements ExtendedFinderSupport<TYPE, EXTENDED_FINDER>, 
                                           FinderStream<TYPE>
  {
    @Override @Nonnull
    public Optional<TYPE> optionalResult()
      {
        try 
          {
            return Optional.of(result());
          } 
        catch (NotFoundException e) 
          {
            return Optional.empty();
          }
      }
    
    @Override @Nonnull
    public Stream<TYPE> stream() 
      {
        return ((List<TYPE>)results()).stream();
      }
    
    @Override
    public Stream<TYPE> filter(Predicate<? super TYPE> predicate) 
      {
        return stream().filter(predicate);
      }

    @Override
    public <R> Stream<R> map(Function<? super TYPE, ? extends R> mapper)
      {
        return stream().map(mapper);
      }

    @Override
    public IntStream mapToInt(ToIntFunction<? super TYPE> mapper)
      {
        return stream().mapToInt(mapper);
      }

    @Override
    public LongStream mapToLong(ToLongFunction<? super TYPE> mapper) 
      {
        return stream().mapToLong(mapper);
      }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super TYPE> mapper) 
      {
        return stream().mapToDouble(mapper);
      }

    @Override
    public <R> Stream<R> flatMap(Function<? super TYPE, ? extends Stream<? extends R>> mapper) 
      {
        return stream().flatMap(mapper);
      }
 
    @Override
    public IntStream flatMapToInt(Function<? super TYPE, ? extends IntStream> mapper) 
      {
        return stream().flatMapToInt(mapper);
      }

    @Override
    public LongStream flatMapToLong(Function<? super TYPE, ? extends LongStream> mapper) 
      {
        return stream().flatMapToLong(mapper);
      }  

    @Override
    public DoubleStream flatMapToDouble(Function<? super TYPE, ? extends DoubleStream> mapper) 
      { 
        return stream().flatMapToDouble(mapper);
      }

    @Override
    public Stream<TYPE> distinct() 
      {
        return stream().distinct();
      }

    @Override
    public Stream<TYPE> sorted()  
      {
        return stream().sorted();
      }

    @Override
    public Stream<TYPE> sorted(Comparator<? super TYPE> comparator) 
      {
        return stream().sorted(comparator);
      }

    @Override
    public Stream<TYPE> peek(Consumer<? super TYPE> action) 
      {
        return stream().peek(action);
      }  

//    @Override
//    public Stream<Type> limit(long maxSize) 
//      {
//        return stream().limit(maxSize);
//      }
//
//    @Override
//    public Stream<Type> skip(long n) 
//      {
//        return stream().skip(n);
//      } 

    @Override
    public void forEach(Consumer<? super TYPE> action) 
      {
        stream().forEach(action);
      }

    @Override
    public void forEachOrdered(Consumer<? super TYPE> action) 
      {
        stream().forEachOrdered(action);
      } 

    @Override
    public Object[] toArray() 
      {
        return stream().toArray();
      } 

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) 
      {
        return stream().toArray(generator);
      }

    @Override
    public TYPE reduce(TYPE identity, BinaryOperator<TYPE> accumulator) 
      {
        return stream().reduce(identity, accumulator);
      }

    @Override
    public Optional<TYPE> reduce(BinaryOperator<TYPE> accumulator) 
      {
        return stream().reduce(accumulator);
      }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super TYPE, U> accumulator, BinaryOperator<U> combiner) 
      {
        return stream().reduce(identity, accumulator, combiner);
      }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super TYPE> accumulator, BiConsumer<R, R> combiner) 
      {
        return stream().collect(supplier, accumulator, combiner);
      }

    @Override
    public <R, A> R collect(Collector<? super TYPE, A, R> collector)  
      {
        return stream().collect(collector);
      }

    @Override
    public Optional<TYPE> min(Comparator<? super TYPE> comparator) 
      {
        return stream().min(comparator);
      }

    @Override
    public Optional<TYPE> max(Comparator<? super TYPE> comparator) 
      {
        return stream().max(comparator);
      } 

//    @Override
//    public long count() 
//      {
//        return stream().count();
//      }

    @Override
    public boolean anyMatch(Predicate<? super TYPE> predicate) 
      {
        return stream().anyMatch(predicate);
      }

    @Override
    public boolean allMatch(Predicate<? super TYPE> predicate) 
      {
        return stream().allMatch(predicate);
      }

    @Override
    public boolean noneMatch(Predicate<? super TYPE> predicate) 
      {
        return stream().noneMatch(predicate);
      }

    @Override
    public Optional<TYPE> findFirst() 
      {
        return stream().findFirst();
      }

    @Override
    public Optional<TYPE> findAny() 
      {
        return stream().findAny();
      }  

    @Override
    public Iterator<TYPE> iterator() 
      {
        return stream().iterator();
      }

    @Override
    public Spliterator<TYPE> spliterator() 
      {
        return stream().spliterator();
      }

    @Override
    public boolean isParallel() 
      {
        return stream().isParallel();
      }

    @Override
    public Stream<TYPE> sequential() 
      {
        return stream().sequential();
      }

    @Override
    public Stream<TYPE> parallel() 
      {
        return stream().parallel();
      }

    @Override
    public Stream<TYPE> unordered() 
      {
        return stream().unordered();
      }

    @Override
    public Stream<TYPE> onClose(Runnable closeHandler) 
      {
        return stream().onClose(closeHandler);
      }

    @Override
    public void close() 
      {
        stream().close();
      }
  }
