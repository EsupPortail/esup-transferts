/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;

import org.esupportail.transferts.domain.beans.IdentifiantEtudiant;

/**
 * The class that represent users.
 */
public class IdentifiantEtudiant implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427712345404494181L;

	private Integer codEtu;

	private Integer codInd;			

	private String numeroIne;

	/**
	 * Bean constructor.
	 */
	public IdentifiantEtudiant() {
		super();
	}
	
	public IdentifiantEtudiant(Integer codEtu, Integer codInd, String numeroIne) {
		super();
		this.codEtu = codEtu;
		this.codInd = codInd;
		this.numeroIne = numeroIne;
	}

	@Override
	public String toString() {
		return "IdentifiantEtudiant [codEtu=" + codEtu + ", codInd=" + codInd
				+ ", numeroIne=" + numeroIne + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof IdentifiantEtudiant)) return false;

		IdentifiantEtudiant that = (IdentifiantEtudiant) o;

		if (getCodEtu() != null ? !getCodEtu().equals(that.getCodEtu()) : that.getCodEtu() != null) return false;
		if (getCodInd() != null ? !getCodInd().equals(that.getCodInd()) : that.getCodInd() != null) return false;
		return getNumeroIne() != null ? getNumeroIne().equals(that.getNumeroIne()) : that.getNumeroIne() == null;

	}

	@Override
	public int hashCode() {
		int result = getCodEtu() != null ? getCodEtu().hashCode() : 0;
		result = 31 * result + (getCodInd() != null ? getCodInd().hashCode() : 0);
		result = 31 * result + (getNumeroIne() != null ? getNumeroIne().hashCode() : 0);
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getCodEtu() {
		return codEtu;
	}

	public void setCodEtu(Integer codEtu) {
		this.codEtu = codEtu;
	}

	public Integer getCodInd() {
		return codInd;
	}

	public void setCodInd(Integer codInd) {
		this.codInd = codInd;
	}

	public String getNumeroIne() {
		return numeroIne;
	}

	public void setNumeroIne(String numeroIne) {
		this.numeroIne = numeroIne;
	}


}