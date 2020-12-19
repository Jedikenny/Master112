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

package it.senato.jedi.bean;

import it.senato.jedi.ejb.entity.XmlSource;
import it.senato.jedi.util.base.bean.AbstractOidBean;

/**
 * This class defines the bean of a XML source.
 *
 * @see XmlSource
 */
public class XMLSourceBean extends AbstractOidBean {
	private XMLSourceTypeBean type;
	
	private String storedProcedure;
    private String SQLQuery;
	private String url;

    private Long idDocument;

	public XMLSourceBean() {
		type = new XMLSourceTypeBean();
		type.setOid(1);
		type.setDescription("Oracle Stored procedure");
		type.setTypeClass(TypeClassEnum.StoredProcedure);
	}

    public XMLSourceTypeBean getType() {
		return type;
	}

	public void setType(XMLSourceTypeBean type) {
		this.type = type;
	}

	public String getStoredProcedure() {
		return storedProcedure;
	}

	public void setStoredProcedure(String storedProcedure) {
		this.storedProcedure = storedProcedure;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(Long idDocument) {
		this.idDocument = idDocument;
	}

    public String getSQLQuery() {
        return SQLQuery;
    }

    public void setSQLQuery(String SQLQuery) {
        this.SQLQuery = SQLQuery;
    }
}
