/*
     2  *************************************************************************
     3  * The contents of this file are subject to the Openbravo  Public  License
     4  * Version  1.0  (the  "License"),  being   the  Mozilla   Public  License
     5  * Version 1.1  with a permitted attribution clause; you may not  use this
     6  * file except in compliance with the License. You  may  obtain  a copy of
     7  * the License at http://www.openbravo.com/legal/license.html
     8  * Software distributed under 
    the License  is  distributed  on  an "AS IS"
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
package com.gai.sizeCodeGenerate.erpCommon.ad_process;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.openbravo.erpCommon.utility.DateTimeData;
import org.openbravo.erpCommon.utility.ComboTableData;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.xmlEngine.XmlDocument;
import org.openbravo.dal.service.OBDal;
import org.openbravo.dal.core.DalUtil;
import org.openbravo.dal.core.OBContext;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.service.db.CallStoredProcedure;
//entitas

public class GenerateMaterial extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
      ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);

    // Get user Client's base currency
    if (vars.commandIn("DEFAULT")) {
      String strWindowId = vars.getGlobalVariable("inpwindowId", "Product|Window_ID");
      String strTabID = vars.getGlobalVariable("inpTabId","Product|Tab_ID");
//      String strOrg = vars.getRequestGlobalVariable("inpadOrgId", "");
//      String strAssetID = vars.getStringParameter("inpaAssetId", "");
//      String strDate = vars.getStringParameter("inpDateFrom", 
//             DateTimeData.today(this));
//      String strMovement = vars.getStringParameter("inpProcess", "");
//      String strProduct = vars.getStringParameter("inpmProductId", "");
      //nurdin untuk mendapatkan id produk category
      String strProductCategory = vars.getStringParameter("inpmProductCategoryId", "");
      String strProductId = vars.getStringParameter("inpmProductId", "");
//      String strLocator = vars.getStringParameter("inpmLocatorId", "");
      //String strPIC = vars.getStringParameter("inpemGasNamapemegang", "");

      printPageDataSheet(response, vars, strProductCategory, strProductId);

    } else if (vars.commandIn("PROCESS")) {
      String strWindowId = vars.getGlobalVariable("inpwindowId", "Product|Window_ID");
      String strTabID = vars.getGlobalVariable("inpTabId","Product|Tab_ID");
//      String strAssetID = vars.getStringParameter("strAssetID", "");
//      String strGmmMasterDepartmentId = vars.getStringParameter("inpGmmMasterDepartmentId");
//      String strDate = vars.getStringParameter("inpDateFrom");
//      String strMovement = vars.getStringParameter("inpProcess");
//      String strBusinesspartner = vars.getStringParameter("inpcBPartnerId");
//      String strGroupMaterial = vars.getStringParameter("inpGroupMaterialId");
//      String strTax = vars.getStringParameter("inpcTaxId");
//      String strPriceList = vars.getStringParameter("inpmPriceListId"); 
//      String strLocator = vars.getStringParameter("inpLocatorId");
//      String strPIC = vars.getStringParameter("inpAdUserId");
//      String strNetUnitPrice = vars.getStringParameter("inpNetUnitPrice");

      String v_group_material = vars.getStringParameter("inpGroupMaterialId");
      String v_nama_bahan_baku = vars.getStringParameter("inpNamaBahanBakuId");
      String v_jenis_bahan_baku = vars.getStringParameter("inpJenisBahanBakuId"); 
      String v_ukuran_produk = vars.getStringParameter("inpUkuranProdukId");
      String v_type_material = vars.getStringParameter("inpMaterialType");            
      String v_Record_ID = vars.getStringParameter("strProductId");

      processAndClose(request, response, vars, strTabID, v_group_material, v_nama_bahan_baku, v_jenis_bahan_baku, v_ukuran_produk, v_type_material, v_Record_ID);
    } else
      pageError(response);
  }

  void processAndClose(HttpServletRequest request, HttpServletResponse response,
      VariablesSecureApp vars, String strTabId, String v_group_material, String v_nama_bahan_baku, 
      String v_jenis_bahan_baku, String v_ukuran_produk, String v_type_material, String v_Record_ID) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

      String strErrorMsg = "";
      OBError myMessage = new OBError();

      strErrorMsg = myMessage.getMessage();
      
      try {
        
            List<Object> params = new ArrayList<Object>();
            String p_Result = null;

            params.add(v_group_material);
            params.add(v_nama_bahan_baku);
            params.add(v_jenis_bahan_baku);
            params.add(v_ukuran_produk);
            params.add(v_type_material);
            params.add(p_Result);
            params.add(v_Record_ID);
            CallStoredProcedure.getInstance().call("scg_generatematrial_java", params, null, true, false);

            myMessage.setTitle("Success");
            myMessage.setType("SUCCESS");
            myMessage.setMessage(p_Result);

      } catch (Exception e) {
        OBDal.getInstance().rollbackAndClose();
        log4j.info(e.getMessage());
        myMessage = Utility.translateError(this, vars, vars.getLanguage(), e.getMessage());
        throw new IllegalStateException(e);
      }

      //OBDal.getInstance().commitAndClose();

      /*myMessage.setTitle("Success");
      myMessage.setType("SUCCESS");
      myMessage.setMessage("Asset has been processed");
      */
      vars.setMessage("105", myMessage);

      printPageClosePopUpAndRefreshParent(response, vars);

  }

  void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars, String strProductCategory, String strProductId) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

    XmlDocument xmlDocument = null;
    xmlDocument = xmlEngine.readXmlTemplate(
        "com/gai/sizeCodeGenerate/erpCommon/ad_process/GenerateMaterial").createXmlDocument();
    ToolBar toolbar = new ToolBar(this, vars.getLanguage(),
        "GenerateMaterial", false, "", "", "", false, "ad_reports",
        strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());
    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.gai.sizeCodeGenerate.erpCommon.ad_process.GenerateMaterial");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(),
          "GenerateMaterial.html", classInfo.id, classInfo.type, strReplaceWith, tabs
              .breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "GenerateMaterial.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    {
      OBError myMessage = vars.getMessage("GenerateMaterial");
      vars.removeMessage("GenerateMaterial");
      if (myMessage != null) {
        xmlDocument.setParameter("messageType", myMessage.getType());
        xmlDocument.setParameter("messageTitle", myMessage.getTitle());
        xmlDocument.setParameter("messageMessage", myMessage.getMessage());
      }
    }

    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");

    xmlDocument.setParameter("title", "Asset Possition");      
    //
    xmlDocument.setParameter("strProductCategoryID", strProductCategory);
    xmlDocument.setParameter("strProductId", strProductId);
    xmlDocument.setParameter("dateFromdisplayFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    xmlDocument.setParameter("dateFromsaveFormat", vars.getSessionValue("#AD_SqlDateFormat"));
    //xmlDocument.setParameter("strMovement", strMovement);

    //strMovement = "PD";
    try {
      String Material_Type_Reference_ID = "34F03A314FCE44B1A415CA58EB2B0099";
      ComboTableData comboTableData = new ComboTableData(vars, this, "LIST", "",
          Material_Type_Reference_ID, "", Utility.getContext(this, vars,
              "#AccessibleOrgTree", "GenerateMaterial"), Utility.getContext(this, vars,
              "#User_Client", "GenerateMaterial"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableData, "GenerateMaterial", "");
      xmlDocument.setData("reportMaterialType", "liststructure", comboTableData.select(false));
      comboTableData = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    /*try {
      String Storage_Type_Reference_ID = "E6E29CF3D37A49D2BFDA81A30B0E3D1D";
      ComboTableData comboTableDataStorage = new ComboTableData(vars, this, "LIST", "",
          Storage_Type_Reference_ID, "", Utility.getContext(this, vars,
              "#AccessibleOrgTree", "GenerateMaterial"), Utility.getContext(this, vars,
              "#User_Client", "GenerateMaterial"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableDataStorage, "GenerateMaterial", "");
      xmlDocument.setData("M_Locator_ID", "liststructure", comboTableDataStorage.select(false));
      comboTableDataStorage = null;
    } catch (Exception ex) {
      throw new ServletException(ex);
    }*/

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();
  }

  public String getServletInfo() {
    return "Servlet Asset Possition. This Servlet was made by GAI";
  } // end of getServletInfo() method
}
