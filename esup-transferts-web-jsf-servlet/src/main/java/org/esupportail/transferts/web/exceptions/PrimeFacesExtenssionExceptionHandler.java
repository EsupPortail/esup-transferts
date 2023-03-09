package org.esupportail.transferts.web.exceptions;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.mail.internet.InternetAddress;

import org.esupportail.commons.services.smtp.SmtpService;
import org.primefaces.extensions.component.ajaxerrorhandler.AjaxExceptionHandler;

/**
 * 
 */

public class PrimeFacesExtenssionExceptionHandler extends AjaxExceptionHandler {
	
    public PrimeFacesExtenssionExceptionHandler(javax.faces.context.ExceptionHandler wrapped) {
        super(wrapped);
    }
   
    @Override
    public void handle() throws FacesException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getResponseComplete()) {
            return;
        }

        Iterable<ExceptionQueuedEvent> exceptionQueuedEvents = getUnhandledExceptionQueuedEvents();
        if (exceptionQueuedEvents != null
                && exceptionQueuedEvents.iterator() != null) {
            Iterator<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = exceptionQueuedEvents.iterator();

            if (unhandledExceptionQueuedEvents.hasNext()) {
                final ExceptionQueuedEventContext eqec = unhandledExceptionQueuedEvents.next().getContext();
                final Throwable exception = eqec.getException();

                if (exception instanceof ViewExpiredException) {
                    unhandledExceptionQueuedEvents.remove();

                    final String outcome = "/stylesheets/welcome.xhtml?faces-redirect=true&expired=expired";
                    
                    final NavigationHandler navHandler = context.getApplication().getNavigationHandler();
                    navHandler.handleNavigation(context, null, outcome);
                    context.responseComplete();
                } 
            }
        }
       
        getWrapped().handle();
    }
}

