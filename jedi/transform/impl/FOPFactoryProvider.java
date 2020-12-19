/*
 * JEDI
 *
 *     Copyright (C) 2013  Senato della Repubblica
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.senato.jedi.transform.impl;

import org.apache.fop.apps.FopFactory;

/**
 * A singleton class that provides a FopFactory instance.
 *
 */
public class FOPFactoryProvider {
    private static FopFactory fopFactory = null;
    
    protected FOPFactoryProvider() {

    }

    /**
     * Returns the single FopFactory object.
     * 
     * @return a FopFactory object
     */
    public static FopFactory getInstance() {
        if (fopFactory == null) {
            synchronized(FOPFactoryProvider.class) {
                if (fopFactory == null) {
                	fopFactory = FopFactory.newInstance();
                }
            }
        }
        return fopFactory;
    }

}
