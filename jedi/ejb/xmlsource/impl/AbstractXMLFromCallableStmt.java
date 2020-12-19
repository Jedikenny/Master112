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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * This abstract class is a base for all the classes that read data from a database using a JDBC CallableStatement.
 * <p>
 * Each subclass must implements {@link #registerOutputParameter(CallableStatement,int) registerOutputParameter} 
 * and {@link #setXMLFromOutParams(CallableStatement) setXMLFromOutParams}  methods.
 * 
 * @see AbstractJDBCReader
 */
public abstract class AbstractXMLFromCallableStmt extends AbstractJDBCReader {

	/**
	 * Creates a AbstractXMLFromCallableStmt object.
	 */
	public AbstractXMLFromCallableStmt() {
	}

	@Override
	public void execute(Connection con) throws SQLException {
        CallableStatement cs = null;

        try {
        	// Compose SQL statement
        	String SQLStatement = getSQLStatement();

        	if(Logger.getLogger(LogChannel.EJB_CHANNEL).isDebugEnabled())
        		Logger.getLogger(LogChannel.EJB_CHANNEL).debug("SQL statement: " + SQLStatement);

        	// Call the database procedure.
        	cs = con.prepareCall(SQLStatement);

	        // Register the output parameter
        	registerOutputParameter(cs,1);

	        // Register input parameter values
	        setParameterValues(2, cs);

	        // Execute the statement
		    cs.execute();

		    // Read the XML output
	        setXMLFromOutParams(cs);

	    } catch (Exception e) {
	    	logger.error(e.getMessage(),e);

	    	handleException(e);
	    } finally {
	    	close(null, cs, con);
	    }
	}

	/**
	 * Registers the output parameter on the SQL statement.
	 *
	 * @param cs is the CallableStatement object
	 * @param index is the index parameter
	 * @throws java.sql.SQLException
	 */
	protected abstract void registerOutputParameter(CallableStatement cs,int index) throws SQLException;

	/**
	 * Sets the input parameters for the statement.
	 * 
	 * @param cs is CallableStatement object
	 * @throws SQLException
	 */
    protected abstract void setXMLFromOutParams(CallableStatement cs) throws SQLException;
    
	/**
	 * Returns the SQL statement. <br>
	 * The statement is based on document parameters.
	 * 
	 * @return is a String object
	 */
	protected String getSQLStatement() {
        String SQLStatement = "{? = call " + document.getXmlSource().getStoredProcedure();
        
        for(int i = 0; i < document.getParameters().size(); i++) {
        	if(i == 0)
        		SQLStatement += "(?";
            
            if(i != 0)
                SQLStatement += ", ?";
            
            if (i == document.getParameters().size() - 1)
                SQLStatement += ")}";
        }

        return SQLStatement;
	}
	
}
