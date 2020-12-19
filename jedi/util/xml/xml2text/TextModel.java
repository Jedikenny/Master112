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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This class contains some logic to create an text document at fixed positions.
 * <p>
 * At the end of the process the user has to call {@link #close() close} method in order to flush all data into an output stream.
 * 
 */
public class TextModel {
	private String separator;
	
	private ByteArrayOutputStream bao;
	
	// buffer containing the actual row
	private StringBuffer row;
	
	/**
	 * Creates a TextModel object.
	 * 
	 * @param bao is a ByteArrayOutputStream object where to write text document
	 */
	public TextModel(ByteArrayOutputStream bao) {
		this.bao = bao;
	}
	
	/**
	 * Initialize the output buffer.
	 * 
	 * @param separator is a String value
	 */
	public void createText(String separator) {
		this.separator = separator;
	}
	
	/**
	 * Flushes a possible existing actual row and then prepares to fill a new one.  
	 *  
	 */	
	public void createRow() {
		if(row != null) { // prima di una nuova riga scrive quella attuale
			writeRow();
		}
		
		row = new StringBuffer();
	}

	/**
	 * Writes a column on the actual row.
	 * 
	 * @param name is a String object containing the name of the column
	 * @param filler is a String object containing the characters to be used as filler
	 * @param length is the length of the resulting string
	 * @param alignment is a string containing "left" or "right"
	 * @param value is the initial String object
	 */
	public void createColumn(String name,String filler,int length,String alignment,String value) {
		// caso non posizionale
		if(length == 0) {
			if(row.length() != 0)
				row.append(separator);
			row.append(value);
			
			return;
		}
		
		// caso posizionale
		if(value.length() > length) 
			throw new Xml2TextException("Colonna " + name + ": il valore è superiore alla lunghezza consentita di " + length + " caratteri.");
		
		if(alignment.equals("left") == false && alignment.equals("right") == false)
			throw new Xml2TextException("Colonna " + name + ". Valore align non corretto : " + alignment);

		final boolean alignToLeft = alignment.equals("left"); 
		row.append(fillString(filler,length,value,alignToLeft));
	}
	
	/**
	 * Flushes all data of the test document into an output stream.
	 * 
	 */
	public void close() {
		if(row != null) { // scrive l'eventuale ultima riga attuale
			writeRow();
		}		
	}
		
	/**
	 * Writes the actual row on the output stream.
	 *
	 */
	private void writeRow() {		
		try {
			row.append("\n");
			bao.write(row.toString().getBytes());
			bao.flush();
		} catch (IOException e) {
			throw new Xml2TextException(e.getMessage(),e);
		}		
	}
	
	/**
	 * Returns a string filled with some logic.
	 *  
	 * @param filler is a String object containing the characters to be used as filler
	 * @param length is the length of the resulting string
	 * @param value is the initial String object
	 * @param alignToLeft is true if the value must be placed on the left. Otherwise is false
	 * @return is a String object
	 */
	private String fillString(String filler,int length,String value,boolean alignToLeft) {
		final int diff = length - value.length();
		
		StringBuffer sb = new StringBuffer();
        for(int j=0;j<diff;j++)
            sb = sb.append(filler);

        if(alignToLeft)
        	return value + sb.toString();
        else
        	return sb.toString() + value;
         
        /*
         *  The above code works in Java versions 1.3, 1.4, and 1.5.
         *  However, 1.5 provides the Formatter class, and the single
         *  line below can replace the preceeding code.
         *	System.out.printf("%14.2f%n", dbl);		
         */
	}

}
