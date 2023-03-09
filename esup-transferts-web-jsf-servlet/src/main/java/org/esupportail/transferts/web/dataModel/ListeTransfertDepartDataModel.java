package org.esupportail.transferts.web.dataModel;

import org.esupportail.transferts.domain.beans.EtudiantRef;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;
  
public class ListeTransfertDepartDataModel extends ListDataModel<EtudiantRef> implements SelectableDataModel<EtudiantRef> {
  
    public ListeTransfertDepartDataModel() {  
    }  
  
    public ListeTransfertDepartDataModel(List<EtudiantRef> data) {  
        super(data);  
    }  

    public EtudiantRef getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<EtudiantRef> listeTransferts = (List<EtudiantRef>) getWrappedData();  
          
        for(EtudiantRef liste : listeTransferts) {  
            if(liste.getNumeroEtudiant().equals(rowKey))  
                return liste;  
        }  
          
        return null;  
    }  

    public Object getRowKey(EtudiantRef etudiantRef) {  
        return etudiantRef.getNumeroEtudiant(); 
    }  
}  