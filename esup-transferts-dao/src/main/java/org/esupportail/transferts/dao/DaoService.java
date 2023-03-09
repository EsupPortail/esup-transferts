/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-blank
 */
package org.esupportail.transferts.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.transferts.domain.beans.*;

/**
 * @author Farid AIT KARRA (Universite d'Artois) - 2016
 * 
 * The DAO service interface.
 */
public interface DaoService extends Serializable {

	/**
	 * @param id
	 * @return the Task instance that corresponds to an id.
	 */
	EtudiantRef getEtudiantRef(String numeroEtudiant, Integer annee);

	EtudiantRef addDemandeTransferts(EtudiantRef currentEtudiant);

//	List<EtudiantRef> getAllDemandesTransferts();

	List<IndOpi> getAllIndOpiBySource(Integer annee, String Source);

	void addIndOpi(IndOpi opi, boolean maj);

	List<WsPub> getListeWsPub();

	WsPub getWsPubByRneAndAnnee(String rne, Integer annee);
	
	List<WsPub> getWsPubByAnnee(Integer annee);

	void addAvis(Avis a);

	List<Avis> getAvis(String numeroEtudiant, Integer annee);
	
	Avis getDernierAvisFavorable(String numeroEtudiant, Integer annee);

	List<EtatDossier> getEtatsDossier();

	EtatDossier getEtatDossierById(Integer idEtatDossier);

	List<LocalisationDossier> getLocalisationDossier();

	LocalisationDossier getLocalisationDossierById(Integer idLocalisationDossier);

	List<DecisionDossier> getDecisionDossier();

	DecisionDossier getDecisionDossierById(Integer idDecisionDossier);

	void addFichier(Fichier f);

	List<Fichier> getFichiersByAnneeAndFrom(Integer annee, String from);

	Fichier getFichierByIdAndAnneeAndFrom(String md5, Integer annee, String from);
	
	void updateDefautFichier(Fichier fichier, Integer annee, String from);

	Fichier getFichierDefautByAnneeAndFrom(Integer annee, String from);

	List<EtudiantRef> getAllDemandesTransfertsByAnnee(Integer annee, String source);

	List<Integer> getListeAnnees();

//	AdresseEtablissement getAdresseEtablissementByRne(String rne);

	void deleteDemandeTransfert(EtudiantRef demandeTransferts, Integer annee);

	Long getDemandesTransfertsByEnCoursAndAnnee(Integer annee, String source);

	Long getDemandesTransfertsByAvisSaisieAndAnnee(Integer annee, String source);

	Long getDemandesTransfertsByTerminerAndAnnee(Integer annee, String source);

	Long getDemandesTransfertsByPbOpiAndAnnee(Integer annee);

	Long getDemandesTransfertsByEtabNonPartenaireAndAnnee(Integer annee);

	Long getDemandesTransfertsByOpiOkAndAnnee(Integer annee);

//	List<DatasExterne> getInterditBu(String numeroEtudiant);

	List<String> getIndOpiExtractBySource(Integer annee, String source);

	List<String> getVoeuxInsExtractBySource(Integer annee, String source);

	CodeSizeAnnee getCodeSizeByAnnee(Integer annee);

	List<CodeSizeAnnee> getAllCodeSize();
	
	void addCodeSize(CodeSizeAnnee cs);
	
	void updateDefautCodeSize(CodeSizeAnnee cs);

	CodeSizeAnnee getCodeSizeDefaut();

	List<Parametres> getAllParametres();

	Parametres getParametreByCode(String codeParametre);

	void addParametre(Parametres param);

	List<EtudiantRef> getAllDemandesTransfertsByAnneeAndNonTraite(Integer annee, String source);

	void updateLibelleVersionEtapeLocal(IndOpi etu);

	void deleteFichier(String md5, Integer annee, String from) throws Exception;

	List<OffreDeFormationsDTO> getSelectedOdfs(Integer currentAnnee, String rne);

	void addOdfs(OffreDeFormationsDTO[] selectedOdfs);

	Map<String, String> getOdfTypesDiplomeByRneAndAnnee(String rne, Integer currentAnnee, boolean actif, String source);

	void updateWsPub(WsPub partenaire);

	Date getDateMaxMajByRneAndAnnee(Integer annee, String rne);

	List<OffreDeFormationsDTO> getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne(Date maxDate, Integer annee, String rne);

	List<OffreDeFormationsDTO> getFormationsByRneAndAnnee(String rne, Integer annee);

	Map<Integer, String> getAnneesEtudeByRneAndAnneeAndCodTypDip(String rne, Integer annee, String codTypDip, boolean actif, String source);

	Map<String, String> getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(String rne, Integer currentAnnee, String codTypDip, Integer codeNiveau, boolean actif, String source);

	List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDip(String rne, Integer currentAnnee, String codTypDip, Integer codeNiveau, String codDip, String source);

	List<IndOpi> getAllIndOpiNonSynchroAndSource(Integer annee, String source);

	void updateIndOpi(IndOpi opi);

	List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndAtifOuPas(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeDiplome);

	List<OffreDeFormationsDTO> getAllOffreDeFormationByAnneeAndRneAndAtifOuPas(Integer currentAnnee, String rne);

	List<AccueilAnnee> getListeAccueilAnnee();

	List<AccueilResultat> getListeAccueilResultat();

	AccueilAnnee getAccueilAnneeById(Integer id);

	AccueilResultat getAccueilResultatById(Integer id);

	Map<String, String> getListeManagersBySourceAndAnnee(String source, Integer annee);

	void addPersonnelComposante(String uid, String source, Integer annee, List<PersonnelComposante> target);

	List<PersonnelComposante> getListeComposantesByUidAndSourceAndAnnee(String uid, String source, Integer annee);

