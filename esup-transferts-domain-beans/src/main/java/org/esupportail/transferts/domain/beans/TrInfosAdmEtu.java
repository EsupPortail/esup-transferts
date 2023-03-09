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

public class TrInfosAdmEtu implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 123453214251181L;

	/**
	 * Code Pays de naissance
	 */
	private String codPayNat;
	
	/**
	 * Libellé Pays de naissance
	 */
	private String libPayNat;	
	
	/**
	 * Bean constructor.
	 */
	public TrInfosAdmEtu() {
		super();
	}

	public TrInfosAdmEtu(String codPayNat, String libPayNat) {
		super();
		this.codPayNat = codPayNat;
		this.libPayNat = libPayNat;
	}

	@Override
	public String toString() {
		return "TrInfosAdmEtu [codPayNat=" + codPayNat + ", libPayNat="
				+ libPayNat + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TrInfosAdmEtu)) return false;

		TrInfosAdmEtu that = (TrInfosAdmEtu) o;

		if (!getCodPayNat().equals(that.getCodPayNat())) return false;
		return getLibPayNat().equals(that.getLibPayNat());

	}

	@Override
	public int hashCode() {
		int result = getCodPayNat().hashCode();
		result = 31 * result + getLibPayNat().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCodPayNat() {
		return codPayNat;
	}

	public void setCodPayNat(String codPayNat) {
		this.codPayNat = codPayNat;
	}

	public String getLibPayNat() {
		return libPayNat;
	}

	public void setLibPayNat(String libPayNat) {
		this.libPayNat = libPayNat;
	}
}