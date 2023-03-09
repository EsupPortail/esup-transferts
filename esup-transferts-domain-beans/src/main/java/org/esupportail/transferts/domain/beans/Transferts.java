/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The class that represent users.
 */
@Entity
@IdClass(EtudiantRefPK.class)
//@Table(name = "Transferts")
@Table(name = "TRANSFERTS")
public class Transferts implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427739997404494181L;

	@Id
	@Column(name = "numeroEtudiant")
	private String numeroEtudiant;

	@Id
	@Column(name = "annee", length = 4)
	private Integer annee;

	/**
	 * Code etablissement
	 */
	@Column(name = "COD_ETB", length = 8)
	private String rne;

	/**
	 * Libelle etablissement
	 */
	@Transient
	private String libRne;

	/**
	 * Code departement
	 */
	@Column(name = "COD_DEPT")
	private String dept;

	/**
	 * Libelle departement
	 */
	@Transient
	private String libDept;

	/**
	 * Libelle type diplome
	 */
	@Column(name = "LIB_TYP_DIP")
	private String libelleTypeDiplome;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateDemandeTransfert;

	/**
	 * Type de transferts
	 */
	@NotEmpty(message = "Le type de transfert est obligatoire")
	@Column(name = "COD_TYPE_TRF")
	private String typeTransfert;

	/**
	 * Libelle du type de transferts
	 */
	@Transient
	private String libTypeTransfert;

	/**
	 * Temoin de transferts valide
	 * 0 Pas valider
	 * 1 Valider
	 * 2 Avis favorable ou défavorable (authorise l'impression depuis la partie etudiant)
	 */
	@Column(name = "TEM_TRF_VALID")
	private Integer temoinTransfertValide;

	/**
	 * Temoin de transferts WS
	 * 0 Pas de transferts  a l'universite d'accueil
	 * 1 Transfert OPI via WS OK
	 * 2 Transfert probleme OPI via WS !
	 */
	@Column(name = "TEM_OPI_WS")
	private Integer temoinOPIWs;

	/**
	 * Temoin de retour du transfert accueil
	 * 0 Pas de retour  de l'universite d'accueil
	 * 1 Retour transfert accepté par l'universite d'accueil
	 * 2 Retour transfert refusé par l'universite d'accueil
	 */
	@Column(name = "TEM_RETOUR_TRANSFERT_ACCUEIL", nullable = false, columnDefinition = "INTEGER default 0")
	private Integer temoinRetourTransfertAccueil;

	/**
	 * Fichier de signature de la demande de transferts
	 */
	@OneToOne(optional=true, fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumns({
			@JoinColumn(name = "md5", referencedColumnName = "md5"),
			@JoinColumn(name = "anneeSignature", referencedColumnName = "annee"),
			@JoinColumn(name = "from_source", referencedColumnName = "FROM_SOURCE") })
	private Fichier fichier;

	/**
	 * Formation
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumns({
			@JoinColumn(name = "rneOdf", referencedColumnName = "COD_RNE"),
			@JoinColumn(name = "anneeOdf", referencedColumnName = "COD_ANU"),
			@JoinColumn(name = "codeDiplome", referencedColumnName = "COD_DIP"),
			@JoinColumn(name = "codeVersionDiplome", referencedColumnName = "COD_VRS_VDI"),
			@JoinColumn(name = "codeEtape", referencedColumnName = "COD_ETP"),
			@JoinColumn(name = "codeVersionEtape", referencedColumnName = "COD_VET"),
			@JoinColumn(name = "codeCentreGestion", referencedColumnName = "COD_CGE")
	})
	private OffreDeFormationsDTO odf = new OffreDeFormationsDTO();	

	/**
	 * Bean constructor.
	 */
	public Transferts() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Transferts)) return false;

		Transferts that = (Transferts) o;

		if (!getNumeroEtudiant().equals(that.getNumeroEtudiant())) return false;
		if (!getAnnee().equals(that.getAnnee())) return false;
		if (getRne() != null ? !getRne().equals(that.getRne()) : that.getRne() != null) return false;
		if (getLibRne() != null ? !getLibRne().equals(that.getLibRne()) : that.getLibRne() != null) return false;
		if (getDept() != null ? !getDept().equals(that.getDept()) : that.getDept() != null) return false;
		if (getLibDept() != null ? !getLibDept().equals(that.getLibDept()) : that.getLibDept() != null) return false;
		if (getLibelleTypeDiplome() != null ? !getLibelleTypeDiplome().equals(that.getLibelleTypeDiplome()) : that.getLibelleTypeDiplome() != null)
			return false;
		if (getDateDemandeTransfert() != null ? !getDateDemandeTransfert().equals(that.getDateDemandeTransfert()) : that.getDateDemandeTransfert() != null)
			return false;
		if (getTypeTransfert() != null ? !getTypeTransfert().equals(that.getTypeTransfert()) : that.getTypeTransfert() != null)
			return false;
		if (getLibTypeTransfert() != null ? !getLibTypeTransfert().equals(that.getLibTypeTransfert()) : that.getLibTypeTransfert() != null)
			return false;
		if (getTemoinTransfertValide() != null ? !getTemoinTransfertValide().equals(that.getTemoinTransfertValide()) : that.getTemoinTransfertValide() != null)
			return false;
		if (getTemoinOPIWs() != null ? !getTemoinOPIWs().equals(that.getTemoinOPIWs()) : that.getTemoinOPIWs() != null)
			return false;
		if (getTemoinRetourTransfertAccueil() != null ? !getTemoinRetourTransfertAccueil().equals(that.getTemoinRetourTransfertAccueil()) : that.getTemoinRetourTransfertAccueil() != null)
			return false;
		if (getFichier() != null ? !getFichier().equals(that.getFichier()) : that.getFichier() != null) return false;
		return getOdf() != null ? getOdf().equals(that.getOdf()) : that.getOdf() == null;

	}

	@Override
	public int hashCode() {
		int result = getNumeroEtudiant().hashCode();
		result = 31 * result + getAnnee().hashCode();
		result = 31 * result + (getRne() != null ? getRne().hashCode() : 0);
		result = 31 * result + (getLibRne() != null ? getLibRne().hashCode() : 0);
		result = 31 * result + (getDept() != null ? getDept().hashCode() : 0);
		result = 31 * result + (getLibDept() != null ? getLibDept().hashCode() : 0);
		result = 31 * result + (getLibelleTypeDiplome() != null ? getLibelleTypeDiplome().hashCode() : 0);
		result = 31 * result + (getDateDemandeTransfert() != null ? getDateDemandeTransfert().hashCode() : 0);
		result = 31 * result + (getTypeTransfert() != null ? getTypeTransfert().hashCode() : 0);
		result = 31 * result + (getLibTypeTransfert() != null ? getLibTypeTransfert().hashCode() : 0);
		result = 31 * result + (getTemoinTransfertValide() != null ? getTemoinTransfertValide().hashCode() : 0);
		result = 31 * result + (getTemoinOPIWs() != null ? getTemoinOPIWs().hashCode() : 0);
		result = 31 * result + (getTemoinRetourTransfertAccueil() != null ? getTemoinRetourTransfertAccueil().hashCode() : 0);
		result = 31 * result + (getFichier() != null ? getFichier().hashCode() : 0);
		result = 31 * result + (getOdf() != null ? getOdf().hashCode() : 0);
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Transferts{" +
				"numeroEtudiant='" + numeroEtudiant + '\'' +
				", annee=" + annee +
				", rne='" + rne + '\'' +
				", libRne='" + libRne + '\'' +
				", dept='" + dept + '\'' +
				", libDept='" + libDept + '\'' +
				", libelleTypeDiplome='" + libelleTypeDiplome + '\'' +
				", dateDemandeTransfert=" + dateDemandeTransfert +
				", typeTransfert='" + typeTransfert + '\'' +
				", libTypeTransfert='" + libTypeTransfert + '\'' +
				", temoinTransfertValide=" + temoinTransfertValide +
				", temoinOPIWs=" + temoinOPIWs +
				", temoinRetourTransfertAccueil=" + temoinRetourTransfertAccueil +
				", fichier=" + fichier +
				", odf=" + odf +
				'}';
	}

	public String getRne() {
		return rne;
	}

	public void setRne(String rne) {
		this.rne = rne;
	}

	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}

	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Date getDateDemandeTransfert() {
		return dateDemandeTransfert;
	}

	public void setDateDemandeTransfert(Date dateDemandeTransfert) {
		this.dateDemandeTransfert = dateDemandeTransfert;
	}

	public String getTypeTransfert() {
		return typeTransfert;
	}

	public void setTypeTransfert(String typeTransfert) {
		this.typeTransfert = typeTransfert;
	}

	public void setLibelleTypeDiplome(String libelleTypeDiplome) {
		this.libelleTypeDiplome = libelleTypeDiplome;
	}

	public String getLibelleTypeDiplome() {
		return libelleTypeDiplome;
	}

	public void setFichier(Fichier fichier) {
		this.fichier = fichier;
	}

	public Fichier getFichier() {
		return fichier;
	}

	public void setTemoinOPIWs(Integer temoinOPIWs) {
		this.temoinOPIWs = temoinOPIWs;
	}

	public Integer getTemoinOPIWs() {
		return temoinOPIWs;
	}

	public Integer getTemoinTransfertValide() {
		return temoinTransfertValide;
	}

	public void setTemoinTransfertValide(Integer temoinTransfertValide) {
		this.temoinTransfertValide = temoinTransfertValide;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setLibTypeTransfert(String libTypeTransfert) {
		this.libTypeTransfert = libTypeTransfert;
	}

	public String getLibTypeTransfert() {
		return libTypeTransfert;
	}

	public void setLibRne(String libRne) {
		this.libRne = libRne;
	}

	public String getLibRne() {
		return libRne;
	}

	public void setLibDept(String libDept) {
		this.libDept = libDept;
	}

	public String getLibDept() {
		return libDept;
	}

	public OffreDeFormationsDTO getOdf() {
		return odf;
	}

	public void setOdf(OffreDeFormationsDTO odf) {
		this.odf = odf;
	}

	public Integer getTemoinRetourTransfertAccueil() {
		return temoinRetourTransfertAccueil;
	}

	public void setTemoinRetourTransfertAccueil(Integer temoinRetourTransfertAccueil) {
		this.temoinRetourTransfertAccueil = temoinRetourTransfertAccueil;
	}
}