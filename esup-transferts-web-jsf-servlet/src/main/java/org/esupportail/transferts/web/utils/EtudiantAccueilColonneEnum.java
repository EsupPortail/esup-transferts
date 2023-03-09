package org.esupportail.transferts.web.utils;

/**
 * @author dhouillo
 *
 */
public enum EtudiantAccueilColonneEnum {

	/*
	 ******************* ENUM VALUE ******************* */
	
	/**
	 * col1 , Numero Convention.
	 */
	col1("EXPORT.EXCEL.COLONNE.DATE_DEMANDE_TRANSFERT", "dateDeLaDemandeTransfert"),
	col2("EXPORT.EXCEL.COLONNE.UFR_ARRIVEE", "composante"),
	col3("EXPORT.EXCEL.COLONNE.FORMATION_SOUHAITEE", "odf.libVersionEtape"),
	col4("EXPORT.EXCEL.COLONNE.NUMERO_INE", "numeroIne"),
	col5("EXPORT.EXCEL.COLONNE.NOM_PATRONYMIQUE", "nomPatronymique"),	
	col6("EXPORT.EXCEL.COLONNE.PRENOM1", "prenom1"),
	col7("EXPORT.EXCEL.COLONNE.ADRESSE.MAIL", "adresse.email"),
	col8("EXPORT.EXCEL.COLONNE.DATE_DE_NAISSANCE", "dateNaissance"),
	col9("EXPORT.EXCEL.COLONNE.ETABLISSEMENT_DEPART", "universiteDepart.libEtb"),
	col10("EXPORT.EXCEL.COLONNE.DERNIERE_FORMATION", "derniereFormation"),
	col11("EXPORT.EXCEL.COLONNE.CODE_BAC", "codeBac"),
	col12("EXPORT.EXCEL.COLONNE.ANNEE_BAC", "anneeBac"),
	col13("EXPORT.EXCEL.COLONNE.VALIDATION", "validation"),
	col14("EXPORT.EXCEL.COLONNE.FROM_SOURCE", "from_source"),
	col15("EXPORT.EXCEL.COLONNE.DECISION_DE", "decisionDE"),
	col16("EXPORT.EXCEL.COLONNE.CANDIDATURE", "dataExterneNiveau2");

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
	private EtudiantAccueilColonneEnum(final String keyLabe, final String nameProperty) {
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
