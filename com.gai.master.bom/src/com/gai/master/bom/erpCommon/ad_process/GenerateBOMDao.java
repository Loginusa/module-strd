/*
 *************************************************************************
 * The contents of this file are subject to the Openbravo  Public  License
 * Version  1.0  (the  "License"),  being   the  Mozilla   Public  License
 * Version 1.1  with a permitted attribution clause; you may not  use this
 * file except in compliance with the License. You  may  obtain  a copy of
 * the License at http://www.openbravo.com/legal/license.html
 * Software distributed under the License  is  distributed  on  an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific  language  governing  rights  and  limitations
 * under the License.
 * The Original Code is Openbravo ERP.
 * The Initial Developer of the Original Code is Openbravo SLU
 * All portions are Copyright (C) 2010-2013 Openbravo SLU
 * All Rights Reserved.
 * Contributor(s):  Enterprise Intelligence Systems (http://www.eintel.com.au).
 *************************************************************************
 */

package com.gai.master.bom.erpCommon.ad_process;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
//
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Expression;

import org.hibernate.Query;
//
import org.openbravo.advpaymentmngt.APRMPendingPaymentFromInvoice;
//
import org.openbravo.base.exception.OBException;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.base.session.OBPropertiesProvider;
import org.openbravo.base.structure.BaseOBObject;
import org.openbravo.dal.core.DalUtil;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.dal.service.OBQuery;
import org.openbravo.data.FieldProvider;
import org.openbravo.erpCommon.utility.FieldProviderFactory;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.model.ad.domain.Preference;
import org.openbravo.model.ad.system.Client;
import org.openbravo.model.common.businesspartner.BusinessPartner;

import org.openbravo.model.common.enterprise.DocumentType;
import org.openbravo.model.common.enterprise.Organization;
import org.openbravo.model.common.enterprise.OrganizationInformation;

import org.openbravo.model.common.plm.Product;
import org.openbravo.model.common.plm.ProductCategory;

import com.gai.master.bom.GmbBomline;
import com.gai.master.bom.GmbBom;
import org.openbravo.model.common.uom.UOM;
import org.openbravo.model.common.plm.ProductBOM;
import org.openbravo.erpCommon.utility.OBMessageUtils;
import org.openbravo.erpCommon.utility.OBError;
import java.util.StringTokenizer;
import org.openbravo.database.ConnectionProvider;

import org.openbravo.model.marketing.Campaign;
import org.openbravo.model.materialmgmt.cost.ABCActivity;
import org.openbravo.model.project.Project;
import org.openbravo.model.sales.SalesRegion;

public class GenerateBOMDao {



  public GenerateBOMDao() {

  }

  public <T extends BaseOBObject> T getObject(Class<T> t, String strId) {
    return OBDal.getInstance().get(t, strId);
  }


   public FieldProvider[] getDataBomIdTrx(String strBomID){
  //(String strFinancialAccount) {
    String dateFormat = OBPropertiesProvider.getInstance().getOpenbravoProperties()
        .getProperty("dateFormat.java");
    SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormat);
    OBContext.setAdminMode();

