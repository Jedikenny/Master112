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

import java.util.Comparator;

/**
 * Generic comparator for classes that implement Identifier. 
 *
 * @param <T>
 * @see Identifier
 */
public class IdentifierComparator<T extends Identifier> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		return o1.toString().compareTo(o2.toString());
	}

}
