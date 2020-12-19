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

package it.senato.jedi.ejb.xmlsource.impl;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.ejb.DocProducerDelegate;
import it.senato.jedi.ejb.xmlsource.XMLSourceReader;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;
import it.senato.jedi.exception.JEDIInvocationException;

/**
 * This class reads XML data as result of a JEDI XML document.
 * <p>
 * Note that the JEDI XML document has to be configured into the document's XML source.
 * 
 * @see XMLSourceReader
 */
public class JEDIXMLDocumentReader implements XMLSourceReader {
	private Logger logger;
	
	/**
	 * Creates a JEDIXMLDocumentReader object.
	 */
	public JEDIXMLDocumentReader() {
		logger = Logger.getLogger(LogChannel.EJB_CHANNEL);
	}
	
	@Override
	public String getXML(EntityManager em, DocumentBean document) throws JEDIInvocationException {		
		try {
			logger.info("Begin XML reading for document [" + document.getOid() + "] " + document.getDescription());

			// read oid document to invoke
			long idDocument = document.getXmlSource().getIdDocument();
			
			// input parameters
			String values[] = new String[document.getParameters().size()];
			for(int k=0;k<values.length;k++) {
				values[k] = document.getParameters().get(k).getValueAsString();
			}
			
			DocProducerDelegate delegate = new DocProducerDelegate();
			byte[] data = delegate.createDocumentByValues(idDocument, values, null);
			
			logger.info("End XML reading for document [" + document.getOid() + "] " + document.getDescription());
			
			return new String(data);
		} catch (JEDIConfigException e) {
			throw new JEDIInvocationException(e.getMessage());
		} catch(JEDIInfrastructureException e) {
			throw new JEDIInvocationException(e.getMessage());
		}
	}

}
