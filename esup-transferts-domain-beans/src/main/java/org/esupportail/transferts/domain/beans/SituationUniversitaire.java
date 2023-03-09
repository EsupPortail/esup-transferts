package org.esupportail.transferts.domain.beans;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Farid AIT KARRA : farid.aitkarra@univ-artois.fr
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "getSituationUniversitaireByNumeroEtudiantAndAnnee", query = "select situationUniversitaire FROM SituationUniversitaire situationUniversitaire WHERE situationUniversitaire.infosAccueil.numeroEtudiant = :numeroEtudiant and situationUniversitaire.infosAccueil.annee = :annee")
	})
@Table(name="SITUATION_UNIVERSITAIRE")
public class SituationUniversitaire implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 987654321L;

	@Id
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SITUATION_UNIV")
//	@SequenceGenerator(name="SITUATION_UNIV", sequenceName="SITUATION_UNIV", allocationSize=1)
	private String id;		

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "numeroEtudiant", referencedColumnName = "numeroEtudiant"),
		@JoinColumn(name = "annee", referencedColumnName = "annee") })
	private InfosAccueil infosAccueil;	

	/**
	 * Annee de la situation universitaire
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumns({
			@JoinColumn(name = "idAccueilAnnee", referencedColumnName = "ID_ACCUEIL_ANNEE")})
	private AccueilAnnee annee = new AccueilAnnee();

	/**
	 * Libelle de la situation universitaire
	 */
	@Column(name = "libelle")
	private String libelle;			

	/**
	 * Resultat de la situation universitaire
	 */
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumns({
			@JoinColumn(name = "idAccueilResultat", referencedColumnName = "ID_ACCUEIL_RESULTAT")})
	private AccueilResultat resultat = new AccueilResultat();	

	/**
	 * Libelle de l'annee
	 * issus des etablissements partenaires
	 */
	@Column(name = "LIB_ACCUEIL_ANNEE")
	private String libAccueilAnnee;

	/**
	 * Libelle du resultat
	 * issus des etablissements partenaires
	 */
	@Column(name = "LIB_ACCUEIL_RESULTAT")
	private String libAccueilResultat;
	
	/**
	 * Constructeur
	 */
	public SituationUniversitaire(){
		super();
	}

//	@Override
//	public String toString() {
//		return "SituationUniversitaire [id=" + id + ", infosAccueil="
//				+ infosAccueil + ", annee=" + annee + ", libelle=" + libelle
//				+ ", resultat=" + resultat + ", libAccueilAnnee="
//				+ libAccueilAnnee + ", libAccueilResultat="
//				+ libAccueilResultat + "]";
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AccueilAnnee getAnnee() {
		return annee;
	}

	public void setAnnee(AccueilAnnee annee) {
		this.annee = annee;
	}

	public AccueilResultat getResultat() {
		return resultat;
	}

	public void setResultat(AccueilResultat resultat) {
		this.resultat = resultat;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public InfosAccueil getInfosAccueil() {
		return infosAccueil;
	}

	public void setInfosAccueil(InfosAccueil infosAccueil) {
		this.infosAccueil = infosAccueil;
	}

	public String getLibAccueilAnnee() {
		return libAccueilAnnee;
	}

	public void setLibAccueilAnnee(String libAccueilAnnee) {
		this.libAccueilAnnee = libAccueilAnnee;
	}

	public String getLibAccueilResultat() {
		return libAccueilResultat;
	}

	public void setLibAccueilResultat(String libAccueilResultat) {
		this.libAccueilResultat = libAccueilResultat;
	}


}
