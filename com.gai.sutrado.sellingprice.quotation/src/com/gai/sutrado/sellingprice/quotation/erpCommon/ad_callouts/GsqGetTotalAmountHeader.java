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
public class GsqGetTotalAmountHeader extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {


  
  BigDecimal Nettotal=BigDecimal.ZERO;
  BigDecimal Net1=BigDecimal.ZERO;
  String hasil="";
 
  String orderID = info.getStringParameter("inpcOrderId",null );
  String LineAmount = info.getStringParameter("inptotallines",null);
  String HandlingAmount = info.getStringParameter("inpemGsqHandlingamt", null);
  String temp = info.getStringParameter("inpgrandtotal",null);
  //String Tax =info.getStringParameter("inptaxamt",null);
  String a = LineAmount.replaceAll(",","");
  String a1 = HandlingAmount.replaceAll(",","");
  String a3 = temp.replaceAll(",","");

  //info.addResult("inpemGsqHandlingtemp",temp.toString());

  String temp1 =  info.getStringParameter("inpemGsqHandlingtemp", null);
  String a4 = temp1.replaceAll(",","");
  //String a2 = Tax.replaceAll(",","");
  //BigDecimal a3 = new BigDecimal(a2);
  //BigDecimal a4= info.getBigDecimalParameter("inptaxamt");
  //Net1=new BigDecimal(a).add(new BigDecimal(a1));
  //Nettotal=Net1.add(new BigDecimal("0"));

  //GsqGetTaxData[] data = GsqGetTaxData.select(this,orderID);

      //hasil=0;
 //if (data != null && data.length > 0) {
      
      //hasil=data[0].ppn;
   // }
      //info.addResult("inptaxamt",hasil.toString());
      //String tex =info.getStringParameter("inptaxamt",null);
      //String a2= tex.replaceAll(",","");

      Net1=new BigDecimal(a3).subtract(new BigDecimal(a4));
      Nettotal=Net1.add(new BigDecimal(a1));        

      //BigDecimal m,n;
      //m=new BigDecimal(a3);
      //n=new BigDecimal(a4);

      //int test = m.compareTo(n);
      //if(test == 0 ){
         //info.addResult("inpemGsqHandlingtemp",temp.toString());
         //String temp11 =  info.getStringParameter("inpemGsqHandlingtemp", null);
         //String a14 = temp11.replaceAll(",","");
         //Nettotal=Net1.add(new BigDecimal(a14));
      //}else{
        // Nettotal=Net1.add(new BigDecimal(a4));        
      // }
 
  
    //GspGetProdukData[] data = GspGetProdukData.selectPacking(this, strProductId);
 
    //if (data != null && data.length > 0) {
     // hasilPacking=data[0].total;    
   // }

    //hasilPersenVariableCost = desimalVariable.multiply(new BigDecimal("100")).divide(hasilNetSales);

    info.addResult("inpemGsqHandlingtemp",a1.toString());
    info.addResult("inpgrandtotal",Nettotal.toString());
    //info.addResult("inpconvertValue","0");

    // Mencari Nilai dari persen

//    info.inpbomV("inpunitPrice",hppValue.toString());
    //info.addResult("inphppV",hasilTotalPrice.multiply(hasilUnitPrice).toString());

 



     }
}
