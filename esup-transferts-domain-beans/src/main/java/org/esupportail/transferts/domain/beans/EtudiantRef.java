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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Farid AIT KARRA : farid.aitkarra@univ-artois.fr
 * 
 */
@Entity
@IdClass(EtudiantRefPK.class)
@NamedQueries({
	
	
		@NamedQuery(name = "getDemandeTransfertByAnneeAndNumeroIneAndSource", query = "SELECT etu FROM EtudiantRef etu LEFT JOIN FETCH etu.adresse LEFT JOIN FETCH etu.transferts LEFT JOIN FETCH etu.accueil LEFT JOIN FETCH etu.accueil.situationUniversitaire LEFT JOIN FETCH etu.accueilDecision LEFT JOIN FETCH etu.transferts.odf LEFT JOIN FETCH etu.transferts.fichier LEFT JOIN FETCH etu.correspondances WHERE etu.numeroIne = :numeroIne AND etu.annee = :annee"),
		@NamedQuery(name = "getDemandeTransfertByAnneeAndNumeroIneAndSource2", query = "SELECT etu FROM EtudiantRef etu LEFT JOIN FETCH etu.adresse LEFT JOIN FETCH etu.transferts LEFT JOIN FETCH etu.accueil LEFT JOIN FETCH etu.accueil.situationUniversitaire LEFT JOIN FETCH etu.accueilDecision LEFT JOIN FETCH etu.transferts.odf LEFT JOIN FETCH etu.transferts.fichier LEFT JOIN FETCH etu.correspondances WHERE etu.numeroIne = :numeroIne AND etu.annee = :annee AND etu.source = :source"),
		@NamedQuery(name = "getAccueilDecisionByNumeroEtudiantAndAnnee", query = "select etu from EtudiantRef etu LEFT JOIN FETCH etu.accueilDecision WHERE etu.numeroEtudiant =:numeroEtudiant and etu.annee =:annee"),
		@NamedQuery(name = "allDemandesTransfertsByAnnee", query = "SELECT DISTINCT etu FROM EtudiantRef etu LEFT JOIN FETCH etu.adresse LEFT JOIN FETCH etu.transferts LEFT JOIN FETCH etu.accueil LEFT JOIN FETCH etu.accueil.situationUniversitaire LEFT JOIN FETCH etu.accueilDecision LEFT JOIN FETCH etu.transferts.odf WHERE etu.annee = :annee AND etu.source = :source ORDER BY etu.transferts.temoinTransfertValide ASC"),
		@NamedQuery(name = "allDemandesTransfertsByAnneeAndNonTraite", query = "SELECT DISTINCT etu FROM EtudiantRef etu LEFT JOIN FETCH etu.adresse LEFT JOIN FETCH etu.transferts LEFT JOIN FETCH etu.accueil LEFT JOIN FETCH etu.accueil.situationUniversitaire LEFT JOIN FETCH etu.accueilDecision LEFT JOIN FETCH etu.transferts.odf WHERE etu.annee = :annee AND etu.source = :source AND (etu.transferts.temoinTransfertValide = 0 OR etu.transferts.temoinTransfertValide = 1 OR etu.transferts.temoinOPIWs = 2) ORDER BY etu.transferts.dateDemandeTransfert ASC"),
		@NamedQuery(name = "getDemandeTransfert", query = "SELECT etu FROM EtudiantRef etu WHERE etu.numeroEtudiant = :numeroEtudiant"),
		@NamedQuery(name = "getDemandeTransfertByAnneeAndNumeroEtudiantAndSource", query = "SELECT etu FROM EtudiantRef etu LEFT JOIN FETCH etu.adresse LEFT JOIN FETCH etu.transferts LEFT JOIN FETCH etu.accueil LEFT JOIN FETCH etu.accueilDecision LEFT JOIN FETCH etu.transferts.odf LEFT JOIN FETCH etu.transferts.fichier LEFT JOIN FETCH etu.correspondances WHERE etu.numeroEtudiant = :numeroEtudiant AND etu.annee = :annee AND etu.source = :source"),
		@NamedQuery(name = "getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance", query = "SELECT etu FROM EtudiantRef etu LEFT JOIN FETCH etu.adresse LEFT JOIN FETCH etu.transferts LEFT JOIN FETCH etu.accueil LEFT JOIN FETCH etu.accueilDecision LEFT JOIN FETCH etu.transferts.odf LEFT JOIN FETCH etu.transferts.fichier WHERE etu.numeroEtudiant = :numeroEtudiant AND etu.annee = :annee AND etu.source = :source"),
		@NamedQuery(name = "getListeAnnees", query = "SELECT DISTINCT etu.annee FROM EtudiantRef etu"),
		@NamedQuery(name = "getDemandesTransfertsByEnCoursAndAnnee", query = "SELECT etu FROM EtudiantRef etu WHERE etu.transferts.temoinTransfertValide = 0 AND etu.annee = :annee AND etu.source = :source"),
		@NamedQuery(name = "getDemandesTransfertsByAvisSaisieAndAnnee", query = "SELECT etu FROM EtudiantRef etu WHERE etu.transferts.temoinTransfertValide = 1 AND etu.annee = :annee AND etu.source = :source"),
		@NamedQuery(name = "getDemandesTransfertsByTerminerAndAnnee", query = "SELECT etu FROM EtudiantRef etu WHERE etu.transferts.temoinTransfertValide = 2 AND etu.annee = :annee AND etu.source = :source"),
		@NamedQuery(name = "getDemandesTransfertsByPbOpiAndAnnee", query = "SELECT etu FROM EtudiantRef etu WHERE etu.transferts.temoinOPIWs = 2 AND etu.annee = :annee"),
		@NamedQuery(name = "getDemandesTransfertsByEtabNonPartenaireAndAnnee", query = "SELECT etu FROM EtudiantRef etu WHERE etu.transferts.temoinOPIWs = 0 AND etu.annee = :annee"),
		@NamedQuery(name = "getDemandesTransfertsByOpiOkAndAnnee", query = "SELECT etu FROM EtudiantRef etu WHERE etu.transferts.temoinOPIWs = 1 AND etu.annee = :annee"),
		@NamedQuery(name = "getPresenceEtudiantRefByIneAndAnnee", query = "SELECT etu FROM EtudiantRef etu WHERE etu.numeroIne=:numeroIne AND etu.annee=:annee"),
		@NamedQuery(name = "getStatistiquesTransfertDepart", query = "select etu.transferts.rne, count(etu.transferts.rne) from EtudiantRef etu where etu.source = :source AND etu.annee = :annee group by cod_etb"),
		@NamedQuery(name = "getStatistiquesTransfertAccueil", query = "select etu.accueil.codeRneUnivDepart, count(etu.accueil.codeRneUnivDepart) from EtudiantRef etu where etu.annee = :annee and etu.source = :source group by etu.accueil.codeRneUnivDepart"),
		@NamedQuery(name = "getStatistiquesNombreTotalTransfertDepart", query = "select count(etu.numeroEtudiant) from EtudiantRef etu where etu.annee = :annee and etu.source='D'"),
		@NamedQuery(name = "getStatistiquesNombreTotalTransfertAccueil", query = "select count(etu.numeroEtudiant) from EtudiantRef etu where etu.annee = :annee and etu.source='A'")
		})
