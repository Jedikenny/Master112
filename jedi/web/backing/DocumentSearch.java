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
import it.senato.jedi.ejb.JEDIConfigDelegate;
import it.senato.jedi.web.util.GeneralComparator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import java.util.*;

@ManagedBean(name = "DocumentSearch")
@SessionScoped
public class DocumentSearch extends AbstractSearchBackingBean {

    public DocumentSearch(){
    }

    @Override
    public Class<DocumentBean> getClassT() {
        return DocumentBean.class;
    }

    private Long idApplication;
    private Long idFormatType;
    private String description;


    //DAO Methods

    public void loadAllData() {
        try {

            queryResults = confDelegate.getDocuments(idApplication, description, idFormatType, null);
            totalRows = queryResults.size();

        } catch (Exception e) {
            sendException(null, "lookupKO", e);
        }

    }
    //END DAO Methods



// Getters ------------------------------------------------------------------------------------


    public Long getIdApplication() {
        return idApplication;
    }

    public Long getIdFormatType() {
        return idFormatType;
    }

    public String getDescription() {
        return description;
    }


// Setters ------------------------------------------------------------------------------------


    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdFormatType(Long idFormatType) {
        this.idFormatType = idFormatType;
    }

    public void setIdApplication(Long idApplication) {
        this.idApplication = idApplication;
    }

}
