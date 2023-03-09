package org.esupportail.transferts.domain.beans;

import javax.persistence.Column;

public class WsPubPK implements java.io.Serializable {

	private static final long serialVersionUID = 7427732123404494181L;

	@Column(name = "rne", nullable=false, length=8)
	private String rne;

	@Column(name = "annee", length = 4)
	private Integer annee;

	public WsPubPK() {}

	public WsPubPK(String rne, Integer annee) {
		this.rne=rne;
		this.annee = annee;
	}

	public boolean equals(Object obj) {
		boolean resultat;

		if (obj == this) {
			resultat = true;
		} else {
			if (!(obj instanceof WsPubPK)) {
				resultat = false;
			} else {
				WsPubPK autre = (WsPubPK) obj;
				if (rne!=autre.rne) {
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
		return (rne + annee).hashCode();
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setCodAnu(Integer annee) {
		this.annee = annee;
	}

	public void setIdAnneeEtude(String idAnneeEtude) {
		this.rne = idAnneeEtude;
	}

	public String getIdAnneeEtude() {
		return rne;
	}



}