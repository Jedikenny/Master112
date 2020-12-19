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

import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.bean.FormatTypeBean;
import it.senato.jedi.ejb.action.DocProducerAction;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;
import it.senato.jedi.exception.JEDIInvocationException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This EJB provides all the operations to produce a document.
 *
 */
@Stateless(mappedName="DocProducer")
public class DocProducerEJB extends AbstractEJB implements DocProducerRemote {
    @PersistenceContext
    private EntityManager em;
    
	public DocProducerEJB() {
		
	}
	
	@Override
	public byte[] createDocumentByValues(long idDocument,String values[],FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		logger.info("createDocumentByValues idDocument: " + idDocument + " format: " + (format == null ? "" : format.getOid()));
		
		DocProducerAction action = new DocProducerAction(em,idDocument,null);		
		return action.createDocumentByValues(values,format);
	}

	@Override
	public byte[] createDocumentByValues(DocumentBean document,String values[],FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		logger.info("createDocumentByValues idDocument: " + document.getOid() + " description: " + document.getDescription() + " format: " + (format == null ? "" : format.getOid()));
		
		DocProducerAction action = new DocProducerAction(em,document.getOid(),document);		
		return action.createDocumentByValues(values,format);
	}

	@Override
	public byte[] createDocumentByXML(long idDocument, String xml,FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		logger.info("createDocumentByXML idDocument: " + idDocument + " format: " + (format == null ? "" : format.getOid()));
		
		DocProducerAction action = new DocProducerAction(em,idDocument,null);		
		return action.createDocumentByXML(xml,format);
	}

	@Override
	public byte[] createDocumentByXML(DocumentBean document, String xml,FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		logger.info("createDocumentByXML idDocument: " + document.getOid() + " description: " + document.getDescription() + " format: " + (format == null ? "" : format.getOid()));
		
		DocProducerAction action = new DocProducerAction(em,document.getOid(),document);		
		return action.createDocumentByXML(xml,format);
	}
	
	@Override
	public String readXMLSource(long idDocument, String[] values) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		logger.info("readXMLSource idDocument: " + idDocument);

		DocProducerAction action = new DocProducerAction(em,idDocument,null);		
		return action.readXMLSource(values);
	}

	@Override
	public String readXMLSource(DocumentBean document, String[] values) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
		logger.info("readXMLSource idDocument: " + document.getOid() + " description: " + document.getDescription());

		DocProducerAction action = new DocProducerAction(em,document.getOid(),document);		
		return action.readXMLSource(values);
	}
	
}
