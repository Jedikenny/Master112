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

/**
 * This interface defines a subject capable to be identified and distinguished from another one. 
 *
 */
public interface Identifier extends Cloneable {
	
	/**
	 * Checks if two objects are the same.
	 * 
	 * @param o is a Object
	 * @return true is the objects are the same. Otherwise false
	 */
	boolean equals(Object o);
	
	/**
	 * Clones an object.
	 * @return a Object object
	 * @throws CloneNotSupportedException
	 */
	Object clone() throws CloneNotSupportedException;
}
