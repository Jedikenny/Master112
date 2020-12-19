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

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;

/**
 * This transformer creates an XML document as result of XSL transformation.
 * 
 * @see AbstractTransformer
 */
public class XSLTransformer extends AbstractTransformer {

	/*
	 * Creates a XSLTransformer object.
	 */
    public XSLTransformer() {

    }

    @Override
    public ByteArrayOutputStream run(String xsl,String xml) throws Exception {

        // Transformer XSL
        Transformer transformer = getTrasformer(xsl);

        // makes transformation
        ByteArrayOutputStream os = new ByteArrayOutputStream(10000);
        Result result = new StreamResult(os);

        transformer.transform(getSource(xml),result);

        // ritorna l'XML del prodotto trasformato
        out.write(os.toByteArray());

        return out;
    }
}