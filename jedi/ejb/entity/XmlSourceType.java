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

package it.senato.jedi.ejb.entity;

import it.senato.jedi.bean.TypeClassEnum;
import it.senato.jedi.bean.XMLSourceTypeBean;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;

import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 * This entity defines a type of a XML source.
 *
 */
@Entity
@NamedQuery(name = "findAllXMLSourceTypes", query = "SELECT o FROM XmlSourceType o")
public class XmlSourceType {
    private long oid;

    @javax.persistence.Column(name = "IDXMLSOURCETYPE", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    @Id
    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    private String description;

    @javax.persistence.Column(name = "DESCRIPTION", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    private String readerClassName;
    
    @javax.persistence.Column(name = "READERCLASSNAME", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    @Basic
    public String getReaderClassName() {
		return readerClassName;
	}

	public void setReaderClassName(String readerClassName) {
		this.readerClassName = readerClassName;
	}
	
	private int typeClass;

    @javax.persistence.Column(name = "TYPECLASS", nullable = false, insertable = true, updatable = true, length = 2, precision = 0)
    @Basic
	public int getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(int typeClass) {
		this.typeClass = typeClass;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XmlSourceType that = (XmlSourceType) o;

        if (oid != that.oid) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (oid ^ (oid >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    /**
     * Returns a bean that contains the XML source type's data.
     * 
     * @return a XMLSourceTypeBean object
     */    
    @Transient
    public XMLSourceTypeBean getBean() throws SQLException, IOException, JEDIInfrastructureException, JEDIConfigException {    	
    	XMLSourceTypeBean bean = new XMLSourceTypeBean();
        bean.setOid(oid);
        bean.setDescription(description);
        bean.setReaderClassName(readerClassName);
        bean.setTypeClass(TypeClassEnum.getInstance(typeClass));

        return bean;
    }
}
