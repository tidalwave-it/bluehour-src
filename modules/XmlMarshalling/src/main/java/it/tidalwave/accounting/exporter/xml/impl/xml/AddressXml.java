/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2017 Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.exporter.xml.impl.xml;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import it.tidalwave.accounting.model.types.Address;
import lombok.NoArgsConstructor;
import static javax.xml.bind.annotation.XmlAccessOrder.ALPHABETICAL;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@NoArgsConstructor
@XmlRootElement(name = "address") @XmlAccessorType(FIELD) @XmlAccessorOrder(ALPHABETICAL)
public class AddressXml 
  {
    @XmlElement(name = "street")
    private String street;
    
    @XmlElement(name = "city")
    private String city;
    
    @XmlElement(name = "zip")
    private String zip;
    
    @XmlElement(name = "state")
    private String state;
    
    @XmlElement(name = "country")
    private String country;
    
    public AddressXml (final @Nonnull Address address)
      {
        this.street = address.getStreet();
        this.city = address.getCity();
        this.zip = address.getZip();
        this.state = address.getState();
        this.country = address.getCountry();
      }
    
    @Nonnull
    public Address toAddress()
      {
        return Address.builder().withStreet(street)
                                .withCity(city)
                                .withZip(zip)
                                .withState(state)
                                .withCountry(country)
                                .create();
      }
  }
