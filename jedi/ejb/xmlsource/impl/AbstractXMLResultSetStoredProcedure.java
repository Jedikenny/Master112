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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This abstract class is a base for all the classes that read data from a database using a JDBC CallableStatement based on a stored procedure that returns a ResultSet.
 * <p>
 * Each subclass must implements {@link #registerOutputParameter(CallableStatement,int) registerOutputParameter} method.
 * 
 * @see AbstractXMLFromCallableStmt
 */
public abstract class AbstractXMLResultSetStoredProcedure extends AbstractXMLFromCallableStmt {

	/**
	 * Creates a AbstractXMLResultSetStoredProcedure object.
	 */
	public AbstractXMLResultSetStoredProcedure() {
	}

    @Override
    protected void setXMLFromOutParams(CallableStatement cs) throws SQLException {
        ResultSet rs = null;
        try {
            rs = (ResultSet) cs.getObject(1);
            //Generate a standard xml
            xmlResult = JDBCUtil.toString(rs, document.getDescription());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            handleException(e);
        } finally {
            close(rs, null, null);
        }
    }


}
