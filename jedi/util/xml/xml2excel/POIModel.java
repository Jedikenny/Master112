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

import org.apache.poi.hssf.usermodel.*;

import it.senato.jedi.bean.FormatterUtil;

import java.io.*;

import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.*;

/**
 * This class contains some logic to create an Excel document using POI library.
 * <p>
 * It provides utility methods to write some Excel properties. 
 * <p>
 * At the end of the process the user has to call {@link #close() close} method in order to flush all data into a file or an output stream.
 * 
 */
public class POIModel {
	private String fileName = null;

	private HSSFWorkbook wb = new HSSFWorkbook();
	private DataFormat format = wb.createDataFormat();

	private HSSFSheet sheet = null;

	private HSSFRow row = null;

	private int rowIndex = -1;

	private OutputStream out = null;

	private boolean defaultStyle = true;

	private Hashtable<String, HSSFCellStyle> htStili = new Hashtable<String, HSSFCellStyle>();
	private Hashtable<Integer, String> htChiaviRigaColonna = new Hashtable<Integer, String>();

	/**
	 * Creates a POIModel object.
	 * 
	 * @param out is a OutputStream object where to write Excel document
	 */
	public POIModel(OutputStream out) {
		this(out,null);
	}

	/**
	 * Creates a POIModel object.
	 * 
	 * @param fileName is a String object representing the file name where to write Excel document
	 */
	public POIModel(String fileName) {
		this(null,fileName);
	}

	protected POIModel(OutputStream out,String fileName) {
		this.out = out;
		this.fileName = fileName;

		// init operations...
	}

	public void createSheet(String name) {
		sheet = wb.createSheet(name);
		rowIndex = -1;
	}

	public void createRow(int index) {
		if (index >= 0) {
			row = sheet.createRow(index);
			rowIndex = index;
		} else {
			row = sheet.createRow(++rowIndex);
		}
	}

	public void setRowHeight(short height) {
		if(row != null)
			row.setHeight(height);
	}

	public void autoSizeColumn(int column) {
		if(sheet != null)
			sheet.autoSizeColumn(column);
	}

