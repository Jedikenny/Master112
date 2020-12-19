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

package it.senato.jedi.ejb;

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.bean.FormatTypeBean;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;
import it.senato.jedi.exception.JEDIInvocationException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * Delegate class to call DocProducerEJB operations.
 *
 */
public class DocProducerDelegate {
	private Logger logger;
	
	private static final String JNDI_NAME = "java:app/jediEJB/DocProducerEJB";

	/**
	 * Creates a DocProducerDelegate object.
	 */
	public DocProducerDelegate() {
		logger = Logger.getLogger(LogChannel.EJB_CHANNEL);
	}
	
	/**
	 * Produces the document by input values.
	 * 
	 * @param idDocument is Document oid
	 * @param values is a String array of input values
	 * @param format is the required FormatTypeBean object. If null, the document format is used
	 * @return the document as byte[]
	 * @throws JEDIInvocationException
	 * @throws JEDIInfrastructureException
	 * @throws JEDIConfigException
	 */
	public byte[] createDocumentByValues(long idDocument,String values[],FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		try {
			Context context = new InitialContext();    			
			DocProducerRemote remote = (DocProducerRemote) context.lookup(JNDI_NAME);
			
			return remote.createDocumentByValues(idDocument,values,format);
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInfrastructureException(e.getMessage());
		}
	}
	
	/**
	 * Produces the document by input values.
	 * 
	 * @param document is DocumentBean object
	 * @param values is a String array of input values
	 * @param format is the required FormatTypeBean object. If null, the document format is used
	 * @return the document as byte[]
	 * @throws JEDIInvocationException
	 * @throws JEDIInfrastructureException
	 * @throws JEDIConfigException
	 */
	public byte[] createDocumentByValues(DocumentBean document,String values[],FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		try {
			Context context = new InitialContext();    			
			DocProducerRemote remote = (DocProducerRemote) context.lookup(JNDI_NAME);
			
			return remote.createDocumentByValues(document,values,format);
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInfrastructureException(e.getMessage());
		}
	}

	/**
	 * Produces the document by a given XML data.
	 * 
	 * @param idDocument is Document oid
	 * @param xml is the given data
	 * @param format is the required FormatTypeBean object. If null, the document format is used
	 * @return the document as byte[]
	 * @throws JEDIInvocationException
	 * @throws JEDIInfrastructureException
	 * @throws JEDIConfigException
	 */
	public byte[] createDocumentByXML(long idDocument,String xml,FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		try {
			Context context = new InitialContext();    			
			DocProducerRemote remote = (DocProducerRemote) context.lookup(JNDI_NAME);
			
			return remote.createDocumentByXML(idDocument,xml,format);
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInfrastructureException(e.getMessage());
		}
	}

	/**
	 * Produces the document by a given XML data.
	 * 
	 * @param document is DocumentBean object
	 * @param xml is the given data
	 * @param format is the required FormatTypeBean object. If null, the document format is used
	 * @return the document as byte[]
	 * @throws JEDIInvocationException
	 * @throws JEDIInfrastructureException
	 * @throws JEDIConfigException
	 */
	public byte[] createDocumentByXML(DocumentBean document,String xml,FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		try {
			Context context = new InitialContext();    			
			DocProducerRemote remote = (DocProducerRemote) context.lookup(JNDI_NAME);
			
			return remote.createDocumentByXML(document,xml,format);
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInfrastructureException(e.getMessage());
		}
	}

	/**
	 * Reads XML data.
	 * 
	 * @param idDocument is Document oid
	 * @param values is a String array of input values
	 * @return a String of XML data
	 * @throws JEDIInvocationException
	 * @throws JEDIInfrastructureException
	 * @throws JEDIConfigException
	 */
	public String readXMLSource(long idDocument,String values[]) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		try {
			Context context = new InitialContext();    			
			DocProducerRemote remote = (DocProducerRemote) context.lookup(JNDI_NAME);
			
			return remote.readXMLSource(idDocument,values);
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInfrastructureException(e.getMessage());
		}
	}

	/**
	 * Reads XML data.
	 * 
	 * @param document is DocumentBean object
	 * @param values is a String array of input values
	 * @return a String of XML data
	 * @throws JEDIInvocationException
	 * @throws JEDIInfrastructureException
	 * @throws JEDIConfigException
	 */
	public String readXMLSource(DocumentBean document,String values[]) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		try {
			Context context = new InitialContext();    			
			DocProducerRemote remote = (DocProducerRemote) context.lookup(JNDI_NAME);
			
			return remote.readXMLSource(document,values);
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInfrastructureException(e.getMessage());
		}
	}
	
}
