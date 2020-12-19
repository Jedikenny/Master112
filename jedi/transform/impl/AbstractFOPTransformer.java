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

package it.senato.jedi.transform.impl;

import it.senato.jedi.transform.AbstractTransformer;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;

/**
 * This abstract class is a base for all the classes that use FOP library.
 * 
 * @see AbstractTransformer
 */
public abstract class AbstractFOPTransformer extends AbstractTransformer {

	/**
	 * Performs a FOP transformation.
	 * 
	 * @param xsl is a String object of the XSL transformation
	 * @param xml is a String object of the XML data
	 * @param mimeType is a MimeConstants string
	 * @return a ByteArrayOutputStream object
	 * @throws Exception
	 */
    protected ByteArrayOutputStream run(String xsl,String xml,String mimeType) throws Exception {
        // EURO symbol in encoding ISO-LATIN-15 with the relative entity
        xml = xml.replaceAll((char)164 + "","&#8364;");
    	
    	// XML data source
        Source src = getSource(xml);
        Source xslSrc = new StreamSource(new java.io.StringReader(xsl));

        // Setup output
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // configure fopFactory as desired
        logger.debug("Create FopFactory");
        FopFactory fopFactory = FOPFactoryProvider.getInstance();

        // configure foUserAgent as desired
        logger.debug("Create FOUserAgent");
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        try {
            // Construct fop with desired output format
            logger.debug("Create FOP");
            Fop fop = fopFactory.newFop(mimeType,foUserAgent,out);

            // Setup XSLT
            logger.debug("Create trasformer");
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xslSrc);

            // Set the value of a <param> in the stylesheet
            transformer.setParameter("versionParam", "1.1");

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            logger.debug("Begin FOP transformation");
            transformer.transform(src, res);
            logger.debug("End FOP transformation");
        } finally {
            out.close();
        }
 
        return out;        
    }    
}