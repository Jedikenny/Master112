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

package it.senato.jedi.ejb.xmlsource.impl;


import java.sql.CallableStatement;
import java.sql.SQLException;


/**
 * This abstract class is a base for all the classes that read data from a database using a JDBC CallableStatement based on a stored procedure.
 * <p>
 * Each subclass must implements {@link #getXMLObject(CallableStatement,int) getXMLObject} method.
 * 
 * @see AbstractXMLFromCallableStmt
 */
public abstract class AbstractXMLStoredProcedure extends AbstractXMLFromCallableStmt {

	/**
	 * Creates a AbstractXMLStoredProcedure object.
	 */
	public AbstractXMLStoredProcedure() {
	}

    @Override
    protected void setXMLFromOutParams(CallableStatement cs) throws SQLException {
        try {
		    // Read the XML output
	        xmlResult = getXMLObject(cs,1);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            handleException(e);
        }
    }

	/**
	 * Reads the XML data from the output parameter of the statement.
	 * 
	 * @param cs is the CallableStatement object
	 * @param index is the index parameter
	 * @return a String of XML data
	 * @throws SQLException
	 */
	protected abstract String getXMLObject(CallableStatement cs,int index) throws SQLException;

}
