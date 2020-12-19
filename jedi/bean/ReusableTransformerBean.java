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

import it.senato.jedi.ejb.entity.ReusableTransformer;
import it.senato.jedi.util.base.bean.AbstractOidBean;

import java.io.Serializable;

/**
 * This class defines the bean of a reusable transformer.
 *
 * @see ReusableTransformer
 */
public class ReusableTransformerBean extends AbstractOidBean implements Serializable, Cloneable {
    private String description;
    private String transformer;

	public ReusableTransformerBean() {
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransformer() {
		return transformer;
	}

	public void setTransformer(String transformer) {
		this.transformer = transformer;
	}


    @Override
    public String toString() {
        return description;
    }
}
