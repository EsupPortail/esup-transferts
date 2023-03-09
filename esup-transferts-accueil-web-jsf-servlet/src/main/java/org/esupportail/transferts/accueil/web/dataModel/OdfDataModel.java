package org.esupportail.transferts.accueil.web.dataModel;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;  

import org.esupportail.transferts.domain.beans.OffreDeFormationsDTO;
import org.primefaces.model.SelectableDataModel;  
  
public class OdfDataModel extends ListDataModel<OffreDeFormationsDTO> implements Serializable, SelectableDataModel<OffreDeFormationsDTO> {
  
    public OdfDataModel() {  
    }  
  
    public OdfDataModel(List<OffreDeFormationsDTO> data) {  
        super(data);  
    }  
      
    public OffreDeFormationsDTO getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<OffreDeFormationsDTO> odfs = (List<OffreDeFormationsDTO>) getWrappedData();  
          
        for(OffreDeFormationsDTO odf : odfs) 
        {
        	String cle = odf.getRne()+odf.getAnnee().toString()+odf.getCodeDiplome()+odf.getCodeVersionDiplome().toString()+odf.getCodeEtape()+odf.getCodeVersionEtape()+odf.getCodeCentreGestion();
            if(cle.equals(rowKey))  
                return odf;  
        }  
          
        return null;  
    }  
  
    public Object getRowKey(OffreDeFormationsDTO odf) 
    {
    	String cle = odf.getRne()+odf.getAnnee().toString()+odf.getCodeDiplome()+odf.getCodeVersionDiplome().toString()+odf.getCodeEtape()+odf.getCodeVersionEtape()+odf.getCodeCentreGestion();
        return cle;  
    }  
}  