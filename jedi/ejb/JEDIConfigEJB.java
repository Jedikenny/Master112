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
import it.senato.jedi.ejb.action.SaveDocumentAction;
import it.senato.jedi.ejb.action.SaveReusableTransformerAction;
import it.senato.jedi.ejb.entity.*;
import it.senato.jedi.exception.JEDIConfigException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This EJB provides all the operations to manage documents configuration.
 *
 */
@Stateless(mappedName="JEDIConfig")
public class JEDIConfigEJB extends AbstractEJB implements JEDIConfigRemote {

    @PersistenceContext
    private EntityManager em;

	@Override
	public List<FormatTypeBean> getFormatTypes() throws JEDIConfigException {
        try {
            List<FormatType> domainList = em.createNamedQuery("findAllFormatTypes").getResultList();

            List<FormatTypeBean> result = new ArrayList<FormatTypeBean>();
            for (FormatType bean : domainList){
                result.add(bean.getBean());
            }
            return result;
        } catch (Exception e) {
            throw new JEDIConfigException(e.getMessage());
        }
	}

	@Override
	public List<XMLSourceTypeBean> getXMLSourceTypes() throws JEDIConfigException {
        try {
            List<XmlSourceType> domainList = em.createNamedQuery("findAllXMLSourceTypes").getResultList();

            List<XMLSourceTypeBean> result = new ArrayList<XMLSourceTypeBean>();
            for (XmlSourceType bean : domainList){
                result.add(bean.getBean());
            }
            return result;
        } catch (Exception e) {
            throw new JEDIConfigException(e.getMessage());
        }
	}

	@Override
	public List<ApplicationBean> getApplications() throws JEDIConfigException {
        try {
            List<Application> domainList = em.createNamedQuery("findAllApplications").getResultList();

            List<ApplicationBean> result = new ArrayList<ApplicationBean>();
            for (Application bean : domainList){
                result.add(bean.getBean());
            }
            return result;
        } catch (Exception e) {
            throw new JEDIConfigException(e.getMessage());
        }
	}

    @Override
    public List<ReusableTransformerBean> getReusableTransformers() throws JEDIConfigException {
        try {
            List<ReusableTransformer> domainList = em.createNamedQuery("findAllReusableTransformers").getResultList();

            List<ReusableTransformerBean> result = new ArrayList<ReusableTransformerBean>();
            for (ReusableTransformer bean : domainList){
                result.add(bean.getBean());
            }
            return result;
        } catch (Exception e) {
            throw new JEDIConfigException(e.getMessage());
        }
    }

	@Override
	public List<ReusableTransformerBean> getReusableTransformers(String descriptionLike) throws JEDIConfigException {
        try {


            String query = " select {rt.*} " +
                           " from   reusableTransformer rt " +
                           " where  lower(rt.description) like '%' || '" + (descriptionLike==null?"":descriptionLike.toLowerCase()) + "' || '%' "
                    ;
            Session session = em.unwrap(Session.class);
            List<ReusableTransformer> list = (List<ReusableTransformer>)session.createSQLQuery(query)
                                    .addEntity("rt", ReusableTransformer.class)
                                    .list()
                    ;

            List<ReusableTransformerBean> result = new ArrayList<ReusableTransformerBean>();
            for (ReusableTransformer bean : list){
                result.add((bean).getBean());
            }
            return result;
        } catch (Exception e) {
            throw new JEDIConfigException(e.getMessage());
        }
	}

