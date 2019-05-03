/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2019 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.model.impl;

import it.tidalwave.util.As;
import it.tidalwave.util.spi.AsSupport;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.CustomerRegistry;
import it.tidalwave.accounting.model.InvoiceRegistry;
import it.tidalwave.accounting.model.ProjectRegistry;
import lombok.Delegate;
import lombok.Getter;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class InMemoryAccounting implements Accounting
  {
    @Delegate
    private final As asSupport = new AsSupport(this);

    @Getter
    private final CustomerRegistry customerRegistry = new InMemoryCustomerRegistry(this);

    @Getter
    private final ProjectRegistry projectRegistry = new InMemoryProjectRegistry(this);
    
    @Getter
    private final InvoiceRegistry invoiceRegistry = new InMemoryInvoiceRegistry();
  }
