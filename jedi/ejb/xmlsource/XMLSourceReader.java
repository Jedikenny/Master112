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

package it.senato.jedi.ejb.xmlsource;

import javax.persistence.EntityManager;

import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.exception.JEDIInvocationException;

/**
 * Defines the interface of an entity capable to provide the XML data for a document.
 *
 */
public interface XMLSourceReader {
	
	/**
	 * Returns the XML data as string.
	 * @param em is the EntityManager object
	 * @param document is DocumentBean object
	 * @return a String of XML data
	 * @throws JEDIInvocationException
	 */
	String getXML(EntityManager em,DocumentBean document) throws JEDIInvocationException;
	
}
