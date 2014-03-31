/*
 * #%L
 * %%
 * %%
 * #L%
 */

package it.tidalwave.accounting.ui.jobeventexplorer.impl;

import it.tidalwave.role.Aggregate;
import it.tidalwave.role.spi.MapAggregate;
import it.tidalwave.role.ui.PresentationModel;
import it.tidalwave.role.ui.spi.DefaultPresentationModel;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

/***********************************************************************************************************************
 *
 * @author  fritz
 * @version $Id$
 *
 **********************************************************************************************************************/
public class AggregatePresentationModelBuilder 
  {
    private final Map<String, PresentationModel> map = new HashMap<>();
    
    @Nonnull
    public AggregatePresentationModelBuilder add (final @Nonnull String name, final @Nonnull Object ... roles)
      {
        map.put(name, new DefaultPresentationModel("", roles));
        return this; // FIXME: make immutable   
      }
    
    @Nonnull
    public Aggregate create()
      {
        return new MapAggregate<>(map);
      }
  }
