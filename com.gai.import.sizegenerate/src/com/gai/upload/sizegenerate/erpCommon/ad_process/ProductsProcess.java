/*
 ************************************************************************************
 * Copyright (C) 2009-2010 Openbravo S.L.U.
 * Licensed under the Openbravo Commercial License version 1.0
 * You may obtain a copy of the License at http://www.openbravo.com/legal/obcl.html
 * or in the legal folder of this module distribution.
 ************************************************************************************
 */

package com.gai.upload.sizegenerate.erpCommon.ad_process;

import java.math.BigDecimal;
import java.util.Calendar;

 import org.openbravo.idl.proc.Parameter;
 import org.openbravo.idl.proc.Validator;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.core.DalUtil;

import org.openbravo.base.exception.OBException;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.base.structure.BaseOBObject;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.idl.initial_data_load.productjob_0_1.ProductJob;
import org.openbravo.model.common.currency.Currency;
import org.openbravo.model.common.plm.AttributeSet;
import org.openbravo.model.common.plm.AttributeSetInstance;
import org.openbravo.model.common.plm.Product;
import org.openbravo.model.common.plm.ProductCategory;
import org.openbravo.model.common.uom.UOM;
import org.openbravo.model.financialmgmt.tax.TaxCategory;
import org.openbravo.model.pricing.pricelist.PriceList;
import org.openbravo.model.pricing.pricelist.PriceListSchema;
import org.openbravo.model.pricing.pricelist.PriceListSchemeLine;
import org.openbravo.model.pricing.pricelist.PriceListVersion;
import org.openbravo.model.pricing.pricelist.ProductPrice;
import org.openbravo.scheduling.ProcessLogger;
import org.apache.log4j.*;

//Deklarasi 6 atrribut
import org.openbravo.model.common.plm.Brand; //Attribut 1
import com.gai.sizeCodeGenerate.data.process.SCGMgroupKabel; //Attribut 2
import com.gai.sizeCodeGenerate.data.process.SCGJenisProduk; //Attribut 3
import com.gai.sizeCodeGenerate.data.process.SCGTeganganPengenal; //Attribut 4
import com.gai.sizeCodeGenerate.data.process.SCGStandart; //Attribut 5
import com.gai.sizeCodeGenerate.data.process.ScgSize; //Attribut 6

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
 
import org.openbravo.idl.proc.Value;
import org.openbravo.idl.proc.DALUtils; 
import org.openbravo.idl.proc.IdlServiceETL; 
import org.openbravo.module.idljava.proc.IdlServiceJava;
import com.gai.sizeCodeGenerate.data.process.ScgWarna;

/**
 * 
 * @author adrian
 */
public class ProductsProcess extends IdlServiceJava  {

  // private static Logger log4j = Logger.getLogger(ProductsProcess.class);

  
  
  
 Product product = null;
 String flag = "";
 private static Logger log=Logger.getLogger(SizeMaterialProcess.class);
 
 public String getEntityName() {
 return "Simple Upload Produk";
 }
 
 
 
 
  public Parameter[] getParameters() {
    return new Parameter[] { new Parameter("Organization", Parameter.STRING),
        new Parameter("Merk", Parameter.STRING), new Parameter("Grup", Parameter.STRING),
	    	new Parameter("Jenis", Parameter.STRING), new Parameter("Tegangan", Parameter.STRING),
	     	new Parameter("Standart", Parameter.STRING), new Parameter("Ukuran", Parameter.STRING),
        new Parameter("KodeUkuran", Parameter.STRING),     new Parameter("UPCEAN", Parameter.STRING),
        new Parameter("ProductCategory", Parameter.STRING), new Parameter("UOM", Parameter.STRING),
        new Parameter("ProductType", Parameter.STRING),
        new Parameter("Production", Parameter.STRING),
        new Parameter("BillOfMaterial", Parameter.STRING),
        new Parameter("Discontinued", Parameter.STRING),
        new Parameter("CostType", Parameter.STRING),
        new Parameter("AttributeSet", Parameter.STRING),
        new Parameter("AttributeValue", Parameter.STRING),
        new Parameter("Stocked", Parameter.STRING), new Parameter("Purchase", Parameter.STRING),
        new Parameter("Sale", Parameter.STRING), new Parameter("TaxCategory", Parameter.STRING),
        new Parameter("PriceListVersion", Parameter.STRING),
        new Parameter("PriceSales", Parameter.STRING),
        new Parameter("PricePurchase", Parameter.STRING) };
  }
  
