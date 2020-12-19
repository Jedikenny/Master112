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
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;
import it.senato.jedi.exception.JEDIInvocationException;

import javax.ejb.Remote;

/**
 * Defines all the operations to produce a document.
 *
 */
@Remote
public interface DocProducerRemote {

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
	public byte[] createDocumentByValues(long idDocument,String values[],FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException;

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
	public byte[] createDocumentByValues(DocumentBean document,String values[],FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException;

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
	public byte[] createDocumentByXML(long idDocument,String xml,FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException;

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
	public byte[] createDocumentByXML(DocumentBean document,String xml,FormatTypeBean format) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException;

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
	public String readXMLSource(long idDocument,String values[]) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException;

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
	public String readXMLSource(DocumentBean document,String values[]) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException;
}
