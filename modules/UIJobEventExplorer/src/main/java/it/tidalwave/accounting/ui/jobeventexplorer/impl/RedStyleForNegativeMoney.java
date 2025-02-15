/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2025 by Tidalwave s.a.s. (http://tidalwave.it)
 *
 * *************************************************************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 *
 * *************************************************************************************************************************************************************
 *
 * git clone https://bitbucket.org/tidalwave/bluehour-src
 * git clone https://github.com/tidalwave-it/bluehour-src
 *
 * *************************************************************************************************************************************************************
 */
package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import jakarta.annotation.Nonnull;
// import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.math.BigDecimal;
import it.tidalwave.accounting.model.types.Money;
import it.tidalwave.ui.core.role.Styleable;
import lombok.RequiredArgsConstructor;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
/* @Immutable */ @RequiredArgsConstructor 
public class RedStyleForNegativeMoney implements Styleable
  {
    @Nonnull
    private final Supplier<Money> moneySupplier;
    
    @Override @Nonnull
    public Collection<String> getStyles() 
      {
        return List.of(moneySupplier.get().getAmount().compareTo(BigDecimal.ZERO) >= 0 ? "" : "red");
      }
  }
