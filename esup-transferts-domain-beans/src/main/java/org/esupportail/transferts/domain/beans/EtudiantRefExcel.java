package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author Farid AIT KARRA : farid.aitkarra@univ-artois.fr
 *
 */
public class EtudiantRefExcel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 123456789L;

	private String numeroEtudiant;	
	
	private String numeroIne;

	private String nomPatronymique;
	
	private String nomUsuel;

	private String prenom1;
	
	private String dateNaissance;

	private AdresseRef adresse = new AdresseRef();

	private Transferts transferts = new Transferts(); 
	
	private TrEtablissementDTO universiteAccueil;
	
	private TrEtablissementDTO universiteDepart;
	
	private TrBac bac;
	
	private Avis avis;
	
	private TrResultatVdiVetDTO trResultatVdiVetDTO;
	
	private String anneeUniversitaire;
	
	private String dateDeLaDemandeTransfert;
	
	private String etatDuDossier;
	
	private String composante;
	
	private String derniereIaInscription;	
	
	private OffreDeFormationsDTO odf;
	
	private String libelleVET;
	
	private String derniereFormation;
	
	private String codeBac;
	
	private String anneeBac;
	
	private String validation;
	
	private String from_source;
	
	private String decisionDE;
	
	private String dataExterneNiveau2;

	/**
	 * Constructeur
	 */
	public EtudiantRefExcel(){}

	@Override
	public String toString() {
		return "EtudiantRefExcel [numeroEtudiant=" + numeroEtudiant
				+ ", numeroIne=" + numeroIne + ", nomPatronymique="
				+ nomPatronymique + ", nomUsuel=" + nomUsuel + ", prenom1="
				+ prenom1 + ", dateNaissance=" + dateNaissance + ", adresse="
				+ adresse + ", transferts=" + transferts
				+ ", universiteAccueil=" + universiteAccueil
				+ ", universiteDepart=" + universiteDepart + ", bac=" + bac
				+ ", avis=" + avis + ", trResultatVdiVetDTO="
				+ trResultatVdiVetDTO + ", anneeUniversitaire="
				+ anneeUniversitaire + ", dateDeLaDemandeTransfert="
				+ dateDeLaDemandeTransfert + ", etatDuDossier=" + etatDuDossier
				+ ", composante=" + composante + ", derniereIaInscription="
				+ derniereIaInscription + ", odf=" + odf + ", libelleVET="
				+ libelleVET + ", derniereFormation=" + derniereFormation
				+ ", codeBac=" + codeBac + ", anneeBac=" + anneeBac
				+ ", validation=" + validation + ", from_source=" + from_source
				+ ", decisionDE=" + decisionDE + ", dataExterneNiveau2="
				+ dataExterneNiveau2 + "]";
	}

	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}

	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}

	public void setNumeroIne(String numeroIne) {
		this.numeroIne = numeroIne;
	}

	public String getNumeroIne() {
		return numeroIne;
	}

	public void setNomPatronymique(String nomPatronymique) {
		this.nomPatronymique = nomPatronymique;
	}

	public String getNomPatronymique() {
		return nomPatronymique;
	}

	public void setNomUsuel(String nomUsuel) {
		this.nomUsuel = nomUsuel;
	}

	public String getNomUsuel() {
		return nomUsuel;
	}

	public void setPrenom1(String prenom1) {
		this.prenom1 = prenom1;
	}

	public String getPrenom1() {
		return prenom1;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setAdresse(AdresseRef adresse) {
		this.adresse = adresse;
	}

	public AdresseRef getAdresse() {
		return adresse;
	}

	public void setTransferts(Transferts transferts) {
		this.transferts = transferts;
	}

	public Transferts getTransferts() {
		return transferts;
	}

	public void setUniversiteAccueil(TrEtablissementDTO universiteAccueil) {
		this.universiteAccueil = universiteAccueil;
	}

	public TrEtablissementDTO getUniversiteAccueil() {
		return universiteAccueil;
	}

	public void setTrBac(TrBac trBac) {
		this.bac = trBac;
	}

	public TrBac getTrBac() {
		return bac;
	}

	public void setAvis(Avis avis) {
		this.avis = avis;
	}

	public Avis getAvis() {
		return avis;
	}

	public void setAnneeUniversitaire(String anneeUniversitaire) {
		this.anneeUniversitaire = anneeUniversitaire;
	}

	public String getAnneeUniversitaire() {
		return anneeUniversitaire;
	}

	public void setTrResultatVdiVetDTO(TrResultatVdiVetDTO trResultatVdiVetDTO) {
		this.trResultatVdiVetDTO = trResultatVdiVetDTO;
	}

	public TrResultatVdiVetDTO getTrResultatVdiVetDTO() {
		return trResultatVdiVetDTO;
	}

	public void setDateDeLaDemandeTransfert(String dateDeLaDemandeTransfert) {
		this.dateDeLaDemandeTransfert = dateDeLaDemandeTransfert;
	}

	public String getDateDeLaDemandeTransfert() {
		return dateDeLaDemandeTransfert;
	}

	public void setEtatDuDossier(String etatDuDossier) {
		this.etatDuDossier = etatDuDossier;
	}

	public String getEtatDuDossier() {
		return etatDuDossier;
	}

	public void setComposante(String composante) {
		this.composante = composante;
	}

	public String getComposante() {
		return composante;
	}

	public void setDerniereIaInscription(String derniereIaInscription) {
		this.derniereIaInscription = derniereIaInscription;
	}

	public String getDerniereIaInscription() {
		return derniereIaInscription;
	}

	public OffreDeFormationsDTO getOdf() {
		return odf;
	}

	public void setOdf(OffreDeFormationsDTO odf) {
		this.odf = odf;
	}

	public String getLibelleVET() {
		return libelleVET;
	}

	public void setLibelleVET(String libelleVET) {
		this.libelleVET = libelleVET;
	}

	public TrEtablissementDTO getUniversiteDepart() {
		return universiteDepart;
	}

	public void setUniversiteDepart(TrEtablissementDTO universiteDepart) {
		this.universiteDepart = universiteDepart;
	}

	public String getDerniereFormation() {
		return derniereFormation;
	}

	public void setDerniereFormation(String derniereFormation) {
		this.derniereFormation = derniereFormation;
	}

	public String getCodeBac() {
		return codeBac;
	}

	public void setCodeBac(String codeBac) {
		this.codeBac = codeBac;
	}

	public String getAnneeBac() {
		return anneeBac;
	}

	public void setAnneeBac(String anneeBac) {
		this.anneeBac = anneeBac;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public String getFrom_source() {
		return from_source;
	}

	public void setFrom_source(String from_source) {
		this.from_source = from_source;
	}

	public String getDecisionDE() {
		return decisionDE;
	}

	public void setDecisionDE(String decisionDE) {
		this.decisionDE = decisionDE;
	}

	public String getDataExterneNiveau2() {
		return dataExterneNiveau2;
	}

	public void setDataExterneNiveau2(String dataExterneNiveau2) {
		this.dataExterneNiveau2 = dataExterneNiveau2;
	}
}
