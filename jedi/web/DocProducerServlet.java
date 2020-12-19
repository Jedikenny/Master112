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

package it.senato.jedi.web;

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.DocumentBean;
import it.senato.jedi.bean.FormatTypeBean;
import it.senato.jedi.bean.FormatterUtil;
import it.senato.jedi.ejb.DocProducerDelegate;
import it.senato.jedi.ejb.DocProducerEJB;
import it.senato.jedi.ejb.JEDIConfigDelegate;
import it.senato.jedi.ejb.JEDIConfigEJB;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;
import it.senato.jedi.exception.JEDIInvocationException;
import it.senato.jedi.web.util.DocOutputWriter;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * This class has the job to produce a JEDI document and send it as response of a HTTP request. 
 * <p>
 * In the request must be present the following parameters:<br>
 * <ul>
 * 	<li><strong>id</strong> with the value of the document identifier</li>
 * 	<li>many <strong>valString</strong> (with value formatted as string according to {@link FormatterUtil FormatterUtil} class) as the number of the document 
 * parameters and with the same order</li>
 * </ul>
 * JEDI document is produced by using the format type defined in the document configuration.<br>
 * Because sometimes it's useful to override the format type at runtime, optionally in the request can be specified a <strong>format</strong> parameter containing
 * the identifier of the format type to be used.
 * <p>
 * This class invokes {@link JEDIConfigEJB JEDIConfigEJB} and {@link DocProducerEJB DocProducerEJB} beans to read the document configuration and to produce of JEDI document. 
 * 
 * @see DocProducerDelegate
 * @see JEDIConfigDelegate
 */
public class DocProducerServlet extends HttpServlet {
	private Logger logger;
	
	// Request parameters
	private static final String ID_DOCUMENT_PARAM = "id";
	private static final String ID_FORMATTYE_PARAM = "format";
	private static final String ID_VALUE_PARAM = "valString";
	
	// Session attributes
	private static final String DOCUMENT_ATTRIBUTE = "document";
	private static final String FORMATTYPES_ATTRIBUTE = "formatTypes";

	/**
	 * Creates a DocProducerServlet object.
	 */
	public DocProducerServlet() {
		
	}

	@Override
	public void init() throws ServletException {
		super.init();
		
		logger = Logger.getLogger(LogChannel.WEB_CHANNEL);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	/**
	 * Starts the process of a JEDI document production and send it as response. 
	 * 
	 * @param req is a HttpServletRequest object
	 * @param resp is a HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// read document
			DocumentBean document = readDocument(req);
			
			// read format type (not required)
	        FormatTypeBean formatType = readFormatType(req);
            if(formatType==null)
                formatType = document.getFormatType();
	
	        // reading parameter values	        
	        String values[] = req.getParameterValues(ID_VALUE_PARAM);	        		
	        
	        // produce document
	        DocProducerDelegate delegate = new DocProducerDelegate();
	        byte[] os = delegate.createDocumentByValues(document,values,formatType);
	        
	        // write the result
	        DocOutputWriter.writeDocument(resp,formatType,os);
		} catch(JEDIInvocationException e) {
			logger.error(e.getMessage(),e);
		} catch(JEDIConfigException e) {
			logger.error(e.getMessage(),e);
		} catch(JEDIInfrastructureException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Reads the configuration of the requested JEDI document.<br>
	 * The document identifier must be specified in the request by a parameter called "id".  
	 * 
	 * @param req is a HttpServletRequest object
	 * @return a DocumentBean object
	 * @throws JEDIInvocationException
	 * @throws JEDIInfrastructureException
	 * @throws JEDIConfigException
	 */
    private DocumentBean readDocument(HttpServletRequest req) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
    	long idDocument;
    	try {
    		idDocument = new Long(req.getParameter(ID_DOCUMENT_PARAM)).longValue();
    	} catch(NullPointerException e) {
    		throw new JEDIInvocationException("Parameter " + ID_DOCUMENT_PARAM + " absent or invalid.");
    	} catch(NumberFormatException e) {
    		throw new JEDIInvocationException("Parameter " + ID_DOCUMENT_PARAM + " absent or invalid.");    		
    	}
    	
        if(req.getSession().getAttribute(DOCUMENT_ATTRIBUTE) == null || ((DocumentBean) req.getSession().getAttribute(DOCUMENT_ATTRIBUTE)).getOid() != idDocument) {        	
    		JEDIConfigDelegate delegate = new JEDIConfigDelegate();
    		
            DocumentBean document = delegate.lookupDocument(idDocument);
   			req.getSession().setAttribute(DOCUMENT_ATTRIBUTE, document);
   			
   			return document;
        } else
            return (DocumentBean) req.getSession().getAttribute(DOCUMENT_ATTRIBUTE);
    }

    /**
     * Reads the format type to be used for the JEDI production.
     * 
     * @param req is a HttpServletRequest object
     * @return a FormatTypeBean object
     * @throws JEDIInvocationException
     * @throws JEDIInfrastructureException
     * @throws JEDIConfigException
     */
    private FormatTypeBean readFormatType(HttpServletRequest req) throws JEDIInvocationException, JEDIInfrastructureException, JEDIConfigException {
    	long idFormatType;
    	try {
    		idFormatType = new Long(req.getParameter(ID_FORMATTYE_PARAM)).longValue();
    	} catch(NullPointerException e) {
    		return null;
    	} catch(NumberFormatException e) {
    		return null;    		
    	}
    	
        if(req.getSession().getAttribute(FORMATTYPES_ATTRIBUTE) == null) {        	
    		JEDIConfigDelegate delegate = new JEDIConfigDelegate();
    		
            List<FormatTypeBean> formatTypes = delegate.getFormatTypes();
   			req.getSession().setAttribute(FORMATTYPES_ATTRIBUTE,formatTypes);
        } 
                
        List<FormatTypeBean> formatTypes = (List<FormatTypeBean>) req.getSession().getAttribute(FORMATTYPES_ATTRIBUTE);
        
        // looking for FormatType
        for (FormatTypeBean ft : formatTypes) {
			if(ft.getOid() == idFormatType)
				return ft;
		}
        
        // not found
        throw new JEDIInvocationException("Parameter " + ID_FORMATTYE_PARAM + " invalid.");
    }

}
