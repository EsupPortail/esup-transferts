package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Farid AIT KARRA : farid.aitkarra@univ-artois.fr
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "getAllTestUnitaireEtudiantRefBySource", query = "SELECT etu FROM TestUnitaireEtudiantRef etu where etu.source = :source")
})
//@Table(name = "TestUnitaireEtudiantRef")
@Table(name = "TESTUNITAIREETUDIANTREF")
public class TestUnitaireEtudiantRef implements Serializable {
	
	private static final long serialVersionUID = 12455545478L;

	@Id
	@Column(name = "numeroIne", nullable = false, length = 11)
	private String numeroIne;	

	@Column(name = "numeroEtudiant")
	private String numeroEtudiant;

	@Column(name = "SOURCE", nullable = false, length = 1)
	private String source;	

	/**
	 * Constructeur
	 */
	public TestUnitaireEtudiantRef() {
		super();
	}

	@Override
	public String toString() {
		return "TestUnitaireEtudiantRef [numeroEtudiant=" + numeroEtudiant
				+ ", numeroIne=" + numeroIne + ", source=" + source + "]";
	}

	public String getNumeroIne() {
		return numeroIne;
	}

	public void setNumeroIne(String numeroIne) {
		this.numeroIne = numeroIne;
	}

	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}

	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
