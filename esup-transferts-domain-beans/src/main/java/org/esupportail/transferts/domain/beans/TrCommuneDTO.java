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

public class TrCommuneDTO implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 2427732897404494181L;

	/**
	 * Code la commune
	 */
	private String codeCommune;

	/**
	 * Libellé de la commune
	 */
	private String libCommune;			

	/**
	 * Bean constructor.
	 */
	public TrCommuneDTO() {
		super();
	}

	/**
	 * Bean constructor.
	 */
	public TrCommuneDTO(String codeCommune, String libCommune) {
		super();
		this.codeCommune=codeCommune;
		this.libCommune=libCommune;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TrCommuneDTO)) return false;

		TrCommuneDTO that = (TrCommuneDTO) o;

		if (!getCodeCommune().equals(that.getCodeCommune())) return false;
		return getLibCommune().equals(that.getLibCommune());

	}

	@Override
	public int hashCode() {
		int result = getCodeCommune().hashCode();
		result = 31 * result + getLibCommune().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "TrCommuneDTO#" + hashCode() + "[codeCommune=[" + codeCommune + "],  libCommune=["+ libCommune +"]";
	}

	public void setLibCommune(String libCommune) {
		this.libCommune = libCommune;
	}

	public String getLibCommune() {
		return libCommune;
	}

	public void setCodeCommune(String codeCommune) {
		this.codeCommune = codeCommune;
	}

	public String getCodeCommune() {
		return codeCommune;
	}

}