/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2015 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.model.types;

import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class MoneyTest
  {
    @Test
    public void toString_must_be_properly_computed()
      {
        final Money m1 = new Money(4053, "EUR");
        assertThat(m1.toString(), is("4053.00 EUR"));

      }
    @Test
    public void toString_for_ZERO_must_be_properly_computed()
      {
        assertThat(Money.ZERO.toString(), is("0.00 EUR"));
      }
    
    @Test
    public void isEqualTo_must_work()
      {
        final Money m1 = new Money(349, "EUR");
        final Money m2 = new Money(349, "EUR");
        final Money m3 = new Money(495, "EUR");
        
        assertThat(m1.isEqualTo(m2), is(true));
        assertThat(m2.isEqualTo(m1), is(true));
        assertThat(m1.isEqualTo(m3), is(false));
        assertThat(m3.isEqualTo(m1), is(false));
      }
    
    @Test
    public void greaterThan_and_lowerThan_must_work_at_the_opposite()
      {
        final Money greater = new Money(349, "EUR");
        final Money lower = new Money(130, "EUR");
        assertThat(greater.greaterThan(lower), is(true));  
        assertThat(greater.lowerThan(lower)  , is(false));  
        assertThat(lower.greaterThan(greater), is(false));  
        assertThat(lower.lowerThan(greater),   is(true));  
      }
    
    @Test
    public void greaterThan_and_lowerThan_must_be_false_when_equality()
      {
        final Money m1 = new Money(349, "EUR");
        final Money m2 = new Money(349, "EUR");
        assertThat(m1.greaterThan(m2), is(false));  
        assertThat(m1.lowerThan(m2),   is(false));  
        assertThat(m2.greaterThan(m1), is(false));  
        assertThat(m2.lowerThan(m1),   is(false));  
      }
  }