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
package com.gai.sutrado.sellingprice.erpCommon.ad_reports;

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
import org.openbravo.data.FieldProvider;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.xmlEngine.XmlDocument;
import org.openbravo.dal.service.OBDal;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.provider.OBProvider;
import com.gai.sutrado.sellingprice.data.GspSellingPrice;
import org.openbravo.model.common.currency.Currency;
//entitas
//import com.gai.sutrado.sellingprice.data.gsp_selling_price;
//import org.openbravo.model.common.businesspartner.BusinessPartner;

public class GspPrint extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
      ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);
//print default pop
    // Get user Client's base currency
    String strUserCurrencyId = Utility.stringBaseCurrencyId(this, vars.getClient());
    if (vars.commandIn("DEFAULT")) {
      String strWindowId = vars.getGlobalVariable("inpwindowId", "GspSellingPrice|Window_ID");
      String strSellingPriceId = vars.getGlobalVariable("inpgspSellingPriceId",strWindowId+"|"+"GSP_Selling_Price_ID");
      String strOrgId = vars.getRequestGlobalVariable("inpadOrgId", "");
      String strTabId = vars.getGlobalVariable("inpTabId", "GspSellingPrice|TabId");
      String strReportTitle = "Selling Price Report";
          
          String strSelling = strSellingPriceId.replace("'","");
          GspSellingPrice gspSellingPrice = OBDal.getInstance().get(GspSellingPrice.class,strSelling);
          String strCurrencyId = gspSellingPrice.getReportingCurrency().getId();



      printPageDataSheet(response,vars,strWindowId,strSelling,strReportTitle,strCurrencyId,strTabId);

    } else if (vars.commandIn("FIND")) {
      String strWindowId = vars.getGlobalVariable("inpwindowId", "GspSellingPrice|Window_ID");
      String strSellingPriceId = vars.getGlobalVariable("inpgspSellingPriceId",strWindowId+"|"+"GSP_Selling_Price_ID");
      String strOrgId = vars.getRequestGlobalVariable("inpadOrgId", "");
      String strTabId = vars.getGlobalVariable("inpTabId", "GspSellingPrice|TabId");

          String strSelling = strSellingPriceId.replace("'","");
   

          GspSellingPrice gspSellingPrice = OBDal.getInstance().get(GspSellingPrice.class,strSelling);
          String strCurrencyId = gspSellingPrice.getReportingCurrency().getId();


      String strReportTitle = vars.getRequestGlobalVariable("inpTitleReport", "");
      String strOutput = "pdf";
      String strDebug = strOrgId;

      printPageDataSheet(response,vars,strWindowId,strSellingPriceId,strReportTitle,strCurrencyId,strTabId);

    } else if (vars.commandIn("PRINT_SELLING_PRICE")) {
      String strIdList = vars.getRequestGlobalVariable("inpgspSellingPriceId", "");
      String strOutput = "pdf";
      String strReportFile = "SellingPriceReport";
      
          String strSelling = strIdList.replace("'","");

          GspSellingPrice gspSellingPrice = OBDal.getInstance().get(GspSellingPrice.class,strSelling);
          String strCurrencyId = gspSellingPrice.getReportingCurrency().getId();


      log4j.debug("Output: "+strIdList);

      printOut(request,response,vars,strIdList,strCurrencyId,strOutput,strReportFile,null);
    } 

    else if (vars.commandIn("PRINT_PRICELIST")) {
      String strIdList = vars.getRequestGlobalVariable("inpgspSellingPriceId", "");
      String strIdListHeader = vars.getRequestGlobalVariable("inpgspSellingPriceHeaderId", "");
      String strOutput = "pdf";
      String strReportFile = "Report_PriceList";
      //strIdListHeader = "'"+strIdListHeader+"'";

          String strSelling = strIdListHeader.replace("'","");

          GspSellingPrice gspSellingPrice = OBDal.getInstance().get(GspSellingPrice.class,strSelling);
          String strCurrencyId = gspSellingPrice.getReportingCurrency().getId();

     log4j.debug("Output: "+strIdList);

      printOut(request,response,vars,strIdList,strCurrencyId,strOutput,strReportFile,strIdListHeader);

    } else
      pageError(response);
  }


