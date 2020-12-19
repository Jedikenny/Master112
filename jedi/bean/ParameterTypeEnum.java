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

import it.senato.jedi.util.base.bean.Listable;

/**
 * Enumeration that defines the possible parameter types.
 *
 */
public enum ParameterTypeEnum implements Listable {
	Integer(1,"java.lang.Integer", "Integer"), String(2,"java.lang.String", "String"),Date(3,"java.sql.Date", "Date"),
	Double(4,"java.lang.Double", "Double"),Boolean(5,"java.lang.Boolean", "Boolean"),Long(6,"java.lang.Long", "Long");
	
	private long oid;
    private String description;
    private String className;

	private ParameterTypeEnum(long oid, String className, String description) {
		this.oid = oid;
        this.className = className;
        this.description = description;
	}

    public long getOid() {
        return oid;
    }


    public String getDescription() {
		return description;
	}


	public String getClassName() {
		return className;
	}

    public static ParameterTypeEnum getInstance(long oid){
        ParameterTypeEnum values[] = ParameterTypeEnum.values();

        for (ParameterTypeEnum stato : values) {
            if(stato.getOid() == oid)
                return stato;
        }

        return null;
    }

    @Override
    public String toString() {
        return description;
    }

}
