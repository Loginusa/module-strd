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
public class GspGetCalculate extends SimpleCallout {
 
  private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {




  String hasilUOM = "";
  String hasilDescription = "";
  String hasilBrand = "";
  String hasilMaterial = "";
  String hasilPacking = "";
  String hasilvar="";
  
  BigDecimal hasilJumlah = BigDecimal.ZERO;
  BigDecimal CurrencyRate = BigDecimal.ZERO;
  BigDecimal Premium = BigDecimal.ZERO;
  BigDecimal LME = BigDecimal.ZERO;

  BigDecimal bomValue = BigDecimal.ZERO;
 // BigDecimal variableCost = BigDecimal.ZERO;   
  BigDecimal hppValue = BigDecimal.ZERO;   
 
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
  
    //GspGetProdukData[] data = GspGetProdukData.selectPacking(this, strProductId);
 
    //if (data != null && data.length > 0) {
     // hasilPacking=data[0].total;    
   // }

    hasilPacking=GspGetProdukData.selectPacking(this,strTanggal,strTanggal,strCurrencyId,strTanggal,strProductId,strTanggal);

    if (hasilPacking == null) {
      hasilPacking="0";    
    }

    BigDecimal hasilAvalan = BigDecimal.ZERO;
    BigDecimal hasilFixCost = BigDecimal.ZERO;
    BigDecimal hasilWaste = BigDecimal.ZERO;
    BigDecimal hasilCostOfFound = BigDecimal.ZERO;
    BigDecimal hasilProfit = BigDecimal.ZERO;
    BigDecimal hasilNetSales = BigDecimal.ZERO;
    BigDecimal hasilTotalPrice = BigDecimal.ZERO;
    BigDecimal hasilUnitPrice = BigDecimal.ZERO;
    BigDecimal hasilPersenFreight = BigDecimal.ZERO;
    BigDecimal hasilPersenInsurance = BigDecimal.ZERO;
    BigDecimal hasilPersenHandling= BigDecimal.ZERO;
    BigDecimal hasilPersenVariableCost = BigDecimal.ZERO;

    BigDecimal desimalaterial = new BigDecimal(hasilMaterial);
    BigDecimal hasildesimalPacking = new BigDecimal(hasilPacking);
    BigDecimal desimalAvalan = info.getBigDecimalParameter("inpavalanP");
    BigDecimal desimalPacking = info.getBigDecimalParameter("inppackingV");
    BigDecimal desimalFixCost = info.getBigDecimalParameter("inpfixCostP");
    BigDecimal desimalVariable = info.getBigDecimalParameter("inpvariableV");
    BigDecimal desimalWaste = info.getBigDecimalParameter("inpwasteCostP");
    BigDecimal desimalCostOfFound = info.getBigDecimalParameter("inpcofP");
    BigDecimal desimalProfit = info.getBigDecimalParameter("inpprofitP");

    hasilvar=GspGetProdukData.selectVariableCost(this,strCurrencyId,strTanggal,strProductId);
    String strdesimalIDRref = hasilvar.replaceAll(",",""); 

    BigDecimal variableCost =BigDecimal.ZERO;
    if(hasilvar==null){

      variableCost=BigDecimal.ZERO;
    }else{

      variableCost = new BigDecimal(hasilvar);
    }
    


    hasilAvalan  = desimalaterial.multiply(desimalAvalan).divide(new BigDecimal("100"));
    hasilWaste  = desimalaterial.multiply(desimalWaste).divide(new BigDecimal("100"));
    bomValue	 = desimalaterial.add(hasildesimalPacking);

    hasilCostOfFound = bomValue.multiply(desimalCostOfFound).divide(new BigDecimal("100"));
    hasilFixCost = bomValue.multiply(desimalFixCost).divide(new BigDecimal("100"));
    //variableCost = desimalVariable;

    hppValue =  hasilAvalan.add(bomValue).add(hasilCostOfFound).add(hasilFixCost).add(variableCost);	
    hasilProfit = hppValue.multiply(desimalProfit).divide(new BigDecimal("100"));

    hasilNetSales = hppValue.add(hasilProfit);

    //hasilFixCost = (desimalaterial.add(desimalPacking)).multiply(desimalFixCost).divide(new BigDecimal("100"));
    
    //hasilCostOfFound = (desimalaterial.add(desimalPacking)).multiply(desimalCostOfFound).divide(new BigDecimal("100")); // lama

    //hasilProfit = (desimalaterial.add(hasilAvalan).add(desimalPacking).add(hasilFixCost).add(desimalVariable).add(hasilWaste).add(hasilCostOfFound)).multiply(desimalProfit).divide(new BigDecimal("100"));
    //hasilNetSales = (desimalaterial.add(hasilAvalan).add(desimalPacking).add(hasilFixCost).add(desimalVariable).add(hasilWaste).add(hasilCostOfFound).add(hasilProfit));
   
    hasilUnitPrice = hasilNetSales.divide(new BigDecimal("1000"));

    //hasilPersenVariableCost = desimalVariable.multiply(new BigDecimal("100")).divide(hasilNetSales);


    info.addResult("inpmBrandId",hasilBrand);
    info.addResult("inpcUomId",hasilUOM); 
    info.addResult("inpmaterialPrice",desimalaterial.toString());
    //info.addResult("inpmaterialPrice","0");

    // Mencari Nilai dari persen
    info.addResult("inpavalanV",hasilAvalan.toString());
    info.addResult("inpfixCostV",hasilFixCost.toString());
    //info.addResult("inpvariableV",desimalVariable.toString());
    //info.addResult("inppackingV",desimalPacking.toString());
    info.addResult("inppackingV",hasilPacking.toString());
    //info.addResult("inpvariableV",hasilvar.toString());
    info.addResult("inpvariabelCost",hasilvar.toString());
    info.addResult("inpwasteCostV",hasilWaste.toString());
    info.addResult("inpcofV",hasilCostOfFound.toString());
    info.addResult("inpprofitV",hasilProfit.toString());
    info.addResult("inpnetSalesV",hasilNetSales.toString());
    info.addResult("inpnetSales",hasilNetSales.toString());
    info.addResult("inppacking",hasilPacking.toString());
    info.addResult("inpbomV",bomValue.toString());
    info.addResult("inphppV",hppValue.toString());

    info.addResult("inptSalespriceV",hasilTotalPrice.toString());
    info.addResult("inpunitPrice",hasilUnitPrice.toString());
//    info.inpbomV("inpunitPrice",hppValue.toString());
    //info.addResult("inphppV",hasilTotalPrice.multiply(hasilUnitPrice).toString());

 



     }
}