    try {
      final StringBuilder whereClause = new StringBuilder();
      final List<Object> parameters = new ArrayList<Object>();

      whereClause.append(" as p ");
      whereClause.append(" where p.");
      whereClause.append(GmbBomline.PROPERTY_GMBBOM);
      whereClause.append(".id = '");
      whereClause.append(strBomID);
      whereClause.append("' ");
      whereClause.append("ORDER BY ");
      whereClause.append(GmbBomline.PROPERTY_LINENO);	  
	  



      final OBQuery<GmbBomline> obqP = OBDal.getInstance().createQuery(GmbBomline.class,
          whereClause.toString(), parameters);

      List<GmbBomline> fdcOBList = obqP.list();

      GmbBomline[] GmbBomline = new GmbBomline[0];
      GmbBomline = fdcOBList.toArray(GmbBomline);
      FieldProvider[] data = FieldProviderFactory.getFieldProviderArray(fdcOBList);

      for (int i = 0; i < data.length; i++) {



        FieldProviderFactory.setField(data[i], "bmid", GmbBomline[i].getId());
        BigDecimal bomQty = GmbBomline[i].getBomQuantity();
      

        String strProductId = "-";
        String strProductName ="-";
        if (GmbBomline[i].getProduct() != null) {
          strProductId = GmbBomline[i].getProduct().getId();
          strProductName = GmbBomline[i].getProduct().getName(); // Product
        }
        FieldProviderFactory.setField(data[i], "productid", strProductId);
        FieldProviderFactory.setField(data[i], "productname",strProductName);


        String strUomId = "-";
        String strUomName ="-";
        if (GmbBomline[i].getUom() != null) {
          strUomId =   GmbBomline[i].getUom().getId();
          strUomName = GmbBomline[i].getUom().getName(); // Uom Name
        }
		
		String NamaProduk = "-";
     
        if (GmbBomline[i].getGMBBom() != null) {
          NamaProduk =   GmbBomline[i].getGMBBom().getProduct().getName();
       
        }
		
        FieldProviderFactory.setField(data[i], "uomid",   strUomId );
        FieldProviderFactory.setField(data[i], "uomname",strUomName );

        FieldProviderFactory.setField(data[i],"qtybom", bomQty.toString());
	

    }
    
    return data;

    } catch (Exception e) {
      throw new OBException(e);

    } finally {
      OBContext.restorePreviousMode();
    } 
  
}


/*
  public FieldProvider[] getDataNamaProduct(String strBomID){
  //(String strFinancialAccount) {
    String dateFormat = OBPropertiesProvider.getInstance().getOpenbravoProperties()
        .getProperty("dateFormat.java");
    SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormat);
    OBContext.setAdminMode();

    try {
      final StringBuilder whereClause = new StringBuilder();
      final List<Object> parameters = new ArrayList<Object>();

      whereClause.append(" as p ");
      whereClause.append(" where p.");
      whereClause.append(GmbBom.PROPERTY_ID);
      whereClause.append(" = '");
      whereClause.append(strBomID);
      whereClause.append("' ");

      final OBQuery<GmbBom> obqP = OBDal.getInstance().createQuery(GmbBom.class,
          whereClause.toString(), parameters);

      List<GmbBom> fdcOBList = obqP.list();

      GmbBom[] GmbBom = new GmbBom[0];
      GmbBom = fdcOBList.toArray(GmbBom);
      FieldProvider[] data = FieldProviderFactory.getFieldProviderArray(fdcOBList);

      for (int i = 0; i < data.length; i++) {

	
		String NamaProduk = "-";
		
		if (GmbBom[i].getProduct() != null) {
         
 		 NamaProduk =   GmbBom[i].getProduct().getName();
       
         }

        FieldProviderFactory.setField(data[i],"nameproductvalue", NamaProduk);
	

    }
    
    return data;

    } catch (Exception e) {
      throw new OBException(e);

    } finally {
      OBContext.restorePreviousMode();
    } 
  
} */




public OBError hapusLines(ConnectionProvider cnn,VariablesSecureApp vars,String produk) {
 OBError myError = null;
  try {
  
        //  String hqlVersionedHapus = "DELETE FROM M_PRODUCT_BOM WHERE M_PRODUCT_ID ='"+produk+"'";
        //  OBDal.getInstance().getSession().createQuery(hqlVersionedHapus).executeUpdate();
		//  OBDal.getInstance().flush();
		 final Product bomyangdihapus = OBDal.getInstance().get(Product.class, produk);
		 // ProductBOM bomyangdihapus = OBDal.getInstance().get(ProductBOM.class, produk);
		 
		 final OBCriteria<ProductBOM> obc = OBDal.getInstance().createCriteria(ProductBOM.class);
       
	      obc.setFilterOnReadableClients(false);
          obc.setFilterOnReadableOrganization(false);
          obc.add(Expression.eq(ProductBOM.PROPERTY_PRODUCT, bomyangdihapus));
		  
		 for (final ProductBOM g : obc.list()) {
    		  OBDal.getInstance().remove(g);
		      OBDal.getInstance().flush();
         }
		  
        } catch (Throwable ex) {
          myError = OBMessageUtils.translateError(cnn, vars, vars.getLanguage(), ex.getMessage());
          return myError;
      }
	  return myError;
}

