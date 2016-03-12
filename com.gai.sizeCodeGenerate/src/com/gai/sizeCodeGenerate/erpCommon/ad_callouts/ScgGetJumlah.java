// the package name corresponds to the module's manual code folder
// created above
package com.gai.sizeCodeGenerate.erpCommon.ad_callouts;

import javax.servlet.ServletException;

import org.openbravo.utils.FormatUtilities;
import org.openbravo.erpCommon.ad_callouts.SimpleCallout;
import org.openbravo.base.secureApp.VariablesSecureApp;
import java.math.*;
// classes required to retrieve product category data from the
// database using the DAL
//import org.openbravo.dal.service.OBDal;
//import org.openbravo.model.common.plm.ProductCategory;

// the name of the class corresponds to the filename that holds it
// hence, modules/modules/org.openbravo.howtos/src/org/openbravo/howtos/ad_callouts/ProductConstructSearchKey.java.
// The class must extend SimpleCallout
public class ScgGetJumlah extends SimpleCallout {

  private static final long serialVersionUID = 1L;

  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    BigDecimal perkalianP = BigDecimal.ZERO;
    BigDecimal perkalianN = BigDecimal.ZERO;
    BigDecimal perkalianL = BigDecimal.ZERO;
    BigDecimal penjumlahan = BigDecimal.ZERO;
    BigDecimal penjumlahan1 = BigDecimal.ZERO;
    

    BigDecimal data_phasa = BigDecimal.ZERO;
    BigDecimal data_netral = BigDecimal.ZERO;
    BigDecimal data_lighting = BigDecimal.ZERO;
    BigDecimal data_sizeP = BigDecimal.ZERO;
    BigDecimal data_sizeN = BigDecimal.ZERO;
    BigDecimal data_sizeL = BigDecimal.ZERO;
    BigDecimal data_diameter = BigDecimal.ZERO;
    BigDecimal data_lebar = BigDecimal.ZERO;
    BigDecimal data_tebal = BigDecimal.ZERO;
   // BigDecimal data_penyebutphasa = BigDecimal.ZERO;       
   //BigDecimal data_penyebutnetral = BigDecimal.ZERO;    
   // BigDecimal data_penyebutlighting = BigDecimal.ZERO;    


    data_phasa = info.getBigDecimalParameter("inpphasaamt");
	  data_netral = info.getBigDecimalParameter("inpnetralamt");
    data_lighting = info.getBigDecimalParameter("inplightingamt");
    data_sizeL = info.getBigDecimalParameter("inpsizelighting");
    data_sizeN = info.getBigDecimalParameter("inpsizenetral");
    data_sizeP = info.getBigDecimalParameter("inpsizephasa");
 	  data_diameter = info.getBigDecimalParameter("inpdiameter");
    data_lebar = info.getBigDecimalParameter("inplebar");
    data_tebal = info.getBigDecimalParameter("inptebal");
   // data_penyebutphasa = info.getBigDecimalParameter("inpperphasa") ;       
   // data_penyebutnetral = info.getBigDecimalParameter("inppernetral");    
   // data_penyebutlighting = info.getBigDecimalParameter("inpperlighting");    

    perkalianP = data_phasa.multiply(data_sizeP);
    perkalianN = data_netral.multiply(data_sizeN);
    perkalianL = data_lighting.multiply(data_sizeL);
        
    penjumlahan = perkalianP.add(perkalianN);
    penjumlahan1 = penjumlahan.add(perkalianL);


    info.addResult("inpjumlah", penjumlahan1);
  }
}
