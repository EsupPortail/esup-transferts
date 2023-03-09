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

public class TrPaysDTO implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427733497404494181L;

	/**
	 * Code Pays
	 */
	private String codePay;

	/**
	 * Libellee du pays
	 */
	private String libPay;			

	/**
	 * Libellee de la nationalite
	 */
	private String libNationalite;				
	
	/**
	 * Bean constructor.
	 */
	public TrPaysDTO() {
		super();
	}

	/**
	 * Bean constructor.
	 */
	public TrPaysDTO(String codePay, String libPay) {
		super();
		this.codePay=codePay;
		this.libPay=libPay;
	}	
	
	/**
	 * Bean constructor.
	 */
	public TrPaysDTO(String codePay, String libPay, String libNationalite) {
		super();
		this.codePay=codePay;
		this.libPay=libPay;
		this.libNationalite=libNationalite;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TrPaysDTO)) return false;

		TrPaysDTO trPaysDTO = (TrPaysDTO) o;

		if (!getCodePay().equals(trPaysDTO.getCodePay())) return false;
		if (!getLibPay().equals(trPaysDTO.getLibPay())) return false;
		return getLibNationalite().equals(trPaysDTO.getLibNationalite());

	}

	@Override
	public int hashCode() {
		int result = getCodePay().hashCode();
		result = 31 * result + getLibPay().hashCode();
		result = 31 * result + getLibNationalite().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TrPaysDTO [codePay=" + codePay + ", libPay=" + libPay
				+ ", libNationalite=" + libNationalite + "]";
	}

	public void setCodePay(String codePay) {
		this.codePay = codePay;
	}

	public String getCodePay() {
		return codePay;
	}

	public void setLibPay(String libPay) {
		this.libPay = libPay;
	}

	public String getLibPay() {
		return libPay;
	}

	public String getLibNationalite() {
		return libNationalite;
	}

	public void setLibNationalite(String libNationalite) {
		this.libNationalite = libNationalite;
	}

}