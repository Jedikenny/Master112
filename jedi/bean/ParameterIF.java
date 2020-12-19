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

import it.senato.jedi.util.base.bean.AbstractBean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * This interface defines the operations to manage document's parameters.
 *
 */
public interface ParameterIF extends Serializable, Comparable<AbstractBean>  {

	/**
	 * Returns the parameter type.
	 * 
	 * @return is a ParameterTypeEnum object
	 */
	ParameterTypeEnum getParameterType();

	/**
	 * Returns the identifier.
	 * 
	 * @return a long value
	 */
    long getOid();

	/**
	 * Returns the identifier of the parameter type.
	 * 
	 * @return a Long object
	 */
    Long getParameterTypeOid();

    /**
     * Sets the identifier of the parameter type. 
     * 
     * @param parameterTypeOid is a Long object
     */
    void setParameterTypeOid(Long parameterTypeOid);

    /**
     * Clears the parameter value.
     * 
     */
	void reset();

	/**
	 * Returns the document identifier.
	 * 
	 * @return a long value
	 */
	long getIdDocument();

	/**
	 * Returns the description of the parameter.
	 * 
	 * @return a String object
	 */
	String getDescription();

	/**
	 * Returns the value of the parameter.
	 * 
	 * @return a Object
	 */
	Object getValue();

	/**
	 * Sets the value of the parameter.
	 * 
	 * @param value is a Object
	 */
	void setValue(Object value);

	/**
	 * Returns the value of the parameter as string.
	 * 
	 * @return is a String object
	 */
	String getValueAsString();

	/**
	 * Sets the value of the parameter as string.
	 * 
	 * @param valueString is a String object
	 */
	void setValueAsString(String valueString) throws ParseException;

	/**
	 * Returns the value of the parameter as date.
	 * 
	 * @return is a Date object. If the parameter type isn't date return null
	 */
    Date getValueAsDate();
    
	/**
	 * Sets the valueDate of the parameter as date, if the parameter type is date. Otherwise sets null value.
	 * 
	 * @param valueDate is a Date object
	 */
    void setValueAsDate(Date valueDate);

	/**
	 * Returns the value of the parameter as double.
	 * 
	 * @return is a Double object. If the parameter type isn't double return null
	 */
    Double getValueAsDouble();

	/**
	 * Sets the value of the parameter as double, if the parameter type is double. Otherwise sets null value.
	 * 
	 * @param valueDouble is a Double object
	 */
    void setValueAsDouble(Double valueDouble);

}
