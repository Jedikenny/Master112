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

import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.bean.ParameterIF;
import it.senato.jedi.web.util.DocOutputWriter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "DocumentTest")
@SessionScoped
public class DocumentTest extends AbstractBackingBean {

    private DocumentBean document;

    private Long idDocument;

    private byte[] output;

    private String XMLSourceResult;

    public String getXMLSourceResult() {
        return XMLSourceResult;
    }

    public void setXMLSourceResult(String XMLSourceResult) {
        this.XMLSourceResult = XMLSourceResult;
    }

    public Long getIdDocument() {
        return idDocument;
    }


    public void setIdDocument(Long idDocument) {

        this.idDocument = idDocument;

        lookup();
    }

    private void lookup(){

        if (idDocument==null || idDocument==0){
            document=null;
            XMLSourceResult=null;
        } else
            try {
                document = confDelegate.lookupDocument(idDocument);
                XMLSourceResult=null;
            } catch (Exception e) {
                sendException(null, "lookupKO", e);
            }

    }

    public DocumentBean getDocument() {
        if (document==null)
            lookup();

        return document;
    }

    public void setDocument(DocumentBean document) {
        this.document = document;
    }

    private String[] getParamValues(){
        List<String> list = new ArrayList<String>();

        for (ParameterIF par : document.getParameters())
            list.add(par.getValueAsString());

        return list.toArray(new String[list.size()]);
    }

    public void generateDocument() {
        try {
            output = docDelegate.createDocumentByValues(document, getParamValues(), document.getFormatType());
            XMLSourceResult=null;
            DocOutputWriter.writeDocument(getResponse(), document.getFormatType(), output);
            getContext().responseComplete();
        } catch (Exception e) {
            sendException(null, "docGenKO", e);
        }
    }

    public void generateXML() {
        try {
            XMLSourceResult = docDelegate.readXMLSource(document, getParamValues());
            DocOutputWriter.writeDocument(getResponse(), getSessionUtils().lookupFormatType(5/*XML*/), XMLSourceResult.getBytes());
            getContext().responseComplete();
        } catch (Exception e) {
            sendException(null, "docGenKO", e);
        }

    }

    public void generateByXML() {
        try {
            output = docDelegate.createDocumentByXML(document, XMLSourceResult, document.getFormatType());
            DocOutputWriter.writeDocument(getResponse(), document.getFormatType(), output);
            getContext().responseComplete();

        } catch (Exception e) {
            sendException(null, "docGenKO", e);
        }
    }

}
