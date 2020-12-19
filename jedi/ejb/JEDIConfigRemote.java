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

import it.senato.jedi.bean.*;
import it.senato.jedi.exception.JEDIConfigException;

import java.util.List;

import javax.ejb.Remote;

/**
 * Defines all the operations to manage documents configuration.
 *
 */
@Remote
public interface JEDIConfigRemote {

	/**
	 * Returns the possible format types.
	 * 
	 * @return a list of FormatTypeBean objects
	 * @throws JEDIConfigException
	 */
	List<FormatTypeBean> getFormatTypes() throws JEDIConfigException;

	/**
	 * Returns the possible XML source types.
	 * 
	 * @return a list of XMLSourceTypeBean objects
	 * @throws JEDIConfigException
	 */
	List<XMLSourceTypeBean> getXMLSourceTypes() throws JEDIConfigException;
	
	/**
	 * Returns the configured applications.
	 * 
	 * @return a list of ApplicationBean objects
	 * @throws JEDIConfigException
	 */
	List<ApplicationBean> getApplications() throws JEDIConfigException;

	/**
	 * Returns the configured reusable transformers.
	 * 
	 * @return a list of ReusableTransformerBean objects
	 * @throws JEDIConfigException
	 */
    List<ReusableTransformerBean> getReusableTransformers() throws JEDIConfigException;

    /**
     * Returns the configured reusable transformers.
     * 
     * @param descriptionLike is description of the transformers to find
     * @return a list of ReusableTransformerBean objects
     * @throws JEDIConfigException
     */
    List<ReusableTransformerBean> getReusableTransformers(String descriptionLike) throws JEDIConfigException;

    /**
     * Returns the configured documents that match some given attributes.
     * 
     * @param idApplication is the identifier of an application 
     * @param descriptionLike is the description of the document (to be used as like)
     * @param idFormatType is the identifier of the format type (it can be null)
     * @param descriptionEquals is the description of the document (to be used as equals) 
     * @return a list of DocumentBean objects
     * @throws JEDIConfigException
     */
	List<DocumentBean> getDocuments(Long idApplication, String descriptionLike, Long idFormatType, String descriptionEquals) throws JEDIConfigException;
	
	/**
	 * Returns a configured document.
	 * 
	 * @param idDocument is the identifier of the document
	 * @return a DocumentBean object
	 * @throws JEDIConfigException
	 */
	DocumentBean lookupDocument(long idDocument) throws JEDIConfigException;

	/**
	 * Returns a reusable transformer.
	 * 
	 * @param idReusableTransformer is the identifier of the reusable transformer
	 * @return a ReusableTransformerBean object
	 * @throws JEDIConfigException
	 */
    ReusableTransformerBean lookupReusableTransformer(long idReusableTransformer) throws JEDIConfigException;

    /**
     * Saves a document configuration.
     * 
     * @param document is a DocumentBean object
     * @return a DocumentBean object
     * @throws JEDIConfigException
     */
	DocumentBean saveDocument(DocumentBean document) throws JEDIConfigException;

	/**
	 * Saves a reusable transformer.
	 * 
	 * @param reusableTransformer is a ReusableTransformerBean object
	 * @return a ReusableTransformerBean object
	 * @throws JEDIConfigException
	 */
    ReusableTransformerBean saveReusableTransformer(ReusableTransformerBean reusableTransformer) throws JEDIConfigException;

    /**
     * Returns all the configured documents depending on a given one.
     * 
     * @param idDocument is the identifier of the document
     * @return a list of DocumentBean objects
     * @throws JEDIConfigException
     */
	List<DocumentBean> getDocumentsDependingBy(long idDocument) throws JEDIConfigException;
	
}
