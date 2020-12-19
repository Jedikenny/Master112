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

import it.senato.jedi.bean.ReusableTransformerBean;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;

import javax.persistence.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * This entity defines a transformer that can be used between multiple documents. 
 * 
 *
 */
@Entity
@Table(name = "REUSABLETRANSFORMER")
@NamedQuery(name = "findAllReusableTransformers", query = "SELECT o FROM ReusableTransformer o")

public class ReusableTransformer {
    private long oid;

    /**
     * Creates a ReusableTransformer object.
     */
    public ReusableTransformer(){
    }

    @Column(name = "IDREUSABLETRANSFORMER", nullable = false, insertable = true, updatable = true, length = 9, precision = 0)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REUSABLETRANSFORMERSEQ")
    @SequenceGenerator(name="REUSABLETRANSFORMERSEQ", sequenceName="REUSABLETRANSFORMERSEQ", allocationSize =1)
    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    private String description;

    @Column(name = "DESCRIPTION", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String transformer;

    @Column(name = "TRANSFORMER", nullable = false, insertable = true, updatable = true, length = 4000, precision = 0)
    @Lob
    @Basic
    public String getTransformer() {
        return transformer;
    }

    public void setTransformer(String transformer) {
        this.transformer = transformer;
    }

    private String user;

    @Column(name = "LASTUSER", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private Date timest;

    @Version
    @Column(name = "TIMEST", nullable = false, insertable = true, updatable = true, length = 7, precision = 0, columnDefinition="DATE DEFAULT SYSDATE")
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

        ReusableTransformer ru = (ReusableTransformer) o;

        if (oid != ru.oid) return false;
        if (description != null ? !description.equals(ru.description) : ru.description != null)
            return false;
        if (timest != null ? !timest.equals(ru.timest) : ru.timest != null) return false;
        if (transformer != null ? !transformer.equals(ru.transformer) : ru.transformer != null)
            return false;
        if (user != null ? !user.equals(ru.user) : ru.user != null) return false;

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

    /**
     * Returns a bean that contains the reusable transformer's data.
     * 
     * @return a ReusableTransformerBean object
     */
    @Transient
    public ReusableTransformerBean getBean() throws SQLException, IOException, JEDIInfrastructureException, JEDIConfigException {
        ReusableTransformerBean bean = new ReusableTransformerBean();
        bean.setOid(oid);
        bean.setDescription(description);
        bean.setLastUser(user);
        bean.setTimeStamp(timest);

        bean.setTransformer(transformer);

        return bean;
    }

}
