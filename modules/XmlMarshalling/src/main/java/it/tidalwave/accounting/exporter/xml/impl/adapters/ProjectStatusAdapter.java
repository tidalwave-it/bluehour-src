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
package it.tidalwave.accounting.exporter.xml.impl.adapters;

import jakarta.annotation.Nonnull;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import it.tidalwave.accounting.model.Project;

/***************************************************************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 *
 **************************************************************************************************************************************************************/
public class ProjectStatusAdapter extends XmlAdapter<String, Project.Status>
  {
    @Override @Nonnull
    public Project.Status unmarshal (final String v)
      {
        return Project.Status.valueOf(v.toUpperCase());
      }

    @Override @Nonnull
    public String marshal (final Project.Status v)
      {
        return v.name().toLowerCase();
      }
  }
