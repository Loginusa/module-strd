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
public class GspGetHitungHand extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    BigDecimal hasilHand = BigDecimal.ZERO;
    BigDecimal hasilBagi = BigDecimal.ZERO;
    BigDecimal hasilTambah = BigDecimal.ZERO;
    BigDecimal hasilUnit = BigDecimal.ZERO;
	BigDecimal hasilSellingPrice = BigDecimal.ZERO;

    // inpmProductCategoryId html field
    BigDecimal desimalHand = info.getBigDecimalParameter("inphandlingP");
    BigDecimal desimalNetsales = info.getBigDecimalParameter("inpnetSales");
    BigDecimal desimalSelling = info.getBigDecimalParameter("inpsellingPrice");
    BigDecimal desimalQty = info.getBigDecimalParameter("inpqty");
	BigDecimal desimalSales = info.getBigDecimalParameter("inpnetSales");
	BigDecimal desimalFreight = info.getBigDecimalParameter("inpfreightV");
	BigDecimal desimalInsurance = info.getBigDecimalParameter("inpinsuranceV");
	BigDecimal desimalHandling = info.getBigDecimalParameter("inphandlingV");
	
	hasilSellingPrice = desimalSales.add(desimalFreight).add(desimalInsurance).add(desimalHandling);

    hasilBagi = desimalHand.divide(new BigDecimal("100"));
    hasilHand  = desimalNetsales.multiply(hasilBagi);
	hasilTambah = desimalSelling.add(hasilHand);
    hasilUnit = hasilTambah.divide(desimalQty);

    // inject the result into the response
    info.addResult("inphandlingV",hasilHand.toString());
	info.addResult("inpsellingPrice",hasilSellingPrice.toString());   
    info.addResult("inpunitPrice",hasilUnit.toString());
    
    
 
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
