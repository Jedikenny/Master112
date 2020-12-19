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

import it.senato.jedi.bean.FormatTypeBean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This entity defines a possible format type of a document.
 *
 */
@Entity
@Table(name = "FORMATTYPE")
@NamedQuery(name = "findAllFormatTypes", query = "SELECT o FROM FormatType o")
public class FormatType implements Serializable {
    private long oid;
	private String description;
	private String mimeType;
	private String transformerClassName;

	/**
	 * Creates a FormatType object.
	 */
	public FormatType() {
		
	}

	@Column(name = "DESCRIPTION", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getTransformerClassName() {
		return transformerClassName;
	}

	public void setTransformerClassName(String transformerClassName) {
		this.transformerClassName = transformerClassName;
	}

    @Id
    @Column(name = "IDFORMATTYPE", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    /**
     * Returns a bean that contains the format type's data.
     * 
     * @return a FormatTypeBean object
     */
    @Transient
    public FormatTypeBean getBean(){
        FormatTypeBean ftb = new FormatTypeBean();
        ftb.setOid(oid);
        ftb.setDescription(description);
        ftb.setMimeType(mimeType);
        ftb.setTransformerClassName(transformerClassName);
        return ftb;
    }

    private String mimetype;

    @Column(name = "MIMETYPE", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    private String transformerclassname;

    @Column(name = "TRANSFORMERCLASSNAME", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    @Basic
    public String getTransformerclassname() {
        return transformerclassname;
    }

    public void setTransformerclassname(String transformerclassname) {
        this.transformerclassname = transformerclassname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormatType that = (FormatType) o;

        if (oid != that.oid) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (mimetype != null ? !mimetype.equals(that.mimetype) : that.mimetype != null) return false;
        if (transformerclassname != null ? !transformerclassname.equals(that.transformerclassname) : that.transformerclassname != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (oid ^ (oid >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (mimetype != null ? mimetype.hashCode() : 0);
        result = 31 * result + (transformerclassname != null ? transformerclassname.hashCode() : 0);
        return result;
    }
}
