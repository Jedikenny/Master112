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

/**
 * Enumeration that defines the possible class types.
 *
 */
public enum TypeClassEnum {
	StoredProcedure(1, "Stored procedure"), JEDIXMlDocument(2, "JEDI Xml Document"), Url(3, "Url"), SQLQuery(4, "SQL Query");

	private long oid;
	private String description;
	
	private TypeClassEnum(long oid,String description) {
		this.oid = oid;
		this.description = description;
	}

	public long getOid() {
		return oid;
	}

    public String getDescription() {
		return description;
	}

	public static TypeClassEnum getInstance(long oid){
		TypeClassEnum values[] = TypeClassEnum.values();

        for (TypeClassEnum bean : values) {
            if(bean.getOid() == oid)
                return bean;
        }

        return null;
    }

    @Override
    public String toString() {
        return description;
    }
}
