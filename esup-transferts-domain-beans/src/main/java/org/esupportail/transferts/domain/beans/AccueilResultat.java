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
		    name="getAccueilResultat",
		    query="SELECT accueilResultat FROM AccueilResultat accueilResultat"
		    ),
	@NamedQuery(
			name="getAccueilResultatSansNull",
			query="SELECT accueilResultat FROM AccueilResultat accueilResultat WHERE accueilResultat.idAccueilResultat != 0"
			),		    
    @NamedQuery(
    		name="getAccueilResultatByIdAccueilResultat",
    		query="SELECT accueilResultat FROM AccueilResultat accueilResultat WHERE accueilResultat.idAccueilResultat = :idAccueilResultat"
    )
})
@Table(name = "ACCUEIL_RESULTAT")
public class AccueilResultat implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732897404503181L;

	/**
	 * Identifiant du resultat
	 */
	@Id
	@Column(name = "ID_ACCUEIL_RESULTAT")
	private Integer idAccueilResultat;

	/**
	 * Libelle
	 */
	@Column(name = "LIBELLE")
	private String libelle;		
	
	/**
	 * Bean constructor.
	 */
	public AccueilResultat() {
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

	@Override
	public String toString() {
		return "AccueilResultat [idAccueilResultat=" + idAccueilResultat
				+ ", libelle=" + libelle + "]";
	}

	public Integer getIdAccueilResultat() {
		return idAccueilResultat;
	}

	public void setIdAccueilResultat(Integer idAccueilResultat) {
		this.idAccueilResultat = idAccueilResultat;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}