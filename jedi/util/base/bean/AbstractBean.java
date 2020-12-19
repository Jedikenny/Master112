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
import java.util.Date;

/**
 * This abstract class is a base for all the bean classes.
 *
 */
public abstract class AbstractBean implements Serializable, Comparable<AbstractBean> {

	/**
	 * Creates a AbstractBean object.
	 */
	public AbstractBean() {
		super();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		AbstractBean tmp = (AbstractBean) super.clone();
		return tmp;
	}

	/**
	 * Clones an object.
	 * 
	 * @param value is an Object
	 * @return an Object
	 * @throws CloneNotSupportedException
	 */
	protected Object cloneObject(Object value)
			throws CloneNotSupportedException {
		if (value == null)
			return null;

		if (value instanceof Date)
			return ((Date) value).clone();

		if (value instanceof Long)
			return new Long((Long) value);

		if (value instanceof Integer)
			return new Integer((Integer) value);

		if (value instanceof Double)
			return new Double((Double) value);

		if (value instanceof String)
			return new String((String) value);

		if (value instanceof Boolean)
			return new Boolean((Boolean) value);

		/*
		 * if (value instanceof BaseBean) return ((BaseBean) value).clone();
		 * 
		 * if (value instanceof Identifier) return ((Identifier) value).clone();
		 */
		return null;
	}

	/**
	 * Compares two objects handling also null values.
	 * 
	 * @param value1 is an Object
	 * @param value2 is an Object
	 * @return true if the same. Otherwise false
	 */
	protected boolean compareObjects(Object value1, Object value2) {
		if (value1 == null) {
			return (value2 == null);
		} else {
			if (value2 == null)
				return false;
		}
		return (value1.equals(value2));
	}

    @Override
    public int compareTo(AbstractBean o) {
        if (o==null)
            return -1;

        if (toString()==null)
            if (o.toString()==null)
                return 0;
            else
                return 1;

        if (o.toString()==null)
            return -1;

        return toString().compareTo(o.toString());
    }

}
