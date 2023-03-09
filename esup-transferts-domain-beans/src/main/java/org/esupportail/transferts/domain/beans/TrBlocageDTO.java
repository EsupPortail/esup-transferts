/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
/**
 * The class that represent users.
 */

public class TrBlocageDTO implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732894044494181L;

	/**
	 * Code du blocage
	 */
	private String codeBlocage;

	/**
	 * Libellé du blocage
	 */
	private String libBlocage;			

	/**
	 * Bean constructor.
	 */
	public TrBlocageDTO() {
		super();
	}

	/**
	 * Bean constructor.
	 */
	public TrBlocageDTO(String codeBlocage, String libBlocage) {
		super();
		this.codeBlocage=codeBlocage;
		this.libBlocage=libBlocage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TrBlocageDTO)) return false;

		TrBlocageDTO that = (TrBlocageDTO) o;

		if (!getCodeBlocage().equals(that.getCodeBlocage())) return false;
		return getLibBlocage().equals(that.getLibBlocage());

	}

	@Override
	public int hashCode() {
		int result = getCodeBlocage().hashCode();
		result = 31 * result + getLibBlocage().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "TrBlocageDTO#" + hashCode() + "[codeBlocage=[" + codeBlocage + "],  libBlocage=["+ libBlocage +"]";
	}

	public void setCodeBlocage(String codeBlocage) {
		this.codeBlocage = codeBlocage;
	}

	public String getCodeBlocage() {
		return codeBlocage;
	}

	public void setLibBlocage(String libBlocage) {
		this.libBlocage = libBlocage;
	}

	public String getLibBlocage() {
		return libBlocage;
	}

}