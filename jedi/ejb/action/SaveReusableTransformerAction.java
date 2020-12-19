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

package it.senato.jedi.ejb.action;

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.*;
import it.senato.jedi.ejb.entity.*;
import it.senato.jedi.exception.JEDIConfigException;
import it.senato.jedi.exception.JEDIInfrastructureException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

/**
 * This action has the job to save a reusable transformer.
 * 
 */
public class SaveReusableTransformerAction {
	private Logger logger;

	private EntityManager em;

	/**
	 * Creates a SaveReusableTransformerAction object.
	 * 
	 * @param em is an EntityManager object
	 */
	public SaveReusableTransformerAction(EntityManager em) {
		logger = Logger.getLogger(LogChannel.EJB_CHANNEL);
		
		this.em = em;
	}
	
	/**
	 * Saves a reusable transformer.
	 * 
	 * @param bean is a ReusableTransformerBean object
	 * @return a ReusableTransformerBean object
	 * @throws JEDIConfigException
	 */
	public ReusableTransformerBean execute(ReusableTransformerBean bean) throws JEDIConfigException {
		try {
			final boolean isNew = (bean.getOid() == 0);
			logger.debug("saveReusableTransformer -> begin with bean: " + bean.getDescription() + " isNew: " + isNew);
			
			ReusableTransformer DBBean;
			if(isNew) {
				DBBean = new ReusableTransformer();
			}
			else {
				DBBean = em.find(ReusableTransformer.class,bean.getOid());
                if (!DBBean.getTimest().equals(bean.getTimeStamp()))
                    throw new JEDIInfrastructureException("The object you are trying to update is not up-to-date");
			}
			
			DBBean.setDescription(bean.getDescription());
			DBBean.setUser(bean.getLastUser());
            DBBean.setTransformer(bean.getTransformer());

            if(isNew)
                em.persist(DBBean);

            em.flush();
            em.refresh(DBBean);

			logger.debug("saveReusableTransformer -> done.");
			return DBBean.getBean();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new JEDIConfigException(e.getMessage());
		}
	}

}
