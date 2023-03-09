package org.esupportail.transferts.domain.beans;

import javax.persistence.Column;

public class FichierPK implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "md5", nullable=false)
	private String md5;

	@Column(name = "annee", length = 4)
	private Integer annee;
	
	@Column(name = "FROM_SOURCE", nullable=false, length=1)
	private String from;

	public FichierPK() {}

	public FichierPK(String md5, Integer annee, String from) {
		super();
		this.md5 = md5;
		this.annee = annee;
		this.from = from;
	}

	public boolean equals(Object obj) {
		boolean resultat;

		if (obj == this) {
			resultat = true;
		} else {
			if (!(obj instanceof FichierPK))
			{
				resultat = false;
			}
			else 
			{
				FichierPK autre = (FichierPK) obj;
				if (md5==autre.md5  
					&& annee==autre.annee 
					&& from==autre.from) 
					resultat = true;
				else 
					resultat = false;
			}
		}
		return resultat;		
	}

	public int hashCode() {
		return (md5 + annee + from).hashCode();
	}

	public void setNumeroEtudiant(String numeroEtudiant) {
		this.md5 = numeroEtudiant;
	}

	public String getNumeroEtudiant() {
		return md5;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public Integer getAnnee() {
		return annee;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}