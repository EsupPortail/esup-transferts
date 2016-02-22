/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.awt.Image;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;


/**
 * The class that represent users.
 */
@Entity
@IdClass(FichierPK.class)
@NamedQueries({
	@NamedQuery(
			name="getFichiersByAnneeAndFrom",
			query="SELECT fichier FROM Fichier fichier WHERE fichier.annee= :annee AND fichier.from=:from AND fichier.md5!='ETABLISSEMENT_PARTENAIRE'"
			),
			@NamedQuery(
					name="getFichierDefautByAnneeAndFrom",
					query="SELECT fichier FROM Fichier fichier WHERE fichier.defaut=1 AND fichier.annee= :annee AND fichier.from=:from AND fichier.md5!='ETABLISSEMENT_PARTENAIRE'"
					),
					@NamedQuery(
							name="getFichiersByIdAndAnneeAndFrom",
							query="SELECT fichier FROM Fichier fichier WHERE fichier.md5= :md5 AND fichier.annee= :annee AND fichier.from=:from AND fichier.md5!='ETABLISSEMENT_PARTENAIRE'"
							),		
							@NamedQuery(
									name="resetDefautFichierByAnneeAndFrom", 
									query="UPDATE Fichier fichier SET fichier.defaut=0 WHERE fichier.annee= :annee AND fichier.from=:from AND fichier.md5!='ETABLISSEMENT_PARTENAIRE'"
									)
})		    	    
//@Table(name = "Fichier")
@Table(name = "FICHIER")
public class Fichier implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732897404494181L;

	/**
	 * MD5 du fichier
	 */
	@Id
	private String md5;		

	@Id
	private Integer annee;		

	@Id
	private String from;		

	/**
	 * Nom du fichier
	 */
	@Column(name = "nom", nullable=false)
	//	@Column(name = "nom")
	private String nom;		

	/**
	 * Nom du signataire
	 */
	@Column(name = "nom_signataire", nullable=false)
	private String nomSignataire;		

	/**
	 * Taille du fichier
	 */
	@Column(name = "taille", nullable=false)
	private long taille;			

	/**
	 * Chemin
	 */
	@Column(name = "chemin")
	private String chemin;		

	/**
	 * Blob images
	 */
	@Lob
	private byte[] img;

	/**
	 * Signature par défaut
	 */
	@Column(name = "defaut")
	private Boolean defaut;		

	/**
	 * Libellé
	 */
	@Column(name = "libelle")
	private String libelle;			

	/**
	 * adresse
	 */
	@Column(name = "adresse")
	private String adresse;		

	/**
	 * boitePostale
	 */
	@Column(name = "boitepostale")
	private String boitePostale;		

	/**
	 * codepostal
	 */
	@Column(name = "codepostal")
	private String codePostal;		

	/**
	 * Bean constructor.
	 */
	public Fichier() {
		super();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return super.hashCode();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString2() {
		return "Fichier#" + hashCode() + "md5=["
				+ md5 +"],  nom=["+ nom +"], from=["+ from +"]";
	}

	@Override
	public String toString() {
		return "Fichier [md5=" + md5 + ", annee=" + annee + ", from=" + from
				+ ", nom=" + nom + ", nomSignataire=" + nomSignataire
				+ ", taille=" + taille + ", chemin=" + chemin + ", img="
				+ Arrays.toString(img) + ", defaut=" + defaut + ", libelle="
				+ libelle + ", adresse=" + adresse + ", boitePostale="
				+ boitePostale + ", codePostal=" + codePostal + "]";
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public long getTaille() {
		return taille;
	}

	public void setTaille(long taille) {
		this.taille = taille;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public void setDefaut(Boolean defaut) {
		this.defaut = defaut;
	}

	public Boolean getDefaut() {
		return defaut;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public byte[] getImg() {
		return img;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setBoitePostale(String boitePostale) {
		this.boitePostale = boitePostale;
	}

	public String getBoitePostale() {
		return boitePostale;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setNomSignataire(String nomSignataire) {
		this.nomSignataire = nomSignataire;
	}

	public String getNomSignataire() {
		return nomSignataire;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}