// the package name corresponds to the module's manual code folder 
// created above
package com.gai.sutrado.shipment.detail.erpCommon.ad_callouts;
 
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
public class CgsdGetProduct extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    String hasilUOM = "";
	  String hasilQty = "";
    String hasilQP = "";
    String hasilproduksi = "";
    String hasilHaspel="";
    String hasilId="";
     
     
 
    // parse input parameters here; the names derive from the column
    // names of the table prepended by inp and stripped of all
    // underscore characters; letters following the underscore character
    // are capitalized; this way a database column named
    // M_PRODUCT_CATEGORY_ID that is shown on a tab will become
    // inpmProductCategoryId html field
    //String strHaspel = info.getStringParameter("inpnomorhaspel", null);

    String strProductId = info.getStringParameter("inpemCgidDetailId", null);

    CgsdGetProductData[] data = CgsdGetProductData.select(this,strProductId);
 
    if (data != null && data.length > 0) {
      hasilQty=data[0].qty;
      hasilUOM=data[0].uomid;
      hasilQP=data[0].qp;
	    hasilproduksi=data[0].order;
      hasilHaspel=data[0].haspel;
      hasilId=data[0].id;
    }
	
	
 
    info.addResult("inpmProductId",hasilId);

    // inject the result into the response
    info.addResult("inpnomororderproduksi",hasilproduksi);
    info.addResult("inpnomorhaspel",hasilHaspel);
	
	
	
    info.addResult("inpqtypackage",hasilQP);
        info.addResult("inpqty",hasilQty);
    info.addResult("inpcUomId",hasilUOM);

    
    
 
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
