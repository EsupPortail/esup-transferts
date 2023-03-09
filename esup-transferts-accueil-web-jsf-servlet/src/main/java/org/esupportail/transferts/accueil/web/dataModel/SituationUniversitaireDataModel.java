package org.esupportail.transferts.accueil.web.dataModel;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;  

import org.esupportail.transferts.domain.beans.CodeSizeAnnee;
import org.esupportail.transferts.domain.beans.SituationUniversitaire;
import org.primefaces.model.SelectableDataModel;  
  
public class SituationUniversitaireDataModel extends ListDataModel<SituationUniversitaire> implements Serializable, SelectableDataModel<SituationUniversitaire> {
  
    public SituationUniversitaireDataModel() {  
    }  
  
    public SituationUniversitaireDataModel(List<SituationUniversitaire> data) {  
        super(data);  
    }

	public SituationUniversitaire getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
        
        @SuppressWarnings("unchecked")
		List<SituationUniversitaire> listeSituationUniversitaire = (List<SituationUniversitaire>) getWrappedData();  
          
        for(SituationUniversitaire liste : listeSituationUniversitaire)  
        	if(String.valueOf(liste.getId()).equals(rowKey)) 
                return liste;
          
        return null;  
	}    
    
	public Object getRowKey(SituationUniversitaire su) {
		return su.getId();
	}
}  