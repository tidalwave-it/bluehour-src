/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2021 Tidalwave s.a.s. (http://tidalwave.it)
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
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.accounting.role;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import it.tidalwave.util.PreferencesHandler;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.accounting.model.Accounting;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.role.io.Marshallable._Marshallable_;
import static it.tidalwave.role.io.Unmarshallable._Unmarshallable_;

/***********************************************************************************************************************
 *
 * The implementation of {@link Loadable} and {@link Saveable} for {@link LoadableSaveableAccounting}.
 * 
 * @stereotype role
 * 
 * @author  Fabrizio Giudici
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor @DciRole(datumType = Accounting.class) @Slf4j
public class LoadableSaveableAccounting implements Loadable, Saveable
  {
    public final static String BLUEHOUR_FILE_NAME = "blueHour.xml";

    @Nonnull
    private final Accounting accounting;
    
    @Nonnull
    private final PreferencesHandler preferencesHandler;

    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     *
     ******************************************************************************************************************/
    @Override
    public Accounting load()
      throws IOException 
      {
        final Path dataFile = getDataFile();
        log.info(">>>> loading data from {}...", dataFile);

        try (final InputStream is = new FileInputStream(dataFile.toFile()))
          {
            return accounting.as(_Unmarshallable_).unmarshal(is);
          }
      }

    /*******************************************************************************************************************
     *
     * {@inheritDoc} 
     *
     ******************************************************************************************************************/
    @Override
    public void save() 
      throws IOException 
      {
        final Path dataFile = getDataFile();
        log.info(">>>> saving data to {}...", dataFile);

        try (final OutputStream os = new FileOutputStream(dataFile.toFile()))
          {
            accounting.as(_Marshallable_).marshal(os);
          }
      }

    /*******************************************************************************************************************
     *
     *  
     *
     ******************************************************************************************************************/
    @Nonnull
    protected Path getDataFile() 
      {
        return preferencesHandler.getAppFolder().resolve(BLUEHOUR_FILE_NAME);
      }
  }
