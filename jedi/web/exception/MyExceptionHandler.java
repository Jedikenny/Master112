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

package it.senato.jedi.web.exception;

import it.senato.jedi.LogChannel;
import org.apache.log4j.Logger;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;

public class MyExceptionHandler extends ExceptionHandlerWrapper {

    private static Logger log = Logger.getLogger(LogChannel.WEB_CHANNEL);
    private ExceptionHandler wrapped;

    public MyExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        //Iterate over all unhandeled exceptions
        Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context =
                    (ExceptionQueuedEventContext) event.getSource();

            //obtain throwable object
            Throwable t = context.getException();

            //here you do what ever you want with exception
            try {
                //log error
                log.error("Serious error happened!", t);
                //redirect to error view etc....
            } finally {
                //after exception is handeled, remove it from queue
                i.remove();
            }
        }
        //let the parent handle the rest
        getWrapped().handle();
    }
}