package org.esupportail.transferts.web.utils;

/**
 * @author dhouillo
 *
 */
public enum EtudiantColonneEnum {

	/*
	 ******************* ENUM VALUE ******************* */
	
	/**
	 * col1 , Numero Convention.
	 */
	col1("EXPORT.EXCEL.COLONNE.DATE_DEMANDE_TRANSFERT", "dateDeLaDemandeTransfert"),
	col2("EXPORT.EXCEL.COLONNE.NUNERO_ETUDIANT", "numeroEtudiant"),
	col3("EXPORT.EXCEL.COLONNE.NOM_PATRONYMIQUE", "nomPatronymique"),	
	col4("EXPORT.EXCEL.COLONNE.PRENOM1", "prenom1"),
	col5("EXPORT.EXCEL.COLONNE.NUMERO_INE", "numeroIne"),
	col6("EXPORT.EXCEL.COLONNE.ADRESSE.MAIL", "adresse.email"),
	col7("EXPORT.EXCEL.COLONNE.DATE_DE_NAISSANCE", "dateNaissance"),
	col8("EXPORT.EXCEL.COLONNE.ADRESE.LIBAD1", "adresse.libAd1"),
//	col8("EXPORT.EXCEL.COLONNE.ADRESE.CODECOMMUNE", "adresse.codeCommune"),	
//	col9("EXPORT.EXCEL.COLONNE.ADRESE.NOMCOMUNE", "adresse.nomCommune"),		
	col9("EXPORT.EXCEL.COLONNE.ETAT_DU_DOSSIER", "etatDuDossier"),
	col10("EXPORT.EXCEL.COLONNE.COMPOSANTE_DE_DEPART", "composante"),
	col11("EXPORT.EXCEL.COLONNE.DERNIERE_IA_UNIVERSITE_ORIGINE", "derniereIaInscription"),
	col12("EXPORT.EXCEL.COLONNE.ETABLISSEMENT_ACCUEIL", "universiteAccueil.libEtb"),
	col13("EXPORT.EXCEL.COLONNE.FORMATION_ACCUEIL", "libelleVET"),
	col14("EXPORT.EXCEL.COLONNE.AVIS", "avis.libDecisionDossier");	
	
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
	private EtudiantColonneEnum(final String keyLabe, final String nameProperty) {
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
