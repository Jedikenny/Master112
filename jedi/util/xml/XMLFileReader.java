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

package it.senato.jedi.util.xml;

import java.io.*;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.*;
import java.net.URL;

/**
 * This is a reader of XML documents using SAX parser.
 * <p>
 * The XML content is handled by a XMLPropertyParser object that has some logic to dispatch
 * each read element into the registered XMLPropertyListener objects. 
 *
 * @see XMLPropertyParser
 * @see XMLPropertyListener
 */
public class XMLFileReader {
	private String fileName = null;

    private InputStream input = null;

    private XMLPropertyParser parser = null;

    private boolean validation = true;
	private String schemaURL = null;

	/**
	 * Creates a XMLFileReader object.
	 * 
	 * @param fileName is a String object representing the fileName of the XML document.
	 */
    public XMLFileReader(String fileName) {
		this.fileName = fileName;

        parser = new XMLPropertyParser();
    }

    /**
	 * Creates a XMLFileReader object.
     * 
     * @param input is a InputStream object representing the input stream of the XML document.
     */
    public XMLFileReader(InputStream input) {
		this.input = input;

        parser = new XMLPropertyParser();
    }

    /**
     * Adds a XMLPropertyListener.
     * 
     * @param listener is a XMLPropertyListener object
     */
    public void addXMLPropertyListener(XMLPropertyListener listener) {
		parser.addXMLPropertyListener(listener);
    }

	/**
     * Reads the XML document using SAX parser.
     * 
     * @param eHandler is a ErrorHandler object
     */
    public void readXMLConfiguration(ErrorHandler eHandler) throws SAXException,IOException,FileNotFoundException {
		System.setProperty("org.xml.sax.driver","org.apache.xerces.parsers.SAXParser");

		// Create SAX 2 parser...
		XMLReader xr = XMLReaderFactory.createXMLReader();

        if(validation && schemaURL != null) {
			xr.setFeature("http://xml.org/sax/features/validation",true);
	        xr.setFeature("http://apache.org/xml/features/validation/schema",true);
	        xr.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",schemaURL);
        }
        else
			xr.setFeature("http://xml.org/sax/features/validation",false);

		// Set the ContentHandler...
		xr.setContentHandler(parser);
		xr.setErrorHandler(eHandler);
	
		// Parse the file...
        if(fileName != null)
			xr.parse( new InputSource(new FileReader(fileName)));
		else
			xr.parse( new InputSource(input));
    }

    /**
     * Returns the validation attribute.
     * 
     * @return true if the XML document has to be validated. Otherwise false 
     */
    public boolean isValidation(){ return validation; }

    /**
     * Sets the validation attribute.
     * 
     * @param validation is true if the XML document has to be validated. Otherwise false 
     */
    public void setValidation(boolean validation){ this.validation = validation; }

    /**
     * Sets the schema to be used in the validation.
     * 
     * @param url is a URL object
     */
    public void setSchemaURL(URL url) {
		schemaURL = url.toString();
    }
}
