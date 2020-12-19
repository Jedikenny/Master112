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

import it.senato.jedi.LogChannel;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * This class reads XML data from a database using a JDBC statement.
 * 
 * @see AbstractJDBCReader
 */
public class XMLJDBCSQLQuery extends AbstractJDBCReader {

	/** Creates a XMLJDBCSQLQuery object.
	 * 
	 */
	public XMLJDBCSQLQuery() {
		
	}

	@Override
	public void execute(Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	if(Logger.getLogger(LogChannel.EJB_CHANNEL).isDebugEnabled())
        		Logger.getLogger(LogChannel.EJB_CHANNEL).debug("SQL query: " + document.getXmlSource().getSQLQuery());

        	// Prepare the SQL Query
            ps = con.prepareStatement(document.getXmlSource().getSQLQuery());

	        // Register input parameter values
	        setParameterValues(1, ps);

	        // Execute the statement
		    rs = ps.executeQuery();

            //Generate a standard xml
            xmlResult = JDBCUtil.toString(rs, document.getDescription());

	    } catch (Exception e) {
	    	logger.error(e.getMessage(),e);

	    	handleException(e);
	    } finally {
	    	close(rs, ps, con);
	    }
	}

}
