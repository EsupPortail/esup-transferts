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

public class TrEtablissementDTO implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732897404491011L;

	/**
	 * Code établissement
	 */
	private String codeEtb;

	/**
	 * Libellé établissement
	 */
	private String libEtb;			

	/**
	 * Code département
	 */
	private String codeDep;

	/**
	 * Libellé département
	 */
	private String libDep;		

	/**
	 * Libellé Académie
	 */
	private String libAcademie;		
	
//	/**
//	 * Adresse établissement
//	 */
//	private AdresseEtablissement adresseEtablissement;			

	/**
	 * Libellé etablissement
	 */
	private String libOffEtb;		
	
	/**
	 * Libellé adresse 1
	 */
	private String libAd1Etb;		

	/**
	 * Libellé adresse 2
	 */
	private String libAd2Etb;		

	/**
	 * Libellé adresse 3
	 */
	private String libAd3Etb;		

	/**
	 * Code postal adresse établissement
	 */
	private String codPosAdrEtb;		

	/**
	 * Libellé acheminiment
	 */
	private String libAch;			
	
	/**
	 * Bean constructor.
	 */
	public TrEtablissementDTO() {
		super();
	}

	/**
	 * Bean constructor.
	 */
	public TrEtablissementDTO(String codeEtb, String libEtb) {
		super();
		this.codeEtb=codeEtb;
		this.libEtb=libEtb;
	}	

	public TrEtablissementDTO(String codeEtb, String libEtb, String codeDep, String libDep, String libAcademie) {
		super();
		this.codeEtb=codeEtb;
		this.libEtb=libEtb;
		this.codeDep = codeDep;
		this.libDep = libDep;
		this.libAcademie = libAcademie;
	}		

	public TrEtablissementDTO(String codeEtb, String libEtb, String codeDep,
			String libDep, String libAcademie, String libOffEtb,
			String libAd1Etb, String libAd2Etb, String libAd3Etb,
			String codPosAdrEtb, String libAch) {
		super();
		this.codeEtb = codeEtb;
		this.libEtb = libEtb;
		this.codeDep = codeDep;
		this.libDep = libDep;
		this.libAcademie = libAcademie;
		this.libOffEtb = libOffEtb;
		this.libAd1Etb = libAd1Etb;
		this.libAd2Etb = libAd2Etb;
		this.libAd3Etb = libAd3Etb;
		this.codPosAdrEtb = codPosAdrEtb;
		this.libAch = libAch;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TrEtablissementDTO)) return false;

		TrEtablissementDTO that = (TrEtablissementDTO) o;

		if (!getCodeEtb().equals(that.getCodeEtb())) return false;
		if (!getLibEtb().equals(that.getLibEtb())) return false;
		if (!getCodeDep().equals(that.getCodeDep())) return false;
		if (!getLibDep().equals(that.getLibDep())) return false;
		if (!getLibAcademie().equals(that.getLibAcademie())) return false;
		if (!getLibOffEtb().equals(that.getLibOffEtb())) return false;
		if (!getLibAd1Etb().equals(that.getLibAd1Etb())) return false;
		if (!getLibAd2Etb().equals(that.getLibAd2Etb())) return false;
		if (!getLibAd3Etb().equals(that.getLibAd3Etb())) return false;
		if (!getCodPosAdrEtb().equals(that.getCodPosAdrEtb())) return false;
		return getLibAch().equals(that.getLibAch());

	}

	@Override
	public int hashCode() {
		int result = getCodeEtb().hashCode();
		result = 31 * result + getLibEtb().hashCode();
		result = 31 * result + getCodeDep().hashCode();
		result = 31 * result + getLibDep().hashCode();
		result = 31 * result + getLibAcademie().hashCode();
		result = 31 * result + getLibOffEtb().hashCode();
		result = 31 * result + getLibAd1Etb().hashCode();
		result = 31 * result + getLibAd2Etb().hashCode();
		result = 31 * result + getLibAd3Etb().hashCode();
		result = 31 * result + getCodPosAdrEtb().hashCode();
		result = 31 * result + getLibAch().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TrEtablissementDTO [codeEtb=" + codeEtb + ", libEtb=" + libEtb
				+ ", codeDep=" + codeDep + ", libDep=" + libDep
				+ ", libAcademie=" + libAcademie + ", libOffEtb=" + libOffEtb
				+ ", libAd1Etb=" + libAd1Etb + ", libAd2Etb=" + libAd2Etb
				+ ", libAd3Etb=" + libAd3Etb + ", codPosAdrEtb=" + codPosAdrEtb
				+ ", libAch=" + libAch + "]";
	}

	public void setCodeEtb(String codeEtb) {
		this.codeEtb = codeEtb;
	}

	public String getCodeEtb() {
		return codeEtb;
	}

	public void setLibEtb(String libEtb) {
		this.libEtb = libEtb;
	}

	public String getLibEtb() {
		return libEtb;
	}

	public void setCodeDep(String codeDep) {
		this.codeDep = codeDep;
	}

	public String getCodeDep() {
		return codeDep;
	}

	public void setLibDep(String libDep) {
		this.libDep = libDep;
	}

	public String getLibDep() {
		return libDep;
	}

	public void setLibAcademie(String libAcademie) {
		this.libAcademie = libAcademie;
	}

	public String getLibAcademie() {
		return libAcademie;
	}

	public String getLibOffEtb() {
		return libOffEtb;
	}

	public void setLibOffEtb(String libOffEtb) {
		this.libOffEtb = libOffEtb;
	}

	public String getLibAd1Etb() {
		return libAd1Etb;
	}

	public void setLibAd1Etb(String libAd1Etb) {
		this.libAd1Etb = libAd1Etb;
	}

	public String getLibAd2Etb() {
		return libAd2Etb;
	}

	public void setLibAd2Etb(String libAd2Etb) {
		this.libAd2Etb = libAd2Etb;
	}

	public String getLibAd3Etb() {
		return libAd3Etb;
	}

	public void setLibAd3Etb(String libAd3Etb) {
		this.libAd3Etb = libAd3Etb;
	}

	public String getCodPosAdrEtb() {
		return codPosAdrEtb;
	}

	public void setCodPosAdrEtb(String codPosAdrEtb) {
		this.codPosAdrEtb = codPosAdrEtb;
	}

	public String getLibAch() {
		return libAch;
	}

	public void setLibAch(String libAch) {
		this.libAch = libAch;
	}

}