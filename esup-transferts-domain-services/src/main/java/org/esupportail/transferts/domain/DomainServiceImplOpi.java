/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.WebServiceClient;

import org.esupportail.transferts.dao.DaoService;
import org.esupportail.transferts.domain.beans.*;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Farid AIT KARRA (Universite de d'Artois) - 2011
 * 
 */
public class DomainServiceImplOpi implements DomainServiceOpi, InitializingBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 5562208937407153456L;

	/**
	 * For Logging.
	 */
	private static final Logger logger = new LoggerImpl(DomainServiceImplOpi.class);
	private DaoService daoService;

	/**
	 * Constructor.
	 */
	public DomainServiceImplOpi() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// nothing to do yet.
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

	public DaoService getDaoService() {
		return daoService;
	}

//	@Override
//	public void addIndOpi(IndOpi opi) {
//		getDaoService().addIndOpi(opi);
//	}

	@Override
	public List<OffreDeFormationsDTO> getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne(Date maxDate, Integer annee, String rne){
		return getDaoService().getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne(maxDate, annee, rne);
	}

	@Override
	public List<OffreDeFormationsDTO> getFormationsByRneAndAnnee(String rne, Integer annee) {
		return getDaoService().getFormationsByRneAndAnnee(rne, annee);
	}

	@Override
	public void addTransfertOpiToListeTransfertsAccueil(EtudiantRef etu) {
		getDaoService().addDemandeTransferts(etu);
	}

	@Override
	public Parametres getParametreByCode(String codeParametre) {
		return getDaoService().getParametreByCode(codeParametre);
	}

	@Override
	public Integer addFeedBackFromTransfertAccueilToTransfertDepart(String ine, Integer currentAnnee, String source, Integer temoinRetourTransfertAccueil) {
		return getDaoService().addFeedBackFromTransfertAccueilToTransfertDepart(ine, currentAnnee, source, temoinRetourTransfertAccueil);
	}

	@Override
	public Versions getVersionByEtat(Integer etat) {
		return getDaoService().getVersionByEtat(etat);
	}
}
