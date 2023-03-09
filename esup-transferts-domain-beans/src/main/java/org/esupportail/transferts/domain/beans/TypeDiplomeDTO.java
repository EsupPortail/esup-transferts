/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;

public class TypeDiplomeDTO implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732666404494181L;

	/**
	 * Code type diplome
	 */
	private String codeTypeDiplome;		

	/**
	 * Libellé du type de diplome
	 */
	private String libelleTypeDiplome;		

	/**
	 * Bean constructor.
	 */
	public TypeDiplomeDTO() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (!(o instanceof TypeDiplomeDTO)) {return false;}
		TypeDiplomeDTO that = (TypeDiplomeDTO) o;
		if (!getCodeTypeDiplome().equals(that.getCodeTypeDiplome())) {return false;}
		return getLibelleTypeDiplome().equals(that.getLibelleTypeDiplome());
	}

	@Override
	public int hashCode() {
		int result = getCodeTypeDiplome().hashCode();
		result = 31 * result + getLibelleTypeDiplome().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TypeDiplomeDTO [codeTypeDiplome=" + codeTypeDiplome
				+ ", libelleTypeDiplome=" + libelleTypeDiplome + "]";
	}

	public void setLibelleTypeDiplome(String libelleTypeDiplome) {
		this.libelleTypeDiplome = libelleTypeDiplome;
	}

	public String getLibelleTypeDiplome() {
		return libelleTypeDiplome;
	}

	public void setCodeTypeDiplome(String codeTypeDiplome) {
		this.codeTypeDiplome = codeTypeDiplome;
	}

	public String getCodeTypeDiplome() {
		return codeTypeDiplome;
	}
}