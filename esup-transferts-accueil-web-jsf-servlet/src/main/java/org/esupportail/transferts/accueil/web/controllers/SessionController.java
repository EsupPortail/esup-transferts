/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-transferts-accueil
 */
package org.esupportail.transferts.accueil.web.controllers;

import artois.domain.beans.Interdit;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.utils.ContextUtils;
import org.esupportail.commons.utils.strings.StringUtils;
import org.esupportail.commons.web.controllers.ExceptionController;
import org.esupportail.transferts.domain.beans.*;
import org.esupportail.transferts.services.auth.Authenticator;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * A bean to memorize the context of the application.
 */
public class SessionController extends AbstractDomainAwareBean {

	/*
	 ******************* PROPERTIES ******************** */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5936434246704000653L;

	/**
	 * The exception controller (called when logging in/out).
	 */
	private ExceptionController exceptionController;
	
	/**
	 * The authenticator.
	 */
	private transient Authenticator authenticator;

	private static final Logger logger = new LoggerImpl(SessionController.class);

	/**
	 * The CAS logout URL.
	 */
	private String casLogoutUrl;
	private Integer currentAnnee;
	private String htmlCssStyle;
	private boolean error = false;
	private boolean boutonDeconnexion;
	private String rne;
	private boolean editionPdfAccueilSansDecision;
	private String timezone;
	private String ajoutEtablissementManuellement;
	private boolean activEtablissementManuellement;
	private Date aujourdhui;
	private boolean defaultCodeSizeAnnee = false;
	private CodeSizeAnnee defaultCodeSize;
	private String numeroSerieImmatriculation;
	private Integer annee;
	private boolean choixDuVoeuParComposante;
	private boolean majOdfAuto;
	private boolean planningFermeturesAuto;
	private boolean useWsBu;
	private boolean useTimeOutConnexionWs;
	private Integer timeOutConnexionWs;
	/**
	 * Constructor.
	 */
	public SessionController() {
		super();
	}

