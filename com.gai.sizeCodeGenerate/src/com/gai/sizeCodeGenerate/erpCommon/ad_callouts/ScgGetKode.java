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
public class ScgGetKode extends SimpleCallout {

  private static final long serialVersionUID = 1L;

  @Override
  protected void execute(CalloutInfo info) throws ServletException {

    String data_phasa = null;
    String data_netral = null;
    String data_lighting = null;
    String data_sizeP = null;
    String data_sizeN = null;
    String data_sizeL = null;
    String data_diameter = null;
    String data_lebar = null;
    String data_tebal = null;
    String data_penyebutphasa = null;       
    String data_penyebutnetral = null;    
    String data_penyebutlighting = null;    

    String data_uom = "";
    String data_conductor = "";
	String data_warna = "";

    // parse input parameters here; the names derive from the column
    // names of the table prepended by inp and stripped of all
    // underscore characters; letters following the underscore character
    // are capitalized; this way a database column named
    // M_PRODUCT_CATEGORY_ID that is shown on a tab will become
    // inpmProductCategoryId html field

	String isPhasa = info.getStringParameter("inpisphasa", null);
	String isNetral = info.getStringParameter("inpisnetral", null);
    String isLighting = info.getStringParameter("inpislighting", null);

    data_phasa = info.getStringParameter("inpphasaamt", null);
	  data_netral = info.getStringParameter("inpnetralamt", null);
    data_lighting = info.getStringParameter("inplightingamt", null);
    data_sizeL = info.getStringParameter("inpsizelighting", null);
    data_sizeN = info.getStringParameter("inpsizenetral", null);
    data_sizeP = info.getStringParameter("inpsizephasa", null);
 	  data_conductor = info.getStringParameter("inpconductor", null);
    data_diameter = info.getStringParameter("inpdiameter",null);
    data_lebar = info.getStringParameter("inplebar",null);
    data_tebal = info.getStringParameter("inptebal",null);
    data_penyebutphasa = info.getStringParameter("inpperphasa",null) ;       
    data_penyebutnetral = info.getStringParameter("inppernetral",null);    
    data_penyebutlighting = info.getStringParameter("inpperlighting",null);    
    



	String strIdEmployee = info.getStringParameter("inpscgSatuanId", null);
	ScgGetKodeData[] data = ScgGetKodeData.select(this, strIdEmployee);

	String strIdKonduktor = info.getStringParameter("inpscgKonduktorId", null);
	ScgGetKodeData[] data1 = ScgGetKodeData.selectKonduktor(this, strIdKonduktor);
    
     String strwarna = info.getStringParameter("inpscgWarnaId", null);
	 ScgGetKodeData[] data2 = ScgGetKodeData.selectWarna(this,strwarna);	
	
    if (data != null && data.length > 0) {
       	data_uom = data[0].kode;
    }

	    if (data1 != null && data1.length > 0) {
       	data_conductor = data1[0].kode;
    }
   	    if (data2 != null && data2.length > 0) {
       	data_warna = data2[0].nama;
    }
	 
    String resultBuilder = "";
	  String plusNetral = "";
	  String plusLighting = "";

	  String kaliP = "";
	  String kaliN="";
	  String kaliL="";
    String kaliFC="";
    String perphasa="";
    String pernetral="";
    String perlighting="";


    //cek apakah 1
    //phasa
    if ( (data_phasa.equals("1") || (data_phasa==null || data_phasa =="") ) || (data_sizeP==null || data_sizeP == "") )  {
	  kaliP = "";
	  data_phasa="";
    }
    else {
      kaliP = " x ";
    }
    

    //netral
    if ( (data_netral.equals("1") || (data_netral==null || data_netral =="") ) || (data_sizeN==null || data_sizeN == "") ) {
      kaliN = "";
	  data_netral ="";
    }
    else {
      kaliN = " x ";
    }


    //lighting
    if ( (data_lighting.equals("1") || (data_lighting==null || data_lighting =="") ) || (data_lighting==null || data_sizeL == "") ) {
      kaliL = "";
	  data_lighting="";
    }
    else {
	  kaliL = " x ";
    }

    //FC RC
    //if ((data_diameter!=null) || (data_diameter!="") && (((data_tebal==null) ||(data_tebal.equals("") )) ||((data_lebar ==null) ||(data_lebar.equals("") )))){
    //  kaliFC = "";
    //}


    if(((data_tebal ==null) ||(data_tebal =="" )) ||((data_lebar ==null) ||(data_lebar =="" ))){
      kaliFC="";
      data_tebal="";
      data_lebar="";
    }else{
      kaliFC=" x ";

    }







    //add plus
    //netral
    if ( ((data_netral == null || data_netral == "") && (data_sizeN == null || data_sizeN == "")) || isNetral.equals("N") && isPhasa.equals("N")) {
      plusNetral = "";
    }else {
	  plusNetral = " + ";
	}

    //lighting
    if ( ((data_lighting == null || data_lighting == "") && (data_sizeL == null || data_sizeL == "")) || isPhasa.equals("N") && isNetral.equals("N") && isLighting.equals("N") ) {
      plusLighting = "";
    }
	else {
	 plusLighting = " + ";
	}

 //add per

     if((data_penyebutphasa == null) || (data_penyebutphasa == "")){
        data_penyebutphasa = "" ;
        perphasa = ""; 
      }else{
        perphasa =" / ";
      }


     if(data_penyebutnetral == null || data_penyebutnetral == ""){
        data_penyebutnetral = "" ;
        pernetral = ""; 
     }else{
        pernetral =" / ";
     }


     if(data_penyebutlighting == null || data_penyebutlighting == ""){
        data_penyebutlighting = "";
        perlighting = ""; 
     }else{
        perlighting =" / ";
     }



    //finalisasi
    if (isPhasa.equals("N")) {
      data_phasa ="";
      data_sizeP ="";
	    kaliP="";
      data_penyebutphasa ="";
    }
    if (isNetral.equals("N")) {
      data_netral ="";
      data_sizeN ="";
	  kaliN="";
	  plusNetral = "";
    data_penyebutnetral ="";
    }
    if (isLighting.equals("N")) {
        data_lighting ="";
        data_sizeL ="";
	      kaliL="";
	       plusLighting = "";
    data_penyebutlighting ="";
    }

    if (isPhasa.equals("N") && isNetral.equals("N") && isLighting.equals("N")) {
      resultBuilder="";
    }


   
    resultBuilder = data_diameter+data_tebal+kaliFC+data_lebar+data_phasa+kaliP+data_sizeP+perphasa+data_penyebutphasa+plusNetral+data_netral+kaliN+data_sizeN+pernetral+data_penyebutnetral+plusLighting+data_lighting+kaliL+data_sizeL+perlighting+data_penyebutlighting;

	String temp = resultBuilder.substring(0,1);
	
  if (temp.equals(" + ") || temp.equals(" x ")) {
		resultBuilder = resultBuilder.substring(1,resultBuilder.length());
	}
    info.addResult("inpname", resultBuilder + " "+data_uom+" "+data_conductor+" "+data_warna);

    BigDecimal perkalianP = BigDecimal.ZERO;
    BigDecimal perkalianN = BigDecimal.ZERO;
    BigDecimal perkalianL = BigDecimal.ZERO;
    BigDecimal penjumlahan = BigDecimal.ZERO;
    BigDecimal penjumlahan1 = BigDecimal.ZERO;
    

    BigDecimal phasa = BigDecimal.ZERO;
    BigDecimal netral = BigDecimal.ZERO;
    BigDecimal lighting = BigDecimal.ZERO;
    BigDecimal sizeP = BigDecimal.ZERO;
    BigDecimal sizeN = BigDecimal.ZERO;
    BigDecimal sizeL = BigDecimal.ZERO;
    //BigDecimal diameter = BigDecimal.ZERO;
    BigDecimal lebar = BigDecimal.ZERO;
    BigDecimal tebal = BigDecimal.ZERO;
   // BigDecimal data_penyebutphasa = BigDecimal.ZERO;       
   //BigDecimal data_penyebutnetral = BigDecimal.ZERO;    
   // BigDecimal data_penyebutlighting = BigDecimal.ZERO;    


    phasa = info.getBigDecimalParameter("inpphasaamt");
    netral = info.getBigDecimalParameter("inpnetralamt");
    lighting = info.getBigDecimalParameter("inplightingamt");
    sizeL = info.getBigDecimalParameter("inpsizelighting");
    sizeN = info.getBigDecimalParameter("inpsizenetral");
    sizeP = info.getBigDecimalParameter("inpsizephasa");
   // diameter = info.getBigDecimalParameter("inpdiameter");
    lebar = info.getBigDecimalParameter("inplebar");
    tebal = info.getBigDecimalParameter("inptebal");
   // data_penyebutphasa = info.getBigDecimalParameter("inpperphasa") ;       
   // data_penyebutnetral = info.getBigDecimalParameter("inppernetral");    
   // data_penyebutlighting = info.getBigDecimalParameter("inpperlighting");    

    perkalianP = phasa.multiply(sizeP);
    perkalianN = netral.multiply(sizeN);
    perkalianL = lighting.multiply(sizeL);
        
    penjumlahan = perkalianP.add(perkalianN);
    penjumlahan1 = penjumlahan.add(perkalianL);


    info.addResult("inpjumlah", penjumlahan1);

  }
}
