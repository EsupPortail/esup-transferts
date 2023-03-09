package org.esupportail.transferts.domain.beans;

import javax.persistence.Column;
import javax.persistence.Id;

public class OffreDeFormationPK implements java.io.Serializable {

	private static final long serialVersionUID = 2327732897404494181L;

	@Column(name = "COD_RNE", nullable=false, length=8)
	private String rne;
	
	@Column(name = "COD_ANU", nullable=false, length=4)	
	private Integer annee;		
	
	@Column(name = "COD_DIP", nullable=false, length=7)
	private String codeDiplome;

	@Column(name = "COD_VRS_VDI", nullable=false, length=10)
	private Integer codeVersionDiplome;		

	@Column(name = "COD_ETP", nullable=false, length=6)
	private String codeEtape;		

	@Column(name = "COD_VET", nullable=false, length=6)
	private String codeVersionEtape;		
	
	@Column(name = "COD_CGE", nullable=true, length=9)
	private String codeCentreGestion;		

	public OffreDeFormationPK() {}

	public OffreDeFormationPK(String rne, Integer annee, String codeDiplome, Integer codeVersionDiplome, String codeEtape, String codeVersionEtape, String codeCentreGestion) 
	{
		this.rne=rne;
		this.annee=annee;
		this.codeDiplome=codeDiplome;
		this.codeVersionDiplome = codeVersionDiplome;
		this.codeEtape=codeEtape;
		this.codeVersionEtape = codeVersionEtape;		
		this.codeCentreGestion=codeCentreGestion;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OffreDeFormationPK)) return false;

		OffreDeFormationPK that = (OffreDeFormationPK) o;

		if (!getRne().equals(that.getRne())) return false;
		if (!getAnnee().equals(that.getAnnee())) return false;
		if (!getCodeDiplome().equals(that.getCodeDiplome())) return false;
		if (!getCodeVersionDiplome().equals(that.getCodeVersionDiplome())) return false;
		if (!getCodeEtape().equals(that.getCodeEtape())) return false;
		if (!getCodeVersionEtape().equals(that.getCodeVersionEtape())) return false;
		return getCodeCentreGestion() != null ? getCodeCentreGestion().equals(that.getCodeCentreGestion()) : that.getCodeCentreGestion() == null;

	}

	@Override
	public int hashCode() {
		int result = getRne().hashCode();
		result = 31 * result + getAnnee().hashCode();
		result = 31 * result + getCodeDiplome().hashCode();
		result = 31 * result + getCodeVersionDiplome().hashCode();
		result = 31 * result + getCodeEtape().hashCode();
		result = 31 * result + getCodeVersionEtape().hashCode();
		result = 31 * result + (getCodeCentreGestion() != null ? getCodeCentreGestion().hashCode() : 0);
		return result;
	}

	public String getCodeDiplome() {
		return codeDiplome;
	}

	public void setCodeDiplome(String codeDiplome) {
		this.codeDiplome = codeDiplome;
	}

	public Integer getCodeVersionDiplome() {
		return codeVersionDiplome;
	}

	public void setCodeVersionDiplome(Integer codeVersionDiplome) {
		this.codeVersionDiplome = codeVersionDiplome;
	}

	public String getCodeEtape() {
		return codeEtape;
	}

	public void setCodeEtape(String codeEtape) {
		this.codeEtape = codeEtape;
	}

	public String getCodeVersionEtape() {
		return codeVersionEtape;
	}

	public void setCodeVersionEtape(String codeVersionEtape) {
		this.codeVersionEtape = codeVersionEtape;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public String getRne() {
		return rne;
	}

	public void setRne(String rne) {
		this.rne = rne;
	}

	public String getCodeCentreGestion() {
		return codeCentreGestion;
	}

	public void setCodeCentreGestion(String codeCentreGestion) {
		this.codeCentreGestion = codeCentreGestion;
	}



}