/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.esupportail.transferts.domain.beans.Composante;


/**
 * The class that represent users.
 */
@Entity
@NamedQueries({
	@NamedQuery(
			name="getListeComposantesFromBddByAnneeAndSource",
			query="SELECT c FROM Composante c WHERE c.annee = :annee AND c.source = :source)"
			)//,
//	@NamedQuery(
//			name="getComposantesFromBddByAnneeAndSourceAndCodeComposante",
//			query="SELECT c FROM Composante c WHERE c.annee = :annee AND c.source = :source AND c.codeComposante = :codeComposante)"
//			)			
})
@IdClass(ComposantePK.class)
@Table(name = "COMPOSANTE")
public class Composante implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 1234532897404494181L;

	@Id
	private String codeComposante;		

	@Id
	private String source;		
	
	@Id
	private Integer annee;			
	
	@Column(name = "LIB_CMP", nullable=false)
	private String libelleComposante;	

	@Column(name = "valid_auto")
	private String validAuto;		
	
	/**
	 * Bean constructor.
	 */
	public Composante() {
		super();
	}

	public Composante(String codeComposante, String source, Integer annee, String libelleComposante, String validAuto) 
	{
		super();
		this.codeComposante = codeComposante;
		this.source = source;
		this.annee = annee;
		this.libelleComposante = libelleComposante;
		this.validAuto = validAuto;
	}

	@Override
	public String toString() {
		return "Composante [codeComposante=" + codeComposante + ", source="
				+ source + ", annee=" + annee + ", libelleComposante="
				+ libelleComposante + ", validAuto=" + validAuto + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Composante)) return false;

		Composante that = (Composante) o;

		if (!getCodeComposante().equals(that.getCodeComposante())) return false;
		if (!getSource().equals(that.getSource())) return false;
		if (!getAnnee().equals(that.getAnnee())) return false;
		if (!getLibelleComposante().equals(that.getLibelleComposante())) return false;
		return getValidAuto().equals(that.getValidAuto());

	}

	@Override
	public int hashCode() {
		int result = getCodeComposante().hashCode();
		result = 31 * result + getSource().hashCode();
		result = 31 * result + getAnnee().hashCode();
		result = 31 * result + getLibelleComposante().hashCode();
		result = 31 * result + getValidAuto().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCodeComposante() {
		return codeComposante;
	}

	public void setCodeComposante(String codeComposante) {
		this.codeComposante = codeComposante;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLibelleComposante() {
		return libelleComposante;
	}

	public void setLibelleComposante(String libelleComposante) {
		this.libelleComposante = libelleComposante;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public String getValidAuto() {
		return validAuto;
	}

	public void setValidAuto(String validAuto) {
		this.validAuto = validAuto;
	}
}