// the package name corresponds to the module's manual code folder
// created above
package com.gai.sizeCodeGenerate.erpCommon.ad_callouts;

import javax.servlet.ServletException;

import org.openbravo.utils.FormatUtilities;
import org.openbravo.erpCommon.ad_callouts.SimpleCallout;
import org.openbravo.base.secureApp.VariablesSecureApp;
import java.math.*;
// classes required to retrieve product category data from the
// database using the DAL
//import org.openbravo.dal.service.OBDal;
//import org.openbravo.model.common.plm.ProductCategory;

// the name of the class corresponds to the filename that holds it
// hence, modules/modules/org.openbravo.howtos/src/org/openbravo/howtos/ad_callouts/ProductConstructSearchKey.java.
// The class must extend SimpleCallout
public class ScgGetGroupKabel extends SimpleCallout {

  private static final long serialVersionUID = 1L;

  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    String data_group = "";

	String strgroupkabel = info.getStringParameter("inpscgJenisProdukId", null);
	ScgGetGroupKabelData[] data = ScgGetGroupKabelData.select(this, strgroupkabel);

      if (data != null && data.length > 0) {
      
          data_group = data[0].name;
      }


    info.addResult("inpscgMgroupkabelId", data_group);

  }
}
