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
		    name="getAllParametres",
		    query="SELECT param FROM Parametres param"
		    ),
    @NamedQuery(
    name="getParametreByCode",
    query="SELECT param FROM Parametres param WHERE param.codeParametre = :codeParametre"
    )
})
@Table(name = "PARAMETRES")
public class Parametres implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427999897404494181L;

	/**
	 * Numéro RNE de l'établissement
	 */
	@Id
	@Column(name = "code", nullable=false)
	private String codeParametre;

	/**
	 * Valeur vrai ou faux pour le code paramètre
	 */
	@Column(name = "etat")
	private boolean bool;		
	
	/**
	 * Commentaire
	 */
	@Column(name = "commentaire", length = 2000)
	private String commentaire;		
	

	/**
	 * Bean constructor.
	 */
	public Parametres() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Parametres)) return false;

		Parametres that = (Parametres) o;

		if (isBool() != that.isBool()) return false;
		if (!getCodeParametre().equals(that.getCodeParametre())) return false;
		return getCommentaire() != null ? getCommentaire().equals(that.getCommentaire()) : that.getCommentaire() == null;

	}

	@Override
	public int hashCode() {
		int result = getCodeParametre().hashCode();
		result = 31 * result + (isBool() ? 1 : 0);
		result = 31 * result + (getCommentaire() != null ? getCommentaire().hashCode() : 0);
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Parametres [codeParametre=" + codeParametre + ", bool=" + bool
				+ ", commentaire=" + commentaire + "]";
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public void setCodeParametre(String codeParametre) {
		this.codeParametre = codeParametre;
	}

	public String getCodeParametre() {
		return codeParametre;
	}

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}
}