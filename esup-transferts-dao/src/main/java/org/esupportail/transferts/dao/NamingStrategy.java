package org.esupportail.transferts.dao;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.cfg.ImprovedNamingStrategy;

public class NamingStrategy extends ImprovedNamingStrategy {  

	private static final long serialVersionUID = -734450386268904960L;  
	final String prefixe="";

	public String classToTableName(String className) {  
		return StringHelper.unqualify(className);  
	}  

	public String tableName(String tableName) { 
		return prefixe+tableName;
	}  

	public String propertyToTableName(String className, String propertyName) {  
		return prefixe+classToTableName(className)+propertyToColumnName(propertyName);  
	}
	
	public String propertyToColumnName(String propertyName) {
		return StringHelper.unqualify(propertyName);
	}
	
	public String columnName(String columnName) {
		return columnName;
	}
	
}  
