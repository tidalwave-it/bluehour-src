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
public class FinderStreamSupport<Type, ExtendedFinder extends Finder<Type>> 
                                extends FinderSupport<Type, ExtendedFinder>
                                implements ExtendedFinderSupport<Type, ExtendedFinder>, 
                                           FinderStream<Type>
  {
    @Nonnull
    public Optional<Type> optionalResult()
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
    
    @Override
    public Stream<Type> filter(Predicate<? super Type> predicate) 
      {
        return getResultsAsList().stream().filter(predicate);
      }

    @Override
    public <R> Stream<R> map(Function<? super Type, ? extends R> mapper)
      {
        return getResultsAsList().stream().map(mapper);
      }

    @Override
    public IntStream mapToInt(ToIntFunction<? super Type> mapper)
      {
        return getResultsAsList().stream().mapToInt(mapper);
      }

    @Override
    public LongStream mapToLong(ToLongFunction<? super Type> mapper) 
      {
        return getResultsAsList().stream().mapToLong(mapper);
      }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super Type> mapper) 
      {
        return getResultsAsList().stream().mapToDouble(mapper);
      }

    @Override
    public <R> Stream<R> flatMap(Function<? super Type, ? extends Stream<? extends R>> mapper) 
      {
        return getResultsAsList().stream().flatMap(mapper);
      }
 
    @Override
    public IntStream flatMapToInt(Function<? super Type, ? extends IntStream> mapper) 
      {
        return getResultsAsList().stream().flatMapToInt(mapper);
      }

    @Override
    public LongStream flatMapToLong(Function<? super Type, ? extends LongStream> mapper) 
      {
        return getResultsAsList().stream().flatMapToLong(mapper);
      }  

    @Override
    public DoubleStream flatMapToDouble(Function<? super Type, ? extends DoubleStream> mapper) 
      { 
        return getResultsAsList().stream().flatMapToDouble(mapper);
      }

    @Override
    public Stream<Type> distinct() 
      {
        return getResultsAsList().stream().distinct();
      }

    @Override
    public Stream<Type> sorted()  
      {
        return getResultsAsList().stream().sorted();
      }

    @Override
    public Stream<Type> sorted(Comparator<? super Type> comparator) 
      {
        return getResultsAsList().stream().sorted(comparator);
      }

    @Override
    public Stream<Type> peek(Consumer<? super Type> action) 
      {
        return getResultsAsList().stream().peek(action);
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
        getResultsAsList().stream().forEach(action);
      }

    @Override
    public void forEachOrdered(Consumer<? super Type> action) 
      {
        getResultsAsList().stream().forEachOrdered(action);
      } 

    @Override
    public Object[] toArray() 
      {
        return getResultsAsList().stream().toArray();
      } 

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) 
      {
        return getResultsAsList().stream().toArray(generator);
      }

    @Override
    public Type reduce(Type identity, BinaryOperator<Type> accumulator) 
      {
        return getResultsAsList().stream().reduce(identity, accumulator);
      }

    @Override
    public Optional<Type> reduce(BinaryOperator<Type> accumulator) 
      {
        return getResultsAsList().stream().reduce(accumulator);
      }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super Type, U> accumulator, BinaryOperator<U> combiner) 
      {
        return getResultsAsList().stream().reduce(identity, accumulator, combiner);
      }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Type> accumulator, BiConsumer<R, R> combiner) 
      {
        return getResultsAsList().stream().collect(supplier, accumulator, combiner);
      }

    @Override
    public <R, A> R collect(Collector<? super Type, A, R> collector)  
      {
        return getResultsAsList().stream().collect(collector);
      }

    @Override
    public Optional<Type> min(Comparator<? super Type> comparator) 
      {
        return getResultsAsList().stream().min(comparator);
      }

    @Override
    public Optional<Type> max(Comparator<? super Type> comparator) 
      {
        return getResultsAsList().stream().max(comparator);
      } 

//    @Override
//    public long count() 
//      {
//        return ((List<Type>)results()).stream().count();
//      }

    @Override
    public boolean anyMatch(Predicate<? super Type> predicate) 
      {
        return getResultsAsList().stream().anyMatch(predicate);
      }

    @Override
    public boolean allMatch(Predicate<? super Type> predicate) 
      {
        return getResultsAsList().stream().allMatch(predicate);
      }

    @Override
    public boolean noneMatch(Predicate<? super Type> predicate) 
      {
        return getResultsAsList().stream().noneMatch(predicate);
      }

    @Override
    public Optional<Type> findFirst() 
      {
        return getResultsAsList().stream().findFirst();
      }

    @Override
    public Optional<Type> findAny() 
      {
        return getResultsAsList().stream().findAny();
      }  

    @Override
    public Iterator<Type> iterator() 
      {
        return getResultsAsList().stream().iterator();
      }

    @Override
    public Spliterator<Type> spliterator() 
      {
        return getResultsAsList().stream().spliterator();
      }

    @Override
    public boolean isParallel() 
      {
        return getResultsAsList().stream().isParallel();
      }

    @Override
    public Stream<Type> sequential() 
      {
        return getResultsAsList().stream().sequential();
      }

    @Override
    public Stream<Type> parallel() 
      {
        return getResultsAsList().stream().parallel();
      }

    @Override
    public Stream<Type> unordered() 
      {
        return getResultsAsList().stream().unordered();
      }

    @Override
    public Stream<Type> onClose(Runnable closeHandler) 
      {
        return getResultsAsList().stream().onClose(closeHandler);
      }

    @Override
    public void close() 
      {
        getResultsAsList().stream().close();
      }
    
    @Nonnull
    private List<Type> getResultsAsList()
      {
        return (List<Type>)results();
      }
  }
