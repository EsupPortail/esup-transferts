/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.dao.DaoService;
import org.esupportail.transferts.domain.beans.*;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Farid AIT KARRA (Universite d'Artois) - 2016
 * 
 */
public class DomainServiceImpl implements DomainService, InitializingBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 5562208937407153456L;

	/**
	 * For Logging.
	 */
	private static final Logger logger = new LoggerImpl(DomainServiceImpl.class);
	private DaoService daoService;
	private Transferts transfert = new Transferts();

	/**
	 * En l'absence de Dao et de Ldap, on constitue ici une liste... limitee de
	 * fait a l'utilisateur courant.
	 */
	private List<User> users;

	/**
	 * Constructor.
	 */
	public DomainServiceImpl() {
		super();
		users = new ArrayList<User>();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// nothing to do yet.
	}

	@Override
	public User getUser(String uid) {
		User user = null;
		for (User userInList : users) {
			if (userInList.getLogin().equals(uid)) {
				user = userInList;
				break;
			}
		}
		if (user == null) {
			user = new User();
			user.setLogin(uid);
			// On cree l'utilisateur, son nom complet prend la valeur de
			// l'Uid.
			user.setDisplayName(uid);
			user.setLanguage("fr");
			users.add(user);
		}
		return user;
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

	public DaoService getDaoService() {
		return daoService;
	}

	@Override
	public EtudiantRef addDemandeTransferts(EtudiantRef currentEtudiant) {
		return getDaoService().addDemandeTransferts(currentEtudiant);
	}
	
	public Transferts getTransfert() {
		return transfert;
	}

	public void setTransfert(Transferts transfert) {
		this.transfert = transfert;
	}

//	@Override
//	public List<EtudiantRef> getAllDemandesTransferts() {
//		return getDaoService().getAllDemandesTransferts();
//	}

	@Override
	public List<WsPub> getListeWsPub() {
		return getDaoService().getListeWsPub();
	}

	@Override
	public WsPub getWsPubByRneAndAnnee(String rne, Integer annee) {
		return daoService.getWsPubByRneAndAnnee(rne, annee); 
	}

	@Override
	public List<WsPub> getWsPubByAnnee(Integer annee) {
		return daoService.getWsPubByAnnee(annee); 
	}
	
	@Override
	public void addAvis(Avis a) {
		getDaoService().addAvis(a);
	}

	@Override
	public List<Avis> getAvis(String numeroEtudiant, Integer annee) {
		return getDaoService().getAvis(numeroEtudiant, annee);
	}

	@Override
	public List<EtatDossier> getEtatsDossier() {
		return getDaoService().getEtatsDossier();
	}

	@Override
	public EtatDossier getEtatDossierById(Integer idEtatDossier) {
		return getDaoService().getEtatDossierById(idEtatDossier);
	}

	@Override
	public List<LocalisationDossier> getLocalisationDossier() 
	{
		return getDaoService().getLocalisationDossier();
	}

	@Override
	public LocalisationDossier getLocalisationDossierById(Integer idLocalisationDossier) 
	{
		return getDaoService().getLocalisationDossierById(idLocalisationDossier);
	}

	@Override
	public List<DecisionDossier> getDecisionDossier() {
		return getDaoService().getDecisionDossier();
	}

	@Override
	public DecisionDossier getDecisionDossierById(Integer idDecisionDossier) {
		return getDaoService().getDecisionDossierById(idDecisionDossier);
	}

	@Override
	public EtudiantRef getPresenceEtudiantRef(String numeroEtudiant, Integer annee) {
		return getDaoService().getEtudiantRef(numeroEtudiant, annee);
	}

	@Override
	public void addFichier(Fichier f) {
		getDaoService().addFichier(f);
	}

	@Override
	public List<Fichier> getFichiersByAnneeAndFrom(Integer annee, String from) {
		return getDaoService().getFichiersByAnneeAndFrom(annee, from);
	}

	@Override
	public void updateDefautFichier(Fichier fichier, Integer annee, String from) {
		getDaoService().updateDefautFichier(fichier, annee, from);
	}

	@Override
	public Fichier getFichierDefautByAnneeAndFrom(Integer annee, String from) {
		return getDaoService().getFichierDefautByAnneeAndFrom(annee, from);
	}

	@Override
	public EtudiantRefImp getPresenceEtudiantRefImp(String numeroEtudiant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EtudiantRef> getAllDemandesTransfertsByAnnee(Integer annee, String source) {
		return getDaoService().getAllDemandesTransfertsByAnnee(annee, source);
	}

	@Override
	public List<Integer> getListeAnnees() {
		return getDaoService().getListeAnnees();
	}

	@Override
	public Fichier getFichierByIdAndAnneeAndFrom(String md5, Integer annee, String from) {
		return getDaoService().getFichierByIdAndAnneeAndFrom(md5, annee, from);
	}

	@Override
	public Avis getDernierAvisFavorable(String numeroEtudiant, Integer annee) {
		return getDaoService().getDernierAvisFavorable(numeroEtudiant, annee);
	}

//	@Override
//	public AdresseEtablissement getAdresseEtablissementByRne(String rne) {
//		return getDaoService().getAdresseEtablissementByRne(rne);
//	}

	@Override
	public void deleteDemandeTransfert(EtudiantRef demandeTransferts, Integer annee) {
		getDaoService().deleteDemandeTransfert(demandeTransferts, annee);
	}	
	
	@Override
	public List<IndOpi> getAllIndOpiBySource(Integer annee, String source) {
		return getDaoService().getAllIndOpiBySource(annee, source);
	}

	/*Statistiques*/
	@Override
	public Long getDemandesTransfertsByEnCoursAndAnnee(Integer annee, String source) {
		return getDaoService().getDemandesTransfertsByEnCoursAndAnnee(annee, source);
	}

	@Override
	public Long getDemandesTransfertsByAvisSaisieAndAnnee(Integer annee, String source) {
		return getDaoService().getDemandesTransfertsByAvisSaisieAndAnnee(annee, source);
	}

	@Override
	public Long getDemandesTransfertsByTerminerAndAnnee(Integer annee, String source) {
		return getDaoService().getDemandesTransfertsByTerminerAndAnnee(annee, source);
	}

	@Override
	public Long getDemandesTransfertsByPbOpiAndAnnee(Integer annee) {
		return getDaoService().getDemandesTransfertsByPbOpiAndAnnee(annee);
	}

	@Override
	public Long getDemandesTransfertsByEtabNonPartenaireAndAnnee(Integer annee) {
		return getDaoService().getDemandesTransfertsByEtabNonPartenaireAndAnnee(annee);
	}

	@Override
	public Long getDemandesTransfertsByOpiOkAndAnnee(Integer annee) {
		return getDaoService().getDemandesTransfertsByOpiOkAndAnnee(annee);
	}

//	@Override
//	public List<DatasExterne> getInterditBu(String numeroEtudiant) {
//		return getDaoService().getInterditBu(numeroEtudiant);
//	}

	@Override
	public List<String> getIndOpiExtractBySource(Integer annee, String source) {
		return getDaoService().getIndOpiExtractBySource(annee, source);
	}

	@Override
	public EtudiantRefExcel getEtudiantRefExcel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getVoeuxInsExtractBySource(Integer annee, String source) {
		return getDaoService().getVoeuxInsExtractBySource(annee, source);
	}

	@Override
	public CodeSizeAnnee getCodeSizeByAnnee(Integer annee) {
		return getDaoService().getCodeSizeByAnnee(annee);
	}

	@Override
	public List<CodeSizeAnnee> getAllCodeSize() {
		return getDaoService().getAllCodeSize();
	}

	@Override
	public CodeSizeAnnee getCodeSizeDefaut() {
		return getDaoService().getCodeSizeDefaut();
	}

	@Override
	public void updateDefautCodeSize(CodeSizeAnnee cs) {
		getDaoService().updateDefautCodeSize(cs);
	}

	@Override
	public void addCodeSize(CodeSizeAnnee cs) {
		getDaoService().addCodeSize(cs);
	}

	@Override
	public List<Parametres> getAllParametres() {
		return getDaoService().getAllParametres();
	}

	@Override
	public Parametres getParametreByCode(String codeParametre) {
		return getDaoService().getParametreByCode(codeParametre);
	}

	@Override
	public void addParametre(Parametres param) {
		getDaoService().addParametre(param);
	}

	@Override
	public List<EtudiantRef> getAllDemandesTransfertsByAnneeAndNonTraite(Integer annee, String source) {
		return getDaoService().getAllDemandesTransfertsByAnneeAndNonTraite(annee, source);
	}

	@Override
	public void updateLibelleVersionEtapeLocal(IndOpi etu) {
		getDaoService().updateLibelleVersionEtapeLocal(etu);
	}

	@Override
	public void deleteFichier(String md5, Integer annee, String from) throws Exception {
		getDaoService().deleteFichier(md5, annee, from);
	}

	@Override
	public List<OffreDeFormationsDTO> getSelectedOdfs(Integer currentAnnee, String rne) {
		return getDaoService().getSelectedOdfs(currentAnnee, rne);
	}

	@Override
	public void addOdfs(OffreDeFormationsDTO[] selectedOdfs) {
		getDaoService().addOdfs(selectedOdfs);
	}

	@Override
	public Map<String, String> getOdfTypesDiplomeByRneAndAnnee(String rne, Integer currentAnnee, boolean actif, String source) {
		return getDaoService().getOdfTypesDiplomeByRneAndAnnee(rne, currentAnnee, actif, source);
	}

	@Override
	public void updateWsPub(WsPub partenaire) {
		getDaoService().updateWsPub(partenaire);
	}

	@Override
	public Date getDateMaxMajByRneAndAnnee(Integer annee, String rne) {
		return getDaoService().getDateMaxMajByRneAndAnnee(annee, rne);
	}

	@Override
	public Map<Integer, String> getAnneesEtudeByRneAndAnneeAndCodTypDip(String rne, Integer annee, String codTypDip, boolean actif, String source) {
		return getDaoService().getAnneesEtudeByRneAndAnneeAndCodTypDip(rne, annee, codTypDip, actif, source);
	}

	@Override
	public Map<String, String> getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(String rne, Integer currentAnnee, String codTypDip, Integer codeNiveau, boolean actif, String source) {
		return getDaoService().getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(rne, currentAnnee, codTypDip, codeNiveau, actif, source);
	}

	@Override
	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDip(String rne, Integer currentAnnee, String codTypDip, Integer codeNiveau, String codDip, String source) {
		return getDaoService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDip(rne, currentAnnee, codTypDip, codeNiveau, codDip, source);
	}

	@Override
	public List<IndOpi> getAllIndOpiNonSynchroAndSource(Integer annee, String source) {
		return getDaoService().getAllIndOpiNonSynchroAndSource(annee, source);
	}

	@Override
	public void updateIndOpi(IndOpi opi) {
		getDaoService().updateIndOpi(opi);
	}

	@Override
	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndAtifOuPas(String rne, Integer currentAnnee, String codTypDip, Integer codeNiveau, String codeDiplome) {
		return getDaoService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndAtifOuPas(rne, currentAnnee, codTypDip, codeNiveau, codeDiplome);
	}

	@Override
	public List<OffreDeFormationsDTO> getAllOffreDeFormationByAnneeAndRneAndAtifOuPas(Integer currentAnnee, String rne) {
		return getDaoService().getAllOffreDeFormationByAnneeAndRneAndAtifOuPas(currentAnnee, rne);
	}

	@Override
	public List<AccueilAnnee> getListeAccueilAnnee() {
		return getDaoService().getListeAccueilAnnee();
	}

	@Override
	public List<AccueilResultat> getListeAccueilResultat() {
		return getDaoService().getListeAccueilResultat();
	}

	@Override
	public AccueilAnnee getAccueilAnneeById(Integer id) {
		return getDaoService().getAccueilAnneeById(id);
	}

	@Override
	public AccueilResultat getAccueilResultatById(Integer id) {
		return getDaoService().getAccueilResultatById(id);
	}

	@Override
	public Map<String, String> getListeManagersBySourceAndAnnee(String source, Integer annee) {
		return getDaoService().getListeManagersBySourceAndAnnee(source, annee);
	}

	@Override
	public void addPersonnelComposante(String uid, String source, Integer annee, List<PersonnelComposante> target) {
		getDaoService().addPersonnelComposante(uid, source, annee, target);
	}

	@Override
	public List<PersonnelComposante> getListeComposantesByUidAndSourceAndAnnee(String login, String source, Integer annee) 
	{
		return getDaoService().getListeComposantesByUidAndSourceAndAnnee(login, source, annee);
	}

	@Override
	public Map<String, String> getOdfComposanteByRneAndAnneeAndActif(String rne, Integer annee) {
		return getDaoService().getOdfComposanteByRneAndAnneeAndActif(rne, annee);
	}

	@Override
	public Map<String, String> getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante(String rne, Integer currentAnnee, String codTypDip, Integer codeNiveau, String codeComposante, boolean actif) {
		return getDaoService().getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante(rne, currentAnnee, codTypDip, codeNiveau, codeComposante, actif);
	}

	@Override
	public void addWsPub(WsPub ws) {
		getDaoService().addWsPub(ws);
	}

	@Override
	public void deleteWsPub(WsPub wsPub) {
		getDaoService().deleteWsPub(wsPub);
	}

	@Override
	public AccueilDecision getDecisionByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer currentAnnee) 
	{
		return getDaoService().getDecisionByNumeroEtudiantAndAnnee(numeroEtudiant, currentAnnee); 
	}

	@Override
	public EtudiantRef getEtudiantRef(String numeroEtudiant, Integer annee) {
		return getDaoService().getEtudiantRef(numeroEtudiant, annee);
	}

	@Override
	public void deleteSituationUniversitaireByNumeroEtudiantAndAnneeIsNull() {
		getDaoService().deleteSituationUniversitaireByNumeroEtudiantAndAnneeIsNull();
	}

	@Override
	public EtudiantRef getPresenceEtudiantRefByIne(String ine, Integer currentAnnee) {
		return getDaoService().getPresenceEtudiantRefByIne(ine, currentAnnee);
	}

	@Override
	public IndOpi getPresenceEtudiantOPiByIneAndAnnee(String ine, Integer currentAnnee) {
		return getDaoService().getPresenceEtudiantOPiByIneAndAnnee(ine, currentAnnee);
	}

	@Override
	public List<PersonnelComposante> getListePersonnelsComposantesBySourceAndAnnee(String source, Integer annee) {
		return getDaoService().getListePersonnelsComposantesBySourceAndAnnee(source, annee);
	}

	@Override
	public OffreDeFormationsDTO getOdfByPK(String rne, Integer annee, String codDip, Integer codVrsVdi, String codEtp, String codVrsVet, String codeCge) {
		return getDaoService().getOdfByPK(rne, annee, codDip, codVrsVdi, codEtp, codVrsVet, codeCge);
	}

	@Override
	public boolean getDroitsTransferts(String login, String source, Integer annee) {
		return getDaoService().getDroitsTransferts(login, source, annee);
	}

	@Override
	public List<IndOpi> getAllIndOpiBySynchroAndSource(Integer currentAnnee,Integer synchro, String source) {
		return getDaoService().getAllIndOpiBySynchroAndSource(currentAnnee, synchro, source);
	}

	@Override
	public Map<String, Long> getStatistiquesTransfert(String source, Integer annee) {
		return getDaoService().getStatistiquesTransfert(source, annee);
	}

	@Override
	public Map<String, Long> getStatistiquesTransfertAccueil(String source,	Integer annee) {
		return getDaoService().getStatistiquesTransfertAccueil(source,	annee); 
	}

	@Override
	public Long getStatistiquesNombreTotalTransfertDepart(Integer currentAnnee) {
		return getDaoService().getStatistiquesNombreTotalTransfertDepart(currentAnnee);
	}

	@Override
	public Long getStatistiquesNombreTotalTransfertAccueil(Integer currentAnnee) {
		return getDaoService().getStatistiquesNombreTotalTransfertAccueil(currentAnnee);
	}

	@Override
	public Long getStatistiquesNombreTotalTransfertOPI(Integer currentAnnee) {
		return getDaoService().getStatistiquesNombreTotalTransfertOPI(currentAnnee);
	}

	@Override
	public void addValidationAutoByComposante(List<Composante> listeComposantes) {
		getDaoService().addValidationAutoByComposante(listeComposantes);
	}

	@Override
	public List<Composante> getListeComposantesFromBddByAnneeAndSource(Integer currentAnnee, String source) {
		return getDaoService().getListeComposantesFromBddByAnneeAndSource(currentAnnee, source);
	}

	@Override
	public Composante getComposantesFromBddByAnneeAndSourceAndCodeComposante(Integer annee, String source, String codeComposante) {
		return getDaoService().getComposantesFromBddByAnneeAndSourceAndCodeComposante(annee, source, codeComposante);
	}

	@Override
	public List<CGE> getListeCGEFromBddByAnneeAndSource(Integer currentAnnee,String source) {
		return getDaoService().getListeCGEFromBddByAnneeAndSource(currentAnnee,source);
	}

	@Override
	public void addValidationAutoByCGE(List<CGE> listeCGEMerge) {
		getDaoService().addValidationAutoByCGE(listeCGEMerge);
	}

	@Override
	public CGE getCGEFromBddByAnneeAndSourceAndCodeCGE(Integer annee, String source, String codeCGE) {
		return getDaoService().getCGEFromBddByAnneeAndSourceAndCodeCGE(annee, source, codeCGE);
	}

	@Override
	public PersonnelComposante getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante(String login, String source, Integer currentAnnee, String composante) {
		return getDaoService().getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante(login, source, currentAnnee, composante);
	}

//	@Override
//	public Map<String, String> getOdfComposanteByRneAndAnneeAndActifAndArrivee(String rne, Integer currentAnnee) {
//		return getDaoService().getOdfComposanteByRneAndAnneeAndActifAndArrivee(rne, currentAnnee);
//	}

	@Override
	public List<DatasExterne> getAllDatasExterneByIdentifiant(String identifiant) {
		return getDaoService().getAllDatasExterneByIdentifiant(identifiant);
	}

	@Override
	public DatasExterne getAllDatasExterneByCodeInterditAndNumeroEtudiant(String identifiant, String code) {
		return getDaoService().getAllDatasExterneByCodeInterditAndNumeroEtudiant(identifiant, code);
	}

	@Override
	public List<DatasExterne> getAllDatasExterneByIdentifiantAndNiveau(String identifiant, Integer niveau) {
		return getDaoService().getAllDatasExterneByIdentifiantAndNiveau(identifiant, niveau);
	}

//	@Override
//	public Map<String, String> getOdfComposanteByRneAndAnneeAndActifAndArriveeAndCodTypDip(String rne, Integer annee, String codTypDip) 
//	{
//		return getDaoService().getOdfComposanteByRneAndAnneeAndActifAndArriveeAndCodTypDip(rne, annee, codTypDip);
//	}

	@Override
	public DatasExterne getDataExterneByIdentifiantAndCode(String identifiant, String code) {
		return getDaoService().getDataExterneByIdentifiantAndCode(identifiant, code);
	}

	@Override
	public void addIndOpi(IndOpi opi, boolean maj) {
		getDaoService().addIndOpi(opi, maj);
	}

	@Override
	public IndOpi getOpiByNumeroOpi(IndOpi opi) {
		return getDaoService().getOpiByNumeroOpi(opi);
	}

	@Override
	public AccueilAnnee getAccueilAnneeByLibelle(String libelle) {
		return getDaoService().getAccueilAnneeByLibelle(libelle);
	}

	@Override
	public AccueilAnnee getAccueilAnneeByIdAccueilAnnee(Integer idAccueilAnnee) {
		return getDaoService().getAccueilAnneeByIdAccueilAnnee(idAccueilAnnee);
	}

	@Override
	public AccueilResultat getAccueilResultatByIdAccueilResultat(Integer idAccueilResultat) {
		return getDaoService().getAccueilResultatByIdAccueilResultat(idAccueilResultat);
	}

	@Override
	public List<IndOpi> getAllIndOpiBySynchroAndExcluAndSource(Integer currentAnnee, String source) {
		return getDaoService().getAllIndOpiBySynchroAndExcluAndSource(currentAnnee, source);
	}

	@Override
	public List<AccueilResultat> getAccueilResultatSansNull() {
		return getDaoService().getAccueilResultatSansNull();
	}

	@Override
	public List<AccueilAnnee> getAccueilAnneeSansNull() {
		return getDaoService().getAccueilAnneeSansNull();
	}

	@Override
	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposante(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeComposante, String source) {
		return getDaoService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposante(rne, annee, codTypDip, codeNiveau, codeComposante, source);
	}

	@Override
	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndAtifOuPas(
			String rne, Integer currentAnnee, String codTypDip,
			Integer codeNiveau, String codeComposante, String source) {
		return getDaoService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndAtifOuPas(
				rne, currentAnnee, codTypDip,
				codeNiveau, codeComposante, source);
	}

	@Override
	public Parametres updateConfiguration(Parametres param) {
		return getDaoService().updateConfiguration(param);
	}

	@Override
	public void deleteSelectedOpi(IndOpi selectedOpiForDelete) {
		getDaoService().deleteSelectedOpi(selectedOpiForDelete);		
	}

	@Override
	public Map<String, String> getOdfComposanteByRneAndAnneeAndCodTypDip(String rne, Integer currentAnnee, String codTypDip) {
		return getDaoService().getOdfComposanteByRneAndAnneeAndCodTypDip(rne, currentAnnee, codTypDip);
	}

	@Override
	public EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(String numeroEtudiant, int annee, String source)
	{
		return getDaoService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(numeroEtudiant, annee, source);
	}

	@Override
	public List<TestUnitaireEtudiantRef> getAllTestUnitaireEtudiantRefBySource(String source) {
		return getDaoService().getAllTestUnitaireEtudiantRefBySource(source);
	}

	@Override
	public List<DatasExterne> getAllDatasExterneByNiveau(Integer niveau) {
		return getDaoService().getAllDatasExterneByNiveau(niveau);
	}

	@Override
	public List<IndOpi> getAllIndOpiByAnnee(Integer currentAnnee) {
		return getDaoService().getAllIndOpiByAnnee(currentAnnee);
	}

	@Override
	public void deleteOpi(IndOpi opi) {
		getDaoService().deleteOpi(opi);
	}

	@Override
	public EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee) {
		return getDaoService().getDemandeTransfertByAnneeAndNumeroIneAndSource(ine, annee);
	}

	@Override
	public EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee, String source) {
		return getDaoService().getDemandeTransfertByAnneeAndNumeroIneAndSource(ine, annee, source);
	}

	@Override
	public IndOpi getIndOpiByNneAndCleIneAndAnnee(String nne, String cleIne, Integer annee) {
		return getDaoService().getIndOpiByNneAndCleIneAndAnnee(nne, cleIne, annee);
	}

	@Override
	public List<SituationUniversitaire> getSituationUniversitaireByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer annee) {
		return getDaoService().getSituationUniversitaireByNumeroEtudiantAndAnnee(numeroEtudiant, annee);
	}

	@Override
	public void addFermeture(Fermeture myFermeture) {
		getDaoService().addFermeture(myFermeture);
	}

	@Override
	public List<Fermeture> getListeFermeturesBySourceAndAnnee(String source, int annee) {
		return getDaoService().getListeFermeturesBySourceAndAnnee(source, annee);
	}

	@Override
	public List<Fermeture> addPeriodeFermetures(List<Fermeture> lFermetures) {
		return getDaoService().addPeriodeFermetures(lFermetures);
	}

	@Override
	public void deletePeriodeFermeture(String id) {
		getDaoService().deletePeriodeFermeture(id);
	}

	@Override
	public Fermeture getFermetureFromId(String id) {
		return getDaoService().getFermetureFromId(id);
	}

	@Override
	public EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance(String numeroEtudiant, Integer currentAnnee, String source) {
		return getDaoService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance(numeroEtudiant, currentAnnee, source);
	}

	@Override
	public Versions getVersionByEtat(Integer etat) {
		return getDaoService().getVersionByEtat(etat);
	}

	@Override
	public WebService updateWebService(WebService currentWs) {
		return getDaoService().updateWebService(currentWs);
	}

	@Override
	public WebService getWebServiceByCode(String code) {
		return getDaoService().getWebServiceByCode(code);
	}

	@Override
	public void addPersonnelComposanteFromImport(List<PersonnelComposante> newLpc) {
		getDaoService().addPersonnelComposanteFromImport(newLpc);
	}

    @Override
    public List<PersonnelComposante> getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante(String source, Integer currentAnnee, String composante) {
        return daoService.getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante(source, currentAnnee, composante);
    }

}