   protected Object[] validateProcess(Validator validator, String... values) throws Exception {
 
     
 validator.checkOrganization(values[0]); //0
 validator.checkString(values[1], 255);//1
 validator.checkString(values[2], 255);//2
 validator.checkString(values[3], 255);//3
 validator.checkString(values[4], 255);//4
 validator.checkString(values[5], 255);//5
 validator.checkString(values[6], 255);//6
 validator.checkString(values[7], 255);//7
 validator.checkString(values[8], 255);//8
 validator.checkString(values[9], 255);//9
 validator.checkString(values[10], 255);//10
 validator.checkString(values[11], 255);//11
 validator.checkString(values[12], 255);//12
 validator.checkString(values[13], 255);//13
 validator.checkString(values[14], 255);//14
 validator.checkString(values[15], 255);//15
 validator.checkString(values[16], 255);//16
 validator.checkString(values[17], 255);//17
 validator.checkString(values[18], 255);//18
 validator.checkString(values[19], 255);//19
 validator.checkString(values[20], 255);//18
 validator.checkString(values[21], 255);//19
 validator.checkString(values[22], 255);//18
 validator.checkString(values[23], 255);//19
 validator.checkString(values[24], 255);//19
 return values;
 
 }
  

  @Override
  public BaseOBObject internalProcess(Object... values) throws Exception {

    return createProduct((String) values[0], (String) values[1], (String) values[2],
        (String) values[3], (String) values[4], (String) values[5], (String) values[6],
        (String) values[7], (String) values[8], (String) values[9], (String) values[10],
        (String) values[11], (String) values[12], (String) values[13], (String) values[14],
        (String) values[15], (String) values[16], (String) values[17], (String) values[18],
        (String) values[19], (String) values[20],(String) values[21], (String) values[22],
		    (String) values[23],(String) values[24]);
  }

