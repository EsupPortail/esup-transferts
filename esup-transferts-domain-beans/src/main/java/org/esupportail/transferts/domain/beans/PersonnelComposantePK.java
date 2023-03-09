package org.esupportail.transferts.domain.beans;

import javax.persistence.Column;

public class PersonnelComposantePK implements java.io.Serializable {

	private static final long serialVersionUID = 7427732897404494123L;

	@Column(name = "IDENTIFIANT")
	private String uid;
	
	@Column(name = "COD_CMP", nullable=false, length=9)
	private String codeComposante;

	@Column(name = "FROM_SOURCE", nullable=false, length=1)
	private String source;	
	
	@Column(name = "ANNEE", length = 4)
	private Integer annee;		
	
	public PersonnelComposantePK() {}

	public PersonnelComposantePK(String uid, String codeComposante, String source, Integer annee) 
	{
		this.uid=uid;
		this.codeComposante=codeComposante;
		this.source=source;
		this.annee=annee;
	}

	public boolean equals(Object obj) {
		boolean resultat;

		if (obj == this) {
			resultat = true;
		} else {
			if (!(obj instanceof PersonnelComposantePK))
			{
				resultat = false;
			}
			else 
			{
				PersonnelComposantePK autre = (PersonnelComposantePK) obj;
				if (uid==autre.uid  
					&& codeComposante==autre.codeComposante 
					&& source==autre.source
					&& annee==autre.annee) 
					resultat = true;
				else 
					resultat = false;
			}
		}
		return resultat;
	}

	public int hashCode() {
		return (uid + codeComposante + source + annee).hashCode();
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}