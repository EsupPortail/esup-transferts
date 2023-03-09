package org.esupportail.transferts.web.dataModel;

import org.esupportail.transferts.domain.beans.CodeSizeAnnee;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;
  
public class CodeSizeDataModel extends ListDataModel<CodeSizeAnnee> implements SelectableDataModel<CodeSizeAnnee> {
  
    public CodeSizeDataModel() {  
    }  
  
    public CodeSizeDataModel(List<CodeSizeAnnee> data) {  
        super(data);  
    }  
      
    public CodeSizeAnnee getRowData(String rowKey) {   
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<CodeSizeAnnee> listeCodeSize = (List<CodeSizeAnnee>) getWrappedData();  
          
        for(CodeSizeAnnee liste : listeCodeSize) {  
            if(rowKey.equals(liste.getAnnee()))
                return liste;  
        }  
          
        return null;  
    }  
  
    public Object getRowKey(CodeSizeAnnee codeSize) {  
        return codeSize.getAnnee(); 
    }  
}  