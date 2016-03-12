// the package name corresponds to the module's manual code folder 
// created above
package com.gai.sutrado.sellingprice.erpCommon.ad_callouts;
 
import javax.servlet.ServletException;
 
import org.openbravo.utils.FormatUtilities;
import org.openbravo.erpCommon.ad_callouts.SimpleCallout;
import org.openbravo.base.secureApp.VariablesSecureApp;
// classes required to retrieve product category data from the 
// database using the DAL
import org.openbravo.dal.service.OBDal;
import org.openbravo.model.common.plm.ProductCategory;
import java.math.*;
 
// the name of the class corresponds to the filename that holds it 
// hence, modules/modules/org.openbravo.howtos/src/org/openbravo/howtos/ad_callouts/ProductConstructSearchKey.java.
// The class must extend SimpleCallout
public class GspGetCalculatePrice extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {
    
    String hasilConvert = "";
    String strdesimalIDR = info.getStringParameter("inppricelistidr",null);
    String strId = info.getStringParameter("inpgspPriceProductId", null);
    String strdesimalIDRref = strdesimalIDR.replaceAll(",","");
    GspGetProdukDataPrice[] data = GspGetProdukDataPrice.select(this, strdesimalIDRref, strId);
 
    if (data != null && data.length > 0) {
      
      hasilConvert=data[0].idr;
      
    }      
    
    info.addResult("inppricelist",hasilConvert);
    
     }
}
