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
public class ResultatEtape implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427745678404494181L;

	/**
	 * Année d'étape
	 */
	private String annee;
	
	/**
	 * Libellé d'étape
	 */
	private String libEtape;
	
	/**
	 * Liste de session 1 et 2
	 */
	private List<ResultatSession> session;
	
	/**
	 * Bean constructor.
	 */
	public ResultatEtape() {
		super();
	}

	/**
	 * Bean constructor.
	 */
	public ResultatEtape(String annee, String etape, List<ResultatSession> sessions) {
		super();
		this.annee=annee;
		this.libEtape=etape;
		this.session=sessions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ResultatEtape)) return false;

		ResultatEtape that = (ResultatEtape) o;

		if (!getAnnee().equals(that.getAnnee())) return false;
		if (!getLibEtape().equals(that.getLibEtape())) return false;
		return getSession() != null ? getSession().equals(that.getSession()) : that.getSession() == null;

	}

	@Override
	public int hashCode() {
		int result = getAnnee().hashCode();
		result = 31 * result + getLibEtape().hashCode();
		result = 31 * result + (getSession() != null ? getSession().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ResultatEtape [annee=" + annee + ", libEtape=" + libEtape
				+ ", session=" + session + "]";
	}

	public void setSession(List<ResultatSession> session) {
		this.session = session;
	}

	public List<ResultatSession> getSession() {
		return session;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public String getAnnee() {
		return annee;
	}

	public String getLibEtape() {
		return libEtape;
	}

	public void setLibEtape(String libEtape) {
		this.libEtape = libEtape;
	}
}