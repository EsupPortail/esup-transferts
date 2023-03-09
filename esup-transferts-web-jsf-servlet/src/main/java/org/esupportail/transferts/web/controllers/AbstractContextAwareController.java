/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-transferts
 */
package org.esupportail.transferts.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.esupportail.transferts.domain.DomainServiceDTO;
import org.esupportail.transferts.domain.DomainServiceScolarite;
import org.esupportail.transferts.domain.beans.User;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.commons.utils.Assert;

/**
 * An abstract class inherited by all the beans for them to get:
 * - the context of the application (sessionController).
 * - the domain service (domainService).
 * - the application service (applicationService).
 * - the i18n service (i18nService).
 */
public abstract class AbstractContextAwareController extends AbstractDomainAwareBean {

	/*
	 ******************* PROPERTIES ******************** */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -1826458262448752328L;
	
	
	/**
	 * The SessionController.
	 */
	private SessionController sessionController;
	private DomainServiceScolarite domainServiceScolarite;
	private DomainServiceDTO domainServiceDTO;
	private String typesEtablissement;
	private List<String> typesEtablissementListSplit = new ArrayList<String>();	
	private String xmlXslPath;
	private String tempPath;	
	private SmtpService smtpService;

	/*
	 ******************* INIT ******************** */

	/**
	 * Constructor.
	 */
	protected AbstractContextAwareController() {
		super();
	}

	/**
	 * @see org.esupportail.transferts.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		Assert.notNull(this.sessionController, "property sessionController of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.domainServiceScolarite, "property domainServiceScolarite of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.domainServiceDTO, "property domainServiceDTO of class "
				+ this.getClass().getName() + " can not be null");
		Assert.hasText(typesEtablissement, "property typesEtablissement of class "
				+ this.getClass().getName() + " can not be null");		
		Assert.hasText(getXmlXslPath(), "property xmlXslPath of class "
				+ this.getClass().getName() + " can not be null");
		Assert.hasText(tempPath, "property tempPath of class "
				+ this.getClass().getName() + " can not be null");			
		
		if(this.typesEtablissement!=null && this.typesEtablissement!="" && ((this.typesEtablissement.split(",")).length>1))
		{
			String[] tokens = this.typesEtablissement.split(",");
			for(int i=0; i<tokens.length; i++)
				this.typesEtablissementListSplit.add(tokens[i]);
		}
		else
			this.typesEtablissementListSplit.add(this.typesEtablissement);		
		
		Assert.notNull(smtpService, "property smtpService of class "
				+ this.getClass().getName() + " can not be null");		
	}

	/*
	 ******************* CALLBACK ******************** */
	
	
	/*
	 ******************* METHODS ******************** */
	
	/**
	 * @see org.esupportail.transferts.web.controllers.AbstractDomainAwareBean#getCurrentUser()
	 */
	@Override
	protected User getCurrentUser() throws Exception {
		return sessionController.getCurrentUser();
	}
	
	/**
	 * @param sessionController the sessionController to set
	 */
	public void setSessionController(final SessionController sessionController) {
		this.sessionController = sessionController;
	}

	/**
	 * @return the sessionController
	 */
	public SessionController getSessionController() {
		return sessionController;
	}

	public DomainServiceScolarite getDomainServiceScolarite() {
		return domainServiceScolarite;
	}

	public void setDomainServiceScolarite(DomainServiceScolarite domainServiceScolarite) {
		this.domainServiceScolarite = domainServiceScolarite;
	}

	public String getTypesEtablissement() {
		return typesEtablissement;
	}

	public void setTypesEtablissement(String typesEtablissement) {
		this.typesEtablissement = typesEtablissement;
	}

	public List<String> getTypesEtablissementListSplit() {
		return typesEtablissementListSplit;
	}

	public void setTypesEtablissementListSplit(
			List<String> typesEtablissementListSplit) {
		this.typesEtablissementListSplit = typesEtablissementListSplit;
	}

	public String getXmlXslPath() {
		return xmlXslPath;
	}

	public void setXmlXslPath(String xmlXslPath) {
		this.xmlXslPath = xmlXslPath;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}	

	public SmtpService getSmtpService() {
		return smtpService;
	}

	public void setSmtpService(SmtpService smtpService) {
		this.smtpService = smtpService;
	}

	public DomainServiceDTO getDomainServiceDTO() {
		return domainServiceDTO;
	}

	public void setDomainServiceDTO(DomainServiceDTO domainServiceDTO) {
		this.domainServiceDTO = domainServiceDTO;
	}
}
