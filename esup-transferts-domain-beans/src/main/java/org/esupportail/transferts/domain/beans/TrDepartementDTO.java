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

public class TrDepartementDTO implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732897404491234L;

	/**
	 * Code département
	 */
	private String codeDept;

	/**
	 * Libellé département
	 */
	private String libDept;			

	/**
	 * Bean constructor.
	 */
	public TrDepartementDTO() {
		super();
	}

	/**
	 * Bean constructor.
	 */
	public TrDepartementDTO(String codeDept, String libDept) {
		super();
		this.codeDept=codeDept;
		this.libDept=libDept;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TrDepartementDTO)) return false;

		TrDepartementDTO that = (TrDepartementDTO) o;

		if (!getCodeDept().equals(that.getCodeDept())) return false;
		return getLibDept().equals(that.getLibDept());

	}

	@Override
	public int hashCode() {
		int result = getCodeDept().hashCode();
		result = 31 * result + getLibDept().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "TrDepartementDTO#" + hashCode() + "[codeDept=[" + codeDept + "],  libDept=["+ libDept +"]";
	}

	public void setCodeDept(String codeDept) {
		this.codeDept = codeDept;
	}

	public String getCodeDept() {
		return codeDept;
	}

	public void setLibDept(String libDept) {
		this.libDept = libDept;
	}

	public String getLibDept() {
		return libDept;
	}

}