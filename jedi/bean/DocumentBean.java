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

import it.senato.jedi.ejb.entity.Document;
import it.senato.jedi.util.base.bean.AbstractOidBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the bean of a document.
 *
 * @see Document
 */
public class DocumentBean extends AbstractOidBean implements Serializable, Cloneable {
    private String description;
    private ApplicationBean application;
    private FormatTypeBean formatType;
    private String transformer;
    private List<ParameterIF> parameters;
    private XMLSourceBean xmlSource;
    private ReusableTransformerBean reusableTransformer;
    private TransformerTypeEnum transformerType;


    private ParameterIF newPar;

    
	public DocumentBean() {
        xmlSource = new XMLSourceBean();
		parameters = new ArrayList<ParameterIF>();
        application = new ApplicationBean();
        formatType = new FormatTypeBean();
        transformerType = TransformerTypeEnum.Normal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ApplicationBean getApplication() {
		return application;
	}

	public void setApplication(ApplicationBean application) {
		this.application = application;
	}

	public FormatTypeBean getFormatType() {
		return formatType;
	}

	public void setFormatType(FormatTypeBean formatType) {
		this.formatType = formatType;
	}

	public String getTransformer() {
        if (getTransformerType()==TransformerTypeEnum.Reusable && reusableTransformer != null)
            return reusableTransformer.getTransformer();

		return transformer;
	}

	public void setTransformer(String transformer) {
		this.transformer = transformer;
	}

	public List<ParameterIF> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterIF> parameters) {
		this.parameters = parameters;
	}

	public XMLSourceBean getXmlSource() {
		return xmlSource;
	}

	public void setXmlSource(XMLSourceBean xmlSource) {
		this.xmlSource = xmlSource;
	}

    public XMLSourceTypeBean getXMLSourceType() {
        return xmlSource.getType();
    }

    public ReusableTransformerBean getReusableTransformer() {
        return reusableTransformer;
    }

    public void setReusableTransformer(ReusableTransformerBean reusableTransformer) {
        if (reusableTransformer != null && reusableTransformer.getOid()>0)
            setTransformerType(TransformerTypeEnum.Reusable);
        else
            setTransformerType(TransformerTypeEnum.Normal);

        this.reusableTransformer = reusableTransformer;
    }

    public TransformerTypeEnum getTransformerType(){
        return transformerType;
    }

    public void setTransformerType(TransformerTypeEnum transformerType){
        if (transformerType==TransformerTypeEnum.Reusable)
            reusableTransformer = null;
        else
            transformer = null;

        this.transformerType = transformerType;
    }


    @Override
    public String toString() {
        return description;
    }
}
