/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The class that represent users.
 */
@Entity
@Table(name = "ACCUEIL_DECISION")
public class AccueilDecision implements Serializable {

	/**
	 * For serialize.
	 */
	@Transient
	private static final long serialVersionUID = 1234512397404494181L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="DECISION_SEQ")
	@SequenceGenerator(name="DECISION_SEQ", sequenceName="DECISION_SEQ", allocationSize=1)
	private long id;		

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "numeroEtudiant", referencedColumnName = "numeroEtudiant"),
		@JoinColumn(name = "annee", referencedColumnName = "annee") })
	private EtudiantRef etudiant;		

	/**
	 * Date de saisie
	 */
	@Column(name = "DATE_SAISIE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSaisie;

	/**
	 * Auteur de la saisie
	 */
	@Column(name = "AUTEUR", nullable = false)
	private String auteur;	

	/**
	 * Voeux
	 */
	@Column(name = "AVIS", nullable = false, length = 1)
	private String avis;		
	
	/**
	 * Decision
	 */
	@Column(name = "DECISION")
	private String decision;		

	/**
	 * Bean constructor.
	 */
	public AccueilDecision() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AccueilDecision)) return false;

		AccueilDecision that = (AccueilDecision) o;

		if (getId() != that.getId()) return false;
		if (!getEtudiant().equals(that.getEtudiant())) return false;
		if (!getDateSaisie().equals(that.getDateSaisie())) return false;
		if (!getAuteur().equals(that.getAuteur())) return false;
		if (!getAvis().equals(that.getAvis())) return false;
		return getDecision() != null ? getDecision().equals(that.getDecision()) : that.getDecision() == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (getId() ^ (getId() >>> 32));
		result = 31 * result + getEtudiant().hashCode();
		result = 31 * result + getDateSaisie().hashCode();
		result = 31 * result + getAuteur().hashCode();
		result = 31 * result + getAvis().hashCode();
		result = 31 * result + (getDecision() != null ? getDecision().hashCode() : 0);
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getDateSaisie() {
		return dateSaisie;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getAvis() {
		return avis;
	}

	public void setAvis(String avis) {
		this.avis = avis;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EtudiantRef getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(EtudiantRef etudiant) {
		this.etudiant = etudiant;
	}
}