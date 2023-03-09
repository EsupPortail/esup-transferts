package org.esupportail.transferts.web.utils;

/**
 * @author dhouillo
 *
 */
public enum OdfColonneEnum {

	/*
	 ******************* ENUM VALUE ******************* */
	/**
	 * col1 , Numero Convention.
	 */
	col1("EXPORT.EXCEL.ODF.COLONNE.ANNEE", "annee"),
	col2("EXPORT.EXCEL.ODF.COLONNE.RNE", "rne"),
	col3("EXPORT.EXCEL.ODF.COLONNE.CODE_CGE", "codeCentreGestion"),	
	col4("EXPORT.EXCEL.ODF.COLONNE.LIB_CGE", "libCentreGestion"),
	col5("EXPORT.EXCEL.ODF.COLONNE.CODE_COMP", "codeComposante"),
	col6("EXPORT.EXCEL.ODF.COLONNE.LIB_COMP", "libComposante"),
	col7("EXPORT.EXCEL.ODF.COLONNE.COD_TYP_DIP", "codTypDip"),
	col8("EXPORT.EXCEL.ODF.COLONNE.LIB_TYP_DIP", "libTypDip"),	
	col9("EXPORT.EXCEL.ODF.COLONNE.CODE_DIPLOME", "codeDiplome"),
	col10("EXPORT.EXCEL.ODF.COLONNE.LIB_DIP", "libDiplome"),
	col11("EXPORT.EXCEL.ODF.COLONNE.COD_SIS_DAA_MIN", "codeNiveau"),
	col12("EXPORT.EXCEL.ODF.COLONNE.LIB_COD_SIS_DAA_MIN", "libNiveau"),
	col13("EXPORT.EXCEL.ODF.COLONNE.CODE_VRS_VDI", "codeVersionDiplome"),
	col14("EXPORT.EXCEL.ODF.COLONNE.CODE_ETP", "codeEtape"),
	col15("EXPORT.EXCEL.ODF.COLONNE.CODE_VET", "codeVersionEtape"),	
	col16("EXPORT.EXCEL.ODF.COLONNE.LIB_WEB_VET", "libVersionEtape"),
	col17("EXPORT.EXCEL.ODF.COLONNE.DATE_MAJ", "dateMaj"),
	col18("EXPORT.EXCEL.ODF.COLONNE.ACTIF", "actif"),
	col19("EXPORT.EXCEL.ODF.COLONNE.DEPART", "depart"),
	col20("EXPORT.EXCEL.ODF.COLONNE.ARRIVEE", "arrivee");
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
	private OdfColonneEnum(final String keyLabe, final String nameProperty) {
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
