/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-transferts
 */
package org.esupportail.transferts.web.controllers;

import java.util.Locale;

import org.esupportail.transferts.domain.DomainService;
import org.esupportail.transferts.domain.beans.User;
import org.esupportail.commons.beans.AbstractJsfMessagesAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.web.controllers.Resettable;

/**
 * An abstract class inherited by all the beans for them to get:
 * - the domain service (domainService).
 * - the application service (applicationService).
 * - the i18n service (i18nService).
 */
public abstract class AbstractDomainAwareBean extends AbstractJsfMessagesAwareBean implements Resettable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3917164118541810598L;

	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(AbstractDomainAwareBean.class);
	
	/**
	 * see {@link DomainService}.
	 */
	private DomainService domainService;
	
	/**
	 * Constructor.
	 */
	protected AbstractDomainAwareBean() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public final void afterPropertiesSet() {
		super.afterPropertiesSet(); 
		Assert.notNull(this.domainService, 
				"property domainService of class " + this.getClass().getName() + " can not be null");
		afterPropertiesSetInternal();
		reset();
	}

	/**
	 * This method is run once the object has been initialized, just before reset().
	 */
	protected void afterPropertiesSetInternal() {
		// override this method
	}
	
	/**
	 * @see org.esupportail.commons.web.controllers.Resettable#reset()
	 */
	public void reset() {
		// nothing to reset
		
	}

	/**
	 * @return the current user.
	 */
	protected User getCurrentUser() throws Exception {
		// this method should be overriden
		return null;
	}

	/**
	 * @return the current user's locale.
	 */
	@Override
	public Locale getCurrentUserLocale() {
		if (logger.isDebugEnabled()) {
			logger.debug(this.getClass().getName() + ".getCurrentUserLocale()");
		}
		User currentUser = null;
		try {
			currentUser = getCurrentUser();
		} catch (Exception e) {
			logger.error(e);
		}
		if (currentUser == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("no current user, return null");
			}
			return null;
		}
		String lang = currentUser.getLanguage();
		if (lang == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("language not set for user '" + currentUser.getLogin() 
						+ "', return null");
			}
			return null;
		}
		Locale locale = new Locale(lang);
		if (logger.isDebugEnabled()) {
			logger.debug("language for user '" + currentUser.getLogin() + "' is '" + locale + "'");
		}
		return locale;
	}
	
	/**
	 * @param domainService the domainService to set
	 */
	public void setDomainService(final DomainService domainService) {
		this.domainService = domainService;
	}

	/**
	 * @return the domainService
	 */
	public DomainService getDomainService() {
		return domainService;
	}

}
