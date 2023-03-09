/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The class that represent users.
 */
@Entity
@NamedQueries({
	@NamedQuery(
			name="getAllOffreDeFormations",
			query="SELECT odf FROM OffreDeFormationsDTO odf"
			)
	,
	@NamedQuery(
			name="getAllOffreDeFormationByAnneeAndRne",
			query="SELECT odf FROM OffreDeFormationsDTO odf WHERE odf.annee = :annee AND odf.rne = :rne AND odf.actif=1"
			)
	,	
	@NamedQuery(
			name="getAllOffreDeFormationByAnneeAndRneAndAtifOuPas",
			query="SELECT odf FROM OffreDeFormationsDTO odf WHERE odf.annee = :annee AND odf.rne = :rne"
			)
	,
	@NamedQuery(
			name="getOdfTypesDiplomeByRneAndAnneeAndDepart",
			query="SELECT DISTINCT(odf.codTypDip) , odf.libTypDip FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND odf.actif=1 AND odf.depart = 'oui'"
			)	
	,
	@NamedQuery(
			name="getOdfTypesDiplomeByRneAndAnneeAndArrivee",
			query="SELECT DISTINCT(odf.codTypDip) , odf.libTypDip FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND odf.actif=1 AND odf.arrivee = 'oui'"
			)	
	,	
	@NamedQuery(
			name="getAllOdfTypesDiplomeByRneAndAnnee",
			query="SELECT DISTINCT(odf.codTypDip) , odf.libTypDip FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee"
			)	
	,
	@NamedQuery(
			name="getAnneesEtudeByRneAndAnneeAndCodTypDipAndDepart",
			query="SELECT DISTINCT(odf.codeNiveau) , odf.libNiveau FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND codTypDip = :codTypDip AND odf.actif=1 AND odf.depart = 'oui'"
			)
	,
	@NamedQuery(
			name="getAnneesEtudeByRneAndAnneeAndCodTypDipAndArrivee",
			query="SELECT DISTINCT(odf.codeNiveau) , odf.libNiveau FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND codTypDip = :codTypDip AND odf.actif=1 AND odf.arrivee = 'oui'"
			)	
	,
	@NamedQuery(
			name="getAllAnneesEtudeByRneAndAnneeAndCodTypDip",
			query="SELECT DISTINCT(odf.codeNiveau) , odf.libNiveau FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND codTypDip = :codTypDip"
			)	
	,	
	@NamedQuery(
			name="getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndDepart",
			query="SELECT DISTINCT(odf.codeDiplome) , odf.libDiplome FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND codTypDip = :codTypDip AND odf.codeNiveau = :codeNiveau AND odf.actif=1 AND odf.depart = 'oui'"
			)	
	,
	@NamedQuery(
			name="getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndArrivee",
			query="SELECT DISTINCT(odf.codeDiplome) , odf.libDiplome FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND codTypDip = :codTypDip AND odf.codeNiveau = :codeNiveau AND odf.actif=1 AND odf.arrivee = 'oui'"
			)	
	,		
	@NamedQuery(
			name="getAllLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau",
			query="SELECT DISTINCT(odf.codeDiplome) , odf.libDiplome FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND codTypDip = :codTypDip AND odf.codeNiveau = :codeNiveau"
			)	
	,		
	@NamedQuery(
			name="getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndDepart",
			query="SELECT odf FROM OffreDeFormationsDTO odf WHERE odf.rne =:rne AND odf.annee =:annee AND codTypDip =:codTypDip AND odf.codeNiveau = :codeNiveau AND odf.codeDiplome =:codeDiplome AND odf.actif=1 AND odf.depart = 'oui'"
			)	
	,		
	@NamedQuery(
			name="getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndArrivee",
			query="SELECT odf FROM OffreDeFormationsDTO odf WHERE odf.rne =:rne AND odf.annee =:annee AND codTypDip =:codTypDip AND odf.codeNiveau = :codeNiveau AND odf.codeDiplome =:codeDiplome AND odf.actif=1 AND odf.arrivee = 'oui'"
			)	
	,
	@NamedQuery(
			name="getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndDepart",
			query="SELECT odf FROM OffreDeFormationsDTO odf WHERE odf.rne =:rne AND odf.annee =:annee AND codTypDip =:codTypDip AND odf.codeNiveau = :codeNiveau AND odf.codeComposante=:codeComposante AND odf.actif=1 AND odf.depart = 'oui'"
			)	
	,		
	@NamedQuery(
			name="getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndArrivee",
			query="SELECT odf FROM OffreDeFormationsDTO odf WHERE odf.rne =:rne AND odf.annee =:annee AND codTypDip =:codTypDip AND odf.codeNiveau = :codeNiveau AND odf.codeComposante=:codeComposante AND odf.actif=1 AND odf.arrivee = 'oui'"
			)	
	,	
	@NamedQuery(
			name="getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndDepartAndAtifOuPas",
			query="SELECT odf FROM OffreDeFormationsDTO odf WHERE odf.rne =:rne AND odf.annee =:annee AND codTypDip =:codTypDip AND odf.codeNiveau = :codeNiveau AND odf.codeComposante=:codeComposante AND odf.depart = 'oui'"
			)	
	,		
	@NamedQuery(
			name="getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposanteAndArriveeAndAtifOuPas",
			query="SELECT odf FROM OffreDeFormationsDTO odf WHERE odf.rne =:rne AND odf.annee =:annee AND codTypDip =:codTypDip AND odf.codeNiveau = :codeNiveau AND odf.codeComposante=:codeComposante AND odf.arrivee = 'oui'"
			)	
	,		
	@NamedQuery(
			name="getOdfByPK",
			query="SELECT odf FROM OffreDeFormationsDTO odf"
			)	
	,
	@NamedQuery(
			name="getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDipAndAtifOuPas",
			query="SELECT odf FROM OffreDeFormationsDTO odf WHERE odf.rne =:rne AND odf.annee =:annee AND codTypDip =:codTypDip AND odf.codeNiveau = :codeNiveau AND odf.codeDiplome =:codeDiplome"
			)
	,		
	@NamedQuery(
			name="getDateMaxMajByRneAndAnnee",
			query="SELECT MAX(odf.dateMaj) FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee"
			)
	,
	@NamedQuery(
			name="getFormationsByRneAndAnnee",
			query="select odf FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee"
			)	
	,
	@NamedQuery(
			name="getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne",
			query="select odf FROM OffreDeFormationsDTO odf WHERE odf.dateMaj > :dateMax AND odf.annee = :annee AND odf.rne = :rne"
			)
	,
	@NamedQuery(
			name="getOdfComposanteByRneAndAnneeAndActif",
			query="SELECT DISTINCT(odf.codeComposante) , odf.libComposante FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND odf.actif=1"
			)	
	,
//	@NamedQuery(
//			name="getOdfComposanteByRneAndAnneeAndActifAndArrivee",
//			query="SELECT DISTINCT(odf.codeComposante) , odf.libComposante FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND odf.actif=1 AND odf.arrivee = 'oui'"
//			)	
//	,	
//	@NamedQuery(
//			name="getOdfComposanteByRneAndAnneeAndActifAndArriveeAndCodTypDip",
//			query="SELECT DISTINCT(odf.codeComposante) , odf.libComposante FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND odf.actif=1 AND odf.arrivee = 'oui' AND codTypDip = :codTypDip"
//			)	
//	,		
//	@NamedQuery(
//			name="getOdfComposanteByRneAndAnneeAndDepartAndCodTypDip",
//			query="SELECT DISTINCT(odf.codeComposante) , odf.libComposante FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND odf.codTypDip = :codTypDip AND odf.depart = 'oui'"
//			)	
//	,		
//	@NamedQuery(
//			name="getOdfComposanteByRneAndAnneeAndArriveeAndCodTypDip",
//			query="SELECT DISTINCT(odf.codeComposante) , odf.libComposante FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND odf.codTypDip = :codTypDip AND odf.arrivee = 'oui'"
//			)	
//	,			
	@NamedQuery(
			name="getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante",
			query="SELECT DISTINCT(odf.codeDiplome) , odf.libDiplome FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND codTypDip = :codTypDip AND odf.codeNiveau = :codeNiveau AND odf.codeComposante=:codeComposante AND odf.actif=1"
			)	
	,
	@NamedQuery(
			name="getAllLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveauAndComposante",
			query="SELECT DISTINCT(odf.codeDiplome) , odf.libDiplome FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND codTypDip = :codTypDip AND odf.codeNiveau = :codeNiveau AND odf.codeComposante=:codeComposante"
			)	,
	@NamedQuery(
			name="getOdfComposanteByRneAndAnneeAndCodTypDip",
			query="SELECT DISTINCT(odf.codeComposante) , odf.libComposante FROM OffreDeFormationsDTO odf WHERE odf.rne = :rne AND odf.annee = :annee AND odf.codTypDip = :codTypDip"
			)
})

