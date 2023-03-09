/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The class that represent users.
 */
@Entity
@NamedQueries({
	@NamedQuery(
		    name="getEtatsDossier",
		    query="SELECT etatDossier FROM EtatDossier etatDossier"
		    ),
    @NamedQuery(
    name="getEtatDossierByIdEtatDossier",
    query="SELECT etatDossier FROM EtatDossier etatDossier WHERE etatDossier.idEtatDossier = :idEtatDossier"
    )
})
@Table(name = "ETAT_DOSSIER")
public class EtatDossier implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732897444494181L;

	/**
	 * Identifiant de l'état du dossier
	 */
	@Id
	@Column(name = "ID_ETAT_DOSSIER")
	private Integer idEtatDossier;

	/**
	 * Libellé court etat du dossier
	 */
	@Column(name = "LIB_COURT")
	private String libelleCourtEtatDossier;		
	
	/**
	 * Libellé court etat du dossier
	 */
	@Column(name = "LIB_LONG")
	private String libelleLongEtatDossier;			

	/**
	 * Bean constructor.
	 */
	public EtatDossier() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof EtatDossier)) return false;

		EtatDossier that = (EtatDossier) o;

		if (!getIdEtatDossier().equals(that.getIdEtatDossier())) return false;
		if (getLibelleCourtEtatDossier() != null ? !getLibelleCourtEtatDossier().equals(that.getLibelleCourtEtatDossier()) : that.getLibelleCourtEtatDossier() != null)
			return false;
		return getLibelleLongEtatDossier() != null ? getLibelleLongEtatDossier().equals(that.getLibelleLongEtatDossier()) : that.getLibelleLongEtatDossier() == null;

	}

	@Override
	public int hashCode() {
		int result = getIdEtatDossier().hashCode();
		result = 31 * result + (getLibelleCourtEtatDossier() != null ? getLibelleCourtEtatDossier().hashCode() : 0);
		result = 31 * result + (getLibelleLongEtatDossier() != null ? getLibelleLongEtatDossier().hashCode() : 0);
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "EtatDossier#" + hashCode() + "[idEtatDossier=[" + idEtatDossier + "], libelleCourtEtatDossier=["
				+ libelleCourtEtatDossier +"],  libelleLongEtatDossier=["+ libelleLongEtatDossier +"]";
	}

	public void setIdEtatDossier(Integer idEtatDossier) {
		this.idEtatDossier = idEtatDossier;
	}

	public Integer getIdEtatDossier() {
		return idEtatDossier;
	}

	public void setLibelleCourtEtatDossier(String libelleCourtEtatDossier) {
		this.libelleCourtEtatDossier = libelleCourtEtatDossier;
	}

	public String getLibelleCourtEtatDossier() {
		return libelleCourtEtatDossier;
	}

	public void setLibelleLongEtatDossier(String libelleLongEtatDossier) {
		this.libelleLongEtatDossier = libelleLongEtatDossier;
	}

	public String getLibelleLongEtatDossier() {
		return libelleLongEtatDossier;
	}

}