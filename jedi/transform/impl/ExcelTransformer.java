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
import it.senato.jedi.util.xml.XMLFileReader;
import it.senato.jedi.util.xml.xml2excel.POIModel;
import it.senato.jedi.util.xml.xml2excel.POIPropertyListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This transformer creates an Excel document.
 * <p>
 * Given a XML document containing business data, it applies a XSL transformation to produce as result an intermediate XML document.
 * <p>
 * Afterwards the resulting XML document is read with SAX parser and its content handled by a {@link POIPropertyListener POIPropertyListener} object
 * that has the logic to create the Excel document invoking the methods of a {@link POIModel POIModel} object based on POI library.
 * 
 * @see AbstractTransformer
 * @see POIPropertyListener
 * @see POIModel
 */
public class ExcelTransformer extends AbstractTransformer {

	/**
	 * Creates a ExcelTransformer object.
	 */
    public ExcelTransformer() {
    }

    @Override
    public ByteArrayOutputStream run(String xsl,String xml) throws Exception {

        // Transformer XSL
        Transformer transformer = getTrasformer(xsl);

        // makes transformation
        ByteArrayOutputStream os = new ByteArrayOutputStream(10000);
        Result result = new StreamResult(os);

        transformer.transform(getSource(xml),result);

        //ora in os ho l'XML in formato adatto a POI
        InputStream is = new ByteArrayInputStream(os.toByteArray());

        XMLFileReader reader = new XMLFileReader(is);

        POIModel model = new POIModel(out);

        reader.addXMLPropertyListener(new POIPropertyListener(model));

        reader.readXMLConfiguration(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                logger.warn(exception.getMessage(), exception);
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                logger.error(exception.getMessage(), exception);
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                logger.fatal(exception.getMessage(), exception);
            }
        });

        model.close();

        return out;
    }
}