/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-transferts
 */
package org.esupportail.transferts.web.controllers;

import artois.domain.beans.Interdit;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.utils.ContextUtils;
import org.esupportail.commons.utils.strings.StringUtils;
import org.esupportail.commons.web.controllers.ExceptionController;
import org.esupportail.transferts.domain.beans.*;
import org.esupportail.transferts.services.auth.Authenticator;
import org.esupportail.transferts.utils.Fonctions;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

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
	private boolean error = false;
	private String rne;
	private String numeroSerieImmatriculation;
	private Integer annee;
	private Integer currentAnnee;
	private String htmlCssStyle;
	private boolean boutonDeconnexion;
	private boolean transfertsAccueil;
	private String superGestionnaire;
	private List<String> listSuperGestionnaire = new ArrayList<String>();
	private String informaticiens;
	private List<String> listInformaticiens = new ArrayList<String>();
	private String validationAutomatique;
	private Integer regleGestionTE02;
	private boolean choixDuVoeuParComposante;
	private boolean majOdfAuto;
	private boolean reloadDemandeTransfertsDepartEchecAuto;
	private boolean reloadDemandeTransfertsAccueilEchecAuto;
	private boolean planningFermeturesAuto;
	private String ajoutEtablissementManuellement;
	private boolean activEtablissementManuellement;
	private Integer nbJourAvantAlertSilenceVautAccord;
	private Integer nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord;
	private boolean useWsCandidatures;
	private boolean useWsBu;
	private boolean useWsPostBac;
	private boolean useSuperGestionnaire;
	private boolean useRelanceDepartPersonnelConcerneSVA;
	private boolean useRelanceAccueilPersonnelConcerneSVA;
	private boolean useRelanceResumeSVA;
	private String timezone;
	private Date aujourdhui;
	private boolean defaultCodeSizeAnnee = false;
	private CodeSizeAnnee defaultCodeSize;
	private boolean useTimeOutConnexionWs;
	private Integer timeOutConnexionWs;
	private String schedulerCronExpression;
	private LdapUserService ldapUserService;
	private String ldapDisplayNameAttribute;
	private String ldapEmailAttribute;
	private List<SystemeProperties> propertiesSysteme;
	private boolean decisionAccueilFavorableToAutoSynchroOPI;

	/*
	 ******************* INIT ******************** */
	/**
	 * Constructor.
	 */
	public SessionController() {
		super();
	}

	/**
	 * @see org.esupportail.transferts.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		Assert.notNull(this.ldapUserService, "property ldapUserService of class "
				+ this.getClass().getName() + " can not be null");
		Assert.hasText(ldapDisplayNameAttribute, "property ldapDisplayNameAttribute of class "
				+ this.getClass().getName() + " can not be null");
		Assert.hasText(ldapEmailAttribute, "property ldapEmailAttribute of class "
				+ this.getClass().getName() + " can not be null");

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
		Assert.notNull(this.transfertsAccueil, "property transfertsAccueil of class "
				+ this.getClass().getName() + " can not be null");

		Assert.notNull(this.nbJourAvantAlertSilenceVautAccord, "property nbJourAvantAlertSilenceVautAccord of class "
				+ this.getClass().getName() + " can not be null");

		Assert.notNull(this.nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord, "property nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord of class "
				+ this.getClass().getName() + " can not be null");

		Assert.hasText(timezone, "property timezone of class "
				+ this.getClass().getName() + " can not be null");

		Assert.notNull(this.decisionAccueilFavorableToAutoSynchroOPI, "property decisionAccueilFavorableToAutoSynchroOPI of class "
				+ this.getClass().getName() + " can not be null");

		this.listInformaticiens=Fonctions.stringSplitToArrayList(this.informaticiens, ",");
	}

	@PostConstruct
	public void init() {
		setAujourdhui(new Date());
		Enumeration<?> liste = System.getProperties().propertyNames();
		String cle;
		propertiesSysteme = new ArrayList<SystemeProperties>();
		while( liste.hasMoreElements() ) {
			cle = (String)liste.nextElement();
			if (logger.isDebugEnabled())
				logger.debug("System.getProperty===>"+cle + " = " + System.getProperty(cle)+"<===");
			propertiesSysteme.add(new SystemeProperties(cle, System.getProperty(cle)));
		}
		Versions version = null;
		Parametres paramChoixDuVoeuParComposante;
		String text="Liste des erreurs : <BR /><BR />";

		try{
			version = getDomainService().getVersionByEtat(1);
		}
		catch(Exception  e)
		{
			logger.error(e);
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
		catch(Exception e)
		{
			logger.error(e);
			if(e.getCause()!=null && e.getCause().getCause()!=null)
				text += "- Erreurs : "+e.getCause().getCause().getMessage()+" (code_size) ===> NOK <BR />";
			else
				text += "- Erreurs : "+e.getMessage()+" ===> NOK <BR />";
		}

		try{
			paramChoixDuVoeuParComposante = getDomainService().getParametreByCode("choixDuVoeuParComposante");
			if(paramChoixDuVoeuParComposante!=null)
				setChoixDuVoeuParComposante(paramChoixDuVoeuParComposante.isBool());
			else
				setChoixDuVoeuParComposante(true);

			Parametres paramMajOdfAuto = getDomainService().getParametreByCode("maj_odf_auto");
			if(paramMajOdfAuto!=null)
				setMajOdfAuto(paramMajOdfAuto.isBool());
			else
				setMajOdfAuto(true);

			Parametres paramReloadDemandeTransfertsDepartEchecAuto = getDomainService().getParametreByCode("reload_demande_transferts_depart_echec_auto");
			if(paramReloadDemandeTransfertsDepartEchecAuto!=null)
				setReloadDemandeTransfertsDepartEchecAuto(paramReloadDemandeTransfertsDepartEchecAuto.isBool());
			else
				setReloadDemandeTransfertsDepartEchecAuto(true);

			Parametres paramReloadDemandeTransfertsAccueilEchecAuto = getDomainService().getParametreByCode("reload_demande_transferts_accueil_echec_auto");
			if(paramReloadDemandeTransfertsAccueilEchecAuto!=null)
				setReloadDemandeTransfertsAccueilEchecAuto(paramReloadDemandeTransfertsAccueilEchecAuto.isBool());
			else
				setReloadDemandeTransfertsAccueilEchecAuto(true);

			Parametres paramPlanningFermeturesAuto = getDomainService().getParametreByCode("planning_fermetures");
			if(paramPlanningFermeturesAuto!=null)
				setPlanningFermeturesAuto(paramPlanningFermeturesAuto.isBool());
			else
				setPlanningFermeturesAuto(true);

			Parametres paramAjoutEtablissementManuellement = getDomainService().getParametreByCode("ajout_etablissement_manuellement");
			if(paramAjoutEtablissementManuellement!=null) {
				setActivEtablissementManuellement(paramAjoutEtablissementManuellement.isBool());
				setAjoutEtablissementManuellement(paramAjoutEtablissementManuellement.getCommentaire());
			}
			else
			{
				setActivEtablissementManuellement(false);
				setAjoutEtablissementManuellement("");
			}

			Parametres paramWsBu = getDomainService().getParametreByCode("ws_bu");
			if(paramWsBu!=null)
				setUseWsBu(paramWsBu.isBool());
			else
				setUseWsBu(false);

			Parametres paramWsCandidatures = getDomainService().getParametreByCode("ws_candidatures");
			if(paramWsCandidatures!=null)
				setUseWsCandidatures(paramWsCandidatures.isBool());
			else
				setUseWsCandidatures(false);

			Parametres paramWsPostBac = getDomainService().getParametreByCode("ws_postbac");
			if(paramWsPostBac!=null)
				setUseWsPostBac(paramWsPostBac.isBool());
			else
				setUseWsPostBac(false);

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

			Parametres ajoutSuperGestionnaire = getDomainService().getParametreByCode("super_gestionnaire");
			if(ajoutSuperGestionnaire!=null && ajoutSuperGestionnaire.isBool()) {
				setUseSuperGestionnaire(ajoutSuperGestionnaire.isBool());
				setSuperGestionnaire(ajoutSuperGestionnaire.getCommentaire());
			}
			else
			{
				setUseSuperGestionnaire(false);
				setSuperGestionnaire(null);
			}

			Parametres paramRelanceDepartPersonnelConcerneSVA = getDomainService().getParametreByCode("relance_depart_sva");
			if(paramRelanceDepartPersonnelConcerneSVA!=null)
				setUseRelanceDepartPersonnelConcerneSVA(paramRelanceDepartPersonnelConcerneSVA.isBool());
			else
				setUseRelanceDepartPersonnelConcerneSVA(false);

			Parametres paramRelanceAccueilPersonnelConcerneSVA = getDomainService().getParametreByCode("relance_accueil_sva");
			if(paramRelanceAccueilPersonnelConcerneSVA!=null)
				setUseRelanceAccueilPersonnelConcerneSVA(paramRelanceAccueilPersonnelConcerneSVA.isBool());
			else
				setUseRelanceAccueilPersonnelConcerneSVA(false);

			Parametres paramRelanceResumeSVA = getDomainService().getParametreByCode("relance_resume_sva");
			if(paramRelanceResumeSVA!=null)
				setUseRelanceResumeSVA(paramRelanceResumeSVA.isBool());
			else
				setUseRelanceResumeSVA(false);
		}
		catch(Exception e)
		{
			logger.error(e);
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

	/**
	 * @return the current user, or null if guest.
	 * @throws Exception
	 */
	@Override
	public User getCurrentUser() throws Exception {
		User user = authenticator.getUser();
		// Verification du login authorise
		if (listSuperGestionnaire!= null && !listSuperGestionnaire.isEmpty()) {
			if(user!=null)
			{
				user.setAdmin(false);
				for (String ident : listSuperGestionnaire) {
					if (ident.equals(user.getLogin()))
					{
						if (logger.isDebugEnabled())
							logger.debug("SuperGestionnaire===>"+ user.getLogin()+" est un super gestionnaire<===");
						user.setAdmin(true);
						break;
					}
					else
					{
						if (logger.isDebugEnabled())
							logger.debug("SuperGestionnaire===>"+ user.getLogin()+" n'est pas un super gestionnaire<===");
					}
				}
			}
		}
		if (listInformaticiens!= null && user!=null && !listInformaticiens.isEmpty()) {
			user.setInformaticien(false);
			for (String ident : listInformaticiens) {
				if (ident.equals(user.getLogin()))
				{
					if (logger.isDebugEnabled())
						logger.debug("Informaticien===>"+ user.getLogin()+" est un informaticien<===");
					user.setInformaticien(true);
					user.setAdmin(true);
					break;
				}
				else
				{
					if (logger.isDebugEnabled())
						logger.debug("Informaticien===>"+ user.getLogin()+" n'est pas un informaticien<===");
				}
			}
		}
		return user;
	}

	public TreeNode constructTreeNode(TrResultatVdiVetDTO trResultatVdiVetDTO){
		TreeNode root = new DefaultTreeNode(new DocumentResultats("Files", "-", "Folder"), null);

        if (logger.isDebugEnabled())
		    logger.debug("trResultatVdiVetDTO===>"+trResultatVdiVetDTO+"<===");

		if(trResultatVdiVetDTO!=null && !trResultatVdiVetDTO.getEtapes().isEmpty()) {
			for (ResultatEtape re : trResultatVdiVetDTO.getEtapes()) {

                if (logger.isDebugEnabled()){
				    logger.debug("re.getAnnee()===>" + re.getAnnee() + "<===");
				    logger.debug("re.getLibEtape()===>" + re.getLibEtape() + "<===");
                }

				TreeNode treeNodeResultatEtape = new DefaultTreeNode(new DocumentResultats(re.getAnnee() + "-" + re.getLibEtape(), "", ""), root);

				if(re.getSession()!=null && !re.getSession().isEmpty()) {
					for (ResultatSession rs : re.getSession()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("rs.getLibSession()===>" + rs.getLibSession() + "<===");
                            logger.debug("rs.getMention()===>" + rs.getMention() + "<===");
                            logger.debug("rs.getResultat()===>" + rs.getResultat() + "<===");
                        }
						new DefaultTreeNode(new DocumentResultats(rs.getLibSession(), rs.getMention(), rs.getResultat()), treeNodeResultatEtape);
					}
				}
			}
		}
		return root;
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
		String test = request.getRequestURL().toString();
		String returnUrl=null;
		if(test.contains("/stylesheets/gestionnaire/"))
			returnUrl = request.getRequestURL().toString().replaceFirst("/stylesheets/.*", "/stylesheets/gestionnaire/welcome.xhtml");
		else if(test.contains("/stylesheets/depart/"))
			returnUrl = request.getRequestURL().toString().replaceFirst("/stylesheets/.*", "/stylesheets/depart/welcome.xhtml");
		else if(test.contains("/stylesheets/arrivee/"))
			returnUrl = request.getRequestURL().toString().replaceFirst("/stylesheets/.*", "/stylesheets/arrivee/welcome.xhtml");
		String forwardUrl;
		Assert.hasText(
				casLogoutUrl,
				"property casLogoutUrl of class " + getClass().getName() + " is null");
		forwardUrl = String.format(casLogoutUrl, StringUtils.utf8UrlEncode(returnUrl));
		request.getSession().invalidate();
		request.getSession(true);
		exceptionController.restart();
		externalContext.redirect(forwardUrl);
		facesContext.responseComplete();
		return null;
	}

	@Override
	public String toString() {
		return "SessionController{" +
				"exceptionController=" + exceptionController +
				", authenticator=" + authenticator +
				", casLogoutUrl='" + casLogoutUrl + '\'' +
				", error=" + error +
				", rne='" + rne + '\'' +
				", numeroSerieImmatriculation='" + numeroSerieImmatriculation + '\'' +
				", annee=" + annee +
				", currentAnnee=" + currentAnnee +
				", htmlCssStyle='" + htmlCssStyle + '\'' +
				", boutonDeconnexion=" + boutonDeconnexion +
				", transfertsAccueil=" + transfertsAccueil +
				", superGestionnaire='" + superGestionnaire + '\'' +
				", listSuperGestionnaire=" + listSuperGestionnaire +
				", informaticiens='" + informaticiens + '\'' +
				", listInformaticiens=" + listInformaticiens +
				", validationAutomatique='" + validationAutomatique + '\'' +
				", regleGestionTE02=" + regleGestionTE02 +
				", choixDuVoeuParComposante=" + choixDuVoeuParComposante +
				", majOdfAuto=" + majOdfAuto +
				", planningFermeturesAuto=" + planningFermeturesAuto +
				", ajoutEtablissementManuellement='" + ajoutEtablissementManuellement + '\'' +
				", activEtablissementManuellement=" + activEtablissementManuellement +
				", nbJourAvantAlertSilenceVautAccord=" + nbJourAvantAlertSilenceVautAccord +
				", nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord=" + nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord +
				", useWsCandidatures=" + useWsCandidatures +
				", useWsBu=" + useWsBu +
				", useWsPostBac=" + useWsPostBac +
				", useSuperGestionnaire=" + useSuperGestionnaire +
				", timezone='" + timezone + '\'' +
				", aujourdhui=" + aujourdhui +
				", defaultCodeSizeAnnee=" + defaultCodeSizeAnnee +
				", defaultCodeSize=" + defaultCodeSize +
				", logger=" + logger +
				'}';
	}

	/**
	 * JSF callback.
	 * @return a String.
	 * @throws IOException
	 */
	public String logout2() throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String returnUrl = request.getRequestURL().toString().replaceFirst("/stylesheets/[^/]*$", "");
		String forwardUrl;
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

	/**/
	public List<User> rechercherLdap(String filtre){
        if (logger.isDebugEnabled())
		    logger.debug("public void rechercherLdap(String filtre, String by)===>"+filtre+"<===");

		List<User> personnelsRecherche = new ArrayList<User>();

		List<LdapUser> resultList = ldapUserService.getLdapUsersFromFilter(filtre);

		// Via l'objet User on utilise DisplayName pour stocker le nom et Language pour stocker le prenom...
		for(LdapUser ldapUser : resultList){
			User u = new User();
			u.setLogin(ldapUser.getAttribute(ldapUserService.getIdAttribute()));
			u.setDisplayName(ldapUser.getAttribute(getLdapDisplayNameAttribute()));
			u.setMail(ldapUser.getAttribute(getLdapEmailAttribute()));

            if (logger.isDebugEnabled())
			    logger.debug("u===>"+u+"<===");

			if (!personnelsRecherche.contains(u)){
				personnelsRecherche.add(u);
			}
		}
		return personnelsRecherche;
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

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isError() {
		return error;
	}

	public String getRne() {
		return rne;
	}

	public void setRne(String rne) {
		this.rne = rne;
	}

	public void setNumeroSerieImmatriculation(String numeroSerieImmatriculation) {
		this.numeroSerieImmatriculation = numeroSerieImmatriculation;
	}

	public String getNumeroSerieImmatriculation() {
		return numeroSerieImmatriculation;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setCurrentAnnee(Integer currentAnnee) {
		this.currentAnnee = currentAnnee;
	}

	public Integer getCurrentAnnee() {
		return currentAnnee;
	}

	public String getHtmlCssStyle() {
		return htmlCssStyle;
	}

	public void setHtmlCssStyle(String htmlCssStyle) {
		this.htmlCssStyle = htmlCssStyle;
	}

	public void setBoutonDeconnexion(boolean boutonDeconnexion) {
		this.boutonDeconnexion = boutonDeconnexion;
	}

	public boolean isBoutonDeconnexion() {
		return boutonDeconnexion;
	}

	public String getSuperGestionnaire() {
		return superGestionnaire;
	}

	public void setSuperGestionnaire(String superGestionnaire) {
		this.superGestionnaire = superGestionnaire;
		if(this.superGestionnaire!=null)
			setListSuperGestionnaire(Fonctions.stringSplitToArrayList(this.superGestionnaire, ","));
	}

	public List<String> getListSuperGestionnaire() {
		return listSuperGestionnaire;
	}

	public Integer getNbJourAvantAlertSilenceVautAccord() {
		return nbJourAvantAlertSilenceVautAccord;
	}

	public void setNbJourAvantAlertSilenceVautAccord(
			Integer nbJourAvantAlertSilenceVautAccord) {
		this.nbJourAvantAlertSilenceVautAccord = nbJourAvantAlertSilenceVautAccord;
	}

	public void setListSuperGestionnaire(List<String> listSuperGestionnaire) {
		this.listSuperGestionnaire = listSuperGestionnaire;
	}

	public boolean isTransfertsAccueil() {
		return transfertsAccueil;
	}

	public void setTransfertsAccueil(boolean transfertsAccueil) {
		this.transfertsAccueil = transfertsAccueil;
	}

	public String getValidationAutomatique() {
		return validationAutomatique;
	}

	public void setValidationAutomatique(String validationAutomatique) {
		this.validationAutomatique = validationAutomatique;
	}

	public Integer getRegleGestionTE02() {
		return regleGestionTE02;
	}

	public void setRegleGestionTE02(Integer regleGestionTE02) {
		this.regleGestionTE02 = regleGestionTE02;
	}

	public boolean isChoixDuVoeuParComposante() {
		return choixDuVoeuParComposante;
	}

	public void setChoixDuVoeuParComposante(boolean choixDuVoeuParComposante) {
		this.choixDuVoeuParComposante = choixDuVoeuParComposante;
	}

	public Integer getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord() {
		return nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord;
	}

	public void setNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord(
			Integer nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord) {
		this.nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord = nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord;
	}

	public boolean isMajOdfAuto() {
		return majOdfAuto;
	}

	public void setMajOdfAuto(boolean majOdfAuto) {
		this.majOdfAuto = majOdfAuto;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public boolean isPlanningFermeturesAuto() {
		return planningFermeturesAuto;
	}

	public void setPlanningFermeturesAuto(boolean planningFermeturesAuto) {
		this.planningFermeturesAuto = planningFermeturesAuto;
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

	public List<String> getListInformaticiens() {
		return listInformaticiens;
	}

	public void setListInformaticiens(List<String> listInformaticiens) {
		this.listInformaticiens = listInformaticiens;
	}

	public String getInformaticiens() {
		return informaticiens;
	}

	public void setInformaticiens(String informaticiens) {
		this.informaticiens = informaticiens;
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

	public boolean isUseWsBu() {
		return useWsBu;
	}

	public void setUseWsBu(boolean useWsBu) {
		this.useWsBu = useWsBu;
	}

	public boolean isUseWsCandidatures() {
		return useWsCandidatures;
	}

	public void setUseWsCandidatures(boolean useWsCandidatures) {
		this.useWsCandidatures = useWsCandidatures;
	}

	public boolean isUseWsPostBac() {
		return useWsPostBac;
	}

	public void setUseWsPostBac(boolean useWsPostBac) {
		this.useWsPostBac = useWsPostBac;
	}

	public boolean isUseSuperGestionnaire() {
		return useSuperGestionnaire;
	}

	public void setUseSuperGestionnaire(boolean useSuperGestionnaire) {
		this.useSuperGestionnaire = useSuperGestionnaire;
	}

	public boolean isUseTimeOutConnexionWs() {
		return useTimeOutConnexionWs;
	}

	public void setUseTimeOutConnexionWs(boolean useTimeOutConnexionWs) {
		this.useTimeOutConnexionWs = useTimeOutConnexionWs;
	}

	public Integer getTimeOutConnexionWs() {
		return timeOutConnexionWs;
	}

	public void setTimeOutConnexionWs(Integer timeOutConnexionWs) {
		this.timeOutConnexionWs = timeOutConnexionWs;
	}

	public boolean isReloadDemandeTransfertsDepartEchecAuto() {
		return reloadDemandeTransfertsDepartEchecAuto;
	}

	public void setReloadDemandeTransfertsDepartEchecAuto(boolean reloadDemandeTransfertsDepartEchecAuto) {
		this.reloadDemandeTransfertsDepartEchecAuto = reloadDemandeTransfertsDepartEchecAuto;
	}

	public String getSchedulerCronExpression() {
		return schedulerCronExpression;
	}

	public void setSchedulerCronExpression(String schedulerCronExpression) {
		this.schedulerCronExpression = schedulerCronExpression;
	}

	public boolean isReloadDemandeTransfertsAccueilEchecAuto() {
		return reloadDemandeTransfertsAccueilEchecAuto;
	}

	public void setReloadDemandeTransfertsAccueilEchecAuto(boolean reloadDemandeTransfertsAccueilEchecAuto) {
		this.reloadDemandeTransfertsAccueilEchecAuto = reloadDemandeTransfertsAccueilEchecAuto;
	}

	public LdapUserService getLdapUserService() {
		return ldapUserService;
	}

	public void setLdapUserService(LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

	public String getLdapDisplayNameAttribute() {
		return ldapDisplayNameAttribute;
	}

	public void setLdapDisplayNameAttribute(String ldapDisplayNameAttribute) {
		this.ldapDisplayNameAttribute = ldapDisplayNameAttribute;
	}

	public String getLdapEmailAttribute() {
		return ldapEmailAttribute;
	}

	public void setLdapEmailAttribute(String ldapEmailAttribute) {
		this.ldapEmailAttribute = ldapEmailAttribute;
	}

	public boolean isUseRelanceResumeSVA() {
		return useRelanceResumeSVA;
	}

	public void setUseRelanceResumeSVA(boolean useRelanceResumeSVA) {
		this.useRelanceResumeSVA = useRelanceResumeSVA;
	}

	public boolean isUseRelanceDepartPersonnelConcerneSVA() {
		return useRelanceDepartPersonnelConcerneSVA;
	}

	public void setUseRelanceDepartPersonnelConcerneSVA(boolean useRelanceDepartPersonnelConcerneSVA) {
		this.useRelanceDepartPersonnelConcerneSVA = useRelanceDepartPersonnelConcerneSVA;
	}

	public boolean isUseRelanceAccueilPersonnelConcerneSVA() {
		return useRelanceAccueilPersonnelConcerneSVA;
	}

	public void setUseRelanceAccueilPersonnelConcerneSVA(boolean useRelanceAccueilPersonnelConcerneSVA) {
		this.useRelanceAccueilPersonnelConcerneSVA = useRelanceAccueilPersonnelConcerneSVA;
	}

	public List<SystemeProperties> getPropertiesSysteme() {
		return propertiesSysteme;
	}

	public void setPropertiesSysteme(List<SystemeProperties> propertiesSysteme) {
		this.propertiesSysteme = propertiesSysteme;
	}

	public boolean isDecisionAccueilFavorableToAutoSynchroOPI() {
		return decisionAccueilFavorableToAutoSynchroOPI;
	}

	public void setDecisionAccueilFavorableToAutoSynchroOPI(boolean decisionAccueilFavorableToAutoSynchroOPI) {
		this.decisionAccueilFavorableToAutoSynchroOPI = decisionAccueilFavorableToAutoSynchroOPI;
	}
}