@IdClass(OffreDeFormationPK.class)
@Table(name = "OFFRE_FORMATIONS")
public class OffreDeFormationsDTO implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427666897404494181L;

	/**
	 * Code RNE
	 */
	@Id
	private String rne;		

	/**
	 * Annee
	 */
	@Id
	private Integer annee;		

	/**
	 * Code diplome
	 */
	@Id
	private String codeDiplome;

	/**
	 * Code version diplome
	 */
	@Id
	private Integer codeVersionDiplome;		

	/**
	 * Code etape
	 */
	@Id
	private String codeEtape;		

	/**
	 * Code version etape
	 */
	@Id
	private String codeVersionEtape;	

	/**
	 * Code centre de gestion
	 */
	@Id
	private String codeCentreGestion;		

	/**
	 * Libelle version Diplome
	 */
	@Column(name = "LIB_DIP", nullable=false, length=120)
	private String libDiplome;	

	/**
	 * Libelle version d'etape
	 */
	@Column(name = "LIB_WEB_VET", nullable=true, length=120)
	private String libVersionEtape;		

	/**
	 * Code Composante
	 */
	@Column(name = "COD_COMP", nullable=true, length=9)
	private String codeComposante;			

	/**
	 * Libelle composante
	 */
	@Column(name = "LIB_COMP", nullable=true, length=200)
	private String libComposante;		

	/**
	 * Libelle centre de gestion
	 */
	@Column(name = "LIB_CGE", nullable=true, length=40)	
	private String libCentreGestion;	

	/**
	 * code niveau
	 */
	@Column(name = "COD_SIS_DAA_MIN", nullable=false, length=2)
	private Integer codeNiveau;		

	/**
	 * libelle niveau
	 */
	@Column(name = "LIB_COD_SIS_DAA_MIN", nullable=false, length=40)
	private String libNiveau;		

	/**
	 * Temoin de formation active
	 */
	@Column(name = "ACTIF", nullable=false, columnDefinition = "INTEGER default 1")
	//	@Column(name = "ACTIF", nullable=false)
	private Integer actif;	

	@Column(name = "COD_TYP_DIP", nullable=false, length=6)
	private String codTypDip;	

	@Column(name = "LIB_TYP_DIP", nullable=false, length=40)
	private String libTypDip;	

	@Column(name = "DATE_MAJ", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	//le nombre maximum sera atteint le 19 janvier 2038 a 3 h 14 min 7 s.
	private Date dateMaj;		

	@Column(name = "DEPART", nullable=false, length=3)
	private String depart;		

	@Column(name = "ARRIVEE", nullable=false, length=3)
	private String arrivee;	

	/**
	 * Bean constructor.
	 */
	public OffreDeFormationsDTO() {
		super();
	}

	/**
	 * Bean constructor.
	 */
	public OffreDeFormationsDTO(String rne,
			Integer annee,
			String codTypDip,
			String libTypDip,
			String codeDiplome, 
			Integer codeVersionDiplome, 
			String codeEtape, 
			String codeVersionEtape,
			String libVersionDiplome,
			String libVersionEtape,
			String codeComposante,
			String libComposante,
			String codeCentreGestion,
			String libCentreGestion,
			Integer codeNiveau,
			String libNiveau,
			String depart,
			String arrivee) 
	{
		super();
		this.rne=rne;
		this.annee=annee;
		this.codTypDip=codTypDip;
		this.libTypDip=libTypDip;
		this.codeDiplome=codeDiplome;
		this.codeVersionDiplome=codeVersionDiplome;
		this.codeEtape=codeEtape;
		this.codeVersionEtape=codeVersionEtape;
		this.libDiplome=libVersionDiplome;
		this.libVersionEtape=libVersionEtape;
		this.codeComposante=codeComposante;
		this.libComposante=libComposante;
		this.codeCentreGestion=codeCentreGestion;
		this.libCentreGestion=libCentreGestion;
		this.codeNiveau=codeNiveau;
		this.libNiveau=libNiveau;
		this.depart=depart;
		this.arrivee=arrivee;
	}

	@Override
	public String toString() {
		return "OffreDeFormationsDTO [rne=" + rne + ", annee=" + annee
				+ ", codeDiplome=" + codeDiplome + ", codeVersionDiplome="
				+ codeVersionDiplome + ", codeEtape=" + codeEtape
				+ ", codeVersionEtape=" + codeVersionEtape
				+ ", codeCentreGestion=" + codeCentreGestion + ", libDiplome="
				+ libDiplome + ", libVersionEtape=" + libVersionEtape
				+ ", codeComposante=" + codeComposante + ", libComposante="
				+ libComposante + ", libCentreGestion=" + libCentreGestion
				+ ", codeNiveau=" + codeNiveau + ", libNiveau=" + libNiveau
				+ ", actif=" + actif + ", codTypDip=" + codTypDip
				+ ", libTypDip=" + libTypDip + ", dateMaj=" + dateMaj
				+ ", depart=" + depart + ", arrivee=" + arrivee + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OffreDeFormationsDTO)) return false;

		OffreDeFormationsDTO that = (OffreDeFormationsDTO) o;

		if (!getRne().equals(that.getRne())) return false;
		if (!getAnnee().equals(that.getAnnee())) return false;
		if (!getCodeDiplome().equals(that.getCodeDiplome())) return false;
		if (!getCodeVersionDiplome().equals(that.getCodeVersionDiplome())) return false;
		if (!getCodeEtape().equals(that.getCodeEtape())) return false;
		if (!getCodeVersionEtape().equals(that.getCodeVersionEtape())) return false;
		if (!getCodeCentreGestion().equals(that.getCodeCentreGestion())) return false;
		if (!getLibDiplome().equals(that.getLibDiplome())) return false;
		if (getLibVersionEtape() != null ? !getLibVersionEtape().equals(that.getLibVersionEtape()) : that.getLibVersionEtape() != null)
			return false;
		if (getCodeComposante() != null ? !getCodeComposante().equals(that.getCodeComposante()) : that.getCodeComposante() != null)
			return false;
		if (getLibComposante() != null ? !getLibComposante().equals(that.getLibComposante()) : that.getLibComposante() != null)
			return false;
		if (getLibCentreGestion() != null ? !getLibCentreGestion().equals(that.getLibCentreGestion()) : that.getLibCentreGestion() != null)
			return false;
		if (!getCodeNiveau().equals(that.getCodeNiveau())) return false;
		if (!getLibNiveau().equals(that.getLibNiveau())) return false;
		if (!getActif().equals(that.getActif())) return false;
		if (!getCodTypDip().equals(that.getCodTypDip())) return false;
		if (!getLibTypDip().equals(that.getLibTypDip())) return false;
		if (!getDateMaj().equals(that.getDateMaj())) return false;
		if (!getDepart().equals(that.getDepart())) return false;
		return getArrivee().equals(that.getArrivee());

	}

	@Override
	public int hashCode() {
		int result = getRne().hashCode();
		result = 31 * result + getAnnee().hashCode();
		result = 31 * result + getCodeDiplome().hashCode();
		result = 31 * result + getCodeVersionDiplome().hashCode();
		result = 31 * result + getCodeEtape().hashCode();
		result = 31 * result + getCodeVersionEtape().hashCode();
		result = 31 * result + getCodeCentreGestion().hashCode();
		result = 31 * result + getLibDiplome().hashCode();
		result = 31 * result + (getLibVersionEtape() != null ? getLibVersionEtape().hashCode() : 0);
		result = 31 * result + (getCodeComposante() != null ? getCodeComposante().hashCode() : 0);
		result = 31 * result + (getLibComposante() != null ? getLibComposante().hashCode() : 0);
		result = 31 * result + (getLibCentreGestion() != null ? getLibCentreGestion().hashCode() : 0);
		result = 31 * result + getCodeNiveau().hashCode();
		result = 31 * result + getLibNiveau().hashCode();
		result = 31 * result + getActif().hashCode();
		result = 31 * result + getCodTypDip().hashCode();
		result = 31 * result + getLibTypDip().hashCode();
		result = 31 * result + getDateMaj().hashCode();
		result = 31 * result + getDepart().hashCode();
		result = 31 * result + getArrivee().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getLibVersionDiplome() {
		return libDiplome;
	}

	public void setLibVersionDiplome(String libDiplome) {
		this.libDiplome = libDiplome;
	}

	public String getLibVersionEtape() {
		return libVersionEtape;
	}

	public void setLibVersionEtape(String libVersionEtape) {
		this.libVersionEtape = libVersionEtape;
	}

	public String getCodeComposante() {
		return codeComposante;
	}

	public void setCodeComposante(String codeComposante) {
		this.codeComposante = codeComposante;
	}

	public String getLibComposante() {
		return libComposante;
	}

	public void setLibComposante(String libComposante) {
		this.libComposante = libComposante;
	}

	public String getCodeCentreGestion() {
		return codeCentreGestion;
	}

	public void setCodeCentreGestion(String codeCentreGestion) {
		this.codeCentreGestion = codeCentreGestion;
	}

	public String getLibCentreGestion() {
		return libCentreGestion;
	}

	public void setLibCentreGestion(String libCentreGestion) {
		this.libCentreGestion = libCentreGestion;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public Integer getActif() {
		return actif;
	}

	public void setActif(Integer actif) {
		this.actif = actif;
	}

	public String getRne() {
		return rne;
	}

	public void setRne(String rne) {
		this.rne = rne;
	}

	public String getCodTypDip() {
		return codTypDip;
	}

	public void setCodTypDip(String codTypDip) {
		this.codTypDip = codTypDip;
	}

	public String getLibTypDip() {
		return libTypDip;
	}

	public void setLibTypDip(String libTypDip) {
		this.libTypDip = libTypDip;
	}

	public Date getDateMaj() {
		return dateMaj;
	}

	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	public Integer getCodeNiveau() {
		return codeNiveau;
	}

	public void setCodeNiveau(Integer codeNiveau) {
		this.codeNiveau = codeNiveau;
	}

	public String getLibNiveau() {
		return libNiveau;
	}

	public void setLibNiveau(String libNiveau) {
		this.libNiveau = libNiveau;
	}

	public String getLibDiplome() {
		return libDiplome;
	}

	public void setLibDiplome(String libDiplome) {
		this.libDiplome = libDiplome;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getArrivee() {
		return arrivee;
	}

	public void setArrivee(String arrivee) {
		this.arrivee = arrivee;
	}
}