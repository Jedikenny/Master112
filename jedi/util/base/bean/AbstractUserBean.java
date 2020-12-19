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

package it.senato.jedi.util.base.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * This bean class contains the user and timestamp of last saving.
 *
 * @see AbstractBean
 */
public abstract class AbstractUserBean extends AbstractBean implements Serializable {
	private String lastUser;
	private Date timeStamp;

	/**
	 * Creates a AbstractUserBean object.
	 */
	public AbstractUserBean() {

	}

	/**
	 * Returns the timestamp of last saving.
	 * 
	 * @return a Date object
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the timestamp of last saving.
	 * 
	 * @param timeStamp is a Date object
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * Returns the user of last saving.
	 * 
	 * @return a String object
	 */
	public String getLastUser() {
		return lastUser;
	}

	/**
	 * Sets the user of last saving.
	 * 
	 * @param lastUser is a String object
	 */
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
        AbstractUserBean tmp = (AbstractUserBean) super.clone ();
        
        tmp.setTimeStamp((Date) cloneObject(timeStamp));
        tmp.setLastUser((String)cloneObject(lastUser));
        
        return tmp;
      }
}
