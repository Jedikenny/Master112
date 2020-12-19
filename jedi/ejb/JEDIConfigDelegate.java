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

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import it.senato.jedi.bean.*;
import org.apache.log4j.Logger;

import it.senato.jedi.LogChannel;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;

/**
 * Delegate class to call JEDIConfigEJB operations.
 *
 */
public class JEDIConfigDelegate {
	private Logger logger;
	
	private static final String JNDI_NAME = "java:app/jediEJB/JEDIConfigEJB";

	/**
	 * Creates a JEDIConfigDelegate object.
	 */
	public JEDIConfigDelegate() {
		logger = Logger.getLogger(LogChannel.EJB_CHANNEL);
	}
	
	/**
	 * Returns a configured document.
	 * 
	 * @param idDocument is the identifier of the document
	 * @return a DocumentBean object
	 * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
	 */
	public DocumentBean lookupDocument(long idDocument) throws JEDIConfigException, JEDIInfrastructureException {		
		try {
			Context context = new InitialContext();    			
			JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);
			
			return remote.lookupDocument(idDocument);
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInfrastructureException(e.getMessage());
		}
	}

	/**
	 * Returns a reusable transformer.
	 * 
	 * @param idReusableTransformer is the identifier of the reusable transformer
	 * @return a ReusableTransformerBean object
	 * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
	 */
    public ReusableTransformerBean lookupReusableTransformer(long idReusableTransformer) throws JEDIConfigException, JEDIInfrastructureException {
        try {
            Context context = new InitialContext();
            JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);

            return remote.lookupReusableTransformer(idReusableTransformer);
        } catch (NamingException e) {
            logger.error(e.getMessage(),e);
            throw new JEDIInfrastructureException(e.getMessage());
        }
    }

    /**
     * Saves a document configuration.
     * 
     * @param bean is a DocumentBean object
     * @return a DocumentBean object
     * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
     */
    public DocumentBean saveDocument(DocumentBean bean) throws JEDIConfigException, JEDIInfrastructureException {
        try {
            Context context = new InitialContext();
            JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);

            return remote.saveDocument(bean);
        } catch (NamingException e) {
            logger.error(e.getMessage(),e);
            throw new JEDIInfrastructureException(e.getMessage());
        }
    }

	/**
	 * Saves a reusable transformer.
	 * 
	 * @param bean is a ReusableTransformerBean object
	 * @return a ReusableTransformerBean object
	 * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
	 */
    public ReusableTransformerBean saveReusableTransformer(ReusableTransformerBean bean) throws JEDIConfigException, JEDIInfrastructureException {
        try {
            Context context = new InitialContext();
            JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);

            return remote.saveReusableTransformer(bean);
        } catch (NamingException e) {
            logger.error(e.getMessage(),e);
            throw new JEDIInfrastructureException(e.getMessage());
        }
    }

	/**
	 * Returns the possible format types.
	 * 
	 * @return a list of FormatTypeBean objects
	 * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
	 */
	public List<FormatTypeBean> getFormatTypes() throws JEDIConfigException, JEDIInfrastructureException {
		try {
			Context context = new InitialContext();    			
			JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);
			
			return remote.getFormatTypes();
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInfrastructureException(e.getMessage());
		}
	}

	/**
	 * Returns the possible XML source types.
	 * 
	 * @return a list of XMLSourceTypeBean objects
	 * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
	 */
	public List<XMLSourceTypeBean> getXMLSourceTypes() throws JEDIConfigException, JEDIInfrastructureException {
		try {
			Context context = new InitialContext();    			
			JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);
			
			return remote.getXMLSourceTypes();
		} catch (NamingException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInfrastructureException(e.getMessage());
		}
	}

    /**
     * Returns the configured documents that match some given attributes.
     * 
     * @param idApplication is the identifier of an application 
     * @param descriptionLike is the description of the document (to be used as like)
     * @param idFormatType is the identifier of the format type (it can be null)
     * @param descriptionEquals is the description of the document (to be used as equals) 
     * @return a list of DocumentBean objects
     * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
     */
	public List<DocumentBean> getDocuments(Long idApplication, String descriptionLike, Long idFormatType, String descriptionEquals) throws JEDIConfigException, JEDIInfrastructureException {
        try {
            Context context = new InitialContext();
            JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);

            return remote.getDocuments(idApplication, descriptionLike, idFormatType, descriptionEquals);
        } catch (NamingException e) {
            logger.error(e.getMessage(),e);
            throw new JEDIInfrastructureException(e.getMessage());
        }
    }

	/**
	 * Returns the configured applications.
	 * 
	 * @return a list of ApplicationBean objects
	 * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
	 */
    public List<ApplicationBean> getApplications() throws JEDIConfigException, JEDIInfrastructureException {
        try {
            Context context = new InitialContext();
            JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);

            return remote.getApplications();
        } catch (NamingException e) {
            logger.error(e.getMessage(),e);
            throw new JEDIInfrastructureException(e.getMessage());
        }
    }

	/**
	 * Returns the configured reusable transformers.
	 * 
	 * @return a list of ReusableTransformerBean objects
	 * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
	 */
    public List<ReusableTransformerBean> getReusableTransformers() throws JEDIConfigException, JEDIInfrastructureException {
        try {
            Context context = new InitialContext();
            JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);

            return remote.getReusableTransformers();
        } catch (NamingException e) {
            logger.error(e.getMessage(),e);
            throw new JEDIInfrastructureException(e.getMessage());
        }
    }

    /**
     * Returns the configured reusable transformers.
     * 
     * @param descriptionLike is description of the transformers to find
     * @return a list of ReusableTransformerBean objects
     * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
     */
    public List<ReusableTransformerBean> getReusableTransformers(String descriptionLike) throws JEDIConfigException, JEDIInfrastructureException {
        try {
            Context context = new InitialContext();
            JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);

            return remote.getReusableTransformers(descriptionLike);
        } catch (NamingException e) {
            logger.error(e.getMessage(),e);
            throw new JEDIInfrastructureException(e.getMessage());
        }
    }

    /**
     * Returns all the configured documents depending on a given one.
     * 
     * @param idDocument is the identifier of the document
     * @return a list of DocumentBean objects
     * @throws JEDIConfigException
	 * @throws JEDIInfrastructureException
     */
    public List<DocumentBean> getDocumentsDependingBy(long idDocument) throws JEDIConfigException, JEDIInfrastructureException {
        try {
            Context context = new InitialContext();
            JEDIConfigRemote remote = (JEDIConfigRemote) context.lookup(JNDI_NAME);

            return remote.getDocumentsDependingBy(idDocument);
        } catch (NamingException e) {
            logger.error(e.getMessage(),e);
            throw new JEDIInfrastructureException(e.getMessage());
        }
    }

}
