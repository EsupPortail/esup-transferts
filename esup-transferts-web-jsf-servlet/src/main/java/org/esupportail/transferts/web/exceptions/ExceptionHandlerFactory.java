/**
 * 
 */
package org.esupportail.transferts.web.exceptions;

import javax.faces.context.ExceptionHandler;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * @author cleprous
 *
 */
public class ExceptionHandlerFactory extends javax.faces.context.ExceptionHandlerFactory {

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
	 * ExceptionHandlerFactory parent.
	 */
	private javax.faces.context.ExceptionHandlerFactory parent;
	/*
	 *************************** INIT ************************************** */

	/**
	 * Constructor.
	 */
	public ExceptionHandlerFactory() {
		super();
	}



	/**
	 * Constructor.
	 * @param parent
	 */
	public ExceptionHandlerFactory(final javax.faces.context.ExceptionHandlerFactory parent) {
		super();
		this.parent = parent;
	}



	/*
	 *************************** METHODS *********************************** */

	@Override
	public ExceptionHandler getExceptionHandler() {
		if (log.isDebugEnabled()) {
			log.debug("entering ExceptionHandlerFactory::getExceptionHandler");
		}
		if (parent != null) {
			ExceptionHandler result = parent.getExceptionHandler();
			if (log.isDebugEnabled()) {
				log.debug("parent --> "+parent);
				log.debug("result --> "+result);
			}	
			result = new EsupExceptionHandler(result);
//			result = new ViewExpiredExceptionExceptionHandler(result);
//			result = new PrimeFacesExtenssionExceptionHandler(result);

			return result;

		}
		return null;
	}



	/**
	 * @see javax.faces.context.ExceptionHandlerFactory#getWrapped()
	 */
	@Override
	public javax.faces.context.ExceptionHandlerFactory getWrapped() {
		return parent;
	}



	/*
	 *************************** ACCESSORS ********************************* */

}
