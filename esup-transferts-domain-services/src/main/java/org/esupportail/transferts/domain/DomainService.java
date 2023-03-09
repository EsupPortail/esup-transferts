/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.transferts.domain.beans.*;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author Farid AIT KARRA (Universite d'Artois) - 2016
 * 
 */
public interface DomainService extends Serializable {
	
	/**
	 * @param uid
	 * @return a user.
	 */
	public User getUser(String uid);

	/**
	 * 
	 * @return Tous les OPI (transferts entrants)
	 */
	public List<IndOpi> getAllIndOpiBySource(Integer annee, String source);	
	
	public EtudiantRef addDemandeTransferts(EtudiantRef currentEtudiant);

//	public List<EtudiantRef> getAllDemandesTransferts();
	
	public List<EtudiantRef> getAllDemandesTransfertsByAnnee(Integer annee, String source);	
	
	public List<EtudiantRef> getAllDemandesTransfertsByAnneeAndNonTraite(Integer annee, String source);

	public List<WsPub> getListeWsPub();
	
	public WsPub getWsPubByRneAndAnnee(String rne, Integer annee);
	
	public List<WsPub> getWsPubByAnnee(Integer annee);
	
	public void addAvis(Avis a);

	public List<Avis> getAvis(String numeroEtudiant, Integer annee);
	
	public Avis getDernierAvisFavorable(String numeroEtudiant, Integer annee);
	
	public List<EtatDossier> getEtatsDossier();
	
	public EtatDossier getEtatDossierById(Integer idEtatDossier);
	
	public List<LocalisationDossier> getLocalisationDossier();
	
	public LocalisationDossier getLocalisationDossierById(Integer idLocalisationDossier);
	
	public List<DecisionDossier> getDecisionDossier();
	
	public DecisionDossier getDecisionDossierById(Integer idDecisionDossier);

	public EtudiantRef getPresenceEtudiantRef(String numeroEtudiant, Integer annee);
	
	public EtudiantRefImp getPresenceEtudiantRefImp(String numeroEtudiant);			
	
	public void addFichier(Fichier f);

	public List<Fichier> getFichiersByAnneeAndFrom(Integer annee, String from);	
	
	public void updateDefautFichier(Fichier fichier, Integer annee, String from);	
	
	public Fichier getFichierDefautByAnneeAndFrom(Integer annee, String from);

	public List<Integer> getListeAnnees();

	public Fichier getFichierByIdAndAnneeAndFrom(String md5, Integer annee, String from);
	
//	public AdresseEtablissement getAdresseEtablissementByRne(String rne);
	
	public void deleteDemandeTransfert(EtudiantRef demandeTransferts, Integer annee);
	
	/*Statistiques*/
	public Long getDemandesTransfertsByEnCoursAndAnnee(Integer annee, String source);
	
	public Long getDemandesTransfertsByAvisSaisieAndAnnee(Integer annee, String source);
	
	public Long getDemandesTransfertsByTerminerAndAnnee(Integer annee, String source);
	
	public Long getDemandesTransfertsByPbOpiAndAnnee(Integer annee);
	
	public Long getDemandesTransfertsByEtabNonPartenaireAndAnnee(Integer annee);
	
	public Long getDemandesTransfertsByOpiOkAndAnnee(Integer annee);
	
//	public List<DatasExterne> getInterditBu(String numeroEtudiant);
	
	public List<String> getIndOpiExtractBySource(Integer annee, String source);
	
	public List<String> getVoeuxInsExtractBySource(Integer annee, String source);
	
	public EtudiantRef getEtudiantRef(String numeroEtudiant, Integer annee);
	
	public EtudiantRefExcel getEtudiantRefExcel();
	
	public CodeSizeAnnee getCodeSizeByAnnee(Integer annee);
	
	public List<CodeSizeAnnee> getAllCodeSize();	
	
	public CodeSizeAnnee getCodeSizeDefaut();
	
	public void updateDefautCodeSize(CodeSizeAnnee cs);
	
	public void addCodeSize(CodeSizeAnnee cs);

	public List<Parametres> getAllParametres();
	
	public Parametres getParametreByCode(String codeParametre);
	
	public void addParametre(Parametres param);
	
	public void updateLibelleVersionEtapeLocal(IndOpi etu);
	
	public void deleteFichier(String md5, Integer annee, String from) throws Exception;

	public List<OffreDeFormationsDTO> getSelectedOdfs(Integer currentAnnee, String rne);
	
	public void addOdfs(OffreDeFormationsDTO[] selectedOdfs);
	
	public Map<String, String> getOdfTypesDiplomeByRneAndAnnee(String rne, Integer currentAnnee, boolean actif, String source);
	
	public void updateWsPub(WsPub partenaire);
	
	public Date getDateMaxMajByRneAndAnnee(Integer annee, String rne);

	public Map<Integer, String> getAnneesEtudeByRneAndAnneeAndCodTypDip(String rne, Integer annee, String codTypDip, boolean actif, String source);

	public Map<String, String> getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(String rne, Integer currentAnnee, String codTypDip,	Integer codeNiveau, boolean actif, String source);

	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDip(String rne, Integer currentAnnee, String codTypDip, Integer codeNiveau, String codDip, String source);

	public List<IndOpi> getAllIndOpiNonSynchroAndSource(Integer annee, String source);

	public void updateIndOpi(IndOpi indOpi);

	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndAtifOuPas(String rne, Integer currentAnnee, String codTypDip, Integer codeNiveau, String codeDiplome);
	
	public List<OffreDeFormationsDTO> getAllOffreDeFormationByAnneeAndRneAndAtifOuPas(Integer currentAnnee, String rne);

	public List<AccueilAnnee> getListeAccueilAnnee();

	public List<AccueilResultat> getListeAccueilResultat();

	public AccueilAnnee getAccueilAnneeById(Integer id);

	public AccueilResultat getAccueilResultatById(Integer id);
	
	public Map<String, String> getListeManagersBySourceAndAnnee(String source, Integer annee);

	public void addPersonnelComposante(String uid, String source, Integer annee, List<PersonnelComposante> target);

	public List<PersonnelComposante> getListeComposantesByUidAndSourceAndAnnee(String login, String source, Integer annee);
	
	public Map<String, String> getOdfComposanteByRneAndAnneeAndActif(String rne, Integer annee);
	
	public 	Map<String, String> getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante(String rne, 
			  Integer currentAnnee, 
			  String codTypDip,
			  Integer codeNiveau, 
			  String codeComposante,
			  boolean actif);
	
	public void addWsPub(WsPub ws);

	public void deleteWsPub(WsPub wsPub);

	public AccueilDecision getDecisionByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer currentAnnee);
	
	public void deleteSituationUniversitaireByNumeroEtudiantAndAnneeIsNull();

	public EtudiantRef getPresenceEtudiantRefByIne(String ine, Integer currentAnnee);

	public IndOpi getPresenceEtudiantOPiByIneAndAnnee(String ine, Integer currentAnnee);

	public List<PersonnelComposante> getListePersonnelsComposantesBySourceAndAnnee(String source, Integer annee);

	public OffreDeFormationsDTO getOdfByPK(String rne, Integer annee, String codDip,	Integer codVrsVdi, String codEtp, String codVrsVet, String codeCge);

	public boolean getDroitsTransferts(String login, String source, Integer annee);

	public List<IndOpi> getAllIndOpiBySynchroAndSource(Integer currentAnnee, Integer synchro, String source);

	public Map<String, Long> getStatistiquesTransfert(String source, Integer annee);

	public Map<String, Long> getStatistiquesTransfertAccueil(String source, Integer annee);

	public Long getStatistiquesNombreTotalTransfertDepart(Integer currentAnnee);

	public Long getStatistiquesNombreTotalTransfertAccueil(Integer currentAnnee);

	public Long getStatistiquesNombreTotalTransfertOPI(Integer currentAnnee);

	public void addValidationAutoByComposante(List<Composante> listeComposantes);

	public List<Composante> getListeComposantesFromBddByAnneeAndSource(Integer currentAnnee, String source);
	
	public Composante getComposantesFromBddByAnneeAndSourceAndCodeComposante(Integer annee, String source, String codeComposante);

	public List<CGE> getListeCGEFromBddByAnneeAndSource(Integer currentAnnee,String source);

	public void addValidationAutoByCGE(List<CGE> listeCGEMerge);

	public CGE getCGEFromBddByAnneeAndSourceAndCodeCGE(Integer annee, String source, String codeCGE);

	public PersonnelComposante getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante(
			String login, String source, Integer currentAnnee, String composante);
	
	List<DatasExterne> getAllDatasExterneByIdentifiant(String identifiant);

	DatasExterne getAllDatasExterneByCodeInterditAndNumeroEtudiant(String identifiant, String code);
	
	List<DatasExterne> getAllDatasExterneByIdentifiantAndNiveau(String identifiant, Integer niveau);

	public DatasExterne getDataExterneByIdentifiantAndCode(String identifiant,String code);

	public void addIndOpi(IndOpi opi, boolean maj);

	public IndOpi getOpiByNumeroOpi(IndOpi opi);

	public AccueilAnnee getAccueilAnneeByLibelle(String libelle);

	public AccueilAnnee getAccueilAnneeByIdAccueilAnnee(Integer idAccueilAnnee);

	public AccueilResultat getAccueilResultatByIdAccueilResultat(
			Integer idAccueilResultat);

	public List<IndOpi> getAllIndOpiBySynchroAndExcluAndSource(Integer currentAnnee, String source);

	public List<AccueilResultat> getAccueilResultatSansNull();
	
	public List<AccueilAnnee> getAccueilAnneeSansNull();
	
	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposante(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeComposante, String source);

	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndAtifOuPas(
			String rne, Integer currentAnnee, String codTypDip,
			Integer codeNiveau, String codeComposante, String source);

	public Parametres updateConfiguration(Parametres param);

	public void deleteSelectedOpi(IndOpi selectedOpiForDelete);

	public Map<String, String> getOdfComposanteByRneAndAnneeAndCodTypDip(String rne, Integer currentAnnee, String codTypDip);

	public EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(String numeroEtudiant, int annee, String source);

	public List<TestUnitaireEtudiantRef> getAllTestUnitaireEtudiantRefBySource(String source);

	public List<DatasExterne> getAllDatasExterneByNiveau(Integer niveau);

	public List<IndOpi> getAllIndOpiByAnnee(Integer currentAnnee);

	public void deleteOpi(IndOpi opi);

	public EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee);

	public EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee, String source);

	public IndOpi getIndOpiByNneAndCleIneAndAnnee(String nne, String cleIne, Integer annee);

	public List<SituationUniversitaire> getSituationUniversitaireByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer annee);

	public void addFermeture(Fermeture myFermeture);

	public List<Fermeture> getListeFermeturesBySourceAndAnnee(String source, int annee);

	public List<Fermeture> addPeriodeFermetures(List<Fermeture> lFermetures);

	public void deletePeriodeFermeture(String id);

	public Fermeture getFermetureFromId(String id);

	public org.esupportail.transferts.domain.beans.EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance(
			String numeroEtudiant, Integer currentAnnee, String source);

	public Versions getVersionByEtat(Integer etat);

	public WebService updateWebService(WebService currentWs);

	public WebService getWebServiceByCode(String code);

	void addPersonnelComposanteFromImport(List<PersonnelComposante> newLpc);

    List<PersonnelComposante> getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante(String source, Integer currentAnnee, String composante);
}
