package org.esupportail.transferts.web.dataModel;

import org.esupportail.transferts.domain.beans.IndOpi;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;
  
public class TransfertDataModelOpi extends ListDataModel<IndOpi> implements SelectableDataModel<IndOpi> {
  
    public TransfertDataModelOpi() {  
    }  
  
    public TransfertDataModelOpi(List<IndOpi> data) {  
        super(data);  
    }  
      
    public IndOpi getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<IndOpi> listeTransferts = (List<IndOpi>) getWrappedData();  
          
        for(IndOpi liste : listeTransferts) {  
            if(liste.getNumeroOpi().equals(rowKey))  
                return liste;  
        }  
          
        return null;  
    }  

    public Object getRowKey(IndOpi indOpi) {  
        return indOpi.getNumeroOpi(); 
    }      
}  