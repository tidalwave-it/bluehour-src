/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
 * %%
 * Copyright (C) 2013 - 2023 Tidalwave s.a.s. (http://tidalwave.it)
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.util.PreferencesHandler;
import it.tidalwave.dci.annotation.DciRole;
import lombok.RequiredArgsConstructor;
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
    public static final String BLUEHOUR_FILE_NAME = "blueHour.xml";

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
        final var dataFile = getDataFile();
        log.info(">>>> loading data from {}...", dataFile);

        try (final var is = Files.newInputStream(dataFile))
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
        final var dataFile = getDataFile();
        log.info(">>>> saving data to {}...", dataFile);

        try (final var os = Files.newOutputStream(dataFile))
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
