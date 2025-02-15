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
package it.tidalwave.accounting.exporter.xml.impl.xml;

import jakarta.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import it.tidalwave.accounting.exporter.xml.impl.adapters.IdAdapter;
import it.tidalwave.accounting.model.Customer;
import it.tidalwave.util.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static javax.xml.bind.annotation.XmlAccessOrder.ALPHABETICAL;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
//@Mutable
@NoArgsConstructor
@XmlRootElement(name = "customer") @XmlAccessorType(FIELD) @XmlAccessorOrder(ALPHABETICAL)
public class CustomerXml 
  {
    @Getter // FIXME
    
    @XmlAttribute(name = "id") 
    @XmlID
    @XmlJavaTypeAdapter(IdAdapter.class)
    private Id id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "billingAddress")
    private AddressXml billingAddressXml;

    @XmlElement(name = "vatNumber")
    private String vatNumber;
    
    public CustomerXml (@Nonnull final Customer customer)
      {
        final var builder = customer.toBuilder();
        this.id = builder.getId();
        this.name = builder.getName();
        this.billingAddressXml = new AddressXml(builder.getBillingAddress());
        this.vatNumber = builder.getVatNumber();
      }
    
    @Nonnull
    public Customer.Builder toBuilder()
      {
        return new Customer.Builder().withId(id)
                                     .withName(name)
                                     .withBillingAddress(billingAddressXml.toAddress())
                                     .withVatNumber(vatNumber);
      }
  }
