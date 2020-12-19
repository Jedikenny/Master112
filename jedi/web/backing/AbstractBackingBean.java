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

import it.senato.jedi.LogChannel;
import it.senato.jedi.ejb.DocProducerDelegate;
import it.senato.jedi.ejb.JEDIConfigDelegate;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class AbstractBackingBean {

    JEDIConfigDelegate confDelegate = new JEDIConfigDelegate();
    DocProducerDelegate docDelegate = new DocProducerDelegate();
    Logger log = Logger.getLogger(LogChannel.WEB_CHANNEL);

    protected FacesContext getContext(){
        return FacesContext.getCurrentInstance();
    }

    protected ResourceBundle getMessageBundle(){
        return ResourceBundle.getBundle("it.senato.jedi.resources.JEDILabels", getContext().getViewRoot().getLocale());
    }

    protected HttpServletRequest getRequest(){
        return (HttpServletRequest) getContext().getExternalContext().getRequest();
    }

    protected HttpServletResponse getResponse(){
        return (HttpServletResponse) getContext().getExternalContext().getResponse();
    }

    protected HttpSession getSession(){
        return getRequest().getSession();
    }

    protected void sendMessage(String sourceComponent, FacesMessage.Severity severity, String caption, String detail){
        getContext().addMessage(sourceComponent, new FacesMessage(severity, caption, detail));
    }

    protected void sendGeneralInfo(String infoLabel){
        sendMessage(null, FacesMessage.SEVERITY_INFO, null, getMessageBundle().getString(infoLabel));
    }

    protected void sendException(String sourceComponent, String captionLabel, Throwable e){
        log.error(e.getMessage(),e);
        sendMessage(sourceComponent, FacesMessage.SEVERITY_ERROR, getMessageBundle().getString(captionLabel), e.getMessage());
    }

    protected SessionUtils getSessionUtils(){
        return (SessionUtils) getSession().getAttribute("SessionUtils");
    }

}
