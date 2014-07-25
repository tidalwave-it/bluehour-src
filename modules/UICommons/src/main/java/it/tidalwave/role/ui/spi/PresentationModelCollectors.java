/*
 * #%L
 * *********************************************************************************************************************
 *
 * NorthernWind - lightweight CMS
 * http://northernwind.tidalwave.it - hg clone https://bitbucket.org/tidalwave/northernwind-src
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
package it.tidalwave.role.ui.spi;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import it.tidalwave.util.ArrayListCollectorSupport;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.spi.ArrayListSimpleComposite;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
public class PresentationModelCollectors extends ArrayListCollectorSupport<PresentationModel, PresentationModel>
  {
    @Nonnull
    private final List<Object> roles = new ArrayList<>();
    
    @Nonnull
    // FIXME: rename to toCompositePresentationModel
    public static PresentationModelCollectors toContainerPresentationModel (final @Nonnull Object ... roles)
      {
        return new PresentationModelCollectors(Arrays.asList(roles));
      }

    private PresentationModelCollectors (final @Nonnull List<Object> roles) 
      {
        this.roles.addAll(roles);
      }
    
    @Override @Nonnull 
    public Function<List<PresentationModel>, PresentationModel> finisher() 
      {
        return childrenPms ->
          {
            final List<Object> temp = new ArrayList<>(roles);
            temp.add(new ArrayListSimpleComposite<>(childrenPms));
            // FIXME: "" triggers a NPE in RoleManagerSupport.java:341
            return new DefaultPresentationModel("", temp.toArray());
          };
      }
  }
