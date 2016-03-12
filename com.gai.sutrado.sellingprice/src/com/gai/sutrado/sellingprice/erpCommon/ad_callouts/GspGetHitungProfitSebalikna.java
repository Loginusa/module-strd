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
public class GspGetHitungProfitSebalikna extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    BigDecimal hasilProfit = BigDecimal.ZERO;
    BigDecimal hasilTambah = BigDecimal.ZERO;
    BigDecimal hasilKali = BigDecimal.ZERO;
    BigDecimal hasilTambahNetSales = BigDecimal.ZERO;
	
	BigDecimal hasilSellingPrice = BigDecimal.ZERO;

    // parse input parameters here; the names derive from the column
    // names of the table prepended by inp and stripped of all
    // underscore characters; letters following the underscore character
    // are capitalized; this way a database column named
    // M_PRODUCT_CATEGORY_ID that is shown on a tab will become
    // inpmProductCategoryId html field
    BigDecimal desimalaterial =info.getBigDecimalParameter("inpmaterialPrice");
    BigDecimal desimalPacking = info.getBigDecimalParameter("inppacking");
    BigDecimal desimalFix = info.getBigDecimalParameter("inpfixCostV");
    BigDecimal desimalAvalan = info.getBigDecimalParameter("inpavalanV");
    BigDecimal desimalVariable = info.getBigDecimalParameter("inpvariabelCost");
    BigDecimal desimalCof = info.getBigDecimalParameter("inpcofV");
    BigDecimal desimalWaste = info.getBigDecimalParameter("inpwasteCostV");
    BigDecimal desimalProfit = info.getBigDecimalParameter("inpprofitV");
	
    BigDecimal desimalInsuranceV = info.getBigDecimalParameter("inpinsuranceV");
    BigDecimal desimalFreight = info.getBigDecimalParameter("inpfreightV");
	BigDecimal desimalHandling = info.getBigDecimalParameter("inphandlingV");

    hasilKali = desimalProfit.multiply(new BigDecimal("100"));
    hasilTambah = desimalaterial.add(desimalPacking).add(desimalFix).add(desimalAvalan).add(desimalVariable).add(desimalCof).add(desimalWaste);
    hasilProfit  = hasilKali.divide(hasilTambah);
    hasilTambahNetSales = hasilTambah.add(desimalProfit);
	
	hasilSellingPrice = hasilTambahNetSales.add(desimalFreight).add(desimalInsuranceV).add(desimalHandling);
    // inject the result into the response
    info.addResult("inpprofitP",hasilProfit.toString());
    info.addResult("inpnetSales",hasilTambahNetSales.toString());
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
