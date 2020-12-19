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

import it.senato.jedi.bean.FormatterUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import it.senato.jedi.util.xml.XMLPropertyListener;

import java.text.*;
import java.util.*;

/**
 * This class is a XMLPropertyListener specialization used to read a XML document from which to create an Excel document.
 * <p>
 * The XML document must contain business data organized in a well-defined structure similar to an Excel document.
 *   
 * 
 *
 * @see XMLPropertyListener
 * @see POIModel
 */
public class POIPropertyListener implements XMLPropertyListener {
    private static final String SHEET_ELEMENT = "sheet";
    private static final String ROW_ELEMENT = "row";

    private static final String STRING_CELL_ELEMENT = "string-cell";
    private static final String INTEGER_CELL_ELEMENT = "integer-cell";
    private static final String DOUBLE_CELL_ELEMENT = "double-cell";
    private static final String BOOLEAN_CELL_ELEMENT = "boolean-cell";
    private static final String DATE_CELL_ELEMENT = "date-cell";

    private static final String BORDER_ELEMENT = "border";
    private static final String FONT_ELEMENT = "font";

    private POIModel model;

    private int columnCount;
    private int rowCount;

    /**
     * Creates a POIPropertyListener object.
     * 
     * @param model is a POIModel object
     */
    public POIPropertyListener(POIModel model) {
        this.model = model;
    }

    @Override
    public boolean endElement(String url) throws SAXException {
        if (url.endsWith(SHEET_ELEMENT)) {
            for (int i = 0; i <= columnCount; i++)
                model.autoSizeColumn(i);
        }

        return true;
    }

    @Override
    public boolean startElement(String url, Attributes attr) throws SAXException {
        if (url.endsWith(SHEET_ELEMENT)) {
            String name = "";

            for (int i = 0; i < attr.getLength(); i++) {
                if (attr.getLocalName(i).equals("name")) {
                    name = attr.getValue(i);
                }
            }

            model.createSheet(name);
            return true;
        }

        if (url.endsWith(ROW_ELEMENT)) {

            rowCount++;

            int index = -1;

            for (int i = 0; i < attr.getLength(); i++) {
                if (attr.getLocalName(i).equals("index")) {
                    index = new Integer(attr.getValue(i)).intValue();
                }
            }

            model.createRow(index);
            return true;
        }

        final int typeCell = isCellUrl(url);
        if (typeCell != 0) {


            int index = 0;
            String value = "";
            int colSpan = 1;
            int width = 0;

            for (int i = 0; i < attr.getLength(); i++) {
                if (attr.getLocalName(i).equals("index")) {
                    index = new Integer(attr.getValue(i)).intValue();
                }
                if (attr.getLocalName(i).equals("value")) {
                    value = attr.getValue(i);
                }
                if (attr.getLocalName(i).equals("colSpan")) {
                    colSpan = new Integer(attr.getValue(i)).intValue();
                }
                if (attr.getLocalName(i).equals("width")) {
                    width = new Integer(attr.getValue(i)).intValue();
                }
            }

            if (rowCount == 1 && index + colSpan > columnCount)
                columnCount = index + colSpan;


            if (value == null || value.equals("")) // per evitare che si ottenga una NumberFormatException
                model.createCellAsString(index, "", colSpan, width);
            else
                try {
                    switch (typeCell) {
                        case 1:
                            model.createCellAsString(index, value, colSpan, width);
                            break;
                        case 2:
                            model.createCellAsInteger(index, new Integer(value).intValue(), colSpan, width);
                            break;
                        case 3:
                            try {
                                NumberFormat nf = NumberFormat.getInstance(Locale.ITALIAN);
                                model.createCellAsDouble(index, nf.parse(value).doubleValue(), colSpan, width);
                            } catch (ParseException e) {
                                throw new java.lang.NumberFormatException(e.getMessage());
                            }
                            break;
                        case 4:
                            model.createCellAsBoolean(index, new Boolean(value).booleanValue(), colSpan, width);
                            break;
                        case 5:
                            try {
                                model.createCellAsDate(index, FormatterUtil.parseDate(value), colSpan, width);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                    }
                } catch (ClassCastException e) {
                    model.createCellAsString(index, value, colSpan, width);
                }

            return true;
        }

        if (url.endsWith(BORDER_ELEMENT)) {
            boolean top = false;
            boolean bottom = false;
            boolean left = false;
            boolean right = false;

            for (int i = 0; i < attr.getLength(); i++) {
                if (attr.getLocalName(i).equals("top")) {
                    top = new Boolean(attr.getValue(i)).booleanValue();
                }
                if (attr.getLocalName(i).equals("bottom")) {
                    bottom = new Boolean(attr.getValue(i)).booleanValue();
                }
                if (attr.getLocalName(i).equals("left")) {
                    left = new Boolean(attr.getValue(i)).booleanValue();
                }
                if (attr.getLocalName(i).equals("right")) {
                    right = new Boolean(attr.getValue(i)).booleanValue();
                }
            }

            model.createBorderOfCell(top, bottom, right, left);
            return true;
        }

        if (url.endsWith(FONT_ELEMENT)) {
            String fontname = "Arial";
            int fontsize = 10;
            boolean fontitalic = false;
            boolean fontbold = false;
            String alignment = null;
            short bkColor = 0;
            String formato = null;

            for (int i = 0; i < attr.getLength(); i++) {
                if (attr.getLocalName(i).equals("name")) {
                    fontname = attr.getValue(i);
                }
                if (attr.getLocalName(i).equals("size")) {
                    fontsize = new Integer(attr.getValue(i)).intValue();
                }
                if (attr.getLocalName(i).equals("italic")) {
                    fontitalic = new Boolean(attr.getValue(i)).booleanValue();
                }
                if (attr.getLocalName(i).equals("bold")) {
                    fontbold = new Boolean(attr.getValue(i)).booleanValue();
                }
                if (attr.getLocalName(i).equals("bkColor")) {
                    bkColor = Short.parseShort(attr.getValue(i));
                }
                if (attr.getLocalName(i).equals("alignment")) {
                    alignment = attr.getValue(i);
                }
                if (attr.getLocalName(i).equals("format")) {
                    formato = attr.getValue(i);
                }
            }

            model.createStyleOfCell(fontname, fontsize, fontitalic, fontbold, alignment, bkColor, formato);

            return true;
        }

        return false;
    }

    private int isCellUrl(String url) {
        if (url.endsWith(STRING_CELL_ELEMENT)) {
            return 1;
        }
        if (url.endsWith(INTEGER_CELL_ELEMENT)) {
            return 2;
        }
        if (url.endsWith(DOUBLE_CELL_ELEMENT)) {
            return 3;
        }
        if (url.endsWith(BOOLEAN_CELL_ELEMENT)) {
            return 4;
        }
        if (url.endsWith(DATE_CELL_ELEMENT)) {
            return 5;
        }

        return 0;
    }
}
