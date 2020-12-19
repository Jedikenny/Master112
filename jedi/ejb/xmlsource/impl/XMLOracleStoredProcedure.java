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

import it.senato.jedi.exception.JEDIRuntimeException;
import it.senato.jedi.exception.NoDataFoundException;

import java.sql.CallableStatement;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;
import oracle.xdb.XMLType;

/**
 * This class reads data from a database using an Oracle JDBC CallableStatement based on a stored procedure..
 * 
 * @see AbstractXMLStoredProcedure
 */
public class XMLOracleStoredProcedure extends AbstractXMLStoredProcedure {
	
	/**
	 * Creates a XMLOracleStoredProcedure object.
	 */
	public XMLOracleStoredProcedure() {
		
	}
	
	@Override
	protected void registerOutputParameter(CallableStatement cs,int index) throws SQLException {
        // Register the output as OracleTypes.OPAQUE that corresponds to XMLTYPE.
        cs.registerOutParameter(index,OracleTypes.OPAQUE, "SYS.XMLTYPE");
	}

	@Override
	protected String getXMLObject(CallableStatement cs,int index) throws SQLException {
        // Get the XMLTYPE value returned from database function.
	    XMLType xml = XMLType.createXML((oracle.sql.OPAQUE) cs.getObject(index));

        return xml.getStringVal();
	}

	@Override
	protected void handleException(Exception e) {
        if ((e instanceof SQLException) && (e.getMessage().indexOf("ORA-20200") != -1)) {
            throw new NoDataFoundException(e.getMessage());
        } else {
        	throw new JEDIRuntimeException(e);
        }
	}
}
