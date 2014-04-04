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
package it.tidalwave.role.ui.spi;

import javax.annotation.Nonnull;
import java.io.PrintWriter;
import it.tidalwave.role.PlainTextRenderable;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici
 * @version $Id$
 *
 **********************************************************************************************************************/
@FunctionalInterface
public interface PlainTextRenderableSupport8 extends PlainTextRenderable
  {
    public static final Class<PlainTextRenderableSupport8> PlainTextRenderableSupport8 = PlainTextRenderableSupport8.class;
    
    @Override
    public default void renderTo (final @Nonnull StringBuilder stringBuilder, 
                                  final @Nonnull Object ... args)
      {
        stringBuilder.append(render(args));
      }

    @Override
    public default void renderTo (final @Nonnull PrintWriter printWriter, 
                                  final @Nonnull Object ... args)
      {
        printWriter.print(render(args));
      }
  }

