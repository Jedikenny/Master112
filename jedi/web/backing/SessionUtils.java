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

package it.senato.jedi.web.backing;

import it.senato.jedi.bean.*;
import it.senato.jedi.ejb.JEDIConfigDelegate;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean (name = "SessionUtils")
@SessionScoped
public class SessionUtils {
    private List<ApplicationBean> applications;

    private List<FormatTypeBean> formatTypes;

    private List<DocumentBean> documents;

    private List<XMLSourceTypeBean> xmlSourceTypes;

    private List<ReusableTransformerBean> reusableTransformers;

    public List<DocumentBean> getDocuments() throws JEDIInfrastructureException, JEDIConfigException {

        if (documents == null) {
            JEDIConfigDelegate delegate = new JEDIConfigDelegate();
            documents = delegate.getDocuments(null, null, null, null);
        }

        return documents;

    }

    public List<ApplicationBean> getApplications() throws JEDIInfrastructureException, JEDIConfigException {

        if (applications == null) {
            JEDIConfigDelegate delegate = new JEDIConfigDelegate();
            applications = delegate.getApplications();
        }

        return applications;

    }

    public List<ReusableTransformerBean> getReusableTransformers() throws JEDIInfrastructureException, JEDIConfigException {

        if (reusableTransformers == null) {
            JEDIConfigDelegate delegate = new JEDIConfigDelegate();
            reusableTransformers = delegate.getReusableTransformers();
        }

        return reusableTransformers;

    }

    public List<FormatTypeBean> getFormatTypes() throws JEDIInfrastructureException, JEDIConfigException {

        if (formatTypes == null) {
            JEDIConfigDelegate delegate = new JEDIConfigDelegate();
            formatTypes = delegate.getFormatTypes();
        }

        return formatTypes;

    }

    public List<XMLSourceTypeBean> getXmlSourceTypes() throws JEDIInfrastructureException, JEDIConfigException {
        if (xmlSourceTypes == null) {
            JEDIConfigDelegate delegate = new JEDIConfigDelegate();
            xmlSourceTypes = delegate.getXMLSourceTypes();
        }

        return xmlSourceTypes;

    }

    public List<ParameterTypeEnum> getParameterTypes() throws JEDIInfrastructureException, JEDIConfigException {
        return Arrays.asList(ParameterTypeEnum.values());
    }

    public ApplicationBean lookupApplication(long idApplication) throws JEDIInfrastructureException, JEDIConfigException{
        ApplicationBean app = new ApplicationBean();
        app.setOid(idApplication);
        return getApplications().get(getApplications().indexOf(app));
    }

    public ReusableTransformerBean lookupReusableTransformer(long idReusableTransformer) throws JEDIInfrastructureException, JEDIConfigException{
        ReusableTransformerBean rt = new ReusableTransformerBean();
        rt.setOid(idReusableTransformer);
        return getReusableTransformers().get(getReusableTransformers().indexOf(rt));
    }

    public FormatTypeBean lookupFormatType(long idFormat) throws JEDIInfrastructureException, JEDIConfigException{
        FormatTypeBean xmlFormat = new FormatTypeBean();
        xmlFormat.setOid(idFormat);
        return getFormatTypes().get(getFormatTypes().indexOf(xmlFormat));
    }

    public XMLSourceTypeBean lookupXMLSourceType(long idType) throws JEDIInfrastructureException, JEDIConfigException{
        XMLSourceTypeBean type = new XMLSourceTypeBean(idType);
        return getXmlSourceTypes().get(getXmlSourceTypes().indexOf(type));
    }

}