void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars,
      String strWindowId,String strSelling,String strReportTitle,String strCurrency,String strTabId) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

    XmlDocument xmlDocument = null;
    xmlDocument = xmlEngine.readXmlTemplate(
        "com.gai.sutrado.sellingprice.erpCommon.ad_reports.GspPrint").createXmlDocument();

    ToolBar toolbar = new ToolBar(this, vars.getLanguage(),
        "GspPrint", false, "", "", "", false, "ad_reports",
        strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());
    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.gai.sutrado.sellingprice.erpCommon.ad_reports.GspPrint");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(),
          "GspPrint.html", classInfo.id, classInfo.type, strReplaceWith, tabs
              .breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "GspPrint.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
    {
      OBError myMessage = vars.getMessage("ReportInvoiceDiscountJR");
      vars.removeMessage("ReportInvoiceDiscountJR");
      if (myMessage != null) {
        xmlDocument.setParameter("messageType", myMessage.getType());
        xmlDocument.setParameter("messageTitle", myMessage.getTitle());
        xmlDocument.setParameter("messageMessage", myMessage.getMessage());
      }
    }

    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("calendar", vars.getLanguage().substring(0, 2));
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");
    xmlDocument.setParameter("paramCurrencyName", strCurrency);
    xmlDocument.setParameter("TitleReport", strReportTitle);
    xmlDocument.setParameter("title", "Report Coba print");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(xmlDocument.print());
    out.close();
  }


  void printOut(HttpServletRequest request, HttpServletResponse response,
      VariablesSecureApp vars, String strIdList,String strCurrencyId,String strOutput,String strReportFile ) throws IOException, ServletException {
        printOut(request, response,
      vars,  strIdList,strCurrencyId,strOutput,strReportFile,null);
  }



  void printOut(HttpServletRequest request, HttpServletResponse response,
      VariablesSecureApp vars, String strIdList,String strCurrencyId,String strOutput,String strReportFile,String strIdListHeader  ) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

    String strJudul = "";
    String strReportName = "@basedesign@/com/gai/sutrado/sellingprice/erpCommon/ad_reports/"+strReportFile+".jrxml";
    response.setHeader("Content-disposition", "inline; filename="+strReportFile+".pdf");

    GspPrintData[] data = null;
    String strConvRateErrorMsg = "";
    OBError myMessage = null;
    myMessage = new OBError();
    try {
      if (strReportFile.equals("SellingPriceReport")) {

         strJudul="NetPriceReport";

        data = GspPrintData.select(this, strIdList,strCurrencyId);  
      }
      if (strReportFile.equals("DetailSellingPrice")) {

                 strJudul="DetailNetPrice";


        data = GspPrintData.selectline(this, strIdList);  
      }

      if (false) {
        //data = GspPrintData.select(this, strCobaPrintId);  
      }
      
    } catch (ServletException ex) {
      myMessage = Utility.translateError(this, vars, vars.getLanguage(), ex.getMessage());
    }
    strConvRateErrorMsg = myMessage.getMessage();
    // If a conversion rate is missing for a certain transaction, an error
    // message window pops-up.
    if (!strConvRateErrorMsg.equals("") && strConvRateErrorMsg != null) {
      advisePopUp(request, response, "ERROR", /*Utility.messageBD(this, "NoConversionRateHeader",
          vars.getLanguage())*/" Print ERROR", strConvRateErrorMsg);
    } else { // Launch the report as usual, calling the JRXML file*/
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("GSP_Selling_Price_ID", strIdListHeader);
      parameters.put("GSP_Sellprice_Line_ID"," AND gsl.gsp_sellprice_line_id IN ("+strIdList+") ");

      if (strIdListHeader == null) {
        renderJR(vars, response, strReportName,strJudul,"pdf", parameters, data, null);  
      } else {
        FieldProvider[] kosong = null;
        strJudul="PriceList";
        renderJR(vars, response, strReportName,strJudul,"pdf", parameters, kosong, null);  
      }
      
    }
  }

  public String getServletInfo() {
    return "Servlet CobaReport. This Servlet was made by Moch Fachmi Rizal";
  } // end of getServletInfo() method
}

