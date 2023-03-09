package org.esupportail.transferts.web.dataModel;

import org.esupportail.transferts.domain.beans.Fichier;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;
  
public class SignatureDataModel extends ListDataModel<Fichier> implements SelectableDataModel<Fichier> {    
  
    public SignatureDataModel() {  
    }  
  
    public SignatureDataModel(List<Fichier> data) {  
        super(data);  
    }  
      
    public Fichier getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<Fichier> listeFichiers = (List<Fichier>) getWrappedData();  
          
        for(Fichier liste : listeFichiers) {  
            if(liste.getMd5().equals(rowKey))  
                return liste;  
        }  
          
        return null;  
    }  
  
    public Object getRowKey(Fichier fichier) {  
        return fichier.getMd5(); 
    }  
}  