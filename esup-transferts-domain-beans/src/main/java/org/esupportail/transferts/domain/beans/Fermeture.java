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
	@NamedQuery(
			name="getListeFermeturesBySourceAndAnnee",
			query="SELECT f FROM Fermeture f WHERE (f.annee = :annee OR f.annee = :annee - 1) AND f.source = :source)"
			)			
})
@Table(name = "FERMETURE")
public class Fermeture implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 1234532897401294181L;

	@Id
	@Column(name = "id",  nullable=false)
	private String idScheduler;		
	
	@Column(name = "source",  nullable=false)
	private String source;		
	
	@Column(name = "annee",  nullable=false)
	private Integer annee;			
	
	@Column(name = "Titre",  length = 2000)
	private String titre;	

	@Column(name = "date_debut")
	private Date dateDebut;		
	
	@Column(name = "date_fin")
	private Date dateFin;		
	
	@Column(name = "all_day")
	private boolean allDay;	
	
	/**
	 * Bean constructor.
	 */
	public Fermeture() {
		super();
	}

	public Fermeture(String idScheduler, String source, Integer annee, String titre, Date dateDebut, Date dateFin,
			boolean allDay) {
		super();
		this.idScheduler = idScheduler;
		this.source = source;
		this.annee = annee;
		this.titre = titre;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.allDay = allDay;
	}

	@Override
	public String toString() {
		return "Fermeture [idScheduler=" + idScheduler + ", source=" + source + ", annee=" + annee + ", titre=" + titre
				+ ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", allDay=" + allDay+"]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Fermeture)) return false;

		Fermeture fermeture = (Fermeture) o;

		if (isAllDay() != fermeture.isAllDay()) return false;
		if (!getIdScheduler().equals(fermeture.getIdScheduler())) return false;
		if (!getSource().equals(fermeture.getSource())) return false;
		if (!getAnnee().equals(fermeture.getAnnee())) return false;
		if (getTitre() != null ? !getTitre().equals(fermeture.getTitre()) : fermeture.getTitre() != null) return false;
		if (getDateDebut() != null ? !getDateDebut().equals(fermeture.getDateDebut()) : fermeture.getDateDebut() != null)
			return false;
		return getDateFin() != null ? getDateFin().equals(fermeture.getDateFin()) : fermeture.getDateFin() == null;

	}

	@Override
	public int hashCode() {
		int result = getIdScheduler().hashCode();
		result = 31 * result + getSource().hashCode();
		result = 31 * result + getAnnee().hashCode();
		result = 31 * result + (getTitre() != null ? getTitre().hashCode() : 0);
		result = 31 * result + (getDateDebut() != null ? getDateDebut().hashCode() : 0);
		result = 31 * result + (getDateFin() != null ? getDateFin().hashCode() : 0);
		result = 31 * result + (isAllDay() ? 1 : 0);
		return result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}

	public String getIdScheduler() {
		return idScheduler;
	}

	public void setIdScheduler(String idScheduler) {
		this.idScheduler = idScheduler;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
}