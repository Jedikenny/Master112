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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This entity defines the type of a parameter.
 *
 */
@Entity
public class ParameterType {
    private long oid;

    @Column(name = "IDPARAMETERTYPE", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    @Id
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

    private String classname;

    @Column(name = "CLASSNAME", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParameterType that = (ParameterType) o;

        if (oid != that.oid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (oid ^ (oid >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (classname != null ? classname.hashCode() : 0);
        return result;
    }
}
