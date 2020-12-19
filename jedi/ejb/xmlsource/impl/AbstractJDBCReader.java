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
import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.bean.ParameterBean;
import it.senato.jedi.ejb.xmlsource.XMLSourceReader;
import it.senato.jedi.exception.InvalidTypeException;
import it.senato.jedi.exception.JEDIInvocationException;
import it.senato.jedi.exception.JEDIRuntimeException;
import it.senato.jedi.exception.NoDataFoundException;

import java.sql.*;

import javax.persistence.EntityManager;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 * This abstract class is a base for all the classes that read XML data from a database using a JDBC statement.
 * <p>
 * Each subclass must implements at least {@link #execute(Connection) execute} method of Work interface.
 * 
 * @see XMLSourceReader
 */
public abstract class AbstractJDBCReader implements XMLSourceReader, Work {
	/**
	 * XML result as string.
	 */
	protected String xmlResult = null;
	
	/**
	 * The document for which to read XML data.
	 */
	protected DocumentBean document;
	
	/**
	 * Defines a logger.
	 */
	protected Logger logger;

	/**
	 * Creates a AbstractJDBCReader object.
	 */
	public AbstractJDBCReader() {
		logger = Logger.getLogger(LogChannel.EJB_CHANNEL);
	}
	
	@Override
	public String getXML(EntityManager em,DocumentBean document) throws JEDIInvocationException {
		this.document = document;
			
		try {
	        logger.info("Begin XML reading for document [" + document.getOid() + "] " + document.getDescription());
	
	        Session session = em.unwrap(Session.class);
	        session.doWork(this);
	
	        logger.info("End XML reading for document [" + document.getOid() + "] " + document.getDescription());
	
	        return xmlResult;
		} catch(NoDataFoundException e) {
			throw new JEDIInvocationException(e.getMessage());
		} catch(JEDIRuntimeException e) {
			if(e.getCause() == null)
				throw new JEDIInvocationException(e.getMessage());
			else
				throw new JEDIInvocationException(e.getCause().getMessage());
		}
	}
	

	/**
	 * Method to handling an Exception.
	 * @param e
	 */
	protected void handleException(Exception e) {
       	throw new JEDIRuntimeException(e);
	}
	
	/**
	 * Sets input parameter values on the SQL statement.
	 * 
	 * @param cs is the CallableStatement object
	 * @throws SQLException
	 * @throws InvalidTypeException
	 */
	protected void setParameterValues(int startIndex, PreparedStatement cs) throws SQLException, InvalidTypeException {

		for(int i=0;i<document.getParameters().size();i++) {
	        ParameterBean p = (ParameterBean) document.getParameters().get(i);
	        
	        switch(p.getParameterType()) {
	            case Integer:
	                if(p.getValue() == null)
	                    cs.setNull(startIndex + i, OracleTypes.INTEGER);
	                else
	                    cs.setInt(startIndex + i, ((Integer) p.getValue()).intValue());
	                break;
	            case String:
	                if(p.getValue() == null)
	                    cs.setNull(startIndex + i, OracleTypes.VARCHAR);
	                else
	                    cs.setString(startIndex + i, p.getValue().toString());
	                break;
	            case Date:
	                if(p.getValue() == null)
	                    cs.setNull(startIndex + i, OracleTypes.DATE);
	                else
	                    cs.setDate(startIndex + i, (Date) p.getValue());
	                break;
	            case Double:
	                if(p.getValue() == null)
	                    cs.setNull(startIndex + i, OracleTypes.DOUBLE);
	                else
	                    cs.setDouble(startIndex + i, ((Double) p.getValue()).doubleValue());
	                break;
	            case Boolean:
	                if(p.getValue() == null)
	                    cs.setNull(startIndex + i, OracleTypes.BOOLEAN);
	                else
	                    cs.setBoolean(startIndex + i, ((Boolean) p.getValue()).booleanValue());
	                break;
	            case Long:
	                if(p.getValue() == null)
	                    cs.setNull(startIndex + i, OracleTypes.NUMBER);
	                else
	                    cs.setLong(startIndex + i, ((Long) p.getValue()).longValue());
	                break;
	            default:
	                throw new InvalidTypeException("Unknown parameter type - Index: " + i);	
	        }

	    }		
	}
	
	/**
	 * Close all the opened JDBS objects.
	 * 
	 * @param rs is a ResultSet object 
	 * @param ps is a PreparedStatement object
	 * @param con is a Connection object
	 */
	protected void close(ResultSet rs, PreparedStatement ps, Connection con) {
        if(ps != null)
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
            }

     	if(rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}

    	if(con != null)
			try {
				con.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
	}
}
