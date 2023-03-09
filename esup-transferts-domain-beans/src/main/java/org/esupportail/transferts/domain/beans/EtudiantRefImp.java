package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author Farid AIT KARRA : farid.aitkarra@univ-artois.fr
 *
 */
@XmlRootElement(name="EtudiantRefImp")
public class EtudiantRefImp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 123456789L;

	private String numeroEtudiant;	
	
	private String numeroIne;

	private String nomPatronymique;
	
	private String nomUsuel;

	private String prenom1;
	
	private String prenom2;
	
	private Date dateNaissance;

    private String libNationalite;
    
	private AdresseRef adresse = new AdresseRef();

	private Transferts transferts = new Transferts(); 
	
	private TrEtablissementDTO universiteDepart;
	
	private TrEtablissementDTO universiteAccueil;
	
	private TrBac bac;
	
	private TrResultatVdiVetDTO trResultatVdiVetDTO;
	
	private Avis avis;
	
	private Date dateDuJour;
	
	private String anneeUniversitaire;
	
	private List<TrSituationUniversitaire> situationUniversitaire;		

	private AccueilDecision accueilDecision;
	
	private String codeDecision;
	
	private String decision;
	
	private boolean partenaire;	
	
	/**
	 * Constructeur
	 */
	
	public EtudiantRefImp(){}
	
	public EtudiantRefImp(String numeroEtudiant,
						  String numeroIne,
						  String nomPatronymique,
						  String nomUsuel,
						  String prenom1,
						  String prenom2,
						  Date dateNaissance,
						  String libNationalite,
						  AdresseRef adresse,
						  Transferts transferts,
						  TrEtablissementDTO universiteDepart,
						  TrEtablissementDTO universiteAccueil,
						  TrBac bac,
						  TrResultatVdiVetDTO trResultatVdiVetDTO)
	{
		this.numeroEtudiant = numeroEtudiant;
		this.numeroIne = numeroIne;
		this.nomPatronymique = nomPatronymique;
		this.nomUsuel = nomUsuel;
		this.prenom1 = prenom1;
		this.prenom2 = prenom2;
		this.dateNaissance = dateNaissance;
		this.libNationalite = libNationalite;
		this.adresse = adresse;
		this.transferts = transferts;
		this.universiteDepart=universiteDepart;
		this.universiteAccueil=universiteAccueil;
		this.bac = bac;
		this.trResultatVdiVetDTO=trResultatVdiVetDTO;
	}

	@Override
	public String toString() {
		return "EtudiantRefImp [numeroEtudiant=" + numeroEtudiant
				+ ", numeroIne=" + numeroIne + ", nomPatronymique="
				+ nomPatronymique + ", nomUsuel=" + nomUsuel + ", prenom1="
				+ prenom1 + ", prenom2=" + prenom2 + ", dateNaissance="
				+ dateNaissance + ", libNationalite=" + libNationalite
				+ ", adresse=" + adresse + ", transferts=" + transferts
				+ ", universiteDepart=" + universiteDepart
				+ ", universiteAccueil=" + universiteAccueil + ", bac=" + bac
				+ ", trResultatVdiVetDTO=" + trResultatVdiVetDTO + ", avis="
				+ avis + ", dateDuJour=" + dateDuJour + ", anneeUniversitaire="
				+ anneeUniversitaire + ", situationUniversitaire="
				+ situationUniversitaire + ", accueilDecision="
				+ accueilDecision + ", codeDecision=" + codeDecision
				+ ", decision=" + decision + ", partenaire=" + partenaire + "]";
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

	public void setPrenom2(String prenom2) {
		this.prenom2 = prenom2;
	}

	public String getPrenom2() {
		return prenom2;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Date getDateNaissance() {
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

	public void setLibNationalite(String libNationalite) {
		this.libNationalite = libNationalite;
	}

	public String getLibNationalite() {
		return libNationalite;
	}

	public void setTrEtablissementDTO(TrEtablissementDTO trEtablissementDTO) {
		this.universiteDepart = trEtablissementDTO;
	}

	public TrEtablissementDTO getTrEtablissementDTO() {
		return universiteDepart;
	}

	public void setUniversiteDepart(TrEtablissementDTO universiteDepart) {
		this.universiteDepart = universiteDepart;
	}

	public TrEtablissementDTO getUniversiteDepart() {
		return universiteDepart;
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

	public void setTrResultatVdiVetDTO(TrResultatVdiVetDTO trResultatVdiVetDTO) {
		this.trResultatVdiVetDTO = trResultatVdiVetDTO;
	}

	public TrResultatVdiVetDTO getTrResultatVdiVetDTO() {
		return trResultatVdiVetDTO;
	}

	public void setAvis(Avis avis) {
		this.avis = avis;
	}

	public Avis getAvis() {
		return avis;
	}

	public void setDateDuJour(Date dateDuJour) {
		this.dateDuJour = dateDuJour;
	}

	public Date getDateDuJour() {
		return dateDuJour;
	}

	public void setAnneeUniversitaire(String anneeUniversitaire) {
		this.anneeUniversitaire = anneeUniversitaire;
	}

	public String getAnneeUniversitaire() {
		return anneeUniversitaire;
	}

	public List<TrSituationUniversitaire> getSituationUniversitaire() {
		return situationUniversitaire;
	}

	public void setSituationUniversitaire(List<TrSituationUniversitaire> situationUniversitaire) {
		this.situationUniversitaire = situationUniversitaire;
	}

	public AccueilDecision getAccueilDecision() {
		return accueilDecision;
	}

	public void setAccueilDecision(AccueilDecision accueilDecision) {
		this.accueilDecision = accueilDecision;
	}

	public String getCodeDecision() {
		return codeDecision;
	}

	public void setCodeDecision(String codeDecision) {
		this.codeDecision = codeDecision;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public boolean isPartenaire() {
		return partenaire;
	}

	public void setPartenaire(boolean partenaire) {
		this.partenaire = partenaire;
	}
}
