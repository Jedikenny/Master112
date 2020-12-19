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

package it.senato.jedi.transform;

import java.io.ByteArrayOutputStream;

/**
 * Defines an entity capable to make a transformation of a XML data. 
 * <p>
 * The transformation is based on XSL and some optional Java logic.
 *
 */
public interface XMLTransformer {

	/**
	 * Returns the result of the transformation.
	 * 
	 * @param xsl is the XSL to apply
	 * @param xml is the XML data to transform
	 * @return a ByteArrayOutputStream object
	 * @throws Exception
	 */
	ByteArrayOutputStream run(String xsl,String xml) throws Exception;
	
}
