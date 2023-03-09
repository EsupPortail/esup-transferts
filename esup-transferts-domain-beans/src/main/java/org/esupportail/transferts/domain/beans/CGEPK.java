package org.esupportail.transferts.domain.beans;

import javax.persistence.Column;

public class CGEPK implements java.io.Serializable {

	private static final long serialVersionUID = 7422002897404494181L;

	@Column(name = "annee")
	private Integer annee;
	
	@Column(name = "COD_CGE", nullable=false, length=9)
	private String codeCGE;

	@Column(name = "FROM_SOURCE", nullable=false, length=1)
	private String source;	
		
	public CGEPK() {}

	public CGEPK(Integer annee, String codeCGE, String source) 
	{
		this.annee=annee;
		this.codeCGE=codeCGE;
		this.source=source;
	}

	public boolean equals(Object obj) {
		boolean resultat;

		if (obj == this) {
			resultat = true;
		} else {
			if (!(obj instanceof CGEPK))
			{
				resultat = false;
			}
			else 
			{
				CGEPK autre = (CGEPK) obj;
				if (annee==autre.annee  
					&& codeCGE==autre.codeCGE 
					&& source==autre.source) 
					resultat = true;
				else 
					resultat = false;
			}
		}
		return resultat;
	}

	public int hashCode() {
		return (annee + codeCGE + source).hashCode();
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

	public String getCodeCGE() {
		return codeCGE;
	}

	public void setCodeCGE(String codeCGE) {
		this.codeCGE = codeCGE;
	}
}