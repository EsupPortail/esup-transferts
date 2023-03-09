/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The class that represent users.
 */
public class TrResultatVdiVetDTO implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 1234732897404494181L;


	/**
	 * Session
	 */
	private List<ResultatEtape> etapes;		
		
	/**
	 * Bean constructor.
	 */
	public TrResultatVdiVetDTO() {
		super();
	}		
	
	@Override
	public String toString() {
		return "TrResultatVdiVetDTO [etapes=" + etapes + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TrResultatVdiVetDTO)) return false;

		TrResultatVdiVetDTO that = (TrResultatVdiVetDTO) o;

		return getEtapes() != null ? getEtapes().equals(that.getEtapes()) : that.getEtapes() == null;

	}

	@Override
	public int hashCode() {
		return getEtapes() != null ? getEtapes().hashCode() : 0;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setEtapes(List<ResultatEtape> etapes) {
		this.etapes = etapes;
	}

	public List<ResultatEtape> getEtapes() {
		return etapes;
	}

}