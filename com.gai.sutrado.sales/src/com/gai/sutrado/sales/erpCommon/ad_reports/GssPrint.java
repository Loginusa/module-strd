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
package com.gai.sutrado.sales.erpCommon.ad_reports;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.ComboTableData;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.xmlEngine.XmlDocument;
import org.openbravo.dal.service.OBDal;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.provider.OBProvider;
import org.openbravo.model.common.order.Order;
import org.openbravo.model.common.currency.Currency;
import org.openbravo.data.FieldProvider;


public class GssPrint extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
      ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);
//print default pop
    if (vars.commandIn("PRINT_ORDER_PRODUKSI")) {
      String strIdList = vars.getRequestGlobalVariable("inpcOrderId", "");
      String strOutput = "pdf";
      String strReportFile = "Rpt_OrderProduksi";
      String strSelling = strIdList.replace("'","");


      log4j.debug("Output: "+strIdList);

      printOut(request,response,vars,strSelling,strOutput,strReportFile);
    } 

      else
      pageError(response);
   }

  void printOut(HttpServletRequest request, HttpServletResponse response,
      VariablesSecureApp vars, String strIdList,String strOutput,String strReportFile ) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

     String strJudul = "OrderProduksi";


    String strReportPath = "@basedesign@/com/gai/sutrado/sales/erpCommon/ad_reports/"+strReportFile+".jrxml";
    response.setHeader("Content-disposition", "inline; filename="+strJudul+".pdf");
  
    String strConvRateErrorMsg = "";
    OBError myMessage = null;
    myMessage = new OBError();
    

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("DOCUMENT_ID", strIdList);

       FieldProvider[] data = null;


      renderJR(vars, response,strReportPath,strJudul,"pdf",parameters,data,null);
  
     
  
}
  public String getServletInfo() {
    return "Servlet CobaReport. This Servlet was made by Tias ade putra";
  } // end of getServletInfo() method
}

