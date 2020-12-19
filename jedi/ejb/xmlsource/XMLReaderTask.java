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

import java.text.ParseException;

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.bean.XMLSourceTypeBean;
import it.senato.jedi.exception.JEDIInvocationException;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * This task has the job to read of XML source data for a given document.
 * 
 */
public class XMLReaderTask {
	private Logger logger;
	
	/**
	 * Creates a XMLReaderTask object.
	 */
	public XMLReaderTask() {
		logger = Logger.getLogger(LogChannel.GENERIC_CHANNEL);
	}
	
	/**
	 * Reads XML source data.
	 * 
	 * @param em is EntityManager object
	 * @param document is DocumentBean object for which to read XML data
	 * @param values is array of String parameter values
	 * @return a String of XML data
	 * @throws JEDIInvocationException
	 */
	public String getXML(EntityManager em,DocumentBean document,String values[]) throws JEDIInvocationException {
        // setting parameter values
        if(values == null || values.length < document.getParameters().size())
        	throw new JEDIInvocationException("Number of values is less than the one of the required parameters " + document.getParameters().size());
        
        for(int k=0;k<document.getParameters().size();k++)
			try {
				document.getParameters().get(k).setValueAsString(values[k]);
			} catch (ParseException e) {
				logger.error(e.getMessage(),e);
				throw new JEDIInvocationException(e.getMessage());
			}

		XMLSourceTypeBean type = document.getXmlSource().getType();
		
        logger.debug("XML reading for XMLSourceType: [" + type.getOid() + "] " + type.getDescription() + 
        		" ClassName: " + type.getReaderClassName() + " TypeClass: " + type.getTypeClass());
		
		String xml = null;
        
		try {
			Class<? extends XMLSourceReader> c = Class.forName(type.getReaderClassName()).asSubclass(XMLSourceReader.class);

			XMLSourceReader t = c.newInstance();
			
			xml = t.getXML(em,document);

	        if (xml.indexOf("<?xml version") >= 0) {
	            int fine = xml.indexOf("?>");
	            xml = xml.substring(fine + 2);
	        }
	        xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>" + xml;

		    if(Logger.getLogger(LogChannel.EJB_CHANNEL).isDebugEnabled())
	            Logger.getLogger(LogChannel.EJB_CHANNEL).debug(xml);

	        return xml;
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInvocationException(e.getMessage());
		} catch (InstantiationException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInvocationException(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInvocationException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInvocationException(e.getMessage());
		}		
	}
}
