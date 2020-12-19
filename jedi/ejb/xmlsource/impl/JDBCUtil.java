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

package it.senato.jedi.ejb.xmlsource.impl;

import it.senato.jedi.bean.FormatterUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This is an utility class to perform some common JDBC operations.
 *
 */
public class JDBCUtil {

	/**
	 * Converts a ResultSet into an XML data
	 * @param rs is a ResultSet object
	 * @param title is the title of the XML structure
	 * @return a String of XML data 
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
    public static String toString(ResultSet rs, String title) throws SQLException, ParserConfigurationException, IOException {
        return docToString(toDocument(rs, title));
    }

	/**
	 * Converts a ResultSet into a DOM document
	 * @param rs is a ResultSet object
	 * @param title is the title of the XML structure
	 * @return a DOM document
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
    public static Document toDocument(ResultSet rs, String title) throws ParserConfigurationException, SQLException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element results = doc.createElement("Results");
        results.setAttribute("title", title);
        doc.appendChild(results);

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        List<Column> columns = new ArrayList<Column>();

        for (int i = 1; i <= colCount; i++) {
            Column col = new Column();
            col.name = rsmd.getColumnName(i);
            col.label = rsmd.getColumnLabel(i);
            col.type = rsmd.getColumnTypeName(i);
            col.size = rsmd.getColumnDisplaySize(i);
            col.precision = rsmd.getPrecision(i);
            col.scale = rsmd.getScale(i);
            col.className = rsmd.getColumnClassName(i);
            columns.add(col);
        }

        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            Element row = doc.createElement("Row");
            results.appendChild(row);

            int columnIndex = 0;

            for (Column col : columns) {
                columnIndex++;
                String formattedValue = col.getFormattedValue(rs.getObject(columnIndex));

                Element node = doc.createElement(col.name);
                if (rowCount==1){
                    node.setAttribute("type",col.getCustomType()+"");
                    node.setAttribute("precision",col.precision+"");
                    node.setAttribute("scale",col.scale+"");
                }
                node.appendChild(doc.createTextNode(formattedValue));
                row.appendChild(node);
            }
        }
        return doc;
    }

    static class Column {
        String name;
        String type;
        String label;
        String className;
        int precision;
        int scale;
        int size;
        CustomTypes customType;

        CustomTypes getCustomType(){
            if (customType != null)
                return customType;

            customType = CustomTypes.String;

            if (className.equals("java.math.BigDecimal"))
                if (scale==0)
                    customType = CustomTypes.Integer;
                else
                    customType = CustomTypes.Double;

            if (className.equals("java.sql.Timestamp"))
                if (name.endsWith("_TIME"))
                    customType = CustomTypes.DateTime;
                else
                    customType = CustomTypes.Date;

            return customType;
        }

        String getFormattedValue(Object value){
            if (value == null)
                return "";

            String fv = "";

            switch (getCustomType()) {
                case String:
                    fv = value.toString();
                    break;
                case Integer:
                    fv = value.toString();
                    break;
                case Double:
                    Double dbl = ((BigDecimal) value).doubleValue();
                    fv = FormatterUtil.fmtDouble(dbl);
                    break;
                case Date:
                    Date dt = new Date(((java.sql.Timestamp) value).getTime());
                    fv = FormatterUtil.fmtDate(dt);
                    break;
                case DateTime:
                    Date dtm = new Date(((java.sql.Timestamp) value).getTime());
                    fv = FormatterUtil.fmtDateTime(dtm);
                    break;
            }

            return fv;
        }
    }


    enum CustomTypes {
        Date, DateTime, String, Integer, Double
    }

    /**
     * Converts a DOM document into an XML data.
     * 
     * @param doc is a Document DOM object
     * @return a String of XML data
     * @throws IOException
     */
    public static String docToString(Document doc) throws IOException {
        StringWriter writer = new StringWriter(1000000);
        OutputFormat format = new OutputFormat(doc);
        format.setOmitXMLDeclaration(true);
        format.setIndenting(true);
        format.setLineWidth(20000);
        XMLSerializer serializer = new XMLSerializer(writer, format);
        serializer.asDOMSerializer();
        serializer.serialize(doc);
        return writer.toString();
    }

}
