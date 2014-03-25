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

import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import it.tidalwave.role.Unmarshallable;
import it.tidalwave.accounting.model.Accounting;
import lombok.RequiredArgsConstructor;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class AccountingXmlUnmarshallable implements Unmarshallable
  {
    private final Accounting accounting;
    
    @Override
    public <TYPE> TYPE unmarshal(InputStream is) 
      throws IOException 
      {
        try 
          {
            final JAXBContext jaxbc = JAXBContext.newInstance(AccountingXml.class);
            final Unmarshaller unmarshaller = jaxbc.createUnmarshaller();
            final AccountingXml accountingXml = (AccountingXml)unmarshaller.unmarshal(is);

            accountingXml.fill(accounting);
            return (TYPE)accounting; 
          } 
        catch (JAXBException e) 
          {
            throw new IOException(e);
          }
      }
  }