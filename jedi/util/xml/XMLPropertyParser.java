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

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a DefaultHandler specialization.
 * <p>
 * It's used by a XMLFileReader object while reading a XML document with SAX parser.
 * <p>
 * This class registers the XMLPropertyListener objects to be notified when the parser reads a XML element
 * and has some logic to dispatch to the right XMLPropertyListener object. 
 * 
 * @see XMLFileReader
 * @see XMLPropertyListener
 */
public class XMLPropertyParser extends DefaultHandler {
    StringBuffer buffer = new StringBuffer("");
    int index = 0;

    private List<XMLPropertyListener> listeners = new ArrayList<XMLPropertyListener>();

    /**
     * Creates a XMLPropertyParser object.
     */
	public XMLPropertyParser() {}

	/**
     * Adds a XMLPropertyListener.
     * 
     * @param listener is a XMLPropertyListener object
	 */
    public void addXMLPropertyListener(XMLPropertyListener listener) {
    	listeners.add(listener);
    }

    public void startDocument() throws SAXException {

    }

    public void endDocument() throws SAXException {

    }

    public void startElement( String namespaceURI,String localName,String qName,Attributes attr) throws SAXException {
    	index = buffer.length();
        if(index != 0)
            buffer.append(".");
        
        buffer.append(localName);

        for(XMLPropertyListener xmlListener: listeners) {
			if(xmlListener.startElement(buffer.toString(),attr) == true)
                return;
        }

   }

   public void endElement( String namespaceURI,String localName,String qName ) throws SAXException {

       for(XMLPropertyListener xmlListener: listeners) {
           if(xmlListener.endElement(buffer.toString()) == true)
               continue;
       }

     buffer.delete(index,buffer.length());
     
     index = buffer.toString().lastIndexOf(".");
     if(index == -1)
        index = 0;

   }

   public void characters( char[] ch, int start, int length ) throws SAXException {

   }

}
