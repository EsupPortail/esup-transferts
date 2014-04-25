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
		    name="getListeTests",
		    query="SELECT test FROM Test test"
		    ),
    @NamedQuery(
    name="getTest",
    query="SELECT test FROM Test test WHERE test.id = :id"
    )
})
@Table(name = "TEST")
public class Test implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732897404494181L;

	/**
	 * Identifiant
	 */
	@Id
	@Column(name = "ID")
	private Integer id;

	/**
	 * Libelle
	 */
	@Column(name = "LIBELLE")
	private String libelle;			

	/**
	 * Bean constructor.
	 */
	public Test() {
		super();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return super.hashCode();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", libelle="
				+ libelle + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}