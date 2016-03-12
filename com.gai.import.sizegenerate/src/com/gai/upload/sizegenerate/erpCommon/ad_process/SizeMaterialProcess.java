/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gai.upload.sizegenerate.erpCommon.ad_process;

 import com.gai.sizeCodeGenerate.data.process.ScgUkuranMaterial;
 import com.gai.sizeCodeGenerate.data.process.Scg_GroupMaterial;
 import org.openbravo.idl.proc.Parameter;
 import org.openbravo.idl.proc.Validator;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.text.ParseException;
 import java.math.BigDecimal;
 import java.util.Date;
 import org.apache.log4j.*;
 //OBDAL
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Expression;
 import org.hibernate.criterion.Projections;
 import org.openbravo.dal.service.OBCriteria;
 import org.openbravo.dal.service.OBDal;
 import org.openbravo.scheduling.ProcessBundle;
 import org.openbravo.scheduling.ProcessLogger;
 import org.openbravo.service.db.DalBaseProcess;
 import org.quartz.JobExecutionException;
 
 import org.openbravo.base.exception.OBException;
 import org.openbravo.base.provider.OBProvider;
 import org.openbravo.base.structure.BaseOBObject;
 import org.openbravo.dal.service.OBDal;
 import org.openbravo.erpCommon.utility.Utility;
 import org.openbravo.idl.proc.Value;
 import org.openbravo.module.idljava.proc.IdlServiceJava;
 //import Invoice dan Line
 import org.openbravo.model.common.invoice.Invoice;
 import org.openbravo.model.common.invoice.InvoiceLine;
// import BP
 import org.openbravo.model.common.businesspartner.BusinessPartner;
 import org.openbravo.model.common.businesspartner.Location;
 //import Financial
 import org.openbravo.model.financialmgmt.payment.FIN_PaymentMethod;
 import org.openbravo.model.financialmgmt.payment.PaymentTerm;
 //import Document Type
 import org.openbravo.model.common.enterprise.DocumentType;
 //import GLItem
 import org.openbravo.model.financialmgmt.gl.GLItem;
 //import PriceList
 import org.openbravo.model.pricing.pricelist.PriceList;
//import CUrrency
 import org.openbravo.model.common.currency.Currency;
//import TAX
 import org.openbravo.model.financialmgmt.tax.TaxRate;

 /**
 *
 * @author Tias Ade Putra - GAI
 */
public class SizeMaterialProcess extends IdlServiceJava {
    
 ScgUkuranMaterial material = null;
 String flag = "";
 private static Logger log=Logger.getLogger(SizeMaterialProcess.class);
 
 public String getEntityName() {
 return "Simple Sales Invoice";
 }
 
 public Parameter[] getParameters() {
 
     return new Parameter[] {
//Header
	 new Parameter("Organization", Parameter.STRING), //0
	 new Parameter("Name",Parameter.STRING), //1         
	 new Parameter("GroupMaterial", Parameter.STRING), //2
	
	};
 }
 

 protected Object[] validateProcess(Validator validator, String... values) throws Exception {
 
     
 validator.checkOrganization(values[0]);
 validator.checkString(values[1], 255);
 validator.checkString(values[2], 255);


 
 return values;
 }


 public BaseOBObject internalProcess(Object... values) throws Exception {
	 return createMaterialSize((String) values[0], (String) values[1], (String) values[2]);
}


 public BaseOBObject createMaterialSize(
			final String Organization, 
			final String Name, 
			final String GroupMaterial

			)
 throws Exception {

	
		                material = OBProvider.getInstance().get(ScgUkuranMaterial.class);	

					
				material.setActive(true);
				material.setOrganization(rowOrganization); 
				material.setName(Name);
                material.setGrupMaterial(findDALInstance(false, Scg_GroupMaterial.class, new Value(Scg_GroupMaterial.PROPERTY_NAME, GroupMaterial)));
					
                                
                                OBDal.getInstance().save(material);
		                OBDal.getInstance().flush();
								
 OBDal.getInstance().commitAndClose();
 
 return material;
 }
    
}
