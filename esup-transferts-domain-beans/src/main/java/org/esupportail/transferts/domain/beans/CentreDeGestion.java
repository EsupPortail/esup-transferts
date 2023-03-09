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
public class CentreDeGestion implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427712397404494181L;

	/**
	 * Code Centre de Gestion
	 */
	private String codCge;

	/**
	 * Libelle Long Centre de Gestion
	 */
	private String libCge;		
	
	/**
	 * Libelle Court Centre de Gestion
	 */
	private String licCge;		
	
	/**
	 * Témoin en service du code Centre de Gestion
	 */
	private String temEnSve;
	
	/**
	 * Bean constructor.
	 */
	public CentreDeGestion() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CentreDeGestion)) return false;

		CentreDeGestion that = (CentreDeGestion) o;

		if (!getCodCge().equals(that.getCodCge())) return false;
		if (!getLibCge().equals(that.getLibCge())) return false;
		if (!getLicCge().equals(that.getLicCge())) return false;
		return getTemEnSve().equals(that.getTemEnSve());

	}

	@Override
	public int hashCode() {
		int result = getCodCge().hashCode();
		result = 31 * result + getLibCge().hashCode();
		result = 31 * result + getLicCge().hashCode();
		result = 31 * result + getTemEnSve().hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "CentreDeGestion [codCge=" + codCge + ", libCge=" + libCge
				+ ", licCge=" + licCge + ", temEnSve=" + temEnSve + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCodCge() {
		return codCge;
	}

	public void setCodCge(String codCge) {
		this.codCge = codCge;
	}

	public String getLibCge() {
		return libCge;
	}

	public void setLibCge(String libCge) {
		this.libCge = libCge;
	}

	public String getLicCge() {
		return licCge;
	}

	public void setLicCge(String licCge) {
		this.licCge = licCge;
	}

	public String getTemEnSve() {
		return temEnSve;
	}

	public void setTemEnSve(String temEnSve) {
		this.temEnSve = temEnSve;
	}


}