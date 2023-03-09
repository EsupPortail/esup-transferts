package org.esupportail.transferts.domain.beans;

public class EtudiantRefPK implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String numeroEtudiant;

	private Integer annee;

	public EtudiantRefPK() {}

	public EtudiantRefPK(String numeroEtudiant, Integer annee) {
		this.numeroEtudiant=numeroEtudiant;
		this.annee = annee;
	}

	public boolean equals(Object obj) {
		boolean resultat;

		if (obj == this) {
			resultat = true;
		} else {
			if (!(obj instanceof EtudiantRefPK)) {
				resultat = false;
			} else {
				EtudiantRefPK autre = (EtudiantRefPK) obj;
				if (numeroEtudiant!=autre.numeroEtudiant) {
					resultat = false;
				} else {
					if (annee != autre.annee) {
						resultat = false;
					} else {
						resultat = true;
					}
				}
			}
		}
		return resultat;
	}

	public int hashCode() {
		return (numeroEtudiant + annee).hashCode();
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
}