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
import java.math.*;
 
// the name of the class corresponds to the filename that holds it 
// hence, modules/modules/org.openbravo.howtos/src/org/openbravo/howtos/ad_callouts/ProductConstructSearchKey.java.
// The class must extend SimpleCallout
public class GspHitungPrice extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {


    BigDecimal hasil= BigDecimal.ZERO;
    BigDecimal lmeA2c = BigDecimal.ZERO;
    BigDecimal premiumA2c = BigDecimal.ZERO;
    BigDecimal lmeA3c = BigDecimal.ZERO;
    BigDecimal premiumA3c = BigDecimal.ZERO;
    BigDecimal lmeCopper = BigDecimal.ZERO;
    BigDecimal premiumCopper = BigDecimal.ZERO;

    
     lmeA2c = info.getBigDecimalParameter("inplmeA2c");
     premiumA2c = info.getBigDecimalParameter("inppremiumA2c");
	   lmeA3c = info.getBigDecimalParameter("inplmeA3c");
     premiumA3c = info.getBigDecimalParameter("inppremiumA3c");
	   lmeCopper = info.getBigDecimalParameter("inplmeCopper");
     premiumCopper = info.getBigDecimalParameter("inppremiumCopper");	
	


hasil  = (lmeA2c != null ? lmeA2c : new BigDecimal(0).add(premiumA2c != null ? premiumA2c : new BigDecimal(0)).add(lmeA3c != null ? lmeA3c : new BigDecimal(0)).add(premiumA3c != null ? premiumA3c : new BigDecimal(0)).add(lmeCopper != null ? lmeCopper : new BigDecimal(0)).add(premiumCopper != null ? premiumCopper : new BigDecimal(0)));
  info.addResult("inppricelist",hasil.toString());
 

     info.addResult("inppricelist",hasil.toString());
	
	//BigDecimal t = (lmeA2c.add(premiumA2c).add(lmeA3c).add(premiumA3c).add(lmeCopper).add(premiumCopper));
 
    // inject the result into the response
        

    
    String hasilConvert = "";
    String strdesimalIDR = hasil.toString();
    String strId = info.getStringParameter("inpgspMasterPriceId", null);
    String strdesimalIDRref = strdesimalIDR.replaceAll(",","");


    GspGetProdukDataPrice[] data = GspGetProdukDataPrice.conv(this,strdesimalIDRref,strId);
 
      if (data != null && data.length > 0) {
      
      hasilConvert=data[0].idr;
      
    }
    String a1 = hasilConvert.replaceAll(",","");
    BigDecimal hasil1=new BigDecimal(hasilConvert);
    info.addResult("inpconvertidr",hasil1.toString());

}
}
