/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The class that represent users.
 */
@Entity
@IdClass(DatasExternePK.class)
@NamedQueries({
	@NamedQuery(
		    name="getAllDatasExterneByIdentifiant",
		    query="SELECT datasExterne FROM DatasExterne datasExterne WHERE datasExterne.identifiant = :identifiant"),
	@NamedQuery(
		    name="getAllDatasExterneByIdentifiantAndNiveau",
		    query="SELECT datasExterne FROM DatasExterne datasExterne WHERE datasExterne.identifiant = :identifiant AND datasExterne.niveau = :niveau"),
	@NamedQuery(
		    name="getAllDatasExterneByNiveau",
		    query="SELECT datasExterne FROM DatasExterne datasExterne WHERE datasExterne.niveau = :niveau")		    
})
@Table(name = "DATAS_EXTERNE")
public class DatasExterne implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427722297404494181L;

	/**
	 * Identifiant
	 */
	@Id
	private String identifiant;

	/**
	 * Code de la data
	 */
	@Id
	private String code;	

	/**
	 * niveau de l'interdit
	 * 1 blocage de la saisie de la demande de transfert
	 * 2 VAP
	 * 3 etc...
	 */
	private Integer niveau;		
	
	/**
	 * Libellé d'interdit
	 */
	@Column(name = "lib_interdit")
	private String libInterdit;		
	
	@Column(name = "date_fin")	
	@Temporal(TemporalType.DATE)
	private Date dateFin;	

	@Column(name = "date_creation")
	@Temporal(TemporalType.DATE)
	private Date dateCreation;	
	
	/**
	 * Bean constructor.
	 */
	public DatasExterne() {
		super();
	}

	@Override
	public String toString() {
		return "DatasExterne [identifiant=" + identifiant + ", code=" + code
				+ ", niveau=" + niveau + ", libInterdit=" + libInterdit
				+ ", dateFin=" + dateFin + ", dateCreation=" + dateCreation
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DatasExterne)) return false;

		DatasExterne that = (DatasExterne) o;

		if (!getIdentifiant().equals(that.getIdentifiant())) return false;
		if (!getCode().equals(that.getCode())) return false;
		if (!getNiveau().equals(that.getNiveau())) return false;
		if (!getLibInterdit().equals(that.getLibInterdit())) return false;
		if (getDateFin() != null ? !getDateFin().equals(that.getDateFin()) : that.getDateFin() != null) return false;
		return getDateCreation() != null ? getDateCreation().equals(that.getDateCreation()) : that.getDateCreation() == null;

	}

	@Override
	public int hashCode() {
		int result = getIdentifiant().hashCode();
		result = 31 * result + getCode().hashCode();
		result = 31 * result + getNiveau().hashCode();
		result = 31 * result + getLibInterdit().hashCode();
		result = 31 * result + (getDateFin() != null ? getDateFin().hashCode() : 0);
		result = 31 * result + (getDateCreation() != null ? getDateCreation().hashCode() : 0);
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLibInterdit(String libInterdit) {
		this.libInterdit = libInterdit;
	}

	public String getLibInterdit() {
		return libInterdit;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public Integer getNiveau() {
		return niveau;
	}

	public void setNiveau(Integer niveau) {
		this.niveau = niveau;
	}
}