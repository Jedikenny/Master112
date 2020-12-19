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

package it.senato.jedi.bean;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * This is an utility class to perform some common formatting string operations.  
 *
 */
public class FormatterUtil {
	public final static String LONG_DEFAULT_FMT = "###0";
	public final static String INTEGER_DEFAULT_FMT = "###0";
	public final static String DATE_DEFAULT_FMT = "dd/MM/yyyy";
    public final static String DATETIME_DEFAULT_FMT = "dd/MM/yyyy H:m:s";
	public final static String EURO_DEFAULT_FMT = "#,##0.00";

	public static int strToInt(String value) {
		return Integer.parseInt(value.replaceAll("\\.", ""));
	}

	public static String fmtInt(Object value) {
		return fmtInt(value, INTEGER_DEFAULT_FMT);
	}

	public static String fmtInt(Object value, String fmt) {
		DecimalFormat fmter = (DecimalFormat) NumberFormat.getInstance();
		fmter.applyPattern(fmt);
		return fmter.format(value);
	}

	public static String fmtDate(Object value) {
		return fmtDate(value, DATE_DEFAULT_FMT);
	}

	public static String fmtDate(Object value, String fmt) {
		if (value == null)
			return "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(fmt);
		return dateFormat.format(value);
	}

    public static String fmtDateTime(Object value) {
        return fmtDate(value, DATETIME_DEFAULT_FMT);
    }

	public static String fmtDouble(Object value) {
		return fmtDouble(value, EURO_DEFAULT_FMT);
	}

	public static String fmtDouble(Object value, String fmt) {
		DecimalFormat fmter = (DecimalFormat) NumberFormat.getInstance();
		fmter.applyPattern(fmt);
		return fmter.format(value);
	}

	public static String fmtBool(Object value) {
		if(value == null)
			return "";
		
		Boolean b = (Boolean) value;
			
		if(b)
			return "true";
		return "false";
	}

	public static String fmtLong(Object value) {
		return fmtInt(value, LONG_DEFAULT_FMT);
	}

	public static String fmtLong(Object value, String fmt) {
		DecimalFormat fmter = (DecimalFormat) NumberFormat.getInstance();
		fmter.applyPattern(fmt);
		return fmter.format(value);
	}
	
	public static Date parseDate(String date) throws ParseException {
		if (date==null || date.equals(""))
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_DEFAULT_FMT);		
		return new Date(dateFormat.parse(date).getTime());
	}
}
