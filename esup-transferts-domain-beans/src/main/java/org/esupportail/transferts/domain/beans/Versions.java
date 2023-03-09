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
@Entity
@NamedQueries({
	@NamedQuery(
		    name="getAllVersions",
		    query="SELECT versions FROM Versions versions"
		    ),
    @NamedQuery(
    name="getVersionByNumero",
    query="SELECT version FROM Versions version WHERE version.numero = :numero"
		    ),
    @NamedQuery(
    name="getVersionByEtat",
    query="SELECT version FROM Versions version WHERE etat = :etat"    
    )
})
@Table(name = "VERSIONS")
public class Versions implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 1427732897404494181L;

	/**
	 * Numéro RNE de l'établissement
	 */
	@Id
	@Column(name = "numero", nullable=false)
	private String numero;

	/**
	 * Valeur vrai ou faux pour le code paramètre
	 */
	@Column(name = "etat")
	private Integer etat;		
	
	/**
	 * Commentaire
	 */
	@Column(name = "commentaire", length = 2000)
	private String commentaire;		
	

	/**
	 * Bean constructor.
	 */
	public Versions() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Versions)) return false;

		Versions versions = (Versions) o;

		if (!getNumero().equals(versions.getNumero())) return false;
		if (etat != null ? !etat.equals(versions.etat) : versions.etat != null) return false;
		return getCommentaire() != null ? getCommentaire().equals(versions.getCommentaire()) : versions.getCommentaire() == null;

	}

	@Override
	public int hashCode() {
		int result = getNumero().hashCode();
		result = 31 * result + (etat != null ? etat.hashCode() : 0);
		result = 31 * result + (getCommentaire() != null ? getCommentaire().hashCode() : 0);
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Versions [numero=" + numero + ", etat=" + etat
				+ ", commentaire=" + commentaire + "]";
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer isEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}
}