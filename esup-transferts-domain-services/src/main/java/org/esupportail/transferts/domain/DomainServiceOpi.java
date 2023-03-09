/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.esupportail.transferts.domain.beans.*;

/**
 * @author Farid AIT KARRA (Universite d'Artois) - 2011
 * 
 */
@WebService
public interface DomainServiceOpi extends Serializable {
	
	public List<OffreDeFormationsDTO> getFormationsByRneAndAnnee(String rne, Integer annee);
	
	public List<OffreDeFormationsDTO> getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne(Date maxDate, Integer annee, String rne);
	
	public void addTransfertOpiToListeTransfertsAccueil(EtudiantRef etu);

	public Integer addFeedBackFromTransfertAccueilToTransfertDepart(String ine, Integer currentAnnee, String source, Integer temoinRetourTransfertAccueil);

	public Parametres getParametreByCode(String codeParametre);

	public Versions getVersionByEtat(Integer etat);
}
