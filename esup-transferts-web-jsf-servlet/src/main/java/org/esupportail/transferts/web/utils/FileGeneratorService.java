package org.esupportail.transferts.web.utils;

import fr.univ.rennes1.cri.services.export.SpreadsheetObject;
import org.esupportail.transferts.domain.beans.EtudiantRefExcel;
import org.esupportail.transferts.domain.beans.OffreDeFormationsDTO;
import org.esupportail.transferts.domain.beans.PersonnelComposante;

import java.util.List;

/**
 * @author dhouillo
 *
 */
public interface FileGeneratorService {
	
	/**
	 * generate a content to download corresponding to the content of the SpreadsheetObject.
	 * @param typeExport
	 * @param filename
	 * @param sso
	 */
	void generate(final SpreadsheetObject sso, final String typeExport, final String filename);

	
	/**
	 * @param conventions
	 * @param typeExport
	 * @param filename
	 * @param colonnesChoisies 
	 */
	void conventionFile(final List<EtudiantRefExcel> conventions, 
			 final String typeExport, final String filename, final List<String> colonnesChoisies);

	void conventionFileAccueil(final List<EtudiantRefExcel> conventions, 
			 final String typeExport, final String filename, final List<String> colonnesChoisies);
	
	void opiFile(List<String> listeOpi, String typeExport,
			String fileName);


	void exportXlsODF(OffreDeFormationsDTO[] selectedOdfs, String typeExport, String fileName, List<String> colonnesChoisies);
	
//	void exportXlsPersonnelsComposantes(final List<PersonnelComposante> persComp, final String typeExport, final String filename, final List<String> colonnesChoisies);


	void exportXlsPersonnelsComposantesDepart(
			List<PersonnelComposante> lPersComp, String typeExport,
			String fileName, List<String> colonnesChoisies);


	void exportXlsPersonnelsComposantesArrivee(
			List<PersonnelComposante> lPersComp, String typeExport,
			String fileName, List<String> colonnesChoisies);
}
