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

package it.senato.jedi.web.backing;

import it.senato.jedi.bean.*;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;


import java.util.List;

@ManagedBean(name = "DocumentDetail")
@ViewScoped
public class DocumentDetail extends AbstractBackingBean{


    public DocumentDetail(){
    }

    private Long oid;

    private DocumentBean documentDetail;


    public DocumentBean getDocumentDetail() {
        return documentDetail;
    }

    public void setDocumentDetail(DocumentBean documentDetail) {
        this.documentDetail = documentDetail;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public DocumentBean getXMLSourceDocument() {
    	if(documentDetail == null)
    		return null;	// do nothing

    	if(documentDetail.getXmlSource().getIdDocument() == null)
    		return null;
    	
    	// load document bean
    	log.debug("getDocument -> lookupDocument");
        try {
			return confDelegate.lookupDocument(documentDetail.getXmlSource().getIdDocument());
		} catch (Exception e) {
            sendException(null, "lookupKO", e);
		}
        
        return null;
    }

    public void setXMLSourceDocument(DocumentBean document) {
    	if(documentDetail == null)
    		return;	// do nothing
    	
        // set idDocument of XML Source
        if(document != null && document.getOid() != 0)
       		documentDetail.getXmlSource().setIdDocument(document.getOid());
        else
        	documentDetail.getXmlSource().setIdDocument(null);
    }

    public String init() {

        if (!FacesContext.getCurrentInstance().isPostback()){
            documentDetail = null;

            if (oid!=null)
                try {
                	log.debug("lookupDocument da init");
                    documentDetail = confDelegate.lookupDocument(oid);
                    return "detail";
                } catch (Exception e) {
                    sendException(null, "lookupKO", e);
                    return "lookupError";
                }
            else {
                //init the bean
                documentDetail = new DocumentBean();
                return "new";
            }
        } else
            return "postback";


    }

    public void save() {

        try {
        	documentDetail.setLastUser(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());

            boolean wasNew = false;

            //for newly created document referencing older documents clone parameter list
            if (documentDetail.getOid()==0){
                wasNew = true;

                if (documentDetail.getXMLSourceType().getTypeClass()== TypeClassEnum.JEDIXMlDocument){
                    //clone parameter list
                    //lookup referenced document
                    DocumentBean refDoc = confDelegate.lookupDocument(documentDetail.getXmlSource().getIdDocument());
                    for (ParameterIF par : refDoc.getParameters()){
                        ParameterBean pb = new ParameterBean();
                        pb.setType(par.getParameterType());
                        pb.setDescription(par.getDescription());
                        documentDetail.getParameters().add(pb);
                    }

                }
            }

            documentDetail = confDelegate.saveDocument(documentDetail);

            if (getSession() != null) {

                SessionUtils su = getSessionUtils();
                List<DocumentBean> listAll = su.getDocuments();

                if (listAll!=null) {
                    int i = listAll.indexOf(documentDetail);
                    
                    if (listAll.size() > 0 && !wasNew && i!=-1)
                        listAll.set(i, documentDetail);

                    if (wasNew && i==0)
                        listAll.add(documentDetail);
                }

                if (!wasNew){
                    DocumentSearch search = (DocumentSearch) getSession().getAttribute("DocumentSearch");
                    //looking for the bean to update in the list results
                    if (search!=null){
                        List<DocumentBean> list = search.getQueryResults();
                        if (list!=null && list.size()>0){
                            int i = list.indexOf(documentDetail);
                            if (i != -1)
                                list.set(i, documentDetail);
                        }
                    }

                    //looking for the bean to update in the list buffer
                    if (search!=null){
                        List<DocumentBean> list = search.getResultsBuffer();
                        if (list!=null && list.size()>0){
                            int i = list.indexOf(documentDetail);
                            if (i != -1)
                                list.set(i, documentDetail);
                        }
                    }
                }
            }

            sendGeneralInfo("saveOK");

        } catch (Exception e) {
            sendException(null, "saveKO", e);
        }

    }


    public List<DocumentBean> autocompleteDocuments(String search) {

        FacesContext context = FacesContext.getCurrentInstance();

        List<DocumentBean> result = null;

        try {
            result = confDelegate.getDocuments(null, search, 5L/*Documents returning XML*/, null);

            //forbidden due to circular refernce
            if (documentDetail!=null && documentDetail.getOid()!=0){
                List<DocumentBean> filter = confDelegate.getDocumentsDependingBy(documentDetail.getOid());

                result.removeAll(filter);

            }

        } catch (Exception e) {
            sendException("myform:documentSource", "lookupKO", e);
            return null;
        }

        return result;
    }

    public void addParameter(){
        documentDetail.getParameters().add(new ParameterBean());
    }

    public void removeParameter(int rowIndex){
        documentDetail.getParameters().remove(rowIndex);
    }

    public Long getIdType(){
        return documentDetail.getXmlSource().getType().getOid();
    }

    public void setIdType(Long oid){
        SessionUtils su = getSessionUtils();

        try {
            documentDetail.getXmlSource().setType(su.lookupXMLSourceType(oid));
        } catch (Exception e) {
        }

    }

    public Long getIdFormatType(){
        return documentDetail.getFormatType().getOid();
    }

    public void setIdFormatType(Long oid){
        SessionUtils su = getSessionUtils();

        try {
            documentDetail.setFormatType(su.lookupFormatType(oid));
        } catch (Exception e) {
        }

    }

    public Long getIdApplication(){
        return documentDetail.getApplication().getOid();
    }

    public void setIdApplication(Long oid){
        SessionUtils su = getSessionUtils();

        try {
            documentDetail.setApplication(su.lookupApplication(oid));
        } catch (Exception e) {
        }

    }

    public Long getIdReusableTransformer(){
        if (documentDetail.getReusableTransformer()==null)
            return 0L;
        
        return documentDetail.getReusableTransformer().getOid();
    }

    public void setIdReusableTransformer(Long oid){
        SessionUtils su = getSessionUtils();

        try {
            documentDetail.setReusableTransformer(su.lookupReusableTransformer(oid));
        } catch (Exception e) {
        }

    }

    public TransformerTypeEnum getTransformerType(){
        return documentDetail.getTransformerType();
    }

    public void setTransformerType(TransformerTypeEnum transformerType){
        if (transformerType==TransformerTypeEnum.Reusable && documentDetail.getReusableTransformer()==null)
            try {
                documentDetail.setReusableTransformer(getSessionUtils().getReusableTransformers().get(0));
            } catch (Exception e) {
            }

        documentDetail.setTransformerType(transformerType);
    }


}