@XmlRootElement(name = "ETUDIANTREF")
//@XMLACCESSORTYPE()
//@Table(name = "EtudiantRef")
@Table(name = "ETUDIANTREF")
public class EtudiantRef implements Serializable {
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 123456789L;

	@Transient
	private String from;

	@Transient
	private boolean interdit = false;

	@Transient
	private boolean interditLocal = false;

	@Transient
	private List<TrBlocageDTO> listeBlocagesDTO = new ArrayList<TrBlocageDTO>();

	@Id
	@Column(name = "numeroEtudiant")
	private String numeroEtudiant;

	@Id
	@Column(name = "annee", length = 4)
	private Integer annee;

	@Column(name = "numeroIne", nullable = false, length = 11)
	private String numeroIne;

	@Column(name = "nomPatronymique", nullable = false, length = 30)
	private String nomPatronymique;

	@Column(name = "nomUsuel", nullable = true, length = 30)
	private String nomUsuel;

	@Column(name = "prenom1", nullable = false, length = 20)
	private String prenom1;

	@Column(name = "prenom2", nullable = true, length = 20)
	private String prenom2;

	@Temporal(TemporalType.DATE)
	private Date dateNaissance;

	@Column(name = "nationalite", nullable = true)
	private String libNationalite;

	@Column(name = "composante", nullable = true, length = 100)
	private String composante;

