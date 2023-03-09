package org.esupportail.transferts.domain.beans;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Farid AIT KARRA : farid.aitkarra@univ-artois.fr
 *
 */
@Entity
@IdClass(EtudiantRefPK.class)
@Table(name="INFOS_ACCUEIL")
public class InfosAccueil implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 123126789L;

	@Id
	@Column(name = "numeroEtudiant")
	private String numeroEtudiant;	

	@Id
	@Column(name = "annee", length = 4)
	private Integer annee;		
	
	/**
	 * Code departement de l'universite de depart
	 */
	@Column(name = "COD_DEP_DEPART", length = 3)
	private String codeDepUnivDepart;	
	
	@Transient
	private String libDepUnivDepart;

	/**
	 * Code rne de l'universite de depart
	 */
	@Column(name = "COD_RNE_UNIV_DEPART", length = 8)
	private String codeRneUnivDepart;		

	@Transient
	private String libRneUnivDepart;	

	/**
	 * Code pays de nationalite
	 */
	@Column(name = "COD_PAY_NAT", length = 3)
	private String codePaysNat;	

	/**
	 * Annee d'obtention du baccalaureat ou de l'equivalence
	 */
	@Column(name = "ANNEE_BAC", length = 4)
	private String anneeBac;		
	
	/**
	 * Code du baccalaureat ou de l'equivalence
	 */
	@Column(name = "COD_BAC", length = 4)
	private String codeBac;

	/**
	 * Source de la demande de transferts accueil
	 * L ==> saisie depuis l'application accueil local
	 * P ==> issue des établissements partenaires
	 * 
	 */
	@Column(name = "FROM_SOURCE")
	private String from_source;	
	
	@Transient
	private String libelleBac;
	
	/**
	 * Validation ou candidature
	 * 0 ==> non specifie
	 * 1 ==> oui
	 * 2 ==> non
	 */
	@Column(name = "VALID_OU_CAND")
	private Integer validationOuCandidature;		
	
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL })
	@JoinColumns({
		@JoinColumn(name = "numeroEtudiant", referencedColumnName = "numeroEtudiant"),
		@JoinColumn(name = "annee", referencedColumnName = "annee") })	
	private List<SituationUniversitaire> situationUniversitaire;	

	/**
	 * Constructeur
	 */
	public InfosAccueil(){
		super();
	}

//	@Override
//	public String toString() {
//		return "InfosAccueil [numeroEtudiant=" + numeroEtudiant + ", annee="
//				+ annee + ", codeDepUnivDepart=" + codeDepUnivDepart
//				+ ", libDepUnivDepart=" + libDepUnivDepart
//				+ ", codeRneUnivDepart=" + codeRneUnivDepart
//				+ ", libRneUnivDepart=" + libRneUnivDepart + ", codePaysNat="
//				+ codePaysNat + ", anneeBac=" + anneeBac + ", codeBac="
//				+ codeBac + ", libelleBac=" + libelleBac
//				+ ", validationOuCandidature=" + validationOuCandidature
//				+ ", situationUniversitaire=" + situationUniversitaire + "]";
//	}

	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}

	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public Integer getAnnee() {
		return annee;
	}

	public String getCodeDepUnivDepart() {
		return codeDepUnivDepart;
	}

	public void setCodeDepUnivDepart(String codeDepUnivDepart) {
		this.codeDepUnivDepart = codeDepUnivDepart;
	}

	public String getCodeRneUnivDepart() {
		return codeRneUnivDepart;
	}

	public void setCodeRneUnivDepart(String codeRneUnivDepart) {
		this.codeRneUnivDepart = codeRneUnivDepart;
	}

	public Integer getValidationOuCandidature() {
		return validationOuCandidature;
	}

	public void setValidationOuCandidature(Integer validationOuCandidature) {
		this.validationOuCandidature = validationOuCandidature;
	}

	public List<SituationUniversitaire> getSituationUniversitaire() {
		return situationUniversitaire;
	}

	public void setSituationUniversitaire(List<SituationUniversitaire> situationUniversitaire) {
		this.situationUniversitaire = situationUniversitaire;
	}

	public String getLibDepUnivDepart() {
		return libDepUnivDepart;
	}

	public void setLibDepUnivDepart(String libDepUnivDepart) {
		this.libDepUnivDepart = libDepUnivDepart;
	}

	public String getLibRneUnivDepart() {
		return libRneUnivDepart;
	}

	public void setLibRneUnivDepart(String libRneUnivDepart) {
		this.libRneUnivDepart = libRneUnivDepart;
	}

	public String getCodePaysNat() {
		return codePaysNat;
	}

	public void setCodePaysNat(String codePaysNat) {
		this.codePaysNat = codePaysNat;
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

	public String getLibelleBac() {
		return libelleBac;
	}

	public void setLibelleBac(String libelleBac) {
		this.libelleBac = libelleBac;
	}

	public String getFrom_source() {
		return from_source;
	}

	public void setFrom_source(String from_source) {
		this.from_source = from_source;
	}
}
