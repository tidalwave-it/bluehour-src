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
package it.tidalwave.accounting.ui.customerexplorer.impl;

import jakarta.annotation.Nonnull;
import java.util.Collection;
import it.tidalwave.accounting.model.spi.CustomerSpi;
import it.tidalwave.ui.core.role.Displayable;
import it.tidalwave.ui.core.role.Presentable;
import it.tidalwave.ui.core.role.PresentationModel;
import it.tidalwave.dci.annotation.DciRole;
import lombok.RequiredArgsConstructor;
import static it.tidalwave.util.Parameters.r;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@DciRole(datumType = CustomerSpi.class) @RequiredArgsConstructor
public class CustomerPresentable implements Presentable
  {
    @Nonnull
    private final CustomerSpi customer;

    @Override @Nonnull
    public PresentationModel createPresentationModel (@Nonnull final Collection<Object> instanceRoles)
      {
        return PresentationModel.of(customer, r(Displayable.of(customer.getName()), instanceRoles));
      }
  }
