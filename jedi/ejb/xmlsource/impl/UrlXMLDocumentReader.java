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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.ejb.xmlsource.XMLSourceReader;
import it.senato.jedi.exception.JEDIInvocationException;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * This class reads XML data as result of an URL resource.
 * <p>
 * Note that the URL address has to be configured into the document's XML source.
 * 
 * @see XMLSourceReader
 */
public class UrlXMLDocumentReader implements XMLSourceReader {
	private Logger logger;
	
	/**
	 * Creates a UrlXMLDocumentReader object.
	 */
	public UrlXMLDocumentReader() {
		logger = Logger.getLogger(LogChannel.EJB_CHANNEL);
	}
	
	@Override
	public String getXML(EntityManager em, DocumentBean document) throws JEDIInvocationException {		
		try {
			logger.info("Begin XML reading for document [" + document.getOid() + "] " + document.getDescription());

			// read oid document to invoke
			StringBuffer sb = new StringBuffer(document.getXmlSource().getUrl());
			
			// input parameters
			if(document.getParameters().size() > 0)
				sb.append("?");
			
			for(int k=0;k<document.getParameters().size();k++) {
				String name = document.getParameters().get(k).getDescription();
				String value = document.getParameters().get(k).getValueAsString();
				
				if(k>0)
					sb.append("&");
				sb.append(name + "=" + value);
			}
			
			logger.info("Calling URL: " + sb.toString());
			URL url = new URL(sb.toString());
			
			// create a urlconnection object
		    URLConnection urlConnection = url.openConnection();
		    
		    // wrap the urlconnection in a bufferedreader
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

		    StringBuffer content = new StringBuffer("");
		    String line;
		    // read from the urlconnection via the bufferedreader
		    while ((line = bufferedReader.readLine()) != null) {
		        content.append(line + "\n");
		    }
		    bufferedReader.close();
			
			logger.info("End XML reading for document [" + document.getOid() + "] " + document.getDescription());
			
			return content.toString();
		} catch (Exception e) {
			throw new JEDIInvocationException(e.getMessage());
		}
	}

}
