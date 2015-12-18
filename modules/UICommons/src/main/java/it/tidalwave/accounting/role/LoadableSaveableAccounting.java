/*
 * #%L
 * *********************************************************************************************************************
 *
 * blueHour
 * http://bluehour.tidalwave.it - git clone git@bitbucket.org:tidalwave/bluehour-src.git
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
package it.tidalwave.accounting.role;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Configurable;
import it.tidalwave.dci.annotation.DciRole;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.util.PreferencesHandler;
import lombok.extern.slf4j.Slf4j;
import static it.tidalwave.role.Marshallable.Marshallable;
import static it.tidalwave.role.Unmarshallable.Unmarshallable;

/***********************************************************************************************************************
 *
 * The implementation of {@link Loadable} and {@link Saveable} for {@link LoadableSaveableAccounting}.
 * 
 * @stereotype role
 * 
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@DciRole(datumType = Accounting.class) @Configurable @Slf4j
public class LoadableSaveableAccounting implements Loadable, Saveable
  {
    @Nonnull
    private final Accounting accounting;
    
    @Inject @Nonnull
    private PreferencesHandler preferencesHandler;

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    public LoadableSaveableAccounting (final @Nonnull Accounting accounting)
      {
        this.accounting = accounting;
      }
    
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
            return accounting.as(Unmarshallable).unmarshal(is);
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
            accounting.as(Marshallable).marshal(os);
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
        return preferencesHandler.getAppFolder().resolve(PreferencesHandler.BLUEHOUR_FILE_NAME);
      }
  }
