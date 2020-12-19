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

import java.io.*;

import it.senato.jedi.ejb.entity.Application;
import it.senato.jedi.util.base.bean.AbstractOidBean;
import it.senato.jedi.util.base.bean.Listable;

/**
 * This class defines the bean of an application.
 *
 * @see Application
 */
public class ApplicationBean extends AbstractOidBean implements Serializable, Cloneable, Listable {
	private String description;
	
	public ApplicationBean() {
		
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @Override
    public String toString() {
        return description;
    }

}
