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

package it.senato.jedi.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;

public class GeneralComparator<T> implements Comparator<T> {

    private String fieldName;
    private boolean reverseOrder;
    private Method getterMethod;

    public GeneralComparator(String fieldName, Class<T> type) {
        this(fieldName, type, false);
    }

    public GeneralComparator(String fieldName, Class<T> type, boolean reverseOrder) {
        this.fieldName = fieldName;
        this.reverseOrder = reverseOrder;
        getterMethod = retrieveGetterMethod(type);
    }

    @Override
    public int compare(T o1, T o2) {

        try {

            Comparable value1 = (Comparable) getterMethod.invoke(o1);
            Comparable value2 = (Comparable) getterMethod.invoke(o2);

            return (reverseOrder)?value2.compareTo(value1):value1.compareTo(value2);

        } catch (ClassCastException e) {
            //if the field is not a Comparable Object there is no priority
            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Error reading field values", e);
        }

    }

    private Method retrieveGetterMethod(Class type){

        String upperFieldName = toFirstUpperCase(fieldName);
        String error = "Error retrieving getter method for field " + fieldName;

        try {
            return type.getMethod("get" + upperFieldName);
        } catch (NoSuchMethodException e) {
            try {
                return type.getMethod("is" + upperFieldName);
            } catch (Exception e1) {
                throw new RuntimeException(error, e1);
            }
        } catch (Exception e) {
            throw new RuntimeException(error, e);
        }

    }

    private String toFirstUpperCase(String value) {
        return value.substring(0,1).toUpperCase() + value.substring(1);
    }
}


