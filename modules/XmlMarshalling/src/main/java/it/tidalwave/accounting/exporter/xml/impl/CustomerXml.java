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
package it.tidalwave.accounting.exporter.xml.impl;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import it.tidalwave.util.Id;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.accounting.exporter.xml.impl.adapters.IdAdapter;
import lombok.NoArgsConstructor;
import static javax.xml.bind.annotation.XmlAccessOrder.*;
import static javax.xml.bind.annotation.XmlAccessType.*;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
//@Mutable
@NoArgsConstructor
@XmlRootElement(name = "customer") @XmlAccessorType(FIELD) @XmlAccessorOrder(ALPHABETICAL)
public class CustomerXml 
  {
    @XmlAttribute(name = "id") 
    @XmlID
    @XmlJavaTypeAdapter(IdAdapter.class)
    private Id id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "billingAddress")
    private AddressXml billingAddress;

    @XmlElement(name = "vatNumber")
    private String vatNumber;
    
    public CustomerXml (final @Nonnull Customer customer)
      {
        final Customer.Builder b = customer.asBuilder();
        this.id = b.getId();
        this.name = b.getName();
        this.billingAddress = new AddressXml(b.getBillingAddress());
        this.vatNumber = b.getVatNumber();
      }
  }
