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

import java.io.ByteArrayOutputStream;

import org.apache.fop.apps.MimeConstants;

/**
 * This transformer creates a PDF document.
 *
 * @see AbstractFOPTransformer
 */
public class PDFTransformer extends AbstractFOPTransformer {

	/**
	 * Creates a PDFTransformer object.
	 */
    public PDFTransformer() {

    }

    @Override
    public ByteArrayOutputStream run(String xsl,String xml) throws Exception {
    	return super.run(xsl,xml,MimeConstants.MIME_PDF);
    }

}