	@Column(name = "LIB_ETP_PRE", nullable = true, length = 255)
	private String libEtapePremiereLocal;

	@Column(name = "SOURCE", nullable = false, length = 1)
	private String source;	
	
	@OneToOne(optional=false, fetch=FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumns({
			@JoinColumn(name = "numeroEtudiant", referencedColumnName = "numeroEtudiant"),
			@JoinColumn(name = "annee", referencedColumnName = "annee") })
	private AdresseRef adresse = new AdresseRef();

	@OneToOne(optional=false, fetch=FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumns({
			@JoinColumn(name = "numeroEtudiant", referencedColumnName = "numeroEtudiant"),
			@JoinColumn(name = "annee", referencedColumnName = "annee") })
	private Transferts transferts = new Transferts();

	@OneToOne(optional=true, fetch=FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumns({
			@JoinColumn(name = "numeroEtudiant", referencedColumnName = "numeroEtudiant"),
			@JoinColumn(name = "annee", referencedColumnName = "annee") })
	private InfosAccueil accueil;

	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL })
	@JoinColumns({
		@JoinColumn(name = "numeroEtudiant", referencedColumnName = "numeroEtudiant"),
		@JoinColumn(name = "annee", referencedColumnName = "annee") })	
	private Set<AccueilDecision> accueilDecision;	
	
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL })
	@JoinColumns({
		@JoinColumn(name = "numeroEtudiant", referencedColumnName = "numeroEtudiant"),
		@JoinColumn(name = "annee", referencedColumnName = "annee") })	
	private Set<Correspondance> correspondances;			

	/*0 ==> n'existe pas dans la bdd scolarite
	 *1 ==> existe dans la bdd scolaritE*/
	@Transient
	Integer bddScol;

	@Transient
	private String codCge;	

	@Transient
	private String libCge;		

	@Transient
	private String libComposante;	
	
	@Transient
	private long dateDeLaDemande;	
	
	@Transient
	private Date alertSilenceVautAccord;		
	
	@Transient
	private Date alertDepassementSilenceVautAccord;	
	
	/**
	 * Constructeur
	 */
	public EtudiantRef() {
		super();
	}

	@Override
	public String toString() {
		return "EtudiantRef [from=" + from + ", interdit=" + interdit
				+ ", interditLocal=" + interditLocal + ", listeBlocagesDTO="
				+ listeBlocagesDTO + ", numeroEtudiant=" + numeroEtudiant
				+ ", annee=" + annee + ", numeroIne=" + numeroIne
				+ ", nomPatronymique=" + nomPatronymique + ", nomUsuel="
				+ nomUsuel + ", prenom1=" + prenom1 + ", prenom2=" + prenom2
				+ ", dateNaissance=" + dateNaissance + ", libNationalite="
				+ libNationalite + ", composante=" + composante
				+ ", libEtapePremiereLocal=" + libEtapePremiereLocal
				+ ", source=" + source + ", adresse=" + adresse
				+ ", transferts=" + transferts + ", accueil=" + accueil
				+ ", accueilDecision=" + accueilDecision + ", bddScol="
				+ bddScol + ", codCge=" + codCge + ", libCge=" + libCge
				+ ", libComposante=" + libComposante + ", dateDeLaDemande="
				+ dateDeLaDemande + ", alertSilenceVautAccord="
				+ alertSilenceVautAccord
				+ ", alertDepassementSilenceVautAccord="
				+ alertDepassementSilenceVautAccord + "]";
	}

	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}

	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}

	public void setNumeroIne(String numeroIne) {
		this.numeroIne = numeroIne;
	}

	public String getNumeroIne() {
		return numeroIne;
	}

	public void setNomPatronymique(String nomPatronymique) {
		this.nomPatronymique = nomPatronymique;
	}

	public String getNomPatronymique() {
		return nomPatronymique;
	}

	public void setNomUsuel(String nomUsuel) {
		this.nomUsuel = nomUsuel;
	}

	public String getNomUsuel() {
		return nomUsuel;
	}

	public void setPrenom1(String prenom1) {
		this.prenom1 = prenom1;
	}

	public String getPrenom1() {
		return prenom1;
	}

	public void setPrenom2(String prenom2) {
		this.prenom2 = prenom2;
	}

	public String getPrenom2() {
		return prenom2;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setAdresse(AdresseRef adresse) {
		this.adresse = adresse;
	}

	public AdresseRef getAdresse() {
		return adresse;
	}

	public void setInterdit(boolean interdit) {
		this.interdit = interdit;
	}

	public boolean isInterdit() {
		return interdit;
	}

	public List<TrBlocageDTO> getListeBlocagesDTO() {
		return listeBlocagesDTO;
	}

	public void setListeBlocagesDTO(List<TrBlocageDTO> listeBlocagesDTO) {
		this.listeBlocagesDTO = listeBlocagesDTO;
	}

	public Set<Correspondance> getCorrespondances() {
		return correspondances;
	}

	public void setCorrespondances(Set<Correspondance> correspondances) {
		this.correspondances = correspondances;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFrom() {
		return from;
	}

	public void setTransferts(Transferts transferts) {
		this.transferts = transferts;
	}

	public Transferts getTransferts() {
		return transferts;
	}

	public void setLibNationalite(String libNationalite) {
		this.libNationalite = libNationalite;
	}

	public String getLibNationalite() {
		return libNationalite;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public Date getAlertDepassementSilenceVautAccord() {
		return alertDepassementSilenceVautAccord;
	}

	public void setAlertDepassementSilenceVautAccord(
			Date alertDepassementSilenceVautAccord) {
		this.alertDepassementSilenceVautAccord = alertDepassementSilenceVautAccord;
	}

	public Date getAlertSilenceVautAccord() {
		return alertSilenceVautAccord;
	}

	public void setAlertSilenceVautAccord(Date alertSilenceVautAccord) {
		this.alertSilenceVautAccord = alertSilenceVautAccord;
	}

	public long getDateDeLaDemande() {
		return dateDeLaDemande;
	}

	public void setDateDeLaDemande(long dateDeLaDemande) {
		this.dateDeLaDemande = dateDeLaDemande;
	}

	public Integer getAnnee() {
		return annee;
	}

	public String getComposante() {
		return composante;
	}

	public void setComposante(String composante) {
		this.composante = composante;
	}

	public void setInterditLocal(boolean interditLocal) {
		this.interditLocal = interditLocal;
	}

	public boolean isInterditLocal() {
		return interditLocal;
	}

	public String getLibEtapePremiereLocal() {
		return libEtapePremiereLocal;
	}

	public void setLibEtapePremiereLocal(String libEtapePremiereLocal) {
		this.libEtapePremiereLocal = libEtapePremiereLocal;
	}
	
	public Integer getBddScol() {
		return bddScol;
	}

	public void setBddScol(Integer bddScol) {
		this.bddScol = bddScol;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public InfosAccueil getAccueil() {
		return accueil;
	}

	public void setAccueil(InfosAccueil accueil) {
		this.accueil = accueil;
	}

	public Set<AccueilDecision> getAccueilDecision() {
		return accueilDecision;
	}

	public void setAccueilDecision(Set<AccueilDecision> accueilDecision) {
		this.accueilDecision = accueilDecision;
	}

	public String getCodCge() {
		return codCge;
	}

	public void setCodCge(String codCge) {
		this.codCge = codCge;
	}

	public String getLibCge() {
		return libCge;
	}

	public void setLibCge(String libCge) {
		this.libCge = libCge;
	}

	public String getLibComposante() {
		return libComposante;
	}

	public void setLibComposante(String libComposante) {
		this.libComposante = libComposante;
	}
}