	/**
	 * @see esup.transferts.accueil.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		Assert.notNull(this.exceptionController, "property exceptionController of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.authenticator, "property authenticator of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.hasText(rne, "property rne of class "
				+ this.getClass().getName() + " can not be null");				
		Assert.hasText(htmlCssStyle, "property htmlCssStyle of class "
				+ this.getClass().getName() + " can not be null");		
		Assert.notNull(this.boutonDeconnexion, "property boutonDeconnexion of class " 
				+ this.getClass().getName() + " can not be null");		
		Assert.notNull(this.editionPdfAccueilSansDecision, "property editionPdfAccueilSansDecision of class " 
				+ this.getClass().getName() + " can not be null");		
		Assert.hasText(timezone, "property timezone of class "
				+ this.getClass().getName() + " can not be null");	
	}

	@PostConstruct
	public void init() {
		setAujourdhui(new Date());
		Enumeration<?> liste = System.getProperties().propertyNames();
		String cle;

		while( liste.hasMoreElements() ) {
			cle = (String)liste.nextElement();
			if (logger.isDebugEnabled())
				logger.debug("===>"+cle + " = " + System.getProperty(cle)+"<===");
		}

		Versions version = null;
		Parametres choixDuVoeuParComposante;
		String text;
		text = "Liste des erreurs : <BR /><BR />";

		try{
			version = getDomainService().getVersionByEtat(1);
		}
		catch(Exception  e)
		{
			logger.warn(e);
			if(e.getCause()!=null && e.getCause().getCause()!=null)
				text += "- Erreurs : "+e.getCause().getCause().getMessage()+" (Versions) ===> NOK <BR />";
			else
				text += "- Erreurs : "+e.getMessage()+" ===> NOK <BR />";
		}

		try{
			defaultCodeSize = getDomainService().getCodeSizeDefaut();
			if (defaultCodeSize != null)
			{
				setDefaultCodeSizeAnnee(true);
				setNumeroSerieImmatriculation(this.defaultCodeSize.getCode());
				setAnnee(this.defaultCodeSize.getAnnee());
				setCurrentAnnee(this.defaultCodeSize.getAnnee());
			}
		}
		catch(Exception  e)
		{
			logger.warn(e);
			if(e.getCause()!=null && e.getCause().getCause()!=null)
				text += "- Erreurs : "+e.getCause().getCause().getMessage()+" (code_size) ===> NOK <BR />";
			else
				text += "- Erreurs : "+e.getMessage()+" ===> NOK <BR />";
		}

		try{
			Parametres paramWsBu = getDomainService().getParametreByCode("ws_bu");
			if(paramWsBu!=null)
				setUseWsBu(paramWsBu.isBool());
			else
				setUseWsBu(false);

			Parametres paramTimeOutConnexionWs = getDomainService().getParametreByCode("time_out_connexion_ws");
			if(paramTimeOutConnexionWs!=null && paramTimeOutConnexionWs.isBool()) {
				setUseTimeOutConnexionWs(paramTimeOutConnexionWs.isBool());
				setTimeOutConnexionWs(Integer.parseInt(paramTimeOutConnexionWs.getCommentaire()));
			}
			else
			{
				setUseTimeOutConnexionWs(false);
				setTimeOutConnexionWs(0);
			}

			choixDuVoeuParComposante = getDomainService().getParametreByCode("choixDuVoeuParComposante");
			if(choixDuVoeuParComposante!=null)
				setChoixDuVoeuParComposante(choixDuVoeuParComposante.isBool());
			else
				setChoixDuVoeuParComposante(true);

			Parametres maj_odf_auto = getDomainService().getParametreByCode("maj_odf_auto");
			if(maj_odf_auto!=null)
				setMajOdfAuto(maj_odf_auto.isBool());
			else
				setMajOdfAuto(true);

			Parametres planning_fermetures_auto = getDomainService().getParametreByCode("planning_fermetures");
			if(planning_fermetures_auto!=null)
				setPlanningFermeturesAuto(planning_fermetures_auto.isBool());
			else
				setPlanningFermeturesAuto(true);

			Parametres ajout_etablissement_manuellement = getDomainService().getParametreByCode("ajout_etablissement_manuellement");
			if(ajout_etablissement_manuellement!=null) {
				setActivEtablissementManuellement(ajout_etablissement_manuellement.isBool());
				setAjoutEtablissementManuellement(ajout_etablissement_manuellement.getCommentaire());
			}
			else
			{
				setActivEtablissementManuellement(false);
				setAjoutEtablissementManuellement("");
			}
		}
		catch(Exception  e)
		{
			logger.warn(e);
			if(e.getCause()!=null && e.getCause().getCause()!=null)
				text += "- Erreurs : "+e.getCause().getCause().getMessage()+" (parametres) ===> NOK <BR />";
			else
				text += "- Erreurs : "+e.getMessage()+" ===> NOK <BR />";
		}


		String v = getApplicationService().getVersion().toString();
		if (logger.isDebugEnabled())
			logger.debug("Version Application ===>"+v+"<===");

		text += "- Version de l'application : "+v+" ===> OK <BR />";

		if(version==null)
		{
			if (logger.isDebugEnabled())
				logger.debug("===>if(version==null)<===");
			text += "- Version de la base de données est null ===> NOK (initialiser les nomenclatures ou vérifier l'état à 1 dans la table version)<BR />";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "L'application a rencontré des erreurs lors de son lancement", text);
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
		else
		{
			if(!version.getNumero().equals(v))
			{
				text += "- Version de la base de données : "+version.getNumero()+" est différente de la version de l'application ("+v+"), veuillez passer le script de migration correspondant ===> NOK <BR />";
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "L'application a rencontré des erreurs lors de son lancement", text);
				RequestContext.getCurrentInstance().showMessageInDialog(message);
				if (logger.isDebugEnabled()) {
					logger.debug("Version BDD ===>" + version.toString() + "<===");
					logger.debug("Version Application ===>" + v + "<===");
				}
			}

		}
	}

	public void resetController()
	{
		// calling this method will reset all the beans of the application
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();		
		request.getSession().invalidate();
		request.getSession(true);		
		exceptionController.restart();	
	}
	
	/**
	 * @return the current user, or null if guest.
	 * @throws Exception 
	 */
	@Override
	public User getCurrentUser() throws Exception {
		return authenticator.getUser();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + "#" + hashCode();
	}

	

