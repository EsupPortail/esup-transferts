package org.esupportail.transferts.web.controllers;

import artois.domain.beans.Interdit;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.DomainServiceOpi;
import org.esupportail.transferts.domain.beans.*;
import org.esupportail.transferts.utils.Fonctions;
import org.esupportail.transferts.utils.GestionDate;
import org.esupportail.transferts.utils.RneModuleBase36;
import org.esupportail.transferts.web.comparator.ComparatorDateTimeAccueilDecision;
import org.esupportail.transferts.web.comparator.ComparatorDateTimeCorrespondance;
import org.esupportail.transferts.web.comparator.ComparatorSelectItem;
import org.esupportail.transferts.web.dataModel.*;
import org.esupportail.transferts.web.utils.FileGeneratorService;
import org.esupportail.transferts.web.utils.PDFUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.util.Assert;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdministrationController extends AbstractContextAwareController {

	/**
	 *
	 */
	private static final long serialVersionUID = -1084612345906407867L;
	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(AdministrationController.class);
	private String employeeAffiliation;
	private boolean personnel;
	private transient ListeTransfertDepartDataModel listeTransfertDepartDataModel;
	private transient TransfertDataModelOpi transfertDataModelOpi;
	private transient CodeSizeDataModel codeSizeDataModel;
	private List<IndOpi> listEntrants;
	private IndOpi currentOpi;
	private boolean switchTraiteNontraite;
	private EtudiantRef currentDemandeTransferts;
	private Fichier defautFichier;
	private String codePaysItems;
	private boolean rneVide = false;
	private CodeSizeAnnee codeSizeAnnee;
	private CodeSizeAnnee defaultCodeSize;
	private CodeSizeAnnee selectedCodeSizeAnnee;
	private List<Avis> listeAvisByNumeroEtudiantAndAnnee;
	private Fichier selectedFichier;
	private Avis selectedAvis;
	private Avis currentAvis;
	private Avis currentAvisMultiple;
	private AccueilDecision currentDecisionMultiple;
	private List<SelectItem> listeEtatsDossier;
	private List<SelectItem> listeLocalisationDossier;
	private List<SelectItem> listeDecisionsDossier;
	private DomainServiceOpi domainServiceWSOpiExt;
	private FileUploadController fileUploadController;
	private Parametres parametreAppli;
	/*Debut propriete Concernant le voeux d'orientation*/
	private transient List<SelectItem> listeDepartements = null;
	private transient List<SelectItem> listeEtablissements = null;
	private transient List<SelectItem> listeTypesDiplome = null;
	private transient List<SelectItem> listeAnneesEtude = null;
	private transient List<SelectItem> listeLibellesDiplome = null;
	private List<OffreDeFormationsDTO> listeLibellesEtape;
	private boolean deptVide = true;
	private boolean typesDiplomeVide = true;
	private boolean typesDiplomeAutreVide = true;
	private boolean libelleDiplomeVide = true;
	private boolean libelleEtapeVide = true;
	private boolean AnneeEtudeVide = true;
	private boolean composanteVide = true;
	private String codTypDip;
	private Integer codeNiveau;
	private String codeDiplome;
	private OffreDeFormationsDTO currentOdf;
	private transient OdfDataModel odfDataModel;
	/*Fin de propriete Concernant le voeux d'orientation*/
	private boolean modeSynchro;
	private Integer timeOutConnexionWsOpiScolarite;
	private String exclueEtpOpi;
	private String exclueBacOpi;
	private transient FileGeneratorService fileGeneratorService;
	private String source;
	/*Debut de la gestion de la sitution universitaire*/
	private transient SituationUniversitaireDataModel sudm;
	private SituationUniversitaire selectedSituationUniv;
	private String currentCleAccueilAnnee;
	private String currentCleAccueilResultat;
	private SituationUniversitaire currentSituationUniv;
	private AccueilAnnee currentAccueilAnnee;
	private AccueilResultat currentAccueilResultat;
	private boolean maxSU;
	/*Fin de la gestion de la sitution universitaire*/
	private Integer typePersonnel;
	private List<SelectItem> listeComposantes;
	private String codeComposante;
	private AccueilDecision currentAccueilDecision;
	private transient List<AccueilDecision> listeAccueilDecision = null;
	private AccueilDecision selectedDecision;
	private boolean droitsDepart = false;
	private boolean droitsArrivee = false;
	private EtudiantRef[] selectedDemandeTransferts;
	private String msgAjaxStatus;
	private Integer progress;
	private List<EtudiantRef> filteredEtudiantDepart;
	private List<EtudiantRef> filteredEtudiantAccueil;
	private List<EtudiantRef> filteredEtudiantOpi;
	private List<EtudiantRef> filteredEtudiantDepartMultiple;
	private List<EtudiantRef> filteredEtudiantAccueilMultiple;
	private Integer totalDepart;
	private Integer totalAccueil;
	private Integer totalOpi;
	private PersonnelComposante droitPC;
	private String aideTypeTransfert;
	//Candidature
	private boolean interditNiveau2;
	//OPI POSTBAC
	private boolean interditNiveau3;
	//DatasExterne datasEterneVap;
	private String texteInterditNiveau2;
	private String texteInterditNiveau3;
	private IndOpi[] selectedOpis;
	private boolean existCodeBac;
	private boolean multiple;
	private boolean repriseEtudes;
	private IndOpi selectedOpiForDelete;
	private String aideChoixVoeuParComposante;
	private String selectedmd5;
	private transient List<Fichier> listeFichiers;
	private transient List<Correspondance> listeCorrespondances = null;
	private WebService currentWs=null;
	private String sourceWs;
	private Integer selectedCurrentAnneeDroitUtilisateursACopier;
	private Integer selectedCurrentAnneeSignaturesACopier;
	private Integer selectedCurrentAnneePartenairesACopier;
	private boolean choixDuVoeuParComposanteByPartenaire;
	private transient TreeNode root;
	private Integer totalNombreDatasExterneNiveau1;
	private Integer totalNombreDatasExterneNiveau2;
	private Integer totalNombreDatasExterneNiveau3;
	private Versions version;
	private boolean droitAvisDefinitifTransfertDepartMultiple;


	@Override
	public void afterPropertiesSetInternal()
	{
		super.afterPropertiesSetInternal();
		Assert.hasText(employeeAffiliation, "property employeeAffiliation of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.modeSynchro, "property modeSynchro of class "
				+ this.getClass().getName() + " can not be null");
	}

	public String goToAPropos(){
		if(logger.isDebugEnabled())
			logger.debug("===>goToAPropos<===");
		version = getDomainService().getVersionByEtat(1);
		if(version==null) {
			version = new Versions();
			version.setNumero("null");
		}
		return "goToAPropos";
	}

	public void addPartenairesFromImport() throws CloneNotSupportedException {
		if(logger.isDebugEnabled())
			logger.debug("addPartenairesFromImport===>"+getSelectedCurrentAnneePartenairesACopier()+"<===");

		if(getSelectedCurrentAnneePartenairesACopier()!=null)
		{
			List<WsPub> lp = getDomainService().getWsPubByAnnee(getSelectedCurrentAnneePartenairesACopier());

			for(WsPub p : lp)
			{
				WsPub tmp = (WsPub) p.clone();
				tmp.setAnnee(getSessionController().getCurrentAnnee());
				getDomainService().addWsPub(tmp);
			}
			String summary = "Importation des partenaires réussies";
			String detail = "Importation des partenaires réussies";
			Severity severity=FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
		else
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Vous devez selectionner une année source", "Vous devez selectionner une année source");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void addSignaturesFromImport() throws CloneNotSupportedException {
		if(logger.isDebugEnabled())
			logger.debug("addSignaturesFromImport===>"+getSelectedCurrentAnneeSignaturesACopier()+"<===");
		if(getSelectedCurrentAnneeSignaturesACopier()!=null)
		{
			List<Fichier> lf = getDomainService().getFichiersByAnneeAndFrom(getSelectedCurrentAnneeSignaturesACopier(), "D");
			for(Fichier f : lf)
			{
				Fichier tmp = (Fichier) f.clone();
				tmp.setAnnee(getSessionController().getCurrentAnnee());
				getDomainService().addFichier(tmp);
			}
			lf = getDomainService().getFichiersByAnneeAndFrom(getSelectedCurrentAnneeSignaturesACopier(), "A");
			for(Fichier f : lf)
			{
				Fichier tmp = (Fichier) f.clone();
				tmp.setAnnee(getSessionController().getCurrentAnnee());
				getDomainService().addFichier(tmp);
			}
			String summary = "Importation des signatures réussies";
			String detail = "Importation des signatures réussies";
			Severity severity=FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
		else
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Vous devez selectionner une année source", "Vous devez selectionner une année source");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public void addDroitsUtilisateursFromImport() throws CloneNotSupportedException {
		if(logger.isDebugEnabled())
			logger.debug("importDroitsUtilisateurs===>"+getSelectedCurrentAnneeDroitUtilisateursACopier()+"<===");

		if(getSelectedCurrentAnneeDroitUtilisateursACopier()!=null) {
			List<PersonnelComposante> lpcDepart = getDomainService().getListePersonnelsComposantesBySourceAndAnnee("D", getSelectedCurrentAnneeDroitUtilisateursACopier());
			List<PersonnelComposante> lpcAccueil = getDomainService().getListePersonnelsComposantesBySourceAndAnnee("A", getSelectedCurrentAnneeDroitUtilisateursACopier());

			if(logger.isDebugEnabled()) {
				logger.debug("lpc===>" + lpcDepart + "<===");
				logger.debug("lpc===>" + lpcAccueil + "<===");
			}

			List<PersonnelComposante> newLpc = new ArrayList<PersonnelComposante>();

			if(lpcDepart!=null) {
				for (PersonnelComposante pc : lpcDepart) {
					PersonnelComposante tmp = (PersonnelComposante) pc.clone();
					tmp.setAnnee(getSessionController().getCurrentAnnee());
					newLpc.add(tmp);
				}
			}

			if(lpcAccueil!=null) {
				for (PersonnelComposante pc : lpcAccueil) {
					PersonnelComposante tmp = (PersonnelComposante) pc.clone();
					tmp.setAnnee(getSessionController().getCurrentAnnee());
					newLpc.add(tmp);
				}
			}
			if(lpcDepart!=null || lpcAccueil!=null)
				getDomainService().addPersonnelComposanteFromImport(newLpc);

			String summary = "Importation des droits utilisateurs réussis";
			String detail = "Importation des droits utilisateurs réussis";
			Severity severity=FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
		else
		{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Vous devez selectionner une année source", "Vous devez selectionner une année source");
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public String goToImportDonneesAnneesPrecedentes(){
		if(logger.isDebugEnabled())
			logger.debug("===>goToImportDonneesAnneesPrecedentes<===");

		Fonctions.soso("org.esupportail.transferts.domain.beans.Parametres",
				getSessionController().getTimeOutConnexionWs(),
				"sofianou");

		setSelectedCurrentAnneeDroitUtilisateursACopier(null);
		setSelectedCurrentAnneeSignaturesACopier(null);
		setSelectedCurrentAnneePartenairesACopier(null);
		return "goToImportDonneesAnneesPrecedentes";
	}

	public void saisieWs() {
		String source_ws = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("source_ws");
		if (logger.isDebugEnabled())
			logger.debug("param===>"+source_ws+"<===");

		if(source_ws!=null && !"".equals(source_ws)) {
			setSourceWs(source_ws);
			currentWs = getDomainService().getWebServiceByCode(getSourceWs());
			if (currentWs == null) {
				currentWs = new WebService();
				currentWs.setCode(getSourceWs());
			}
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("resizable", true);
			options.put("closable", true);
			options.put("contentHeight", "500");
			options.put("contentWidth", "900");
			options.put("draggable", false);
			options.put("modal", true);
			RequestContext.getCurrentInstance().openDialog("saisieWs", options, null);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "l'application a rencontré un problème technique (param==null)", "l'application a rencontré un problème technique (param==null)");
			FacesContext.getCurrentInstance().addMessage("administration.xhtml", message);
		}
	}

	public void onSaisieWs(SelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Le webservice a bien été mis à jour", "Le webservice a bien été mis à jour\"");
		FacesContext.getCurrentInstance().addMessage("administration.xhtml", message);
	}

	public void updateWebService()
	{
		currentWs = getDomainService().updateWebService(currentWs);
		RequestContext.getCurrentInstance().closeDialog(null);
		setSourceWs(null);
	}

	public String testWebservice(){
		if(logger.isDebugEnabled())
			logger.debug("public String testWebservice()===>"+currentWs+"<===");
		Integer etatConnexion=0;
		if(currentWs!=null)
		{
			Object tabReturn[] = Fonctions.appelWSAuth(currentWs.getUrl(),
					currentWs.getIdentifiant(),
					currentWs.getPwd(),
					currentWs.getNomClassJava(),
					currentWs.getNomMethodeJavaGetAll(),
					"arrayList",
					getSessionController().getTimeOutConnexionWs());

			List<DatasExterne> listeInterdit = (List<DatasExterne>) tabReturn[0];
			etatConnexion = (Integer) tabReturn[1];

			if (logger.isDebugEnabled())
			{
				logger.debug("listeInterdit===>" + listeInterdit + "<===");
				logger.debug("etatConnexion===>" + etatConnexion + "<===");
			}
		}
		String ret = "Connexion réussie";

		if(etatConnexion==0 && currentWs!=null)
			ret = "Impossible de se connecter au WebService "+currentWs.getCode();

		FacesMessage message;
		if(etatConnexion==1)
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, ret, ret);
		else
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ret, ret);

		RequestContext.getCurrentInstance().showMessageInDialog(message);
		return "toto";
	}

	public String goToSaisirCorrespondance()
	{
		return "goToSaisirCorrespondance";
	}

	public void showMessageInterditNiveau2() {
		if (logger.isDebugEnabled())
			logger.debug("public void showMessageInterditNiveau2()");
		if(isInterditNiveau2())
		{
			if (logger.isDebugEnabled())
				logger.debug("if(isVap())");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention...", this.getTexteInterditNiveau2());
			RequestContext.getCurrentInstance().showMessageInDialog(message);
		}
	}

	public String goToAdministration()
	{
		if (logger.isDebugEnabled())
			logger.debug("public String goToAdministration()");
		if(!getSessionController().isUseWsBu())
		{
			List<DatasExterne> nbDatasExterneNiveau1 = getDomainService().getAllDatasExterneByNiveau(1);
			if(nbDatasExterneNiveau1!=null)
				setTotalNombreDatasExterneNiveau1(nbDatasExterneNiveau1.size());
			else
				setTotalNombreDatasExterneNiveau1(0);
		}
		if(!getSessionController().isUseWsCandidatures())
		{
			List<DatasExterne> nbDatasExterneNiveau2 = getDomainService().getAllDatasExterneByNiveau(2);
			if(nbDatasExterneNiveau2!=null)
				setTotalNombreDatasExterneNiveau2(nbDatasExterneNiveau2.size());
			else
				setTotalNombreDatasExterneNiveau2(0);
		}
		if(!getSessionController().isUseWsPostBac())
		{
			List<DatasExterne> nbDatasExterneNiveau3 = getDomainService().getAllDatasExterneByNiveau(3);
			if(nbDatasExterneNiveau3!=null)
				setTotalNombreDatasExterneNiveau3(nbDatasExterneNiveau3.size());
			else
				setTotalNombreDatasExterneNiveau3(0);
		}
		return "goToAdministration";
	}

	public String goToValidationTransfertsDepart()
	{
		if (logger.isDebugEnabled())
			logger.debug("public String goToValidationTransfertsDepart()");
		return "goToValidationTransfertsDepart";
	}

	public String goToStats()
	{
		if (logger.isDebugEnabled())
			logger.debug("public String goToStats()");
		return "goToStats";
	}

	public void deleteSelectedOpi()
	{
		getDomainService().deleteSelectedOpi(getSelectedOpiForDelete());
		setTransfertDataModelOpi(null);
		String summary = getString("SUPPRESSION.OPI");
		String detail = getString("SUPPRESSION.OPI");
		Severity severity = FacesMessage.SEVERITY_INFO;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public List<SelectItem> getListeFichiers() {
		if (logger.isDebugEnabled())
			logger.debug("public List<SelectItem> getListeFichiers()===>"+listeFichiers+"<===");

		List<SelectItem> fichiers = new ArrayList<SelectItem>();

		if(listeFichiers==null)
			listeFichiers = getDomainService().getFichiersByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource());

		if(listeFichiers!=null)
		{
			if (logger.isDebugEnabled())
				logger.debug("===>if(listeFichiers!=null)<===");
			for(Fichier f : listeFichiers)
			{
				SelectItem option = new SelectItem(f.getMd5(),f.getNomSignataire()+" - "+f.getNom());
				fichiers.add(option);
				if (logger.isDebugEnabled()) {
					logger.debug("listeFichiers - MD5 --> " + f.getMd5());
					logger.debug("listeFichiers - NOM --> " + f.getNom());
				}
			}
			Collections.sort(fichiers,new ComparatorSelectItem());
		}
		else
		{
			//			SelectItem option = new SelectItem("", "");
			//			fichiers.add(option);	
			if (logger.isDebugEnabled())
				logger.debug("public List<SelectItem> getListeFichiers()===>null<===");
			fichiers = null;
		}
		return fichiers;
	}

	public void decisionChange(ValueChangeEvent vce)
	{
		String decision = "";
		if(vce!=null && !"".equals(vce) && vce.getNewValue()!=null) {
			String voeux = vce.getNewValue().toString();
			switch (voeux.charAt(0)) {
				case 'A':
					decision = getString("DECISION.FAVORABLE");
					break;
				case 'B':
					decision = getString("DECISION.DEFAVORABLE");
					break;
				case 'C':
					decision = getString("DECISION.AUTRE");
					break;
			}
			this.currentAccueilDecision.setDecision(decision);
		}
	}

	public List<SelectItem> getListeAccueilAnnee()
	{
		if (logger.isDebugEnabled())
			logger.debug("public List<AccueilAnnee> getListeAccueilAnnee()");

		List<SelectItem> listeAccueilAnnee = new ArrayList<SelectItem>();
		List<AccueilAnnee> listeAnneeAccueilDTO = getDomainService().getListeAccueilAnnee();
		if(listeAnneeAccueilDTO!=null)
		{
			for(AccueilAnnee aa : listeAnneeAccueilDTO)
			{
				SelectItem option = new SelectItem(aa.getIdAccueilAnnee(), aa.getLibelle());
				listeAccueilAnnee.add(option);
			}
			//			Collections.sort(listeAccueilAnnee,new ComparatorSelectItem());
		}
		else
		{
			SelectItem option = new SelectItem("", "");
			listeAccueilAnnee.add(option);
		}
		return listeAccueilAnnee;
	}

	public List<SelectItem> getListeAccueilResultat()
	{
		if (logger.isDebugEnabled())
			logger.debug("public List<AccueilAnnee> getListeAccueilAnnee()");

		List<SelectItem> listeAccueilResultat = new ArrayList<SelectItem>();
		List<AccueilResultat> listeAnneeResultatDTO = getDomainService().getListeAccueilResultat();
		if(listeAnneeResultatDTO!=null)
		{
			for(AccueilResultat ar : listeAnneeResultatDTO)
			{
				SelectItem option = new SelectItem(ar.getIdAccueilResultat(), ar.getLibelle());
				listeAccueilResultat.add(option);
			}
			Collections.sort(listeAccueilResultat,new ComparatorSelectItem());
		}
		else
		{
			SelectItem option = new SelectItem("", "");
			listeAccueilResultat.add(option);
		}
		return listeAccueilResultat;
	}

	public void deleteSelectedSituationUniv()
	{
		if (logger.isDebugEnabled())
			logger.debug("public void deleteSelectedSituationUniv()");

		if(selectedSituationUniv!=null)
		{
			if (logger.isDebugEnabled())
				logger.debug("################ selectedSituationUniv.getLibelle() ################ >"+selectedSituationUniv.getLibelle()+"<");
			this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().remove(selectedSituationUniv);
			selectedSituationUniv=null;
		}
		else
		{
			String summary = getString("ERREUR.ANNEE_ETUDE");
			String detail = getString("ERREUR.ANNEE_ETUDE");
			//			String summary = "Vous devez selectionner une annee d'etude";
			//			String detail = "Vous devez selectionner une annee d'etude";
			Severity severity=FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
	}

	public void ajouterSituationUniv()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("public void deleteSelectedSituationUniv()");
			logger.debug("################ currentCleAccueilAnnee ################ --> "+currentCleAccueilAnnee);
			logger.debug("################ currentCleAccueilResultat ################ --> "+currentCleAccueilResultat);
			logger.debug("################ currentSituationUniv.getLibelle() ################ >"+currentSituationUniv.getLibelle()+"<");
		}

		if(this.currentDemandeTransferts.getAccueil().getSituationUniversitaire()==null)
		{
			if (logger.isDebugEnabled())
				logger.debug("this.currentDemandeTransferts.getAccueil().getSituationUniversitaire()==null");
			List<SituationUniversitaire> lSitUniv = new ArrayList<SituationUniversitaire>();
			this.currentDemandeTransferts.getAccueil().setSituationUniversitaire(lSitUniv);
		}
		else
		{
			if (logger.isDebugEnabled())
				logger.debug("################ this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size() ################ --> "+this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size()+"<");
		}

		if(currentCleAccueilAnnee!=null && !"".equals(currentCleAccueilAnnee)
				&& currentCleAccueilResultat!=null && !"".equals(currentCleAccueilResultat)
				&& currentSituationUniv.getLibelle()!=null && !"".equals(currentSituationUniv.getLibelle()))
		{
			currentAccueilAnnee = getDomainService().getAccueilAnneeById(Integer.parseInt(currentCleAccueilAnnee));
			currentAccueilResultat = getDomainService().getAccueilResultatById(Integer.parseInt(currentCleAccueilResultat));
			String timestamp = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
			currentSituationUniv.setId(this.currentDemandeTransferts.getNumeroEtudiant()+"_"+timestamp);
			currentSituationUniv.setAnnee(currentAccueilAnnee);
			currentSituationUniv.setResultat(currentAccueilResultat);
			this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().add(currentSituationUniv);
			/*Reinitialisation des currents objects*/
			currentCleAccueilAnnee=null;
			currentCleAccueilResultat=null;
			currentAccueilResultat = new AccueilResultat();
			currentSituationUniv = new SituationUniversitaire();
			if (logger.isDebugEnabled())
				logger.debug("################ this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size() apres ajout du nouveau ################ --> "+this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size()+"<");
		}
		else
		{
			String summary = getString("ERREUR.CHAMP_OBLIGATOIRE_SITUATION_UNIVERSITAIRE");
			String detail = getString("ERREUR.CHAMP_OBLIGATOIRE_SITUATION_UNIVERSITAIRE");
			//			String summary = "Tous les champs sont obligatoires";
			//			String detail = "Tous les champs sont obligatoires";
			Severity severity=FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}

	}

	public List<SelectItem> getListeBacOuEqu() {
		if (logger.isDebugEnabled())
			logger.debug("getDomainServiceScolarite().recupererBacOuEquWS()");
		List<SelectItem> listeBac = getDomainServiceDTO().getListeBacOuEqu();
		Collections.sort(listeBac,new ComparatorSelectItem());
		return listeBac;
	}

	public List<SelectItem> getListeNationalite() {
		if (logger.isDebugEnabled())
			logger.debug("getDomainServiceScolarite().getListePays();");

		List<SelectItem> listePays = new ArrayList<SelectItem>();
		List<TrPaysDTO> listePaysDTO = getDomainServiceScolarite().getListePays();
		if(listePaysDTO!=null)
		{
			for(TrPaysDTO pDTO : listePaysDTO)
			{
				SelectItem option = new SelectItem(pDTO.getCodePay(), pDTO.getLibNationalite());
				listePays.add(option);
			}
			Collections.sort(listePays,new ComparatorSelectItem());
		}
		else
		{
			SelectItem option = new SelectItem("", "");
			listePays.add(option);
		}
		return listePays;
	}

	public String deleteDemandeTransfert() {
		getDomainService().deleteDemandeTransfert(this.currentDemandeTransferts, getSessionController().getCurrentAnnee());
		String summary = getString("SUPPRESSION.DEMANDE_TRANSFERT");
		String detail = getString("SUPPRESSION.DEMANDE_TRANSFERT");
		Severity severity = FacesMessage.SEVERITY_INFO;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		if("D".equals(getSource()))
			return this.goToTransfertsDepart();
		else
			return this.goToTransfertsAccueil();
	}

	/**
	 *
	 */
	public void exportOpi() {
		String typeExport = "txt";
		String fileName = "DonneesEtudiantOpi" + "." + typeExport;
		List<String> listeIndOpi = getDomainService().getIndOpiExtractBySource(getSessionController().getCurrentAnnee(), getSource());

		List<String> colonnesChoisies = new ArrayList<String>();

		if (listeIndOpi != null && !listeIndOpi.isEmpty())
		{
			for (String opi : listeIndOpi)
				colonnesChoisies.add(opi);
		}
		getFileGeneratorService().opiFile(listeIndOpi, typeExport, fileName);
	}

	/**
	 *
	 */
	public void exportOpiVoeux() {
		String typeExport = "txt";
		String fileName = "VoeuxOpi" + "." + typeExport;
		List<String> listeIndOpi = getDomainService().getVoeuxInsExtractBySource(getSessionController().getCurrentAnnee(), getSource());

		List<String> colonnesChoisies = new ArrayList<String>();

		if (listeIndOpi != null && !listeIndOpi.isEmpty()) {
			for (String opi : listeIndOpi)
				colonnesChoisies.add(opi);
		}
		getFileGeneratorService().opiFile(listeIndOpi, typeExport, fileName);
	}

	public void exportDemandeTransfertsAccueil()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("exportDemandeTransfertsAccueil()");
		}
		setSource("A");
		String typeExport = "xls";
		String fileName = "listeDemandesTransfertsAccueil" + "." + typeExport;

		List<PersonnelComposante> lPc=null;
		List<EtudiantRef> lEtu=new ArrayList<EtudiantRef>();
		String chaineComposante=null;
		try {
			lPc = getDomainService().getListeComposantesByUidAndSourceAndAnnee(getSessionController().getCurrentUser().getLogin(), getSource(), getSessionController().getCurrentAnnee());
			if(lPc!=null && !lPc.isEmpty())
			{
				for(PersonnelComposante pc : lPc)
				{
					chaineComposante+=pc.getCodeComposante()+",";
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}

		List<EtudiantRef> lEtu2 = getDomainService().getAllDemandesTransfertsByAnnee(this.getSessionController().getCurrentAnnee(), "A");

		if(lEtu2!=null)
		{
			for(EtudiantRef etu : lEtu2)
			{
				try {
					if((lPc!=null
							&& chaineComposante!=null
							&& !"".equals(chaineComposante)
							&& chaineComposante.contains(etu.getTransferts().getOdf().getCodeComposante())
					) || getSessionController().getCurrentUser().isAdmin())
						lEtu.add(etu);
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}

		//List<DatasExterne> listeDatasEterneNiveau2 = getDomainService().getAllDatasExterneByIdentifiantAndNiveau(etu.getNumeroIne(), 2);
		/*************************************************************************************/
		List<Interdit> listeInterditsNiveau2;
		List<DatasExterne> listeDatasEterneNiveau2;
		WebService currentWsCandidatures = getDomainService().getWebServiceByCode("candidature");
		if(getSessionController().isUseWsCandidatures() && currentWsCandidatures!=null)
		{
			Integer etatConnexion;
			Object tabReturn[] = Fonctions.appelWSAuth(currentWsCandidatures.getUrl(),
					currentWsCandidatures.getIdentifiant(),
					currentWsCandidatures.getPwd(),
					currentWsCandidatures.getNomClassJava(),
					currentWsCandidatures.getNomMethodeJavaGetAll(),
					"arrayList",
					getSessionController().getTimeOutConnexionWs());

			listeInterditsNiveau2 = (List<Interdit>) tabReturn[0];
			etatConnexion = (Integer) tabReturn[1];

			if (logger.isDebugEnabled()) {
				logger.debug("listeInterditsNiveau2===>" + listeInterditsNiveau2 + "<===");
				logger.debug("etatConnexion===>" + etatConnexion + "<===");
			}
			if(etatConnexion==1)
				listeDatasEterneNiveau2 = getSessionController().convertListInterditsToListDatasExterne(listeInterditsNiveau2);
			else
				listeDatasEterneNiveau2 = getSessionController().returnWebServiceOffline(getString("WARNING.SERVICE_INDISPONIBLE"));

		}
		else
		{
			listeDatasEterneNiveau2 = getDomainService().getAllDatasExterneByNiveau(2);
		}
		/*************************************************************************************/

		List<EtudiantRefExcel> listeEtudiantRefExcel = new ArrayList<EtudiantRefExcel>();
		List<AccueilAnnee> lAA = getDomainService().getListeAccueilAnnee();

		for (EtudiantRef etu : lEtu)
		{
			if (logger.isDebugEnabled())
				logger.debug("etu.getNumeroEtudiant()===>"+etu.getNumeroEtudiant()+"<===");

			AdresseRef adresse = new AdresseRef();
			adresse.setLibAd1(etu.getAdresse().getLibAd1());
			adresse.setCodeCommune(etu.getAdresse().getCodeCommune());
			adresse.setEmail(etu.getAdresse().getEmail());
			EtudiantRefExcel excel = new EtudiantRefExcel();

			/*Debut d'initialisation de la colonne candidature à partir des donnees de la table dataExterne (niveau 2)*/
			String txtDataExterneNiveau2 = "";

			if(listeDatasEterneNiveau2!=null)
				for(DatasExterne lInterditNiveau2 : listeDatasEterneNiveau2)
				{
					if(etu.getNumeroIne().equals(lInterditNiveau2.getIdentifiant()))
					{
						if (logger.isDebugEnabled())
							logger.debug("===>"+etu.getNumeroIne()+"-----"+lInterditNiveau2.getLibInterdit()+"<===");
						txtDataExterneNiveau2 += " - "+lInterditNiveau2.getLibInterdit();
					}
				}

			if (logger.isDebugEnabled())
				logger.debug("Liste des interdits de niveau 2-->"+txtDataExterneNiveau2);

			excel.setDataExterneNiveau2(txtDataExterneNiveau2);
			/*Fin d'initialisation de la colonne candidature à partir des donnees de la table dataExterne (niveau 2)*/

			DateFormat dfl = DateFormat.getDateTimeInstance(DateFormat.SHORT , DateFormat.SHORT);
			excel.setDateDeLaDemandeTransfert(dfl.format(etu.getTransferts().getDateDemandeTransfert()));
			excel.setNumeroEtudiant(etu.getNumeroEtudiant());
			excel.setNomPatronymique(etu.getNomPatronymique());
			excel.setPrenom1(etu.getPrenom1());
			excel.setNumeroIne(etu.getNumeroIne());
			SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
			excel.setDateNaissance(formater.format(etu.getDateNaissance()));
			excel.setAdresse(adresse);
			excel.setEtatDuDossier(this.getLibEtatDossier(etu.getTransferts().getTemoinTransfertValide()));
			excel.setComposante(etu.getTransferts().getOdf().getLibComposante());
			excel.setDerniereIaInscription(etu.getLibEtapePremiereLocal());
			excel.setOdf(etu.getTransferts().getOdf());
			excel.setUniversiteDepart(getDomainServiceScolarite().getEtablissementByRne(etu.getAccueil().getCodeRneUnivDepart()));
			String fromSource;

			if("P".equals(etu.getAccueil().getFrom_source()))
			{
				fromSource="Partenaire";
				excel.setFrom_source(fromSource);
			}
			else
			{
				fromSource="Autre";
				excel.setFrom_source(fromSource);
			}

			List<SituationUniversitaire> lSU =  etu.getAccueil().getSituationUniversitaire();

			boolean continu = true;
			for(AccueilAnnee aa : lAA)
			{
				for(SituationUniversitaire su : lSU)
				{
					if(su.getAnnee().getIdAccueilAnnee().equals(aa.getIdAccueilAnnee()))
					{
						excel.setDerniereFormation(su.getLibelle());
						continu = false;
						break;
					}
				}
				if(!continu)
				{
					break;
				}
			}
			excel.setCodeBac(etu.getAccueil().getCodeBac());
			excel.setAnneeBac(etu.getAccueil().getAnneeBac());
			excel.setValidation(this.getValidationOuCandidature(etu.getAccueil().getValidationOuCandidature()));


			if (excel.getOdf() == null)
				excel.setLibelleVET(etu.getTransferts().getLibelleTypeDiplome());
			else
				excel.setLibelleVET(excel.getOdf().getLibVersionEtape());

			/* Debut des informations sur la décision */
			String decision="";
			if(etu.getTransferts().getTemoinTransfertValide()==2)
			{
				Set<AccueilDecision> lAd = etu.getAccueilDecision();
				//				Set<AccueilDecision> lAd = getDomainService().getAccueilDecisionByNumeroEtudiantAndAnnee(etu.getNumeroEtudiant(), etu.getAnnee());

				long tableau[] = new long[lAd.size()];
				int i=0;
				for(AccueilDecision ad : lAd)
				{
					tableau[i] = ad.getId();
					i++;
				}
				Arrays.sort(tableau);
				for(AccueilDecision ad : lAd)
				{
					if(ad.getId()==tableau[tableau.length-1])
					{
						if("A".equals(ad.getAvis()))
							decision="Favorable";
						else
							decision="Défavorable";
					}
				}
			}
			excel.setDecisionDE(decision);
			/* Fin des informations sur la décision */

			listeEtudiantRefExcel.add(excel);
		}
		List<String> colonnesChoisies = new ArrayList<String>();
		getFileGeneratorService().conventionFileAccueil(listeEtudiantRefExcel,	typeExport, fileName, colonnesChoisies);

	}

	public void exportListeDroitsDepart()
	{
		if (logger.isDebugEnabled())
			logger.debug("exportListeDroitsDepart()");

		setSource("D");
		String typeExport = "xls";
		String fileName = "listeDesDroitsTransfertsDepart" + "." + typeExport;
		List<String> colonnesChoisies = new ArrayList<String>();
		List<PersonnelComposante> lPersComp = getDomainService().getListePersonnelsComposantesBySourceAndAnnee(getSource(), getSessionController().getCurrentAnnee());

		if(lPersComp!=null)
			for(PersonnelComposante pc : lPersComp)
				pc.setLibelleTypePersonnel(this.getTypePersonnel(pc.getTypePersonnel()));

		getFileGeneratorService().exportXlsPersonnelsComposantesDepart(lPersComp,	typeExport, fileName, colonnesChoisies);
	}

	public void exportListeDroitsAccueil()
	{
		if (logger.isDebugEnabled())
			logger.debug("exportListeDroitAccueil()");

		setSource("A");
		String typeExport = "xls";
		String fileName = "listeDesDroitsTransfertsAccueil" + "." + typeExport;
		List<String> colonnesChoisies = new ArrayList<String>();
		List<PersonnelComposante> lPersComp = getDomainService().getListePersonnelsComposantesBySourceAndAnnee(getSource(), getSessionController().getCurrentAnnee());

		if(lPersComp!=null)
			for(PersonnelComposante pc : lPersComp)
				pc.setLibelleTypePersonnel(this.getTypePersonnel(pc.getTypePersonnel()));

		getFileGeneratorService().exportXlsPersonnelsComposantesArrivee(lPersComp,	typeExport, fileName, colonnesChoisies);
	}

	private String getTypePersonnel(Integer idTypePersonnel) {
		/*
		 * 0 ==> Transferts depart
		 * 1 ==> Personnel de scolarite
		 * 2 ==> Autres
		 * */
		String ret;
		if (idTypePersonnel == 1)
			ret = "Direction des etudes";
		else if (idTypePersonnel == 2)
			ret = "Gestionnaire secretariat pedagogique";
		else
			ret = "";
		return ret;
	}

	private String getValidationOuCandidature(Integer idValidationOuCandidature) {
		/**
		 * Validation ou candidature
		 * 0 ==> non specifie
		 * 1 ==> oui
		 * 2 ==> non
		 */
		String ret;
		if (idValidationOuCandidature == 0)
			ret = "Non specifie";
		else if (idValidationOuCandidature == 1)
			ret = "Oui";
		else
			ret = "Non";
		return ret;
	}

	/**
	 *
	 */
	public void exportDemandeTransferts() {
		if (logger.isDebugEnabled())
			logger.debug("exportDemandeTransferts()");
		setSource("D");
		String typeExport = "xls";
		String fileName = "listeDemandesTransfertsDepart" + "." + typeExport;

		List<PersonnelComposante> lPc=null;
		List<EtudiantRef> lEtu=new ArrayList<EtudiantRef>();
		String chaineComposante=null;
		try {
			lPc = getDomainService().getListeComposantesByUidAndSourceAndAnnee(getSessionController().getCurrentUser().getLogin(), getSource(), getSessionController().getCurrentAnnee());
			if(lPc!=null && !lPc.isEmpty())
			{
				for(PersonnelComposante pc : lPc)
				{
					chaineComposante+=pc.getCodeComposante()+",";
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}

		List<EtudiantRef> lEtu2 = getDomainService().getAllDemandesTransfertsByAnnee(this.getSessionController().getCurrentAnnee(), source);
		if(lEtu2!=null)
		{
			for(EtudiantRef etu : lEtu2)
			{
				try {
					if((lPc!=null
							&& chaineComposante!=null
							&& !"".equals(chaineComposante)
							&& chaineComposante.contains(etu.getComposante())
					) || getSessionController().getCurrentUser().isAdmin())
						lEtu.add(etu);
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		List<EtudiantRefExcel> listeEtudiantRefExcel = new ArrayList<EtudiantRefExcel>();

		for (EtudiantRef etu : lEtu)
		{
			if (logger.isDebugEnabled())
				logger.debug("etu ==>"+etu.getNumeroEtudiant()+"-----"+etu.getNomPatronymique());
			AdresseRef adresse = new AdresseRef();
			adresse.setLibAd1(etu.getAdresse().getLibAd1()+" ("+etu.getAdresse().getCodeCommune()+")");
			adresse.setCodeCommune(etu.getAdresse().getCodeCommune());
			adresse.setEmail(etu.getAdresse().getEmail());
			EtudiantRefExcel excel = new EtudiantRefExcel();
			DateFormat dfl = DateFormat.getDateTimeInstance(DateFormat.SHORT , DateFormat.SHORT);
			excel.setDateDeLaDemandeTransfert(dfl.format(etu.getTransferts().getDateDemandeTransfert()));
			excel.setNumeroEtudiant(etu.getNumeroEtudiant());
			excel.setNomPatronymique(etu.getNomPatronymique());
			excel.setPrenom1(etu.getPrenom1());
			excel.setNumeroIne(etu.getNumeroIne());
			SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
			excel.setDateNaissance(formater.format(etu.getDateNaissance()));
			excel.setAdresse(adresse);
			excel.setEtatDuDossier(this.getLibEtatDossier(etu.getTransferts().getTemoinTransfertValide()));
			excel.setComposante(etu.getComposante());
			excel.setDerniereIaInscription(etu.getLibEtapePremiereLocal());
			excel.setOdf(etu.getTransferts().getOdf());
			excel.setUniversiteAccueil(getDomainServiceScolarite().getEtablissementByRne(etu.getTransferts().getRne()));

			if (excel.getOdf() == null)
				excel.setLibelleVET(etu.getTransferts().getLibelleTypeDiplome());
			else
				excel.setLibelleVET(excel.getOdf().getLibVersionEtape());

			Avis dernierAvis = getDomainService().getDernierAvisFavorable(etu.getNumeroEtudiant(),getSessionController().getCurrentAnnee());
			if (dernierAvis != null && dernierAvis.getIdEtatDossier() !=null && dernierAvis.getId() != 0)
			{
				dernierAvis.setLibEtatDossier(getDomainService().getEtatDossierById(dernierAvis.getIdEtatDossier()).getLibelleLongEtatDossier());
				dernierAvis.setLibLocalisationDossier(getDomainService().getLocalisationDossierById(dernierAvis.getIdLocalisationDossier()).getLibelleLongLocalisationDossier());
				dernierAvis.setLibDecisionDossier(getDomainService().getDecisionDossierById(dernierAvis.getIdDecisionDossier()).getLibelleLongDecisionDossier());
			}
			excel.setAvis(dernierAvis);

			listeEtudiantRefExcel.add(excel);
		}
		List<String> colonnesChoisies = new ArrayList<String>();
		getFileGeneratorService().conventionFile(listeEtudiantRefExcel,	typeExport, fileName, colonnesChoisies);
	}

	private String getLibEtatDossier(Integer idEtatDossier) {
		/**
		 * Temoin de transferts valide null Pas valider 1 Valider 2 Avis
		 * favorable ou defavorable (authorise l'impression depuis la partie
		 * etudiant)
		 */
		String ret;
		if (idEtatDossier == 0)
			ret = "Dossier non traite";
		else if (idEtatDossier == 1)
			ret = "Dossier en cours de traitement";
		else
			ret = "Dossier traite";
		return ret;
	}

	public void deverouillerDemandeTransfert()
	{
		if (logger.isDebugEnabled())
			logger.debug("public void deverouillerDemandeTransfert() ");
		this.currentDemandeTransferts.getTransferts().setTemoinTransfertValide(1);
		this.currentDemandeTransferts.getTransferts().setTemoinOPIWs(null);
		this.currentDemandeTransferts.getTransferts().setTemoinRetourTransfertAccueil(0);
		this.addDemandeTransferts();
		String summary = getString("ENREGISTREMENT.DEVEROUILLER_DEMANDE_TRANSFERT");
		String detail = getString("ENREGISTREMENT.DEVEROUILLER_DEMANDE_TRANSFERT");
		Severity severity = FacesMessage.SEVERITY_WARN;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public void synchroOpi()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("##################################### exlueEtpOpi #################################################");
			logger.debug("exlueEtpOpi --> "+exclueEtpOpi);
			logger.debug("#######################################################################################################################");
			logger.debug("##################################### exclueBacOpi #################################################");
			logger.debug("exclueBacOpi --> "+exclueBacOpi);
			logger.debug("#######################################################################################################################");
		}
		List<IndOpi> opis = new ArrayList<IndOpi>();
		List<IndOpi> reinscription = new ArrayList<IndOpi>();
		Correspondance correspondance = new Correspondance();

		/*Test si il s'agit d'un primo ou d'une réinscription*/
		for(IndOpi io : selectedOpis)
		{
			IdentifiantEtudiant identifiantEtudiant = getDomainServiceScolarite().getIdentifiantEtudiantByIne(io.getCodNneIndOpi(), io.getCodCleNneIndOpi());

			if(identifiantEtudiant==null)
			{
				if (logger.isDebugEnabled()) {
					logger.debug("##################################### !!! identifiantEtudiant == null!!! #################################################");
					logger.debug("io.toString() --> "+io.toString());
					logger.debug("#######################################################################################################################");
				}
				opis.add(io);
			}
			else
			{
				if (logger.isDebugEnabled()) {
					logger.debug("##################################### !!! identifiantEtudiant !!! #################################################");
					logger.debug("identifiantEtudiant.toString() --> "+identifiantEtudiant.toString());
					logger.debug("#######################################################################################################################");
				}
				if(identifiantEtudiant.getCodInd()!=null && identifiantEtudiant.getCodEtu()!=null)
				{
					io.setCodInd(identifiantEtudiant.getCodInd());
					io.setCodEtuLpa(identifiantEtudiant.getCodEtu());
					reinscription.add(io);
				}
			}

		}

		List<IndOpi> primoInscription = this.getOpiByFiltre(opis,true);
		reinscription = this.getOpiByFiltre(reinscription,true);

		List<IndOpi> listeErreursPrimoInscription;
		List<IndOpi> listeErreursReinscription;

		if(primoInscription !=null && !primoInscription.isEmpty())
		{
			listeErreursPrimoInscription = getDomainServiceScolarite().synchroOpi(primoInscription);
			for(IndOpi opi : primoInscription)
			{
				if (logger.isDebugEnabled()) {
					logger.debug("##################################### !!! INTEGRATION OPI APOGEE !!! #################################################");
					logger.debug("opi.getVoeux().getCodEtp() --> "+opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi()+" - "+opi.getLibNomPatIndOpi()+" - "+opi.getLibPr1IndOpi());
					logger.debug("#######################################################################################################################");
				}
				getDomainService().updateIndOpi(opi);
				if (logger.isDebugEnabled()) {
					logger.debug("##################################### !!! Primo inscription !!! #################################################");
					logger.debug("opi.getVoeux().getCodEtp() --> "+opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi()+" - "+opi.getLibNomPatIndOpi()+" - "+opi.getLibPr1IndOpi());
					logger.debug("#######################################################################################################################");
				}
			}
			if(listeErreursPrimoInscription!=null && !listeErreursPrimoInscription.isEmpty() && listeErreursPrimoInscription.size()==primoInscription.size())
			{
				for(IndOpi opi : listeErreursPrimoInscription)
				{
					if (logger.isDebugEnabled()) {
						logger.debug("##################################### !!! ERREUR OPI APOGEE - SYNCHRO.OPI_ECHEC !!! #################################################");
						logger.debug("opi.getVoeux().getCodEtp() --> "+opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi()+" - "+opi.getLibNomPatIndOpi()+" - "+opi.getLibPr1IndOpi());
						logger.debug("#######################################################################################################################");
					}
					opi.setSynchro(0);
					getDomainService().updateIndOpi(opi);
				}
				String summary = getString("SYNCHRO.OPI_ECHEC");
				String detail = getString("SYNCHRO.OPI_ECHEC");
				Severity severity = FacesMessage.SEVERITY_ERROR;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			}
			else if(listeErreursPrimoInscription!=null && !listeErreursPrimoInscription.isEmpty() && listeErreursPrimoInscription.size()!=primoInscription.size())
			{
				for(IndOpi opi : listeErreursPrimoInscription)
				{
					if (logger.isDebugEnabled()) {
						logger.debug("##################################### !!! ERREUR OPI APOGEE - SYNCHRO.OPI_PARTIELLE !!! #################################################");
						logger.debug("opi.getVoeux().getCodEtp() --> "+opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi()+" - "+opi.getLibNomPatIndOpi()+" - "+opi.getLibPr1IndOpi());
						logger.debug("#######################################################################################################################");
					}
					opi.setSynchro(0);
					getDomainService().updateIndOpi(opi);
				}
				String summary = getString("SYNCHRO.OPI_PARTIELLE");
				String detail = getString("SYNCHRO.OPI_PARTIELLE");
				Severity severity = FacesMessage.SEVERITY_WARN;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			}
			else
			{
				if (logger.isDebugEnabled())
					logger.debug("##################################### !!! SYNCHRO.OPI_OK !!! #################################################");
				for(IndOpi opi : primoInscription)
				{
					if (logger.isDebugEnabled()) {
						logger.debug("##################################### !!! OPI APOGEE - SYNCHRO.OPI_OK !!! #################################################");
						logger.debug("opi.getVoeux().getCodEtp() --> "+opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi()+" - "+opi.getLibNomPatIndOpi()+" - "+opi.getLibPr1IndOpi());
						logger.debug("#######################################################################################################################");
					}
					//					setTexteInterditNiveau2("");
					setTexteInterditNiveau3("");

										/*
					* controlle des interdit APB (postbac)
					*
			 		*/
					List<Interdit> listeInterditsNiveau3;
					List<DatasExterne> listeDatasEterneNiveau3 = null;
					WebService currentWsPostBac = getDomainService().getWebServiceByCode("postbac");
					if(getSessionController().isUseWsPostBac() && currentWsPostBac!=null)
					{
						Integer etatConnexion;
						Object tabReturn[] = Fonctions.appelWSAuth(currentWsPostBac.getUrl(),
								currentWsPostBac.getIdentifiant(),
								currentWsPostBac.getPwd(),
								currentWsPostBac.getNomClassJava(),
								currentWsPostBac.getNomMethodeJavaGetById(),
								"arrayList",
								getSessionController().getTimeOutConnexionWs(),
								opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi());

						listeInterditsNiveau3 = (List<Interdit>) tabReturn[0];
						etatConnexion = (Integer) tabReturn[1];

						if (logger.isDebugEnabled()) {
							logger.debug("listeInterditsNiveau3===>" + listeInterditsNiveau3 + "<===");
							logger.debug("etatConnexion===>" + etatConnexion + "<===");
						}

						if(etatConnexion==1)
							listeDatasEterneNiveau3 = getSessionController().convertListInterditsToListDatasExterne(listeInterditsNiveau3);

					} else
						listeDatasEterneNiveau3 = getDomainService().getAllDatasExterneByIdentifiantAndNiveau(opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi(), 3);

					//List<DatasExterne> listeDatasEterneNiveau3 = getDomainService().getAllDatasExterneByIdentifiantAndNiveau(opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi(), 3);
					this.currentDemandeTransferts = getDomainService().getDemandeTransfertByAnneeAndNumeroIneAndSource(opi.getCodNneIndOpi()+""+opi.getCodCleNneIndOpi(), opi.getAnnee());

					if(listeDatasEterneNiveau3 !=null)
						for(DatasExterne lInterditNiveau3 : listeDatasEterneNiveau3)
							this.texteInterditNiveau3 = lInterditNiveau3.getLibInterdit();

					if(listeDatasEterneNiveau3!=null && !listeDatasEterneNiveau3.isEmpty())
					{
						opi.setSynchro(4);
						getDomainService().updateIndOpi(opi);

						String decision;
						if(opi.getVoeux().getCodDecVeu()!=null && "F".equals(opi.getVoeux().getCodDecVeu()))
							decision="Favorable";
						else
							decision="Défavorable";

						String sujet = getString("SYNCHRO.MAIL.PRIMO.SUJET");
						String body = getString("SYNCHRO.MAIL.PRIMO.BODY",
								opi.getLibPr1IndOpi(),
								opi.getLibNomPatIndOpi(),
								decision,
								this.texteInterditNiveau3);

						this.envoiMail(opi, sujet, body, correspondance);
					}
					else
					{
						opi.setSynchro(1);
						getDomainService().updateIndOpi(opi);

						String decision;
						if(opi.getVoeux().getCodDecVeu()!=null && "F".equals(opi.getVoeux().getCodDecVeu()))
							decision="Favorable";
						else
							decision="Défavorable";

						String sujet = getString("SYNCHRO.MAIL.PRIMO.SUJET");
						String body = getString("SYNCHRO.MAIL.PRIMO.BODY",
								opi.getLibPr1IndOpi(),
								opi.getLibNomPatIndOpi(),
								decision,
								opi.getNumeroOpi());
						this.envoiMail(opi, sujet, body, correspondance);
					}
					//Ajout d'une nouvelle correspondance
					if(this.currentDemandeTransferts!=null)
					{
						this.addCorrespondance(correspondance);
						getDomainService().addDemandeTransferts(this.currentDemandeTransferts);
					}
					//Fin d'ajout d'une nouvelle correspondance
				}
				String summary = getString("SYNCHRO.OPI_OK");
				String detail = getString("SYNCHRO.OPI_OK");
				Severity severity = FacesMessage.SEVERITY_INFO;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
			}
		}
		else if(reinscription !=null && !reinscription.isEmpty())
		{
			listeErreursReinscription = getDomainServiceScolarite().synchroOpi(reinscription);
			for(IndOpi opi : reinscription)
			{
				if (logger.isDebugEnabled()) {
					logger.debug("##################################### !!! Réinscription !!! #################################################");
					logger.debug("opi.getVoeux().getCodEtp() --> "+opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi()+" - "+opi.getLibNomPatIndOpi()+" - "+opi.getLibPr1IndOpi());
					logger.debug("reinscription.size() --> "+reinscription.size());
					logger.debug("listeErreursReinscription.size() --> "+listeErreursReinscription.size());
					logger.debug("#######################################################################################################################");
				}

			}
			if(listeErreursReinscription!=null && !listeErreursReinscription.isEmpty() && listeErreursReinscription.size()==reinscription.size())
			{
				for(IndOpi opi : listeErreursReinscription)
				{
					if (logger.isDebugEnabled()) {
						logger.debug("##################################### !!! ERREUR OPI APOGEE - SYNCHRO.OPI_ECHEC !!! #################################################");
						logger.debug("opi.getVoeux().getCodEtp() --> "+opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi()+" - "+opi.getLibNomPatIndOpi()+" - "+opi.getLibPr1IndOpi());
						logger.debug("#######################################################################################################################");
					}
					opi.setSynchro(0);
					getDomainService().updateIndOpi(opi);
				}
				String summary = getString("SYNCHRO.OPI_ECHEC");
				String detail = getString("SYNCHRO.OPI_ECHEC");
				Severity severity = FacesMessage.SEVERITY_ERROR;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			}
			else if(listeErreursReinscription!=null && !listeErreursReinscription.isEmpty() && listeErreursReinscription.size()!=reinscription.size())
			{
				for(IndOpi opi : listeErreursReinscription)
				{
					if (logger.isDebugEnabled()) {
						logger.debug("##################################### !!! ERREUR OPI APOGEE - SYNCHRO.OPI_PARTIELLE !!! #################################################");
						logger.debug("opi.getVoeux().getCodEtp() --> "+opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi()+" - "+opi.getLibNomPatIndOpi()+" - "+opi.getLibPr1IndOpi());
						logger.debug("#######################################################################################################################");
					}
					opi.setSynchro(0);
					getDomainService().updateIndOpi(opi);
				}
				String summary = getString("SYNCHRO.OPI_PARTIELLE");
				String detail = getString("SYNCHRO.OPI_PARTIELLE");
				Severity severity = FacesMessage.SEVERITY_WARN;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			}
			else
			{
				if (logger.isDebugEnabled())
					logger.debug("##################################### !!! SYNCHRO.OPI_OK !!! #################################################");
				for(IndOpi opi : reinscription)
				{
					if (logger.isDebugEnabled()) {
						logger.debug("##################################### !!! OPI APOGEE - SYNCHRO.OPI_OK !!! #################################################");
						logger.debug("opi.getVoeux().getCodEtp() --> "+opi.getCodNneIndOpi()+opi.getCodCleNneIndOpi()+" - "+opi.getLibNomPatIndOpi()+" - "+opi.getLibPr1IndOpi());
						logger.debug("#######################################################################################################################");
					}

					opi.setSynchro(1);
					getDomainService().updateIndOpi(opi);

					this.currentDemandeTransferts = getDomainService().getDemandeTransfertByAnneeAndNumeroIneAndSource(opi.getCodNneIndOpi() + "" + opi.getCodCleNneIndOpi(), opi.getAnnee(), "A");
					String decision;
					if(opi.getVoeux().getCodDecVeu()!=null && "F".equals(opi.getVoeux().getCodDecVeu()))
						decision="Favorable";
					else
						decision="Défavorable";
					String sujet = getString("SYNCHRO.MAIL.REINS.SUJET");
					String body = getString("SYNCHRO.MAIL.REINS.BODY",
							opi.getLibPr1IndOpi(),
							opi.getLibNomPatIndOpi(),
							decision,
							opi.getCodEtuLpa().toString().trim());
					this.envoiMail(opi, sujet, body, correspondance);
					//Ajout d'une nouvelle correspondance
					if(this.currentDemandeTransferts!=null)
					{
						this.addCorrespondance(correspondance);
						getDomainService().addDemandeTransferts(this.currentDemandeTransferts);
					}

					//Fin d'ajout d'une nouvelle correspondance
				}

				String summary = getString("SYNCHRO.OPI_OK");
				String detail = getString("SYNCHRO.OPI_OK");
				Severity severity = FacesMessage.SEVERITY_INFO;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
			}

		}
		else
		{
			String summary = getString("SYNCHRO.OPI_AUCUN");
			String detail = getString("SYNCHRO.OPI_AUCUN");
			Severity severity=FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
		transfertDataModelOpi=null;
	}

	public void envoiMail(IndOpi opi, String sujet, String body, Correspondance correspondance){
		try {
			getSmtpService().send(new InternetAddress(opi.getAdrMailOpi()), sujet, body, body);
		}
		catch (AddressException e)
		{
			logger.error(e);
			String summary = getString("ERREUR.ENVOI_MAIL");
			String detail = getString("ERREUR.ENVOI_MAIL");
			Severity severity = FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
		if(this.currentDemandeTransferts!=null)
		{
			try {
				correspondance.setEtudiant(this.currentDemandeTransferts);
				correspondance.setAuteur(getSessionController().getCurrentUser().getDisplayName());
				correspondance.setDateSaisie(new Date());
				correspondance.setTitre(sujet);
				correspondance.setMsg(body);
			} catch (Exception e1) {
				logger.error(e1);
			}
		}
	}

	private List<IndOpi> getOpiByFiltre(List<IndOpi> opis, boolean synchro)
	{
		List<IndOpi> listeSynchroScolarite = new ArrayList<IndOpi>();
		if(opis !=null && !opis.isEmpty())
		{
			for(int i=0;i<opis.size();i++)
			{
				if(((opis.get(i).getSynchro()==0 && !exclueEtpOpi.contains(opis.get(i).getVoeux().getCodEtp())) || "".equals(exclueEtpOpi))
						&& ((opis.get(i).getSynchro()==0 && !exclueBacOpi.contains(opis.get(i).getCodBac())) || "".equals(exclueBacOpi)))
				{
					opis.get(i).setSynchro(1);
					listeSynchroScolarite.add(opis.get(i));
				}
				else
				{
					if (logger.isDebugEnabled()) {
						logger.debug("##################################### !!! EXLUE !!! exlueEtpOpi #################################################");
						logger.debug("opi.getVoeux().getCodEtp() --> "+opis.get(i).getCodNneIndOpi()+opis.get(i).getCodCleNneIndOpi()+" - "+opis.get(i).getLibNomPatIndOpi()+" - "+opis.get(i).getLibPr1IndOpi()+" - "+opis.get(i).getVoeux().getCodEtp());
						logger.debug("#######################################################################################################################");
						logger.debug("##################################### !!! EXLUE !!! exclueBacOpi #################################################");
						logger.debug("opis.get(i).getCodBac() --> "+opis.get(i).getCodNneIndOpi()+opis.get(i).getCodCleNneIndOpi()+" - "+opis.get(i).getLibNomPatIndOpi()+" - "+opis.get(i).getLibPr1IndOpi()+" - "+opis.get(i).getCodBac());
						logger.debug("#######################################################################################################################");
					}
					if(opis.get(i).getSynchro()==0)
						opis.get(i).setSynchro(2);
					getDomainService().updateIndOpi(opis.get(i));
				}
			}
		}
		return listeSynchroScolarite;
	}

	public List<SelectItem> getListePays()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("public List<SelectItem> getListePays()");
			logger.debug("getDomainServiceWSScolarite().getListePays()");
		}
		List<SelectItem> listePays = new ArrayList<SelectItem>();
		List<TrPaysDTO> listePaysDTO = getDomainServiceScolarite().getListePays();
		if (listePaysDTO != null)
		{
			for (TrPaysDTO p : listePaysDTO)
			{
				SelectItem option = new SelectItem(p.getCodePay(), p.getLibPay());
				listePays.add(option);
			}
			Collections.sort(listePays, new ComparatorSelectItem());
			return listePays;
		}
		else
		{
			SelectItem option = new SelectItem("", "");
			listePays.add(option);
			return listePays;
		}
	}

	public void addParametre() {
		getDomainService().addParametre(this.parametreAppli);
		String summary = getString("ENREGISTREMENT.ETAT_APPLICATION");
		String detail = getString("ENREGISTREMENT.ETAT_APPLICATION");
		Severity severity = FacesMessage.SEVERITY_INFO;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	@SuppressWarnings("unused")
	public String updateConfiguration()
	{
		Parametres choixDuVoeuParComposante = getDomainService().getParametreByCode("choixDuVoeuParComposante");
		if(choixDuVoeuParComposante!=null)
		{
			choixDuVoeuParComposante.setBool(getSessionController().isChoixDuVoeuParComposante());
			choixDuVoeuParComposante = getDomainService().updateConfiguration(choixDuVoeuParComposante);

			WsPub ws = getDomainService().getWsPubByRneAndAnnee(getSessionController().getRne(), getSessionController().getCurrentAnnee());

			if(ws!=null) {
				if (choixDuVoeuParComposante.isBool() != ws.isChoixDuVoeuParComposante()) {
					ws.setChoixDuVoeuParComposante(choixDuVoeuParComposante.isBool());
					getDomainService().updateWsPub(ws);
				}
			}
			getSessionController().setChoixDuVoeuParComposante(choixDuVoeuParComposante.isBool());
		}
		else
			getSessionController().setChoixDuVoeuParComposante(true);

		Parametres maj_odf_auto = getDomainService().getParametreByCode("maj_odf_auto");
		if(maj_odf_auto!=null)
		{
			maj_odf_auto.setBool(getSessionController().isMajOdfAuto());
			maj_odf_auto = getDomainService().updateConfiguration(maj_odf_auto);
			getSessionController().setMajOdfAuto(maj_odf_auto.isBool());
		}
		else
			getSessionController().setMajOdfAuto(true);

		Parametres reload_demande_transferts_depart_echec_auto = getDomainService().getParametreByCode("reload_demande_transferts_depart_echec_auto");
		if(reload_demande_transferts_depart_echec_auto!=null)
		{
			reload_demande_transferts_depart_echec_auto.setBool(getSessionController().isReloadDemandeTransfertsDepartEchecAuto());
			reload_demande_transferts_depart_echec_auto = getDomainService().updateConfiguration(reload_demande_transferts_depart_echec_auto);
			getSessionController().setReloadDemandeTransfertsDepartEchecAuto(reload_demande_transferts_depart_echec_auto.isBool());
		}
		else
			getSessionController().setReloadDemandeTransfertsDepartEchecAuto(true);

		Parametres reload_demande_transferts_accueil_echec_auto = getDomainService().getParametreByCode("reload_demande_transferts_accueil_echec_auto");
		if(reload_demande_transferts_accueil_echec_auto!=null)
		{
			reload_demande_transferts_accueil_echec_auto.setBool(getSessionController().isReloadDemandeTransfertsAccueilEchecAuto());
			reload_demande_transferts_accueil_echec_auto = getDomainService().updateConfiguration(reload_demande_transferts_accueil_echec_auto);
			getSessionController().setReloadDemandeTransfertsAccueilEchecAuto(reload_demande_transferts_accueil_echec_auto.isBool());
		}
		else
			getSessionController().setReloadDemandeTransfertsAccueilEchecAuto(true);


		Parametres relance_depart_sva = getDomainService().getParametreByCode("relance_depart_sva");
		if(relance_depart_sva!=null)
		{
			relance_depart_sva.setBool(getSessionController().isUseRelanceDepartPersonnelConcerneSVA());
			relance_depart_sva = getDomainService().updateConfiguration(relance_depart_sva);
			getSessionController().setUseRelanceDepartPersonnelConcerneSVA(relance_depart_sva.isBool());
		}
		else
			getSessionController().setUseRelanceDepartPersonnelConcerneSVA(true);

		Parametres relance_accueil_sva = getDomainService().getParametreByCode("relance_accueil_sva");
		if(relance_accueil_sva!=null)
		{
			relance_accueil_sva.setBool(getSessionController().isUseRelanceAccueilPersonnelConcerneSVA());
			relance_accueil_sva = getDomainService().updateConfiguration(relance_accueil_sva);
			getSessionController().setUseRelanceAccueilPersonnelConcerneSVA(relance_accueil_sva.isBool());
		}
		else
			getSessionController().setUseRelanceAccueilPersonnelConcerneSVA(true);

		Parametres relance_resume_sva_auto = getDomainService().getParametreByCode("relance_resume_sva");
		if(relance_resume_sva_auto!=null)
		{
			relance_resume_sva_auto.setBool(getSessionController().isUseRelanceResumeSVA());
			relance_resume_sva_auto = getDomainService().updateConfiguration(relance_resume_sva_auto);
			getSessionController().setUseRelanceResumeSVA(relance_resume_sva_auto.isBool());
		}
		else
			getSessionController().setUseRelanceResumeSVA(true);

		Parametres planning_fermetures_auto = getDomainService().getParametreByCode("planning_fermetures");
		if(planning_fermetures_auto!=null)
		{
			planning_fermetures_auto.setBool(getSessionController().isPlanningFermeturesAuto());
			planning_fermetures_auto = getDomainService().updateConfiguration(planning_fermetures_auto);
			getSessionController().setPlanningFermeturesAuto(planning_fermetures_auto.isBool());
		}
		else
			getSessionController().setPlanningFermeturesAuto(true);

		Parametres ajout_etablissement_manuellement = getDomainService().getParametreByCode("ajout_etablissement_manuellement");
		boolean valid = true;
		if(ajout_etablissement_manuellement!=null && getSessionController().getAjoutEtablissementManuellement()!="")
		{
			Map <String, String> maps;
			if(getSessionController().isActivEtablissementManuellement())
			{
				maps = Fonctions.stringSplitToMap(getSessionController().getAjoutEtablissementManuellement(), ",", true);
				if (maps != null) {
					for (Map.Entry<String, String> entry : maps.entrySet()) {
						if (logger.isDebugEnabled())
							logger.debug("foreach maps===>" + entry.getKey() + "/" + entry.getValue() + "<===");
						TrEtablissementDTO testEtab = getDomainServiceScolarite().getEtablissementByRne(entry.getKey());
						if (testEtab == null) {
							if (logger.isDebugEnabled())
								logger.debug("Etablissement inconnu dans le SI scol===>" + entry.getKey() + "/" + entry.getValue() + "<===");
							valid = false;
						}
					}
				} else
					valid = false;
			}
			ajout_etablissement_manuellement.setCommentaire(getSessionController().getAjoutEtablissementManuellement());
			ajout_etablissement_manuellement.setBool(getSessionController().isActivEtablissementManuellement());
			if(valid) {
				ajout_etablissement_manuellement = getDomainService().updateConfiguration(ajout_etablissement_manuellement);
				String summary = getString("ENREGISTREMENT.CONFIGURATION");
				String detail = getString("ENREGISTREMENT.CONFIGURATION");
				Severity severity = FacesMessage.SEVERITY_INFO;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(severity, summary, detail));
				context.getExternalContext().getFlash().setKeepMessages(true);
			}
			else
			{
				String summary = "Un des établissements saisies est inconnu dans la base de données scolarité";
				String detail = "Un des établissements saisies est inconnu dans la base de données scolarité";
				Severity severity = FacesMessage.SEVERITY_ERROR;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(severity, summary, detail));
				context.getExternalContext().getFlash().setKeepMessages(true);
			}
			getSessionController().setAjoutEtablissementManuellement(ajout_etablissement_manuellement.getCommentaire());


		}
		else
		{
			getSessionController().setAjoutEtablissementManuellement("");
			getSessionController().setActivEtablissementManuellement(false);
			getDomainService().updateConfiguration(ajout_etablissement_manuellement);
			String summary = getString("ENREGISTREMENT.CONFIGURATION");
			String detail = getString("ENREGISTREMENT.CONFIGURATION");
			Severity severity = FacesMessage.SEVERITY_INFO;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(severity, summary, detail));
			context.getExternalContext().getFlash().setKeepMessages(true);
		}

		Parametres ws_bu = getDomainService().getParametreByCode("ws_bu");
		if(ws_bu!=null)
		{
			ws_bu.setBool(getSessionController().isUseWsBu());
			ws_bu = getDomainService().updateConfiguration(ws_bu);
			getSessionController().setUseWsBu(ws_bu.isBool());
		}
		else
			getSessionController().setUseWsBu(false);

		Parametres ws_candidatures = getDomainService().getParametreByCode("ws_candidatures");
		if(ws_candidatures!=null)
		{
			ws_candidatures.setBool(getSessionController().isUseWsCandidatures());
			ws_candidatures = getDomainService().updateConfiguration(ws_candidatures);
			getSessionController().setUseWsCandidatures(ws_candidatures.isBool());
		}
		else
			getSessionController().setUseWsCandidatures(false);

		Parametres ws_postbac = getDomainService().getParametreByCode("ws_postbac");
		if(ws_postbac!=null)
		{
			ws_postbac.setBool(getSessionController().isUseWsPostBac());
			ws_postbac = getDomainService().updateConfiguration(ws_postbac);
			getSessionController().setUseWsPostBac(ws_postbac.isBool());
		}
		else
			getSessionController().setUseWsPostBac(false);

		Parametres ajout_super_gestionnaire = getDomainService().getParametreByCode("super_gestionnaire");
		if(ajout_super_gestionnaire!=null)
		{
			ajout_super_gestionnaire.setBool(getSessionController().isUseSuperGestionnaire());
			ajout_super_gestionnaire.setCommentaire(getSessionController().getSuperGestionnaire());
			ajout_super_gestionnaire = getDomainService().updateConfiguration(ajout_super_gestionnaire);
			getSessionController().setUseSuperGestionnaire(ajout_super_gestionnaire.isBool());
			getSessionController().setSuperGestionnaire(ajout_super_gestionnaire.getCommentaire());
		}
		else {
			getSessionController().setUseSuperGestionnaire(false);
			getSessionController().setSuperGestionnaire(null);
		}

		Parametres paramTimeOutConnexionWs = getDomainService().getParametreByCode("time_out_connexion_ws");
		if(paramTimeOutConnexionWs!=null) {
			paramTimeOutConnexionWs.setBool(getSessionController().isUseTimeOutConnexionWs());
			paramTimeOutConnexionWs.setCommentaire(getSessionController().getTimeOutConnexionWs().toString());
			paramTimeOutConnexionWs = getDomainService().updateConfiguration(paramTimeOutConnexionWs);
			getSessionController().setUseTimeOutConnexionWs(paramTimeOutConnexionWs.isBool());
			getSessionController().setTimeOutConnexionWs(Integer.parseInt(paramTimeOutConnexionWs.getCommentaire()));
		}
		else
		{
			getSessionController().setUseTimeOutConnexionWs(false);
			getSessionController().setTimeOutConnexionWs(0);
		}

		return "goToAdministration";
	}

	public String goToInformationAppliDepart()
	{
		if (logger.isDebugEnabled())
			logger.debug("public String goToInformationAppliDepart()");
		setSource("D");
		this.parametreAppli = getDomainService().getParametreByCode("informationDepart");
		return "goToInformationAppli";
	}

	public String goToInformationAppliAccueil()
	{
		if (logger.isDebugEnabled())
			logger.debug("public String goToInformationAppliAccueil()");
		setSource("A");
		this.parametreAppli = getDomainService().getParametreByCode("informationAccueil");
		return "goToInformationAppli";
	}

	public String goToFermetureAppliDepart()
	{
		if (logger.isDebugEnabled())
			logger.debug("goToFermetureAppliDepart");
		setSource("D");
		this.parametreAppli = getDomainService().getParametreByCode("ouvertureDepart");
		return "goToFermetureAppli";
	}

	public String goToFermetureAppliAccueil()
	{
		if (logger.isDebugEnabled())
			logger.debug("goToFermetureAppliAccueil");
		setSource("A");
		this.parametreAppli = getDomainService().getParametreByCode("ouvertureAccueil");
		return "goToFermetureAppli";
	}

	public void changeAnnee() {
		String summary = getString("ENREGISTREMENT.CHANGEMENT_ANNEE");
		String detail = getString("ENREGISTREMENT.CHANGEMENT_ANNEE");
		Severity severity = FacesMessage.SEVERITY_INFO;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public List<Integer> getListeAnnees() {
		return getDomainService().getListeAnnees();
	}

	public List<Integer> getListeAnneesWithoutCurrentAnnee() {
		List<Integer> tmp = getDomainService().getListeAnnees();
		if(tmp!=null)
			tmp.remove(getSessionController().getCurrentAnnee());

		if(tmp!=null && tmp.isEmpty())
			tmp=null;

		return tmp;
	}

	public String goToChangeAnnee() {
		return "goToChangeAnnee";
	}

	public String updateDefautCodeSize()
	{
		if (this.selectedCodeSizeAnnee != null)
		{
			this.selectedCodeSizeAnnee.setDefaut(true);
			getDomainService().updateDefautCodeSize(this.selectedCodeSizeAnnee);
			getSessionController().setDefaultCodeSize(this.selectedCodeSizeAnnee);
			getSessionController().setCurrentAnnee(this.selectedCodeSizeAnnee.getAnnee());
			String summary = getString("ENREGISTREMENT.CODE_SIZE_PAR_DEFAUT");
			String detail = getString("ENREGISTREMENT.CODE_SIZE_PAR_DEFAUT");
			Severity severity = FacesMessage.SEVERITY_INFO;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(severity, summary, detail));
			context.getExternalContext().getFlash().setKeepMessages(true);
		}
		else
		{
			String summary = getString("ERREUR.CODE_SIZE_PAR_DEFAUT");
			String detail = getString("ERREUR.CODE_SIZE_PAR_DEFAUT");
			Severity severity = FacesMessage.SEVERITY_ERROR;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(severity, summary, detail));
			context.getExternalContext().getFlash().setKeepMessages(true);
		}
		return "goToChangeCodeSize";

	}

	public void addCodeSize() {
		if (logger.isDebugEnabled()) {
			logger.debug("this.selectedCodeSizeAnnee.getAnnee() --> "+ this.selectedCodeSizeAnnee.getAnnee());
			logger.debug("this.codeSizeAnnee.getAnnee() --> "+ this.codeSizeAnnee.getAnnee());
		}
		if (this.selectedCodeSizeAnnee != null
				&& this.selectedCodeSizeAnnee.getAnnee() != null
				&& this.selectedCodeSizeAnnee.getAnnee().equals(this.codeSizeAnnee.getAnnee()))
			this.codeSizeAnnee.setDefaut(true);
		else
			this.codeSizeAnnee.setDefaut(false);
		this.codeSizeAnnee.setCode(this.codeSizeAnnee.getCode().toUpperCase());
		getDomainService().addCodeSize(this.codeSizeAnnee);
		String summary = getString("ENREGISTREMENT.CODE_SIZE");
		String detail = getString("ENREGISTREMENT.CODE_SIZE");
		Severity severity = FacesMessage.SEVERITY_INFO;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		this.codeSizeAnnee = new CodeSizeAnnee();
		getSessionController().isDefaultCodeSizeAnnee();
	}

	public String goToChangeCodeSize() {
		this.selectedCodeSizeAnnee = getDomainService().getCodeSizeDefaut();
		if (this.selectedCodeSizeAnnee == null)
			this.selectedCodeSizeAnnee = new CodeSizeAnnee();
		this.codeSizeAnnee = new CodeSizeAnnee();
		return "goToChangeCodeSize";
	}

	public String goToTransfertsArriveFromDepart() {
		if (logger.isDebugEnabled()) {
			logger.debug("goToTransfertsArrive");
		}
		setSource("D");
		setFilteredEtudiantOpi(null);
		setTransfertDataModelOpi(null);
		setListEntrants(null);
		return "goToTransfertsArrive";
	}

	public String goToTransfertsArriveFromAccueil() {
		if (logger.isDebugEnabled()) {
			logger.debug("goToTransfertsArrive");
		}
		setSource("A");
		setFilteredEtudiantOpi(null);
		setTransfertDataModelOpi(null);
		setListEntrants(null);
		setSelectedmd5(null);
		return "goToTransfertsArrive";
	}

	public String goToWelcome()
	{
		return "goToWelcomeGestionnaire";
	}

	public String goToTransfertsDepart() {
		if (logger.isDebugEnabled())
			logger.debug("goToTransfertsDepart");
		setSource("D");
		setFilteredEtudiantDepart(null);
		setListeTransfertDepartDataModel(null);
		setSelectedmd5(null);
		return "goToTransfertsDepart";
	}

	public String goToValideMasseTransfertsDepart() {
		if (logger.isDebugEnabled())
			logger.debug("goToValideMasseTransfertsDepart");
		setSource("D");
		if(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource())!=null)
		{
			setFilteredEtudiantDepartMultiple(null);
			setListeTransfertDepartDataModel(null);
			setListeAvisByNumeroEtudiantAndAnnee(new ArrayList<Avis>());
			setSelectedFichier(new Fichier());
			setCurrentAvis(new Avis());
			setProgress(null);
			setSwitchTraiteNontraite(false);
			setDroitAvisDefinitifTransfertDepartMultiple(true);
			return "goToValideMasseTransfertsDepart";
		}
		else
			return "goToSignatureParDefautObligatoire";
	}

	public String goToValideMasseTransfertsAccueil() {
		if (logger.isDebugEnabled())
			logger.debug("goToValideMasseTransfertsAccueil");
		setSource("A");
		if(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource())!=null)
		{
			setFilteredEtudiantAccueilMultiple(null);
			setListeTransfertDepartDataModel(null);
			setSelectedFichier(new Fichier());
			setCurrentAccueilDecision(new AccueilDecision());
			setSwitchTraiteNontraite(false);
			return "goToValideMasseTransfertsAccueil";
		}
		else
			return "goToSignatureParDefautObligatoire";
	}

	public String goToTransfertsAccueil() {
		if (logger.isDebugEnabled())
			logger.debug("goToTransfertsAccueil");
		setSource("A");
		sudm=null;
		setFilteredEtudiantAccueil(null);
		setListeTransfertDepartDataModel(null);
		return "goToTransfertsAccueil";
	}

	public String goToCurrentDemandeTransfertsAccueil() {
		if (logger.isDebugEnabled())
			logger.debug("goToCurrentDemandeTransfertsAccueil");

		if (this.currentDemandeTransferts != null)
		{
			this.setChoixDuVoeuParComposanteByPartenaire(getSessionController().isChoixDuVoeuParComposante());
			this.currentDemandeTransferts = getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(this.currentDemandeTransferts.getNumeroEtudiant(), this.currentDemandeTransferts.getAnnee(), this.currentDemandeTransferts.getSource());
			this.currentDemandeTransferts.getAccueil().setSituationUniversitaire(getDomainService().getSituationUniversitaireByNumeroEtudiantAndAnnee(this.currentDemandeTransferts.getNumeroEtudiant(), this.currentDemandeTransferts.getAnnee()));
			setTexteInterditNiveau2("Candidatures : ");
			setTexteInterditNiveau3("");

			List<DatasExterne> listeDatasEterneNiveau2;
			List<Interdit> listeInterditsNiveau2;
			WebService currentWsCandidatures = getDomainService().getWebServiceByCode("candidature");

			if (logger.isDebugEnabled()) {
				logger.debug("getSessionController().isUseWsCandidatures()===>" + getSessionController().isUseWsCandidatures() + "<===");
				logger.debug("currentWsCandidatures===>" + currentWsCandidatures + "<===");
			}

			if(getSessionController().isUseWsCandidatures() && currentWsCandidatures!=null)
			{
				Object tabReturn2[] = Fonctions.appelWSAuth(currentWsCandidatures.getUrl(),
						currentWsCandidatures.getIdentifiant(),
						currentWsCandidatures.getPwd(),
						currentWsCandidatures.getNomClassJava(),
						currentWsCandidatures.getNomMethodeJavaGetById(),
						"arrayList",
						getSessionController().getTimeOutConnexionWs(),
						this.currentDemandeTransferts.getNumeroIne());

				Integer etatConnexion2 = (Integer) tabReturn2[1];
				listeInterditsNiveau2 = (List<Interdit>) tabReturn2[0];

				if (logger.isDebugEnabled()) {
					logger.debug("listeInterditsNiveau2===>" + listeInterditsNiveau2 + "<===");
					logger.debug("etatConnexion2===>" + etatConnexion2 + "<===");
				}

				if(etatConnexion2==1)
					listeDatasEterneNiveau2 = getSessionController().convertListInterditsToListDatasExterne(listeInterditsNiveau2);
				else
					listeDatasEterneNiveau2 = getSessionController().returnWebServiceOffline(getString("WARNING.SERVICE_INDISPONIBLE"));


			} else
				listeDatasEterneNiveau2 = getDomainService().getAllDatasExterneByIdentifiantAndNiveau(this.currentDemandeTransferts.getNumeroIne(), 2);

			if(listeDatasEterneNiveau2 !=null)
				for(DatasExterne lInterditNiveau2 : listeDatasEterneNiveau2)
					this.texteInterditNiveau2 += "<BR /> - "+lInterditNiveau2.getLibInterdit();

			if (logger.isDebugEnabled())
				logger.debug("Liste des interdits de niveau 2-->"+this.texteInterditNiveau2);

			if(listeDatasEterneNiveau2!=null && !listeDatasEterneNiveau2.isEmpty())
				setInterditNiveau2(true);
			else
				setInterditNiveau2(false);

			/*
			* controlle des interdit APB (postbac)
			*
			 */
			List<Interdit> listeInterditsNiveau3;
			List<DatasExterne> listeDatasEterneNiveau3;
			WebService currentWsPostBac = getDomainService().getWebServiceByCode("postbac");
			if(getSessionController().isUseWsPostBac() && currentWsPostBac!=null)
			{
				Integer etatConnexion;
				Object tabReturn[] = Fonctions.appelWSAuth(currentWsPostBac.getUrl(),
						currentWsPostBac.getIdentifiant(),
						currentWsPostBac.getPwd(),
						currentWsPostBac.getNomClassJava(),
						currentWsPostBac.getNomMethodeJavaGetById(),
						"arrayList",
						getSessionController().getTimeOutConnexionWs(),
						this.currentDemandeTransferts.getNumeroIne());

				listeInterditsNiveau3 = (List<Interdit>) tabReturn[0];
				etatConnexion = (Integer) tabReturn[1];

				if (logger.isDebugEnabled()) {
					logger.debug("listeInterditsNiveau3===>" + listeInterditsNiveau3 + "<===");
					logger.debug("etatConnexion===>" + etatConnexion + "<===");
				}

				if(etatConnexion==1)
					listeDatasEterneNiveau3 = getSessionController().convertListInterditsToListDatasExterne(listeInterditsNiveau3);
				else
					listeDatasEterneNiveau3 = getSessionController().returnWebServiceOffline(getString("WARNING.SERVICE_INDISPONIBLE"));

			} else
				listeDatasEterneNiveau3 = getDomainService().getAllDatasExterneByIdentifiantAndNiveau(this.currentDemandeTransferts.getNumeroIne(), 3);


			if(listeDatasEterneNiveau3!=null)
				logger.debug("listeDatasEterneNiveau3-->"+listeDatasEterneNiveau3.size());

			if(listeDatasEterneNiveau3!=null)
				for(DatasExterne lInterditNiveau3 : listeDatasEterneNiveau3)
					this.texteInterditNiveau3 += lInterditNiveau3.getLibInterdit();

			if (logger.isDebugEnabled())
				logger.debug("Liste des interdits de niveau 3-->"+this.texteInterditNiveau3);

			if(listeDatasEterneNiveau3!=null && !listeDatasEterneNiveau3.isEmpty())
				setInterditNiveau3(true);
			else
				setInterditNiveau3(false);

			droitPC = getDomainService().getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante(getCurrentUserLogin(),
					getSource(),
					getSessionController().getCurrentAnnee(),
					this.currentDemandeTransferts.getTransferts().getOdf().getCodeComposante());

			if ("100".equals(this.currentDemandeTransferts.getAdresse().getCodPay()))
				this.setCodePaysItems("100");
			else
				this.setCodePaysItems("99");

			if(currentDemandeTransferts.getTransferts().getOdf()!=null)
			{
				setCodTypDip(currentDemandeTransferts.getTransferts().getOdf().getCodTypDip());
				setCodeNiveau(currentDemandeTransferts.getTransferts().getOdf().getCodeNiveau());
				setCodeComposante(currentDemandeTransferts.getTransferts().getOdf().getCodeComposante());
				setCodeDiplome(currentDemandeTransferts.getTransferts().getOdf().getCodeDiplome());
				currentOdf=currentDemandeTransferts.getTransferts().getOdf();
			}
			setDeptVide(false);
			setListeLibellesEtape(getListeLibellesEtape());
			setLibelleEtapeVide(false);
			odfDataModel=null;
			currentAccueilAnnee = new AccueilAnnee();
			currentAccueilResultat = new AccueilResultat();
			currentSituationUniv = new SituationUniversitaire();

			if(this.currentDemandeTransferts.getTransferts().getFichier()==null)
			{
				if(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource())!=null)
				{
					this.currentDemandeTransferts.getTransferts().setFichier(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource()));
					setSelectedmd5(this.currentDemandeTransferts.getTransferts().getFichier().getMd5());
				}
				else
				{
					this.currentDemandeTransferts.getTransferts().setFichier(null);
					setSelectedmd5(null);
				}
			}
			else
			{
				setSelectedmd5(this.currentDemandeTransferts.getTransferts().getFichier().getMd5());
			}

			List<TrBac> listeBacDTO = getDomainServiceScolarite().recupererBacOuEquWS(this.currentDemandeTransferts.getAccueil().getCodeBac());
			if (logger.isDebugEnabled())
				logger.debug("existCodeBac---listeBacDTO-->"+listeBacDTO);

			if(listeBacDTO==null)
				this.setExistCodeBac(false);
			else
				this.setExistCodeBac(true);

			if(getSessionController().getRegleGestionTE02()!=null)
				this.verifRepriseEtudes();

			this.currentDemandeTransferts=this.initialiseTransientEtudiantRef(this.currentDemandeTransferts);

			return "goToCurrentDemandeTransfertsAccueil";
		}
		else
		{
			String summary = getString("SELECTION.DEMANDE_TRANSFERT");
			String detail = getString("SELECTION.DEMANDE_TRANSFERT");
			Severity severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			return null;
		}
	}

	public void verifRepriseEtudes()
	{
		Integer tableau[]= new Integer[0];
		if(this.currentDemandeTransferts.getAccueil()!=null && this.currentDemandeTransferts.getAccueil().getSituationUniversitaire()!=null) {
			tableau = new Integer[this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size()];

//			if ("L".equals(this.currentDemandeTransferts.getAccueil().getFrom_source())) {
				for (int i = 0; i < this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size(); i++)
				{
					try {
						tableau[i] = Integer.parseInt(this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().get(i).getLibAccueilAnnee().substring(0, 4));
					}catch (Exception e)
					{
//						logger.error(e);
						tableau[i] = Integer.parseInt(this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().get(i).getAnnee().getLibelle().substring(0, 4));
					}
				}
//			} else if ("P".equals(this.currentDemandeTransferts.getAccueil().getFrom_source())) {
//				for (int i = 0; i < this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size(); i++)
//				{
//					try {
//						tableau[i] = Integer.parseInt(this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().get(i).getLibAccueilAnnee().substring(0, 4));
//					}catch (Exception e)
//					{
//						logger.error(e);
//						tableau[i] = Integer.parseInt(this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().get(i).getAnnee().getLibelle().substring(0, 4));
//					}
//				}
//			} else {
//				String summary = "Impossible de determiner la source de la demande de transfert";
//				String detail = "Impossible de determiner la source de la demande de transfert";
//				Severity severity = FacesMessage.SEVERITY_FATAL;
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
//			}

			if (logger.isDebugEnabled())
				for (int i = 0; i < tableau.length; i++)
					logger.debug("---------- tableau[" + i + "] ------------>" + tableau[i]);

			Arrays.sort(tableau);
		}

		if (logger.isDebugEnabled())
			for(int i=0;i<tableau.length;i++)
				logger.debug("---------- tableau["+i+"] ------------>"+tableau[i]);

		Integer anneeVerifRepriseEtudes = getSessionController().getCurrentAnnee()-getSessionController().getRegleGestionTE02();
		if (logger.isDebugEnabled())
		{
			logger.debug("---------- getSessionController().getCurrentAnnee() ------------>"+getSessionController().getCurrentAnnee());
			//			logger.debug("---------- tableau.length-1 ------------>"+tableau[tableau.length-1]);				
			logger.debug("---------- getSessionController().getRegleGestionTE02() ------------>"+getSessionController().getRegleGestionTE02());
			logger.debug("---------- anneeVerifRepriseEtudes ------------>"+anneeVerifRepriseEtudes);
		}
		setRepriseEtudes(true);
		for(int i=0;i<tableau.length;i++)
		{
			if (logger.isDebugEnabled())
				logger.debug("---------- Boucle tableau["+i+"] ------------>"+tableau[i]);
			if(tableau[i].equals(anneeVerifRepriseEtudes))
			{
				if (logger.isDebugEnabled())
					logger.debug("---------- if(tableau[i].equals(anneeVerifRepriseEtudes)) ------------>"+tableau[i]);
				setRepriseEtudes(false);
				break;
			}
		}
		if (logger.isDebugEnabled())
			if(this.isRepriseEtudes())
				logger.debug("---------- Reprise d'études ------------>"+this.isRepriseEtudes());
			else
				logger.debug("---------- Pas de reprise d'étude ------------>"+this.isRepriseEtudes());
	}

	public String goToCurrentDemandeTransferts() {
		if (logger.isDebugEnabled())
			logger.debug("goToCurrentDemandeTransferts");

		if (this.currentDemandeTransferts != null)
		{

			WsPub wp = getDomainService().getWsPubByRneAndAnnee(this.currentDemandeTransferts.getTransferts().getRne(), getSessionController().getCurrentAnnee());

			if (logger.isDebugEnabled()) {
				logger.debug("===>" + wp + "<===");
				logger.debug("libelleDiplomeVide===>" + libelleDiplomeVide + "<===");
			}

			if(wp!=null)
				this.setChoixDuVoeuParComposanteByPartenaire(wp.isChoixDuVoeuParComposante());
			else
				this.setChoixDuVoeuParComposanteByPartenaire(getSessionController().isChoixDuVoeuParComposante());

			this.currentDemandeTransferts = getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(this.currentDemandeTransferts.getNumeroEtudiant(), this.currentDemandeTransferts.getAnnee(), this.currentDemandeTransferts.getSource());

			if(this.currentDemandeTransferts.getTransferts().getFichier()==null)
			{
				if(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource())!=null)
				{
					this.currentDemandeTransferts.getTransferts().setFichier(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource()));
					setSelectedmd5(this.currentDemandeTransferts.getTransferts().getFichier().getMd5());
				}
				else
				{
					this.currentDemandeTransferts.getTransferts().setFichier(null);
					setSelectedmd5(null);
				}
			}
			else
			{
				setSelectedmd5(this.currentDemandeTransferts.getTransferts().getFichier().getMd5());
			}

			droitPC = getDomainService().getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante(getCurrentUserLogin(),
					getSource(),
					getSessionController().getCurrentAnnee(),
					this.currentDemandeTransferts.getComposante());

			if ("100".equals(this.currentDemandeTransferts.getAdresse().getCodPay()))
				this.setCodePaysItems("100");
			else
				this.setCodePaysItems("99");

			if(currentDemandeTransferts.getTransferts().getOdf()!=null)
			{
				setCodTypDip(currentDemandeTransferts.getTransferts().getOdf().getCodTypDip());
				setCodeNiveau(currentDemandeTransferts.getTransferts().getOdf().getCodeNiveau());
				if(isChoixDuVoeuParComposanteByPartenaire())
				{
					setCodeComposante(currentDemandeTransferts.getTransferts().getOdf().getCodeComposante());
					setListeLibellesEtape(getListeLibellesEtapeByCodeComposante());
				}
				else
				{
					if (logger.isDebugEnabled())
						logger.debug("currentDemandeTransferts.getTransferts().getOdf().getCodeDiplome()===>"+currentDemandeTransferts.getTransferts().getOdf().getCodeDiplome()+"<===");
					setCodeDiplome(currentDemandeTransferts.getTransferts().getOdf().getCodeDiplome());
					setListeLibellesEtape(getListeLibellesEtape());
				}
				currentOdf=currentDemandeTransferts.getTransferts().getOdf();

			}
			currentDemandeTransferts=this.initialiseTransientEtudiantRef(currentDemandeTransferts);

			listeDepartements=null;
			setDeptVide(false);
			odfDataModel=null;

			return "goToCurrentDemandeTransferts";
		}
		else
		{
			String summary = getString("SELECTION.DEMANDE_TRANSFERT");
			String detail = getString("SELECTION.DEMANDE_TRANSFERT");
			Severity severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			return null;
		}
	}

	public String getCurrentUserLogin() {
		try {
			if (this.getSessionController().getCurrentUser()!=null)
			{
				if (logger.isDebugEnabled()) {
					logger.debug("Login --> " + getSessionController().getCurrentUser().getLogin());
					logger.debug("Affiliation de la personne dans le config.properties --> " + this.getEmployeeAffiliation());
					if(getSessionController().getCurrentUser().getAffiliation() != null)
						logger.debug("Affiliation de la personne connectee --> " + getSessionController().getCurrentUser().getAffiliation());
				}
				if(getSessionController().getCurrentUser().getAffiliation() != null && this.getEmployeeAffiliation().contains(getSessionController().getCurrentUser().getAffiliation()))
				{
					if (logger.isDebugEnabled())
						logger.debug("L'utilisateur est un personnel");
					setPersonnel(true);
					if (logger.isDebugEnabled())
					{
						logger.debug("login --> "+getSessionController().getCurrentUser().getLogin());
						logger.debug("Source --> "+source);
					}
					setDroitsDepart(getDomainService().getDroitsTransferts(getSessionController().getCurrentUser().getLogin(), "D", getSessionController().getCurrentAnnee()));
					setDroitsArrivee(getDomainService().getDroitsTransferts(getSessionController().getCurrentUser().getLogin(), "A", getSessionController().getCurrentAnnee()));
					if (logger.isDebugEnabled())
					{
						logger.debug("droitsDepart --> "+isDroitsDepart());
						logger.debug("droitsArrivee --> "+isDroitsArrivee());
					}
				}
				else
				{
					if (logger.isDebugEnabled())
						logger.debug("L'utilisateur n'est pas un personnel");
					setPersonnel(false);
					setDroitsDepart(false);
					setDroitsArrivee(false);
					String summary = "Connexion refusee : ";
					String detail = "Application reserve aux personnels";
					Severity severity = FacesMessage.SEVERITY_ERROR;
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(severity, summary, detail));
				}
				return getSessionController().getCurrentUser().getLogin();
			} else
				return "Invite";
		}
		catch (Exception e)
		{
			logger.error(e);
			return "Invite";
		}
	}

	public ListeTransfertDepartDataModel getAllDemandesTransfertsAccueil() {
		if (logger.isDebugEnabled())
			logger.debug("ListeTransfertDepartDataModel getAllDemandesTransfertsAccueil()");

		setSource("A");
		List<PersonnelComposante> lPc=null;
		String chaineComposante=null;
		try {
			lPc = getDomainService().getListeComposantesByUidAndSourceAndAnnee(getSessionController().getCurrentUser().getLogin(), getSource(), getSessionController().getCurrentAnnee());
			if(lPc!=null)
			{
				for(PersonnelComposante pc : lPc)
				{
					chaineComposante+=pc.getCodeComposante()+",";
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		List<EtudiantRef> lEtu2 = new ArrayList<EtudiantRef>();
		if (switchTraiteNontraite)
		{
			if (listeTransfertDepartDataModel == null)
			{
				List<EtudiantRef> lEtu = getDomainService().getAllDemandesTransfertsByAnnee(getSessionController().getCurrentAnnee(), getSource());
				if(lEtu!=null)
				{
					for (EtudiantRef etu : lEtu)
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("getAujourdhui()===>" +getSessionController().getAujourdhui()+"<===");
							logger.debug("GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), "+getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord()+"===>" +GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord())+"<===");
							logger.debug("GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 0)===>" +GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 0)+"<===");
							logger.debug("GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), "+getSessionController().getNbJourAvantAlertSilenceVautAccord()+")===>" +GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbJourAvantAlertSilenceVautAccord())+"<===");
						}
						etu.setAlertDepassementSilenceVautAccord(GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord()));
						etu.setAlertSilenceVautAccord(GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbJourAvantAlertSilenceVautAccord()));
						if (logger.isDebugEnabled())
							try {
								logger.debug("admin - switchTraiteNontraite - chaineComposante --> "+getSessionController().getCurrentUser().isAdmin()+" - "+switchTraiteNontraite+" - "+chaineComposante);
							} catch (Exception e1) {
								logger.error(e1);
							}
						try {
							if((lPc!=null
									&& chaineComposante!=null
									&& !"".equals(chaineComposante)
									&& chaineComposante.contains(etu.getTransferts().getOdf().getCodeComposante())
							) || getSessionController().getCurrentUser().isAdmin())
								lEtu2.add(etu);
						} catch (Exception e) {
							logger.error(e);
						}
					}
					setTotalAccueil(lEtu2.size());
					listeTransfertDepartDataModel = new ListeTransfertDepartDataModel(lEtu2);
				}
				else
				{
					setTotalAccueil(0);
					listeTransfertDepartDataModel = new ListeTransfertDepartDataModel(new ArrayList<EtudiantRef>());
				}
			}
			return listeTransfertDepartDataModel;
		}
		else
		{
			if (listeTransfertDepartDataModel == null)
			{
				List<EtudiantRef> lEtu = getDomainService().getAllDemandesTransfertsByAnneeAndNonTraite(getSessionController().getCurrentAnnee(), getSource());
				if(lEtu!=null)
				{
					for (EtudiantRef etu : lEtu)
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("getAujourdhui()===>" +getSessionController().getAujourdhui()+"<===");
							logger.debug("GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), "+getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord()+"===>" +GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord())+"<===");
							logger.debug("GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 0)===>" +GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 0)+"<===");
							logger.debug("GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), "+getSessionController().getNbJourAvantAlertSilenceVautAccord()+")===>" +GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbJourAvantAlertSilenceVautAccord())+"<===");
						}

						etu.setAlertDepassementSilenceVautAccord(GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord()));
						etu.setAlertSilenceVautAccord(GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbJourAvantAlertSilenceVautAccord()));

						if (logger.isDebugEnabled())
							try {
								logger.debug("admin - switchTraiteNontraite - chaineComposante --> "+getSessionController().getCurrentUser().isAdmin()+" - "+switchTraiteNontraite+" - "+chaineComposante);
							} catch (Exception e1) {
								logger.error(e1);
							}
						try {
							if((lPc!=null
									&& chaineComposante!=null
									&& !"".equals(chaineComposante)
									&& chaineComposante.contains(etu.getTransferts().getOdf().getCodeComposante())
							) || getSessionController().getCurrentUser().isAdmin())
								lEtu2.add(etu);
						} catch (Exception e) {
							logger.error(e);
						}
					}
					setTotalAccueil(lEtu2.size());
					listeTransfertDepartDataModel = new ListeTransfertDepartDataModel(lEtu2);
				}
				else
				{
					setTotalAccueil(0);
					listeTransfertDepartDataModel = new ListeTransfertDepartDataModel(new ArrayList<EtudiantRef>());
				}
			}
			return listeTransfertDepartDataModel;
		}
	}

	public ListeTransfertDepartDataModel getAllDemandesTransferts() {
		if (logger.isDebugEnabled()) {
			logger.debug("public TransfertDataModel getAllDemandesTransferts()");
		}
		setSource("D");
		List<PersonnelComposante> lPc=null;
		String chaineComposante=null;
		try {
			lPc = getDomainService().getListeComposantesByUidAndSourceAndAnnee(getSessionController().getCurrentUser().getLogin(), getSource(), getSessionController().getCurrentAnnee());
			if(lPc!=null)
			{
				for(PersonnelComposante pc : lPc)
				{
					if(this.isDroitAvisDefinitifTransfertDepartMultiple() && pc.getDroitAvisDefinitif()!=null && "oui".equalsIgnoreCase(pc.getDroitAvisDefinitif()))
						chaineComposante+=pc.getCodeComposante()+",";
					else if(!this.isDroitAvisDefinitifTransfertDepartMultiple())
						chaineComposante+=pc.getCodeComposante()+",";
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		List<EtudiantRef> lEtu2 = new ArrayList<EtudiantRef>();
		if (switchTraiteNontraite)
		{
			if (listeTransfertDepartDataModel == null)
			{
				List<EtudiantRef> lEtu = getDomainService().getAllDemandesTransfertsByAnnee(getSessionController().getCurrentAnnee(), getSource());
				if(lEtu!=null)
				{
					for (EtudiantRef etu : lEtu)
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("getAujourdhui()===>" +getSessionController().getAujourdhui()+"<===");
							logger.debug("GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), "+getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord()+"===>" +GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord())+"<===");
							logger.debug("GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 0)===>" +GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 0)+"<===");
							logger.debug("GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), "+getSessionController().getNbJourAvantAlertSilenceVautAccord()+")===>" +GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbJourAvantAlertSilenceVautAccord())+"<===");
						}

						etu.setAlertDepassementSilenceVautAccord(GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord()));
						etu.setAlertSilenceVautAccord(GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbJourAvantAlertSilenceVautAccord()));

						if(etu.getTransferts().getFichier()==null)
						{
							if (logger.isDebugEnabled()) {
								logger.debug("Pas de signature");
							}
							etu.getTransferts().setFichier(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource()));
						}
						else
						{
							if (logger.isDebugEnabled()) {
								logger.debug("Signature !!!");
							}
						}
						if (logger.isDebugEnabled())
							try {
								logger.debug("admin - switchTraiteNontraite - chaineComposante --> "+getSessionController().getCurrentUser().isAdmin()+" - "+switchTraiteNontraite+" - "+chaineComposante);
							} catch (Exception e1) {
								logger.error(e1);
							}
						try {
							if((lPc!=null
									&& chaineComposante!=null
									&& !"".equals(chaineComposante)
									&& chaineComposante.contains(etu.getComposante())
							) || getSessionController().getCurrentUser().isAdmin())
								lEtu2.add(etu);
						} catch (Exception e) {
							logger.error(e);
						}
					}
					setTotalDepart(lEtu2.size());
					listeTransfertDepartDataModel = new ListeTransfertDepartDataModel(lEtu2);
				}
				else
				{
					setTotalDepart(0);
					listeTransfertDepartDataModel = new ListeTransfertDepartDataModel(new ArrayList<EtudiantRef>());
				}
			}
			return listeTransfertDepartDataModel;
		}
		else
		{
			if (listeTransfertDepartDataModel == null)
			{
				List<EtudiantRef> lEtu = getDomainService().getAllDemandesTransfertsByAnneeAndNonTraite(getSessionController().getCurrentAnnee(), getSource());
				if(lEtu!=null)
				{
					for (EtudiantRef etu : lEtu)
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("getAujourdhui()===>" +getSessionController().getAujourdhui()+"<===");
							logger.debug("GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), "+getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord()+"===>" +GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord())+"<===");
							logger.debug("GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 0)===>" +GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 0)+"<===");
							logger.debug("GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), "+getSessionController().getNbJourAvantAlertSilenceVautAccord()+")===>" +GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbJourAvantAlertSilenceVautAccord())+"<===");
						}

						etu.setAlertDepassementSilenceVautAccord(GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord()));
						etu.setAlertSilenceVautAccord(GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), getSessionController().getNbJourAvantAlertSilenceVautAccord()));

						if(etu.getTransferts().getFichier()==null)
						{
							if (logger.isDebugEnabled())
								logger.debug("Pas de signature");
							etu.getTransferts().setFichier(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource()));
						}
						else
						{
							if (logger.isDebugEnabled())
								logger.debug("Signature !!!");
						}
						if (etu.getLibEtapePremiereLocal() == null || "Non disponible".equals(etu.getLibEtapePremiereLocal()))
						{
							if (logger.isDebugEnabled())
								logger.debug("Derniere IA non renseigne --> "+etu.getNumeroEtudiant()+" ----- "+etu.getNomPatronymique());

							etu=this.initialiseTransientEtudiantRef(etu);
							getDomainService().addDemandeTransferts(etu);
						}
						if (logger.isDebugEnabled())
							try {
								logger.debug("admin - switchTraiteNontraite - chaineComposante --> "+getSessionController().getCurrentUser().isAdmin()+" - "+switchTraiteNontraite+" - "+chaineComposante);
							} catch (Exception e1) {
								logger.error(e1);
							}
						try {
							if((lPc!=null
									&& chaineComposante!=null
									&& !"".equals(chaineComposante)
									&& chaineComposante.contains(etu.getComposante())
							) || getSessionController().getCurrentUser().isAdmin())
								lEtu2.add(etu);
						} catch (Exception e) {
							logger.error(e);
						}
					}
					setTotalDepart(lEtu2.size());
					listeTransfertDepartDataModel = new ListeTransfertDepartDataModel(lEtu2);
				}
				else
				{
					setTotalDepart(0);
					listeTransfertDepartDataModel = new ListeTransfertDepartDataModel(new ArrayList<EtudiantRef>());
				}
			}
			this.setDroitAvisDefinitifTransfertDepartMultiple(false);
			return listeTransfertDepartDataModel;
		}
	}

	public void addMessage()
	{
		this.listeTransfertDepartDataModel = null;
		this.transfertDataModelOpi =null;
		String summary;

		if (switchTraiteNontraite)
			summary = getString("INFOS.LISTE_DE_TOUTES_LES_DEMANDES");
		else
			summary = getString("INFOS.LISTE_DES_DEMANDES_EN_COURS_DE_TRAITEMENT");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
	}

	public List<SelectItem> getListeCommunes() {
		List<SelectItem> communes = new ArrayList<SelectItem>();
		List<TrCommuneDTO> listeCommunes;
		try {
			listeCommunes = getDomainServiceScolarite().getCommunes(currentDemandeTransferts.getAdresse().getCodePostal());
			if (listeCommunes == null || listeCommunes.isEmpty()) {
				String summary = getString("ERREUR.AUCUNE_COMMUNE");
				String detail = getString("ERREUR.AUCUNE_COMMUNE");
				Severity severity = FacesMessage.SEVERITY_ERROR;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			}
			if(listeCommunes!=null)
			{
				for (TrCommuneDTO c : listeCommunes) {
					SelectItem option = new SelectItem(c.getCodeCommune(),c.getLibCommune());
					communes.add(option);
				}
			}
			Collections.sort(communes, new ComparatorSelectItem());
		} catch (Exception e) {
			SelectItem option = new SelectItem("", "");
			communes.add(option);
			logger.error(e);
			String summary = getString("ERREUR.RECUPERATION_COMMUNE");
			String detail = getString("ERREUR.RECUPERATION_COMMUNE");
			Severity severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
		return communes;
	}

	public boolean isPartenaire()
	{
		boolean partenaire = false;
		if (this.currentDemandeTransferts.getTransferts().getRne() != null) {
			List<WsPub> listeEtablissementsPartenaires = getDomainService().getWsPubByAnnee(getSessionController().getCurrentAnnee());
			if(listeEtablissementsPartenaires!=null)
			{
				for(WsPub eu : listeEtablissementsPartenaires)
				{
					if(this.currentDemandeTransferts.getTransferts().getRne().equals(eu.getRne()))
						partenaire = true;
				}
			}
		}
		return partenaire;
	}

	public List<SelectItem> getListeDepartements() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeDepartements()");
			logger.debug("getDomainServiceScolarite().getListeDepartements();");
		}
		//		if(listeDepartements==null)
		//		{
		listeDepartements = new ArrayList<SelectItem>();
		List<TrDepartementDTO> listeDepartementDTO = getDomainServiceScolarite().getListeDepartements();
		if (listeDepartementDTO != null)
		{
			for (TrDepartementDTO dDTO : listeDepartementDTO) {
				SelectItem option = new SelectItem(dDTO.getCodeDept(), dDTO.getLibDept() + " (" + dDTO.getCodeDept() + ")");
				listeDepartements.add(option);
			}
			Collections.sort(listeDepartements, new ComparatorSelectItem());
		} else {
			SelectItem option = new SelectItem("", "");
			listeDepartements.add(option);
		}

		if (isPartenaire() && (this.currentDemandeTransferts.getTransferts().getLibelleTypeDiplome()==null || "".equals(this.currentDemandeTransferts.getTransferts().getLibelleTypeDiplome()))) {
			if (logger.isDebugEnabled()){
				logger.debug("Etablissement partenaire && typediplome==null");
				logger.debug("aaa---libelleDiplomeVide===>" + libelleDiplomeVide + "<===");
				logger.debug("bbb---getCodeDiplome()===>" + getCodeDiplome() + "<===");
			}
			setTypesDiplomeAutreVide(true);
			setTypesDiplomeVide(false);
			if (getCodeNiveau() != null)
				setAnneeEtudeVide(false);
			if (getCodeComposante() != null)
				setComposanteVide(false);
			if (getCodeDiplome() != null && !"".equals(getCodeDiplome()))
				setLibelleDiplomeVide(false);
			if (currentOdf != null)
				setLibelleEtapeVide(false);
		}
		else if (!isPartenaire() && this.currentDemandeTransferts.getTransferts().getOdf()!=null)
		{
			if (logger.isDebugEnabled())
				logger.debug("Etablissement non partenaire && odf!=null");
			setTypesDiplomeAutreVide(true);
			setTypesDiplomeVide(false);
			if (getCodeNiveau() != null)
				setAnneeEtudeVide(false);
			if (getCodeComposante() != null)
				setComposanteVide(false);
			if (getCodeDiplome() != null	&& !"".equals(getCodeDiplome()))
				setLibelleDiplomeVide(false);
			if (currentOdf != null)
				setLibelleEtapeVide(false);
		}
		else
		{
			if (logger.isDebugEnabled())
				logger.debug("Etablissement non partenaire");
			setTypesDiplomeVide(true);
			setAnneeEtudeVide(true);
			setComposanteVide(true);
			setLibelleDiplomeVide(true);
			setLibelleEtapeVide(true);
			if (this.currentDemandeTransferts.getTransferts().getRne() != null)
				setTypesDiplomeAutreVide(false);
			else
				setTypesDiplomeAutreVide(true);
		}
		return listeDepartements;
	}

	public List<SelectItem> getListeEtablissementsAccueil() {
		if(!isDeptVide()) {
			listeEtablissements = getDomainServiceDTO().getListeEtablissements("A", getSessionController().getRne(), getTypesEtablissementListSplit(),
					this.currentDemandeTransferts.getAccueil().getCodeDepUnivDepart(), getSessionController().getAjoutEtablissementManuellement(), "," ,getSessionController().isActivEtablissementManuellement());
			Collections.sort(listeEtablissements, new ComparatorSelectItem());
		}
		return listeEtablissements;
	}


	public List<SelectItem> getListeEtablissements() {
		if(!isDeptVide()) {
			listeEtablissements = getDomainServiceDTO().getListeEtablissements("D", getSessionController().getRne(), getTypesEtablissementListSplit(),
					this.currentDemandeTransferts.getTransferts().getDept(), getSessionController().getAjoutEtablissementManuellement(), "," ,getSessionController().isActivEtablissementManuellement());
			Collections.sort(listeEtablissements, new ComparatorSelectItem());
		}
		return listeEtablissements;
	}

	public List<SelectItem> getListeTypesDiplome() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeTypesDiplome()");
			logger.debug("getDomainService().getOdfTypesDiplomeByRneAndAnnee(currentDemandeTransferts.getTransferts().getRne(), getSessionController().getCurrentAnnee(), false);");
		}
		if(!isTypesDiplomeVide())
		{
			if(listeTypesDiplome==null)
				logger.debug("if(listeTypesDiplome==null) --> " + listeTypesDiplome);
			listeTypesDiplome = new ArrayList<SelectItem>();
			Map<String, String> listeTypesDiplomeDTO = getDomainService().getOdfTypesDiplomeByRneAndAnnee(currentDemandeTransferts.getTransferts().getRne(), getSessionController().getCurrentAnnee(), false, getSource());
			if(listeTypesDiplomeDTO!=null && !listeTypesDiplomeDTO.isEmpty())
			{
				if (logger.isDebugEnabled()) {
					logger.debug("listeTypesDiplomeDTO : "+listeTypesDiplomeDTO);
				}

//				for (String mapKey : listeTypesDiplomeDTO.keySet()) {
//					// utilise ici hashMap.get(mapKey) pour acc�der aux valeurs
//					SelectItem option = new SelectItem(mapKey, listeTypesDiplomeDTO.get(mapKey));
//					listeTypesDiplome.add(option);
//				}

				for (Map.Entry<String,String> entry : listeTypesDiplomeDTO.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					SelectItem option = new SelectItem(key, value);
					listeTypesDiplome.add(option);
				}

				Collections.sort(listeTypesDiplome,new ComparatorSelectItem());
				return listeTypesDiplome;
			}
			else
				return null;
		}
		return listeTypesDiplome;
	}

	public List<SelectItem> getListeAnneesEtude() {
		if (logger.isDebugEnabled())
		{
			logger.debug("public List<SelectItem> getListeAnneesEtude()");
			logger.debug("getDomainService().getAnneesEtudeByRneAndAnneeAndCodTypDip(currentDemandeTransferts.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip(), false);");
		}
		listeAnneesEtude = new ArrayList<SelectItem>();
		Map<Integer, String> listeAnneesEtudeDTO = getDomainService().getAnneesEtudeByRneAndAnneeAndCodTypDip(currentDemandeTransferts.getTransferts().getRne(),
				getSessionController().getCurrentAnnee(),
				getCodTypDip(),
				false,
				getSource());

		if(listeAnneesEtudeDTO!=null && !listeAnneesEtudeDTO.isEmpty())
		{
			if (logger.isDebugEnabled()) {
				logger.debug("listeAnneesEtudeDTO : "+listeAnneesEtudeDTO);
			}

//			for(Integer mapKey : listeAnneesEtudeDTO.keySet())
//			{
//				SelectItem option = new SelectItem(mapKey, listeAnneesEtudeDTO.get(mapKey));
//				listeAnneesEtude.add(option);
//			}

			for (Map.Entry<Integer,String> entry : listeAnneesEtudeDTO.entrySet()) {
				Integer key = entry.getKey();
				String value = entry.getValue();
				SelectItem option = new SelectItem(key, value);
				listeAnneesEtude.add(option);
			}

			Collections.sort(listeAnneesEtude,new ComparatorSelectItem());
			return listeAnneesEtude;
		}
		else
			return null;
	}

	public List<SelectItem> getListeLibellesDiplome() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeLibellesDiplome()");
			logger.debug("getDomainService().getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(currentDemandeTransferts.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip(), getCodeNiveau(),	false);");
		}
		listeLibellesDiplome = new ArrayList<SelectItem>();

		Map<String, String> listeLibellesDiplomeDTO;

		if("D".equals(this.currentDemandeTransferts.getSource()))
		{
			listeLibellesDiplomeDTO = getDomainService().getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(currentDemandeTransferts.getTransferts().getRne(),
					getSessionController().getCurrentAnnee(),
					getCodTypDip(),
					getCodeNiveau(),
					false,
					getSource());
		}
		else
		{
			listeLibellesDiplomeDTO = getDomainService().getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante(currentDemandeTransferts.getTransferts().getRne(),
					getSessionController().getCurrentAnnee(),
					getCodTypDip(),
					getCodeNiveau(),
					getCodeComposante(),
					false);
		}

		if(listeLibellesDiplomeDTO!=null && !listeLibellesDiplomeDTO.isEmpty())
		{
			if (logger.isDebugEnabled())
				logger.debug("listeLibellesDiplomeDTO : "+listeLibellesDiplomeDTO);
			for (Map.Entry<String,String> entry : listeLibellesDiplomeDTO.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				SelectItem option = new SelectItem(key, value);
				listeLibellesDiplome.add(option);
			}
			Collections.sort(listeLibellesDiplome,new ComparatorSelectItem());
			return listeLibellesDiplome;
		}
		else
			return null;
	}

	public List<SelectItem> getListeLibellesDiplomeAccueil() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeLibellesDiplome()");
			logger.debug("getDomainService().getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante(currentDemandeTransferts.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip(),	getCodeNiveau(), getCodeComposante(), false);");
		}
		listeLibellesDiplome = new ArrayList<SelectItem>();

		Map<String, String> listeLibellesDiplomeDTO = getDomainService().getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante(currentDemandeTransferts.getTransferts().getRne(),
				getSessionController().getCurrentAnnee(),
				getCodTypDip(),
				getCodeNiveau(),
				getCodeComposante(),
				false);


		if(listeLibellesDiplomeDTO!=null && !listeLibellesDiplomeDTO.isEmpty())
		{
			if (logger.isDebugEnabled()) {
				logger.debug("listeLibellesDiplomeDTO : "+listeLibellesDiplomeDTO);
			}

//			for(String mapKey : listeLibellesDiplomeDTO.keySet())
//			{
//				SelectItem option = new SelectItem(mapKey, listeLibellesDiplomeDTO.get(mapKey));
//				listeLibellesDiplome.add(option);
//			}

			for (Map.Entry<String,String> entry : listeLibellesDiplomeDTO.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				SelectItem option = new SelectItem(key, value);
				listeLibellesDiplome.add(option);
			}

			Collections.sort(listeLibellesDiplome,new ComparatorSelectItem());
			return listeLibellesDiplome;
		}
		else
			return null;
	}

	public List<OffreDeFormationsDTO> getListeLibellesEtape() {
		if (logger.isDebugEnabled())
			logger.debug("public List<SelectItem> getListeLibellesEtape()");
		return getDomainService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndAtifOuPas(currentDemandeTransferts.getTransferts().getRne(),
				getSessionController().getCurrentAnnee(),
				getCodTypDip(),
				getCodeNiveau(),
				getCodeDiplome());
	}

	public List<OffreDeFormationsDTO> getListeLibellesEtapeByCodeComposante() {
		if (logger.isDebugEnabled())
			logger.debug("public List<OffreDeFormationsDTO> getListeLibellesEtapeByCodeComposante()");
		return getDomainService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndAtifOuPas(currentDemandeTransferts.getTransferts().getRne(),
				getSessionController().getCurrentAnnee(),
				getCodTypDip(),
				getCodeNiveau(),
				getCodeComposante(),
				getSource());

	}

	public void resetGeneral()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("public void resetGeneral() --> "+ this.currentDemandeTransferts.getTransferts().getDept());
		}
		if(currentDemandeTransferts.getTransferts().getDept() !=null && !"".equals(currentDemandeTransferts.getTransferts().getDept()))
		{
			setDeptVide(false);
			listeDepartements=null;
			currentDemandeTransferts.getTransferts().setRne(null);
			setTypesDiplomeVide(true);
			setTypesDiplomeAutreVide(true);
			setAnneeEtudeVide(true);
		}
		else
		{
			setDeptVide(true);
			listeEtablissements=null;
		}
	}

	public void resetGeneralAccueil()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("public void resetGeneralAccueil() --> "+ this.currentDemandeTransferts.getAccueil().getCodeDepUnivDepart());
		}
		if(currentDemandeTransferts.getAccueil().getCodeDepUnivDepart() !=null && !"".equals(currentDemandeTransferts.getAccueil().getCodeDepUnivDepart()))
		{
			setDeptVide(false);
			listeDepartements=null;
		}
		else
		{
			setDeptVide(true);
			listeEtablissements=null;
		}
	}

	public void resetAnneeEtudeAccueil()
	{
		if (logger.isDebugEnabled())
			logger.debug("public void resetAnneeEtude()");

		setTypesDiplomeAutreVide(true);
		if(getCodTypDip() !=null && !"".equals(getCodTypDip()))
		{
			setDeptVide(false);
			setTypesDiplomeVide(false);
			setCodeNiveau(null);
			setLibelleEtapeVide(true);
			setAnneeEtudeVide(false);
			setLibelleDiplomeVide(true);
			setComposanteVide(true);
			this.getListeAnneesEtude();
		}
		else
		{
			setAnneeEtudeVide(true);
			setLibelleDiplomeVide(true);
			this.listeTypesDiplome=null;
			this.setListeAnneesEtude(null);
			this.setListeComposantes(null);
			this.setListeLibellesDiplome(null);
			this.setListeLibellesEtape(null);
		}
	}

	public void resetComposante()
	{
		if (logger.isDebugEnabled())
			logger.debug("public void resetComposante()");

		setTypesDiplomeVide(false);
		setAnneeEtudeVide(false);
		setCodeDiplome(null);
		setLibelleEtapeVide(true);
		setComposanteVide(false);
		setCodeComposante(null);
		setLibelleDiplomeVide(true);
		this.getListeLibellesDiplome();
	}

	public void resetTypeDiplome()
	{
		if (logger.isDebugEnabled())
			logger.debug("public void resetTypeDiplome()");

		currentOdf=null;
		currentDemandeTransferts.getTransferts().setOdf(currentOdf);
		setAnneeEtudeVide(true);

		if(this.currentDemandeTransferts.getTransferts().getRne() !=null && !"".equals(this.currentDemandeTransferts.getTransferts().getRne()) && isPartenaire() && (this.currentDemandeTransferts.getTransferts().getLibelleTypeDiplome()==null || "".equals(this.currentDemandeTransferts.getTransferts().getLibelleTypeDiplome())))
		{

			setTypesDiplomeVide(false);
			setCodTypDip(null);
			setTypesDiplomeAutreVide(true);
			getListeTypesDiplome();
		}
		else
		{
			currentDemandeTransferts.getTransferts().setLibelleTypeDiplome(null);
			setLibelleEtapeVide(true);
			if(getSessionController().isChoixDuVoeuParComposante())
				setComposanteVide(true);
			else
				setLibelleDiplomeVide(true);
			setTypesDiplomeVide(true);
			setTypesDiplomeAutreVide(false);
			listeTypesDiplome=null;
		}
	}

	public void resetAnneeEtude()
	{
		if (logger.isDebugEnabled())
			logger.debug("public void resetAnneeEtude()");

		setTypesDiplomeAutreVide(true);
		if(getCodTypDip() !=null && !"".equals(getCodTypDip()))
		{
			setDeptVide(false);
			setTypesDiplomeVide(false);
			setCodeNiveau(null);
			setLibelleEtapeVide(true);
			setAnneeEtudeVide(false);
			setLibelleDiplomeVide(true);
			this.getListeAnneesEtude();
		}
		else
		{
			setAnneeEtudeVide(true);
			setLibelleDiplomeVide(true);
			this.listeTypesDiplome=null;
			this.setListeAnneesEtude(null);
			this.setListeLibellesDiplome(null);
			this.setListeLibellesEtape(null);
		}
	}

	public void resetLibelleDiplome()
	{
		setTypesDiplomeAutreVide(true);
		if(getCodeNiveau() !=null)
		{
			setDeptVide(false);
			setTypesDiplomeVide(false);
			setAnneeEtudeVide(false);
			setCodeDiplome(null);
			setLibelleEtapeVide(true);
			setLibelleDiplomeVide(false);
			this.getListeLibellesDiplome();
		}
		else
		{
			setLibelleDiplomeVide(true);
			this.setListeLibellesDiplome(null);
			this.listeLibellesEtape=null;
		}
	}

	public void resetLibelleEtape()
	{
		setTypesDiplomeAutreVide(true);
		if(getCodeDiplome() !=null && !"".equals(getCodeDiplome()))
		{
			setDeptVide(false);
			setTypesDiplomeVide(false);
			setAnneeEtudeVide(false);
			setLibelleDiplomeVide(false);
			setLibelleEtapeVide(false);
			currentOdf=null;
			odfDataModel=null;
			setListeLibellesEtape(this.getListeLibellesEtape());
		}
		else
		{
			setLibelleEtapeVide(true);
			this.listeLibellesEtape=null;
		}
	}

	public void resetLibelleEtapeSansCodeDiplome()
	{
		setTypesDiplomeAutreVide(true);
//		if(getCodeNiveau() !=null && !getCodeNiveau().equals(""))
		if(getCodeNiveau() !=null)
		{
			setDeptVide(false);
			//			setTypesDiplomeVide(false);
			setAnneeEtudeVide(false);
			setLibelleDiplomeVide(false);
			setLibelleEtapeVide(false);
			currentOdf=null;
			odfDataModel=null;
			setListeLibellesEtape(this.getListeLibellesEtapeByCodeComposante());
		}
		else
		{
			setLibelleEtapeVide(true);
			this.listeLibellesEtape=null;
		}
	}

	/**
	 * Impression d'une personne
	 *
	 * @return PDF
	 */
	public String imprimerDemandeTransfert() {
		/**
		 * @return String
		 */
		String retour = null;
		setSource("D");
		if(currentOdf==null && isPartenaire())
		{
			String summary = getString("ERREUR.PAS_DE_PARCOURS_SELECTIONNE");
			String detail = getString("ERREUR.PAS_DE_PARCOURS_SELECTIONNE");
			Severity severity=FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
		else
		{
			if(isPartenaire())
			{
				currentDemandeTransferts.getTransferts().setLibelleTypeDiplome(null);
				currentDemandeTransferts.getTransferts().setOdf(currentOdf);
			}

			try {

				/**
				 ** Methodes de creation des documents PDF selon l'edition demandee
				 **/
				String xslXmlPath = this.getXmlXslPath();
				String fileNameXml = generateXml();
				String fileNameXsl = "etudiant.xsl";
				String fileNamePdf = this.currentDemandeTransferts.getNumeroEtudiant()+ ""+ this.currentDemandeTransferts.getAnnee() + ".pdf";

				PDFUtils.exportPDF(fileNameXml, FacesContext.getCurrentInstance(),xslXmlPath, fileNamePdf, fileNameXsl);
			}
			catch (Exception f)
			{
				logger.error(f);
			}
		}
		return retour;
	}

	public String generateXml()
	{
		String nameXml = this.currentDemandeTransferts.getNumeroEtudiant() + ""+ this.currentDemandeTransferts.getAnnee() + ".xml";
		try {
			// creation d'un contexte JAXB sur la classe EtudiantRefImp
			JAXBContext context = JAXBContext.newInstance(EtudiantRefImp.class);

			// creation d'un marshaller a partir de ce contexte
			Marshaller marshaller = context.createMarshaller();

			// on choisit UTF-8 pour encoder ce fichier
			marshaller.setProperty("jaxb.encoding", "UTF-8");
			// et l'on demande à JAXB de formatter ce fichier de facon
			// a� pouvoir le lire a l'oeil nu
			marshaller.setProperty("jaxb.formatted.output", true);

			// écriture finale du document XML dans un fichier etudiant.xml
			TrResultatVdiVetDTO trResultatVdiVetDTO;
			trResultatVdiVetDTO = getDomainServiceScolarite().getSessionsResultats(this.currentDemandeTransferts.getNumeroEtudiant(), "D");

			Fichier file = getDomainService().getFichierByIdAndAnneeAndFrom(this.currentDemandeTransferts.getTransferts().getFichier().getMd5(),getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource());
			String nom = this.getTempPath() + "" + file.getMd5();

			File fichierExiste = new File(nom);
			if (!fichierExiste.exists()) {
				if (logger.isDebugEnabled())
					logger.debug("L'image n'existe pas");
				byte[] data = file.getImg();
				this.genererImage(nom, data);
			}
			else
			{
				if (logger.isDebugEnabled())
					logger.debug("L'image existe deja");
			}

			this.initialiseNomenclatures();

			EtudiantRefImp etudiantRefImp = new EtudiantRefImp();
			etudiantRefImp.setNumeroEtudiant(this.currentDemandeTransferts.getNumeroEtudiant());
			etudiantRefImp.setNumeroIne(this.currentDemandeTransferts.getNumeroIne());
			etudiantRefImp.setNomPatronymique(this.currentDemandeTransferts.getNomPatronymique());
			etudiantRefImp.setNomUsuel(this.currentDemandeTransferts.getNomUsuel());
			etudiantRefImp.setPrenom1(this.currentDemandeTransferts.getPrenom1());
			etudiantRefImp.setPrenom2(this.currentDemandeTransferts.getPrenom2());
			etudiantRefImp.setDateNaissance(this.currentDemandeTransferts.getDateNaissance());
			etudiantRefImp.setLibNationalite(this.currentDemandeTransferts.getLibNationalite());
			etudiantRefImp.setAdresse(this.currentDemandeTransferts.getAdresse());
			etudiantRefImp.setTransferts(this.currentDemandeTransferts.getTransferts());
			etudiantRefImp.getTransferts().setFichier(getDomainService().getFichierByIdAndAnneeAndFrom(this.currentDemandeTransferts.getTransferts().getFichier().getMd5(), getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource()));
			this.currentDemandeTransferts.getTransferts().getFichier().setChemin(nom);
			this.currentDemandeTransferts.getTransferts().getFichier().setImg(new byte[0]);
			etudiantRefImp.setUniversiteDepart(getDomainServiceScolarite().getEtablissementByRne(getSessionController().getRne()));
			etudiantRefImp.setUniversiteAccueil(getDomainServiceScolarite().getEtablissementByRne(this.currentDemandeTransferts.getTransferts().getRne()));

			if (logger.isDebugEnabled())
			{
				logger.debug("etudiantRefImp.getUniversiteDepart().toString()-->"+etudiantRefImp.getUniversiteDepart().toString());
				logger.debug("etudiantRefImp.getUniversiteAccueil().toString()-->"+etudiantRefImp.getUniversiteAccueil().toString());
			}

			etudiantRefImp.setTrBac(getDomainServiceScolarite().getBaccalaureat(this.currentDemandeTransferts.getNumeroEtudiant()));
			etudiantRefImp.setTrResultatVdiVetDTO(trResultatVdiVetDTO);
			//			etudiantRefImp.getUniversiteDepart().setAdresseEtablissement(getDomainService().getAdresseEtablissementByRne(getSessionController().getRne()));
			//			etudiantRefImp.getUniversiteAccueil().setAdresseEtablissement(getDomainService().getAdresseEtablissementByRne(this.currentDemandeTransferts.getTransferts().getRne()));
			Avis dernierAvis = getDomainService().getDernierAvisFavorable(this.currentDemandeTransferts.getNumeroEtudiant(),getSessionController().getCurrentAnnee());

			if (dernierAvis.getId() != 0)
			{
				dernierAvis.setLibEtatDossier(getDomainService().getEtatDossierById(dernierAvis.getIdEtatDossier()).getLibelleLongEtatDossier());
				dernierAvis.setLibLocalisationDossier(getDomainService().getLocalisationDossierById(dernierAvis.getIdLocalisationDossier()).getLibelleLongLocalisationDossier());
				dernierAvis.setLibDecisionDossier(getDomainService().getDecisionDossierById(dernierAvis.getIdDecisionDossier()).getLibelleLongDecisionDossier());
			}
			etudiantRefImp.setAvis(dernierAvis);
			etudiantRefImp.setDateDuJour(new Date());
			etudiantRefImp.setAnneeUniversitaire(getSessionController().getCurrentAnnee()+ "/"+ (getSessionController().getCurrentAnnee() + 1));
			marshaller.marshal(etudiantRefImp, new File(this.getXmlXslPath()+nameXml)) ;
		}
		catch (JAXBException ex)
		{
			logger.error(ex);
		}
		return nameXml;
	}

	/**
	 * Impression d'une personne
	 *
	 * @return PDF
	 */
	public String imprimerDemandeTransfertAccueil() {
		/**
		 * @return String
		 */
		String retour = null;
		setSource("A");
		if(currentOdf==null && isPartenaire())
		{
			String summary = getString("ERREUR.PAS_DE_PARCOURS_SELECTIONNE");
			String detail = getString("ERREUR.PAS_DE_PARCOURS_SELECTIONNE");
			Severity severity=FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
		else
		{
			if(isPartenaire())
			{
				currentDemandeTransferts.getTransferts().setLibelleTypeDiplome(null);
				currentDemandeTransferts.getTransferts().setOdf(currentOdf);
			}

			try {

				/**
				 ** Methodes de creation des documents PDF selon l'edition demandee
				 **/
				String xslXmlPath = this.getXmlXslPath();
				String fileNameXml = generateXmlAccueil();
				String fileNameXsl = "etudiant_accueil.xsl";
				String fileNamePdf = this.currentDemandeTransferts.getNumeroEtudiant()+ ""+ this.currentDemandeTransferts.getAnnee() + ".pdf";

				PDFUtils.exportPDF(fileNameXml, FacesContext.getCurrentInstance(),xslXmlPath, fileNamePdf, fileNameXsl);
			}
			catch (Exception f)
			{
				logger.error(f);
			}
		}
		return retour;
	}

	public String generateXmlAccueil()
	{
		String nameXml = this.currentDemandeTransferts.getNumeroEtudiant() + ""+ this.currentDemandeTransferts.getAnnee() + ".xml";
		try {
			// creation d'un contexte JAXB sur la classe EtudiantRefImp
			JAXBContext context = JAXBContext.newInstance(EtudiantRefImp.class);

			// creation d'un marshaller a partir de ce contexte
			Marshaller marshaller = context.createMarshaller();

			// on choisit UTF-8 pour encoder ce fichier
			marshaller.setProperty("jaxb.encoding", "UTF-8");
			// et l'on demande à JAXB de formatter ce fichier de facon
			// a� pouvoir le lire a l'oeil nu
			marshaller.setProperty("jaxb.formatted.output", true);

			Fichier file = getDomainService().getFichierByIdAndAnneeAndFrom(this.currentDemandeTransferts.getTransferts().getFichier().getMd5(),getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource());
			if (logger.isDebugEnabled())
				logger.debug("file-->"+file);

			//			if(file!=null && file.getNom().equals("ETABLISSEMENT_PARTENAIRE"))
			//				file = getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource());

			String nom = this.getTempPath() + "" + file.getMd5();

			File fichierExiste = new File(nom);
			if (!fichierExiste.exists()) {
				if (logger.isDebugEnabled())
					logger.debug("L'image n'existe pas");
				byte[] data = file.getImg();
				this.genererImage(nom, data);
			}
			else
			{
				if (logger.isDebugEnabled())
					logger.debug("L'image existe deja");
			}

			this.initialiseNomenclatures();

			EtudiantRefImp etudiantRefImp = new EtudiantRefImp();
			etudiantRefImp.setNumeroEtudiant(this.currentDemandeTransferts.getNumeroEtudiant());
			etudiantRefImp.setNumeroIne(this.currentDemandeTransferts.getNumeroIne());
			etudiantRefImp.setNomPatronymique(this.currentDemandeTransferts.getNomPatronymique());
			etudiantRefImp.setNomUsuel(this.currentDemandeTransferts.getNomUsuel());
			etudiantRefImp.setPrenom1(this.currentDemandeTransferts.getPrenom1());
			etudiantRefImp.setPrenom2(this.currentDemandeTransferts.getPrenom2());
			etudiantRefImp.setDateNaissance(this.currentDemandeTransferts.getDateNaissance());
			etudiantRefImp.setLibNationalite(this.currentDemandeTransferts.getLibNationalite());
			etudiantRefImp.setAdresse(this.currentDemandeTransferts.getAdresse());
			etudiantRefImp.setTransferts(this.currentDemandeTransferts.getTransferts());
			etudiantRefImp.getTransferts().setFichier(file);
			this.currentDemandeTransferts.getTransferts().getFichier().setChemin(nom);
			this.currentDemandeTransferts.getTransferts().getFichier().setImg(new byte[0]);
			etudiantRefImp.setTrBac(getDomainServiceScolarite().recupererBacOuEquWS(this.currentDemandeTransferts.getAccueil().getCodeBac()).get(0));
			etudiantRefImp.getTrBac().setAnneeObtentionBac(this.currentDemandeTransferts.getAccueil().getAnneeBac());
			etudiantRefImp.setUniversiteDepart(getDomainServiceScolarite().getEtablissementByRne(this.currentDemandeTransferts.getAccueil().getCodeRneUnivDepart()));
			etudiantRefImp.setUniversiteAccueil(getDomainServiceScolarite().getEtablissementByRne(getSessionController().getRne()));
			List<TrSituationUniversitaire> lTrSU = new ArrayList<TrSituationUniversitaire>();
			for(SituationUniversitaire su : this.currentDemandeTransferts.getAccueil().getSituationUniversitaire())
			{
				String annee;
				String resultat;
				if(su.getAnnee().getIdAccueilAnnee()!=0)
					annee = su.getAnnee().getLibelle();
				else
					annee = su.getLibAccueilAnnee();
				if(su.getResultat().getIdAccueilResultat()!=0)
					resultat = su.getResultat().getLibelle();
				else
					resultat = su.getLibAccueilResultat();
				lTrSU.add(new TrSituationUniversitaire(su.getId(), annee, su.getLibelle(), resultat));
			}
			etudiantRefImp.setSituationUniversitaire(lTrSU);
			etudiantRefImp.setDateDuJour(new Date());

			listeAccueilDecision=null;

			if(this.currentDemandeTransferts.getAccueilDecision()!=null && !this.currentDemandeTransferts.getAccueilDecision().isEmpty())
			{
				if (logger.isDebugEnabled())
				{
					for(int i=0;i<this.getListeAccueilDecision().size();i++)
					{
						logger.debug("this.getListeAccueilDecision().get(i).getId()===>"+this.getListeAccueilDecision().get(i).getId()+"<===");
					}
				}
				etudiantRefImp.setCodeDecision(this.getListeAccueilDecision().get(0).getAvis());
				etudiantRefImp.setDecision(this.getListeAccueilDecision().get(0).getDecision());
			}
			else
			if (logger.isDebugEnabled())
				logger.debug("Pas de decision saisie");

			etudiantRefImp.setAnneeUniversitaire(getSessionController().getCurrentAnnee()+ "/"+ (getSessionController().getCurrentAnnee() + 1));

			WsPub partenaire = getDomainService().getWsPubByRneAndAnnee(this.currentDemandeTransferts.getAccueil().getCodeRneUnivDepart(), getSessionController().getCurrentAnnee());
			if(partenaire!=null)
			{
				if (logger.isDebugEnabled())
					logger.debug("===>"+partenaire.toString()+"<===");
				etudiantRefImp.setPartenaire(true);
			}
			else
			{
				if (logger.isDebugEnabled())
					logger.debug("===>"+null+"<===");
				etudiantRefImp.setPartenaire(false);
			}
			marshaller.marshal(etudiantRefImp, new File(this.getXmlXslPath()+nameXml)) ;
		}
		catch (JAXBException ex)
		{
			logger.error(ex);
		}
		return nameXml;
	}

	public void genererImage(String nom, byte[] data) {
		final int BUFFER_SIZE = 6124;
		InputStream in = new ByteArrayInputStream(data);
		FileOutputStream fileOutputStream=null;
		File result = new File(nom);

		try {
			fileOutputStream = new FileOutputStream(result);

			byte[] buffer = new byte[BUFFER_SIZE];

			int bulk;
			InputStream inputStream = in;
			while (true) {
				bulk = inputStream.read(buffer);
				if (bulk < 0) {
					break;
				}
				fileOutputStream.write(buffer, 0, bulk);
				fileOutputStream.flush();
			}

			fileOutputStream.close();
			inputStream.close();
		} catch (IOException e) {
			logger.error(e);
		}
		finally {
			try {
				if(fileOutputStream!=null)
					fileOutputStream.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	public void initialiseNomenclatures() {
		List<SelectItem> communes = this.getListeCommunes();
		for (int i = 0; i < communes.size(); i++)
		{
			if (communes.get(i).getValue().equals(this.currentDemandeTransferts.getAdresse().getCodeCommune()))
				this.currentDemandeTransferts.getAdresse().setNomCommune(communes.get(i).getLabel());
		}

		if ("T".equals(this.currentDemandeTransferts.getTransferts().getTypeTransfert()))
			this.currentDemandeTransferts.getTransferts().setLibTypeTransfert("Total");
		else
			this.currentDemandeTransferts.getTransferts().setLibTypeTransfert("Partiel");

		this.currentDemandeTransferts.getTransferts().setLibDept(getDomainServiceScolarite().getEtablissementByRne(this.currentDemandeTransferts.getTransferts().getRne()).getLibDep());
		this.currentDemandeTransferts.getTransferts().setLibRne(getDomainServiceScolarite().getEtablissementByRne(this.currentDemandeTransferts.getTransferts().getRne()).getLibEtb());
	}

	public EtudiantRef initialiseTransientEtudiantRef(EtudiantRef etu)
	{
		/*
		 * map.put("libWebVet", insAdmEtpDTO[i].getEtape().getLibWebVet());
		 * map.put("codeCGE", insAdmEtpDTO[i].getCge().getCodeCGE());
		 * map.put("libCGE", insAdmEtpDTO[i].getCge().getLibCGE());
		 * map.put("codeComposante", insAdmEtpDTO[i].getComposante().getLibComposante());
		 * map.put("libComposante", insAdmEtpDTO[i].getComposante().getCodComposante());
		 * 
		 * */
		TrResultatVdiVetDTO trResultatVdiVetDTO;
		if(etu.getSource()!=null && "D".equals(etu.getSource())) {

			Map<String, String> map = getDomainServiceScolarite().getEtapePremiereAndCodeCgeAndLibCge(etu.getNumeroEtudiant());
			for (Map.Entry<String,String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if("libWebVet".equals(key))
					etu.setLibEtapePremiereLocal(value);
				if("codeCGE".equals(key))
					etu.setCodCge(value);
				if("libCGE".equals(key))
					etu.setLibCge(value);
				if("codeComposante".equals(key))
					etu.setComposante(value);
				if("libComposante".equals(key))
					etu.setLibComposante(value);
			}

			trResultatVdiVetDTO = getDomainServiceScolarite().getSessionsResultats(etu.getNumeroEtudiant(), "D");
			setRoot(getSessionController().constructTreeNode(trResultatVdiVetDTO));
		}

		return etu;
	}

	public String goToSaisirAvis() {
		if (logger.isDebugEnabled())
			logger.debug("goToSaisirAvis");

		if ((this.currentDemandeTransferts.getAdresse().getNumTel() == null
				|| "".equals(this.currentDemandeTransferts.getAdresse().getNumTel()))
				&& (this.currentDemandeTransferts.getAdresse().getNumTelPortable() == null
				|| "".equals(this.currentDemandeTransferts.getAdresse().getNumTelPortable())))
		{
			String summary = getString("ERREUR.TELEPHONE");
			String detail = getString("ERREUR.TELEPHONE");
			Severity severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			return null;
		}
		else
		{
			this.initialiseNomenclatures();
			setListeAvisByNumeroEtudiantAndAnnee(new ArrayList<Avis>());
			setSelectedFichier(new Fichier());
			setSelectedAvis(new Avis());
			setCurrentAvis(new Avis());
			return "goToSaisirAvis";
		}
	}

	public String goToSaisirDecision() {
		if (logger.isDebugEnabled())
			logger.debug("goToSaisirDecision");

		List<PersonnelComposante> lpc = null;
		try {
			lpc = getDomainService().getListeComposantesByUidAndSourceAndAnnee(getSessionController().getCurrentUser().getLogin(), "A", getSessionController().getCurrentAnnee());
		} catch (Exception e) {
			logger.error(e);
		}

		if(lpc!=null && !lpc.isEmpty())
			setTypePersonnel(lpc.get(0).getTypePersonnel());
		else
			setTypePersonnel(null);

		if ((this.currentDemandeTransferts.getAdresse().getNumTel() == null
				|| "".equals(this.currentDemandeTransferts.getAdresse().getNumTel()))
				&& (this.currentDemandeTransferts.getAdresse().getNumTelPortable() == null
				|| "".equals(this.currentDemandeTransferts.getAdresse().getNumTelPortable())))
		{
			String summary = getString("ERREUR.TELEPHONE");
			String detail = getString("ERREUR.TELEPHONE");
			Severity severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			return null;
		}
		else
		{
			currentAccueilDecision = new AccueilDecision();
			this.initialiseNomenclatures();
			return "goToSaisirDecision";
		}
	}


	public String getDateDuJour()
	{
		DateFormat fullDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		return fullDateFormat.format(new Date());
	}

	public Logger getLogger() {
		return logger;
	}

	public String getEmployeeAffiliation() {
		return employeeAffiliation;
	}

	public void setEmployeeAffiliation(String employeeAffiliation) {
		this.employeeAffiliation = employeeAffiliation;
	}

	public boolean isPersonnel() {
		return personnel;
	}

	public void setPersonnel(boolean personnel) {
		this.personnel = personnel;
	}

	public ListeTransfertDepartDataModel getListeTransfertDepartDataModel() {
		return listeTransfertDepartDataModel;
	}

	public void setListeTransfertDepartDataModel(ListeTransfertDepartDataModel listeTransfertDepartDataModel) {
		this.listeTransfertDepartDataModel = listeTransfertDepartDataModel;
	}

	public boolean isSwitchTraiteNontraite() {
		return switchTraiteNontraite;
	}

	public void setSwitchTraiteNontraite(boolean switchTraiteNontraite) {
		//		setTransfertDataModelOpi(null);
		//		setListEntrants(null);
		this.switchTraiteNontraite = switchTraiteNontraite;
	}

	public EtudiantRef getCurrentDemandeTransferts() {
		return currentDemandeTransferts;
	}

	public void setCurrentDemandeTransferts(EtudiantRef currentDemandeTransferts) {
		this.currentDemandeTransferts = currentDemandeTransferts;
	}

	public Fichier getDefautFichier() {
		return getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource());
	}

	public void setDefautFichier(Fichier defautFichier) {
		this.defautFichier = defautFichier;
	}

	public String getCodePaysItems() {
		return codePaysItems;
	}

	public void setCodePaysItems(String codePaysItems) {
		this.codePaysItems = codePaysItems;
	}

	public boolean isDeptVide() {
		return deptVide;
	}

	public void setDeptVide(boolean deptVide) {
		this.deptVide = deptVide;
	}

	public boolean isRneVide() {
		return rneVide;
	}

	public void setRneVide(boolean rneVide) {
		this.rneVide = rneVide;
	}

	public boolean isTypesDiplomeVide() {
		return typesDiplomeVide;
	}

	public void setTypesDiplomeVide(boolean typesDiplomeVide) {
		this.typesDiplomeVide = typesDiplomeVide;
	}

	public boolean isTypesDiplomeAutreVide() {
		return typesDiplomeAutreVide;
	}

	public void setTypesDiplomeAutreVide(boolean typesDiplomeAutreVide) {
		this.typesDiplomeAutreVide = typesDiplomeAutreVide;
	}

	public boolean isAnneeEtudeVide() {
		return AnneeEtudeVide;
	}

	public void setAnneeEtudeVide(boolean anneeEtudeVide) {
		AnneeEtudeVide = anneeEtudeVide;
	}

	public boolean isLibelleDiplomeVide() {
		return libelleDiplomeVide;
	}

	public void setLibelleDiplomeVide(boolean libelleDiplomeVide) {
		this.libelleDiplomeVide = libelleDiplomeVide;
	}

	public boolean isLibelleEtapeVide() {
		return libelleEtapeVide;
	}

	public void setLibelleEtapeVide(boolean libelleEtapeVide) {
		this.libelleEtapeVide = libelleEtapeVide;
	}

	public void setListeEtablissements(List<SelectItem> listeEtablissements) {
		this.listeEtablissements = listeEtablissements;
	}

	public void setListeTypesDiplome(List<SelectItem> listeTypesDiplome) {
		this.listeTypesDiplome = listeTypesDiplome;
	}

	public void setListeLibellesEtape(List<OffreDeFormationsDTO> list) {
		this.listeLibellesEtape = list;
	}

//	public void setDefaultCodeSizeAnnee(boolean defaultCodeSizeAnnee) {
//		this.defaultCodeSizeAnnee = defaultCodeSizeAnnee;
//	}

	public CodeSizeAnnee getCodeSizeAnnee() {
		return codeSizeAnnee;
	}

	public void setCodeSizeAnnee(CodeSizeAnnee codeSizeAnnee) {
		this.codeSizeAnnee = codeSizeAnnee;
	}

	public CodeSizeAnnee getDefaultCodeSize() {
		return defaultCodeSize;
	}

	public void setDefaultCodeSize(CodeSizeAnnee defaultCodeSize) {
		this.defaultCodeSize = defaultCodeSize;
	}

	public List<Avis> getListeAvisByNumeroEtudiantAndAnnee() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<Avis> getListeAvisByNumeroEtudiantAndAnnee()");
			logger.debug("getDomainServiceWS().getAvis(this.currentDemandeTransferts.getNumeroEtudiant(), getSessionController().getCurrentAnnee());");
		}
		List<Avis> lAvis = getDomainService().getAvis(this.currentDemandeTransferts.getNumeroEtudiant(), getSessionController().getCurrentAnnee());
		if(lAvis!=null)
		{
			for (Avis a : lAvis)
			{
				a.setLibEtatDossier(getDomainService().getEtatDossierById(a.getIdEtatDossier()).getLibelleLongEtatDossier());
				a.setLibLocalisationDossier(getDomainService().getLocalisationDossierById(a.getIdLocalisationDossier()).getLibelleLongLocalisationDossier());
				a.setLibDecisionDossier(getDomainService().getDecisionDossierById(a.getIdDecisionDossier()).getLibelleLongDecisionDossier());
			}
		}
		return lAvis;
	}

	public void setListeAvisByNumeroEtudiantAndAnnee(
			List<Avis> listeAvisByNumeroEtudiantAndAnnee) {
		this.listeAvisByNumeroEtudiantAndAnnee = listeAvisByNumeroEtudiantAndAnnee;
	}

	public void setSelectedFichier(Fichier selectedFichier) {
		this.selectedFichier = selectedFichier;
	}

	//	public Fichier getSelectedFichier() {
	//		return getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource());
	//	}

	public Avis getSelectedAvis() {
		return selectedAvis;
	}

	public void setSelectedAvis(Avis selectedAvis) {
		this.selectedAvis = selectedAvis;
	}

	public Avis getCurrentAvis() {
		return currentAvis;
	}

	public void setCurrentAvis(Avis currentAvis) {
		this.currentAvis = currentAvis;
	}

	public void setListeEtatsDossier(List<SelectItem> listeEtatDossier) {
		this.listeEtatsDossier = listeEtatDossier;
	}

	public List<SelectItem> getListeEtatsDossier() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeEtatsDossier()");
			logger.debug("getDomainServiceWS().getEtatsDossier();");
		}
		listeEtatsDossier = new ArrayList<SelectItem>();
		List<EtatDossier> listeEtatsDossierDTO = getDomainService().getEtatsDossier();
		if (listeEtatsDossierDTO != null)
		{
			for (EtatDossier ed : listeEtatsDossierDTO)
			{
				SelectItem option = new SelectItem(ed.getIdEtatDossier(),ed.getLibelleLongEtatDossier());
				listeEtatsDossier.add(option);
			}
			return listeEtatsDossier;
		} else {
			SelectItem option = new SelectItem("", "");
			listeEtatsDossier.add(option);
			return listeEtatsDossier;
		}
	}

	public void setListeLocalisationDossier(
			List<SelectItem> listeLocalisationDossier) {
		this.listeLocalisationDossier = listeLocalisationDossier;
	}

	public List<SelectItem> getListeLocalisationDossier() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeLocalisationDossier()");
			logger.debug("getDomainServiceWS().getLocalisationDossier();");
		}
		listeLocalisationDossier = new ArrayList<SelectItem>();
		List<LocalisationDossier> listeLocalisationDossierDTO = getDomainService().getLocalisationDossier();
		if (listeLocalisationDossierDTO != null)
		{
			for (LocalisationDossier ld : listeLocalisationDossierDTO)
			{
				SelectItem option = new SelectItem(ld.getIdLocalisationDossier(), ld.getLibelleLongLocalisationDossier());
				listeLocalisationDossier.add(option);
			}
			return listeLocalisationDossier;
		} else {
			SelectItem option = new SelectItem("", "");
			listeLocalisationDossier.add(option);
			return listeLocalisationDossier;
		}
	}

	public void setListeDecisionsDossier(List<SelectItem> listeDecisionsDossier) {
		this.listeDecisionsDossier = listeDecisionsDossier;
	}

	public List<SelectItem> getListeDecisionsDossier() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeDecisionsDossier()");
			logger.debug("getDomainServiceWS().getDecisionDossier();");
		}
		listeDecisionsDossier = new ArrayList<SelectItem>();
		List<DecisionDossier> listeDecisionsDossierDTO = getDomainService().getDecisionDossier();
		if (listeDecisionsDossierDTO != null)
		{
			for (DecisionDossier dd : listeDecisionsDossierDTO)
			{
				SelectItem option = new SelectItem(dd.getIdDecisionDossier(), dd.getLibelleLongDecisionDossier());
				listeDecisionsDossier.add(option);
			}
			return listeDecisionsDossier;
		} else {
			SelectItem option = new SelectItem("", "");
			listeDecisionsDossier.add(option);
			return listeDecisionsDossier;
		}
	}

	private void decisionAccueilFavorableToAutoSynchroOPI(IndOpi opi){
		selectedOpis = new IndOpi[1];
		selectedOpis[0] = opi;
		this.synchroOpi();
	}

	public void addAccueilDecisionDefinitif()
	{
		if (logger.isDebugEnabled())
			logger.debug("public void addAccueilDecisionDefinitif()");

		Correspondance correspondance = new Correspondance();

		try {
			/* Debut des informations sur la décision */
			Set<AccueilDecision> lAd = this.currentDemandeTransferts.getAccueilDecision();
			String decision="";
			String libDecision="";

			long tableau[] = new long[lAd.size()];
			int i=0;

			if(logger.isDebugEnabled())
				logger.debug("lAd.size() -->"+lAd.size());

			for(AccueilDecision ad : lAd)
			{
				tableau[i] = ad.getId();
				i++;
			}

			Arrays.sort(tableau);

			long id=tableau[tableau.length-1];

			for(int j=0;j<tableau.length;j++)
			{
				if(tableau[j]==0)
					id=0;
			}

			Integer feedBackDecision=0;

			for(AccueilDecision ad : lAd)
			{
				if(logger.isDebugEnabled())
				{
					logger.debug("2--ad.getEtudiant().getNomPatronymique()===>"+ad.getEtudiant().getNomPatronymique()+"<===");
					logger.debug("2--ad.getId()===>"+ad.getId()+"<===");
					logger.debug("2--ad.getAvis()===>"+ad.getAvis()+"<===");
				}

				if(ad.getId()==id)
				{
					if(logger.isDebugEnabled())
					{
						logger.debug("3--tableau[tableau.length-1]===>"+tableau[tableau.length-1]+"<===");
						logger.debug("3--ad.getId()===>"+ad.getId()+"<===");
						logger.debug("3--ad.getAvis()===>"+ad.getAvis()+"<===");
					}

					if("A".equals(ad.getAvis()))
					{
						decision="F";
						libDecision="Favorable";
						feedBackDecision=1;
					}
					else if("B".equals(ad.getAvis()))
					{
						decision="D";
						libDecision="Défavorable";
						feedBackDecision=2;
					}
					else
					{
						decision="A";
						libDecision="Autre";
					}
				}
			}

//			Décision différent de "autre"
			if(!"A".equals(decision))
			{
				if(logger.isDebugEnabled())
					logger.debug("libDecision===>"+libDecision+"<===");

				this.currentDemandeTransferts.getTransferts().setTemoinTransfertValide(2);
				this.addDemandeTransfertsFromAvis(2);
				boolean maj = false;

				//############################################################################################################################################
				this.currentDemandeTransferts.getTransferts().setTemoinOPIWs(1);
				//IndOpi opi = getDomainServiceScolarite().getInfosOpi(this.currentDemandeTransferts.getNumeroEtudiant());

				IndOpi opi = getDomainService().getIndOpiByNneAndCleIneAndAnnee(this.currentDemandeTransferts.getNumeroIne().substring(0, this.currentDemandeTransferts.getNumeroIne().length()-1), this.currentDemandeTransferts.getNumeroIne().substring(this.currentDemandeTransferts.getNumeroIne().length()-1, this.currentDemandeTransferts.getNumeroIne().length()), this.currentDemandeTransferts.getAnnee());
				String cleOpi;

				if(opi==null)
				{
					if(logger.isDebugEnabled())
						logger.debug("maj opi===>"+maj+"<===");
					opi = new IndOpi();
					cleOpi = getDomainService().getCodeSizeByAnnee(getSessionController().getCurrentAnnee()).getCode()	+ RneModuleBase36.genereCle(this.currentDemandeTransferts.getAccueil().getCodeRneUnivDepart());
					opi.setNumeroOpi(cleOpi);
					opi.getVoeux().setNumeroOpi(cleOpi);
				}
				else
				{
					maj = true;
					if(logger.isDebugEnabled())
						logger.debug("maj opi===>"+maj+"<===");
				}

				/*Ajout des données obligatoires saisies l'étudiant*/
				if("P".equals(this.currentDemandeTransferts.getAccueil().getFrom_source()))
					opi.setSource("D");
				else
					opi.setSource(getSource());
				opi.setSynchro(0);

				String ine = this.currentDemandeTransferts.getNumeroIne().toUpperCase();
				String ineSansCle = ine.substring(0, this.currentDemandeTransferts.getNumeroIne().length()-1);
				String cleIne = ine.substring(this.currentDemandeTransferts.getNumeroIne().length()-1, this.currentDemandeTransferts.getNumeroIne().length());

				if (ine.substring(ine.length()-2, ine.length()).matches("[a-zA-Z]+")){
					ineSansCle = ine.substring(0, 9);
					cleIne = ine.substring(9, ine.length());
				}

				// Mise à jour mettreajourOPI_v7 - codnneindopi = ine complet
//				opi.setCodNneIndOpi(ineSansCle);
				opi.setCodNneIndOpi(ine);
				opi.setCodCleNneIndOpi(cleIne);

				opi.setDateNaiIndOpi(this.currentDemandeTransferts.getDateNaissance());
				opi.setTemDateNaiRelOpi("N");
				opi.setLibNomPatIndOpi(this.currentDemandeTransferts.getNomPatronymique());
				opi.setLibPr1IndOpi(this.currentDemandeTransferts.getPrenom1());
				/*Fin ajout des données obligatoires saisies l'étudiant*/
				opi.setCodPay(this.currentDemandeTransferts.getAdresse().getCodPay());
				opi.setCodBdi(this.currentDemandeTransferts.getAdresse().getCodePostal());
				opi.setCodCom(this.currentDemandeTransferts.getAdresse().getCodeCommune());
				opi.setLibAd1(this.currentDemandeTransferts.getAdresse().getLibAd1());
				opi.setLibAd2(this.currentDemandeTransferts.getAdresse().getLibAd2());
				opi.setLibAd3(this.currentDemandeTransferts.getAdresse().getLibAd3());
				opi.setLibAde(this.currentDemandeTransferts.getAdresse().getCodeVilleEtranger());
				opi.setNumTel(this.currentDemandeTransferts.getAdresse().getNumTel());
				opi.setNumTelPorOpi(this.currentDemandeTransferts.getAdresse().getNumTelPortable());
				opi.setAdrMailOpi(this.currentDemandeTransferts.getAdresse().getEmail());
				opi.setEtabDepart(this.currentDemandeTransferts.getAccueil().getCodeRneUnivDepart());
				opi.setAnnee(getSessionController().getCurrentAnnee());
				opi.setCodPayNat(this.currentDemandeTransferts.getAccueil().getCodePaysNat());

				/* Debut des informations sur le baccalaureat */
				opi.setCodBac(this.currentDemandeTransferts.getAccueil().getCodeBac());
				opi.setDaabacObtOba(this.currentDemandeTransferts.getAccueil().getAnneeBac());
				/* Fin des informations sur le baccalaureat */

				opi.getVoeux().setCodDecVeu(decision);
				/* Fin des informations sur la décision */

				opi.getVoeux().setLibNomPatIndOpi(this.currentDemandeTransferts.getNomPatronymique());
				opi.getVoeux().setLibPr1IndOpi(this.currentDemandeTransferts.getPrenom1());

				/* Debut informations obligatoires sur les voeux */
				opi.getVoeux().setCodDip(currentDemandeTransferts.getTransferts().getOdf().getCodeDiplome());
				opi.getVoeux().setCodVrsVdi(currentDemandeTransferts.getTransferts().getOdf().getCodeVersionDiplome());
				opi.getVoeux().setCodCge(currentDemandeTransferts.getTransferts().getOdf().getCodeCentreGestion());
				opi.getVoeux().setCodEtp(currentDemandeTransferts.getTransferts().getOdf().getCodeEtape());
				opi.getVoeux().setCodVrsVet(currentDemandeTransferts.getTransferts().getOdf().getCodeVersionEtape());
				opi.getVoeux().setCodDemDos("C");
				opi.getVoeux().setNumCls("1");
				opi.getVoeux().setCodCmp(currentDemandeTransferts.getTransferts().getOdf().getCodeComposante());

				String summary;
				String detail;
				Severity severity;

				if(!this.isMultiple())
				{
					summary = getString("ENREGISTREMENT.ACCUEIL_DECISION");
					detail = getString("ENREGISTREMENT.ACCUEIL_DECISION");
					severity = FacesMessage.SEVERITY_INFO;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
				}

				TrEtablissementDTO etab =  getDomainServiceScolarite().getEtablissementByRne(getSessionController().getRne());
				String libEtab = etab.getLibOffEtb()+" <BR /> "+
						etab.getLibAd1Etb()+" <BR /> "+
						etab.getCodPosAdrEtb()+" "+etab.getLibAch();

				String sujet;
				String body;

				if (logger.isDebugEnabled())
				{
					logger.debug("Decision -->"+decision);
					logger.debug("exclueEtpOpi -->"+exclueEtpOpi);
					logger.debug("this.currentDemandeTransferts.getTransferts().getOdf().getCodeEtape() -->"+this.currentDemandeTransferts.getTransferts().getOdf().getCodeEtape());
					logger.debug("exclueBacOpi -->"+exclueBacOpi);
					logger.debug("this.currentDemandeTransferts.getAccueil().getCodeBac() -->"+this.currentDemandeTransferts.getAccueil().getCodeBac());
					logger.debug("Candidature -->"+this.isInterditNiveau2());
				}
				if(!exclueBacOpi.contains(this.currentDemandeTransferts.getAccueil().getCodeBac()) || "".equals(exclueBacOpi))
				{
					if(this.isInterditNiveau2())
					{
						opi.setSynchro(3);
						/*
						 * Module accueil activé
						 * */
						if(getSessionController().isTransfertsAccueil())
						{
							if("F".equals(decision))
							{
								/*
								 * Pour les étudiants avec procédure e-candidat / accès via les applis – départ des universités partenaires
								 * */
								if("P".equals(this.currentDemandeTransferts.getAccueil().getFrom_source()))
								{
									if (logger.isDebugEnabled())
										logger.debug("mail===>Pour les étudiants avec procédure e-candidat / accès via les applis – départ des universités partenaires<===");

									sujet = getString("DECISION.MAIL.SUJET");
									body = getString("DECISION.MAIL.BODY_CANDIDATURE_AVIS_F_PARTENAIRE",
											this.currentDemandeTransferts.getPrenom1(),
											this.currentDemandeTransferts.getNomPatronymique(),
											//											this.getListeAccueilDecision().get(0).getDecision(),
											libDecision,
											libEtab,
											etab.getLibOffEtb());
								}
								else
								{
									/*
									 * Pour les étudiants avec procédure e-candidat / accès direct à l’appli Artois - non partenaire
									 * */
									if (logger.isDebugEnabled())
										logger.debug("mail===>Pour les étudiants avec procédure e-candidat / accès direct à l’appli Artois - non partenaire<===");

									sujet = getString("DECISION.MAIL.SUJET");
									body = getString("DECISION.MAIL.BODY_CANDIDATURE_AVIS_F_NON_PARTENAIRE",
											this.currentDemandeTransferts.getPrenom1(),
											this.currentDemandeTransferts.getNomPatronymique(),
											//											this.getListeAccueilDecision().get(0).getDecision(),
											libDecision,
											libEtab,
											etab.getLibOffEtb());
								}
								getDomainService().addIndOpi(opi, maj);
								if(getSessionController().isDecisionAccueilFavorableToAutoSynchroOPI())
									decisionAccueilFavorableToAutoSynchroOPI(opi);
							}
							else
							{
								/*
								 * Mail reçu par un candidat ayant reçu un avis défavorable AVEC E-CANDIDAT
								 * */
								if (logger.isDebugEnabled())
									logger.debug("mail===>Mail reçu par un candidat ayant reçu un avis défavorable AVEC E-CANDIDAT<===");
								sujet = getString("DECISION.MAIL.SUJET");
								body = getString("DECISION.MAIL.BODY_AVIS_D_CANDIDATURE",
										this.currentDemandeTransferts.getPrenom1(),
										this.currentDemandeTransferts.getNomPatronymique(),
										//										this.getListeAccueilDecision().get(0).getDecision(),
										libDecision,
										libEtab,
										etab.getLibOffEtb());
							}

							this.envoiMail(opi, sujet, body, correspondance);

							try {
								getSmtpService().send(new InternetAddress(this.currentDemandeTransferts.getAdresse().getEmail()), sujet, body, body);
							}
							catch (AddressException e)
							{
								logger.error(e);
								summary = getString("ERREUR.ENVOI_MAIL");
								detail = getString("ERREUR.ENVOI_MAIL");
								severity = FacesMessage.SEVERITY_INFO;
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
							}
							try {
								correspondance.setEtudiant(this.currentDemandeTransferts);
								correspondance.setAuteur(getSessionController().getCurrentUser().getDisplayName());
								correspondance.setDateSaisie(new Date());
								correspondance.setTitre(sujet);
								correspondance.setMsg(body);
							} catch (Exception e1) {
								logger.error(e1);
							}
							//Ajout d'une nouvelle correspondance
							this.addCorrespondance(correspondance);
							getDomainService().addDemandeTransferts(this.currentDemandeTransferts);
							//Fin d'ajout d'une nouvelle correspondance
						}
						else
						{
							if("F".equals(decision))
								getDomainService().addIndOpi(opi, maj);
								if(getSessionController().isDecisionAccueilFavorableToAutoSynchroOPI())
									decisionAccueilFavorableToAutoSynchroOPI(opi);

						}
					}
					else
					{
						if(getSessionController().isTransfertsAccueil())
						{
							if("F".equals(decision))
							{
								if("P".equals(this.currentDemandeTransferts.getAccueil().getFrom_source()))
								{
									/*
									 * Pour les étudiants sans procédure e-candidat / accès via les applis – départ des universités partenaires - partenaire
									 * */
									if (logger.isDebugEnabled())
										logger.debug("mail===>Pour les étudiants sans procédure e-candidat / accès via les applis – départ des universités partenaires - partenaire<===");
									sujet = getString("DECISION.MAIL.SUJET");
									body = getString("DECISION.MAIL.BODY_AVIS_F_PARTENAIRE",
											this.currentDemandeTransferts.getPrenom1(),
											this.currentDemandeTransferts.getNomPatronymique(),
											//											this.getListeAccueilDecision().get(0).getDecision(),
											libDecision,
											libEtab,
											etab.getLibOffEtb());
								}
								else
								{
									/*
									 * Pour les étudiants sans procédure e-candidat / accès direct à l’appli Artois - non partenaire
									 * */
									if (logger.isDebugEnabled())
										logger.debug("mail===>Pour les étudiants sans procédure e-candidat / accès direct à l’appli Artois - non partenaire<===");
									sujet = getString("DECISION.MAIL.SUJET");
									body = getString("DECISION.MAIL.BODY_AVIS_F_NON_PARTENAIRE",
											this.currentDemandeTransferts.getPrenom1(),
											this.currentDemandeTransferts.getNomPatronymique(),
											//											this.getListeAccueilDecision().get(0).getDecision(),
											libDecision,
											libEtab,
											etab.getLibOffEtb());
								}
								getDomainService().addIndOpi(opi, maj);
								if(getSessionController().isDecisionAccueilFavorableToAutoSynchroOPI()) {
									selectedOpis = new IndOpi[1];
									selectedOpis[0] = opi;
									this.synchroOpi();
								}
							}
							else
							{
								/*
								 * Mail reçu par un candidat ayant reçu un avis défavorable SANS E-CANDIDAT 
								 * */
								if (logger.isDebugEnabled())
									logger.debug("mail===>Mail reçu par un candidat ayant reçu un avis défavorable SANS E-CANDIDAT<===");
								sujet = getString("DECISION.MAIL.SUJET");
								body = getString("DECISION.MAIL.BODY_AVIS_D",
										this.currentDemandeTransferts.getPrenom1(),
										this.currentDemandeTransferts.getNomPatronymique(),
										libDecision,
										libEtab,
										etab.getLibOffEtb());
							}
							try {
								getSmtpService().send(new InternetAddress(this.currentDemandeTransferts.getAdresse().getEmail()), sujet, body, body);
							}
							catch (AddressException e)
							{
								logger.error(e);
								summary = getString("ERREUR.ENVOI_MAIL");
								detail = getString("ERREUR.ENVOI_MAIL");
								severity = FacesMessage.SEVERITY_INFO;
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
							}
							try {
								correspondance.setEtudiant(this.currentDemandeTransferts);
								correspondance.setAuteur(getSessionController().getCurrentUser().getDisplayName());
								correspondance.setDateSaisie(new Date());
								correspondance.setTitre(sujet);
								correspondance.setMsg(body);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								logger.error(e1);
							}
							//Ajout d'une nouvelle correspondance
							this.addCorrespondance(correspondance);
							getDomainService().addDemandeTransferts(this.currentDemandeTransferts);
							//Fin d'ajout d'une nouvelle correspondance
						}
						else
						{
							if("F".equals(decision)) {
								getDomainService().addIndOpi(opi, maj);
								if(getSessionController().isDecisionAccueilFavorableToAutoSynchroOPI()) {
									selectedOpis = new IndOpi[1];
									selectedOpis[0] = opi;
									this.synchroOpi();
								}
							}
						}
					}
				}
				else
				{
					if(getSessionController().isTransfertsAccueil())
					{
						if("F".equals(decision))
						{
							sujet = getString("DECISION.MAIL.SUJET");
							body = getString("DECISION.MAIL.BODY_EXCLU_BAC",
									this.currentDemandeTransferts.getPrenom1(),
									this.currentDemandeTransferts.getNomPatronymique(),
									//									this.getListeAccueilDecision().get(0).getDecision(),
									libDecision,
									libEtab,
									etab.getLibOffEtb());

							try {
								getSmtpService().send(new InternetAddress(this.currentDemandeTransferts.getAdresse().getEmail()), sujet, body, body);
							}
							catch (AddressException e)
							{
								logger.error(e);
								summary = getString("ERREUR.ENVOI_MAIL");
								detail = getString("ERREUR.ENVOI_MAIL");
								severity = FacesMessage.SEVERITY_INFO;
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
							}
							try {
								correspondance.setEtudiant(this.currentDemandeTransferts);
								correspondance.setAuteur(getSessionController().getCurrentUser().getDisplayName());
								correspondance.setDateSaisie(new Date());
								correspondance.setTitre(getString("TRANSFERT_MAIL_SUJET"));
								correspondance.setMsg(getString("TRANSFERT_MAIL_BODY", this.currentDemandeTransferts.getPrenom1(), this.currentDemandeTransferts.getNomPatronymique()));
							} catch (Exception e1) {
								logger.error(e1);
							}
							//Ajout d'une nouvelle correspondance
							this.addCorrespondance(correspondance);
							getDomainService().addDemandeTransferts(this.currentDemandeTransferts);
							//Fin d'ajout d'une nouvelle correspondance
						}
					}
				}

				WsPub p = getDomainService().getWsPubByRneAndAnnee(currentDemandeTransferts.getAccueil().getCodeRneUnivDepart(), getSessionController().getCurrentAnnee());

				// Appel du WebService de l'universite de départ
				if (p != null) {
					Integer etatConnexion;
					Object tabReturn[] = Fonctions.appelWSAuth(p.getUrl(),
							p.getIdentifiant(),
							p.getPassword(),
							"org.esupportail.transferts.domain.DomainServiceOpi",
							"addFeedBackFromTransfertAccueilToTransfertDepart",
							"object",
							getSessionController().getTimeOutConnexionWs(),
							this.currentDemandeTransferts.getNumeroIne(),
							getSessionController().getCurrentAnnee(),
							"D",
							feedBackDecision);

					Integer ret=2;
					etatConnexion = (Integer) tabReturn[1];
					if(etatConnexion==1 && tabReturn[0]!=null)
						ret = (Integer) tabReturn[0];

					if (logger.isDebugEnabled()) {
						logger.debug("etatConnexion===>" + etatConnexion + "<===");
						logger.debug("ret===>" + ret + "<===");
					}

					this.currentDemandeTransferts.getTransferts().setTemoinOPIWs(ret);

					if(etatConnexion==1)
					{
						String summary2 = "L'information concernant le dossier a été transmis à l'établissement de départ";
						String detail2 = "L'information concernant le dossier a été transmis à l'établissement de départ";
						Severity severity2 = FacesMessage.SEVERITY_INFO;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity2, summary2, detail2));
					}
					else{
						String summary3 = getString("ERREUR.ACCES_OPI3");
						String detail3 = getString("ERREUR.ACCES_OPI3");
						Severity severity3 = FacesMessage.SEVERITY_ERROR;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity3, summary3, detail3));
					}
				}
				else
					this.currentDemandeTransferts.getTransferts().setTemoinOPIWs(0);

				getDomainService().addDemandeTransferts(this.currentDemandeTransferts);
			}
			else
			{
				String sujet = getString("DECISION.MAIL.SUJET");
				String body = getString("DECISION.MAIL.BODY_AVIS_A",
						this.currentDemandeTransferts.getPrenom1(),
						this.currentDemandeTransferts.getNomPatronymique(),
						//						this.getListeAccueilDecision().get(0).getDecision(),
						libDecision);
				try {
					getSmtpService().send(new InternetAddress(this.currentDemandeTransferts.getAdresse().getEmail()), sujet, body, body);
				}
				catch (AddressException e)
				{
					logger.error(e);
					String summary = getString("ERREUR.ENVOI_MAIL");
					String detail = getString("ERREUR.ENVOI_MAIL");
					Severity severity = FacesMessage.SEVERITY_INFO;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
				}
				try {
					correspondance.setEtudiant(this.currentDemandeTransferts);
					correspondance.setAuteur(getSessionController().getCurrentUser().getDisplayName());
					correspondance.setDateSaisie(new Date());
					correspondance.setTitre(getString("TRANSFERT_MAIL_SUJET"));
					correspondance.setMsg(getString("TRANSFERT_MAIL_BODY", this.currentDemandeTransferts.getPrenom1(), this.currentDemandeTransferts.getNomPatronymique()));
				} catch (Exception e1) {
					logger.error(e1);
				}
				//Ajout d'une nouvelle correspondance
				this.addCorrespondance(correspondance);
				getDomainService().addDemandeTransferts(this.currentDemandeTransferts);
				//Fin d'ajout d'une nouvelle correspondance
			}

		} catch (Exception e) {
			logger.error(e);
			String summary = "Une erreur s'est produite lors de l'enregistrement d'une decision ("+this.currentDemandeTransferts.getNumeroIne()+")";
			String detail = "Une erreur s'est produite lors de l'enregistrement d'une decision ("+this.currentDemandeTransferts.getNumeroIne()+")";
			Severity severity = FacesMessage.SEVERITY_FATAL;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
	}

	public void addAccueilDecision()
	{
		if (logger.isDebugEnabled())
			logger.debug("===>public void addAccueilDecision()<===");

		if(this.currentAccueilDecision!=null && this.currentAccueilDecision.getAvis()!="")
		{
			if (logger.isDebugEnabled())
				logger.debug("===>if(this.currentAccueilDecision!=null && this.currentAccueilDecision.getAvis()!='')<===");

			try {
				currentAccueilDecision.setEtudiant(this.currentDemandeTransferts);
				currentAccueilDecision.setAuteur(getSessionController().getCurrentUser().getLogin());
				currentAccueilDecision.setDateSaisie(new Date());
				this.currentDemandeTransferts.getAccueilDecision().add(this.currentAccueilDecision);

				this.currentDemandeTransferts = this.addDemandeTransfertsFromAvis(1);

				this.currentAccueilDecision = new AccueilDecision();
				
				if (!getSessionController().isTransfertsAccueil())
				{
					this.addAccueilDecisionDefinitif();
				}

				if (logger.isDebugEnabled())
					logger.debug("===>this.currentAccueilDecision = new AccueilDecision();<===");

				String summary = getString("ENREGISTREMENT.ACCUEIL_DECISION");
				String detail = getString("ENREGISTREMENT.ACCUEIL_DECISION");
				Severity severity = FacesMessage.SEVERITY_INFO;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			} catch (Exception e) {
				logger.error(e);
				String summary = "Une erreur s'est produite lors de l'enregistrement d'une decision";
				String detail = "Une erreur s'est produite lors de l'enregistrement d'une decision";
				Severity severity = FacesMessage.SEVERITY_FATAL;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			}
		}
		else
		{
			String summary = getString("ERREUR.DECISION_DOSSIER");
			String detail = getString("ERREUR.DECISION_DOSSIER");
			Severity severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
	}

	public void addAvis() {
		currentAvis.setNumeroEtudiant(currentDemandeTransferts.getNumeroEtudiant());
		currentAvis.setAnnee(getSessionController().getCurrentAnnee());
		currentAvis.setDateSaisie(new Date());
		getDomainService().addAvis(currentAvis);
		currentAvis = new Avis();
		this.addDemandeTransfertsFromAvis(1);
		String summary = getString("ENREGISTREMENT.AVIS");
		String detail = getString("ENREGISTREMENT.AVIS");
		Severity severity = FacesMessage.SEVERITY_INFO;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public void addTransfertOpiToListeTransfertsAccueil()
	{
		if (logger.isDebugEnabled())
			logger.debug("addtransfertOpiToListeTransfertAccueil");

		WsPub p = getDomainService().getWsPubByRneAndAnnee(currentDemandeTransferts.getTransferts().getRne(), getSessionController().getCurrentAnnee());

		// Appel du WebService de l'universite d'accueil
		if (p != null)
		{
			EtudiantRef etu = getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance(this.currentDemandeTransferts.getNumeroEtudiant(), getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource());
			if(etu!=null)
			{
				etu.setNumeroEtudiant(etu.getNumeroIne());
				etu.getAdresse().setNumeroEtudiant(etu.getNumeroIne());
				etu.getTransferts().setNumeroEtudiant(etu.getNumeroIne());
				InfosAccueil ia = new InfosAccueil();
						/*Initialisation des variables pour TESTS*/
				//					ia.setNumeroEtudiant(numeroEtudiant);
						/*Fin d'initialisation des variables pour TESTS*/
				ia.setNumeroEtudiant(etu.getNumeroIne());
				ia.setAnnee(etu.getAnnee());

				TrResultatVdiVetDTO sessionsResultats = getDomainServiceScolarite().getSessionsResultats(this.currentDemandeTransferts.getNumeroEtudiant(), "A");
				TrBac bac = getDomainServiceScolarite().getBaccalaureat(this.currentDemandeTransferts.getNumeroEtudiant());
				TrInfosAdmEtu trInfosAdmEtu = getDomainServiceScolarite().getInfosAdmEtu(this.currentDemandeTransferts.getNumeroEtudiant());
				TrEtablissementDTO trEtablissementDTO = getDomainServiceScolarite().getEtablissementByRne(getSessionController().getRne());

				List<SituationUniversitaire> listeSituationUniversitaire = new ArrayList<SituationUniversitaire>();
				if(!sessionsResultats.getEtapes().isEmpty())
				{
					int i=0;
					for(ResultatEtape re :  sessionsResultats.getEtapes())
					{
						if (logger.isDebugEnabled())
							logger.debug("re.getLibEtape() : " + re.getLibEtape());
						boolean test=true;

						for(ResultatSession rs : re.getSession())
						{
							if (logger.isDebugEnabled())
							{
								logger.debug("re.getSession().size() : " + re.getSession().size());
								logger.debug("re.getSession() : " + re.getSession());
							}

							if(rs.getResultat()!=null && !"".equals(rs.getResultat()))
							{
								test=false;
								SituationUniversitaire su = new SituationUniversitaire();
								String timestamp = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
								su.setId(etu.getNumeroIne()+"_"+timestamp+"_P"+i);
								i++;
								su.setLibAccueilAnnee(re.getAnnee());
								su.setLibelle(re.getLibEtape());
								su.setLibAccueilResultat(rs.getLibSession()+" - "+rs.getResultat());
								Integer idAccueilAnnee=0;
								AccueilAnnee aa = getDomainService().getAccueilAnneeByIdAccueilAnnee(idAccueilAnnee);
								Integer idAccueilResultat=0;
								AccueilResultat ar = getDomainService().getAccueilResultatByIdAccueilResultat(idAccueilResultat);
								su.setAnnee(aa);
								su.setResultat(ar);
								listeSituationUniversitaire.add(su);
							}
						}
						if(test)
						{
							SituationUniversitaire su = new SituationUniversitaire();
							String timestamp = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
							su.setId(etu.getNumeroIne()+"_"+timestamp+"_P"+i);
							i++;
							su.setLibAccueilAnnee(re.getAnnee());
							su.setLibelle(re.getLibEtape());
							su.setLibAccueilResultat("");
							Integer idAccueilAnnee=0;
							AccueilAnnee aa = getDomainService().getAccueilAnneeByIdAccueilAnnee(idAccueilAnnee);
							Integer idAccueilResultat=0;
							AccueilResultat ar = getDomainService().getAccueilResultatByIdAccueilResultat(idAccueilResultat);
							su.setAnnee(aa);
							su.setResultat(ar);
							listeSituationUniversitaire.add(su);
						}
					}
				}
				ia.setSituationUniversitaire(listeSituationUniversitaire);
				ia.setFrom_source("P");
				if(bac!=null)
				{
					ia.setAnneeBac(bac.getAnneeObtentionBac());
					ia.setCodeBac(bac.getCodeBac());
				}
				if(trInfosAdmEtu!=null)
					ia.setCodePaysNat(trInfosAdmEtu.getCodPayNat());
				ia.setCodeRneUnivDepart(trEtablissementDTO.getCodeEtb());
				ia.setCodeDepUnivDepart(trEtablissementDTO.getCodeDep());
				ia.setValidationOuCandidature(0);
				etu.setAccueil(ia);
				etu.setSource("A");
				etu.getTransferts().setFichier(null);
				etu.getTransferts().setRne(p.getRne());
				etu.getTransferts().setTemoinTransfertValide(0);
				etu.getTransferts().setTemoinOPIWs(0);
				etu.getTransferts().getOdf().setRne(p.getRne());
				etu.setCorrespondances(null);

				Object tabReturn[] = Fonctions.appelWSAuth(p.getUrl(),
						p.getIdentifiant(),
						p.getPassword(),
						"org.esupportail.transferts.domain.DomainServiceOpi",
						"addTransfertOpiToListeTransfertsAccueil",
						"object",
						getSessionController().getTimeOutConnexionWs(),
						etu);

				Integer etatConnexion = (Integer) tabReturn[1];

				if(etatConnexion==1)
				{
					this.currentDemandeTransferts.getTransferts().setTemoinTransfertValide(2);
					this.currentDemandeTransferts.getTransferts().setTemoinOPIWs(1);
					String summary = getString("ENVOI.OPI");
					String detail = getString("ENVOI.OPI");
					Severity severity = FacesMessage.SEVERITY_INFO;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
				}
				else{
					this.currentDemandeTransferts.getTransferts().setTemoinOPIWs(2);
					this.currentDemandeTransferts.getTransferts().setTemoinTransfertValide(2);
					String summary = getString("ERREUR.ACCES_OPI3");
					String detail = getString("ERREUR.ACCES_OPI3");
					Severity severity = FacesMessage.SEVERITY_ERROR;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
				}
			}
			else
			{
				String summary = getString("ERREUR.ETUDIANT_BDD");
				String detail = getString("ERREUR.ETUDIANT_BDD");
				if (logger.isDebugEnabled())
					logger.debug("Aucun etudiant corresondant a l'INE suivant : "+this.currentDemandeTransferts.getNumeroIne());
				Severity severity = FacesMessage.SEVERITY_FATAL;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getDomainServiceWS : " + p);
				logger.debug("domainServiceWSOpiExt : "	+ domainServiceWSOpiExt);
			}
		}
		else
		{
			this.currentDemandeTransferts.getTransferts().setTemoinOPIWs(0);
			String summary = getString("WARNING.ETABLISSEMENT_NON_PARTENAIRE");
			String detail = getString("WARNING.ETABLISSEMENT_NON_PARTENAIRE");
			Severity severity = FacesMessage.SEVERITY_WARN;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
		this.currentDemandeTransferts.getTransferts().setFichier(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource()));
		getDomainService().addDemandeTransferts(this.currentDemandeTransferts);
	}

	public void addCorrespondance(Correspondance corresp){
		if(corresp!=null && this.currentDemandeTransferts.getCorrespondances()!=null)
		{
			this.currentDemandeTransferts.getCorrespondances().add(corresp);
		}
		else
		{
			Set<Correspondance> lc = new HashSet<Correspondance>();
			lc.add(corresp);
			this.currentDemandeTransferts.setCorrespondances(lc);
		}
	}

	public void addAvisFavorable() {
		if (logger.isDebugEnabled())
			logger.debug("===>public void addAvisFavorable()<===");
		currentAvis.setNumeroEtudiant(currentDemandeTransferts.getNumeroEtudiant());
		currentAvis.setAnnee(getSessionController().getCurrentAnnee());
		this.currentDemandeTransferts.getTransferts().setTemoinTransfertValide(2);
		currentAvis.setDateSaisie(new Date());
		getDomainService().addAvis(this.currentAvis);
		currentAvis = new Avis();

		if (logger.isDebugEnabled())
			logger.debug("currentDemandeTransferts.getCorrespondances().size()===>"+currentDemandeTransferts.getCorrespondances().size()+"<===");

		Correspondance correspondance = new Correspondance();
		try {
			correspondance.setEtudiant(this.currentDemandeTransferts);
			correspondance.setAuteur(getSessionController().getCurrentUser().getDisplayName());
			correspondance.setDateSaisie(new Date());
//			correspondance.setTitre(getString("TRANSFERT_MAIL_SUJET"));
//			correspondance.setMsg(getString("TRANSFERT_MAIL_BODY", this.currentDemandeTransferts.getPrenom1(), this.currentDemandeTransferts.getNomPatronymique()));
			correspondance.setTitre(getString("TRANSFERT_MAIL_SUJET"));
			correspondance.setMsg(getString("TRANSFERT_MAIL_BODY", this.currentDemandeTransferts.getPrenom1(), this.currentDemandeTransferts.getNomPatronymique()));
		} catch (Exception e1) {
			logger.error(e1);
		}
		this.addCorrespondance(correspondance);
		this.currentDemandeTransferts = this.addDemandeTransfertsFromAvis(2);
		this.addTransfertOpi();

		String summary = getString("ENREGISTREMENT.AVIS");
		String detail = getString("ENREGISTREMENT.AVIS");
		Severity severity = FacesMessage.SEVERITY_INFO;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));

		String sujet = getString("TRANSFERT_MAIL_SUJET");
		String body = getString("TRANSFERT_MAIL_BODY");
		try {
			body = getString("TRANSFERT_MAIL_BODY", this.currentDemandeTransferts.getPrenom1(),
					this.currentDemandeTransferts.getNomPatronymique());

			getSmtpService().send(new InternetAddress(this.currentDemandeTransferts.getAdresse().getEmail()), sujet, body, body);
		}
		catch (AddressException e)
		{
			logger.error(e);
			summary = getString("ERREUR.ENVOI_MAIL");
			detail = getString("ERREUR.ENVOI_MAIL");
			severity = FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
		setListeTransfertDepartDataModel(null);
	}

	public void addAvisOuDecisionMultiple() {
		if (logger.isDebugEnabled())
		{
			logger.debug("############################################# -->public Integer getProgress()");
			logger.debug("############################################# -->getSelectedDemandeTransferts() --> "+getSelectedDemandeTransferts());
		}
		this.setMultiple(true);
		String test="";
		if(getSelectedDemandeTransferts() !=null && getSelectedDemandeTransferts().length>0)
		{
			if (logger.isDebugEnabled())
				logger.debug("if(getSelectedDemandeTransferts() !=null && getSelectedDemandeTransferts().length>0 && progress==0) -->"+getSelectedDemandeTransferts().length+"-----"+progress);

			EtudiantRef[] etu = getSelectedDemandeTransferts();

			if("D".equals(this.getSource()))
			{
				setCurrentAvisMultiple(getCurrentAvis());
				for(int i=0 ; i<etu.length; i++)
				{
					if (logger.isDebugEnabled())
						logger.debug("Numero etudiant ----- nom-->"+etu[i].getNumeroEtudiant()+"-----"+etu[i].getNomPatronymique());

					test+=etu[i].getNomPatronymique()+"-";

					this.currentDemandeTransferts=etu[i];

					this.currentDemandeTransferts = getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(this.currentDemandeTransferts.getNumeroEtudiant(), this.currentDemandeTransferts.getAnnee(), this.currentDemandeTransferts.getSource());
					Correspondance correspondance = new Correspondance();
					correspondance.setEtudiant(this.currentDemandeTransferts);
					correspondance.setAuteur("Application");
					correspondance.setDateSaisie(new Date());
					correspondance.setTitre(getString("TRANSFERT_MAIL_SUJET"));
					correspondance.setMsg(getString("TRANSFERT_MAIL_BODY", this.currentDemandeTransferts.getPrenom1(), this.currentDemandeTransferts.getNomPatronymique()));
					this.addCorrespondance(correspondance);

					if(this.currentDemandeTransferts.getTransferts().getTemoinOPIWs()!=null && this.currentDemandeTransferts.getTransferts().getTemoinOPIWs()==2)
					{
						if (logger.isDebugEnabled())
							logger.debug("Reload OPI -->"+this.currentDemandeTransferts.getNumeroEtudiant()+"-----"+this.currentDemandeTransferts.getNomPatronymique());
						this.addTransfertOpi();
					}
					else
					{
						if (logger.isDebugEnabled())
							logger.debug("Validation demande de transferts -->"+this.currentDemandeTransferts.getNumeroEtudiant()+"-----"+this.currentDemandeTransferts.getNomPatronymique());
						currentAvisMultiple.setNumeroEtudiant(this.currentDemandeTransferts.getNumeroEtudiant());
						currentAvisMultiple.setAnnee(getSessionController().getCurrentAnnee());
						this.currentDemandeTransferts.getTransferts().setTemoinTransfertValide(2);
						currentAvisMultiple.setDateSaisie(new Date());

						if (logger.isDebugEnabled())
							logger.debug("##################################### currentAvis -->"+currentAvisMultiple.toString());

						getDomainService().addAvis(currentAvisMultiple);

						setCodePaysItems(etu[i].getAdresse().getCodPay());

						this.currentDemandeTransferts.getTransferts().setFichier(getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource()));
						this.addTransfertOpi();

						String sujet = getString("TRANSFERT_MAIL_SUJET");
						String body = getString("TRANSFERT_MAIL_BODY");
						try {
							body = getString("TRANSFERT_MAIL_BODY", this.currentDemandeTransferts.getPrenom1(),
									this.currentDemandeTransferts.getNomPatronymique());

							if(this.currentDemandeTransferts.getAdresse().getEmail()!=null)
								getSmtpService().send(new InternetAddress(this.currentDemandeTransferts.getAdresse().getEmail()), sujet, body, body);
						}
						catch (AddressException e)
						{
							logger.error(e);
							String summary = getString("ERREUR.ENVOI_MAIL");
							String detail = getString("ERREUR.ENVOI_MAIL");
							Severity severity = FacesMessage.SEVERITY_INFO;
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
						}

						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							logger.error(e);
							Thread.currentThread().interrupt();
						}
					}
				}
			}
			else if("A".equals(this.getSource()))
			{
				setCurrentDecisionMultiple(getCurrentAccueilDecision());
				for(int i=0 ; i<etu.length; i++)
				{
					if (logger.isDebugEnabled())
						logger.debug("Numero etudiant ----- nom-->"+etu[i].getNumeroEtudiant()+"-----"+etu[i].getNomPatronymique());

					test+=etu[i].getNomPatronymique()+"-";

					this.currentDemandeTransferts=etu[i];

					if (logger.isDebugEnabled())
						logger.debug("Validation demande de transferts -->"+this.currentDemandeTransferts.getNumeroEtudiant()+"-----"+this.currentDemandeTransferts.getNomPatronymique());
					currentDecisionMultiple.setEtudiant(this.currentDemandeTransferts);
					try {
						currentDecisionMultiple.setAuteur(getSessionController().getCurrentUser().getLogin());
						currentDecisionMultiple.setAvis(this.currentAccueilDecision.getAvis());
						currentDecisionMultiple.setDateSaisie(new Date());
						currentDecisionMultiple.setDecision(this.currentAccueilDecision.getDecision());
					} catch (Exception e1) {
						logger.error(e1);
					}

					if (logger.isDebugEnabled())
						logger.debug("##################################### currentDecisionMultiple -->"+currentDecisionMultiple.toString());

					this.currentDemandeTransferts.getAccueilDecision().add(currentDecisionMultiple);
					setCodePaysItems(etu[i].getAdresse().getCodPay());

					if(this.currentDemandeTransferts.getAccueil().getCodeBac()!=null)
						this.addAccueilDecisionDefinitif();
					else
						test+="(Manque le code bac)";
				}
			}
			if (logger.isDebugEnabled())
				logger.debug("Liste des etudiants-->"+test);

			String summary = test;
			String detail = test;
			Severity severity = FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
		else
		{
			String summary = "Vous devez selectionner au moins une demande de transfert";
			String detail = "Vous devez selectionner au moins une demande de transfert";
			Severity severity = FacesMessage.SEVERITY_WARN;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}

		setListeTransfertDepartDataModel(null);
		setSelectedDemandeTransferts(null);

		if("D".equals(this.getSource()))
			setCurrentAvis(new Avis());
		else if("A".equals(this.getSource()))
			setCurrentAccueilDecision(new AccueilDecision());

		this.setMultiple(false);
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public void addDemandeTransferts()
	{
		if (logger.isDebugEnabled())
			logger.debug("ccccc-----else if (this.currentDemandeTransferts.getTransferts().getFichier()==null)===>"+this.currentDemandeTransferts.getTransferts().getFichier()+"<===");

		if("D".equals(getSource()))
			this.currentDemandeTransferts.setAccueil(null);

		if ((this.currentDemandeTransferts.getAdresse().getNumTel() == null || "".equals(this.currentDemandeTransferts.getAdresse().getNumTel()))
				&& (this.currentDemandeTransferts.getAdresse().getNumTelPortable() == null || "".equals(this.currentDemandeTransferts.getAdresse().getNumTelPortable())))
		{
			String summary = getString("ERREUR.TELEPHONE");
			String detail = getString("ERREUR.TELEPHONE");
			Severity severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
		else if (currentOdf==null && isPartenaire())
		{
			String summary = getString("ERREUR.PAS_DE_PARCOURS_SELECTIONNE");
			String detail = getString("ERREUR.PAS_DE_PARCOURS_SELECTIONNE");
			Severity severity=FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
		else if (this.currentDemandeTransferts.getTransferts().getFichier()==null && this.getSelectedmd5()==null)
		{
			if (logger.isDebugEnabled())
				logger.debug("ddddd-----else if (this.currentDemandeTransferts.getTransferts().getFichier()==null)===>"+this.currentDemandeTransferts.getTransferts().getFichier()+"<===");
			String summary = "Pas de signature";
			String detail ="Pas de signature";
			Severity severity=FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
		else
		{
			this.currentDemandeTransferts.getTransferts().setFichier(getDomainService().getFichierByIdAndAnneeAndFrom(this.getSelectedmd5(),this.currentDemandeTransferts.getAnnee(), this.currentDemandeTransferts.getSource()));
			if (logger.isDebugEnabled()) {
				if (this.currentAvis != null)
					logger.debug("currentAvis : " + this.currentAvis.toString());
			}

			if ("100".equals(this.getCodePaysItems()))
			{
				this.currentDemandeTransferts.getAdresse().setCodeVilleEtranger(null);
				this.currentDemandeTransferts.getAdresse().setCodPay(this.getCodePaysItems());
				this.currentDemandeTransferts.getAdresse().setLibPay(getDomainServiceScolarite().getPaysByCodePays(this.getCodePaysItems()).getLibPay());
			}
			else
			{
				this.currentDemandeTransferts.getAdresse().setCodePostal(null);
				this.currentDemandeTransferts.getAdresse().setCodeCommune(null);
				this.currentDemandeTransferts.getAdresse().setNomCommune(null);
				this.currentDemandeTransferts.getAdresse().setLibPay(getDomainServiceScolarite().getPaysByCodePays(this.currentDemandeTransferts.getAdresse().getCodPay()).getLibPay());
			}
			if(isPartenaire())
				currentDemandeTransferts.getTransferts().setLibelleTypeDiplome(null);
			currentDemandeTransferts.getTransferts().setOdf(currentOdf);
			getDomainService().addDemandeTransferts(this.currentDemandeTransferts);

			if("A".equals(this.currentDemandeTransferts.getSource()))
			{
				getDomainService().deleteSituationUniversitaireByNumeroEtudiantAndAnneeIsNull();
				currentDemandeTransferts=getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(this.currentDemandeTransferts.getNumeroEtudiant(), getSessionController().getCurrentAnnee(), this.currentDemandeTransferts.getSource());
				this.currentDemandeTransferts.getAccueil().setSituationUniversitaire(getDomainService().getSituationUniversitaireByNumeroEtudiantAndAnnee(this.currentDemandeTransferts.getNumeroEtudiant(), this.currentDemandeTransferts.getAnnee()));
			}
			String summary = getString("ENREGISTREMENT.DEMANDE_TRANSFERT_GESTIONNAIRE");
			String detail = getString("ENREGISTREMENT.DEMANDE_TRANSFERT_GESTIONNAIRE");
			Severity severity = FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
	}

	private EtudiantRef addDemandeTransfertsFromAvis(Integer typeAvis) {
		if (logger.isDebugEnabled())
			logger.debug("===>private void addDemandeTransfertsFromAvis(Integer typeAvis)<===");
		this.currentDemandeTransferts.getTransferts().setFichier(getDomainService().getFichierByIdAndAnneeAndFrom(this.getSelectedmd5(), getSessionController().getCurrentAnnee(), getSource()));
		if (logger.isDebugEnabled()) {
			if (this.currentAvis != null)
				logger.debug("currentAvis : " + this.currentAvis.toString());
		}
		if (typeAvis == 1)
			this.currentDemandeTransferts.getTransferts().setTemoinTransfertValide(1);
		else
			this.currentDemandeTransferts.getTransferts().setTemoinTransfertValide(2);

		if ("100".equals(this.getCodePaysItems()))
		{
			this.currentDemandeTransferts.getAdresse().setCodeVilleEtranger(null);
			this.currentDemandeTransferts.getAdresse().setCodPay(this.getCodePaysItems());
			this.currentDemandeTransferts.getAdresse().setLibPay(getDomainServiceScolarite().getPaysByCodePays(this.getCodePaysItems()).getLibPay());
		}
		else
		{
			this.currentDemandeTransferts.getAdresse().setCodePostal(null);
			this.currentDemandeTransferts.getAdresse().setNomCommune(null);
			this.currentDemandeTransferts.getAdresse().setLibPay(getDomainServiceScolarite().getPaysByCodePays(this.currentDemandeTransferts.getAdresse().getCodPay()).getLibPay());
		}

		if(!this.isMultiple())
		{
			String summary = getString("ENREGISTREMENT.DEMANDE_TRANSFERT");
			String detail = getString("ENREGISTREMENT.DEMANDE_TRANSFERT");
			Severity severity = FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}

		return getDomainService().addDemandeTransferts(this.currentDemandeTransferts);
	}

	public void addTransfertOpi()
	{
		this.addTransfertOpiToListeTransfertsAccueil();
	}

	private boolean testUrl(String host) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(host).openConnection();
			conn.setConnectTimeout(getSessionController().getTimeOutConnexionWs());
			conn.connect();
			return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
		} catch (MalformedURLException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("MalformedURLException");
				logger.debug("host : " + host);
			}
			logger.error(e);
			return false;
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("IOException");
				logger.debug("host : " + host);
			}
			logger.error(e);
			return false;
		}
	}

	private int codeErreurHttp(String host) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(host).openConnection();
			conn.setConnectTimeout(getSessionController().getTimeOutConnexionWs());
			conn.connect();
			return conn.getResponseCode();
		} catch (MalformedURLException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("MalformedURLException");
				logger.debug("host : " + host);
			}
			logger.error(e);
			return 0;
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("IOException");
				logger.debug("host : " + host);
			}
			logger.error(e);
			return 0;
		}
	}

	public DomainServiceOpi getDomainServiceWSOpiExt() {
		return domainServiceWSOpiExt;
	}

	public void setDomainServiceWSOpiExt(DomainServiceOpi domainServiceWSOpiExt) {
		this.domainServiceWSOpiExt = domainServiceWSOpiExt;
	}

	public List<IndOpi> getListEntrants() {
		if (logger.isDebugEnabled())
			logger.debug("public TransfertDataModelOPi getAllOpi()");
		if (listEntrants == null)
			//			listEntrants = getDomainService().getAllIndOpi(getSessionController().getCurrentAnnee());
			if (switchTraiteNontraite)
				listEntrants = getDomainService().getAllIndOpiBySource(getSessionController().getCurrentAnnee(), getSource());
			else
				listEntrants = getDomainService().getAllIndOpiBySynchroAndSource(getSessionController().getCurrentAnnee(),0, getSource());
		return listEntrants;
	}

	public void setListEntrants(List<IndOpi> listEntrants) {
		this.listEntrants = listEntrants;
	}

	public TransfertDataModelOpi getTransfertDataModelOpi() {
		return transfertDataModelOpi;
	}

	public TransfertDataModelOpi getAllIndOpi() {
		if (logger.isDebugEnabled())
			logger.debug("public TransfertDataModelOPi getAllIndOpi()-->"+getSessionController().getCurrentAnnee()+"-----"+getSource());
		if (transfertDataModelOpi == null)
		{
			if (switchTraiteNontraite)
			{
				//				List<IndOpi> lOpis = getDomainService().getAllIndOpiBySynchroAndSource(getSessionController().getCurrentAnnee(), 1, getSource());
				List<IndOpi> lOpis = getDomainService().getAllIndOpiBySynchroAndExcluAndSource(getSessionController().getCurrentAnnee(), getSource());
				if(lOpis!=null)
				{
					setTotalOpi(lOpis.size());
					transfertDataModelOpi = new TransfertDataModelOpi(lOpis);
				}
				else
				{
					setTotalOpi(0);
					transfertDataModelOpi = new TransfertDataModelOpi(new ArrayList<IndOpi>());
				}

				if (logger.isDebugEnabled())
					logger.debug("getTotalOpi()-->"+getTotalOpi());
				setFilteredEtudiantOpi(null);
			}
			else
			{
				List<IndOpi> lOpis = getDomainService().getAllIndOpiBySynchroAndSource(getSessionController().getCurrentAnnee(),0, getSource());
				if(lOpis!=null)
				{
					setTotalOpi(lOpis.size());
					transfertDataModelOpi = new TransfertDataModelOpi(lOpis);
				}
				else
				{
					setTotalOpi(0);
					transfertDataModelOpi = new TransfertDataModelOpi(new ArrayList<IndOpi>());
				}

				if (logger.isDebugEnabled())
					logger.debug("getTotalOpi()-->"+getTotalOpi());
				setFilteredEtudiantOpi(null);
			}
			for (IndOpi t : transfertDataModelOpi)
			{
				t.setLibEtabDepart(t.getEtabDepart());

				if (t.getVoeux().getLibelleVersionEtape() != null
						&& !"".equals(t.getVoeux().getLibelleVersionEtape()))
				{
					t.getVoeux().setLibEtp(t.getVoeux().getLibelleVersionEtape());
				}
				else
				{
					OffreDeFormationsDTO odf = getDomainService().getOdfByPK(getSessionController().getRne(),
							getSessionController().getCurrentAnnee(),
							t.getVoeux().getCodDip(),
							t.getVoeux().getCodVrsVdi(),
							t.getVoeux().getCodEtp(),
							t.getVoeux().getCodVrsVet(),
							t.getVoeux().getCodCge());
					String tmp;

					if (odf != null)
						tmp=odf.getLibVersionEtape();
					else
						tmp = "Non Disponible";

					t.getVoeux().setLibelleVersionEtape(tmp);
					getDomainService().updateLibelleVersionEtapeLocal(t);
					t.getVoeux().setLibEtp(t.getVoeux().getLibelleVersionEtape());
				}
			}
		}
		return transfertDataModelOpi;
	}

	public void setTransfertDataModelOpi(TransfertDataModelOpi transfertDataModelOpi) {
		this.transfertDataModelOpi = transfertDataModelOpi;
	}

	public IndOpi getCurrentOpi() {
		return currentOpi;
	}

	public void setCurrentOpi(IndOpi currentOpi) {
		this.currentOpi = currentOpi;
	}

	public FileUploadController getFileUploadController() {
		return fileUploadController;
	}

	public void setFileUploadController(FileUploadController fileUploadController) {
		this.fileUploadController = fileUploadController;
	}

	//	public Fichier getSelectedFichierDelete() {
	//		return selectedFichierDelete;
	//	}
	//
	//	public void setSelectedFichierDelete(Fichier selectedFichierDelete) {
	//		this.selectedFichierDelete = selectedFichierDelete;
	//	}

	public CodeSizeAnnee getSelectedCodeSizeAnnee() {
		return selectedCodeSizeAnnee;
	}

	public void setSelectedCodeSizeAnnee(CodeSizeAnnee selectedCodeSizeAnnee) {
		this.selectedCodeSizeAnnee = selectedCodeSizeAnnee;
	}

	public void setCodeSizeDataModel(CodeSizeDataModel codeSizeDataModel) {
		this.codeSizeDataModel = codeSizeDataModel;
	}

	public CodeSizeDataModel getCodeSizeDataModel() {
		List<CodeSizeAnnee> lcsa = getDomainService().getAllCodeSize();
		if(lcsa!=null)
			return new CodeSizeDataModel(lcsa);
		else
			return null;
	}

	public void setParametreAppli(Parametres parametreAppli) {
		this.parametreAppli = parametreAppli;
	}

	public Parametres getParametreAppli() {
		if (this.parametreAppli != null)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("this.parametreAppli.getCodeParametre --> "+ this.parametreAppli.getCodeParametre());
				logger.debug("this.parametreAppli.getBool() --> "+ this.parametreAppli.isBool());
				logger.debug("this.parametreAppli.getCommentaire() --> "+ this.parametreAppli.getCommentaire());
			}
		}
		else
		{
			if (logger.isDebugEnabled())
				logger.debug("Aucun parametre nome 'ouverture' trouve dans la table parametre");
		}
		return parametreAppli;
	}

	public String getCodTypDip() {
		return codTypDip;
	}

	public void setCodTypDip(String codTypDip) {
		this.codTypDip = codTypDip;
	}

	public Integer getCodeNiveau() {
		return codeNiveau;
	}

	public void setCodeNiveau(Integer codeNiveau) {
		this.codeNiveau = codeNiveau;
	}

	public String getCodeDiplome() {
		return codeDiplome;
	}

	public void setCodeDiplome(String codeDiplome) {
		this.codeDiplome = codeDiplome;
	}

	public OffreDeFormationsDTO getCurrentOdf() {
		return currentOdf;
	}

	public void setCurrentOdf(OffreDeFormationsDTO currentOdf) {
		this.currentOdf = currentOdf;
	}

	public OdfDataModel getOdfDataModel() {
		if(listeLibellesEtape!=null)
		{
			if(odfDataModel==null)
				odfDataModel = new OdfDataModel(listeLibellesEtape);
			return odfDataModel;
		}
		else
			return new OdfDataModel();
	}

	public void setOdfDataModel(OdfDataModel odfDataModel) {
		this.odfDataModel = odfDataModel;
	}

	public void setListeAnneesEtude(List<SelectItem> listeAnneesEtude) {
		this.listeAnneesEtude = listeAnneesEtude;
	}

	public void setListeLibellesDiplome(List<SelectItem> listeLibellesDiplome) {
		this.listeLibellesDiplome = listeLibellesDiplome;
	}




	public boolean isModeSynchro() {
		return modeSynchro;
	}




	public void setModeSynchro(boolean modeSynchro) {
		this.modeSynchro = modeSynchro;
	}

	public Integer getTimeOutConnexionWsOpiScolarite() {
		return timeOutConnexionWsOpiScolarite;
	}

	public void setTimeOutConnexionWsOpiScolarite(
			Integer timeOutConnexionWsOpiScolarite) {
		this.timeOutConnexionWsOpiScolarite = timeOutConnexionWsOpiScolarite;
	}

	public String getExclueEtpOpi() {
		return exclueEtpOpi;
	}

	public void setExclueEtpOpi(String exclueEtpOpi) {
		this.exclueEtpOpi = exclueEtpOpi;
	}

	public FileGeneratorService getFileGeneratorService() {
		return fileGeneratorService;
	}

	public void setFileGeneratorService(FileGeneratorService fileGeneratorService) {
		this.fileGeneratorService = fileGeneratorService;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public SituationUniversitaireDataModel getSudm() {
		if(this.currentDemandeTransferts.getAccueil().getSituationUniversitaire()!=null)
		{
			//			if(sudm==null)
			if (logger.isDebugEnabled())
			{
				logger.debug("===>aaaaa<===");
				logger.debug("this.currentDemandeTransferts.getAccueil().getSituationUniversitaire()===>"+this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size()+"<===");
			}
			sudm = new SituationUniversitaireDataModel(this.currentDemandeTransferts.getAccueil().getSituationUniversitaire());
			return sudm;
		}
		else
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("===>bbbbb<===");
				if(this.currentDemandeTransferts!=null && this.currentDemandeTransferts.getAccueil()!=null && this.currentDemandeTransferts.getAccueil().getSituationUniversitaire()!=null)
					logger.debug("this.currentDemandeTransferts.getAccueil().getSituationUniversitaire()===>"+this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size()+"<===");
			}
			this.currentDemandeTransferts.getAccueil().setSituationUniversitaire(getDomainService().getSituationUniversitaireByNumeroEtudiantAndAnnee(this.currentDemandeTransferts.getNumeroEtudiant(), this.currentDemandeTransferts.getAnnee()));
			return new SituationUniversitaireDataModel();
		}
	}

	public void setSudm(SituationUniversitaireDataModel sudm) {
		this.sudm = sudm;
	}

	public SituationUniversitaire getSelectedSituationUniv() {
		return selectedSituationUniv;
	}

	public void setSelectedSituationUniv(SituationUniversitaire selectedSituationUniv) {
		this.selectedSituationUniv = selectedSituationUniv;
	}

	public String getCurrentCleAccueilAnnee() {
		return currentCleAccueilAnnee;
	}

	public String getCurrentCleAccueilResultat() {
		return currentCleAccueilResultat;
	}

	public void setCurrentCleAccueilAnnee(String currentCleAccueilAnnee) {
		this.currentCleAccueilAnnee = currentCleAccueilAnnee;
	}

	public void setCurrentCleAccueilResultat(String currentCleAccueilResultat) {
		this.currentCleAccueilResultat = currentCleAccueilResultat;
	}

	public AccueilAnnee getCurrentAccueilAnnee() {
		return currentAccueilAnnee;
	}

	public AccueilResultat getCurrentAccueilResultat() {
		return currentAccueilResultat;
	}

	public void setCurrentAccueilAnnee(AccueilAnnee currentAccueilAnnee) {
		this.currentAccueilAnnee = currentAccueilAnnee;
	}

	public void setCurrentAccueilResultat(AccueilResultat currentAccueilResultat) {
		this.currentAccueilResultat = currentAccueilResultat;
	}

	public SituationUniversitaire getCurrentSituationUniv() {
		return currentSituationUniv;
	}

	public void setCurrentSituationUniv(SituationUniversitaire currentSituationUniv) {
		this.currentSituationUniv = currentSituationUniv;
	}

	public Integer getTypePersonnel() {
		return typePersonnel;
	}

	public void setTypePersonnel(Integer typePersonnel) {
		this.typePersonnel = typePersonnel;
	}

	public List<SelectItem> getListeComposantes() {
		if (logger.isDebugEnabled())
			logger.debug("getListeComposantes");

		listeComposantes = new ArrayList<SelectItem>();
		Map<String, String> listeComposantesDTO=getDomainService().getOdfComposanteByRneAndAnneeAndCodTypDip(this.currentDemandeTransferts.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip());
		if(listeComposantesDTO!=null && !listeComposantesDTO.isEmpty())
		{
			if (logger.isDebugEnabled()) {
				logger.debug("listeComposantesDTO : "+listeComposantesDTO);
			}
			for (Map.Entry<String,String> entry : listeComposantesDTO.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				SelectItem option = new SelectItem(key, value);
				listeComposantes.add(option);
			}
			Collections.sort(listeComposantes,new ComparatorSelectItem());
			return listeComposantes;
		}
		else
			return null;
	}

	public void setListeComposantes(List<SelectItem> listeComposantes) {
		this.listeComposantes = listeComposantes;
	}

	public String getCodeComposante() {
		return codeComposante;
	}

	public void setCodeComposante(String codeComposante) {
		this.codeComposante = codeComposante;
	}

	public boolean isComposanteVide() {
		return composanteVide;
	}

	public void setComposanteVide(boolean composanteVide) {
		this.composanteVide = composanteVide;
	}

	public boolean isMaxSU() {
		if(this.currentDemandeTransferts.getAccueil().getSituationUniversitaire() == null)
			return false;
		else if(this.currentDemandeTransferts.getAccueil().getSituationUniversitaire() != null && this.currentDemandeTransferts.getAccueil().getSituationUniversitaire().size()<7)
			return false;
		else
			return true;
	}

	public void setMaxSU(boolean maxSU) {
		this.maxSU = maxSU;
	}

	public AccueilDecision getCurrentAccueilDecision() {
		return currentAccueilDecision;
	}

	public void setCurrentAccueilDecision(AccueilDecision currentAccueilDecision) {
		this.currentAccueilDecision = currentAccueilDecision;
	}

	public List<AccueilDecision> getListeAccueilDecision() {
		if(this.currentDemandeTransferts.getAccueilDecision()!=null)
		{
			listeAccueilDecision = new ArrayList<AccueilDecision>();
			Iterator i=this.currentDemandeTransferts.getAccueilDecision().iterator(); // on cr�e un Iterator pour parcourir notre HashSet
			while(i.hasNext()) // tant qu'on a un suivant
			{
				listeAccueilDecision.add((AccueilDecision) i.next());
			}
			Collections.sort(listeAccueilDecision, new ComparatorDateTimeAccueilDecision());
		}
		return listeAccueilDecision;
	}

	public void setListeAccueilDecision(List<AccueilDecision> listeAccueilDecision) {
		this.listeAccueilDecision = listeAccueilDecision;
	}

	public AccueilDecision getSelectedDecision() {
		return selectedDecision;
	}

	public void setSelectedDecision(AccueilDecision selectedDecision) {
		this.selectedDecision = selectedDecision;
	}

	public boolean isDroitsDepart() {
		return droitsDepart;
	}

	public void setDroitsDepart(boolean droitsDepart) {
		this.droitsDepart = droitsDepart;
	}

	public boolean isDroitsArrivee() {
		return droitsArrivee;
	}

	public void setDroitsArrivee(boolean droitsArrivee) {
		this.droitsArrivee = droitsArrivee;
	}

	public EtudiantRef[] getSelectedDemandeTransferts() {
		return selectedDemandeTransferts;
	}

	public void setSelectedDemandeTransferts(EtudiantRef[] selectedDemandeTransferts) {
		this.selectedDemandeTransferts = selectedDemandeTransferts;
	}

	public List<EtudiantRef> getFilteredEtudiantDepart() {
		return filteredEtudiantDepart;
	}

	public void setFilteredEtudiantDepart(List<EtudiantRef> filteredEtudiantDepart) {
		this.filteredEtudiantDepart = filteredEtudiantDepart;
	}

	public List<EtudiantRef> getFilteredEtudiantAccueil() {
		return filteredEtudiantAccueil;
	}

	public void setFilteredEtudiantAccueil(List<EtudiantRef> filteredEtudiantAccueil) {
		this.filteredEtudiantAccueil = filteredEtudiantAccueil;
	}

	public List<EtudiantRef> getFilteredEtudiantOpi() {
		return filteredEtudiantOpi;
	}

	public void setFilteredEtudiantOpi(List<EtudiantRef> filteredEtudiantOpi) {
		this.filteredEtudiantOpi = filteredEtudiantOpi;
	}

	public List<EtudiantRef> getFilteredEtudiantDepartMultiple() {
		return filteredEtudiantDepartMultiple;
	}

	public void setFilteredEtudiantDepartMultiple(
			List<EtudiantRef> filteredEtudiantDepartMultiple) {
		this.filteredEtudiantDepartMultiple = filteredEtudiantDepartMultiple;
	}

	public Avis getCurrentAvisMultiple() {
		return currentAvisMultiple;
	}

	public void setCurrentAvisMultiple(Avis currentAvisMultiple) {
		this.currentAvisMultiple = currentAvisMultiple;
	}

	public Integer getTotalDepart() {
		return totalDepart;
	}

	public void setTotalDepart(Integer totalDepart) {
		this.totalDepart = totalDepart;
	}

	public Integer getTotalAccueil() {
		return totalAccueil;
	}

	public void setTotalAccueil(Integer totalAccueil) {
		this.totalAccueil = totalAccueil;
	}

	public Integer getTotalOpi() {
		return totalOpi;
	}

	public void setTotalOpi(Integer totalOpi) {
		this.totalOpi = totalOpi;
	}

	public PersonnelComposante getDroitPC() {
		return droitPC;
	}

	public void setDroitPC(PersonnelComposante droitPC) {
		this.droitPC = droitPC;
	}

	public String getAideTypeTransfert() {
		aideTypeTransfert = getString("AIDE.TYPE_TANSFERT", getDomainServiceScolarite().getEtablissementByRne(getSessionController().getRne()).getLibOffEtb());
		return aideTypeTransfert;
	}

	public void setAideTypeTransfert(String aideTypeTransfert) {
		this.aideTypeTransfert = aideTypeTransfert;
	}

	public IndOpi[] getSelectedOpis() {
		return selectedOpis;
	}

	public void setSelectedOpis(IndOpi[] selectedOpis) {
		this.selectedOpis = selectedOpis;
	}

	public String getExclueBacOpi() {
		return exclueBacOpi;
	}

	public void setExclueBacOpi(String exclueBacOpi) {
		this.exclueBacOpi = exclueBacOpi;
	}

	public boolean isExistCodeBac() {
		return existCodeBac;
	}

	public void setExistCodeBac(boolean existCodeBac) {
		this.existCodeBac = existCodeBac;
	}

	public List<EtudiantRef> getFilteredEtudiantAccueilMultiple() {
		return filteredEtudiantAccueilMultiple;
	}

	public void setFilteredEtudiantAccueilMultiple(
			List<EtudiantRef> filteredEtudiantAccueilMultiple) {
		this.filteredEtudiantAccueilMultiple = filteredEtudiantAccueilMultiple;
	}

	public AccueilDecision getCurrentDecisionMultiple() {
		return currentDecisionMultiple;
	}

	public void setCurrentDecisionMultiple(AccueilDecision currentDecisionMultiple) {
		this.currentDecisionMultiple = currentDecisionMultiple;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public boolean isRepriseEtudes() {
		return repriseEtudes;
	}

	public void setRepriseEtudes(boolean repriseEtudes) {
		this.repriseEtudes = repriseEtudes;
	}

	public String getTexteInterditNiveau2() {
		return texteInterditNiveau2;
	}

	public void setTexteInterditNiveau2(String texteInterditNiveau2) {
		this.texteInterditNiveau2 = texteInterditNiveau2;
	}

	public boolean isInterditNiveau2() {
		return interditNiveau2;
	}

	public void setInterditNiveau2(boolean interditNiveau2) {
		this.interditNiveau2 = interditNiveau2;
	}

	public boolean isInterditNiveau3() {
		return interditNiveau3;
	}

	public void setInterditNiveau3(boolean interditNiveau3) {
		this.interditNiveau3 = interditNiveau3;
	}

	public String getTexteInterditNiveau3() {
		return texteInterditNiveau3;
	}

	public void setTexteInterditNiveau3(String texteInterditNiveau3) {
		this.texteInterditNiveau3 = texteInterditNiveau3;
	}

	public IndOpi getSelectedOpiForDelete() {
		return selectedOpiForDelete;
	}

	public void setSelectedOpiForDelete(IndOpi selectedOpiForDelete) {
		this.selectedOpiForDelete = selectedOpiForDelete;
	}

	public String getAideChoixVoeuParComposante() {
		aideChoixVoeuParComposante = getString("AIDE.CHOIX_VOEU_PAR_COMPOSANTE");
		return aideChoixVoeuParComposante;
	}

	public void setAideChoixVoeuParComposante(String aideChoixVoeuParComposante) {
		this.aideChoixVoeuParComposante = aideChoixVoeuParComposante;
	}

//	public String getVariablesEnvironnement() {
//		return variablesEnvironnement;
//	}
//
//	public void setVariablesEnvironnement(String variablesEnvironnement) {
//		this.variablesEnvironnement = variablesEnvironnement;
//	}

//	public Date getAujourdhui() {
//		return aujourdhui;
//	}
//
//	public void setAujourdhui(Date aujourdhui) {
//		this.aujourdhui = aujourdhui;
//	}

	public String getSelectedmd5() {
		return selectedmd5;
	}

	public void setSelectedmd5(String selectedmd5) {
		this.selectedmd5 = selectedmd5;
	}

	public List<Correspondance> getListeCorrespondances() {
		if(this.currentDemandeTransferts.getCorrespondances()!=null)
		{
			listeCorrespondances = new ArrayList<Correspondance>();
			Iterator i=this.currentDemandeTransferts.getCorrespondances().iterator(); // on cr�e un Iterator pour parcourir notre HashSet
			while(i.hasNext()) // tant qu'on a un suivant
			{
				listeCorrespondances.add((Correspondance) i.next());
			}
			Collections.sort(listeCorrespondances, new ComparatorDateTimeCorrespondance());
		}
		return listeCorrespondances;
	}

	public void setListeCorrespondances(List<Correspondance> listeCorrespondances) {
		this.listeCorrespondances = listeCorrespondances;
	}

	public WebService getCurrentWs() {
		return currentWs;
	}

	public void setCurrentWs(WebService currentWs) {
		this.currentWs = currentWs;
	}

	public String getCheckWsBu() {
		List<Interdit> listeInterditsNiveau1;
		List<DatasExterne> listeInterdit=null;
		Integer etatConnexion=0;
		WebService currentWs = getDomainService().getWebServiceByCode("bu");
		if(getSessionController().isUseWsBu() && currentWs!=null)
		{
			Object tabReturn[] = Fonctions.appelWSAuth(currentWs.getUrl(),
					currentWs.getIdentifiant(),
					currentWs.getPwd(),
					currentWs.getNomClassJava(),
					currentWs.getNomMethodeJavaGetAll(),
					"arrayList",
					getSessionController().getTimeOutConnexionWs());

			listeInterditsNiveau1 = (List<Interdit>) tabReturn[0];
			etatConnexion = (Integer) tabReturn[1];

			if (logger.isDebugEnabled()) {
				logger.debug("listeInterditsNiveau1===>" + listeInterditsNiveau1 + "<===");
				logger.debug("etatConnexion===>" + etatConnexion + "<===");
			}

			if(etatConnexion==1)
				listeInterdit = getSessionController().convertListInterditsToListDatasExterne(listeInterditsNiveau1);
		}
		else
			listeInterdit = getDomainService().getAllDatasExterneByNiveau(1);

		String check = "En service - ";

		if(etatConnexion==0)
			check = "!!! Indisponible !!!";
		else
			check += (listeInterdit!=null) ? listeInterdit.size() : 0 + " donnée(s)";
		return check;
	}

	public String getCheckWsCandidatures() {
		List<DatasExterne> listeInterdit=null;
		List<Interdit> listeInterditsNiveau2;
		Integer etatConnexion=0;
		WebService currentWs = getDomainService().getWebServiceByCode("candidature");

		if(getSessionController().isUseWsCandidatures() && currentWs!=null)
		{
			Object tabReturn[] = Fonctions.appelWSAuth(currentWs.getUrl(),
					currentWs.getIdentifiant(),
					currentWs.getPwd(),
					currentWs.getNomClassJava(),
					currentWs.getNomMethodeJavaGetAll(),
					"arrayList",
					getSessionController().getTimeOutConnexionWs());

			listeInterditsNiveau2 = (List<Interdit>) tabReturn[0];
			etatConnexion = (Integer) tabReturn[1];

			if (logger.isDebugEnabled()) {
				logger.debug("listeInterditsNiveau2===>" + listeInterditsNiveau2 + "<===");
				logger.debug("etatConnexion===>" + etatConnexion + "<===");
			}

			if(etatConnexion==1)
				listeInterdit = getSessionController().convertListInterditsToListDatasExterne(listeInterditsNiveau2);
		}
		else
			listeInterdit = getDomainService().getAllDatasExterneByNiveau(2);

		String check = "En service - ";

		if(etatConnexion==0)
			check = "!!! Indisponible !!!";
		else
			check += (listeInterdit!=null) ? listeInterdit.size() : 0 + " donnée(s)";

		return check;
	}

	public String getCheckWsPostBac() {
		List<DatasExterne> listeInterdit=null;
		List<Interdit> listeInterditsNiveau3;
		Integer etatConnexion=0;
		WebService currentWs = getDomainService().getWebServiceByCode("postbac");

		if(getSessionController().isUseWsPostBac() && currentWs!=null)
		{
			Object tabReturn[] = Fonctions.appelWSAuth(currentWs.getUrl(),
					currentWs.getIdentifiant(),
					currentWs.getPwd(),
					currentWs.getNomClassJava(),
					currentWs.getNomMethodeJavaGetAll(),
					"arrayList",
					getSessionController().getTimeOutConnexionWs());

			listeInterditsNiveau3 = (List<Interdit>) tabReturn[0];
			etatConnexion = (Integer) tabReturn[1];

			if (logger.isDebugEnabled()) {
				logger.debug("listeInterditsNiveau3===>" + listeInterditsNiveau3 + "<===");
				logger.debug("etatConnexion===>" + etatConnexion + "<===");
			}

			if(etatConnexion==1)
				listeInterdit = getSessionController().convertListInterditsToListDatasExterne(listeInterditsNiveau3);
		}
		else
			listeInterdit = getDomainService().getAllDatasExterneByNiveau(3);

		String check = "En service - ";

		if(etatConnexion==0)
			check = "!!! Indisponible !!!";
		else
			check += (listeInterdit!=null) ? listeInterdit.size() : 0 + " donnée(s)";

		return check;
	}

	public String getSourceWs() {
		return sourceWs;
	}

	public void setSourceWs(String sourceWs) {
		this.sourceWs = sourceWs;
	}

	public Integer getSelectedCurrentAnneeDroitUtilisateursACopier() {
		return selectedCurrentAnneeDroitUtilisateursACopier;
	}

	public void setSelectedCurrentAnneeDroitUtilisateursACopier(Integer selectedCurrentAnneeDroitUtilisateursACopier) {
		this.selectedCurrentAnneeDroitUtilisateursACopier = selectedCurrentAnneeDroitUtilisateursACopier;
	}

	public Integer getSelectedCurrentAnneeSignaturesACopier() {
		return selectedCurrentAnneeSignaturesACopier;
	}

	public void setSelectedCurrentAnneeSignaturesACopier(Integer selectedCurrentAnneeSignaturesACopier) {
		this.selectedCurrentAnneeSignaturesACopier = selectedCurrentAnneeSignaturesACopier;
	}

	public Integer getSelectedCurrentAnneePartenairesACopier() {
		return selectedCurrentAnneePartenairesACopier;
	}

	public void setSelectedCurrentAnneePartenairesACopier(Integer selectedCurrentAnneePartenairesACopier) {
		this.selectedCurrentAnneePartenairesACopier = selectedCurrentAnneePartenairesACopier;
	}

	public boolean isChoixDuVoeuParComposanteByPartenaire() {
		return choixDuVoeuParComposanteByPartenaire;
	}

	public void setChoixDuVoeuParComposanteByPartenaire(boolean choixDuVoeuParComposanteByPartenaire) {
		this.choixDuVoeuParComposanteByPartenaire = choixDuVoeuParComposanteByPartenaire;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Integer getTotalNombreDatasExterneNiveau1() {
		return totalNombreDatasExterneNiveau1;
	}

	public void setTotalNombreDatasExterneNiveau1(Integer totalNombreDatasExterneNiveau1) {
		this.totalNombreDatasExterneNiveau1 = totalNombreDatasExterneNiveau1;
	}

	public Integer getTotalNombreDatasExterneNiveau2() {
		return totalNombreDatasExterneNiveau2;
	}

	public void setTotalNombreDatasExterneNiveau2(Integer totalNombreDatasExterneNiveau2) {
		this.totalNombreDatasExterneNiveau2 = totalNombreDatasExterneNiveau2;
	}

	public Integer getTotalNombreDatasExterneNiveau3() {
		return totalNombreDatasExterneNiveau3;
	}

	public void setTotalNombreDatasExterneNiveau3(Integer totalNombreDatasExterneNiveau3) {
		this.totalNombreDatasExterneNiveau3 = totalNombreDatasExterneNiveau3;
	}

	public Versions getVersion() {
		return version;
	}

	public void setVersion(Versions version) {
		this.version = version;
	}

	public boolean isDroitAvisDefinitifTransfertDepartMultiple() {
		return droitAvisDefinitifTransfertDepartMultiple;
	}

	public void setDroitAvisDefinitifTransfertDepartMultiple(boolean droitAvisDefinitifTransfertDepartMultiple) {
		this.droitAvisDefinitifTransfertDepartMultiple = droitAvisDefinitifTransfertDepartMultiple;
	}
}