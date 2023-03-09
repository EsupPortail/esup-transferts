/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The class that represent SystemeProperties.
 */
public class SystemeProperties implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 1427732897404494123L;

	private String cle;

	private String valeur;


	/**
	 * Bean constructor.
	 */
	public SystemeProperties() {
		super();
	}

	public SystemeProperties(String cle, String valeur) {
		this.cle = cle;
		this.valeur = valeur;
	}

	@Override
	public String toString() {
		return "SystemeProperties{" +
				"cle='" + cle + '\'' +
				", valeur='" + valeur + '\'' +
				'}';
	}

	public String getCle() {
		return cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
}