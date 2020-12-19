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

import it.senato.jedi.util.xml.XMLPropertyListener;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This class is a XMLPropertyListener specialization used to read a XML document from which to create an text of fixed positions.
 * <p>
 * The XML document must contain business data organized in a well-defined structure.
 *   
 * 
 *
 * @see XMLPropertyListener
 * @see TextModel
 */
public class TextPropertyListener implements XMLPropertyListener {
	// elementi letti
	private static final String TEXT_FILE_ELEMENT = "text-file";
	private static final String ROW_ELEMENT = "row";
	private static final String COLUMN_ELEMENT = "column";

	// il modello che si occupa di creare il buffer di output
	private TextModel model;
	
	/**
	 * Creates a TextPropertyListener object.
	 * 
	 * @param model is a TextModel object
	 */
	public TextPropertyListener(TextModel model) {
		this.model = model;
	}
	
	@Override
	public boolean startElement(String url, Attributes attr) throws SAXException {
		if(url.endsWith(TEXT_FILE_ELEMENT)) {			
			String separator = ""; // per default non esiste

	        for(int i=0;i<attr.getLength();i++) {
	            if(attr.getLocalName(i).equals("separator"))
	            	separator = attr.getValue(i);
	        }
	        
			model.createText(separator);
			return true;
		}
		
		if(url.endsWith(ROW_ELEMENT)) {
          model.createRow();
          return true;
       }
		
		if(url.endsWith(COLUMN_ELEMENT)) {			
			String name = "";
			String filler = " "; // per default è lo spazio
			int length = 0; // per default nessuna length
			String alignment = "left"; // per default a sx 
			String value = "";

	        for(int i=0;i<attr.getLength();i++) {
	            if(attr.getLocalName(i).equals("name"))
	            	name = attr.getValue(i);
	            if(attr.getLocalName(i).equals("filler"))
	            	filler = attr.getValue(i);
	            if(attr.getLocalName(i).equals("align"))
	            	alignment = attr.getValue(i);
	            if(attr.getLocalName(i).equals("value"))
	            	value = attr.getValue(i);
	            if(attr.getLocalName(i).equals("length"))
	            	try {
	            		length = Integer.parseInt(attr.getValue(i));
	            	} catch(NumberFormatException e) {
	            		throw new Xml2TextException(e.getMessage(),e);
	            	}
	        }
	        
			model.createColumn(name,filler,length,alignment,value);
			return true;
		}
		
		return false;
	}

    @Override
    public boolean endElement(String url) throws SAXException {
        return true;
    }

}
