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

package it.senato.jedi.transform;

import org.apache.log4j.Logger;

import it.senato.jedi.LogChannel;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * This abstract class is a base for all the classes that implement XMLTransformer.
 *
 * @see XMLTransformer
 */
public abstract class AbstractTransformer implements XMLTransformer {
	/**
	 * Defines a logger.
	 */
	protected Logger logger;
	
	/**
	 * The output stream for the result of the transformation.
	 */
    protected ByteArrayOutputStream out;

    /**
     * Creates a AbstractTransformer object.
     */
    public AbstractTransformer() {
    	logger = Logger.getLogger(LogChannel.GENERIC_CHANNEL);
    	
        out = new ByteArrayOutputStream();
    }

    /**
     * Converts a XML string into a Source object.
     * 
     * @param xml is a String object
     * @return a Source object
     */
    protected Source getSource(String xml) {
        // creates Source object from XML data string
        StringReader sread = new StringReader(xml);
        return new StreamSource(sread);
    }

    /**
     * Converts a XSL string into a Transformer object.
     * 
     * @param xsl is a String object of the XSL transformation
     * @return a Transformer object
     * @throws TransformerConfigurationException
     * @throws UnsupportedEncodingException
     */
    protected Transformer getTrasformer(String xsl) throws TransformerConfigurationException, UnsupportedEncodingException {
        // creates XSL transformer
        TransformerFactory factory = TransformerFactory.newInstance();

        if(logger.isDebugEnabled())
            logger.debug(xsl);

        return factory.newTransformer(new StreamSource(new ByteArrayInputStream(xsl.getBytes("ISO-8859-15"))));
    }
}
