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

package it.senato.jedi.util.base.bean;

import java.io.Serializable;

/**
 * This bean class defines a subject that is identified by a long value.
 *
 * @see AbstractUserBean
 * @see LongIdentifier
 */
public abstract class AbstractOidBean extends AbstractUserBean implements Serializable, LongIdentifier {
	
	private long oid;

	/**
	 * Creates a AbstractOidBean object.
	 */
	public AbstractOidBean() {

	}

	@Override
	public long getOid() {
		return oid;
	}

	/**
	 * Sets the identifier.
	 * 
	 * @param oid is a long value
	 */
	public void setOid(long oid) {
		this.oid = oid;
	}

	@Override
	public boolean equals(Object o) {
		try {
			return (oid == ((AbstractOidBean) o).getOid());
		} catch (ClassCastException e) {
			return false;
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		AbstractOidBean tmp = (AbstractOidBean) super.clone();
		tmp.setOid((Long) cloneObject(oid));
		return tmp;
	}

}