	@Override
	public List<DocumentBean> getDocuments(Long idApplication, String descriptionLike, Long idFormatType, String descriptionEquals) throws JEDIConfigException {
        try {


            String query = " select {d.*}, {xs.*}, {xst.*}, {a.*}, {ft.*}, {rt.*} " +
                           " from   document d, xmlSource xs, xmlSourceType xst, application a, formatType ft, reusableTransformer rt " +
                           " where  d.idXMLSource = xs.idXMLSource " +
                           " and    a.idApplication = d.idApplication " +
                           " and    ft.idFormatType = d.idFormatType " +
                           " and    xst.idXmlSourceType = xs.idXmlSourceType " +
                           " and    rt.idReusableTransformer (+) = d.idReusableTransformer " +
                           " and    d.idApplication = nvl(" + (idApplication==null||idApplication==0?"null":idApplication) + ", d.idApplication) " +
                           " and    lower(d.description) like '%' || '" + (descriptionLike==null?"":descriptionLike.toLowerCase()) + "' || '%' " +
                           " and    d.idFormatType = nvl(" + (idFormatType==null||idFormatType==0?"null":idFormatType) + ", d.idFormatType) " +
                           " and    d.description = nvl(" + (descriptionEquals==null||descriptionEquals.length()==0?"null":"'"+descriptionEquals+"'") + ", d.description) " +
                           " order by d.idDocument"
                    ;
            Session session = em.unwrap(Session.class);
            List<Object[]> list = (List<Object[]>)session.createSQLQuery(query)
                                    .addEntity("d", Document.class)
                                    .addEntity("xs", XmlSource.class)
                                    .addEntity("xst", XmlSourceType.class)
                                    .addEntity("a", Application.class)
                                    .addEntity("ft", FormatType.class)
                                    .addEntity("rt", ReusableTransformer.class)
                                    .list()
                    ;

//            List<Document> domainList = (List<Document>)em.createNativeQuery(query, Document.class).getResultList();


            List<DocumentBean> result = new ArrayList<DocumentBean>();
            for (Object[] bean : list){
                result.add(((Document)bean[0]).getBean(true));
            }
            return result;
        } catch (Exception e) {
            throw new JEDIConfigException(e.getMessage());
        }
	}

	@Override
	public DocumentBean lookupDocument(long idDocument) throws JEDIConfigException {
		logger.debug("lookupDocument idDocument: " + idDocument);
		
		try {
			Document document = em.find(Document.class,idDocument);
		
			return document.getBean(false);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new JEDIConfigException(e.getMessage());
		}
	}

    @Override
    public ReusableTransformerBean lookupReusableTransformer(long idReusableTransformer) throws JEDIConfigException {
        logger.debug("lookupReusableTransformer idReusableTransformer: " + idReusableTransformer);

        try {
            ReusableTransformer reusableTransformer = em.find(ReusableTransformer.class, idReusableTransformer);

            return reusableTransformer.getBean();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new JEDIConfigException(e.getMessage());
        }
    }


	@Override
	public DocumentBean saveDocument(DocumentBean documentBean) throws JEDIConfigException {
		SaveDocumentAction action = new SaveDocumentAction(em);
		return action.execute(documentBean);
	}

    @Override
    public ReusableTransformerBean saveReusableTransformer(ReusableTransformerBean reusableTransformerBean) throws JEDIConfigException {
        SaveReusableTransformerAction action = new SaveReusableTransformerAction(em);
        return action.execute(reusableTransformerBean);
    }


	@Override
	public List<DocumentBean> getDocumentsDependingBy(long idDocument) throws JEDIConfigException {
		logger.debug("getDocumentsDependingBy idDocument: " + idDocument);
		
        try {
            String query = " SELECT iddocument " +
            			   " FROM ( " + 
            			   " 	select " +
            			   "	d.iddocument as iddocument, x.iddocument as iddocument_parent " +
            			   "	from " +
            			   "		jedi.document d " +
            			   "	,   jedi.xmlsource x " +    
            			   "	,   jedi.xmlsourcetype y " +    
            			   "	where " +
            			   "	d.idxmlsource = x.idxmlsource " +
            			   "	and x.idxmlsourcetype = y.idxmlsourcetype " +
            			   //"	and y.typeClass = " + TypeClassEnum.JEDIXMlDocument.getOid() +    
            			   "	) " +
            			   " START WITH iddocument = " + idDocument +
            			   " CONNECT BY PRIOR iddocument = iddocument_parent ";
            
            List<? extends Number> list = em.createNativeQuery(query).getResultList();
            List<DocumentBean> result = new ArrayList<DocumentBean>();
            for (Number l : list){
                DocumentBean doc = new DocumentBean();
                doc.setOid(l.longValue());
                result.add(doc);
            }

            
            return result;
        } catch (Exception e) {
            throw new JEDIConfigException(e.getMessage());
        }
	}
}
