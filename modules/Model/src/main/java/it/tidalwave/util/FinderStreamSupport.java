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
public class FinderStreamSupport<Type, ExtendedFinder extends Finder<Type>> 
                                extends FinderSupport<Type, ExtendedFinder>
                                implements ExtendedFinderSupport<Type, ExtendedFinder>, 
                                           FinderStream<Type>
  {
    @Override
    public Stream<Type> filter(Predicate<? super Type> predicate) 
      {
        return ((List<Type>)results()).stream().filter(predicate);
      }

    @Override
    public <R> Stream<R> map(Function<? super Type, ? extends R> mapper)
      {
        return ((List<Type>)results()).stream().map(mapper);
      }

    @Override
    public IntStream mapToInt(ToIntFunction<? super Type> mapper)
      {
        return ((List<Type>)results()).stream().mapToInt(mapper);
      }

    @Override
    public LongStream mapToLong(ToLongFunction<? super Type> mapper) 
      {
        return ((List<Type>)results()).stream().mapToLong(mapper);
      }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super Type> mapper) 
      {
        return ((List<Type>)results()).stream().mapToDouble(mapper);
      }

    @Override
    public <R> Stream<R> flatMap(Function<? super Type, ? extends Stream<? extends R>> mapper) 
      {
        return ((List<Type>)results()).stream().flatMap(mapper);
      }
 
    @Override
    public IntStream flatMapToInt(Function<? super Type, ? extends IntStream> mapper) 
      {
        return ((List<Type>)results()).stream().flatMapToInt(mapper);
      }

    @Override
    public LongStream flatMapToLong(Function<? super Type, ? extends LongStream> mapper) 
      {
        return ((List<Type>)results()).stream().flatMapToLong(mapper);
      }  

    @Override
    public DoubleStream flatMapToDouble(Function<? super Type, ? extends DoubleStream> mapper) 
      { 
        return ((List<Type>)results()).stream().flatMapToDouble(mapper);
      }

    @Override
    public Stream<Type> distinct() 
      {
        return ((List<Type>)results()).stream().distinct();
      }

    @Override
    public Stream<Type> sorted()  
      {
        return ((List<Type>)results()).stream().sorted();
      }

    @Override
    public Stream<Type> sorted(Comparator<? super Type> comparator) 
      {
        return ((List<Type>)results()).stream().sorted(comparator);
      }

    @Override
    public Stream<Type> peek(Consumer<? super Type> action) 
      {
        return ((List<Type>)results()).stream().peek(action);
      }  

//    @Override
//    public Stream<Type> limit(long maxSize) 
//      {
//        return ((List<Type>)results()).stream().limit(maxSize);
//      }
//
//    @Override
//    public Stream<Type> skip(long n) 
//      {
//        return ((List<Type>)results()).stream().skip(n);
//      } 

    @Override
    public void forEach(Consumer<? super Type> action) 
      {
        ((List<Type>)results()).stream().forEach(action);
      }

    @Override
    public void forEachOrdered(Consumer<? super Type> action) 
      {
        ((List<Type>)results()).stream().forEachOrdered(action);
      } 

    @Override
    public Object[] toArray() 
      {
        return ((List<Type>)results()).stream().toArray();
      } 

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) 
      {
        return ((List<Type>)results()).stream().toArray(generator);
      }

    @Override
    public Type reduce(Type identity, BinaryOperator<Type> accumulator) 
      {
        return ((List<Type>)results()).stream().reduce(identity, accumulator);
      }

    @Override
    public Optional<Type> reduce(BinaryOperator<Type> accumulator) 
      {
        return ((List<Type>)results()).stream().reduce(accumulator);
      }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super Type, U> accumulator, BinaryOperator<U> combiner) 
      {
        return ((List<Type>)results()).stream().reduce(identity, accumulator, combiner);
      }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Type> accumulator, BiConsumer<R, R> combiner) 
      {
        return ((List<Type>)results()).stream().collect(supplier, accumulator, combiner);
      }

    @Override
    public <R, A> R collect(Collector<? super Type, A, R> collector)  
      {
        return ((List<Type>)results()).stream().collect(collector);
      }

    @Override
    public Optional<Type> min(Comparator<? super Type> comparator) 
      {
        return ((List<Type>)results()).stream().min(comparator);
      }

    @Override
    public Optional<Type> max(Comparator<? super Type> comparator) 
      {
        return ((List<Type>)results()).stream().max(comparator);
      } 

//    @Override
//    public long count() 
//      {
//        return ((List<Type>)results()).stream().count();
//      }

    @Override
    public boolean anyMatch(Predicate<? super Type> predicate) 
      {
        return ((List<Type>)results()).stream().anyMatch(predicate);
      }

    @Override
    public boolean allMatch(Predicate<? super Type> predicate) 
      {
        return ((List<Type>)results()).stream().allMatch(predicate);
      }

    @Override
    public boolean noneMatch(Predicate<? super Type> predicate) 
      {
        return ((List<Type>)results()).stream().noneMatch(predicate);
      }

    @Override
    public Optional<Type> findFirst() 
      {
        return ((List<Type>)results()).stream().findFirst();
      }

    @Override
    public Optional<Type> findAny() 
      {
        return ((List<Type>)results()).stream().findAny();
      }  

    @Override
    public Iterator<Type> iterator() 
      {
        return ((List<Type>)results()).stream().iterator();
      }

    @Override
    public Spliterator<Type> spliterator() 
      {
        return ((List<Type>)results()).stream().spliterator();
      }

    @Override
    public boolean isParallel() 
      {
        return ((List<Type>)results()).stream().isParallel();
      }

    @Override
    public Stream<Type> sequential() 
      {
        return ((List<Type>)results()).stream().sequential();
      }

    @Override
    public Stream<Type> parallel() 
      {
        return ((List<Type>)results()).stream().parallel();
      }

    @Override
    public Stream<Type> unordered() 
      {
        return ((List<Type>)results()).stream().unordered();
      }

    @Override
    public Stream<Type> onClose(Runnable closeHandler) 
      {
        return ((List<Type>)results()).stream().onClose(closeHandler);
      }

    @Override
    public void close() 
      {
        ((List<Type>)results()).stream().close();
      }
  }
