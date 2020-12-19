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

package it.senato.jedi.web.util;

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.FormatTypeBean;
import it.senato.jedi.exception.JEDIInvocationException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * This is an utility class to write a JEDI document into a HTTP response. 
 *
 */
public class DocOutputWriter {

	/**
	 * Writes a JEDI document into a HTTP response accordinf to the specified format type. 
	 * 
	 * @param response is a HttpServletResponse object
	 * @param formatType is a FormatTypeBean object
	 * @param os is an array of byte of the JEDI document 
	 * @throws JEDIInvocationException
	 */
    public static void writeDocument(HttpServletResponse response,FormatTypeBean formatType,byte[] os) throws JEDIInvocationException {
    	Logger logger = Logger.getLogger(LogChannel.WEB_CHANNEL);
    	
    	response.reset();

        response.setContentType(formatType.getMimeType());
        response.setHeader("Content-Length", "" + os.length);

        if(formatType.getOid() == 4) { // text
        	response.setHeader("Content-Disposition", "attachment; filename=whatever.txt");
            response.setContentType("application/unknown");
        }

        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();//this is for binary data
            out.write(os);
            out.flush();
        } catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInvocationException(e.getMessage());
        } finally {
            try {
            	if(out != null) {
            		out.close();
                }
            } catch(Exception e) {
            	logger.warn(e.getMessage(),e);
            }
        }
    }
	
}