	/**
	 * JSF callback.
	 * @return a String.
	 * @throws IOException 
	 */
	public String logout() throws IOException {
		if (ContextUtils.isPortlet()) {
			throw new UnsupportedOperationException("logout() should not be called in portlet mode.");
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String returnUrl = request.getRequestURL().toString().replaceFirst("/stylesheets/[^/]*$", "");
		String forwardUrl;
		Assert.hasText(
				casLogoutUrl, 
				"property casLogoutUrl of class " + getClass().getName() + " is null");
		forwardUrl = String.format(casLogoutUrl, StringUtils.utf8UrlEncode(returnUrl));
		// note: the session beans will be kept even when invalidating 
		// the session so they have to be reset (by the exception controller).
		// We invalidate the session however for the other attributes.
		request.getSession().invalidate();
		request.getSession(true);
		// calling this method will reset all the beans of the application
		exceptionController.restart();
		externalContext.redirect(forwardUrl);
		facesContext.responseComplete();
		return null;
	}

	public List<DatasExterne> convertListInterditsToListDatasExterne(List<Interdit> lInterdits)
	{
		List<DatasExterne> listeDatasEterneNiveau2=null;
		if(lInterdits!=null && !lInterdits.isEmpty()) {
			listeDatasEterneNiveau2 = new ArrayList<DatasExterne>();
			for (Interdit c : lInterdits) {
				if (logger.isDebugEnabled()) {
					logger.debug("WebServices.Interdits===>" + c.getIdentifiant() + "<===");
					logger.debug("WebServices.Interdits===>" + c.getLibInterdit() + "<===");
				}
				DatasExterne de = new DatasExterne();
				de.setCode(c.getSource());
				de.setIdentifiant(c.getIdentifiant());
				de.setNiveau(c.getCodeNiveauInterdit());
				de.setLibInterdit(c.getLibInterdit());
				listeDatasEterneNiveau2.add(de);
			}
		}
		return listeDatasEterneNiveau2;
	}

	public List<DatasExterne> returnWebServiceOffline(String erreur)
	{
		List<DatasExterne> listeDatasEterneNiveau2=new ArrayList<DatasExterne>();
		DatasExterne de = new DatasExterne();
		de.setCode("offline");
		de.setIdentifiant("");
		de.setNiveau(4);
		de.setLibInterdit(erreur);
		listeDatasEterneNiveau2.add(de);
		return listeDatasEterneNiveau2;
	}
	
	
	
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @param exceptionController the exceptionController to set
	 */
	public void setExceptionController(final ExceptionController exceptionController) {
		this.exceptionController = exceptionController;
	}

	/**
	 * @param authenticator the authenticator to set
	 */
	public void setAuthenticator(final Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	
	/**
	 * @return the casLogoutUrl
	 */
	protected String getCasLogoutUrl() {
		return casLogoutUrl;
	}

	/**
	 * @param casLogoutUrl the casLogoutUrl to set
	 */
	public void setCasLogoutUrl(final String casLogoutUrl) {
		this.casLogoutUrl = StringUtils.nullIfEmpty(casLogoutUrl);
	}

	public Integer getCurrentAnnee() {
		return currentAnnee;
	}

	public void setCurrentAnnee(Integer currentAnnee) {
		this.currentAnnee = currentAnnee;
	}

	public String getHtmlCssStyle() {
		return htmlCssStyle;
	}

	public void setHtmlCssStyle(String htmlCssStyle) {
		this.htmlCssStyle = htmlCssStyle;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isBoutonDeconnexion() {
		return boutonDeconnexion;
	}

	public void setBoutonDeconnexion(boolean boutonDeconnexion) {
		this.boutonDeconnexion = boutonDeconnexion;
	}

	public String getRne() {
		return rne;
	}

	public void setRne(String rne) {
		this.rne = rne;
	}

	public boolean isEditionPdfAccueilSansDecision() {
		return editionPdfAccueilSansDecision;
	}

	public void setEditionPdfAccueilSansDecision(
			boolean editionPdfAccueilSansDecision) {
		this.editionPdfAccueilSansDecision = editionPdfAccueilSansDecision;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getAjoutEtablissementManuellement() {
		return ajoutEtablissementManuellement;
	}

	public void setAjoutEtablissementManuellement(String ajoutEtablissementManuellement) {
		this.ajoutEtablissementManuellement = ajoutEtablissementManuellement;
	}

	public boolean isActivEtablissementManuellement() {
		return activEtablissementManuellement;
	}

	public void setActivEtablissementManuellement(boolean activEtablissementManuellement) {
		this.activEtablissementManuellement = activEtablissementManuellement;
	}

	public Date getAujourdhui() {
		return aujourdhui;
	}

	public void setAujourdhui(Date aujourdhui) {
		this.aujourdhui = aujourdhui;
	}

	public boolean isDefaultCodeSizeAnnee() {
		return defaultCodeSizeAnnee;
	}

	public void setDefaultCodeSizeAnnee(boolean defaultCodeSizeAnnee) {
		this.defaultCodeSizeAnnee = defaultCodeSizeAnnee;
	}

	public CodeSizeAnnee getDefaultCodeSize() {
		return defaultCodeSize;
	}

	public void setDefaultCodeSize(CodeSizeAnnee defaultCodeSize) {
		this.defaultCodeSize = defaultCodeSize;
	}

	public String getNumeroSerieImmatriculation() {
		return numeroSerieImmatriculation;
	}

	public void setNumeroSerieImmatriculation(String numeroSerieImmatriculation) {
		this.numeroSerieImmatriculation = numeroSerieImmatriculation;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public boolean isChoixDuVoeuParComposante() {
		return choixDuVoeuParComposante;
	}

	public void setChoixDuVoeuParComposante(boolean choixDuVoeuParComposante) {
		this.choixDuVoeuParComposante = choixDuVoeuParComposante;
	}

	public boolean isMajOdfAuto() {
		return majOdfAuto;
	}

	public void setMajOdfAuto(boolean majOdfAuto) {
		this.majOdfAuto = majOdfAuto;
	}

	public boolean isPlanningFermeturesAuto() {
		return planningFermeturesAuto;
	}

	public void setPlanningFermeturesAuto(boolean planningFermeturesAuto) {
		this.planningFermeturesAuto = planningFermeturesAuto;
	}

	public boolean isUseWsBu() {
		return useWsBu;
	}

	public void setUseWsBu(boolean useWsBu) {
		this.useWsBu = useWsBu;
	}

	public Integer getTimeOutConnexionWs() {
		return timeOutConnexionWs;
	}

	public void setTimeOutConnexionWs(Integer timeOutConnexionWs) {
		this.timeOutConnexionWs = timeOutConnexionWs;
	}

	public boolean isUseTimeOutConnexionWs() {
		return useTimeOutConnexionWs;
	}

	public void setUseTimeOutConnexionWs(boolean useTimeOutConnexionWs) {
		this.useTimeOutConnexionWs = useTimeOutConnexionWs;
	}
}
