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
public class GspGetProduk extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    String hasilUOM = "";
	String hasilDescription = "";
    String hasilBrand = "";
    String hasilMaterial = "";
	BigDecimal hasilJumlah = BigDecimal.ZERO;
    BigDecimal CurrencyRate = BigDecimal.ZERO;
	BigDecimal Premium = BigDecimal.ZERO;
	BigDecimal LME = BigDecimal.ZERO;
     
     
 
    // parse input parameters here; the names derive from the column
    // names of the table prepended by inp and stripped of all
    // underscore characters; letters following the underscore character
    // are capitalized; this way a database column named
    // M_PRODUCT_CATEGORY_ID that is shown on a tab will become
    // inpmProductCategoryId html field

	String strTanggal = info.getStringParameter("inptanggal", null);
	String strCurrencyId = info.getStringParameter("inpcCurrencyId", null);
    String strProductId = info.getStringParameter("inpmProductId", null);
	

    GspGetProdukData[] data = GspGetProdukData.select(this,strTanggal,strTanggal,strCurrencyId,strTanggal,strProductId,strTanggal);
 
    if (data != null && data.length > 0) {
      hasilBrand=data[0].brand;
      hasilUOM=data[0].uom;
      hasilMaterial=data[0].total;
	  hasilDescription=data[0].description;
      
    }
	
	

 
    // inject the result into the response
    info.addResult("inpmBrandId",hasilBrand);
    info.addResult("inpcUomId",hasilUOM);
	
	
	
    info.addResult("inpmaterialPrice",hasilMaterial);
    
    
 
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
