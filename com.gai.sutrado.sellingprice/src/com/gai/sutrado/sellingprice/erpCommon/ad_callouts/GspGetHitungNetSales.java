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
public class GspGetHitungNetSales extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    //BigDecimal hasilProfit = BigDecimal.ZERO;
    BigDecimal hasilTambah = BigDecimal.ZERO;
    //BigDecimal hasilBagi = BigDecimal.ZERO;
    

    
    
    
 
    // parse input parameters here; the names derive from the column
    // names of the table prepended by inp and stripped of all
    // underscore characters; letters following the underscore character
    // are capitalized; this way a database column named
    // M_PRODUCT_CATEGORY_ID that is shown on a tab will become
    // inpmProductCategoryId html field
    String strProfit = info.getStringParameter("inpprofitV", null);
    String strmaterialPrice = info.getStringParameter("inpmaterialPrice", null);
    String strAvalanV = info.getStringParameter("inpavalanV", null);
    String strPacking = info.getStringParameter("inppacking", null);
    String strFix = info.getStringParameter("inpfixCostV", null);
    String strVariable = info.getStringParameter("inpvariabelCost", null);
    String strWaste = info.getStringParameter("inpwasteCostV", null);
    String strCof = info.getStringParameter("inpcofV", null);
    BigDecimal desimalaterial = new BigDecimal(strmaterialPrice);
    BigDecimal desimalPacking = new BigDecimal(strPacking);
    BigDecimal desimalFix = new BigDecimal(strFix);
    BigDecimal desimalAvalan = new BigDecimal(strAvalanV);
    BigDecimal desimalVariable = new BigDecimal(strVariable);
    BigDecimal desimalCof = new BigDecimal(strCof);
    BigDecimal desimalWaste = new BigDecimal(strWaste);
    BigDecimal desimalProfit = new BigDecimal(strProfit);

    //hasilBagi = desimalProfit.divide(new BigDecimal("100"));
    hasilTambah = desimalaterial.add(desimalPacking).add(desimalFix).add(desimalAvalan).add(desimalVariable).add(desimalCof).add(desimalWaste).add(desimalProfit);
    //hasilProfit  = hasilTambah.multiply(hasilBagi);
 
    // inject the result into the response
    info.addResult("inpnetSales",hasilTambah.toString());
    
    
 
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
