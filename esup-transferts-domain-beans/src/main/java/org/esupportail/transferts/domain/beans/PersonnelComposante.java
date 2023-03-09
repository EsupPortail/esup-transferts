/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esupportail.transferts.domain.beans.PersonnelComposante;


/**
 * The class that represent users.
 */
@Entity
@NamedQueries({
	@NamedQuery(
			name="getListePersonnelsComposantesBySourceAndAnnee",
			query="SELECT pc FROM PersonnelComposante pc WHERE pc.source = :source AND pc.annee = :annee)"
			),	
	@NamedQuery(
			name="getListeComposantesByUidAndSourceAndAnnee",
			query="SELECT pc FROM PersonnelComposante pc WHERE pc.uid = :uid AND pc.source = :source AND pc.annee = :annee)"
			),
	@NamedQuery(
			name="getListeManagersBySourceAndAnnee",
			query="SELECT DISTINCT pc.uid, pc.displayName FROM PersonnelComposante pc WHERE pc.source = :source AND pc.annee = :annee)"
			),
	@NamedQuery(
			name="deleteListeManagersByUidAndSourceAndAnnee",
			query="delete FROM PersonnelComposante pc WHERE pc.uid = :uid AND pc.source = :source AND pc.annee = :annee"
			),
	@NamedQuery(
			name="getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante",
			query="SELECT pc FROM PersonnelComposante pc WHERE pc.uid = :uid AND pc.source = :source AND pc.annee = :annee AND pc.codeComposante = :codeComposante)"
			),
		@NamedQuery(
				name="getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante",
				query="SELECT pc FROM PersonnelComposante pc WHERE pc.source = :source AND pc.annee = :annee AND pc.codeComposante = :codeComposante)"
		)
})
@IdClass(PersonnelComposantePK.class)
@Table(name = "PERSONNEL_COMPOSANTE")
public class PersonnelComposante implements Serializable, Cloneable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 1234532897404494181L;

	public Object clone()throws CloneNotSupportedException{
		return super.clone();
	}

	@Id
	private String uid;	

	@Id
	private String codeComposante;		

	@Id
	private String source;	
	
	@Id
	private Integer annee;		

	@Column(name = "DISPLAY_NAME", nullable=false)
	private String displayName;
	
	@Column(name = "LIB_CMP", nullable=false)
	private String libelleComposante;
	/*
	 * 0 ==> Transferts départ uniquement
	 * 1 ==> Personnel de scolarite
	 * 2 ==> Autres
	 * */
	@Column(name = "TYPE_PERSONNEL", nullable=false)
	private Integer typePersonnel;

	@Column(name = "MAIL", nullable=true)
	private String mailPersonnel;

	@Transient
	private String libelleTypePersonnel;
	
	@Column(name = "DROIT_SUPPRESSION", nullable=false)	
	private String droitSuppression;	

	@Column(name = "DROIT_EDITION_PDF", nullable=false)	
	private String droitEditionPdf;
	
	@Column(name = "DROIT_AVIS", nullable=false)	
	private String droitAvis;

	@Column(name = "DROIT_AVIS_DEFINITIF", nullable=false)
	private String droitAvisDefinitif;

	@Column(name = "DROIT_DECISION", nullable=false)	
	private String droitDecision;	
	
	@Column(name = "DROIT_DEVERROUILLER", nullable=false)	
	private String droitDeverrouiller;

	@Column(name = "ALERT_MAIL_DEMANDE_TRANSFERT", nullable=false)
	private String alertMailDemandeTransfert;

	@Column(name = "ALERT_MAIL_SVA", nullable=false)
	private String alertMailSva;

	/**
	 * Bean constructor.
	 */
	public PersonnelComposante() {
		super();
	}

	public PersonnelComposante(String uid, String codeComposante, String source, Integer annee, String displayName, String libelleComposante) 
	{
		super();
		this.uid = uid;
		this.codeComposante = codeComposante;
		this.source = source;
		this.displayName = displayName;
		this.libelleComposante = libelleComposante;
		this.annee=annee;
	}

	public PersonnelComposante(String uid, String codeComposante, String source, Integer annee, String displayName, String libelleComposante, Integer typePersonnel) 
	{
		super();
		this.uid = uid;
		this.codeComposante = codeComposante;
		this.source = source;
		this.displayName = displayName;
		this.libelleComposante = libelleComposante;
		this.typePersonnel = typePersonnel;
		this.annee=annee;
	}

	public PersonnelComposante(String uid, 
							   String codeComposante, 
							   String source, 
							   Integer annee, 
							   String displayName,
							   String mailPersonnel,
							   String libelleComposante, 
							   Integer typePersonnel, 
							   String droitSuppression,
							   String droitEditionPdf,
							   String droitAvis,
							   String droitAvisDefinitif,
							   String droitDecision,
							   String droitDeverrouiller,
							   String alertMailDemandeTransfert,
							   String alertMailSva)
	{
		super();
		this.uid = uid;
		this.codeComposante = codeComposante;
		this.source = source;
		this.displayName = displayName;
		this.mailPersonnel= mailPersonnel;
		this.libelleComposante = libelleComposante;
		this.typePersonnel = typePersonnel;
		this.annee=annee;
		this.droitSuppression=droitSuppression;
		this.droitEditionPdf=droitEditionPdf;
		this.droitAvis=droitAvis;
		this.droitAvisDefinitif=droitAvisDefinitif;
		this.droitDecision=droitDecision;
		this.droitDeverrouiller=droitDeverrouiller;
		this.alertMailDemandeTransfert=alertMailDemandeTransfert;
		this.alertMailSva=alertMailSva;
	}

	@Override
	public String toString() {
		return "PersonnelComposante{" +
				"uid='" + uid + '\'' +
				", codeComposante='" + codeComposante + '\'' +
				", source='" + source + '\'' +
				", annee=" + annee +
				", displayName='" + displayName + '\'' +
				", libelleComposante='" + libelleComposante + '\'' +
				", typePersonnel=" + typePersonnel +
				", mailPersonnel='" + mailPersonnel + '\'' +
				", libelleTypePersonnel='" + libelleTypePersonnel + '\'' +
				", droitSuppression='" + droitSuppression + '\'' +
				", droitEditionPdf='" + droitEditionPdf + '\'' +
				", droitAvis='" + droitAvis + '\'' +
				", droitAvisDefinitif='" + droitAvisDefinitif + '\'' +
				", droitDecision='" + droitDecision + '\'' +
				", droitDeverrouiller='" + droitDeverrouiller + '\'' +
				", alertMailDemandeTransfert='" + alertMailDemandeTransfert + '\'' +
				", alertMailSva='" + alertMailSva + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PersonnelComposante)) return false;

		PersonnelComposante that = (PersonnelComposante) o;

		if (!getUid().equals(that.getUid())) return false;
		if (!getCodeComposante().equals(that.getCodeComposante())) return false;
		if (!getSource().equals(that.getSource())) return false;
		if (!getAnnee().equals(that.getAnnee())) return false;
		if (!getDisplayName().equals(that.getDisplayName())) return false;
		if (!getLibelleComposante().equals(that.getLibelleComposante())) return false;
		if (!getTypePersonnel().equals(that.getTypePersonnel())) return false;
		if (getMailPersonnel() != null ? !getMailPersonnel().equals(that.getMailPersonnel()) : that.getMailPersonnel() != null)
			return false;
		if (getLibelleTypePersonnel() != null ? !getLibelleTypePersonnel().equals(that.getLibelleTypePersonnel()) : that.getLibelleTypePersonnel() != null)
			return false;
		if (!getDroitSuppression().equals(that.getDroitSuppression())) return false;
		if (!getDroitEditionPdf().equals(that.getDroitEditionPdf())) return false;
		if (!getDroitAvis().equals(that.getDroitAvis())) return false;
		if (!getDroitAvisDefinitif().equals(that.getDroitAvisDefinitif())) return false;
		if (!getDroitDecision().equals(that.getDroitDecision())) return false;
		if (!getDroitDeverrouiller().equals(that.getDroitDeverrouiller())) return false;
		if (!getAlertMailDemandeTransfert().equals(that.getAlertMailDemandeTransfert())) return false;
		return getAlertMailSva().equals(that.getAlertMailSva());

	}

	@Override
	public int hashCode() {
		int result = getUid().hashCode();
		result = 31 * result + getCodeComposante().hashCode();
		result = 31 * result + getSource().hashCode();
		result = 31 * result + getAnnee().hashCode();
		result = 31 * result + getDisplayName().hashCode();
		result = 31 * result + getLibelleComposante().hashCode();
		result = 31 * result + getTypePersonnel().hashCode();
		result = 31 * result + (getMailPersonnel() != null ? getMailPersonnel().hashCode() : 0);
		result = 31 * result + (getLibelleTypePersonnel() != null ? getLibelleTypePersonnel().hashCode() : 0);
		result = 31 * result + getDroitSuppression().hashCode();
		result = 31 * result + getDroitEditionPdf().hashCode();
		result = 31 * result + getDroitAvis().hashCode();
		result = 31 * result + getDroitAvisDefinitif().hashCode();
		result = 31 * result + getDroitDecision().hashCode();
		result = 31 * result + getDroitDeverrouiller().hashCode();
		result = 31 * result + getAlertMailDemandeTransfert().hashCode();
		result = 31 * result + getAlertMailSva().hashCode();
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLibelleComposante() {
		return libelleComposante;
	}

	public void setLibelleComposante(String libelleComposante) {
		this.libelleComposante = libelleComposante;
	}

	public Integer getTypePersonnel() {
		return typePersonnel;
	}

	public void setTypePersonnel(Integer typePersonnel) {
		this.typePersonnel = typePersonnel;
	}

	public String getLibelleTypePersonnel() {
		return libelleTypePersonnel;
	}

	public void setLibelleTypePersonnel(String libelleTypePersonnel) {
		this.libelleTypePersonnel = libelleTypePersonnel;
	}

	public String getDroitSuppression() {
		return droitSuppression;
	}

	public void setDroitSuppression(String droitSuppression) {
		this.droitSuppression = droitSuppression;
	}

	public String getDroitEditionPdf() {
		return droitEditionPdf;
	}

	public void setDroitEditionPdf(String droitEditionPdf) {
		this.droitEditionPdf = droitEditionPdf;
	}

	public String getDroitAvis() {
		return droitAvis;
	}

	public void setDroitAvis(String droitAvis) {
		this.droitAvis = droitAvis;
	}

	public String getDroitDecision() {
		return droitDecision;
	}

	public void setDroitDecision(String droitDecision) {
		this.droitDecision = droitDecision;
	}

	public String getDroitDeverrouiller() {
		return droitDeverrouiller;
	}

	public void setDroitDeverrouiller(String droitDeverrouiller) {
		this.droitDeverrouiller = droitDeverrouiller;
	}

	public String getDroitAvisDefinitif() {
		return droitAvisDefinitif;
	}

	public void setDroitAvisDefinitif(String droitAvisDefinitif) {
		this.droitAvisDefinitif = droitAvisDefinitif;
	}

	public String getMailPersonnel() {
		return mailPersonnel;
	}

	public void setMailPersonnel(String mailPersonnel) {
		this.mailPersonnel = mailPersonnel;
	}

	public String getAlertMailDemandeTransfert() {
		return alertMailDemandeTransfert;
	}

	public void setAlertMailDemandeTransfert(String alertMailDemandeTransfert) {
		this.alertMailDemandeTransfert = alertMailDemandeTransfert;
	}

	public String getAlertMailSva() {
		return alertMailSva;
	}

	public void setAlertMailSva(String alertMailSva) {
		this.alertMailSva = alertMailSva;
	}
}