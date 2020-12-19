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

package it.senato.jedi.transform;

import it.senato.jedi.LogChannel;
import it.senato.jedi.bean.FormatTypeBean;
import it.senato.jedi.exception.JEDIInvocationException;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;


/**
 * This task has the job to make a transformation of a XML data.
 *
 * @see XMLTransformer
 */
public class TransformerTask {
	private Logger logger;

	/**
	 * Creates a TransformerTask object.
	 */
	public TransformerTask() {
		logger = Logger.getLogger(LogChannel.GENERIC_CHANNEL);
	}

	/**
	 * Performs the tranformation.
	 * 
	 * @param xsl is a String object of the XSL transformation
	 * @param xml is a String object of the XML data
	 * @param format is a FormatTypeBean object
	 * @return a ByteArrayOutputStream object
	 * @throws JEDIInvocationException
	 */
	public ByteArrayOutputStream execute(String xsl,String xml,FormatTypeBean format) throws JEDIInvocationException {
		logger.info("Begin document transformation");

		if(logger.isDebugEnabled())
			logger.debug(xml);

		try {
			Class<? extends AbstractTransformer> c = Class.forName(format.getTransformerClassName()).asSubclass(AbstractTransformer.class);

			AbstractTransformer t = c.newInstance();
			
			ByteArrayOutputStream out = t.run(xsl,xml);

			logger.info("End document transformation");

			return out;
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInvocationException(e.getMessage());
		} catch (InstantiationException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInvocationException(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInvocationException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new JEDIInvocationException(e.getMessage());
		}		
	}
}
