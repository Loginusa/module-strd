/*
     2  *************************************************************************
     3  * The contents of this file are subject to the Openbravo  Public  License
     4  * Version  1.0  (the  "License"),  being   the  Mozilla   Public  License
     5  * Version 1.1  with a permitted attribution clause; you may not  use this
     6  * file except in compliance with the License. You  may  obtain  a copy of
     7  * the License at http://www.openbravo.com/legal/license.html
     8  * Software distributed under the License  is  distributed  on  an "AS IS"
     9  * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
    10  * License for the specific  language  governing  rights  and  limitations
    11  * under the License.
    12  * The Original Code is Openbravo ERP.
    13  * The Initial Developer of the Original Code is Openbravo SL
    14  * All portions are Copyright (C) 2001-2008 Openbravo SL
    15  * All Rights Reserved.
    16  * Contributor(s):  ______________________________________.
    17  ************************************************************************
    18  */
package com.gai.master.bom.erpCommon.ad_process;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.gai.master.bom.GmbBom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.criterion.Restrictions;

import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.data.FieldProvider;
import org.openbravo.erpCommon.utility.FieldProviderFactory;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.ComboTableData;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.xmlEngine.XmlDocument;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.dal.core.DalUtil;
import org.openbravo.dal.core.OBContext;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.service.db.CallStoredProcedure;
import org.openbravo.model.common.plm.Product;


 import org.hibernate.Criteria;
 import org.hibernate.criterion.Expression;


//entitas

