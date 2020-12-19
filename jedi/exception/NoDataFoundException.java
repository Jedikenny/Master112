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
 * This exception means that, reading XML data, there wasn't find anything. 
 *
 */
public class NoDataFoundException extends JEDIRuntimeException {

	/**
	 * Creates a NoDataFoundException object.
	 * 
	 * @param message is a String object
	 */
   public NoDataFoundException(String message) {
      super(message.substring(message.indexOf("ORA-20200") + 11, message.indexOf("ORA", message.indexOf("ORA-20200") + 1)));
   }
}
