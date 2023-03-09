/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain.beans;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

import javax.persistence.Entity;

/**
 * The class that represent users.
 */
@SuppressWarnings("unchecked")
public class User implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427562897404494181L;

	/**
	 * For Sorting.
	 */
	@SuppressWarnings("rawtypes")
	public static final Comparator<User> ORDER_DISPLAYNAME = new Comparator() {
		@Override
		public int compare(Object o1, Object o2) {
			return ((User) o1).getDisplayName().compareTo(
					((User) o2).getDisplayName());
		}
	};

	/**
	 * Id of the user.
	 */
	private String login;

	/**
	 * Display Name of the user.
	 */
	private String displayName;

	/**
	 * True for administrators.
	 */
	private boolean admin;

	/**
	 * True for administrators.
	 */
	private boolean informaticien;

	/**
	 * The prefered language.
	 */
	private String language;

	private String affiliation;
	
	private String numeroEtudiant;

	private boolean authorized=false;
	
	private String msg;

	private String mail;

	/**
	 * Bean constructor.
	 */
	public User() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		return login.equals(((User) obj).getLogin());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "User{" +
				"login='" + login + '\'' +
				", displayName='" + displayName + '\'' +
				", admin=" + admin +
				", informaticien=" + informaticien +
				", language='" + language + '\'' +
				", affiliation='" + affiliation + '\'' +
				", numeroEtudiant='" + numeroEtudiant + '\'' +
				", authorized=" + authorized +
				", msg='" + msg + '\'' +
				", mail='" + mail + '\'' +
				'}';
	}

	/**
	 * @return the login of the user.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return Returns the displayName.
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * @param displayName
	 *            The displayName to set.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @param admin
	 *            The admin to set.
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return Returns the admin.
	 */
	public boolean isAdmin() {
		return this.admin;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the user display language.
	 */
	public String getDisplayLanguage() {
		Locale locale = new Locale(language);
		return locale.getDisplayLanguage(locale);
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}

	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public boolean isInformaticien() {
		return informaticien;
	}

	public void setInformaticien(boolean informaticien) {
		this.informaticien = informaticien;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}