	public void createCellAsString(int index, String value, int colSpan, int width) {
		if (colSpan > 1) {
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) index, (short) (index + colSpan - 1)));
		}
		if (width > 0) {
			sheet.setColumnWidth(index, width);
		}

		createCellAsString(index, value);
	}

	public void createCellAsInteger(int index, int value, int colSpan, int width) {
		if (colSpan > 1) {
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) index, (short) (index + colSpan - 1)));
		}
		if (width > 0) {
			sheet.setColumnWidth(index, width);
		}
		createCellAsInteger(index, value);
	}

	public void createCellAsDouble(int index, double value, int colSpan, int width) {
		if (colSpan > 1) {
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) index, (short) (index + colSpan - 1)));
		}
		if (width > 0) {
			sheet.setColumnWidth(index, width);
		}
		createCellAsDouble(index, value);

	}

	public void createCellAsBoolean(int index, boolean value, int colSpan, int width) {
		if (colSpan > 1) {
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) index, (short) (index + colSpan - 1)));
		}
		if (width > 0) {
			sheet.setColumnWidth(index, width);
		}
		createCellAsBoolean(index, value);
	}

	public void createCellAsDate(int index, Date value, int colSpan, int width) {
		if (colSpan > 1) {
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) index, (short) (index + colSpan - 1)));
		}
		if (width > 0) {
			sheet.setColumnWidth(index, width);
		}
		createCellAsDate(index, value);
	}

	public void createCellAsString(int index, String value) {

		HSSFCell cell = row.createCell(index);
		cell.setCellValue(value);

		defaultStyle = true;
	}

	public void createCellAsInteger(int index, int value) {
		HSSFCell cell = row.createCell(index, HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);

		defaultStyle = true;
	}

	public void createCellAsDouble(int index, double value) {
		HSSFCell cell = row.createCell(index, HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);

		defaultStyle = true;
	}

	public void createCellAsBoolean(int index, boolean value) {
		HSSFCell cell = row.createCell(index);
		cell.setCellValue(value);

		defaultStyle = true;
	}

	public void createCellAsDate(int index, Date value) {
		HSSFCell cell = row.createCell(index);
		cell.setCellValue(FormatterUtil.fmtDate(value));

		defaultStyle = true;
	}

	// Crea e aggiunge uno stile alla cache degli stili. styleName deve essere univoco !
	public void createStyle(String styleName) {
		// parte dal default
		HSSFCellStyle style = wb.createCellStyle();


		htStili.put(styleName, style);
	}

	public void setFontOnStyle(String styleName,String fontname, int fontsize, boolean fontitalic, boolean fontbold) {
		// recupera lo stile da applicare
		HSSFCellStyle style = htStili.get(styleName);
		if(style == null)
			return;

		// Create a new font and alter it.
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) fontsize);
		font.setFontName(fontname);
		font.setItalic(fontitalic);
		if(fontbold)
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// Fonts are set into a style so create a new one to use.
		style.setFont(font);
	}

	public void setPropertiesOnStyle(String styleName,String alignment,String vAlignment,short bkColor,String formato) {
		// recupera lo stile da applicare
		HSSFCellStyle style = htStili.get(styleName);
		if(style == null)
			return;

		if (alignment != null && alignment.equals("left")) {
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		}
		if (alignment != null && alignment.equals("right")) {
			style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		}
		if (alignment != null && alignment.equals("center")) {
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		}

		if (vAlignment != null && alignment.equals("top")) {
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		}
		if (vAlignment != null && alignment.equals("center")) {
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		}
		if (vAlignment != null && alignment.equals("bottom")) {
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
		}

		if (bkColor != 0) {
			style.setFillBackgroundColor(bkColor);
			style.setFillForegroundColor(bkColor);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}

		if (formato!=null)
			style.setDataFormat(format.getFormat(formato));
	}

	public void setBorderOnStyle(String styleName,boolean top, boolean bottom, boolean right, boolean left) {
		// recupera lo stile da applicare
		HSSFCellStyle style = htStili.get(styleName);
		if(style == null)
			return;

		if (bottom) {
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBottomBorderColor(HSSFColor.BLACK.index);
		}

		if (left) {
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setLeftBorderColor(HSSFColor.BLACK.index);
		}

		if (right) {
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setRightBorderColor(HSSFColor.BLACK.index);
		}

		if (left) {
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setTopBorderColor(HSSFColor.BLACK.index);
		}
	}

	public void setStyleOnCell(String styleName) {
		// recupera lo stile da applicare
		HSSFCellStyle style = htStili.get(styleName);
		if(style == null)
			return;

		// recupera l'ultima cella inserita
		final int index = row.getLastCellNum() - 1;

		HSSFCell cell = row.getCell(index);

		// applica lo stile
		cell.setCellStyle(style);
	}

	public void createStyleOfCell(String fontname, int fontsize, boolean fontitalic, boolean fontbold, String allignment, short bkColor, String formato) {
		final int index = row.getLastCellNum() - 1;

		HSSFCell cell = row.getCell(index);

		HSSFCellStyle style = cell.getCellStyle();

		String chiaveStile = cell.getCellType() + "-" + fontname + "-" + fontsize + "-" + fontitalic + "-" + fontbold + "-" + allignment + "-" + bkColor + "-" + formato;

		if (htStili.containsKey(chiaveStile)) {
			style = htStili.get(chiaveStile);
		} else {

			if (defaultStyle == true) {
				style = wb.createCellStyle();
			}
			defaultStyle = false;

			// Create a new font and alter it.
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) fontsize);
			font.setFontName(fontname);
			font.setItalic(fontitalic);
			if (fontbold)
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			if (bkColor != 0) {
				style.setFillBackgroundColor(bkColor);
				style.setFillForegroundColor(bkColor);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			}

			// Fonts are set into a style so create a new one to use.
			style.setFont(font);

			if (allignment != null && allignment.equals("left")) {
				style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			}
			if (allignment != null && allignment.equals("right")) {
				style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			}
			if (allignment != null && allignment.equals("center")) {
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			}

			if (formato!=null)
				style.setDataFormat(format.getFormat(formato));

			htStili.put(chiaveStile, style);
		}

		cell.setCellStyle(style);

		htChiaviRigaColonna.put(row.getRowNum()*1000+index, chiaveStile);
	}

	public void createStyleOfCell(String fontname, int fontsize, boolean fontitalic) {
		final int index = row.getLastCellNum() - 1;

		HSSFCell cell = row.getCell(index);

		HSSFCellStyle style = cell.getCellStyle();

		String chiaveStile = cell.getCellType() + "-" + fontname + "-" + fontsize + "-" + fontitalic + "-" + "false" + "-" + "null" + "-" + "null";

		if (htStili.containsKey(chiaveStile)) {
			style = htStili.get(chiaveStile);
		} else {

			if (defaultStyle == true) {
				style = wb.createCellStyle();
			}
			defaultStyle = false;

			// Create a new font and alter it.
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) fontsize);
			font.setFontName(fontname);
			font.setItalic(fontitalic);

			// Fonts are set into a style so create a new one to use.
			style.setFont(font);

			htStili.put(chiaveStile, style);

		}

		cell.setCellStyle(style);
		htChiaviRigaColonna.put(row.getRowNum()*1000+index, chiaveStile);
	}

	public void createBorderOfCell(boolean top, boolean bottom, boolean right, boolean left) {
		final int index = row.getLastCellNum() - 1;

		HSSFCell cell = row.getCell(index);

		String chiaveStile = htChiaviRigaColonna.get(row.getRowNum()*1000+index);

		chiaveStile = chiaveStile + "-" + top + "-" + bottom + "-" + right + "-" + left;

		HSSFCellStyle style = null;

		if (htStili.containsKey(chiaveStile)) {
			style = htStili.get(chiaveStile);
		} else {

			style = wb.createCellStyle();
			HSSFCellStyle fontStyle = cell.getCellStyle();
			style.cloneStyleFrom(fontStyle);

			defaultStyle = false;

			if (bottom) {
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBottomBorderColor(HSSFColor.BLACK.index);
			}

			if (left) {
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setLeftBorderColor(HSSFColor.BLACK.index);
			}

			if (right) {
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setRightBorderColor(HSSFColor.BLACK.index);
			}

			if (left) {
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setTopBorderColor(HSSFColor.BLACK.index);
			}
			htStili.put(chiaveStile, style);
		}
		cell.setCellStyle(style);
	}

	/**
	 * Flushes all data of the Excel document into a file or an output stream.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void close() throws FileNotFoundException, IOException {
		if (fileName != null) {
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.close();
		}
		if (out != null) {
			// Write the output to an OutputStrem
			wb.write(out);
		}
	}

}
