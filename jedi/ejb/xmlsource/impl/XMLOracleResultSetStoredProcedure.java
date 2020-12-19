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

import oracle.jdbc.OracleTypes;

import java.sql.*;

/**
 * This class reads ResultSet by calling a Oracle JDBC stored procedure and converts it to an XML string.
 * 
 * @see AbstractXMLResultSetStoredProcedure
 */
public class XMLOracleResultSetStoredProcedure extends AbstractXMLResultSetStoredProcedure {

	@Override
    protected void registerOutputParameter(CallableStatement cs,int index) throws SQLException {
        cs.registerOutParameter(index, OracleTypes.CURSOR);
    };

}
