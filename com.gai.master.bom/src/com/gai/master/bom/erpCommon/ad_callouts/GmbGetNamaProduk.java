// the package name corresponds to the module's manual code folder
// created above
package com.gai.master.bom.erpCommon.ad_callouts;

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
public class GmbGetNamaProduk extends SimpleCallout {

  private static final long serialVersionUID = 1L;

  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    String data_nama = "";

	String strNamaProduk = info.getStringParameter("inpmProductId", null);
	GmbGetNamaProdukData[] data = GmbGetNamaProdukData.select(this, strNamaProduk);

      if (data != null && data.length > 0) {
      
          data_nama = data[0].nama;
      }


    info.addResult("inpnamaProduct", data_nama);

  }
}
