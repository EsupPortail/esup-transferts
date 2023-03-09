package org.esupportail.transferts.web.utils;

/**
 * @author dhouillo
 *
 */
public enum PersonnelsComposantesColonneEnumArrivee {

	/*
	 ******************* ENUM VALUE ******************* */
	
	/**
	 * col1 , Numero Convention.
	 */
	col1("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.IDENTIFIANT", "uid"),
	col2("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.DISPLAY_NAME", "displayName"),
	col3("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.CODE_COMPOSANTE", "codeComposante"),
	col4("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.LIBELLE_COMPOSANTE", "libelleComposante"),
	col5("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.TYPE_PERSONNEL", "libelleTypePersonnel"),
	col6("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.DROIT_SUPPRESSION", "droitSuppression"),
	col7("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.DROIT_EDITION_PDF", "droitEditionPdf"),
	col8("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.DROIT_DECISION", "droitDecision"),
	col9("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.DROIT_DEVERROUILLER", "droitDeverrouiller"),
	col10("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.DROIT_ALERT_MAIL_TRANSFERT", "alertMailDemandeTransfert"),
	col11("EXPORT.EXCEL.COLONNE.PERSONNEL_COMPOSANTE.DROIT_ALERT_MAIL_SVA", "alertMailSva");

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * i18n key to the label.
	 */
	private String keyLabel;
	
	/**
	 * nameProperty.
	 */
	private String nameProperty;

	/*
	 ******************* INIT ******************* */

	
	/**
	 * @param keyLabe
	 * @param nameProperty 
	 */
	private PersonnelsComposantesColonneEnumArrivee(final String keyLabe, final String nameProperty) {
		this.keyLabel = keyLabe;
		this.nameProperty = nameProperty; 
	}
	
	/*
	 ******************* METHOS ******************* */
	
	

	/*
	 ******************* ACCESSORS ******************* */


	/**
	 * @return the keyLabel
	 */
	public String getKeyLabel() {
		return keyLabel;
	}

	/**
	 * @param keyLabel the keyLabel to set
	 */
	private void setKeyLabel(final String keyLabel) {
		this.keyLabel = keyLabel;
	}

	/**
	 * @return the nameProperty
	 */
	public String getNameProperty() {
		return nameProperty;
	}

	/**
	 * @param nameProperty the nameProperty to set
	 */
	private void setNameProperty(final String nameProperty) {
		this.nameProperty = nameProperty;
	}
	
}
