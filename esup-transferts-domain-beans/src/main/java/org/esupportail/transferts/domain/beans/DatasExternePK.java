package org.esupportail.transferts.domain.beans;

import javax.persistence.Column;

public class DatasExternePK implements java.io.Serializable {

	private static final long serialVersionUID = 7427732897412345181L;

	@Column(name = "IDENTIFIANT", nullable=false)
	private String identifiant;

	@Column(name = "CODE", nullable=false)
	private String code;	
		
	public DatasExternePK() {}

	public DatasExternePK(String identifiant, String code) 
	{
		this.identifiant=identifiant;
		this.code=code;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DatasExternePK)) return false;

		DatasExternePK that = (DatasExternePK) o;

		if (!getIdentifiant().equals(that.getIdentifiant())) return false;
		return getCode().equals(that.getCode());

	}

	@Override
	public int hashCode() {
		int result = getIdentifiant().hashCode();
		result = 31 * result + getCode().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}