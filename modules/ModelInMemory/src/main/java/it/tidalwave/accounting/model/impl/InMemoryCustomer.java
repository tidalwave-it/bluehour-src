/*
 * *************************************************************************************************************************************************************
 *
 * blueHour: open source accounting
 * http://tidalwave.it/projects/bluehour
 *
 * Copyright (C) 2013 - 2024 by Tidalwave s.a.s. (http://tidalwave.it)
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
package it.tidalwave.accounting.model.impl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Map;
import it.tidalwave.accounting.model.Accounting;
import it.tidalwave.accounting.model.Project;
import it.tidalwave.accounting.model.ProjectRegistry;
import it.tidalwave.accounting.model.spi.CustomerSpi;
import it.tidalwave.accounting.model.types.Address;
import it.tidalwave.util.As;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.FinderWithIdMapSupport;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Delegate;
import static java.util.stream.Collectors.*;

/***************************************************************************************************************************************************************
 *
 * This class models a customer.
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
@Immutable @Getter @EqualsAndHashCode @ToString(exclude = {"accounting", "as"}) // FIXME: remove the @Getter
public class InMemoryCustomer implements CustomerSpi
  {
    private static final long serialVersionUID = 1L;
    
    static class InMemoryProjectFinder extends FinderWithIdMapSupport<Project, InMemoryProject, ProjectRegistry.ProjectFinder>
                                implements ProjectRegistry.ProjectFinder
      {
        private static final long serialVersionUID = 1L;
    
        public InMemoryProjectFinder (@Nonnull final InMemoryProjectFinder other, @Nonnull final Object override)
          {
            super(other, override);  
          }
        
        InMemoryProjectFinder (@Nonnull final Map<Id, InMemoryProject> projectMapById)
          {
            super(projectMapById);  
          }
      }
    
    @Delegate
    private final As as = As.forObject(this);

    @Getter @Nonnull
    private final Id id;

    @Nonnull
    private final String name;

    @Nonnull
    private final Address billingAddress;

    @Nonnull
    private final String vatNumber;
    
    @Setter @Nonnull // FIXME: drop the setter!
    private Accounting accounting;

    /***********************************************************************************************************************************************************
     **********************************************************************************************************************************************************/
    public /* FIXME protected */ InMemoryCustomer (@Nonnull final Builder builder)
      {
        this.id = builder.getId();
        this.name = builder.getName();
        this.billingAddress = builder.getBillingAddress();
        this.vatNumber = builder.getVatNumber();
      }

    /***********************************************************************************************************************************************************
     * 
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public ProjectRegistry.ProjectFinder findProjects()
      {
        return new InMemoryProjectFinder(accounting.getProjectRegistry().findProjects()
                                                   .stream()
                                                   .filter(project -> project.getCustomer().getId().equals(getId()))
                                                   .collect(toMap(Project::getId, project -> (InMemoryProject)project)));
      }
    
    /***********************************************************************************************************************************************************
     * {@inheritDoc}
     **********************************************************************************************************************************************************/
    @Override @Nonnull
    public Builder toBuilder()
      {
        return new Builder(id, name, billingAddress, vatNumber, InMemoryCustomer.Builder.Callback.DEFAULT);                
      }
  }
