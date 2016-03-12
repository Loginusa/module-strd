/*
    PT. Global Anugerah Indonesia    
*/
package com.gai.inventory.erpCommon.ad_reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
//tambahan crmspy@gmail.com
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openbravo.base.exception.OBException;
import org.openbravo.base.secureApp.HttpSecureAppServlet;
import org.openbravo.base.secureApp.VariablesSecureApp;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.model.common.enterprise.DocumentTemplate;
import org.openbravo.model.materialmgmt.transaction.InventoryCount;
import org.openbravo.service.db.CallStoredProcedure;
import org.openbravo.data.FieldProvider;


public class ToolbarPrint extends HttpSecureAppServlet {
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    VariablesSecureApp vars = new VariablesSecureApp(request);

    // Get user Client's base currency
    String strUserCurrencyId = Utility.stringBaseCurrencyId(this, vars.getClient());
    if (vars.commandIn("DEFAULT")) {
      //not implemented / not yet
    } else if (vars.commandIn("PRINT_OGS")) {
      String strRecordId = vars.getRequestGlobalVariable("inpRecordId", "");
      String reportPath = "com/gai/othergoodsshipment/erpCommon/ad_reports/OtherGoodShipment.jrxml";
      String reportname = "Other Goods Shipment";
      String strOutput = "pdf";

      printPageDataOut(request, response, vars, strRecordId, reportPath, reportname, strOutput);
    } else if (vars.commandIn("PRINT_OGR")) {
      String strRecordId = vars.getRequestGlobalVariable("inpRecordId", "");
      String reportPath = "com/gai/othergoodsreceipt/erpCommon/ad_reports/OtherGoogsReceipt.jrxml";
      String reportname = "Other Goods Receipt";
      String strOutput = "pdf";

      printPageDataOut(request, response, vars, strRecordId, reportPath, reportname, strOutput);
    } else
      pageError(response);

  }

  void printPageDataOut(HttpServletRequest request, HttpServletResponse response,
      VariablesSecureApp vars, String strRecordId, String reportPath, String reportname,
      String strOutput) throws IOException, ServletException {
    if (log4j.isDebugEnabled())
      log4j.debug("Output: dataSheet");

    log4j.debug("Record ID : " + strRecordId);

    String strReportPath = "@basedesign@/" + reportPath;
    if (strOutput.equals("pdf"))

      if (false) {
        String debugText = "Path : " + strReportPath + " | " + strRecordId;
        throw new OBException(debugText);
      }

    DocumentTemplate dt = null;
    String tempstrRecordId = strRecordId.replaceAll("'","");
    InventoryCount inventory = OBDal.getInstance().get(InventoryCount.class,tempstrRecordId );
    log4j.debug("Record Id : "+strRecordId);

    if (reportname.equals("Other Goods Receipt")){
    	log4j.debug("DocType : "+inventory.getGinDoctypeor().toString());
    	if (inventory.getGinDoctypeor().getDocumentTemplateList().size() > 0) {
    		dt = inventory.getGinDoctypeor().getDocumentTemplateList().get(0);

	    } else {
	      throw new OBException("Report document for this template is not specified : "
	          + inventory.getGinDoctypeor().getPrintText());
	    }

    }else if (reportname.equals("Other Goods Shipment")){

    	if (inventory.getGinDoctypeos().getDocumentTemplateList().size() > 0) {
	      dt = inventory.getGinDoctypeos().getDocumentTemplateList().get(0);

	    } else {
	      throw new OBException("Report document for this template is not specified : "
	          + inventory.getGinDoctypeos().getPrintText());
	    }

    }
     else{

	    if (inventory.getGinDoctypesob().getDocumentTemplateList().size() > 0) {
	      dt = inventory.getGinDoctypesob().getDocumentTemplateList().get(0);

	    } else {
	      throw new OBException("Report document for this template is not specified : "
	          + inventory.getGinDoctypesob().getPrintText());
	    }
	}
    String strDocumentId = strRecordId.replaceAll("\\(|\\)|'", "");
    String v_User_ID = vars.getUser();
    String jenis_report = reportname;

    //override
    jenis_report = dt.getReportFilename();
    strReportPath = dt.getTemplateLocation() + "/" + dt.getTemplateFilename();

    List<Object> param1 = new ArrayList<Object>();
    param1.add(strDocumentId);
    param1.add(jenis_report);
    param1.add(v_User_ID);

	
	
    CallStoredProcedure.getInstance().call("print_history_proc", param1, null, false, false);

    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("DOCUMENT_ID", strRecordId);
    InventoryCount inv = OBDal.getInstance().get(InventoryCount.class,tempstrRecordId );
	String movedate = inv.getMovementDate().toString();
	FieldProvider[] data = null;
    renderJR(vars, response,strReportPath,"ReportHasilProduksi",strOutput,parameters, data, null);

  }

  public String getServletInfo() {
    return "Servlet Toolbar Print for GL Journal.";
  } // end of getServletInfo() method

}
