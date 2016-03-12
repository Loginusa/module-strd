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
public class GspGetHitungFreight extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    BigDecimal hasilFreight = BigDecimal.ZERO;
    BigDecimal hasilBagi = BigDecimal.ZERO;
    
    BigDecimal hasilSellingPrice = BigDecimal.ZERO;
    
    
    
 
    // parse input parameters here; the names derive from the column
    // names of the table prepended by inp and stripped of all
    // underscore characters; letters following the underscore character
    // are capitalized; this way a database column named
    // M_PRODUCT_CATEGORY_ID that is shown on a tab will become
    // inpmProductCategoryId html field
    BigDecimal desimalFreight = info.getBigDecimalParameter("inpfreightP");
    BigDecimal desimalNetsales = info.getBigDecimalParameter("inpnetSales");
    BigDecimal desimalSales = info.getBigDecimalParameter("inpnetSales");
	BigDecimal desimalInsurance = info.getBigDecimalParameter("inpinsuranceV");
	BigDecimal desimalHandling = info.getBigDecimalParameter("inphandlingV");
	


    hasilBagi = desimalFreight.divide(new BigDecimal("100"));
    hasilFreight  = desimalNetsales.multiply(hasilBagi);
	
	hasilSellingPrice = desimalSales.add(hasilFreight).add(desimalInsurance).add(desimalHandling);
 
    // inject the result into the response
    info.addResult("inpfreightV",hasilFreight.toString());
	info.addResult("inpsellingPrice",hasilSellingPrice.toString());   
    
    
 
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
