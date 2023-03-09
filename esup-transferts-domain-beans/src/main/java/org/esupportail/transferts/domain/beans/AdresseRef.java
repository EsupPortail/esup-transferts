package org.esupportail.transferts.domain.beans;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Farid AIT KARRA : farid.aitkarra@univ-artois.fr
 *
 */
@Entity
@IdClass(EtudiantRefPK.class)
//@Table(name="AdresseRef")
@Table(name="ADRESSEREF")
public class AdresseRef implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 123456L;

	@Id
	@Column(name = "numeroEtudiant")
	private String numeroEtudiant;	

	@Id
	@Column(name = "annee", length = 4)
	private Integer annee;		
	
    @Column(name = "codPay", length = 3)
	private String codPay;
	
    @Column(name = "libPay")
	private String libPay;

//    @NotEmpty(message="L'adresse 1 est obligatoire")
	@Column(name = "libAd1", length = 32)    
	private String libAd1;
	
	@Column(name = "libAd2", length = 32)
    private String libAd2;
	
	@Column(name = "libAd3", length = 32)
    private String libAd3;
	
	@Column(name = "codePostal")
    private String codePostal;

	@Column(name = "codeCommune", length = 5)
    private String codeCommune;	
	
	@Transient
    private String nomCommune;
	
	@Column(name = "codeVilleEtranger")
	private String codeVilleEtranger;
	
	@Transient
	private String pays;	

	@Column(name = "numTel", length = 15)
    private String numTel;
	
	@Column(name = "numTelPortable", length = 15)
    private String numTelPortable; 

//    @NotEmpty(message="L'adresse email1 est obligatoire")
//	@Email(message="L'adresse email n'est pas valide")
	@Column(name = "email", length = 200)
    private String email;	
	
	/**
	 * Constructeur
	 */
	public AdresseRef(){
		super();
	}

	@Override
	public String toString() {
		return "AdresseRef [numeroEtudiant=" + numeroEtudiant + ", annee="
				+ annee + ", codPay=" + codPay + ", libPay=" + libPay
				+ ", libAd1=" + libAd1 + ", libAd2=" + libAd2 + ", libAd3="
				+ libAd3 + ", codePostal=" + codePostal + ", codeCommune="
				+ codeCommune + ", nomCommune=" + nomCommune
				+ ", codeVilleEtranger=" + codeVilleEtranger + ", pays=" + pays
				+ ", numTel=" + numTel + ", numTelPortable=" + numTelPortable
				+ ", email=" + email + "]";
	}

	public void setLibAd1(String libAd1) {
		this.libAd1 = libAd1;
	}

	public String getLibAd1() {
		return libAd1;
	}

	public void setLibAd2(String libAd2) {
		this.libAd2 = libAd2;
	}

	public String getLibAd2() {
		return libAd2;
	}

	public void setLibAd3(String libAd3) {
		this.libAd3 = libAd3;
	}

	public String getLibAd3() {
		return libAd3;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}

	public String getNomCommune() {
		return nomCommune;
	}

	public void setCodPay(String codPay) {
		this.codPay = codPay;
	}

	public String getCodPay() {
		return codPay;
	}

	public void setLibPay(String libPay) {
		this.libPay = libPay;
	}

	public String getLibPay() {
		return libPay;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTelPortable(String numTelPortable) {
		this.numTelPortable = numTelPortable;
	}

	public String getNumTelPortable() {
		return numTelPortable;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setCodeVilleEtranger(String codeVilleEtranger) {
		this.codeVilleEtranger = codeVilleEtranger;
	}

	public String getCodeVilleEtranger() {
		return codeVilleEtranger;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getPays() {
		return pays;
	}

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

	public void setCodeCommune(String codeCommune) {
		this.codeCommune = codeCommune;
	}

	public String getCodeCommune() {
		return codeCommune;
	}
}
