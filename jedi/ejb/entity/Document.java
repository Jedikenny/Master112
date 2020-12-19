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

import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.bean.ParameterBean;
import it.senato.jedi.bean.ParameterIF;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This entity defines a document configuration.
 *
 */
@Entity
@Table(name = "DOCUMENT")
@NamedQuery(name="findDocuments",
            query="SELECT e FROM Document e WHERE e.application.oid = coalesce(:idApplication, e.application.oid)")

public class Document {
    private long oid;

    /**
     * Creates a Document object.
     */
    public Document(){
        parameters = new ArrayList<Parameter>();
    }

    @javax.persistence.Column(name = "IDDOCUMENT", nullable = false, insertable = true, updatable = true, length = 9, precision = 0)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCUMENTSEQ")
    @SequenceGenerator(name="DOCUMENTSEQ", sequenceName="DOCUMENTSEQ", allocationSize =1)
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

    private String transformer;

    @javax.persistence.Column(name = "TRANSFORMER", nullable = false, insertable = true, updatable = true, length = 4000, precision = 0)
    @Lob
    @Basic
    public String getTransformer() {
        return transformer;
    }

    public void setTransformer(String transformer) {
        this.transformer = transformer;
    }


    private ReusableTransformer reusableTransformer;

    @ManyToOne()
    @Fetch(FetchMode.JOIN)
    public
    @JoinColumn(name = "IDREUSABLETRANSFORMER", referencedColumnName = "IDREUSABLETRANSFORMER", nullable = true)
    ReusableTransformer getReusableTransformer() {
        return reusableTransformer;
    }

    public void setReusableTransformer(ReusableTransformer reusableTransformer) {
        this.reusableTransformer = reusableTransformer;
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

        Document document = (Document) o;

        if (oid != document.oid) return false;
        if (description != null ? !description.equals(document.description) : document.description != null)
            return false;
        if (timest != null ? !timest.equals(document.timest) : document.timest != null) return false;
        if (transformer != null ? !transformer.equals(document.transformer) : document.transformer != null)
            return false;
        if (user != null ? !user.equals(document.user) : document.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (oid ^ (oid >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (transformer != null ? transformer.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (timest != null ? timest.hashCode() : 0);
        return result;
    }

    private Application application;

    @ManyToOne()
    @Fetch(FetchMode.JOIN)
    public
    @JoinColumn(name = "IDAPPLICATION", referencedColumnName = "IDAPPLICATION", nullable = false)
    Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    private FormatType formatType;

    @ManyToOne()
    @Fetch(FetchMode.JOIN)
    public
    @JoinColumn(name = "IDFORMATTYPE", referencedColumnName = "IDFORMATTYPE", nullable = false)
    FormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    private XmlSource xmlSource;

    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    public
    @JoinColumn(name = "IDXMLSOURCE", referencedColumnName = "IDXMLSOURCE", nullable = false)
    XmlSource getXmlSource() {
        return xmlSource;
    }

    public void setXmlSource(XmlSource xmlSource) {
        this.xmlSource = xmlSource;
    }


    private List<Parameter> parameters;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "document",orphanRemoval=true)
    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns a bean that contains the document's data.
     * 
     * @return a DocumentBean object
     */
    @Transient
    public DocumentBean getBean(boolean justProxy) throws SQLException, IOException, JEDIInfrastructureException, JEDIConfigException {
        DocumentBean bean = new DocumentBean();
        bean.setOid(oid);
        bean.setDescription(description);
        bean.setApplication(application.getBean());
        bean.setFormatType(formatType.getBean());
        bean.setXmlSource(xmlSource.getBean());
        bean.setLastUser(user);
        bean.setTimeStamp(timest);
        if (reusableTransformer!=null && reusableTransformer.getOid()>0)
            bean.setReusableTransformer(reusableTransformer.getBean());


        if (!justProxy){
            List<ParameterBean> params = new ArrayList<ParameterBean>();
            for (Parameter param : parameters)
                params.add(param.getBean());

            Collections.sort((List<ParameterBean>)params);

            bean.setParameters(new ArrayList<ParameterIF>(params));
            bean.setTransformer(transformer);
        }

        return bean;
    }

}
