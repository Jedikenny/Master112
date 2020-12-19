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

package it.senato.jedi.exception;

/**
 * This is a JEDI runtime exception.
 *
 */
public class JEDIRuntimeException extends RuntimeException {

	/**
	 * Creates a JEDIRuntimeException object.
	 * 
	 * @param message is a String object
	 */
	public JEDIRuntimeException(String message) {
		super(message);
	}
	
	/**
	 * Creates a JEDIRuntimeException object.
	 * 
	 * @param e is an Exception object
	 */
	public JEDIRuntimeException(Exception e) {
		super(e);
	}
	
}
