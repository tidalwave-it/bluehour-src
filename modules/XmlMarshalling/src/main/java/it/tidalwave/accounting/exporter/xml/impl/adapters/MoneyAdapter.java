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
package it.tidalwave.accounting.exporter.xml.impl.adapters;

import javax.annotation.Nonnull;
import java.text.ParseException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import it.tidalwave.accounting.model.types.Money;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class MoneyAdapter extends XmlAdapter<String, Money>
  {
    @Override @Nonnull
    public Money unmarshal (final @Nonnull String string) 
      throws ParseException 
      {
        return Money.parse(string);
      }

    @Override @Nonnull
    public String marshal (final @Nonnull Money money) 
      {
        return money.toString();
      }
  }
