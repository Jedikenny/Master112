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

import it.senato.jedi.ejb.entity.Parameter;
import it.senato.jedi.exception.InvalidTypeException;
import it.senato.jedi.util.base.bean.AbstractBean;
import it.senato.jedi.util.base.bean.AbstractOidBean;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * This class defines the bean of a parameter.
 *
 * @see Parameter
 */
public class ParameterBean extends AbstractOidBean implements ParameterIF {
	private long idDocument;
	private String description;
	private ParameterTypeEnum type;

	private Object value;

	public ParameterBean() {

	}

    public Long getParameterTypeOid() {
        if (type==null)
            type = ParameterTypeEnum.Integer;

        return type.getOid();
    }

    public void setParameterTypeOid(Long parameterTypeOid) {
        this.type = ParameterTypeEnum.getInstance(parameterTypeOid);
    }

    public long getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(long idDocument) {
		this.idDocument = idDocument;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ParameterTypeEnum getType() {
		return type;
	}

	public void setType(ParameterTypeEnum type) {
		this.type = type;
	}

	// ParameterIF
	public ParameterTypeEnum getParameterType() {
		return type;
	}
	
	public void reset() {
		value = null;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if(value == null) { 
			this.value = null;
			return;
		}
		
		if(value.getClass().getName().equals(this.type.getClassName()) == false)
			throw new InvalidTypeException("Parameter " + this.description + " must be of type " + type);
		
		this.value = value;
	}

	public String getValueAsString() {
		String s = "";
		
		if(value == null)
			return s;
		
		switch(type) {
			case Integer:
				s = FormatterUtil.fmtInt(value,"###0");
				break;
			case String:
				s = (String) value;
				break;
			case Date:
				s = FormatterUtil.fmtDate(value);
				break;
			case Double:
				s = FormatterUtil.fmtDouble(value);
				break;
			case Boolean:
				s = FormatterUtil.fmtBool(value);
				break;
			case Long:
				s = FormatterUtil.fmtLong(value);
				break;
			default:
				throw new InvalidTypeException("Wrong type " + type);
		}
		
		return s;
	}
	
	public void setValueAsString(String valueString) throws ParseException {

		if (valueString == null || valueString.equals("")) {
			setValue(null);
		} else {

			switch (type) {
			case Integer:
				try {
					Integer i = new Integer(valueString);
					setValue(i);
				} catch (Exception e) {
					throw new InvalidTypeException("Parameter "
							+ description
							+ " must be Integer!");
				}
				break;
			case String:
				try {
					setValue(valueString);
				} catch (Exception e) {
					throw new InvalidTypeException("Parameter "
							+ description
							+ " must bea String!");
				}
				break;
			case Date:
				try {
					Date d = FormatterUtil.parseDate(valueString);
					setValue(d);
				} catch (Exception e) {
					throw new InvalidTypeException("Parameter "
							+ description
							+ " must be a Date!");
				}
				break;
			case Double:
				try {
                    //we expect string in current locale format
                    NumberFormat format = NumberFormat.getNumberInstance(Locale.getDefault());
                    Number fs = format.parse(valueString);

                    Double db = fs.doubleValue();
                    setValue(db);
				} catch (Exception e) {
                    //may be they send in standard double format
                    try {
                        Double db = Double.parseDouble(valueString);
                        setValue(db);
                    } catch (Exception e1) {
                        throw new InvalidTypeException("Parameter "
                                + description
                                + " must be double!");
                    }
				}
				break;
			case Boolean:
				try {
					Boolean boo = new Boolean(valueString);
					setValue(boo);
				} catch (Exception e) {
					throw new InvalidTypeException("Parameter "
							+ description
							+ " must be boolean!");
				}
				break;
			case Long:
				try {
					Long ln = new Long(valueString);
					setValue(ln);
				} catch (Exception e) {
					throw new InvalidTypeException("Parameter "
							+ description
							+ " must be long!");
				}
				break;
			default:
				throw new InvalidTypeException("Wrong type " + type);

			}

		}
	}

    @Override
    public java.util.Date getValueAsDate() {
        if (type==ParameterTypeEnum.Date)
            return (java.util.Date) getValue();

        return null;
    }

    @Override
    public void setValueAsDate(java.util.Date valueDate) {
        if (type==ParameterTypeEnum.Date && valueDate!=null)
            setValue(new java.sql.Date(valueDate.getTime()));
        else
            setValue(null);

    }

    @Override
    public Double getValueAsDouble() {
        if (type==ParameterTypeEnum.Double)
            return (Double) getValue();

        return null;
    }

    @Override
    public void setValueAsDouble(Double valueDouble) {
        if (type==ParameterTypeEnum.Double && valueDouble!=null)
            setValue(valueDouble);
        else
            setValue(null);
    }

    @Override
    public int compareTo(AbstractBean o) {
        if (o==null)
            return -1;

        if (o instanceof ParameterBean)
            return new Long(getOid()).compareTo(((ParameterBean) o).getOid());

        return 0;
    }


}