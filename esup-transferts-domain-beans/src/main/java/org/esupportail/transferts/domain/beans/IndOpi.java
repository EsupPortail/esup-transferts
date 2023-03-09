/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The class that represent users.
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "getIndOpiByNneAndCleIneAndAnnee", query = "SELECT indOpi FROM IndOpi indOpi WHERE indOpi.codNneIndOpi = :nne AND indOpi.codCleNneIndOpi = :cleIne AND indOpi.annee = :annee"),
		@NamedQuery(name = "allIndOpiNonSynchroAndSource", query = "SELECT indOpi FROM IndOpi indOpi LEFT JOIN FETCH indOpi.voeux WHERE indOpi.annee = :annee AND indOpi.synchro=0 AND indOpi.source = :source"),
		@NamedQuery(name = "allIndOpiBySynchroAndSource", query = "SELECT indOpi FROM IndOpi indOpi LEFT JOIN FETCH indOpi.voeux  WHERE indOpi.annee = :annee AND indOpi.synchro= :synchro AND indOpi.source= :source"),
		@NamedQuery(name = "allIndOpiBySynchroAndExcluAndSource", query = "SELECT indOpi FROM IndOpi indOpi WHERE indOpi.annee = :annee AND indOpi.synchro!=0 AND indOpi.source= :source"),
		@NamedQuery(name = "updateSynchroOpi", query = "UPDATE IndOpi i SET i.synchro=1 WHERE i.numeroOpi= :numeroOpi"),	
		@NamedQuery(name = "allIndOpiBySource", query = "SELECT indOpi FROM IndOpi indOpi WHERE indOpi.annee = :annee AND indOpi.source = :source"),
		@NamedQuery(name = "allIndOpi", query = "SELECT indOpi FROM IndOpi indOpi WHERE indOpi.annee = :annee"),
		@NamedQuery(name = "allIndOpiByAnnee", query = "SELECT indOpi FROM IndOpi indOpi WHERE indOpi.annee = :annee"),
		@NamedQuery(name = "getIndOpiBySource", query = "SELECT indOpi FROM IndOpi indOpi WHERE indOpi.numeroOpi = :numeroOpi AND indOpi.annee = :annee AND indOpi.source = :source"),
		@NamedQuery(name = "getIndOpiExtractBySource", query = "SELECT DISTINCT RPAD (IFNULL(io.numeroOpi,' '), 11, ' ')"
				+ "|| RPAD (' ',1,' ')"
				+ "|| RPAD (IFNULL(io.codPayNat,' '),3,' ')"
				+ "|| RPAD (IFNULL(io.codEtb,' '),8,' ')"
				+ "|| RPAD (IFNULL(io.codNneIndOpi,' '),11,' ')"
				+ "|| RPAD (IFNULL(io.codCleNneIndOpi,' '),1,' ')"
				+ "|| DATE_FORMAT(io.dateNaiIndOpi, '%Y-%m-%d')"
				+ "|| RPAD (IFNULL(io.temDateNaiRelOpi,' '),1,' ')"
				+ "|| RPAD (' ',6,' ')"
				+ "|| RPAD (IFNULL(io.daaEntEtbOpi,' '),4,' ')"
				+ "|| RPAD (IFNULL(io.libNomPatIndOpi,' '),30,' ')"
				+ "|| RPAD (IFNULL(io.libNomUsuIndOpi,' '),30,' ')"
				+ "|| RPAD (IFNULL(io.libPr1IndOpi,' '),20,' ')"
				+ "|| RPAD (IFNULL(io.libPr2IndOpi,' '),20,' ')"
				+ "|| RPAD (IFNULL(io.libPr3IndOpi,' '),20,' ')"
				+ "|| RPAD (IFNULL(io.LibVilNaiEtuOpi,' '),30,' ')"
				+ "|| RPAD (IFNULL(io.codDepPayNai,' '),3,' ')"
				+ "|| RPAD (IFNULL(io.codTypDepPayNai,' '),1,' ')"
				+ "|| RPAD (' ',115,' ')"
//				+ "|| RPAD (' ',21,' ')"
				+
				 "|| RPAD (IFNULL(io.codBac, ' '),4, ' ')" +
				 "|| RPAD (IFNULL(io.codEtbBac, ' '),8, ' ')" +
				 "|| RPAD (IFNULL(io.codDep,' '),3,' ')" +
				 "|| RPAD (IFNULL(io.codMnb, ' '),2, ' ')" +
				 "|| RPAD (IFNULL(io.daabacObtOba,' '),4,' ')" +
				"|| RPAD (IFNULL(io.codPay,' '),3,' ')"
				+
				// "|| RPAD (' ',5,' ')" +
				"|| RPAD (IFNULL(io.codBdi,' '),5,' ')"
				+ "|| RPAD (IFNULL(io.codCom,' '),5,' ')"
				+ "|| RPAD (IFNULL(io.libAd1,' '),32,' ')"
				+ "|| RPAD (IFNULL(io.libAd2,' '),32,' ')"
				+ "|| RPAD (IFNULL(io.libAd3,' '),32,' ')"
				+ "|| RPAD (IFNULL(io.libAde,' '),32,' ')"
				+ "|| RPAD (IFNULL(io.numTel,' '),15,' ')"
				+ "|| RPAD (IFNULL(io.daaEnsSupOpi,' '),4,' ')"
				+ "|| RPAD (' ',160,' ')"
				+ "|| RPAD (IFNULL(io.codSexEtuOpi,' '),1,' ')"
				+ "|| RPAD (' ',4,' ')"
				+ "|| RPAD (IFNULL(io.adrMailOpi,' '),200,' ')"
				+ "|| RPAD (IFNULL(io.numTelPorOpi,' '),15,' ')"
				+ "|| RPAD (' ',2,' ')"
				+
				// "|| RPAD (IFNULL(io.codTpe,' '),2,' ')" +
				"|| RPAD (' ',71,' ')"
				+ "|| RPAD (IFNULL(io.daaEtrSup,' '),4,' ')"
				+ "|| RPAD (' ',219,' ') "
				+ "|| RPAD (' ',3,' ')"
				+ "FROM IndOpi io "
				+ "WHERE io.annee = :annee AND io.source = :source"),
		@NamedQuery(name = "getVoeuxInsExtractBySource", query = "SELECT DISTINCT RPAD (IFNULL(vi.voeux.numeroOpi,' '), 11, ' ') "
				+ "|| RPAD (IFNULL(vi.voeux.libNomPatIndOpi,' '),30,' ') "
				+ "|| RPAD (IFNULL(vi.voeux.libPr1IndOpi,' '),20,' ') "
				+ "|| RPAD (IFNULL(vi.voeux.codCge,' '),3,' ') "
				+ "|| RPAD (IFNULL(vi.voeux.codEtp,' '),6,' ') "
				+ "|| RPAD (IFNULL(vi.voeux.codVrsVet,' '),3,' ') "
				+ "|| RPAD (IFNULL(vi.voeux.codDip,' '),7,' ') "
				+
				// "|| RPAD (IFNULL(vi.voeux.codVrsVdi,' '),3,' ') " +
				"|| RPAD (IFNULL(vi.voeux.codVrsVdi,null),3,' ') "
				+ "|| RPAD (IFNULL(vi.voeux.codCmp,' '),3,' ') "
				+ "|| RPAD (' ',4,' ') "
				+ "|| RPAD (IFNULL(vi.voeux.codDemDos,' '),1,' ') "
				+ "|| RPAD (IFNULL(vi.voeux.codDemDos,' '),1,' ') "
				+ "|| RPAD ('F',1,' ') "
				+ "|| RPAD (IFNULL(vi.voeux.numCls,' '),2,' ') "
				+ "|| RPAD (' ',268,' ') "
				+ " FROM IndOpi vi "
				+ " WHERE vi.annee = :annee AND vi.source = :source"),
		@NamedQuery(name = "updateLibelleVersionEtapeLocal", query = "UPDATE IndOpi i SET i.voeux.libelleVersionEtape= :libelleVersionEtape WHERE i.numeroOpi= :numeroOpi"),
		@NamedQuery(name = "getPresenceEtudiantOPiByIneAndAnnee", query = "SELECT indOpi FROM IndOpi indOpi WHERE indOpi.codNneIndOpi=:codNneIndOpi AND indOpi.codCleNneIndOpi=:codCleNneIndOpi AND indOpi.annee=:annee"),
		@NamedQuery(name = "getStatistiquesNombreTotalTransfertOPI", query = "select count(indOpi.numeroOpi) from IndOpi indOpi where indOpi.annee=:annee")
})
@Table(name = "IND_OPI")
public class IndOpi implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7421112897404494181L;

	/**
	 * Numero de l'OPI
	 */
	@Id
	@Column(name = "COD_OPI_INT_EPO", nullable = false, length = 10)
	private String numeroOpi;

	/**
	 * Libelle etablissement de depart
	 */
	@Transient
	private String libEtabDepart;

	/**
	 * Etablissement de depart
	 */
	@Column(name = "ETAB_DEPART", length = 8)
	private String etabDepart;

	/**
	 * Annee universitaire
	 */
	@Column(name = "annee")
	private Integer annee;

	/**
	 * Code pays de nationalite
	 */
	@Column(name = "COD_PAY_NAT", length = 3)
	private String codPayNat;

	/**
	 * Code de l'etablissement de premiere inscription universite en France
	 */
	@Column(name = "COD_ETB", length = 8)
	private String codEtb;

	/**
	 * Numero national etudiant
	 */
	@Column(name = "COD_NNE_IND_OPI", length = 11)
	private String codNneIndOpi;

	/**
	 * Cle du numero national etudiant
	 */
	@Column(name = "COD_CLE_NNE_IND_OPI", length = 2)
	private String codCleNneIndOpi;

	/**
	 * Date de naissance de l'etudiant
	 */
	@Column(name = "DATE_NAI_IND_OPI", nullable = false, length = 8)
	@Temporal(TemporalType.DATE)
	private Date dateNaiIndOpi;

	/**
	 * Temoin de date de naissance estimee
	 */
	@Column(name = "TEM_DATE_NAI_REL_OPI", nullable = false, length = 1)
	private String temDateNaiRelOpi;

	/**
	 * Annee de premiere inscription en Universite francaise
	 */
	@Column(name = "DAA_ENT_ETB_OPI")
	private String daaEntEtbOpi;

	/**
	 * Nom patronymique de l'etudiant
	 */
	@Column(name = "LIB_NOM_PAT_IND_OPI", nullable = false, length = 30)
	private String libNomPatIndOpi;

	/**
	 * Nom usuel de l'etudiant
	 */
	@Column(name = "LIB_NOM_USU_IND_OPI", length = 30)
	private String libNomUsuIndOpi;

	/**
	 * Premier prenom de l'etudiant
	 */
	@Column(name = "LIB_PR1_IND_OPI", nullable = false, length = 20)
	private String libPr1IndOpi;

	/**
	 * Second prenom de l'etudiant
	 */
	@Column(name = "LIB_PR2_IND_OPI", length = 20)
	private String libPr2IndOpi;

	/**
	 * Troisieme prenom de l'etudiant
	 */
	@Column(name = "LIB_PR3_IND_OPI", length = 20)
	private String libPr3IndOpi;

	/**
	 * Ville de naissance de l'etudiant
	 */
	@Column(name = "LIB_VIL_NAI_ETU_OPI", length = 30)
	private String LibVilNaiEtuOpi;

	/**
	 * Code du pays ou du departement de naissance de l'etudiant
	 */
	@Column(name = "COD_DEP_PAY_NAI", length = 3)
	private String codDepPayNai;

	/**
	 * Type precisant s'il s'agit d'un pays ou d'un departement
	 */
	@Column(name = "COD_TYP_DEP_PAY_NAI", length = 1)
	private String codTypDepPayNai;

	/**
	 * Annee de la premiere inscription dans l'enseignement superieure
	 */
	@Column(name = "DAA_ENS_SUP_OPI", length = 4)
	private String daaEnsSupOpi;

	/**
	 * Adresse electronique personnel de l'individu
	 */
	@Column(name = "ADR_MAIL_OPI", length = 200)
	private String adrMailOpi;

	/**
	 * Numero de telephone portable de l'individu
	 */
	@Column(name = "NUM_TEL_POR_OPI", length = 15)
	private String numTelPorOpi;

	/**
	 * Annee universitaire de premiere inscription en enseignement superieur
	 * à l'etranger
	 */
	@Column(name = "DAA_ETR_SUP", length = 4)
	private String daaEtrSup;

	/**
	 * Sexe de l'etudiant
	 */
	@Column(name = "COD_SEX_ETU_OPI", length = 1)
	private String codSexEtuOpi;

	/**
	 * Code de l'etablissement du baccalaureat ou de l'equivalence
	 */
	@Column(name = "COD_ETB_BAC", length = 8)
	private String codEtbBac;

	/**
	 * Code du baccalaureat ou de l'equivalence de l'etudiant
	 */
	@Column(name = "COD_BAC", length = 4)
	private String codBac;

	/**
	 * Departement d'obtention du baccalaureat ou de l'equivalence
	 */
	@Column(name = "COD_DEP", length = 3)
	private String codDep;

	/**
	 * Code de la mention obtenue baccalaureat
	 */
	@Column(name = "COD_MNB", length = 2)
	private String codMnb;

	/**
	 * Annee d'obtention du baccalaureat ou de l'equivalence
	 */
	@Column(name = "DAA_BAC_OBT_OBA", length = 4)
	private String daabacObtOba;

	/**
	 * Type de l'etablissement d'obtention du Bac ou de l'equivalence
	 */
	@Column(name = "COD_TPE", length = 2)
	private String codTpe;

	/**
	 * Numero de telephone temporaire de l'etudiant
	 */
	@Column(name = "NUM_TEL", length = 15)
	private String numTel;

	/**
	 * Code pays INSEE de l'adresse fixe de l'etudiant
	 */
	@Column(name = "COD_PAY", length = 3)
	private String codPay;

	/**
	 * Code du bureau distributeur de l'adresse fixe de l'etudiant
	 */
	@Column(name = "COD_BDI", length = 5)
	private String codBdi;

	/**
	 * Code de la commune de l'adresse fixe de l'etudiant
	 */
	@Column(name = "COD_COM", length = 5)
	private String codCom;

	/**
	 * Premiere ligne de l'adresse fixe de l'etudiant
	 */
	@Column(name = "LIB_AD1", length = 32)
	private String libAd1;

	/**
	 * Seconde ligne de l'adresse fixe de l'etudiant
	 */
	@Column(name = "LIB_AD2", length = 32)
	private String libAd2;

	/**
	 * Troisieme ligne de l'adresse fixe de l'etudiant
	 */
	@Column(name = "LIB_AD3", length = 32)
	private String libAd3;

	/**
	 * Code postal et ville etrangere de l'adresse fixe de l'etudiant
	 */
	@Column(name = "LIB_ADE", length = 32)
	private String libAde;

	/**
	 * Temoin de retour du transfert accueil
	 * 0 Pas de synchro
	 * 1 Synchro OK
	 * 2 Synchro NOK
	 * 3 VAP en cours
	 * 4 APB PostBac
	 */
	@Column(name = "SYNCHRO", nullable=false, columnDefinition = "INTEGER default 0")
	private Integer synchro;

	@Column(name = "SOURCE", nullable=false)
	private String source;		
	
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "COD_OPI_INT_EPO", referencedColumnName = "COD_OPI_INT_EPO")
	private VoeuxIns voeux = new VoeuxIns();

	@Column(name = "COD_IND")
	private Integer codInd;
	
	@Column(name = "COD_ETU_LPA")
	private Integer codEtuLpa;

	@Column(name = "COD_IND_OPI")
	private Integer codIndOpi;	

	/**
	 * Bean constructor.
	 */
	public IndOpi() {
		super();
	}

	@Override
	public String toString() {
		return "IndOpi [numeroOpi=" + numeroOpi + ", libEtabDepart=" + libEtabDepart + ", etabDepart=" + etabDepart
				+ ", annee=" + annee + ", codPayNat=" + codPayNat + ", codEtb=" + codEtb + ", codNneIndOpi="
				+ codNneIndOpi + ", codCleNneIndOpi=" + codCleNneIndOpi + ", dateNaiIndOpi=" + dateNaiIndOpi
				+ ", temDateNaiRelOpi=" + temDateNaiRelOpi + ", daaEntEtbOpi=" + daaEntEtbOpi + ", libNomPatIndOpi="
				+ libNomPatIndOpi + ", libNomUsuIndOpi=" + libNomUsuIndOpi + ", libPr1IndOpi=" + libPr1IndOpi
				+ ", libPr2IndOpi=" + libPr2IndOpi + ", libPr3IndOpi=" + libPr3IndOpi + ", LibVilNaiEtuOpi="
				+ LibVilNaiEtuOpi + ", codDepPayNai=" + codDepPayNai + ", codTypDepPayNai=" + codTypDepPayNai
				+ ", daaEnsSupOpi=" + daaEnsSupOpi + ", adrMailOpi=" + adrMailOpi + ", numTelPorOpi=" + numTelPorOpi
				+ ", daaEtrSup=" + daaEtrSup + ", codSexEtuOpi=" + codSexEtuOpi + ", codEtbBac=" + codEtbBac
				+ ", codBac=" + codBac + ", codDep=" + codDep + ", codMnb=" + codMnb + ", daabacObtOba=" + daabacObtOba
				+ ", codTpe=" + codTpe + ", numTel=" + numTel + ", codPay=" + codPay + ", codBdi=" + codBdi
				+ ", codCom=" + codCom + ", libAd1=" + libAd1 + ", libAd2=" + libAd2 + ", libAd3=" + libAd3
				+ ", libAde=" + libAde + ", synchro=" + synchro + ", source=" + source + ", voeux=" + voeux
				+ ", codInd=" + codInd + ", codEtuLpa=" + codEtuLpa + ", codIndOpi=" + codIndOpi + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof IndOpi)) return false;

		IndOpi indOpi = (IndOpi) o;

		if (!getNumeroOpi().equals(indOpi.getNumeroOpi())) return false;
		if (getLibEtabDepart() != null ? !getLibEtabDepart().equals(indOpi.getLibEtabDepart()) : indOpi.getLibEtabDepart() != null)
			return false;
		if (getEtabDepart() != null ? !getEtabDepart().equals(indOpi.getEtabDepart()) : indOpi.getEtabDepart() != null)
			return false;
		if (getAnnee() != null ? !getAnnee().equals(indOpi.getAnnee()) : indOpi.getAnnee() != null) return false;
		if (getCodPayNat() != null ? !getCodPayNat().equals(indOpi.getCodPayNat()) : indOpi.getCodPayNat() != null)
			return false;
		if (getCodEtb() != null ? !getCodEtb().equals(indOpi.getCodEtb()) : indOpi.getCodEtb() != null) return false;
		if (getCodNneIndOpi() != null ? !getCodNneIndOpi().equals(indOpi.getCodNneIndOpi()) : indOpi.getCodNneIndOpi() != null)
			return false;
		if (getCodCleNneIndOpi() != null ? !getCodCleNneIndOpi().equals(indOpi.getCodCleNneIndOpi()) : indOpi.getCodCleNneIndOpi() != null)
			return false;
		if (!getDateNaiIndOpi().equals(indOpi.getDateNaiIndOpi())) return false;
		if (!getTemDateNaiRelOpi().equals(indOpi.getTemDateNaiRelOpi())) return false;
		if (getDaaEntEtbOpi() != null ? !getDaaEntEtbOpi().equals(indOpi.getDaaEntEtbOpi()) : indOpi.getDaaEntEtbOpi() != null)
			return false;
		if (!getLibNomPatIndOpi().equals(indOpi.getLibNomPatIndOpi())) return false;
		if (getLibNomUsuIndOpi() != null ? !getLibNomUsuIndOpi().equals(indOpi.getLibNomUsuIndOpi()) : indOpi.getLibNomUsuIndOpi() != null)
			return false;
		if (!getLibPr1IndOpi().equals(indOpi.getLibPr1IndOpi())) return false;
		if (getLibPr2IndOpi() != null ? !getLibPr2IndOpi().equals(indOpi.getLibPr2IndOpi()) : indOpi.getLibPr2IndOpi() != null)
			return false;
		if (getLibPr3IndOpi() != null ? !getLibPr3IndOpi().equals(indOpi.getLibPr3IndOpi()) : indOpi.getLibPr3IndOpi() != null)
			return false;
		if (getLibVilNaiEtuOpi() != null ? !getLibVilNaiEtuOpi().equals(indOpi.getLibVilNaiEtuOpi()) : indOpi.getLibVilNaiEtuOpi() != null)
			return false;
		if (getCodDepPayNai() != null ? !getCodDepPayNai().equals(indOpi.getCodDepPayNai()) : indOpi.getCodDepPayNai() != null)
			return false;
		if (getCodTypDepPayNai() != null ? !getCodTypDepPayNai().equals(indOpi.getCodTypDepPayNai()) : indOpi.getCodTypDepPayNai() != null)
			return false;
		if (getDaaEnsSupOpi() != null ? !getDaaEnsSupOpi().equals(indOpi.getDaaEnsSupOpi()) : indOpi.getDaaEnsSupOpi() != null)
			return false;
		if (getAdrMailOpi() != null ? !getAdrMailOpi().equals(indOpi.getAdrMailOpi()) : indOpi.getAdrMailOpi() != null)
			return false;
		if (getNumTelPorOpi() != null ? !getNumTelPorOpi().equals(indOpi.getNumTelPorOpi()) : indOpi.getNumTelPorOpi() != null)
			return false;
		if (getDaaEtrSup() != null ? !getDaaEtrSup().equals(indOpi.getDaaEtrSup()) : indOpi.getDaaEtrSup() != null)
			return false;
		if (getCodSexEtuOpi() != null ? !getCodSexEtuOpi().equals(indOpi.getCodSexEtuOpi()) : indOpi.getCodSexEtuOpi() != null)
			return false;
		if (getCodEtbBac() != null ? !getCodEtbBac().equals(indOpi.getCodEtbBac()) : indOpi.getCodEtbBac() != null)
			return false;
		if (getCodBac() != null ? !getCodBac().equals(indOpi.getCodBac()) : indOpi.getCodBac() != null) return false;
		if (getCodDep() != null ? !getCodDep().equals(indOpi.getCodDep()) : indOpi.getCodDep() != null) return false;
		if (getCodMnb() != null ? !getCodMnb().equals(indOpi.getCodMnb()) : indOpi.getCodMnb() != null) return false;
		if (getDaabacObtOba() != null ? !getDaabacObtOba().equals(indOpi.getDaabacObtOba()) : indOpi.getDaabacObtOba() != null)
			return false;
		if (getCodTpe() != null ? !getCodTpe().equals(indOpi.getCodTpe()) : indOpi.getCodTpe() != null) return false;
		if (getNumTel() != null ? !getNumTel().equals(indOpi.getNumTel()) : indOpi.getNumTel() != null) return false;
		if (getCodPay() != null ? !getCodPay().equals(indOpi.getCodPay()) : indOpi.getCodPay() != null) return false;
		if (getCodBdi() != null ? !getCodBdi().equals(indOpi.getCodBdi()) : indOpi.getCodBdi() != null) return false;
		if (getCodCom() != null ? !getCodCom().equals(indOpi.getCodCom()) : indOpi.getCodCom() != null) return false;
		if (getLibAd1() != null ? !getLibAd1().equals(indOpi.getLibAd1()) : indOpi.getLibAd1() != null) return false;
		if (getLibAd2() != null ? !getLibAd2().equals(indOpi.getLibAd2()) : indOpi.getLibAd2() != null) return false;
		if (getLibAd3() != null ? !getLibAd3().equals(indOpi.getLibAd3()) : indOpi.getLibAd3() != null) return false;
		if (getLibAde() != null ? !getLibAde().equals(indOpi.getLibAde()) : indOpi.getLibAde() != null) return false;
		if (!getSynchro().equals(indOpi.getSynchro())) return false;
		if (!getSource().equals(indOpi.getSource())) return false;
		if (getVoeux() != null ? !getVoeux().equals(indOpi.getVoeux()) : indOpi.getVoeux() != null) return false;
		if (getCodInd() != null ? !getCodInd().equals(indOpi.getCodInd()) : indOpi.getCodInd() != null) return false;
		if (getCodEtuLpa() != null ? !getCodEtuLpa().equals(indOpi.getCodEtuLpa()) : indOpi.getCodEtuLpa() != null)
			return false;
		return getCodIndOpi() != null ? getCodIndOpi().equals(indOpi.getCodIndOpi()) : indOpi.getCodIndOpi() == null;

	}

	@Override
	public int hashCode() {
		int result = getNumeroOpi().hashCode();
		result = 31 * result + (getLibEtabDepart() != null ? getLibEtabDepart().hashCode() : 0);
		result = 31 * result + (getEtabDepart() != null ? getEtabDepart().hashCode() : 0);
		result = 31 * result + (getAnnee() != null ? getAnnee().hashCode() : 0);
		result = 31 * result + (getCodPayNat() != null ? getCodPayNat().hashCode() : 0);
		result = 31 * result + (getCodEtb() != null ? getCodEtb().hashCode() : 0);
		result = 31 * result + (getCodNneIndOpi() != null ? getCodNneIndOpi().hashCode() : 0);
		result = 31 * result + (getCodCleNneIndOpi() != null ? getCodCleNneIndOpi().hashCode() : 0);
		result = 31 * result + getDateNaiIndOpi().hashCode();
		result = 31 * result + getTemDateNaiRelOpi().hashCode();
		result = 31 * result + (getDaaEntEtbOpi() != null ? getDaaEntEtbOpi().hashCode() : 0);
		result = 31 * result + getLibNomPatIndOpi().hashCode();
		result = 31 * result + (getLibNomUsuIndOpi() != null ? getLibNomUsuIndOpi().hashCode() : 0);
		result = 31 * result + getLibPr1IndOpi().hashCode();
		result = 31 * result + (getLibPr2IndOpi() != null ? getLibPr2IndOpi().hashCode() : 0);
		result = 31 * result + (getLibPr3IndOpi() != null ? getLibPr3IndOpi().hashCode() : 0);
		result = 31 * result + (getLibVilNaiEtuOpi() != null ? getLibVilNaiEtuOpi().hashCode() : 0);
		result = 31 * result + (getCodDepPayNai() != null ? getCodDepPayNai().hashCode() : 0);
		result = 31 * result + (getCodTypDepPayNai() != null ? getCodTypDepPayNai().hashCode() : 0);
		result = 31 * result + (getDaaEnsSupOpi() != null ? getDaaEnsSupOpi().hashCode() : 0);
		result = 31 * result + (getAdrMailOpi() != null ? getAdrMailOpi().hashCode() : 0);
		result = 31 * result + (getNumTelPorOpi() != null ? getNumTelPorOpi().hashCode() : 0);
		result = 31 * result + (getDaaEtrSup() != null ? getDaaEtrSup().hashCode() : 0);
		result = 31 * result + (getCodSexEtuOpi() != null ? getCodSexEtuOpi().hashCode() : 0);
		result = 31 * result + (getCodEtbBac() != null ? getCodEtbBac().hashCode() : 0);
		result = 31 * result + (getCodBac() != null ? getCodBac().hashCode() : 0);
		result = 31 * result + (getCodDep() != null ? getCodDep().hashCode() : 0);
		result = 31 * result + (getCodMnb() != null ? getCodMnb().hashCode() : 0);
		result = 31 * result + (getDaabacObtOba() != null ? getDaabacObtOba().hashCode() : 0);
		result = 31 * result + (getCodTpe() != null ? getCodTpe().hashCode() : 0);
		result = 31 * result + (getNumTel() != null ? getNumTel().hashCode() : 0);
		result = 31 * result + (getCodPay() != null ? getCodPay().hashCode() : 0);
		result = 31 * result + (getCodBdi() != null ? getCodBdi().hashCode() : 0);
		result = 31 * result + (getCodCom() != null ? getCodCom().hashCode() : 0);
		result = 31 * result + (getLibAd1() != null ? getLibAd1().hashCode() : 0);
		result = 31 * result + (getLibAd2() != null ? getLibAd2().hashCode() : 0);
		result = 31 * result + (getLibAd3() != null ? getLibAd3().hashCode() : 0);
		result = 31 * result + (getLibAde() != null ? getLibAde().hashCode() : 0);
		result = 31 * result + getSynchro().hashCode();
		result = 31 * result + getSource().hashCode();
		result = 31 * result + (getVoeux() != null ? getVoeux().hashCode() : 0);
		result = 31 * result + (getCodInd() != null ? getCodInd().hashCode() : 0);
		result = 31 * result + (getCodEtuLpa() != null ? getCodEtuLpa().hashCode() : 0);
		result = 31 * result + (getCodIndOpi() != null ? getCodIndOpi().hashCode() : 0);
		return result;
	}

	public String getNumeroOpi() {
		return numeroOpi;
	}

	public void setNumeroOpi(String numeroOpi) {
		this.numeroOpi = numeroOpi;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setVoeux(VoeuxIns voeux) {
		this.voeux = voeux;
	}

	public VoeuxIns getVoeux() {
		return voeux;
	}

	public void setCodPayNat(String codPayNat) {
		this.codPayNat = codPayNat;
	}

	public String getCodPayNat() {
		return codPayNat;
	}

	public void setCodEtb(String codEtb) {
		this.codEtb = codEtb;
	}

	public String getCodEtb() {
		return codEtb;
	}

	public void setCodNneIndOpi(String codNneIndOpi) {
		this.codNneIndOpi = codNneIndOpi;
	}

	public String getCodNneIndOpi() {
		return codNneIndOpi;
	}

	public void setCodCleNneIndOpi(String codCleNneIndOpi) {
		this.codCleNneIndOpi = codCleNneIndOpi;
	}

	public String getCodCleNneIndOpi() {
		return codCleNneIndOpi;
	}

	public void setDateNaiIndOpi(Date dateNaiIndOpi) {
		this.dateNaiIndOpi = dateNaiIndOpi;
	}

	public Date getDateNaiIndOpi() {
		return dateNaiIndOpi;
	}

	public void setTemDateNaiRelOpi(String temDateNaiRelOpi) {
		this.temDateNaiRelOpi = temDateNaiRelOpi;
	}

	public String getTemDateNaiRelOpi() {
		return temDateNaiRelOpi;
	}

	public void setDaaEntEtbOpi(String daaEntEtbOpi) {
		this.daaEntEtbOpi = daaEntEtbOpi;
	}

	public String getDaaEntEtbOpi() {
		return daaEntEtbOpi;
	}

	public void setLibNomPatIndOpi(String libNomPatIndOpi) {
		this.libNomPatIndOpi = libNomPatIndOpi;
	}

	public String getLibNomPatIndOpi() {
		return libNomPatIndOpi;
	}

	public void setLibNomUsuIndOpi(String libNomUsuIndOpi) {
		this.libNomUsuIndOpi = libNomUsuIndOpi;
	}

	public String getLibNomUsuIndOpi() {
		return libNomUsuIndOpi;
	}

	public void setLibPr1IndOpi(String libPr1IndOpi) {
		this.libPr1IndOpi = libPr1IndOpi;
	}

	public String getLibPr1IndOpi() {
		return libPr1IndOpi;
	}

	public void setLibPr2IndOpi(String libPr2IndOpi) {
		this.libPr2IndOpi = libPr2IndOpi;
	}

	public String getLibPr2IndOpi() {
		return libPr2IndOpi;
	}

	public void setLibPr3IndOpi(String libPr3IndOpi) {
		this.libPr3IndOpi = libPr3IndOpi;
	}

	public String getLibPr3IndOpi() {
		return libPr3IndOpi;
	}

	public void setLibVilNaiEtuOpi(String libVilNaiEtuOpi) {
		LibVilNaiEtuOpi = libVilNaiEtuOpi;
	}

	public String getLibVilNaiEtuOpi() {
		return LibVilNaiEtuOpi;
	}

	public void setCodDepPayNai(String codDepPayNai) {
		this.codDepPayNai = codDepPayNai;
	}

	public String getCodDepPayNai() {
		return codDepPayNai;
	}

	public void setCodTypDepPayNai(String codTypDepPayNai) {
		this.codTypDepPayNai = codTypDepPayNai;
	}

	public String getCodTypDepPayNai() {
		return codTypDepPayNai;
	}

	public void setDaaEnsSupOpi(String daaEnsSupOpi) {
		this.daaEnsSupOpi = daaEnsSupOpi;
	}

	public String getDaaEnsSupOpi() {
		return daaEnsSupOpi;
	}

	public void setAdrMailOpi(String adrMailOpi) {
		this.adrMailOpi = adrMailOpi;
	}

	public String getAdrMailOpi() {
		return adrMailOpi;
	}

	public void setNumTelPorOpi(String numTelPorOpi) {
		this.numTelPorOpi = numTelPorOpi;
	}

	public String getNumTelPorOpi() {
		return numTelPorOpi;
	}

	public void setCodSexEtuOpi(String codSexEtuOpi) {
		this.codSexEtuOpi = codSexEtuOpi;
	}

	public String getCodSexEtuOpi() {
		return codSexEtuOpi;
	}

	public void setDaaEtrSup(String daaEtrSup) {
		this.daaEtrSup = daaEtrSup;
	}

	public String getDaaEtrSup() {
		return daaEtrSup;
	}

	public void setCodEtbBac(String codEtbBac) {
		this.codEtbBac = codEtbBac;
	}

	public String getCodEtbBac() {
		return codEtbBac;
	}

	public void setCodBac(String codBac) {
		this.codBac = codBac;
	}

	public String getCodBac() {
		return codBac;
	}

	public void setCodDep(String codDep) {
		this.codDep = codDep;
	}

	public String getCodDep() {
		return codDep;
	}

	public void setCodMnb(String codMnb) {
		this.codMnb = codMnb;
	}

	public String getCodMnb() {
		return codMnb;
	}

	public void setDaabacObtOba(String daabacObtOba) {
		this.daabacObtOba = daabacObtOba;
	}

	public String getDaabacObtOba() {
		return daabacObtOba;
	}

	public void setCodTpe(String codTpe) {
		this.codTpe = codTpe;
	}

	public String getCodTpe() {
		return codTpe;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setCodPay(String codPay) {
		this.codPay = codPay;
	}

	public String getCodPay() {
		return codPay;
	}

	public void setCodBdi(String codBdi) {
		this.codBdi = codBdi;
	}

	public String getCodBdi() {
		return codBdi;
	}

	public void setCodCom(String codCom) {
		this.codCom = codCom;
	}

	public String getCodCom() {
		return codCom;
	}

	public void setLibAd1(String libAd1) {
		this.libAd1 = libAd1;
	}

	public String getLibAd1() {
		return libAd1;
	}

	public void setLibAd2(String libAd2) {
		this.libAd2 = libAd2;
	}

	public String getLibAd2() {
		return libAd2;
	}

	public void setLibAd3(String libAd3) {
		this.libAd3 = libAd3;
	}

	public String getLibAd3() {
		return libAd3;
	}

	public void setLibAde(String libAde) {
		this.libAde = libAde;
	}

	public String getLibAde() {
		return libAde;
	}

	public void setEtabDepart(String etabDepart) {
		this.etabDepart = etabDepart;
	}

	public String getEtabDepart() {
		return etabDepart;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public Integer getAnnee() {
		return annee;
	}

	public void setLibEtabDepart(String libEtabDepart) {
		this.libEtabDepart = libEtabDepart;
	}

	public String getLibEtabDepart() {
		return libEtabDepart;
	}

	public Integer getSynchro() {
		return synchro;
	}

	public void setSynchro(Integer synchro) {
		this.synchro = synchro;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getCodIndOpi() {
		return codIndOpi;
	}

	public void setCodIndOpi(Integer codIndOpi) {
		this.codIndOpi = codIndOpi;
	}

	public Integer getCodEtuLpa() {
		return codEtuLpa;
	}

	public void setCodEtuLpa(Integer codEtuLpa) {
		this.codEtuLpa = codEtuLpa;
	}

	public Integer getCodInd() {
		return codInd;
	}

	public void setCodInd(Integer codInd) {
		this.codInd = codInd;
	}
}