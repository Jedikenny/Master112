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

package it.senato.jedi.ejb.action;

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.bean.FormatTypeBean;
import it.senato.jedi.bean.TransformerTypeEnum;
import it.senato.jedi.ejb.JEDIConfigDelegate;
import it.senato.jedi.ejb.xmlsource.XMLReaderTask;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;
import it.senato.jedi.exception.JEDIInvocationException;
import it.senato.jedi.transform.TransformerTask;

import java.io.ByteArrayOutputStream;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * This action contains all the logic about document production.
 * 
 */
public class DocProducerAction {
	private Logger logger;
	private EntityManager em;	
	private DocumentBean document;	

	/**
	 * Creates a DocProducerAction object.
	 * 
	 * @param em is an EntityManager object
	 * @param idDocument is Document object
	 * @param document is DocumentBean object
	 * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
	 */
	public DocProducerAction(EntityManager em,long idDocument,DocumentBean document) throws JEDIConfigException, JEDIInfrastructureException {
		logger = Logger.getLogger(LogChannel.EJB_CHANNEL);
		
		this.em = em;
		
		// prepare the document		
		if(document == null) {
			// read document
			JEDIConfigDelegate delegate = new JEDIConfigDelegate();
	        this.document = delegate.lookupDocument(idDocument);
		}
		else
			this.document = document;
	}

	/**
	 * Reads XML data.
	 * 
	 * @param values is a String array of input values
	 * @return a string of XML data
	 * @throws JEDIInvocationException
	 */
	public String readXMLSource(String values[]) throws JEDIInvocationException {        
        // read XML source
        XMLReaderTask reader = new XMLReaderTask();
        String xml = reader.getXML(em,document,values);
        
        return xml;
	}
	
	/**
	 * Produces the document by input values.
	 * 
	 * @param values is a String array of input values
	 * @param format is the required FormatTypeBean object. If null, the document format is used
	 * @return the document as byte[]
	 * @throws JEDIInvocationException
	 */
	public byte[] createDocumentByValues(String values[],FormatTypeBean format) throws JEDIInvocationException {
        // reads XML source
        String xml = readXMLSource(values);
        
        return createDocumentByXML(xml,format);
	}

	/**
	 * Produces the document by a given XML data.
	 * 
	 * @param xml is the given data
	 * @param format is the required FormatTypeBean object. If null, the document format is used
	 * @return the document as byte[]
	 * @throws JEDIInvocationException
	 */
	public byte[] createDocumentByXML(String xml,FormatTypeBean format) throws JEDIInvocationException {
		// read format type (not required)
        FormatTypeBean formatType = format;
        if(formatType == null)
        	formatType = document.getFormatType();

        // apply transformation
        TransformerTask task = new TransformerTask();
        String xsl = document.getTransformer();
        if (document.getTransformerType()== TransformerTypeEnum.Reusable)
            xsl = document.getReusableTransformer().getTransformer();

        ByteArrayOutputStream os = task.execute(xsl,xml,formatType);
        
        return os.toByteArray();
	}
}