public OBError copyLines(ConnectionProvider cnn,VariablesSecureApp vars,String strListReqLineId,
      String idproduct,String bommasterid2) {
      StringTokenizer st = new StringTokenizer(strListReqLineId, ",", false);
      OBError myError = null;
     
	 
	        GmbBom MasterBom = OBDal.getInstance().get(GmbBom.class, bommasterid2);
            Product FinishGoods = OBDal.getInstance().get(Product.class, idproduct);
			
	if(MasterBom.getProduct().getId().equals(idproduct)){
	  
	  try {
      long lineno = 0;
     //BigDecimal quantiti = new BigDecimal(kuntiti);
      int count = 0;
      String[] dimensionParameter = new String[2]; // 0 = c_orderline_id , 1 = usedqty , 2 = static qty , 3 = outstandingqty
        while (st.hasMoreTokens()) {
            String temp = st.nextToken().trim();
            dimensionParameter = splitInAndOutToArray(temp);
            String strRownum = dimensionParameter[0];
            GmbBomline bomLineSource = OBDal.getInstance().get(GmbBomline.class, strRownum);
            Product productLineSource = OBDal.getInstance().get(Product.class, idproduct);
            ProductCategory kategoriProduk =  OBDal.getInstance().get(ProductCategory.class,productLineSource.getProductCategory().getId());
            ProductBOM bomLineTarget = OBProvider.getInstance().get(ProductBOM.class);
           
            	
			
            lineno = getMaxLineNoBomLine(idproduct);
            
            bomLineTarget.setActive(true);
            bomLineTarget.setOrganization(bomLineSource.getOrganization());
            bomLineTarget.setLineNo(bomLineSource.getLineNo());
            bomLineTarget.setProduct(productLineSource); 
            bomLineTarget.setBOMProduct(bomLineSource.getProduct()); 
            bomLineTarget.setBOMQuantity(new BigDecimal(dimensionParameter[1]));
            bomLineTarget.setDescription(bomLineSource.getDescription()); 
            bomLineTarget.setScgUom(bomLineSource.getUom().getName()); 
            bomLineTarget.setGmbBom(bomLineSource.getGMBBom());
          
             if(kategoriProduk.isGspPacking() == true){
                  bomLineTarget.setGspIsrm(false);
              }
              
             else {
                  bomLineTarget.setGspIsrm(true);
                 }
             
             

            bomLineTarget.setGspIsrm(kategoriProduk.isGspPacking());
          
            OBDal.getInstance().save(bomLineTarget);
            OBDal.getInstance().flush();
            
            count += 1;
        }
        
        OBDal.getInstance().commitAndClose();
        
        //releaseCommitConnection(conn);
        myError = new OBError();
        myError.setType("Success");
        myError.setTitle(OBMessageUtils.messageBD("Success"));
        myError.setMessage(OBMessageUtils.messageBD("GMB_Berhasil") + " " +count+" "+"Baris");
      } catch (Throwable ex) {
          myError = OBMessageUtils.translateError(cnn, vars, vars.getLanguage(), ex.getMessage());
          //releaseRollbackConnection(cnn);
          return myError;
      }

	}else{
	
	   myError = new OBError();
       myError.setType("Error");
       myError.setTitle(OBMessageUtils.messageBD("Error"));
       myError.setMessage(OBMessageUtils.messageBD("GMB_TidakBerhasil"));
	
	}
			
      return myError;

  }

      public long getMaxLineNoBomLine(String strProductId) {
        long hasil = 0;
        final StringBuilder whereClause = new StringBuilder();
        final List<Object> parameters = new ArrayList<Object>();
        whereClause.append(" as p ");
        whereClause.append(" where p.");
        whereClause.append(ProductBOM.PROPERTY_PRODUCT);
        whereClause.append(".id");
        whereClause.append(" = '");
        whereClause.append(strProductId);
        whereClause.append("' ");
        
        final OBQuery<ProductBOM> obqP = OBDal.getInstance().createQuery(ProductBOM.class,
          whereClause.toString(), parameters);

        List<ProductBOM> bomLineList = obqP.list();
        
        hasil = (bomLineList.size()*1) + 1;
                
        return hasil;
    }

  protected String[] splitInAndOutToArray(String param) {
    String delims = ":";
    int index = 0;
    String[] tokens = param.split(delims);
    return tokens;
  }

}
