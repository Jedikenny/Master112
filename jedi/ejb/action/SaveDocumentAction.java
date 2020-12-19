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
import it.senato.jedi.bean.ParameterBean;
import it.senato.jedi.bean.ParameterIF;
import it.senato.jedi.bean.TransformerTypeEnum;
import it.senato.jedi.ejb.entity.*;
import it.senato.jedi.exception.JEDIConfigException;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import it.senato.jedi.exception.JEDIInfrastructureException;
import org.apache.log4j.Logger;

/**
 * This action has the job to save a document configuration.
 * 
 */
public class SaveDocumentAction {
	private Logger logger;
	
	private EntityManager em;	

	/**
	 * Creates a SaveDocumentAction object.
	 * 
	 * @param em is an EntityManager object
	 */
	public SaveDocumentAction(EntityManager em) {
		logger = Logger.getLogger(LogChannel.EJB_CHANNEL);
		
		this.em = em;
	}
	
	/**
	 * Saves the document configuration.
	 * 
	 * @param documentBean is a DocumentBean object
	 * @return a DocumentBean object
	 * @throws JEDIConfigException
	 */
	public DocumentBean execute(DocumentBean documentBean) throws JEDIConfigException {
		try {
			final boolean isNew = (documentBean.getOid() == 0);
			logger.debug("saveDocument -> begin with document: " + documentBean.getDescription() + " isNew: " + isNew);
			
			Document document;
			if(isNew) {
				document = new Document();
			}
			else {
				document = em.find(Document.class,documentBean.getOid());
                if (!document.getTimest().equals(documentBean.getTimeStamp()))
                    throw new JEDIInfrastructureException("The object you are trying to update is not up-to-date");
			}
			
			// document attributes
			if(document.getApplication() == null || document.getApplication().getOid() != documentBean.getApplication().getOid())
				document.setApplication(em.find(Application.class, documentBean.getApplication().getOid()));

            if(document.getReusableTransformer() == null || document.getReusableTransformer().getOid() != documentBean.getReusableTransformer().getOid())
                if (documentBean.getReusableTransformer()==null)
                    document.setReusableTransformer(null);
                else
                    document.setReusableTransformer(em.find(ReusableTransformer.class, documentBean.getReusableTransformer().getOid()));

			document.setDescription(documentBean.getDescription());
			document.setUser(documentBean.getLastUser());
			if(document.getFormatType() == null || document.getFormatType().getOid() != documentBean.getFormatType().getOid())
				document.setFormatType(em.find(FormatType.class, documentBean.getFormatType().getOid()));

            if (documentBean.getTransformerType()== TransformerTypeEnum.Reusable)
                document.setTransformer(null);
            else
                document.setTransformer(documentBean.getTransformer());


			// XML source
			logger.debug("saveDocument -> saving XML source...");
			if(isNew)
				document.setXmlSource(new XmlSource());
			document.getXmlSource().setStoredprocedure(documentBean.getXmlSource().getStoredProcedure());
            document.getXmlSource().setSQLQuery(documentBean.getXmlSource().getSQLQuery());
			document.getXmlSource().setUrl(documentBean.getXmlSource().getUrl());
			document.getXmlSource().setUser(documentBean.getLastUser());
			document.getXmlSource().setXmlSourceType(em.find(XmlSourceType.class, documentBean.getXmlSource().getType().getOid()));
			document.getXmlSource().setIdDocument(documentBean.getXmlSource().getIdDocument());

			// Parameters
			logger.debug("saveDocument -> saving parameters...");
			ParametersPersistenceStrategy strategy = new ParametersPersistenceStrategy(document, documentBean);
			strategy.saveChildren();

            if(isNew)
                em.persist(document);

            em.flush();
            em.refresh(document);

			logger.debug("saveDocument -> done.");
			return document.getBean(false);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new JEDIConfigException(e.getMessage());
		}
	}

	class ParametersPersistenceStrategy {
		private Document doc;
		private DocumentBean docBean;

		public ParametersPersistenceStrategy(Document doc,DocumentBean docBean) {
			this.doc = doc;
			this.docBean = docBean;
		}
		
		public void saveChildren() {

			Iterator<Parameter> iChildren = doc.getParameters().iterator(); // children entity
			List<ParameterIF> bChildren = docBean.getParameters();	// children bean
			
			while(iChildren.hasNext()) {
				Parameter ac = iChildren.next();
		
				// search in bean...
				ParameterBean prb = null;
                for (ParameterIF aBChildren : bChildren) {
                    ParameterBean db = (ParameterBean) aBChildren;

                    if (ac.getOid() == db.getOid()) { // exist !
                        prb = db;
                        break;
                    }
                }
		
				if(prb != null) {		
					// lo rimuove dalla lista dei bean
					bChildren.remove(prb);
		
					// aggiorna il child
					updateChild(ac,prb);
				}
				else {
					// deve essere rimosso !
					iChildren.remove();
				}
			}

			// ricerca eventuali zone bean nuovi
            for (Object aBChildren : bChildren) {
                ParameterBean db = (ParameterBean) aBChildren;

                // creating a new child...
                addChild(db);
            }
		}
		
		private void updateChild(Parameter p, ParameterBean bean) {
			p.setDescription(bean.getDescription());
			p.setDocument(doc);
			p.setUser(bean.getLastUser());
			p.setParameterType(em.find(ParameterType.class,bean.getParameterType().getOid()));
		}

		private void addChild(ParameterBean bean) {
			Parameter p = new Parameter();
			p.setDescription(bean.getDescription());
			p.setDocument(doc);
			p.setUser(docBean.getLastUser());
			p.setParameterType(em.find(ParameterType.class, bean.getParameterType().getOid()));
            doc.getParameters().add(p);
		}
	}
}
