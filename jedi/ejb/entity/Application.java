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

import it.senato.jedi.bean.ApplicationBean;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This entity defines an application, a group of configured documents.
 *
 */
@Entity
@Table(name = "APPLICATION")
@NamedQuery(name = "findAllApplications", query = "SELECT o FROM Application o")
public class Application implements Serializable {
    private long oid;
	private String description;

	/**
	 * Creates an Application object.
	 */
	public Application() {
		
	}

	@Column(name = "DESCRIPTION", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "IDAPPLICATION", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    /**
     * Returns a bean that contains the application's data.
     * 
     * @return a ApplicationBean object
     */
    @Transient
    public ApplicationBean getBean(){
        ApplicationBean ftb = new ApplicationBean();
        ftb.setOid(oid);
        ftb.setDescription(description);
        return ftb;
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

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timest;

    @Column(name = "TIMEST", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getTimest() {
        return timest;
    }

    public void setTimest(Timestamp timest) {
        this.timest = timest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        return oid == that.oid;

    }

    @Override
    public int hashCode() {
        int result = (int) (oid ^ (oid >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (timest != null ? timest.hashCode() : 0);
        return result;
    }
}
