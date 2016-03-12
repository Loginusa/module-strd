/*
    PT. Global Anugerah Indonesia    
*/
package com.gai.sutrado.sellingprice.erpCommon.ad_reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.base.exception.OBException;
import org.openbravo.erpCommon.utility.NavigationBar;
import org.openbravo.erpCommon.businessUtility.WindowTabs;
import org.openbravo.erpCommon.utility.LeftTabsBar;
import org.openbravo.erpCommon.utility.ToolBar;
import org.openbravo.xmlEngine.XmlDocument;

//import com.gai.investasi.erpCommon.ad_reports.ToolbarPrintData;

//entitas

public class GspToolbarPrint extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
      ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);

    // Get user Client's base currency
    String strUserCurrencyId = Utility.stringBaseCurrencyId(this, vars.getClient());
    if (vars.commandIn("DEFAULT")) {      
      String strWindowId = vars.getGlobalVariable("inpadWindowId", "Net Price|Window_ID");
      String strTabID = vars.getGlobalVariable("inpTabId","Net Price|Tab_ID");
      String strRecordId = vars.getRequestGlobalVariable("inpRecordId", "");
      //String strKepada = vars.getStringParameter("inpKepada", "");
      //String strDari = vars.getStringParameter("inpDari", "");
      //String strPerihal = vars.getStringParameter("inpPerihal", "");

      printPageDataSheet(response, vars, strRecordId);//, strKepada, strDari, strPerihal);
  
    } else if (vars.commandIn("PRINT_GL")) {
      String strRecordId = vars.getRequestGlobalVariable("inpRecordId", "");
      String reportPath = "com/gai/sutrado/sellingprice/erpCommon/ad_reports/Report_PriceList.jrxml";//path report
      String reportname ="ReportPriceList";
      String strOutput = "pdf";
      //String strKepada = vars.getStringParameter("inpKepada", "");
      //String strDari = vars.getStringParameter("inpDari", "");
      //String strPerihal = vars.getStringParameter("inpPerihal", "");

      printPageDataOut(request, response, vars,strRecordId,reportPath,reportname,strOutput);//,strKepada,strDari, strPerihal);
    } /*else if (vars.commandIn("PRINT_REALISASI")) {
      String strRecordId = vars.getRequestGlobalVariable("inpRecordId", "");
      String reportPath = "com/gai/umk/erpCommon/ad_reports/Laporan_PertanggungJawaban.jrxml";//path report
      String reportname ="LaporanPertanggungJawabanUangMukaKerja";
      String strOutput = "pdf";
      String strKepada = vars.getStringParameter("inpKepada", "");
      String strDari = vars.getStringParameter("inpDari", "");
      String strPerihal = vars.getStringParameter("inpPerihal", "");

      printPageDataOut(request, response, vars,strRecordId,reportPath,reportname,strOutput,strKepada,strDari, strPerihal);
    }*/
     else
      pageError(response);
  }

  void printPageDataOut(HttpServletRequest request, HttpServletResponse response,
      VariablesSecureApp vars, String strRecordId ,String reportPath, String reportname, String strOutput
      ) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

      log4j.debug("Record ID : "+strRecordId);

    String strReportPath = "@basedesign@/"+reportPath;    
    // Retrieve Data dari database
    GspToolbarPrintData[] data = null;
    OBError myMessage = null;
    myMessage = new OBError();
      
    /*try {
      if (reportname.equals("ReportUangMukaKerja")){
        data = GspToolbarPrintData.select(this,strRecordId);  
      }
    } catch (ServletException ex) {
        myMessage = Utility.translateError(this, vars, vars.getLanguage(), ex.getMessage());
    }
*/
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("GSP_Selling_Price_ID", strRecordId);
    renderJR(vars, response, strReportPath, strOutput, parameters, null, null);
  }

  void printPageDataSheet(HttpServletResponse response, VariablesSecureApp vars, 
      String strRecordId) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

    XmlDocument xmlDocument = null;
    xmlDocument = xmlEngine.readXmlTemplate(
        "com/gai/sutrado/sellingprice/erpCommon/ad_reports/GspToolbarPrint").createXmlDocument();
    ToolBar toolbar = new ToolBar(this, vars.getLanguage(),
        "GspToolbarPrint", false, "", "", "", false, "ad_reports",
        strReplaceWith, false, true);
    toolbar.prepareSimpleToolBarTemplate();
    xmlDocument.setParameter("toolbar", toolbar.toString());
    try {
      WindowTabs tabs = new WindowTabs(this, vars,
          "com.gai.sutrado.sellingprice.erpCommon.ad_reports.GspToolbarPrint");
      xmlDocument.setParameter("parentTabContainer", tabs.parentTabs());
      xmlDocument.setParameter("mainTabContainer", tabs.mainTabs());
      xmlDocument.setParameter("childTabContainer", tabs.childTabs());
      xmlDocument.setParameter("theme", vars.getTheme());
      
      NavigationBar nav = new NavigationBar(this, vars.getLanguage(),
          "GspToolbarPrint.html", classInfo.id, classInfo.type, strReplaceWith, tabs
              .breadcrumb());
      xmlDocument.setParameter("navigationBar", nav.toString());
      LeftTabsBar lBar = new LeftTabsBar(this, vars.getLanguage(), "GspToolbarPrint.html",
          strReplaceWith);
      xmlDocument.setParameter("leftTabs", lBar.manualTemplate());
    } catch (Exception ex) {
      throw new ServletException(ex);
    }

    {
      OBError myMessage = vars.getMessage("GspToolbarPrint");
      vars.removeMessage("GspToolbarPrint");
      if (myMessage != null) {
        xmlDocument.setParameter("messageType", myMessage.getType());
        xmlDocument.setParameter("messageTitle", myMessage.getTitle());
        xmlDocument.setParameter("messageMessage", myMessage.getMessage());
      }
    }

    xmlDocument.setParameter("paramLanguage", "defaultLang=\"" + vars.getLanguage() + "\";");
    xmlDocument.setParameter("directory", "var baseDirectory = \"" + strReplaceWith + "/\";\n");

    xmlDocument.setParameter("title", "Price List");      
    //xmlDocument.setParameter("strKepada", strKepada);
    //xmlDocument.setParameter("strDari", strDari);
    //xmlDocument.setParameter("strPerihal", strPerihal);
    

    /*try {
      String Storage_Type_Reference_ID = "E6E29CF3D37A49D2BFDA81A30B0E3D1D";
      ComboTableData comboTableDataStorage = new ComboTableData(vars, this, "LIST", "",
          Storage_Type_Reference_ID, "", Utility.getContext(this, vars,
              "#AccessibleOrgTree", "AssetPossition"), Utility.getContext(this, vars,
              "#User_Client", "AssetPossition"), 0);
      Utility.fillSQLParameters(this, vars, null, comboTableDataStorage, "AssetPossition", "");
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
    return "Servlet Toolbar Print for Investasi.";
  } // end of getServletInfo() method
}