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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The class that represent users.
 */
@Entity
@Table(name = "SEQUENCE_OPI")
public class SequenceOpi implements Serializable {
	/**
	 * For serialize.
	 */
	@Transient
	private static final long serialVersionUID = 1234999345604494181L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="OPI_SEQ")
	@SequenceGenerator(name="OPI_SEQ", sequenceName="OPI_SEQ", allocationSize=1)	
	private long id;		

	/**
	 * Bean constructor.
	 */
	public SequenceOpi() {
		super();
	}

	@Override
	public String toString() {
		return "SequenceOpi [id=" + id + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SequenceOpi)) return false;

		SequenceOpi that = (SequenceOpi) o;

		return getId() == that.getId();

	}

	@Override
	public int hashCode() {
		return (int) (getId() ^ (getId() >>> 32));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}