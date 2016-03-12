// the package name corresponds to the module's manual code folder 
// created above
package com.gai.sutrado.sellingprice.quotation.erpCommon.ad_callouts;
 
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
public class GsqGetTotalOthercost extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {


  
  BigDecimal Nettotal =BigDecimal.ZERO;
  BigDecimal Net1=BigDecimal.ZERO;
  BigDecimal Net2=BigDecimal.ZERO;
  BigDecimal Net3=BigDecimal.ZERO;
 
  String orderqty = info.getStringParameter("inpqtyordered",null);
  String othercost = info.getStringParameter("inpemGsqOthercost", null);
  String netprice = info.getStringParameter("inppriceactual", null);
  String linenetamt = info.getStringParameter("inplinenetamt", null);
  String a = othercost.replaceAll(",","");
  String a1 = netprice.replaceAll(",","");
  String a2 = orderqty.replaceAll(",","");
  String a3 = linenetamt.replaceAll(",","");

  Net3 = new BigDecimal(a).divide(new BigDecimal("100"));
  Nettotal=Net3.multiply(new BigDecimal(a2));
  Net1= Nettotal.multiply(new BigDecimal(a1));
  Net2= Net1.add(new BigDecimal(a1));
  
    //GspGetProdukData[] data = GspGetProdukData.selectPacking(this, strProductId);
 
    //if (data != null && data.length > 0) {
     // hasilPacking=data[0].total;    
   // }

   

    //hasilPersenVariableCost = desimalVariable.multiply(new BigDecimal("100")).divide(hasilNetSales);


    info.addResult("inplinenetamt",Net2.toString());
    info.addResult("inpemGsqHasilothercost",Net1.toString());


    //info.addResult("inpconvertValue","0");

    // Mencari Nilai dari persen

//    info.inpbomV("inpunitPrice",hppValue.toString());
    //info.addResult("inphppV",hasilTotalPrice.multiply(hasilUnitPrice).toString());

 



     }
}
