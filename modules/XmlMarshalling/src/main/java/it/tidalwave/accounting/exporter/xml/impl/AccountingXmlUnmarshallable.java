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
package it.tidalwave.accounting.exporter.xml.impl;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import it.tidalwave.accounting.exporter.xml.impl.xml.AccountingXml;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.role.io.Unmarshallable;
import it.tidalwave.dci.annotation.DciRole;
import lombok.RequiredArgsConstructor;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@DciRole(datumType = Accounting.class)
@RequiredArgsConstructor
public class AccountingXmlUnmarshallable implements Unmarshallable
  {
    @Nonnull
    private final Accounting accounting;
    
    @Override @Nonnull
    public <T> T unmarshal (@Nonnull final InputStream is)
      throws IOException 
      {
        try 
          {
            final var jaxbc = JAXBContext.newInstance(AccountingXml.class);
            final var unmarshaller = jaxbc.createUnmarshaller();
            // FIXME: Set encoding
            final var accountingXml = (AccountingXml)unmarshaller.unmarshal(is);
            accountingXml.fill(accounting);
            return (T)accounting;
          } 
        catch (JAXBException e) 
          {
            throw new IOException(e);
          }
      }
  }
