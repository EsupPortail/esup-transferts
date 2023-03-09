package org.esupportail.transferts.domain.beans;

import javax.persistence.Column;

public class ComposantePK implements java.io.Serializable {

	private static final long serialVersionUID = 7427123897404494181L;

	@Column(name = "annee")
	private Integer annee;
	
	@Column(name = "COD_CMP", nullable=false, length=9)
	private String codeComposante;

	@Column(name = "FROM_SOURCE", nullable=false, length=1)
	private String source;	
		
	public ComposantePK() {}

	public ComposantePK(Integer annee, String codeComposante, String source) 
	{
		this.annee=annee;
		this.codeComposante=codeComposante;
		this.source=source;
	}

	public boolean equals(Object obj) {
		boolean resultat;

		if (obj == this) {
			resultat = true;
		} else {
			if (!(obj instanceof ComposantePK))
			{
				resultat = false;
			}
			else 
			{
				ComposantePK autre = (ComposantePK) obj;
				if (annee==autre.annee  
					&& codeComposante==autre.codeComposante 
					&& source==autre.source) 
					resultat = true;
				else 
					resultat = false;
			}
		}
		return resultat;
	}

	public int hashCode() {
		return (annee + codeComposante + source).hashCode();
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}
}