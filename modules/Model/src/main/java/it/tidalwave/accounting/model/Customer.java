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
package it.tidalwave.accounting.model;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import it.tidalwave.util.As;
import it.tidalwave.util.Id;
import it.tidalwave.util.spi.AsSupport;
import it.tidalwave.role.Identifiable;
import it.tidalwave.accounting.model.impl.FinderWithIdMapSupport;
import lombok.AllArgsConstructor;
import lombok.Delegate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Wither;
import static lombok.AccessLevel.PRIVATE;

/***********************************************************************************************************************
 *
 * This class models a customer.
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@Immutable @Getter @EqualsAndHashCode @ToString(exclude = {"accounting", "asSupport"}) // FIXME: remove the @Getter
public class Customer implements Identifiable, As
  {
    @Delegate
    private final AsSupport asSupport = new AsSupport(this);

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @AllArgsConstructor(access = PRIVATE)
    @Immutable @Wither @Getter @ToString
    public static class Builder
      {
        public static interface Callback // Lombok @Wither doesn't support builder subclasses
          {
            public void register (final @Nonnull Customer customer);

            public static final Callback DEFAULT = (customer) -> {};
          }

        private final Id id;
        private final String name;
        private final Address billingAddress;
        private final String vatNumber;
        private final Callback callback;

        public Builder()
          {
            this(Callback.DEFAULT);
          }

        public Builder (final @Nonnull Callback callback)
          {
            this(new Id(""), "", Address.EMPTY, "", callback);
          }
        
        @Nonnull
        public Builder with (final @Nonnull Builder builder)
          {
            return builder.withCallback(callback);
          }

        @Nonnull
        public Customer create()
          {
            final Customer customer = new Customer(this);
            callback.register(customer);
            return customer;
          }
      }

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

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @Nonnull
    public static Builder builder()
      {
        return new Builder();
      }

    /*******************************************************************************************************************
     *
     *
     * 
     ******************************************************************************************************************/
    protected Customer (final @Nonnull Builder builder)
      {
        this.id = builder.getId();
        this.name = builder.getName();
        this.billingAddress = builder.getBillingAddress();
        this.vatNumber = builder.getVatNumber();
      }
    
    /*******************************************************************************************************************
     *
     * @return 
     * 
     ******************************************************************************************************************/
    @Nonnull
    public Builder asBuilder()
      {
        return new Builder(id, name, billingAddress, vatNumber, Customer.Builder.Callback.DEFAULT);                
      }
    
    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    class InMemoryProjectFinder extends FinderWithIdMapSupport<Project, ProjectRegistry.ProjectFinder>
                               implements ProjectRegistry.ProjectFinder
      {
        InMemoryProjectFinder (final Map<Id, Project> projectMapById)
          {
            super(projectMapById);  
          }
      }

    /*******************************************************************************************************************
     *
     * 
     *
     ******************************************************************************************************************/
    @Nonnull
    public ProjectRegistry.ProjectFinder findProjects()
      {
        final Map<Id, List<Project>> temp = accounting.getProjectRegistry().findProjects()
                .filter(project -> project.getCustomer().getId().equals(getId()))
                .collect(Collectors.groupingBy(Project::getId));
        // FIXME: try to merge into a single pipeline
        final Map<Id, Project> map = new HashMap<>();
        temp.forEach((id, projects) -> map.put(id, projects.get(0)));
        
        return new InMemoryProjectFinder(map);
      }
  }
