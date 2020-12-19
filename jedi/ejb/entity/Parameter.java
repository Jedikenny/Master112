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

import it.senato.jedi.bean.ParameterBean;
import it.senato.jedi.bean.ParameterTypeEnum;

import javax.persistence.*;
import java.util.Date;

/**
 * This entity defines a parameter used to produce a document.
 *
 */
@Entity
@SequenceGenerator(name="PARAMETERSEQ", sequenceName="PARAMETERSEQ")
public class Parameter {
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long oid;

    @javax.persistence.Column(name = "IDPARAMETER", nullable = false, insertable = true, updatable = true, length = 9, precision = 0)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PARAMETERSEQ")
    @SequenceGenerator(name="PARAMETERSEQ", sequenceName="PARAMETERSEQ", allocationSize =1)
    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    private Document document;

    @ManyToOne
    public
    @javax.persistence.JoinColumn(name = "IDDOCUMENT", referencedColumnName = "IDDOCUMENT", nullable = false)
    Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
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

    private String user;

    @javax.persistence.Column(name = "LASTUSER", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private Date timest;

    @Version
    @javax.persistence.Column(name = "TIMEST", nullable = false, insertable = true, updatable = true, length = 7, precision = 0, columnDefinition="DATE DEFAULT SYSDATE")
    public Date getTimest() {
        return timest;
    }

    public void setTimest(Date timest) {
        this.timest = timest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (oid != parameter.oid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (oid ^ (oid >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (timest != null ? timest.hashCode() : 0);
        return result;
    }

    private ParameterType parameterType;

    @ManyToOne
    public
    @javax.persistence.JoinColumn(name = "IDPARAMETERTYPE", referencedColumnName = "IDPARAMETERTYPE", nullable = false)
    ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }

    /**
     * Returns a bean that contains the parameter's data.
     * 
     * @return a ParameterBean object
     */
    @Transient
    public ParameterBean getBean(){
        ParameterBean bean = new ParameterBean();
        bean.setOid(oid);
        bean.setDescription(description);
        bean.setType(ParameterTypeEnum.getInstance(parameterType.getOid()));
        bean.setIdDocument(document.getOid());
        bean.setLastUser(user);
        bean.setTimeStamp(timest);

        return bean;
    }

}
