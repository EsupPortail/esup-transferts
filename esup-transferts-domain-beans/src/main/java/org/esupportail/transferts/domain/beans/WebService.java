/**
 * ESUP-Portail example Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The class that represent users.
 */
@Entity
@NamedQueries({
	@NamedQuery(
		    name="getAllWebServices",
		    query="SELECT ws FROM WebService ws"
		    ),
    @NamedQuery(
    name="getWebServiceById",
    query="SELECT ws FROM WebService ws WHERE ws.code = :code"
    )
})
@Table(name = "WEBSERVICE")
public class WebService implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427999812345494181L;
	/**
	 * id WebService
	 */
	@Id
	private String code;

	/**
	 * Url
	 */
	@Column(name = "url", length = 2000)
	private String url;

	/**
	 * Identifiant
	 */
	@Column(name = "identifiant", length = 100)
	private String identifiant;

	/**
	 * Mot de passe
	 */
	@Column(name = "pwd", length = 100)
	private String pwd;

	/**
	 * Nom de la class java
	 */
	@Column(name = "nom_class_java", length = 500)
	private String nomClassJava;

	/**
	 * Nom de la methode getAll java
	 */
	@Column(name = "nom_methode_java_get_all", length = 500)
	private String nomMethodeJavaGetAll;

	/**
	 * Nom de la methode getById java
	 */
	@Column(name = "nom_methode_java_get_by_id", length = 500)
	private String nomMethodeJavaGetById;

	/**
	 * Bean constructor.
	 */
	public WebService() {
		super();
	}

	@Override
	public String toString() {
		return "WebService{" +
				"code='" + code + '\'' +
				", url='" + url + '\'' +
				", identifiant='" + identifiant + '\'' +
				", nomClassJava='" + nomClassJava + '\'' +
				", nomMethodeJavaGetAll='" + nomMethodeJavaGetAll + '\'' +
				", nomMethodeJavaGetById='" + nomMethodeJavaGetById + '\'' +
				'}';
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return super.hashCode();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNomClassJava() {
		return nomClassJava;
	}

	public void setNomClassJava(String nomClassJava) {
		this.nomClassJava = nomClassJava;
	}

	public String getNomMethodeJavaGetAll() {
		return nomMethodeJavaGetAll;
	}

	public void setNomMethodeJavaGetAll(String nomMethodeJavaGetAll) {
		this.nomMethodeJavaGetAll = nomMethodeJavaGetAll;
	}

	public String getNomMethodeJavaGetById() {
		return nomMethodeJavaGetById;
	}

	public void setNomMethodeJavaGetById(String nomMethodeJavaGetById) {
		this.nomMethodeJavaGetById = nomMethodeJavaGetById;
	}
}