public class GenerateBOM extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;
  private GenerateBOMDao dao;
  private GenerateBOMDao dao2;

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
      ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);

    // Get user Client's base currency
    String strUserCurrencyId = Utility.stringBaseCurrencyId(this, vars.getClient());
    if (vars.commandIn("DEFAULT")) {
      String strWindowId = vars.getGlobalVariable("inpwindowId", "Product|Window_ID");
      String strID = vars.getGlobalVariable("inpmproductId",strWindowId+"|"+"M_Product_ID");
      String strOrgId = vars.getRequestGlobalVariable("inpadOrgId", "");
      String strBusinessPartnerId = vars.getRequestGlobalVariable("inpcBPartnerId", "");
      String strDescription = vars.getRequestGlobalVariable("inpDesc", "");
      String strReportTitle = vars.getRequestGlobalVariable("inpTitleReport", "");
      String strOutput = "";
      strBusinessPartnerId = "'"+strBusinessPartnerId+"'";
      strID = "'"+strID+"'";
      String strTabId = vars.getGlobalVariable("inpTabId", "Product|Tab_ID");
      String strDebug = strTabId;

      printPageDataSheet(response, vars,strWindowId,strReportTitle, strID,strBusinessPartnerId,strDescription,strDebug);

    } else if (vars.commandIn("OK")) {
      String strWindowId = vars.getGlobalVariable("inpwindowId", "Product|Window_ID");
      String strTabId = vars.getStringParameter("inpTabId");
      String strID = vars.getGlobalVariable("inpmproductId",strWindowId+"|"+"M_Product_ID");
      String strDebug = strID;
      
      log4j.debug("list param : "+strID);


	  
    	  String bomid = vars.getStringParameter("inpgmbBomID");
		  String bomid2 = "";
       
	     Product product = OBDal.getInstance().get(Product.class, strID);
	  
	 
		 
		 final OBCriteria<GmbBom> obc = OBDal.getInstance().createCriteria(GmbBom.class);
       
	      obc.setFilterOnReadableClients(false);
          obc.setFilterOnReadableOrganization(false);
          obc.add(Expression.eq(GmbBom.PROPERTY_PRODUCT, product));
	     
		 
		 	 for (final GmbBom g : obc.list()) {
    		  
			  bomid2 = g.getId();
			  
             }
	  
	  
	  
      
	  if(bomid==null || bomid.equals("")){
	    bomid = bomid2;
	  }else{
	  bomid = bomid;
	  }
	  
	  
	  
	  
      String strRownum = vars.getRequiredStringParameter("strListBomId"); 
      
      String debugText = "list Master Bom: ["+strID+"] / ["+bomid+"] / ["+strRownum+"] / ["+""+"]";
        // log4j.error(debugText);
      if (false) {
          throw new OBException(debugText);
      }
        
         OBError myErrorhapus = null;
	     myErrorhapus = dao.hapusLines(this,vars,strID);
		 
		 
		 
		 
	   	
		
      OBError myError = null;
      myError = dao.copyLines(this,vars, strRownum, strID,bomid); //isi dengan output yang seuai
      
      String strWindowPath = Utility.getTabURL("26ABE4232053486EBDF30FB6CE7B6511", "R", true);
      if (strWindowPath.equals(""))
        strWindowPath = strDefaultServlet;

      vars.setMessage("26ABE4232053486EBDF30FB6CE7B6511", myError);
      printPageClosePopUp(response, vars, strWindowPath);

    } else if (vars.commandIn("GRID")) {
    String strWindowId = vars.getGlobalVariable("inpwindowId", "Product|Window_ID");   
    String productID = vars.getGlobalVariable("inpmproductId",strWindowId+"|"+"M_Product_ID");
    	  
		  String bomid = vars.getStringParameter("inpgmbBomID");
		  String bomid2 = "";
       
	     Product product = OBDal.getInstance().get(Product.class, productID);
	  
	 
		 
		 final OBCriteria<GmbBom> obc = OBDal.getInstance().createCriteria(GmbBom.class);
       
	      obc.setFilterOnReadableClients(false);
          obc.setFilterOnReadableOrganization(false);
          obc.add(Expression.eq(GmbBom.PROPERTY_PRODUCT, product));
	     
		 
		 	 for (final GmbBom g : obc.list()) {
    		  
			  bomid2 = g.getId();
			  
             }
	  
	  
	  
      
	  if(bomid==null || bomid.equals("")){
	    bomid = bomid2;
	  }else{
	  bomid = bomid;
	  }
	  
	  
	    printGrid(response, vars,bomid);
	  
	  
	XmlDocument xmlDocument = null;
    xmlDocument = xmlEngine.readXmlTemplate(
        "com/gai/master/bom/erpCommon/ad_process/GenerateBOM").createXmlDocument();

    try {	
	
	xmlDocument.setParameter("MasterBomID", bomid);
    } catch (Exception ex) {
      throw new ServletException(ex);
    }


    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");

    xmlDocument.setParameter("title", "Generate BOM");
   
    
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();
	  
	  
	

    

     
    } else if (vars.commandIn("FIELD")) {

	
	    String strWindowId = vars.getGlobalVariable("inpwindowId", "Product|Window_ID");   
    String productID = vars.getGlobalVariable("inpmproductId",strWindowId+"|"+"M_Product_ID");
    	  
		  String bomid = vars.getStringParameter("inpgmbBomID");
		  String bomid2 = "";
       
	     Product product = OBDal.getInstance().get(Product.class, productID);
	  
	 
		 
		 final OBCriteria<GmbBom> obc = OBDal.getInstance().createCriteria(GmbBom.class);
       
	      obc.setFilterOnReadableClients(false);
          obc.setFilterOnReadableOrganization(false);
          obc.add(Expression.eq(GmbBom.PROPERTY_PRODUCT, product));
	     
		 
		 	 for (final GmbBom g : obc.list()) {
    		  
			  bomid2 = g.getId();
			  
             }
	  
	  
	  
      
	  if(bomid==null || bomid.equals("")){
	    bomid = bomid2;
	  }else{
	  bomid = bomid;
	  }
	  
	  
	     GetNamaProduct(response, vars,bomid);
	  
	  
	XmlDocument xmlDocument = null;
    xmlDocument = xmlEngine.readXmlTemplate(
        "com/gai/master/bom/erpCommon/ad_process/GenerateBOM").createXmlDocument();

    try {	
	
	xmlDocument.setParameter("MasterBomID", bomid);
    } catch (Exception ex) {
      throw new ServletException(ex);
    }


    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");

    xmlDocument.setParameter("title", "Generate BOM");
   
    
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();	    
	  
    } 
	
	else
      pageError(response);
  }

  
  private void printGrid(HttpServletResponse response, VariablesSecureApp vars,String strBomId) throws IOException, ServletException {
    dao = new GenerateBOMDao();
    log4j.debug("Output: Pemanggilan Grid");


	
	

    XmlDocument xmlDocument = xmlEngine.readXmlTemplate(
        "com/gai/master/bom/erpCommon/ad_process/GenerateBOMGrid").createXmlDocument();
    OBContext.setAdminMode();
     

    final FieldProvider[] data;

      log4j.debug("masuk Grid dengan data dari BOM");
      data = dao.getDataBomIdTrx(strBomId);
	  
	

    xmlDocument.setData("structure", (data == null) ? set() : data);
    JSONObject table = new JSONObject();
    try {
      table.put("grid", xmlDocument.print());
      //table.put("closeAutomatically", closeAutomatically);
    } catch (JSONException e) {
      log4j.debug("JSON object error" + table.toString());
    }
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("data = " + table.toString());
    out.close();


	
  }
  
  
  private FieldProvider[] set() throws ServletException {
    HashMap<String, String> empty = new HashMap<String, String>();
    empty.put("bmid", "");
    empty.put("productid", "");
    empty.put("productname", "");
    empty.put("uomid", "");
    empty.put("uomname", "");
    empty.put("qtybom", "");

   

    ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    result.add(empty);
    return FieldProviderFactory.getFieldProviderArray(result);
  }

  void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars,
      String strWindowId,String strReportTitle,String  strID,String strBusinessPartnerId,String strDescription,String strDebug) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

    XmlDocument xmlDocument = null;
    xmlDocument = xmlEngine.readXmlTemplate(
        "com/gai/master/bom/erpCommon/ad_process/GenerateBOM").createXmlDocument();
    ToolBar toolbar = new ToolBar(this, vars.getLanguage(),
        "GenerateBOM", false, "", "", "", false, "ad_reports",
        strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());
    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.gai.master.bom.erpCommon.ad_process.GenerateBOM");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(),
          "GenerateBOM.html", classInfo.id, classInfo.type, strReplaceWith, tabs
              .breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "GenerateBOM.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    {
      OBError myMessage = vars.getMessage("GenerateBOM");
      vars.removeMessage("GenerateBOM");
      if (myMessage != null) {
        xmlDocument.setParameter("messageType", myMessage.getType());
        xmlDocument.setParameter("messageTitle", myMessage.getTitle());
        xmlDocument.setParameter("messageMessage", myMessage.getMessage());
      }
    }

    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("Description", strDescription);
    xmlDocument.setParameter("TitleReport", strDebug);
    xmlDocument.setParameter("title", "Generate BOM");
   
    
  response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();
  }
  
  
  

  
  
 private void GetNamaProduct(HttpServletResponse response, VariablesSecureApp vars,String strBomId) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: Percobaan Ambil Field");

    XmlDocument xmlDocument = null;
    xmlDocument = xmlEngine.readXmlTemplate(
        "com/gai/master/bom/erpCommon/ad_process/GenerateBOMField").createXmlDocument();

    try {
	GmbBom bom = OBDal.getInstance().get(GmbBom.class,strBomId);
	
	
	
	xmlDocument.setParameter("NameProductValue", bom==null ? "" : bom.getProduct().getName());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }


    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");

    xmlDocument.setParameter("title", "Generate BOM");
   
    
  response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();
  } 
  
  

  //method custom
  protected String[] convertToArray(String param) {
    String delims = ",";
    //int index = 0;
    String[] tokens = param.split(delims);

    return tokens;
  }

  public String getServletInfo() {
    return "Servlet GenerateBOM. This Servlet was made by Tias Ade Putra";
  } // end of getServletInfo() method
}
