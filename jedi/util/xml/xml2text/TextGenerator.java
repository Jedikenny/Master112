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

package it.senato.jedi.util.xml.xml2text;

import it.senato.jedi.util.xml.XMLFileReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This class can be used to make some test on the production of text documents at fixed positions
 *
 */
public class TextGenerator implements ErrorHandler {

	/**
	 * Creates a TextGenerator object.
	 * 
	 * @param filename is a String object representing the fileName of the XML document.
	 * @throws Exception
	 */
    public TextGenerator(String filename) throws Exception {
        try {
            File ffile = new File(filename);
            URL file = ffile.toURL();

			XMLFileReader reader = new XMLFileReader(file.openStream());

			ByteArrayOutputStream bao = new ByteArrayOutputStream(50000);
            TextModel model = new TextModel(bao);

			reader.addXMLPropertyListener(new TextPropertyListener(model));

			reader.readXMLConfiguration(this);

            model.close();

            System.out.println(bao.toString());
            
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
	        new TextGenerator(argv[0]);
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
