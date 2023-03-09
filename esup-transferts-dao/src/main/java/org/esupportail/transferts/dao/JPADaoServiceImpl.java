/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-blank
 */
package org.esupportail.transferts.dao;

import org.esupportail.commons.dao.AbstractGenericJPADaoService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.*;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
/**
 * @author Farid AIT KARRA (Universite d'Artois) - 2016
 *
 * The Hiberate implementation of the DAO service.
 */
public class JPADaoServiceImpl extends AbstractGenericJPADaoService implements DaoService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3152554337896617315L;

	/**
	 * For Logging.
	 */
	private static final Logger logger = new LoggerImpl(JPADaoServiceImpl.class);

	/**
	 * JPA entity manager
	 */
	transient EntityManager entityManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	//////////////////////////////////////////////////////////////
	// EntityManager
	//////////////////////////////////////////////////////////////

	/**
	 * @param em the em to set
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	/**
	 * @see org.esupportail.commons.dao.AbstractGenericJPADaoService#getEntityManager()
	 */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public EtudiantRef getEtudiantRef(String numeroEtudiant, Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("getEtudiantRef(String numeroEtudiant, Integer annee)");
		}
		try{
			EtudiantRefPK cleEtudiantRef = new EtudiantRefPK(numeroEtudiant, annee);
			EtudiantRef etudiant = entityManager.find(EtudiantRef.class, cleEtudiantRef);
			return etudiant;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public EtudiantRef addDemandeTransferts(EtudiantRef currentEtudiant) {
//		if (logger.isDebugEnabled())
//			logger.debug("public EtudiantRef addDemandeTransferts(EtudiantRef currentEtudiant)===>"+currentEtudiant+"<===");
		return entityManager.merge(currentEtudiant);
	}

	@Override
	public List<IndOpi> getAllIndOpiBySource(Integer annee, String source) {
		if (logger.isDebugEnabled())
			logger.debug("public List<IndOpi> getAllIndOpiBySource(Integer annee, String source)===>"+annee+"-----"+source+"<===");
		try{
			Query q = entityManager.createNamedQuery("allIndOpiBySource");
			//		Query q = entityManager.createNamedQuery("allIndOpi");
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			@SuppressWarnings("unchecked")
			List<IndOpi> ret = (List<IndOpi>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<WsPub> getListeWsPub() {
		if (logger.isDebugEnabled())
			logger.debug("public List<WsPub> getListeWsPub()");
		try{
			Query q = entityManager.createNamedQuery("allWsPub");
			@SuppressWarnings("unchecked")
			List<WsPub> ret = (List<WsPub>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public WsPub getWsPubByRneAndAnnee(String rne, Integer annee) {
		if (logger.isDebugEnabled())
		    logger.debug("getWsPubByRneAndAnnee(String rne, Integer annee)===>"+rne+"---"+annee+"<===");
		try{
			WsPubPK cleWsPub = new WsPubPK(rne, annee);
			WsPub ret = entityManager.find(WsPub.class, cleWsPub);
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<WsPub> getWsPubByAnnee(Integer annee) {
		if (logger.isDebugEnabled())
			logger.debug("public List<WsPub> getWsPubByAnnee(Integer annee)===>"+annee+"<===");
		try{
			Query q = entityManager.createNamedQuery("getWsPubByAnnee");
			q.setParameter("annee", annee);
			@SuppressWarnings("unchecked")
			List<WsPub> ret = (List<WsPub>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addAvis(Avis a) {
		if (logger.isDebugEnabled()){
			logger.debug("addAvis()");
			logger.debug("Avis --> "+a);
		}
		entityManager.merge(a);
	}

	@Override
	public List<Avis> getAvis(String numeroEtudiant, Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("getAvis(String numeroEtudiant)");
		}
		try{
			Query q = entityManager.createNamedQuery("getAvisByNumeroEtudiantAndAnnee");
			q.setParameter("numEtu", numeroEtudiant);
			q.setParameter("annee", annee);
			@SuppressWarnings("unchecked")
			List<Avis> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return new ArrayList<Avis>();
		}
	}

	@Override
	public List<EtatDossier> getEtatsDossier() {
		if (logger.isDebugEnabled()){
			logger.debug("getEtatsDossier()");
		}
		try{
			Query q = entityManager.createNamedQuery("getEtatsDossier");
			@SuppressWarnings("unchecked")
			List<EtatDossier> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return new ArrayList<EtatDossier>();
		}
	}

	@Override
	public EtatDossier getEtatDossierById(Integer idEtatDossier) {
		if (logger.isDebugEnabled()){
			logger.debug("===>getEtatDossierById(Integer idEtatDossier)<===");
		}
		try{
			Query q = entityManager.createNamedQuery("getEtatDossierByIdEtatDossier");
			q.setParameter("idEtatDossier", idEtatDossier);
			EtatDossier ret = (EtatDossier) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<LocalisationDossier> getLocalisationDossier() {
		if (logger.isDebugEnabled()){
			logger.debug("getLocalisationDossier()");
		}
		try{
			Query q = entityManager.createNamedQuery("getLocalisationDossier");
			@SuppressWarnings("unchecked")
			List<LocalisationDossier> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return new ArrayList<LocalisationDossier>();
		}
	}

	@Override
	public LocalisationDossier getLocalisationDossierById(Integer idLocalisationDossier)
	{
		if (logger.isDebugEnabled()){
			logger.debug("getLocalisationDossierById(Integer idLocalisationDossier)");
		}
		try{
			Query q = entityManager.createNamedQuery("getLocalisationDossierByIdLocalisationDossier");
			q.setParameter("idLocalisationDossier", idLocalisationDossier);
			LocalisationDossier ret = (LocalisationDossier) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<DecisionDossier> getDecisionDossier() {
		if (logger.isDebugEnabled()){
			logger.debug("getDecisionDossier()");
		}
		try{
			Query q = entityManager.createNamedQuery("getDecisionsDossier");
			@SuppressWarnings("unchecked")
			List<DecisionDossier> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return new ArrayList<DecisionDossier>();
		}
	}

	@Override
	public DecisionDossier getDecisionDossierById(Integer idDecisionDossier) {
		if (logger.isDebugEnabled()){
			logger.debug("getDecisionDossierById(Integer idDecisionDossier)");
		}
		try{
			Query q = entityManager.createNamedQuery("getDecisionDossierByIdDecisionDossier");
			q.setParameter("idDecisionDossier", idDecisionDossier);
			DecisionDossier ret = (DecisionDossier) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addFichier(Fichier f) {
		if (logger.isDebugEnabled()){
			logger.debug("===>addFichier(Fichier f)<===");
			logger.debug("Fichier===>"+f+"<===");
		}
		entityManager.merge(f);
	}

	@Override
	public List<Fichier> getFichiersByAnneeAndFrom(Integer annee, String from) {
		if (logger.isDebugEnabled()){
			logger.debug("getFichiersByAnneeAndFrom()===>"+annee+"---"+from+"<===");
		}
		try{
			Query q = entityManager.createNamedQuery("getFichiersByAnneeAndFrom");
			q.setParameter("annee", annee);
			q.setParameter("from", from);
			List<Fichier> ret = (List<Fichier>) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void updateDefautFichier(Fichier fichier, Integer annee, String from) {
		if (logger.isDebugEnabled()){
			logger.debug("updateDefautFichier(Fichier fichier)");
			logger.debug("fichier --> "+fichier);
		}
		Query q = entityManager.createNamedQuery("resetDefautFichierByAnneeAndFrom");
		q.setParameter("annee", annee);
		q.setParameter("from", from);
		q.executeUpdate();
		entityManager.merge(fichier);
	}

	@Override
	public Fichier getFichierDefautByAnneeAndFrom(Integer annee, String from) {
		if (logger.isDebugEnabled()){
			logger.debug("getFichierDefautByAnneeAndFrom");
		}
		try{
			Query q = entityManager.createNamedQuery("getFichierDefautByAnneeAndFrom");
			q.setParameter("annee", annee);
			q.setParameter("from", from);
			Fichier ret = (Fichier) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addIndOpi(IndOpi opi, boolean maj) {
		if (logger.isDebugEnabled()){
			logger.debug("addIndOpi()");
			logger.debug("opi --> "+opi);
			logger.debug("maj --> "+maj);
		}
		try{
			if(!maj)
			{
				SequenceOpi so = new SequenceOpi();
				SequenceOpi som = entityManager.merge(so);

				NumberFormat nf = new DecimalFormat("0000");

				String value = nf.format(som.getId());
				if (logger.isDebugEnabled()){
					logger.debug("Numero de sequence --> "+value);
				}
				String moduloBase32 = opi.getNumeroOpi();
				opi.setNumeroOpi(moduloBase32+value);
				opi.getVoeux().setNumeroOpi(moduloBase32+value);
			}
			entityManager.merge(opi);
		}
		catch(NoResultException e){
			logger.warn(e);
			e.fillInStackTrace();
		}
	}

	@Override
	public List<EtudiantRef> getAllDemandesTransfertsByAnnee(Integer annee, String source) {
		if (logger.isDebugEnabled())
			logger.debug("getAllDemandesTransfertsByAnnee(Integer annee, String source)===>"+annee+"-----"+source+"<===");
		try{
			Query q = entityManager.createNamedQuery("allDemandesTransfertsByAnnee");
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			@SuppressWarnings("unchecked")
			List<EtudiantRef> ret = (List<EtudiantRef>) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<Integer> getListeAnnees() {
		if (logger.isDebugEnabled()){
			logger.debug("getListeAnnees()");
		}
		try{
			Query q = entityManager.createNamedQuery("getAnneeCodeSize");
			@SuppressWarnings("unchecked")
			List<Integer> ret = (List<Integer>) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Fichier getFichierByIdAndAnneeAndFrom(String md5, Integer annee, String from) {
		if (logger.isDebugEnabled())
			logger.debug("getFichierByIdAndAnneeAndFrom(String md5, Integer annee, String from)-->"+md5+"-----"+annee+"-----"+from);
		try
		{
			FichierPK cleFichier = new FichierPK(md5, annee, from);
			Fichier fichier = entityManager.find(Fichier.class, cleFichier);
			return fichier;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Avis getDernierAvisFavorable(String numeroEtudiant, Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("getDernierAvisFavorable(String numeroEtudiant, Integer annee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getDernierAvisFavorableByNumeroEtudiantAndAnnee");
			q.setParameter("numEtu", numeroEtudiant);
			q.setParameter("annee", annee);
			q.setMaxResults(1);
			Avis ret = (Avis) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return new Avis();
		}
	}

	@Override
	public void deleteDemandeTransfert(EtudiantRef demandeTransferts, Integer annee)
	{
		if (logger.isDebugEnabled())
			logger.debug("deleteDemandeTransfert(EtudiantRef demandeTransferts)");
		try{
			Query q = entityManager.createNamedQuery("deleteAvisByNumeroEtudiantAndAnnee");
			q.setParameter("annee", annee);
			q.setParameter("numEtu", demandeTransferts.getNumeroEtudiant());
			int delete = q.executeUpdate();
			if (logger.isDebugEnabled()){
				logger.debug("delete ---> "+delete);
			}
			EtudiantRefPK cleEtudiantRef = new EtudiantRefPK(demandeTransferts.getNumeroEtudiant(), annee);
			EtudiantRef etudiant = entityManager.find(EtudiantRef.class, cleEtudiantRef);
			entityManager.remove(etudiant);
		}
		catch(NoResultException e){
			logger.warn(e);
		}
	}

	/*Statistiques*/
	@Override
	public Long getDemandesTransfertsByEnCoursAndAnnee(Integer annee, String source) {
		if (logger.isDebugEnabled()){
			logger.debug("getDemandesTransfertsByEnCoursAndAnnee(Integer annee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getDemandesTransfertsByEnCoursAndAnnee");
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			Long ret = (long) q.getResultList().size();
			if (logger.isDebugEnabled())
				logger.debug("getDemandesTransfertsByEnCoursAndAnnee(Integer annee) --> " + ret);
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Long getDemandesTransfertsByAvisSaisieAndAnnee(Integer annee, String source) {
		if (logger.isDebugEnabled()){
			logger.debug("getDemandesTransfertsByAvisSaisieAndAnnee(Integer annee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getDemandesTransfertsByAvisSaisieAndAnnee");
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			Long ret = (long) q.getResultList().size();
			if (logger.isDebugEnabled())
				logger.debug("getDemandesTransfertsByAvisSaisieAndAnnee(Integer annee) --> " + ret);
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Long getDemandesTransfertsByTerminerAndAnnee(Integer annee, String source) {
		if (logger.isDebugEnabled()){
			logger.debug("getDemandesTransfertsByTerminerAndAnnee(Integer annee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getDemandesTransfertsByTerminerAndAnnee");
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			Long ret = (long) q.getResultList().size();
			logger.debug("getDemandesTransfertsByTerminerAndAnnee(Integer annee) --> " + ret);
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Long getDemandesTransfertsByPbOpiAndAnnee(Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("getDemandesTransfertsByPbOpiAndAnnee(Integer annee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getDemandesTransfertsByPbOpiAndAnnee");
			q.setParameter("annee", annee);
			Long ret = (long) q.getResultList().size();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Long getDemandesTransfertsByEtabNonPartenaireAndAnnee(Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("getDemandesTransfertsByEtabNonPartenaireAndAnnee(Integer annee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getDemandesTransfertsByEtabNonPartenaireAndAnnee");
			q.setParameter("annee", annee);
			Long ret = (long) q.getResultList().size();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Long getDemandesTransfertsByOpiOkAndAnnee(Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("getDemandesTransfertsByOpiOkAndAnnee(Integer annee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getDemandesTransfertsByOpiOkAndAnnee");
			q.setParameter("annee", annee);
			Long ret = (long) q.getResultList().size();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<DatasExterne> getAllDatasExterneByIdentifiant(String identifiant) {
		if (logger.isDebugEnabled())
			logger.debug("public List<DatasExterne> getAllDatasExterneByIdentifiant(String identifiant)-->"+identifiant);
		try{
			Query q = entityManager.createNamedQuery("getAllDatasExterneByIdentifiant");
			q.setParameter("identifiant", identifiant);
			@SuppressWarnings("unchecked")
			List<DatasExterne> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<DatasExterne> getAllDatasExterneByIdentifiantAndNiveau(String identifiant, Integer niveau)
	{
		if (logger.isDebugEnabled())
			logger.debug("public List<DatasExterne> getAllDatasExterneByIdentifiantAndNiveau(String identifiant, Integer niveau)===>"+identifiant+"<======>"+niveau+"<===");
		try{
			Query q = entityManager.createNamedQuery("getAllDatasExterneByIdentifiantAndNiveau");
			q.setParameter("identifiant", identifiant);
			q.setParameter("niveau", niveau);
			List<DatasExterne> ret = q.getResultList();
//			if(ret.isEmpty())
//				return null;
//			else
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public DatasExterne getAllDatasExterneByCodeInterditAndNumeroEtudiant(String identifiant, String code)
	{
		if (logger.isDebugEnabled())
			logger.debug("public List<DatasExterne> getAllDatasExterneByCodeInterditAndNumeroEtudiant(String numeroEtudiant, String codeInterdit)-->"+identifiant+"-----"+code);
		try{
			DatasExternePK cleDatasExterne = new DatasExternePK(identifiant, code);
			DatasExterne ret = entityManager.find(DatasExterne.class, cleDatasExterne);
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<String> getIndOpiExtractBySource(Integer annee, String source) {
		if (logger.isDebugEnabled()){
			logger.debug("public List<String> getIndOpiExtractBySource(Integer annee, String source)-->"+annee+"-----"+source);
		}
		try{
			Query q = entityManager.createNamedQuery("getIndOpiExtractBySource");
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			@SuppressWarnings("unchecked")
			List<String> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<String> getVoeuxInsExtractBySource(Integer annee, String source) {
		if (logger.isDebugEnabled()){
			logger.debug("public List<String> getVoeuxInsExtractBySource(Integer annee, String Source)-->"+annee+"-----"+source);
		}
		try{
			Query q = entityManager.createNamedQuery("getVoeuxInsExtractBySource");
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			@SuppressWarnings("unchecked")
			List<String> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public CodeSizeAnnee getCodeSizeByAnnee(Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("getCodeSizeByAnnee(Integer annee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getCodeSizeByAnnee");
			q.setParameter("annee", annee);
			CodeSizeAnnee ret = (CodeSizeAnnee) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<CodeSizeAnnee> getAllCodeSize() {
		if (logger.isDebugEnabled()){
			logger.debug("getAllCodeSize()");
		}
		try{
			Query q = entityManager.createNamedQuery("getAllCodeSize");
			@SuppressWarnings("unchecked")
			List<CodeSizeAnnee> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addCodeSize(CodeSizeAnnee cs) {
		if (logger.isDebugEnabled()){
			logger.debug("addCodeSize(CodeSizeAnnee cs)");
			logger.debug("CodeSizeAnnee --> "+cs);
		}
		entityManager.merge(cs);
	}

	@Override
	public void updateDefautCodeSize(CodeSizeAnnee cs) {
		if (logger.isDebugEnabled()){
			logger.debug("updateDefautCodeSize(CodeSizeAnnee cs)");
			logger.debug("CodeSizeAnnee --> "+cs);
		}
		Query q = entityManager.createNamedQuery("resetDefautCodeSize");
		q.executeUpdate();
		entityManager.merge(cs);
	}

	@Override
	public CodeSizeAnnee getCodeSizeDefaut() {
		if (logger.isDebugEnabled()){
			logger.debug("getCodeSizeDefaut()");
		}
		try{
			Query q = entityManager.createNamedQuery("getCodeSizeDefaut");
			CodeSizeAnnee ret = (CodeSizeAnnee) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<Parametres> getAllParametres() {
		if (logger.isDebugEnabled()){
			logger.debug("getAllParametres()");
		}
		try{
			Query q = entityManager.createNamedQuery("getAllParametres");
			@SuppressWarnings("unchecked")
			List<Parametres> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Parametres getParametreByCode(String codeParametre) {
		if (logger.isDebugEnabled())
			logger.debug("public Parametres getParametreByCode(String codeParametre)===>"+codeParametre+"<===");

		try{
			Query q = entityManager.createNamedQuery("getParametreByCode");
			q.setParameter("codeParametre", codeParametre);
			Parametres ret = (Parametres) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addParametre(Parametres param) {
		if (logger.isDebugEnabled()){
			logger.debug("addParametre(Parametres param)");
			logger.debug("param --> "+param);
		}
		entityManager.merge(param);
	}

	@Override
	public List<EtudiantRef> getAllDemandesTransfertsByAnneeAndNonTraite(Integer annee, String source) {
		if (logger.isDebugEnabled()){
			logger.debug("getAllDemandesTransfertsByAnneeAndNonTraite(Integer annee, String source)");
		}
		try{
			Query q = entityManager.createNamedQuery("allDemandesTransfertsByAnneeAndNonTraite");
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			@SuppressWarnings("unchecked")
			List<EtudiantRef> ret = (List<EtudiantRef>) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void updateLibelleVersionEtapeLocal(IndOpi etu) {
		if (logger.isDebugEnabled()){
			logger.debug("updateLibelleVersionEtapeLocal(IndOpi etu)");
			logger.debug("Etudiant --> "+etu);
		}
		entityManager.merge(etu);
	}

	@Override
	public void deleteFichier(String md5, Integer annee, String from) throws Exception
	{
		if (logger.isDebugEnabled()){
			logger.debug("deleteFichier(String md5, Integer annee, String from)");
			logger.debug("-->"+md5+"-"+annee+"-"+from);
		}
		FichierPK cleFichier = new FichierPK(md5, annee, from);
		Fichier fichier = entityManager.find(Fichier.class, cleFichier);
		entityManager.remove(fichier);
	}

	@Override
	public List<OffreDeFormationsDTO> getSelectedOdfs(Integer currentAnnee, String rne) {
		if (logger.isDebugEnabled())
			logger.debug("getSelectedOdfs(Integer currentAnnee, String rne)");
		try{
			Query q = entityManager.createNamedQuery("getAllOffreDeFormationByAnneeAndRne");
			q.setParameter("annee", currentAnnee);
			q.setParameter("rne", rne);
			@SuppressWarnings("unchecked")
			List<OffreDeFormationsDTO> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<OffreDeFormationsDTO> getAllOffreDeFormationByAnneeAndRneAndAtifOuPas(Integer currentAnnee, String rne) {
		if (logger.isDebugEnabled())
			logger.debug("getAllOffreDeFormationByAnneeAndRneAndAtifOuPas(Integer currentAnnee, String rne)");
		try{
			Query q = entityManager.createNamedQuery("getAllOffreDeFormationByAnneeAndRneAndAtifOuPas");
			q.setParameter("annee", currentAnnee);
			q.setParameter("rne", rne);
			@SuppressWarnings("unchecked")
			List<OffreDeFormationsDTO> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addOdfs(OffreDeFormationsDTO[] selectedOdfs) {
		if (logger.isDebugEnabled())
			logger.debug("addOdfs(OffreDeFormationsDTO[] selectedOdfs)");
		Date date = new Date();
		for(OffreDeFormationsDTO odf : selectedOdfs)
		{
			odf.setDateMaj(date);
			entityManager.merge(odf);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getOdfTypesDiplomeByRneAndAnnee(String rne, Integer currentAnnee, boolean actif, String source) {
		if (logger.isDebugEnabled()){
			logger.debug("getOdfTypesDiplomeByRneAndAnnee(String rne, Integer currentAnnee, boolean actif)");
		}
		try{
			Query q;
			if(actif)
			{
				if("D".equals(source))
					q = entityManager.createNamedQuery("getOdfTypesDiplomeByRneAndAnneeAndDepart");
				else
					q = entityManager.createNamedQuery("getOdfTypesDiplomeByRneAndAnneeAndArrivee");
			}
			else
				q = entityManager.createNamedQuery("getAllOdfTypesDiplomeByRneAndAnnee");
			q.setParameter("rne", rne);
			q.setParameter("annee", currentAnnee);
			Map<String, String> map = new HashMap<String, String>();

			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1)
			{
				String codeTypeDiplome = (String)resultElement[0];
				String libTypeDiplome = (String)resultElement[1];
				if (logger.isDebugEnabled()){
					logger.debug("################### codeTypeDiplome --> " + codeTypeDiplome);
					logger.debug("################### libTypeDiplome --> " + libTypeDiplome);
				}
				map.put(codeTypeDiplome, libTypeDiplome);
			}
			return map;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void updateWsPub(WsPub partenaire) {
		entityManager.merge(partenaire);
	}

	@Override
	public Date getDateMaxMajByRneAndAnnee(Integer annee, String rne) {
		if (logger.isDebugEnabled())
			logger.debug("public Date getDateMaxMajByRneAndAnnee(Integer annee, String rne)===>"+annee+"-----"+rne+"<===");
		try{
			Query q = entityManager.createNamedQuery("getDateMaxMajByRneAndAnnee");
			q.setParameter("annee", annee);
			q.setParameter("rne", rne);
			Date ret = (Date) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<OffreDeFormationsDTO> getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne(Date maxDate, Integer annee, String rne) {
		if (logger.isDebugEnabled())
			logger.debug("getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne(Date maxDate, Integer annee, String rne)");
		try{
			Query q = entityManager.createNamedQuery("getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne");
			q.setParameter("dateMax", maxDate);
			q.setParameter("annee", annee);
			q.setParameter("rne", rne);
			@SuppressWarnings("unchecked")
			List<OffreDeFormationsDTO> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<OffreDeFormationsDTO> getFormationsByRneAndAnnee(String rne, Integer annee) {
		if (logger.isDebugEnabled())
			logger.debug("getFormationsByRneAndAnnee(String rne, Integer annee)===>"+rne+"/"+annee+"<===");
		try{
			Query q = entityManager.createNamedQuery("getFormationsByRneAndAnnee");
			q.setParameter("rne", rne);
			q.setParameter("annee", annee);
			@SuppressWarnings("unchecked")
			List<OffreDeFormationsDTO> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Map<Integer, String> getAnneesEtudeByRneAndAnneeAndCodTypDip(String rne, Integer annee, String codTypDip, boolean actif, String source) {
		if (logger.isDebugEnabled())
			logger.debug("getAnneesEtudeByRneAndAnneeAndCodTypDip(String rne, Integer annee, String codTypDip, boolean actif)");
		try{
			Query q;
			if(actif)
				if("D".equals(source))
					q = entityManager.createNamedQuery("getAnneesEtudeByRneAndAnneeAndCodTypDipAndDepart");
				else
					q = entityManager.createNamedQuery("getAnneesEtudeByRneAndAnneeAndCodTypDipAndArrivee");
			else
				q = entityManager.createNamedQuery("getAllAnneesEtudeByRneAndAnneeAndCodTypDip");
			q.setParameter("rne", rne);
			q.setParameter("annee", annee);
			q.setParameter("codTypDip", codTypDip);
			Map<Integer, String> map = new HashMap<Integer, String>();
			@SuppressWarnings("unchecked")
			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1)
			{
				Integer codeNiveau = (Integer)resultElement[0];
				String libNiveau = (String)resultElement[1];
				if (logger.isDebugEnabled())
				{
					logger.debug("################### codeNiveau --> " + codeNiveau);
					logger.debug("################### libNiveau --> " + libNiveau);
				}
				map.put(codeNiveau, libNiveau);
			}
			return map;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Map<String, String> getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(String rne, Integer currentAnnee, String codTypDip,	Integer codeNiveau, boolean actif, String source) {
		if (logger.isDebugEnabled()){
			logger.debug("getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(String rne, Integer currentAnnee, String codTypDip,	Integer codeNiveau, boolean actif)");
		}
		try{
			Query q;
			if(actif) {
				if ("D".equals(source))
					q = entityManager.createNamedQuery("getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndDepart");
				else
					q = entityManager.createNamedQuery("getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndArrivee");
			}else
			{
				q = entityManager.createNamedQuery("getAllLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau");
			}
			q.setParameter("rne", rne);
			q.setParameter("annee", currentAnnee);
			q.setParameter("codTypDip", codTypDip);
			q.setParameter("codeNiveau", codeNiveau);
			Map<String, String> map = new HashMap<String, String>();

			@SuppressWarnings("unchecked")
			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1)
			{
				String codeDiplome = (String)resultElement[0];
				String libDiplome = (String)resultElement[1];
				if (logger.isDebugEnabled())
				{
					logger.debug("################### codeDiplome --> " + codeDiplome);
					logger.debug("################### libDiplome --> " + libDiplome);
				}
				map.put(codeDiplome, libDiplome);
			}
			return map;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Map<String, String> getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante(String rne,
																									 Integer currentAnnee,
																									 String codTypDip,
																									 Integer codeNiveau,
																									 String codeComposante,
																									 boolean actif)
	{
		if (logger.isDebugEnabled())
		    logger.debug("getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante(String rne, Integer currentAnnee, String codTypDip, Integer codeNiveau, String codeComposante)===>"+rne+"/"+currentAnnee+"<===");

		try{
			Query q;
			if(actif)
				q = entityManager.createNamedQuery("getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante");
			else
				q = entityManager.createNamedQuery("getAllLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante");
			q.setParameter("rne", rne);
			q.setParameter("annee", currentAnnee);
			q.setParameter("codTypDip", codTypDip);
			q.setParameter("codeNiveau", codeNiveau);
			q.setParameter("codeComposante", codeComposante);
			Map<String, String> map = new HashMap<String, String>();

			@SuppressWarnings("unchecked")
			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1)
			{
				String codeDiplome = (String)resultElement[0];
				String libDiplome = (String)resultElement[1];
				if (logger.isDebugEnabled())
				{
					logger.debug("################### codeDiplome --> " + codeDiplome);
					logger.debug("################### libDiplome --> " + libDiplome);
				}
				map.put(codeDiplome, libDiplome);
			}
			return map;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDip(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeDiplome, String source) {
		if (logger.isDebugEnabled())
			logger.debug("getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDip(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeDiplome)");
		try{
			Query q;
			if("D".equals(source))
				q = entityManager.createNamedQuery("getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndDepart");
			else
				q = entityManager.createNamedQuery("getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndArrivee");
			q.setParameter("annee", annee);
			q.setParameter("rne", rne);
			q.setParameter("codTypDip", codTypDip);
			q.setParameter("codeNiveau", codeNiveau);
			q.setParameter("codeDiplome", codeDiplome);
			@SuppressWarnings("unchecked")
			List<OffreDeFormationsDTO> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposante(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeComposante, String source) {
		if (logger.isDebugEnabled())
			logger.debug("public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposante(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeComposante, String source)");
		try{
			Query q;
			if("D".equals(source))
				q = entityManager.createNamedQuery("getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndDepart");
			else
				q = entityManager.createNamedQuery("getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndArrivee");
			q.setParameter("annee", annee);
			q.setParameter("rne", rne);
			q.setParameter("codTypDip", codTypDip);
			q.setParameter("codeNiveau", codeNiveau);
			q.setParameter("codeComposante", codeComposante);
			@SuppressWarnings("unchecked")
			List<OffreDeFormationsDTO> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndAtifOuPas(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeDiplome) {
		if (logger.isDebugEnabled())
			logger.debug("getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndAtifOuPas(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeDiplome)");
		try{
			Query q = entityManager.createNamedQuery("getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndAtifOuPas");
			q.setParameter("annee", annee);
			q.setParameter("rne", rne);
			q.setParameter("codTypDip", codTypDip);
			q.setParameter("codeNiveau", codeNiveau);
			q.setParameter("codeDiplome", codeDiplome);
			@SuppressWarnings("unchecked")
			List<OffreDeFormationsDTO> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<IndOpi> getAllIndOpiNonSynchroAndSource(Integer annee, String source) {
		if (logger.isDebugEnabled()){
			logger.debug("public List<IndOpi> getAllIndOpiNonSynchroAndSource(Integer annee, String source)");
		}
		Query q = entityManager.createNamedQuery("allIndOpiNonSynchroAndSource");
		q.setParameter("annee", annee);
		q.setParameter("source", source);
		@SuppressWarnings("unchecked")
		List<IndOpi> ret = (List<IndOpi>)q.getResultList();
		if(ret.isEmpty())
			return null;
		else
			return ret;
	}

	public void updateIndOpi(IndOpi opi) {
		if (logger.isDebugEnabled()){
			logger.debug("public void updateIndOpi(IndOpi opi)");
			logger.debug("opi --> "+opi);
		}
		try{
			entityManager.merge(opi);
		}
		catch(NoResultException e){
			logger.warn(e);
			e.fillInStackTrace();
		}
	}

	@Override
	public List<AccueilAnnee> getListeAccueilAnnee() {
		if (logger.isDebugEnabled()){
			logger.debug("getListeAccueilAnnee()");
		}
		try{
			Query q = entityManager.createNamedQuery("getAccueilAnnee");
			@SuppressWarnings("unchecked")
			List<AccueilAnnee> ret = (List<AccueilAnnee>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<AccueilAnnee> getAccueilAnneeSansNull() {
		if (logger.isDebugEnabled()){
			logger.debug("getAccueilAnneeSansNull()");
		}
		try{
			Query q = entityManager.createNamedQuery("getAccueilAnneeSansNull");
			@SuppressWarnings("unchecked")
			List<AccueilAnnee> ret = (List<AccueilAnnee>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<AccueilResultat> getListeAccueilResultat() {
		if (logger.isDebugEnabled()){
			logger.debug("getListeAccueilResultat()");
		}
		try{
			Query q = entityManager.createNamedQuery("getAccueilResultat");
			@SuppressWarnings("unchecked")
			List<AccueilResultat> ret = (List<AccueilResultat>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<AccueilResultat> getAccueilResultatSansNull() {
		if (logger.isDebugEnabled()){
			logger.debug("getAccueilResultatSansNull()");
		}
		try{
			Query q = entityManager.createNamedQuery("getAccueilResultatSansNull");
			@SuppressWarnings("unchecked")
			List<AccueilResultat> ret = (List<AccueilResultat>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public AccueilAnnee getAccueilAnneeById(Integer id) {
		if (logger.isDebugEnabled())
			logger.debug("getAccueilAnneeById(Integer id)");
		try{
			AccueilAnnee ret = entityManager.find(AccueilAnnee.class, id);
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public AccueilResultat getAccueilResultatById(Integer id) {
		if (logger.isDebugEnabled())
			logger.debug("getAccueilResultatById(Integer id)");
		try{
			AccueilResultat ret = entityManager.find(AccueilResultat.class, id);
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Map<String, String> getListeManagersBySourceAndAnnee(String source, Integer annee)
	{
		if (logger.isDebugEnabled()){
			logger.debug("getListeManagersBySourceAndAnnee(String source, Integer annee)-->"+source+"-----"+annee);
		}
		try{
			Query q = entityManager.createNamedQuery("getListeManagersBySourceAndAnnee");
			q.setParameter("source", source);
			q.setParameter("annee", annee);
			Map<String, String> map = new HashMap<String, String>();

			@SuppressWarnings("unchecked")
			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1)
			{
				String codeDiplome = (String)resultElement[0];
				String libDiplome = (String)resultElement[1];
				if (logger.isDebugEnabled())
				{
					logger.debug("################### codeDiplome --> " + codeDiplome);
					logger.debug("################### libDiplome --> " + libDiplome);
				}
				map.put(codeDiplome, libDiplome);
			}
			return map;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addPersonnelComposante(String uid, String source, Integer annee, List<PersonnelComposante> target) {
		if (logger.isDebugEnabled()){
			logger.debug("addPersonnelComposante(String uid, String source, Integer annee, List<PersonnelComposante> target)");
			logger.debug("uid --> "+uid);
			logger.debug("source --> "+source);
			logger.debug("annee --> "+annee);
			if(target!=null)
				logger.debug("target.size() --> "+target.size());
		}
		Query q = entityManager.createNamedQuery("deleteListeManagersByUidAndSourceAndAnnee");
		q.setParameter("uid", uid);
		q.setParameter("source", source);
		q.setParameter("annee", annee);
		int delete = q.executeUpdate();
		if (logger.isDebugEnabled()){
			logger.debug("delete ---> "+delete);
		}
		if(target!=null && !target.isEmpty()) {
			for (PersonnelComposante pc : target) {
				entityManager.merge(pc);
			}
		}
	}

	@Override
	public List<PersonnelComposante> getListeComposantesByUidAndSourceAndAnnee(String uid, String source, Integer annee)
	{
		if (logger.isDebugEnabled())
			logger.debug("DAO Impl public List<PersonnelComposante> getListeComposantesByUidAndSourceAndAnnee(String uid, String source, Integer annee)===>"+uid+"/"+source+"/"+annee+"<===");

		try{
			Query q = entityManager.createNamedQuery("getListeComposantesByUidAndSourceAndAnnee");
			q.setParameter("uid", uid);
			q.setParameter("source", source);
			q.setParameter("annee", annee);
			@SuppressWarnings("unchecked")
			List<PersonnelComposante> ret = (List<PersonnelComposante>) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Map<String, String> getOdfComposanteByRneAndAnneeAndActif(String rne, Integer annee) {
		if (logger.isDebugEnabled())
			logger.debug("Map<Integer, String> getOdfComposanteByRneAndAnneeAndActif(String rne, Integer annee)");
		try{
			Query q = entityManager.createNamedQuery("getOdfComposanteByRneAndAnneeAndActif");
			q.setParameter("rne", rne);
			q.setParameter("annee", annee);
			Map<String, String> map = new HashMap<String, String>();
			@SuppressWarnings("unchecked")
			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1)
			{
				String codeComposante = (String)resultElement[0];
				String libComposante = (String)resultElement[1];
				if (logger.isDebugEnabled())
				{
					logger.debug("################### codeComposante --> " + codeComposante);
					logger.debug("################### libComposante --> " + libComposante);
				}
				map.put(codeComposante, libComposante);
			}
			return map;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addWsPub(WsPub ws) {
		if (logger.isDebugEnabled())
		    logger.debug("public void addWsPub(WsPub ws)===>"+ws+"<===");
		entityManager.merge(ws);
	}

	@Override
	public void deleteWsPub(WsPub wsPub) {
		if (logger.isDebugEnabled()){
			logger.debug("deleteWsPub(WsPub wsPub)");
			logger.debug("-->"+wsPub);
		}
		WsPubPK cleWsPub = new WsPubPK(wsPub.getRne(),wsPub.getAnnee());
		WsPub part = entityManager.find(WsPub.class, cleWsPub);
		entityManager.remove(part);
	}

	@Override
	public AccueilDecision getDecisionByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer currentAnnee)
	{
		if (logger.isDebugEnabled())
			logger.debug("getDecisionByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer currentAnnee)");
		try{
			EtudiantRefPK cleAccueilAnnee = new EtudiantRefPK(numeroEtudiant, currentAnnee);
			AccueilDecision ret = entityManager.find(AccueilDecision.class, cleAccueilAnnee);
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void deleteSituationUniversitaireByNumeroEtudiantAndAnneeIsNull() {
		Query query = entityManager.createNativeQuery("delete FROM SITUATION_UNIVERSITAIRE WHERE numeroEtudiant IS NULL AND annee IS NULL");
		int delete = query.executeUpdate();
		if (logger.isDebugEnabled()){
			logger.debug("delete ---> "+delete);
		}
	}

	@Override
	public EtudiantRef getPresenceEtudiantRefByIne(String ine, Integer currentAnnee) {
		if (logger.isDebugEnabled()){
			logger.debug("public EtudiantRef getPresenceEtudiantRefByIne(String ine, Integer currentAnnee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getPresenceEtudiantRefByIneAndAnnee");
			q.setParameter("numeroIne", ine);
			q.setParameter("annee", currentAnnee);

			EtudiantRef etudiant = (EtudiantRef) q.getSingleResult();
			return etudiant;
		}
		catch(NoResultException nre){
			logger.warn(nre);
			return null;
		}
		catch(NonUniqueResultException nure){
			logger.error("Doublon étudiant pour l'INE "+ ine + " - Exception : ");
			logger.error(nure);
			throw new NonUniqueResultException();
		}
		catch(Exception e){
			logger.error(e);
			return null;
		}
	}

	@Override
	public IndOpi getPresenceEtudiantOPiByIneAndAnnee(String ine, Integer currentAnnee) {
		if (logger.isDebugEnabled()){
			logger.debug("public EtudiantRef getPresenceEtudiantOPiByIneAndAnnee(String ine, Integer currentAnnee)");
		}
		try{
			Query q = entityManager.createNamedQuery("getPresenceEtudiantOPiByIneAndAnnee");

			String codNneIndOpi = ine.substring(0, ine.length()-1);
			String codCleNneIndOpi = ine.substring(ine.length()-1, ine.length());
			q.setParameter("codNneIndOpi", codNneIndOpi);
			q.setParameter("codCleNneIndOpi", codCleNneIndOpi);
			q.setParameter("annee", currentAnnee);
			IndOpi etudiant = (IndOpi) q.getSingleResult();
			return etudiant;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<PersonnelComposante> getListePersonnelsComposantesBySourceAndAnnee(String source, Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("public List<PersonnelComposante> getListePersonnelsComposantesBySourceAndAnnee(String source, Integer annee)");
			logger.debug("source --> "+source);
			logger.debug("annee --> "+annee);
		}
		try{
			Query q = entityManager.createNamedQuery("getListePersonnelsComposantesBySourceAndAnnee");
			q.setParameter("source", source);
			q.setParameter("annee", annee);
			@SuppressWarnings("unchecked")
			List<PersonnelComposante> ret = (List<PersonnelComposante>) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public OffreDeFormationsDTO getOdfByPK(String rne, Integer annee, String codDip, Integer codVrsVdi, String codEtp, String codVrsVet, String codeCge) {
		if (logger.isDebugEnabled()){
			logger.debug("getOdfByPK(String rne, String annee, String codDip, Integer codVrsVdi, String codEtp, String codVrsVet, String codeCge)");
		}
		try{
			OffreDeFormationPK cleOdf = new OffreDeFormationPK(rne, annee, codDip, codVrsVdi, codEtp, codVrsVet, codeCge);
			OffreDeFormationsDTO odf = entityManager.find(OffreDeFormationsDTO.class, cleOdf);
			return odf;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public boolean getDroitsTransferts(String login, String source, Integer annee) {
		if (logger.isDebugEnabled())
			logger.debug("public boolean getDroitsTransferts(String login, String source, Integer annee) -->"+login +"-----"+source+"-----"+annee);
		try{
			Query q = entityManager.createNamedQuery("getListeComposantesByUidAndSourceAndAnnee");
			q.setParameter("uid", login);
			q.setParameter("source", source);
			q.setParameter("annee", annee);
			@SuppressWarnings("unchecked")
			List<PersonnelComposante> ret = (List<PersonnelComposante>) q.getResultList();
			if(ret.isEmpty())
				return false;
			else
				return true;
		}
		catch(NoResultException e){
			logger.warn(e);
			return false;
		}
	}

	@Override
	public List<IndOpi> getAllIndOpiBySynchroAndSource(Integer currentAnnee, Integer synchro, String source) {
		if (logger.isDebugEnabled())
			logger.debug("public List<IndOpi> getAllIndOpiBySynchroAndSource(Integer currentAnnee, Integer synchro, String source) -->"+currentAnnee +"-----"+synchro+"-----"+source);
		Query q = entityManager.createNamedQuery("allIndOpiBySynchroAndSource");
		q.setParameter("annee", currentAnnee);
		q.setParameter("synchro", synchro);
		q.setParameter("source", source);
		@SuppressWarnings("unchecked")
		List<IndOpi> ret = (List<IndOpi>)q.getResultList();
		return ret;
	}

	@Override
	public List<IndOpi> getAllIndOpiBySynchroAndExcluAndSource(Integer currentAnnee, String source) {
		if (logger.isDebugEnabled())
			logger.debug("public List<IndOpi> getAllIndOpiBySynchroAndExcluAndSource(Integer currentAnnee, String source) -->"+currentAnnee+"-----"+source);
		Query q = entityManager.createNamedQuery("allIndOpiBySynchroAndExcluAndSource");
		q.setParameter("annee", currentAnnee);
		q.setParameter("source", source);
		@SuppressWarnings("unchecked")
		List<IndOpi> ret = (List<IndOpi>)q.getResultList();
		if(ret.isEmpty())
			return null;
		else
			return ret;
	}

	@Override
	public Map<String, Long> getStatistiquesTransfert(String source, Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("public Map<String, String> getStatistiquesTransfert(String source) -->"+source);
		}
		try{
			Query q = entityManager.createNamedQuery("getStatistiquesTransfertDepart");
			q.setParameter("source", source);
			q.setParameter("annee", annee);
			//List<String> ret = (List<String>) q.getResultList();
			Map<String, Long> map = new HashMap<String, Long>();

			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1)
			{
				String rne = (String)resultElement[0];
				Long total = (Long)resultElement[1];
				if (logger.isDebugEnabled()){
					logger.debug("################### rne --> " + rne);
					logger.debug("################### total --> " + total);
				}
				map.put(rne, total);
			}
			return map;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Map<String, Long> getStatistiquesTransfertAccueil(String source, Integer annee) {
		if (logger.isDebugEnabled()){
			logger.debug("public Map<String, Long> getStatistiquesTransfertAccueil(String source, Integer annee) -->"+source);
		}
		try{
			Query q = entityManager.createNamedQuery("getStatistiquesTransfertAccueil");
			q.setParameter("source", source);
			q.setParameter("annee", annee);
			//List<String> ret = (List<String>) q.getResultList();
			Map<String, Long> map = new HashMap<String, Long>();

			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1)
			{
				String rne = (String)resultElement[0];
				Long total = (Long)resultElement[1];
				if (logger.isDebugEnabled()){
					logger.debug("################### rne --> " + rne);
					logger.debug("################### total --> " + total);
				}
				map.put(rne, total);
			}
			return map;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Long getStatistiquesNombreTotalTransfertDepart(Integer currentAnnee) {
		if (logger.isDebugEnabled())
			logger.debug("public Long getStatistiquesNombreTotalTransfertDepart(Integer currentAnnee) -->"+ currentAnnee);

		try{
			Query q = entityManager.createNamedQuery("getStatistiquesNombreTotalTransfertDepart");
			q.setParameter("annee", currentAnnee);
			Long nb = (Long) q.getSingleResult();
			return nb;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Long getStatistiquesNombreTotalTransfertAccueil(Integer currentAnnee) {
		if (logger.isDebugEnabled())
			logger.debug("public Long getStatistiquesNombreTotalTransfertAccueil(Integer currentAnnee) -->"+ currentAnnee);

		try{
			Query q = entityManager.createNamedQuery("getStatistiquesNombreTotalTransfertAccueil");
			q.setParameter("annee", currentAnnee);
			Long nb = (Long) q.getSingleResult();
			return nb;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Long getStatistiquesNombreTotalTransfertOPI(Integer currentAnnee) {
		if (logger.isDebugEnabled())
			logger.debug("public Long getStatistiquesNombreTotalTransfertOPI(Integer currentAnnee) -->"+ currentAnnee);

		try{
			Query q = entityManager.createNamedQuery("getStatistiquesNombreTotalTransfertOPI");
			q.setParameter("annee", currentAnnee);
			Long nb = (Long) q.getSingleResult();
			return nb;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addValidationAutoByComposante(List<Composante> listeComposantes) {
		if (logger.isDebugEnabled())
			logger.debug("public void addValidationAutoByComposante(List<Composante> listeComposantes) --> "+listeComposantes.size());
		if(listeComposantes!=null && !listeComposantes.isEmpty())
			for(Composante c : listeComposantes)
				entityManager.merge(c);
	}

	@Override
	public List<Composante> getListeComposantesFromBddByAnneeAndSource(Integer currentAnnee, String source) {
		if (logger.isDebugEnabled())
			logger.debug("public void getListeComposantesFromBddByAnneeAndSource(Integer currentAnnee, String source)-->"+currentAnnee+"-----"+source);
		try{
			Query q = entityManager.createNamedQuery("getListeComposantesFromBddByAnneeAndSource");
			q.setParameter("annee", currentAnnee);
			q.setParameter("source", source);
			@SuppressWarnings("unchecked")
			List<Composante> ret = (List<Composante>) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Composante getComposantesFromBddByAnneeAndSourceAndCodeComposante(Integer annee, String source, String codeComposante) {
		if (logger.isDebugEnabled()){
			logger.debug("public Composante getComposantesFromBddByAnneeAndSourceAndCodeComposante(Integer annee, String source, String codeComposante)-->"+annee+"-----"+source+"-----"+codeComposante);
		}
		try{
			ComposantePK cleComposantePK = new ComposantePK(annee, codeComposante, source);
			Composante composante = entityManager.find(Composante.class, cleComposantePK);
			return composante;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<CGE> getListeCGEFromBddByAnneeAndSource(Integer currentAnnee,String source) {
		if (logger.isDebugEnabled())
			logger.debug("public List<CGE> getListeCGEFromBddByAnneeAndSource(Integer currentAnnee,String from)-->"+currentAnnee+"-----"+source);
		try{
			Query q = entityManager.createNamedQuery("getListeCGEFromBddByAnneeAndSource");
			q.setParameter("annee", currentAnnee);
			q.setParameter("source", source);
			@SuppressWarnings("unchecked")
			List<CGE> ret = (List<CGE>) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addValidationAutoByCGE(List<CGE> listeCGEMerge) {
		if (logger.isDebugEnabled())
			logger.debug("public void addValidationAutoByCGE(List<CGE> listeCGEMerge) --> "+listeCGEMerge.size());
		if(listeCGEMerge!=null && !listeCGEMerge.isEmpty())
			for(CGE c : listeCGEMerge)
				entityManager.merge(c);
	}

	@Override
	public CGE getCGEFromBddByAnneeAndSourceAndCodeCGE(Integer annee, String source, String codeCGE) {
		if (logger.isDebugEnabled()){
			logger.debug("public CGE getCGEFromBddByAnneeAndSourceAndCodeCGE(Integer currentAnnee, String source, String codeCGE)-->"+annee+"-----"+source+"-----"+codeCGE);
		}
		try{
			CGEPK cleCGEPK = new CGEPK(annee, codeCGE, source);
			CGE cge = entityManager.find(CGE.class, cleCGEPK);
			return cge;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public PersonnelComposante getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante(String login, String source, Integer currentAnnee, String composante)
	{
		if (logger.isDebugEnabled())
			logger.debug("public PersonnelComposante getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante(String login, String source, Integer currentAnnee, String composante)-->"+login+"-----"+source+"-----"+currentAnnee+"-----"+composante);
		try
		{
			PersonnelComposantePK clePersonnelComposante = new PersonnelComposantePK(login, composante, source, currentAnnee);
			PersonnelComposante pc = entityManager.find(PersonnelComposante.class, clePersonnelComposante);
			return pc;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public DatasExterne getDataExterneByIdentifiantAndCode(String identifiant, String code) {
		if (logger.isDebugEnabled()){
			logger.debug("public DatasExterne getDataExterneByIdentifiantAndCode(String identifiant, String code)-->"+identifiant+"-----"+code);
		}
		try{
			DatasExternePK cleDatasExterne = new DatasExternePK(identifiant,code);
			DatasExterne data = entityManager.find(DatasExterne.class, cleDatasExterne);
			return data;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public IndOpi getOpiByNumeroOpi(IndOpi opi) {
		if (logger.isDebugEnabled()){
			logger.debug("public IndOpi getOpiByNumeroOpi(IndOpi opi)-->"+opi);
		}
		try{
			IndOpi currentOpi = entityManager.find(IndOpi.class, opi.getNumeroOpi());
			return currentOpi;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public AccueilAnnee getAccueilAnneeByLibelle(String libelle) {
		if (logger.isDebugEnabled())
			logger.debug("public AccueilAnnee getAccueilAnneeByLibelle(String libelle)-->"+libelle);
		try{
			Query q = entityManager.createNamedQuery("getAccueilAnneeByLibelle");
			q.setParameter("libelle", libelle);
			@SuppressWarnings("unchecked")
			AccueilAnnee ret = (AccueilAnnee) q.getSingleResult();
			return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public AccueilAnnee getAccueilAnneeByIdAccueilAnnee(Integer idAccueilAnnee) {
		if (logger.isDebugEnabled()){
			logger.debug("public AccueilAnnee getAccueilAnneeByIdAccueilAnnee(Integer idAccueilAnnee)-->"+idAccueilAnnee);
		}
		try{
			AccueilAnnee accueilAnnee = entityManager.find(AccueilAnnee.class, idAccueilAnnee);
			return accueilAnnee;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public AccueilResultat getAccueilResultatByIdAccueilResultat(Integer idAccueilResultat) {
		if (logger.isDebugEnabled()){
			logger.debug("public AccueilAnnee getAccueilAnneeByIdAccueilAnnee(Integer idAccueilAnnee)-->"+idAccueilResultat);
		}
		try{
			AccueilResultat accueilResultat = entityManager.find(AccueilResultat.class, idAccueilResultat);
			return accueilResultat;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndAtifOuPas(
			String rne, Integer currentAnnee, String codTypDip,
			Integer codeNiveau, String codeComposante, String source) {
		if (logger.isDebugEnabled())
			logger.debug("public List<OffreDeFormationsDTO> getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndAtifOuPas(String rne, Integer annee, String codTypDip, Integer codeNiveau, String codeComposante, String source)");
		try{
			Query q;
			if("D".equals(source))
				q = entityManager.createNamedQuery("getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndDepartAndAtifOuPas");
			else
				q = entityManager.createNamedQuery("getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndArriveeAndAtifOuPas");
			q.setParameter("annee", currentAnnee);
			q.setParameter("rne", rne);
			q.setParameter("codTypDip", codTypDip);
			q.setParameter("codeNiveau", codeNiveau);
			q.setParameter("codeComposante", codeComposante);
			@SuppressWarnings("unchecked")
			List<OffreDeFormationsDTO> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Parametres updateConfiguration(Parametres param) {
		if (logger.isDebugEnabled()){
			logger.debug("addParametre(Parametres param)");
			logger.debug("param --> "+param);
		}
		Parametres p = entityManager.merge(param);
		return p;
	}

	@Override
	public void deleteSelectedOpi(IndOpi selectedOpiForDelete)
	{
		if (logger.isDebugEnabled())
			logger.debug("public void deleteSelectedOpi(IndOpi selectedOpiForDelete)-->"+ selectedOpiForDelete);
		IndOpi opi = entityManager.find(IndOpi.class, selectedOpiForDelete.getNumeroOpi());
		if (logger.isDebugEnabled())
			logger.debug("opi-->"+ opi);
		entityManager.remove(opi);
	}

	@Override
	public Map<String, String> getOdfComposanteByRneAndAnneeAndCodTypDip(String rne, Integer currentAnnee, String codTypDip) {
		if (logger.isDebugEnabled())
			logger.debug("public Map<String, String> getOdfComposanteByRneAndAnneeAndCodTypDip(String rne, Integer currentAnnee, String codTypDip) -->"+rne+"-----"+currentAnnee+"-----"+codTypDip);
		try{
			Query q = entityManager.createNamedQuery("getOdfComposanteByRneAndAnneeAndCodTypDip");
			q.setParameter("rne", rne);
			q.setParameter("annee", currentAnnee);
			q.setParameter("codTypDip", codTypDip);
			Map<String, String> map = new HashMap<String, String>();
			@SuppressWarnings("unchecked")
			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1)
			{
				String codeComposante = (String)resultElement[0];
				String libComposante = (String)resultElement[1];
				if (logger.isDebugEnabled())
				{
					logger.debug("################### codeComposante --> " + codeComposante);
					logger.debug("################### libComposante --> " + libComposante);
				}
				map.put(codeComposante, libComposante);
			}
			return map;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(String numeroEtudiant, int annee, String source)
	{
		if (logger.isDebugEnabled())
			logger.debug("public EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(String numeroEtudiant, int annee, String source)===>"+numeroEtudiant+"-----"+annee+"-----"+source+"<===");
		try{
			Query q = entityManager.createNamedQuery("getDemandeTransfertByAnneeAndNumeroEtudiantAndSource");
			q.setParameter("numeroEtudiant", numeroEtudiant);
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			EtudiantRef etu = (EtudiantRef) q.getSingleResult();
			return etu;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<TestUnitaireEtudiantRef> getAllTestUnitaireEtudiantRefBySource(String source)
	{
		if (logger.isDebugEnabled()){
			logger.debug("public List<TestUnitaireEtudiantRef> getAllTestUnitaireEtudiantRefBySource(String source) ===>"+source+"<===");
		}
		try{
			Query q = entityManager.createNamedQuery("getAllTestUnitaireEtudiantRefBySource");
			q.setParameter("source", source);
			List<TestUnitaireEtudiantRef> ret = (List<TestUnitaireEtudiantRef>) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<DatasExterne> getAllDatasExterneByNiveau(Integer niveau) {
		if (logger.isDebugEnabled())
			logger.debug("public List<DatasExterne> getAllDatasExterneByNiveau===>"+niveau+"<===");
		try{
			Query q = entityManager.createNamedQuery("getAllDatasExterneByNiveau");
			q.setParameter("niveau", niveau);
			@SuppressWarnings("unchecked")
			List<DatasExterne> ret = q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<IndOpi> getAllIndOpiByAnnee(Integer currentAnnee) {
		if (logger.isDebugEnabled())
			logger.debug("public List<IndOpi> getAllIndOpiByAnnee(Integer annee, String source)===>"+currentAnnee+"<===");
		try{
			Query q = entityManager.createNamedQuery("allIndOpiByAnnee");
			//		Query q = entityManager.createNamedQuery("allIndOpi");
			q.setParameter("annee", currentAnnee);
			@SuppressWarnings("unchecked")
			List<IndOpi> ret = (List<IndOpi>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void deleteOpi(IndOpi opi) {
		if (logger.isDebugEnabled())
			logger.debug("===>public void deleteOpi(IndOpi opi)<===");
		try{
			IndOpi opiADelete = entityManager.find(IndOpi.class, opi.getNumeroOpi());
			entityManager.remove(opiADelete);
		}
		catch(NoResultException e){
			logger.warn(e);
		}
	}

	@Override
	public EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee) {
		if (logger.isDebugEnabled())
			logger.debug("DAO impl - public EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee)===>"+ine+"/"+annee+"<===");
		try{
			Query q = entityManager.createNamedQuery("getDemandeTransfertByAnneeAndNumeroIneAndSource");
			q.setParameter("numeroIne", ine);
			q.setParameter("annee", annee);
			EtudiantRef etu = (EtudiantRef) q.getSingleResult();
			return etu;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee, String source) {
		if (logger.isDebugEnabled())
		    logger.debug("DAO impl - public EtudiantRef getDemandeTransfertByAnneeAndNumeroIneAndSource(String ine, Integer annee)===>"+ine+"/"+annee+"/"+source+"<===");
		try{
			Query q = entityManager.createNamedQuery("getDemandeTransfertByAnneeAndNumeroIneAndSource2");
			q.setParameter("numeroIne", ine);
			q.setParameter("annee", annee);
			q.setParameter("source", source);
			EtudiantRef etu = (EtudiantRef) q.getSingleResult();
			return etu;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public IndOpi getIndOpiByNneAndCleIneAndAnnee(String nne, String cleIne, Integer annee) {
		if (logger.isDebugEnabled())
			logger.debug("public IndOpi getIndOpiByNneAndCleIneAndAnnee(String nne, String cleIne, Integer annee)===>"+nne+", "+cleIne+", "+annee+"<===");
		try{
			Query q = entityManager.createNamedQuery("getIndOpiByNneAndCleIneAndAnnee");
			q.setParameter("nne", nne);
			q.setParameter("cleIne", cleIne);
			q.setParameter("annee", annee);
			IndOpi opi = (IndOpi) q.getSingleResult();
			return opi;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<SituationUniversitaire> getSituationUniversitaireByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer annee) {
		if (logger.isDebugEnabled())
			logger.debug("public List<SituationUniversitaire> getSituationUniversitaireByNumeroEtudiantAndAnnee(String numeroEtudiant, Integer annee)===>"+numeroEtudiant+"-----"+annee+"<===");
		try{
			Query q = entityManager.createNamedQuery("getSituationUniversitaireByNumeroEtudiantAndAnnee");
			q.setParameter("numeroEtudiant", numeroEtudiant);
			q.setParameter("annee", annee);
			@SuppressWarnings("unchecked")
			List<SituationUniversitaire> ret = (List<SituationUniversitaire>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addFermeture(Fermeture myFermeture) {
		if (logger.isDebugEnabled()){
			logger.debug("===>public void addFermeture(Fermeture myFermeture) {<===");
			logger.debug("Fermeture===>"+myFermeture+"<===");
		}
		entityManager.merge(myFermeture);
	}

	@Override
	public List<Fermeture> getListeFermeturesBySourceAndAnnee(String source, int annee) {
		if (logger.isDebugEnabled()){
			logger.debug("===>public List<Fermeture> getListeFermeturesBySourceAndAnnee(String source, int annee) {<===");
			logger.debug("source===>"+source+"-----annee===>"+annee+"<===");
		}
		try{
			Query q = entityManager.createNamedQuery("getListeFermeturesBySourceAndAnnee");
			q.setParameter("source", source);
			q.setParameter("annee", annee);
			@SuppressWarnings("unchecked")
			List<Fermeture> ret = (List<Fermeture>)q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<Fermeture> addPeriodeFermetures(List<Fermeture> lFermetures) {
		if (logger.isDebugEnabled()){
			logger.debug("===>public void addFermeture(Fermeture myFermeture) {<===");
		}
		for(Fermeture f : lFermetures ){
			entityManager.merge(f);
		}
		return this.getListeFermeturesBySourceAndAnnee("D", 2016);
	}

	@Override
	public void deletePeriodeFermeture(String id) {
		if (logger.isDebugEnabled())
			logger.debug("===>public List<Fermeture> deletePeriodeFermeture(Fermeture periodeFermetureASupprimer)<===");
		try
		{
			Fermeture myFermeture = entityManager.find(Fermeture.class, id);
			if (logger.isDebugEnabled())
				logger.debug("myFermeture===>"+myFermeture+"<===");
			entityManager.remove(myFermeture);
		}
		catch(NoResultException e){
			logger.warn(e);
		}
	}

	@Override
	public Fermeture getFermetureFromId(String id) {
		if (logger.isDebugEnabled())
			logger.debug("===>public Fermeture getFermetureFromId(String id) {<===");
		try
		{
			Fermeture myFermeture = entityManager.find(Fermeture.class, id);
			if (logger.isDebugEnabled())
				logger.debug("myFermeture===>"+myFermeture+"<===");
			return myFermeture;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance(String numeroEtudiant, Integer currentAnnee, String source) {
		if (logger.isDebugEnabled())
			logger.debug("public EtudiantRef getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance(String numeroEtudiant, int annee, String source)===>"+numeroEtudiant+"-----"+currentAnnee+"-----"+source+"<===");
		try{
			Query q = entityManager.createNamedQuery("getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance");
			q.setParameter("numeroEtudiant", numeroEtudiant);
			q.setParameter("annee", currentAnnee);
			q.setParameter("source", source);
			EtudiantRef etu = (EtudiantRef) q.getSingleResult();
			return etu;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public Versions getVersionByEtat(Integer etat) {
		if (logger.isDebugEnabled()){
			logger.debug("public Versions getVersionByEtat(Integer etat) ===>"+etat+"<===");
		}
		try{
			Query q = entityManager.createNamedQuery("getVersionByEtat");
			q.setParameter("etat", etat);
			Versions v = (Versions) q.getSingleResult();
			return v;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public WebService updateWebService(WebService currentWs) {
		if (logger.isDebugEnabled()){
			logger.debug("WebService updateWebService(WebService currentWs)");
			logger.debug("currentWs --> "+currentWs);
		}
		WebService ws = entityManager.merge(currentWs);
		return ws;
	}

	@Override
	public WebService getWebServiceByCode(String code) {
		if (logger.isDebugEnabled())
			logger.debug("WebService getWebServiceByCode(String id)===>"+code+"<===");
		try
		{
			WebService ws = entityManager.find(WebService.class, code);
			if (logger.isDebugEnabled())
				logger.debug("myWebService===>"+ws+"<===");
			return ws;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public void addPersonnelComposanteFromImport(List<PersonnelComposante> newLpc) {
		if (logger.isDebugEnabled())
		    logger.debug("public void addPersonnelComposanteFromImport(List<PersonnelComposante> newLpc)===>"+newLpc+"<===");
		for(PersonnelComposante pc : newLpc)
			entityManager.merge(pc);
	}

	@Override
	public Integer addFeedBackFromTransfertAccueilToTransfertDepart(String ine, Integer currentAnnee, String source, Integer temoinRetourTransfertAccueil) {
		if (logger.isDebugEnabled())
		    logger.debug("public void addFeedBackFromTransfertAccueilToTransfertDepart(String ine, Integer currentAnnee, String source)===>"+ine+"---"+currentAnnee+"---"+source+"---"+temoinRetourTransfertAccueil+"<===");
		try{
			Query q = entityManager.createNamedQuery("getDemandeTransfertByAnneeAndNumeroIneAndSource2");
			q.setParameter("numeroIne", ine);
			q.setParameter("annee", currentAnnee);
			q.setParameter("source", source);

			EtudiantRef etu = (EtudiantRef) q.getSingleResult();
			if(etu!=null) {
				etu.getTransferts().setTemoinRetourTransfertAccueil(temoinRetourTransfertAccueil);
				this.addDemandeTransferts(etu);
				return 1;
			}else
			{
				return 2;
			}

		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}

	@Override
	public List<PersonnelComposante> getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante(String source, Integer currentAnnee, String composante) {
		if (logger.isDebugEnabled())
			logger.debug("public List<PersonnelComposante> getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante(String source, Integer currentAnnee, String composante)===>"+source+"---"+currentAnnee+"---"+composante+"<===");
		try{
			Query q = entityManager.createNamedQuery("getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante");
			q.setParameter("source", source);
			q.setParameter("annee", currentAnnee);
			q.setParameter("codeComposante", composante);
			@SuppressWarnings("unchecked")
			List<PersonnelComposante> ret = (List<PersonnelComposante> ) q.getResultList();
			if(ret.isEmpty())
				return null;
			else
				return ret;
		}
		catch(NoResultException e){
			logger.warn(e);
			return null;
		}
	}
}