	Map<String, String> getOdfComposanteByRneAndAnneeAndActif(String rne, Integer annee);

	Map<String, String> getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante(String rne, 
																							  Integer currentAnnee, 
																							  String codTypDip,
																							  Integer codeNiveau, 
																							  String codeComposante,
																							  boolean actif);

	void addWsPub(WsPub ws);

	void deleteWsPub(WsPub wsPub);

	AccueilDecision getDecisionByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer currentAnnee);

	void deleteSituationUniversitaireByNumeroEtudiantAndAnneeIsNull();

	EtudiantRef getPresenceEtudiantRefByIne(String ine, Integer currentAnnee);

	IndOpi getPresenceEtudiantOPiByIneAndAnnee(String ine, Integer currentAnnee);

	List<PersonnelComposante> getListePersonnelsComposantesBySourceAndAnnee(String source, Integer annee);

	OffreDeFormationsDTO getOdfByPK(String rne, Integer annee, String codDip, Integer codVrsVdi, String codEtp, String codVrsVet, String codeCge);

	boolean getDroitsTransferts(String login, String source, Integer annee);

	List<IndOpi> getAllIndOpiBySynchroAndSource(Integer currentAnnee, Integer synchro, String source);

	Map<String, Long> getStatistiquesTransfert(String source, Integer annee);

	Map<String, Long> getStatistiquesTransfertAccueil(String source,
			Integer annee);

	Long getStatistiquesNombreTotalTransfertDepart(Integer currentAnnee);

	Long getStatistiquesNombreTotalTransfertAccueil(Integer currentAnnee);

	Long getStatistiquesNombreTotalTransfertOPI(Integer currentAnnee);

//	Test getTest(Integer id);
//
//	List<Test> getListeTests();
//
//	void addTest(Test test);

	void addValidationAutoByComposante(List<Composante> listeComposantes);

	List<Composante> getListeComposantesFromBddByAnneeAndSource(Integer currentAnnee, String source);

	Composante getComposantesFromBddByAnneeAndSourceAndCodeComposante(Integer annee, String source, String codeComposante);

	List<CGE> getListeCGEFromBddByAnneeAndSource(Integer currentAnnee,String source);

	void addValidationAutoByCGE(List<CGE> listeCGEMerge);

	CGE getCGEFromBddByAnneeAndSourceAndCodeCGE(Integer annee, String source, String codeCGE);

	PersonnelComposante getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante(
			String login, String source, Integer currentAnnee, String composante);

//	Map<String, String> getOdfComposanteByRneAndAnneeAndActifAndArrivee(
//			String rne, Integer currentAnnee);

	List<DatasExterne> getAllDatasExterneByIdentifiant(String identifiant);

	DatasExterne getAllDatasExterneByCodeInterditAndNumeroEtudiant(String identifiant, String code);

	List<DatasExterne> getAllDatasExterneByIdentifiantAndNiveau(String identifiant, Integer niveau);

//	Map<String, String> getOdfComposanteByRneAndAnneeAndActifAndArriveeAndCodTypDip(
//			String rne, Integer annee, String codTypDip);

	DatasExterne getDataExterneByIdentifiantAndCode(String identifiant, String code);

	IndOpi getOpiByNumeroOpi(IndOpi opi);

	AccueilAnnee getAccueilAnneeByLibelle(String libelle);

	AccueilAnnee getAccueilAnneeByIdAccueilAnnee(Integer idAccueilAnnee);

	AccueilResultat getAccueilResultatByIdAccueilResultat(Integer idAccueilResultat);

	List<IndOpi> getAllIndOpiBySynchroAndExcluAndSource(Integer currentAnnee, String source);

	List<AccueilResultat> getAccueilResultatSansNull();

	List<AccueilAnnee> getAccueilAnneeSansNull();

	List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposante(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeComposante, String source);

	List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndAtifOuPas(
			String rne, Integer currentAnnee, String codTypDip,
			Integer codeNiveau, String codeComposante, String source);

	Parametres updateConfiguration(Parametres param);

	void deleteSelectedOpi(IndOpi selectedOpiForDelete);

	Map<String, String> getOdfComposanteByRneAndAnneeAndCodTypDip(String rne,
			Integer currentAnnee, String codTypDip);

	EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(String numeroEtudiant, int annee, String source);

	List<TestUnitaireEtudiantRef> getAllTestUnitaireEtudiantRefBySource(String source);

	List<DatasExterne> getAllDatasExterneByNiveau(Integer niveau);

	List<IndOpi> getAllIndOpiByAnnee(Integer currentAnnee);

	void deleteOpi(IndOpi opi);

	EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee);

	EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee, String source);

	IndOpi getIndOpiByNneAndCleIneAndAnnee(String nne, String cleIne, Integer annee);

	List<SituationUniversitaire> getSituationUniversitaireByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer annee);

	void addFermeture(Fermeture myFermeture);

	List<Fermeture> getListeFermeturesBySourceAndAnnee(String source, int annee);

	List<Fermeture> addPeriodeFermetures(List<Fermeture> lFermetures);

	void deletePeriodeFermeture(String id);

	Fermeture getFermetureFromId(String id);

	EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance(String numeroEtudiant,
			Integer currentAnnee, String source);

	Versions getVersionByEtat(Integer etat);

	WebService updateWebService(WebService currentWs);

	WebService getWebServiceByCode(String code);

	void addPersonnelComposanteFromImport(List<PersonnelComposante> newLpc);

	Integer addFeedBackFromTransfertAccueilToTransfertDepart(String ine, Integer currentAnnee, String source, Integer temoinRetourTransfertAccueil);

    List<PersonnelComposante> getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante(String source, Integer currentAnnee, String composante);
}
