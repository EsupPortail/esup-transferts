/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain;

//import gouv.education.apogee.commun.transverse.dto.offreformation.recupererse.DiplomeDTO;
//import gouv.education.apogee.commun.transverse.dto.offreformation.recupererse.DiplomeDTO2;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.esupportail.transferts.domain.beans.CGE;
import org.esupportail.transferts.domain.beans.CentreDeGestion;
import org.esupportail.transferts.domain.beans.Composante;
import org.esupportail.transferts.domain.beans.EtudiantRef;
import org.esupportail.transferts.domain.beans.IdentifiantEtudiant;
import org.esupportail.transferts.domain.beans.IndOpi;
import org.esupportail.transferts.domain.beans.OffreDeFormationsDTO;
import org.esupportail.transferts.domain.beans.PersonnelComposante;
import org.esupportail.transferts.domain.beans.TrBac;
import org.esupportail.transferts.domain.beans.TrCommuneDTO;
import org.esupportail.transferts.domain.beans.TrDepartementDTO;
import org.esupportail.transferts.domain.beans.TrEtablissementDTO;
import org.esupportail.transferts.domain.beans.TrInfosAdmEtu;
import org.esupportail.transferts.domain.beans.TrPaysDTO;
import org.esupportail.transferts.domain.beans.TrResultatVdiVetDTO;

/**
 * @author Farid AIT KARRA (Universite d'Artois) - 2011
 * 
 */
public interface DomainServiceScolarite extends Serializable {
	
	public static int MAX_SESSIONS_RESULTAT_DEPART=15;
	public static int MAX_SESSIONS_RESULTAT_ACCUEIL=9;
	

	public EtudiantRef getCurrentEtudiant(String supannEtuId);

	public EtudiantRef getCurrentEtudiantIne(String ine, Date dateNaissance);

	public List<TrCommuneDTO> getCommunes(String codePostal);

	public List<TrPaysDTO> getListePays();

	public List<TrDepartementDTO> getListeDepartements();

	public List<TrEtablissementDTO> getListeEtablissements(
			String typeEtablissement, String dept);

	public TrEtablissementDTO getEtablissementByRne(String rne);

	public TrEtablissementDTO getEtablissementByDepartement(String dep);

	public TrBac getBaccalaureat(String supannEtuId);

	public TrResultatVdiVetDTO getSessionsResultats(String supannEtuId, String source);

	public IndOpi getInfosOpi(String numeroEtudiant);

	public TrPaysDTO getPaysByCodePays(String codePays);

	public String getComposante(String supannEtuId);

	public List<OffreDeFormationsDTO> getOffreDeFormation(String rne, Integer annee);

	public List<IndOpi> synchroOpi(List<IndOpi> listeSynchroScolarite);
	
	public List<TrBac> recupererBacOuEquWS(String codeBac);
	
	public List<PersonnelComposante> recupererComposante(String uid, String diplayName, String mail, String source, Integer annee);

	public Integer getAuthEtu(String ine, Date dateNaissanceApogee);

	public List<Composante> recupererListeComposantes(Integer annee, String source);

	List<CGE> recupererListeCGE(Integer annee, String source);

	Map<String, String> getEtapePremiereAndCodeCgeAndLibCge(String supannEtuId);

	public IdentifiantEtudiant getIdentifiantEtudiantByIne(String codNneIndOpi, String codCleNneIndOpi);

	TrInfosAdmEtu getInfosAdmEtu(String supannEtuId);

	public List<EtudiantRef> recupererListeEtudiants(String myAnnee, String codeDiplome, String versionDiplome, String codeEtape, String versionEtape);
}
