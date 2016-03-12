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
public class GspGetHitungHandlingSebalikna extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    BigDecimal hasilHandling = BigDecimal.ZERO;
    BigDecimal hasilKali = BigDecimal.ZERO;
    BigDecimal hasilTambah = BigDecimal.ZERO;
     BigDecimal hasilUnitPrice = BigDecimal.ZERO;
	 
	 BigDecimal hasilSellingPrice = BigDecimal.ZERO;
 
    // parse input parameters here; the names derive from the column
    // names of the table prepended by inp and stripped of all
    // underscore characters; letters following the underscore character
    // are capitalized; this way a database column named
    // M_PRODUCT_CATEGORY_ID that is shown on a tab will become
    // inpmProductCategoryId html field
    String strHandling = info.getStringParameter("inphandlingV", null);
    String strNetSales = info.getStringParameter("inpnetSales", null);
    String strSelling = info.getStringParameter("inpsellingPrice", null);
    String strQty = info.getStringParameter("inpqty", null);
	
		BigDecimal desimalSales = info.getBigDecimalParameter("inpnetSales");
	BigDecimal desimalFreight = info.getBigDecimalParameter("inpfreightV");
	BigDecimal desimalInsurance = info.getBigDecimalParameter("inpinsuranceV");


    BigDecimal desimalHandling = new BigDecimal(strHandling);
    BigDecimal desimalSelling = new BigDecimal(strSelling);
    BigDecimal desimalNetsales = new BigDecimal(strNetSales);
    BigDecimal desimalQty = new BigDecimal(strQty);

    hasilKali = desimalHandling.multiply(new BigDecimal("100"));
    hasilHandling  = hasilKali.divide(desimalNetsales);
    
    hasilTambah = desimalSelling.add(desimalHandling);
    hasilUnitPrice = hasilTambah.divide(desimalQty);
	
	hasilSellingPrice = desimalSales.add(desimalFreight).add(desimalInsurance).add(desimalHandling);
    // inject the result into the response
    info.addResult("inphandlingP",hasilHandling.toString());
	info.addResult("inpsellingPrice",hasilSellingPrice.toString());   
    info.addResult("inpunitPrice",hasilUnitPrice.toString());
    
    
 
  /*protected String getConstructedKey(VariablesSecureApp vars,
        String strProductName, String strProductCategoryId) {
 
    // Retrieve the product category name
    final ProductCategory productCategory = OBDal.getInstance().get(ProductCategory.class,
            strProductCategoryId);
    String strProductCategoryName = productCategory.getName();
 
    // construct full key
    String generatedSearchKey = FormatUtilities.replaceJS(strProductName
                .replaceAll(" ", ""))
                + "_" + strProductCategoryName.replaceAll(" ", "");
 
    // return generated key
    return generatedSearchKey;
  }*/
}
}
