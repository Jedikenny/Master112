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

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.XMLSourceBean;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * This entity defines a source of XML data.
 *
 */
@Entity
public class XmlSource {
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long oid;

    @javax.persistence.Column(name = "IDXMLSOURCE", nullable = false, insertable = true, updatable = true, length = 9, precision = 0)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="XMLSOURCESEQ")
    @SequenceGenerator(name="XMLSOURCESEQ", sequenceName="XMLSOURCESEQ", allocationSize =1)
    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    private Long idDocument;

    @javax.persistence.Column(name = "IDDOCUMENT", nullable = true, insertable = true, updatable = true, length = 9, precision = 0)
    @Basic
    public Long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }

    private String storedprocedure;

    @javax.persistence.Column(name = "STOREDPROCEDURE", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getStoredprocedure() {
        return storedprocedure;
    }

    public void setStoredprocedure(String storedprocedure) {
        this.storedprocedure = storedprocedure;
    }


    private String SQLQuery;

    @javax.persistence.Column(name = "SQLQUERY", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getSQLQuery() {
        return SQLQuery;
    }

    public void setSQLQuery(String SQLQuery) {
        this.SQLQuery = SQLQuery;
    }


    private String url;

    @javax.persistence.Column(name = "URL", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

        XmlSource xmlSource = (XmlSource) o;

        if (!idDocument.equals(xmlSource.idDocument)) return false;
        if (oid != xmlSource.oid) return false;
        if (storedprocedure != null ? !storedprocedure.equals(xmlSource.storedprocedure) : xmlSource.storedprocedure != null)
            return false;
        if (timest != null ? !timest.equals(xmlSource.timest) : xmlSource.timest != null) return false;
        if (url != null ? !url.equals(xmlSource.url) : xmlSource.url != null) return false;
        if (user != null ? !user.equals(xmlSource.user) : xmlSource.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (oid ^ (oid >>> 32));
        result = 31 * result + (int) (idDocument ^ (idDocument >>> 32));
        result = 31 * result + (storedprocedure != null ? storedprocedure.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (timest != null ? timest.hashCode() : 0);
        return result;
    }

    private XmlSourceType xmlSourceType;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    public
    @javax.persistence.JoinColumn(name = "IDXMLSOURCETYPE", referencedColumnName = "IDXMLSOURCETYPE", nullable = false)
    XmlSourceType getXmlSourceType() {
        return xmlSourceType;
    }

    public void setXmlSourceType(XmlSourceType xmlSourceType) {
        this.xmlSourceType = xmlSourceType;
    }

    /**
     * Returns a bean that contains the XML source's data.
     * 
     * @return a XMLSourceBean object
     */    
    @Transient
    public XMLSourceBean getBean() throws SQLException, IOException, JEDIInfrastructureException, JEDIConfigException {
    	Logger.getLogger(LogChannel.EJB_CHANNEL).debug("getBean oid: " + getOid() + " type: " + xmlSourceType.getOid() + " idDocument: " + idDocument);    	
    	
        XMLSourceBean bean = new XMLSourceBean();
        bean.setOid(oid);
        bean.setStoredProcedure(storedprocedure);
        bean.setSQLQuery(SQLQuery);
        bean.setIdDocument(idDocument);
        bean.setType(xmlSourceType.getBean());
        bean.setUrl(url);
        bean.setLastUser(user);
        bean.setTimeStamp(timest);

        return bean;
    }

}
