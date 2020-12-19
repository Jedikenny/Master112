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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean(name = "ReusableTransformerDetail")
@ViewScoped
public class ReusableTransformerDetail extends AbstractBackingBean{


    public ReusableTransformerDetail(){
    }

    private Long oid;

    private ReusableTransformerBean bean;


    public ReusableTransformerBean getBean() {
        return bean;
    }

    public void setBean(ReusableTransformerBean bean) {
        this.bean = bean;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }


    public String init() {

        if (!FacesContext.getCurrentInstance().isPostback()){
            bean = null;

            if (oid!=null)
                try {
                	log.debug("lookupReusableTransformer da init");
                    bean = confDelegate.lookupReusableTransformer(oid);
                    return "detail";
                } catch (Exception e) {
                    sendException(null, "lookupKO", e);
                    return "lookupError";
                }
            else {
                //init the bean
                bean = new ReusableTransformerBean();
                return "new";
            }
        } else
            return "postback";


    }

    public void save() {

        try {
        	bean.setLastUser(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());

            boolean wasNew = false;

            if (bean.getOid()==0)
                wasNew = true;

            bean = confDelegate.saveReusableTransformer(bean);


            if (getSession() != null) {

                SessionUtils su = getSessionUtils();
                List<ReusableTransformerBean> listAll = su.getReusableTransformers();

                if (listAll!=null) {
                    int i = listAll.indexOf(bean);

                    if (listAll.size() > 0 && !wasNew && i!=-1)
                        listAll.set(i, bean);

                    if (wasNew && i==0)
                        listAll.add(bean);
                }

                if (!wasNew){
                    ReusableTransformerSearch search = (ReusableTransformerSearch) getSession().getAttribute("ReusableTransformerSearch");
                    //looking for the bean to update in the list results
                    if (search!=null){
                        List<ReusableTransformerBean> list = search.getQueryResults();
                        if (list!=null && list.size()>0){
                            int i = list.indexOf(bean);
                            if (i != -1)
                                list.set(i, bean);
                        }
                    }

                    //looking for the bean to update in the list buffer
                    if (search!=null){
                        List<ReusableTransformerBean> list = search.getResultsBuffer();
                        if (list!=null && list.size()>0){
                            int i = list.indexOf(bean);
                            if (i != -1)
                                list.set(i, bean);
                        }
                    }
                }
            }

            sendGeneralInfo("saveOK");

        } catch (Exception e) {
            sendException(null, "saveKO", e);
        }

    }

}
