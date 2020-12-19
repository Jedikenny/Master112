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

package it.senato.jedi.util.xml.xml2excel;

import it.senato.jedi.util.xml.XMLFileReader;

import java.io.*;
import java.net.URL;
import org.xml.sax.*;

/**
 * This class can be used to make some test on the production of Excel documents.
 *
 */
public class POIGenerator implements ErrorHandler {

	/**
	 * Creates a POIGenerator object.
	 * 
	 * @param filename is a String object representing the fileName of the XML document.
	 * @throws Exception
	 */
    public POIGenerator(String filename) throws Exception {
        try {
            File ffile = new File(filename);
            URL file = ffile.toURL();

			XMLFileReader reader = new XMLFileReader(file.openStream());

            POIModel model = new POIModel("workbook.xls");

			reader.addXMLPropertyListener(new POIPropertyListener(model));

			reader.readXMLConfiguration(this);

            /*
            if(hasErrors()) {
				SAXException se = getErrors();
				throw new ConfigException(se.getMessage());
            }
            */

            model.close();

        } catch(SAXException se) {
			throw se;
        } catch(IOException ie) {
            throw ie;
        }
    }

    /**
     * Starts the test.
     * 
     * @param argv is the file name of the XML document.
     */
    public static void main(String[] argv) {
        try {
	        new POIGenerator(argv[0]);
        } catch(Exception e) {
			e.printStackTrace();
        }
    }

    public void warning(SAXParseException se) throws SAXException {
        se.printStackTrace();
    }

    public void fatalError(SAXParseException se) throws SAXException {
        se.printStackTrace();
    }

    public void error(SAXParseException se) throws SAXException {
        se.printStackTrace();
    }
}
