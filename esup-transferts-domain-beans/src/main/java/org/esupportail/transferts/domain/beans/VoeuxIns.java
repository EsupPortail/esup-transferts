/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The class that represent users.
 */
@Entity
@Table(name = "VOEUX_INS")
public class VoeuxIns implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 745552897404494181L;

	/**
	 * Numero de l'OPI
	 */
	@Id
	@Column(name = "COD_OPI_INT_EPO", nullable = false, length = 10)
	private String numeroOpi;

	/**
	 * Nom patronymique de l'étudiant
	 */
	@Column(name = "LIB_NOM_PAT_IND_OPI", nullable = false, length = 30)
	private String libNomPatIndOpi;

	/**
	 * Premier prénom de l'étudiant
	 */
	@Column(name = "LIB_PR1_IND_OPI", nullable = false, length = 20)
	private String libPr1IndOpi;

	/**
	 * Code du centre de gestion
	 */
	@Column(name = "COD_CGE", nullable = false, length = 9)
	private String codCge;

	/**
	 * Libelle de l'etape (LIB_VET)
	 */
	@Transient
	private String libEtp;

	/**
	 * Code etape de la version d'étape du voeu
	 */
	@Column(name = "COD_ETP", nullable = false, length = 6)
	private String codEtp;

	/**
	 * Code de la version d'étape du voeu
	 */
	@Column(name = "COD_VRS_VET", nullable = false, length = 3)
	private String codVrsVet;

	/**
	 * Code du diplome du voeu
	 */
	@Column(name = "COD_DIP", length = 7)
	private String codDip;

	/**
	 * Code de la version du diplome du voeu
	 */
	@Column(name = "COD_VRS_VDI", length = 3)
	private Integer codVrsVdi;

	/**
	 * Code de la composante
	 */
	@Column(name = "COD_CMP", length = 9)
	private String codCmp;

	/**
	 * Code du type de la demande d'inscription
	 */
	@Column(name = "COD_DEM_DOS", nullable = false, length = 1)
	private String codDemDos;

	/**
	 * Rang du voeu (par ordre de preference)
	 */
	@Column(name = "NUM_CLS", nullable = false, length = 2)
	private String numCls;

	/**
	 * Libelle de la version d'etape LOCAL (LIB_VRS_ETP)
	 */
	@Column(name = "LIB_VRS_ETP")
	private String libelleVersionEtape;

	/**
	 * Code précisant la décision de la commission
	 */
	@Column(name = "COD_DEC_VEU")
	private String codDecVeu;	
	
	/**
	 * Bean constructor.
	 */
	public VoeuxIns() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof VoeuxIns)) return false;

		VoeuxIns voeuxIns = (VoeuxIns) o;

		if (!getNumeroOpi().equals(voeuxIns.getNumeroOpi())) return false;
		if (!getLibNomPatIndOpi().equals(voeuxIns.getLibNomPatIndOpi())) return false;
		if (!getLibPr1IndOpi().equals(voeuxIns.getLibPr1IndOpi())) return false;
		if (!getCodCge().equals(voeuxIns.getCodCge())) return false;
		if (getLibEtp() != null ? !getLibEtp().equals(voeuxIns.getLibEtp()) : voeuxIns.getLibEtp() != null)
			return false;
		if (!getCodEtp().equals(voeuxIns.getCodEtp())) return false;
		if (!getCodVrsVet().equals(voeuxIns.getCodVrsVet())) return false;
		if (getCodDip() != null ? !getCodDip().equals(voeuxIns.getCodDip()) : voeuxIns.getCodDip() != null)
			return false;
		if (getCodVrsVdi() != null ? !getCodVrsVdi().equals(voeuxIns.getCodVrsVdi()) : voeuxIns.getCodVrsVdi() != null)
			return false;
		if (getCodCmp() != null ? !getCodCmp().equals(voeuxIns.getCodCmp()) : voeuxIns.getCodCmp() != null)
			return false;
		if (!getCodDemDos().equals(voeuxIns.getCodDemDos())) return false;
		if (!getNumCls().equals(voeuxIns.getNumCls())) return false;
		if (getLibelleVersionEtape() != null ? !getLibelleVersionEtape().equals(voeuxIns.getLibelleVersionEtape()) : voeuxIns.getLibelleVersionEtape() != null)
			return false;
		return getCodDecVeu() != null ? getCodDecVeu().equals(voeuxIns.getCodDecVeu()) : voeuxIns.getCodDecVeu() == null;

	}

	@Override
	public int hashCode() {
		int result = getNumeroOpi().hashCode();
		result = 31 * result + getLibNomPatIndOpi().hashCode();
		result = 31 * result + getLibPr1IndOpi().hashCode();
		result = 31 * result + getCodCge().hashCode();
		result = 31 * result + (getLibEtp() != null ? getLibEtp().hashCode() : 0);
		result = 31 * result + getCodEtp().hashCode();
		result = 31 * result + getCodVrsVet().hashCode();
		result = 31 * result + (getCodDip() != null ? getCodDip().hashCode() : 0);
		result = 31 * result + (getCodVrsVdi() != null ? getCodVrsVdi().hashCode() : 0);
		result = 31 * result + (getCodCmp() != null ? getCodCmp().hashCode() : 0);
		result = 31 * result + getCodDemDos().hashCode();
		result = 31 * result + getNumCls().hashCode();
		result = 31 * result + (getLibelleVersionEtape() != null ? getLibelleVersionEtape().hashCode() : 0);
		result = 31 * result + (getCodDecVeu() != null ? getCodDecVeu().hashCode() : 0);
		return result;
	}

	public String getNumeroOpi() {
		return numeroOpi;
	}

	public void setNumeroOpi(String numeroOpi) {
		this.numeroOpi = numeroOpi;
	}

	@Override
	public String toString() {
		return "VoeuxIns [numeroOpi=" + numeroOpi + ", libNomPatIndOpi="
				+ libNomPatIndOpi + ", libPr1IndOpi=" + libPr1IndOpi
				+ ", codCge=" + codCge + ", libEtp=" + libEtp + ", codEtp="
				+ codEtp + ", codVrsVet=" + codVrsVet + ", codDip=" + codDip
				+ ", codVrsVdi=" + codVrsVdi + ", codCmp=" + codCmp
				+ ", codDemDos=" + codDemDos + ", numCls=" + numCls
				+ ", libelleVersionEtape=" + libelleVersionEtape
				+ ", codDecVeu=" + codDecVeu + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLibNomPatIndOpi(String libNomPatIndOpi) {
		this.libNomPatIndOpi = libNomPatIndOpi;
	}

	public String getLibNomPatIndOpi() {
		return libNomPatIndOpi;
	}

	public void setLibPr1IndOpi(String libPr1IndOpi) {
		this.libPr1IndOpi = libPr1IndOpi;
	}

	public String getLibPr1IndOpi() {
		return libPr1IndOpi;
	}

	public void setCodCge(String codCge) {
		this.codCge = codCge;
	}

	public String getCodCge() {
		return codCge;
	}

	public void setCodEtp(String codEtp) {
		this.codEtp = codEtp;
	}

	public String getCodEtp() {
		return codEtp;
	}

	public void setCodVrsVet(String codVrsVet) {
		this.codVrsVet = codVrsVet;
	}

	public String getCodVrsVet() {
		return codVrsVet;
	}

	public void setCodDip(String codDip) {
		this.codDip = codDip;
	}

	public String getCodDip() {
		return codDip;
	}

	public void setCodVrsVdi(Integer codVrsVdi) {
		this.codVrsVdi = codVrsVdi;
	}

	public Integer getCodVrsVdi() {
		return codVrsVdi;
	}

	public void setCodCmp(String codCmp) {
		this.codCmp = codCmp;
	}

	public String getCodCmp() {
		return codCmp;
	}

	public void setCodDemDos(String codDemDos) {
		this.codDemDos = codDemDos;
	}

	public String getCodDemDos() {
		return codDemDos;
	}

	public void setNumCls(String numCls) {
		this.numCls = numCls;
	}

	public String getNumCls() {
		return numCls;
	}

	public void setLibEtp(String libEtp) {
		this.libEtp = libEtp;
	}

	public String getLibEtp() {
		return libEtp;
	}

	public String getLibelleVersionEtape() {
		return libelleVersionEtape;
	}

	public void setLibelleVersionEtape(String libelleVersionEtape) {
		this.libelleVersionEtape = libelleVersionEtape;
	}

	public String getCodDecVeu() {
		return codDecVeu;
	}

	public void setCodDecVeu(String codDecVeu) {
		this.codDecVeu = codDecVeu;
	}

}