  public BaseOBObject createProduct(final String Organization,
	  final String merk,final String grup,final String jenis,final String tegangan,
	  final String standart,final String ukuran,final String kodeukuran,final String upcean,
      final String productcategory, final String uom, final String producttype,

      final String production, final String billofmaterial, final String discontinued,
      final String costtype, final String attributeset, final String attributevalue,

      final String stocked, final String purchase, final String sale, final String taxcategory,
      final String pricelistversion, final String pricesales, final String pricepurchase)
      throws Exception {

   // Proses Generate searchkey
   //Variable Tampung
   String Tmerek = "";
   String TmerekKode = "";
   String Tgrup = "";
   String Tjenis = "";
   String Ttegangan = "";
   String Tstandart = "";
   String Tukuran = "";
   String Tkategori = "";
   
   
   String TmerekName = "";
   String TgrupName = "";
   String TjenisName = "";
   String TteganganName = "";
   String TstandartName = "";
   String TukuranName = "";
   String TkategoriName = "";
  
  
   ScgWarna TukuranWarna = null;
  
   String searchkey = "";
   String productName = "";
   String v_spasi="";
   String v_spasi_w="";
   
    final OBCriteria<Brand> oba = OBDal.getInstance().createCriteria(Brand.class);
       
	      oba.setFilterOnReadableClients(false);
          oba.setFilterOnReadableOrganization(false);
          oba.add(Expression.eq(Brand.PROPERTY_DESCRIPTION, merk));
	     
		 
		 	 for (final Brand a : oba.list()) {
    		  
			  Tmerek = a.getId();
			  TmerekKode = a.getName();
			 
			  
             }
	final OBCriteria<SCGMgroupKabel> obb = OBDal.getInstance().createCriteria(SCGMgroupKabel.class);
       
	      obb.setFilterOnReadableClients(false);
          obb.setFilterOnReadableOrganization(false);
          obb.add(Expression.eq(SCGMgroupKabel.PROPERTY_NAME, grup));
	     
		 
		 	 for (final SCGMgroupKabel b : obb.list()) {
    		  
			  Tgrup = b.getValue();
		
             }		 
	
	final OBCriteria<SCGJenisProduk> obc = OBDal.getInstance().createCriteria(SCGJenisProduk.class);
       
	      obc.setFilterOnReadableClients(false);
          obc.setFilterOnReadableOrganization(false);
          obc.add(Expression.eq(SCGJenisProduk.PROPERTY_NAME, jenis));
	     
		 
		 	 for (final SCGJenisProduk c : obc.list()) {
    		  
			  Tjenis = c.getValue();
	
			  
             }			

	final OBCriteria<SCGTeganganPengenal> obd = OBDal.getInstance().createCriteria(SCGTeganganPengenal.class);
       
	      obd.setFilterOnReadableClients(false);
          obd.setFilterOnReadableOrganization(false);
          obd.add(Expression.eq(SCGTeganganPengenal.PROPERTY_NAME, tegangan));
	     
		 
		 	 for (final SCGTeganganPengenal d : obd.list()) {
    		  
			  Ttegangan = d.getValue();
		
			  
             }		
			 
	final OBCriteria<SCGStandart> obe = OBDal.getInstance().createCriteria(SCGStandart.class);
       
	      obe.setFilterOnReadableClients(false);
          obe.setFilterOnReadableOrganization(false);
          obe.add(Expression.eq(SCGStandart.PROPERTY_NAME, standart));
	     
		 
		 	 for (final SCGStandart e : obe.list()) {
    		  
			  Tstandart = e.getValue();
              TteganganName = e.getName();
			  
             }	

	final OBCriteria<ScgSize> obf = OBDal.getInstance().createCriteria(ScgSize.class);
       
	        obf.setFilterOnReadableClients(false);
          obf.setFilterOnReadableOrganization(false);
          obf.add(Expression.eq(ScgSize.PROPERTY_CODEPRODUCT, kodeukuran));
	     
		 
		 	 for (final ScgSize f : obf.list()) {
    		  
			  Tukuran = f.getName();
			  TukuranWarna = f.getWarna();
			  
             }	

 	final OBCriteria<ProductCategory> obg = OBDal.getInstance().createCriteria(ProductCategory.class);
       
	      obg.setFilterOnReadableClients(false);
          obg.setFilterOnReadableOrganization(false);
          obg.add(Expression.eq(ProductCategory.PROPERTY_NAME, productcategory));
	     
		 
		 	 for (final ProductCategory g : obg.list()) {
    		  
			  Tkategori = g.getSearchKey();
			  
             }	    
	 
	 
	 
	 searchkey = Tkategori+"-"+TmerekKode+Tgrup+Tjenis+Ttegangan+Ttegangan+Tstandart+kodeukuran;
	 		 
	  
	  if(TteganganName.equals("0") || TteganganName=="0"){
	  TteganganName="";
	  v_spasi="";
	  }else{
	    TteganganName=TteganganName;
		v_spasi=" ";
	  }
	  
	  
	if(TukuranWarna==null){
	  v_spasi_w="";
	  }else{
	    TukuranWarna=TukuranWarna;
		v_spasi_w=" ";
	  }
			 
 
   //productName = TjenisNama+" "+ukuran+" "+TteganganName+v_spasi+v_spasi_w+TmerekName+" "+standart;
   
  productName = jenis+" "+Tukuran+" "+TteganganName+v_spasi+v_spasi_w+merk+" "+standart;
			 
	
    Product productExist = findDALInstance(false, Product.class, new Value("searchKey", searchkey));
    if (productExist != null) {
      throw new OBException(Utility.messageBD(conn, "IDL_PR_EXISTS", vars.getLanguage())
          + searchkey);
    }

    product = OBProvider.getInstance().get(Product.class);
    product.setActive(true);
    product.setOrganization(rowOrganization);
    product.setSearchKey(searchkey);
    product.setName(productName);
    product.setDescription(jenis);
    product.setUPCEAN(upcean);
    product.setProductCategory(findDALInstance(false, ProductCategory.class, new Value(ProductCategory.PROPERTY_NAME, productcategory)));

      product.setUOM(findDALInstance(false, UOM.class, new Value(UOM.PROPERTY_NAME, uom)));
    

    product.setProductType(getReferenceValue("M_Product_ProductType", producttype));

    // Search Default value for stocked, purchase and sale
    product.setStocked(Parameter.BOOLEAN.parse(stocked));
    product.setPurchase(Parameter.BOOLEAN.parse(purchase));
    product.setSale(Parameter.BOOLEAN.parse(sale));
    product.setProduction(Parameter.BOOLEAN.parse(production));
    product.setBillOfMaterials(Parameter.BOOLEAN.parse(billofmaterial));
    product.setDiscontinued(Parameter.BOOLEAN.parse(discontinued));
    product.setCostType(getReferenceValue("Cost Type", costtype));
    product.setTaxCategory(findDALInstance(false, TaxCategory.class, new Value(TaxCategory.PROPERTY_NAME, taxcategory)));
    


    AttributeSet attset = findDALInstance(false, AttributeSet.class,
        new Value("name", attributeset));
    product.setAttributeSet(attset);

    if (attset != null && attributevalue != null) {

      String defAttSetValueType = searchDefaultValue("Product", "AttributeSetValueType", null);
      String attSetValueType = getReferenceValue("Use Attribute Set Value As", defAttSetValueType);

      // for some reason these entities need
      // to be processed in admin mode.
      OBContext.getOBContext().setInAdministratorMode(true);

      AttributeSetInstance attsetinst = getAttributeSetInstance(attset, attributevalue);

      if (attSetValueType == null) {
        attSetValueType = "D";
      }
      product.setUseAttributeSetValueAs(attSetValueType);

      product.setAttributeSetValue(attsetinst);

      OBContext.getOBContext().setInAdministratorMode(false);
    }

    OBDal.getInstance().save(product);
    OBDal.getInstance().flush();

    // Time part of the date must be set to 0
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);

        ProductPrice productPrice = OBProvider.getInstance().get(ProductPrice.class);
        productPrice.setActive(true);
        productPrice.setOrganization(rowOrganization);
        productPrice.setProduct(product);
        productPrice.setListPrice(new BigDecimal(pricesales));
        productPrice.setStandardPrice(new BigDecimal(pricesales));
        productPrice.setPriceLimit(new BigDecimal(pricesales));
        productPrice.setPriceListVersion(findDALInstance(false, PriceListVersion.class, new Value(PriceListVersion.PROPERTY_NAME, pricelistversion)));
        OBDal.getInstance().save(productPrice);
        OBDal.getInstance().flush();
      

     
    

    // End process
    OBDal.getInstance().commitAndClose();

    return product;
  }
}
