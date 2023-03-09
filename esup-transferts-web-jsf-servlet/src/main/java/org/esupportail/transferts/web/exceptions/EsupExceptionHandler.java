package org.esupportail.transferts.web.exceptions;
/**
 * 
 */

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;

import org.esupportail.commons.services.exceptionHandling.ExceptionService;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * @author cleprous
 *
 */
public class EsupExceptionHandler extends ExceptionHandlerWrapper {


	/*****************************************************************************
	 * TODO : Attention
	 * Fichier cr�� suite � un bug pr�sent dans la version 0.2.8  d'esup-commons
	 * A supprimer si la nouvelle version d'esup-commons corrige ce bug
	 *****************************************************************************/

	/*
	 *************************** PROPERTIES ******************************** */

	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());

	/**
	 * 
	 */
	private ExceptionHandler wrapped;

	/*
	 *************************** INIT ************************************** */

	/**
	 * Constructor.
	 */
	public EsupExceptionHandler() {
		super();
	}

	/**
	 * Constructor.
	 * @param wrapped
	 */
	public EsupExceptionHandler(final ExceptionHandler wrapped) {
		super();
		this.wrapped = wrapped;
		if (log.isDebugEnabled()) {
			log.debug("EsupExceptionHandler(final ExceptionHandler wrapped) --> "+this.wrapped);
		}
		//this.handle();
	}

	/*
	 *************************** METHODS *********************************** */

	/**
	 * @see ExceptionHandlerWrapper#getWrapped()
	 */
	@Override
	public ExceptionHandler getWrapped() {
		if (log.isDebugEnabled()) {
			log.debug("entering ExceptionHandler::getWrapped");
		}
		return this.wrapped;
	}

	/**
	 * @see javax.faces.context.ExceptionHandlerWrapper#handle()
	 */
	@Override
	public void handle() throws FacesException {
		if (log.isDebugEnabled()) {
			log.debug("entering ExceptionHandler::handle");
		}
		Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		if (!i.hasNext()) {
			// At this point, the queue will not contain any ViewExpiredEvents.
			// Therefore, let the parent handle them.
			getWrapped().handle();
		} else {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			Throwable t = context.getException();
			Throwable result = getRootCause(t);
			FacesContext fc = FacesContext.getCurrentInstance();
			ExceptionService e = null;
			HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
            ViewExpiredException vee = (ViewExpiredException) t;
			try {
				e = ExceptionUtils.catchException(result);
				request.getSession().setAttribute(ExceptionUtils.EXCEPTION_MARKER_NAME, e);
			} catch (Throwable th) {
				log.error("problem to catch exception = " + th, th);
				getWrapped().handle();
			}
			NavigationHandler navigation = fc.getApplication().getNavigationHandler();
			// Redirection vers la page des erreurs
			String view = null;
			if(e!=null)
				view = e.getExceptionView();
			if (log.isDebugEnabled()) {
				log.debug("fc --> " + fc);
				log.debug("view --> " + view);
				//log.debug("fc --> " + fc.getViewRoot().getViewId());
			}			

			if ( fc.getViewRoot() == null )
			{
				UIViewRoot view2 = fc.getApplication().getViewHandler().createView( fc, vee.getViewId() );
				fc.setViewRoot(view2);
				if (log.isDebugEnabled()) {
					log.debug("view --> " + view2);
				}					
			}			

			if (log.isDebugEnabled()) {
				log.debug("fc --> " + fc);
				log.debug("fc --> " + fc.getViewRoot().getViewId());
			}				

			navigation.handleNavigation(fc, null, view);
			fc.renderResponse();
		}
	}


	/*
	 *************************** ACCESSORS ********************************* */

}
