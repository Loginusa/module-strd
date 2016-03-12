/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gai.upload.sizegenerate.erpCommon.ad_process;

 import com.gai.sizeCodeGenerate.data.process.ScgUkuranMaterial;
 import org.openbravo.idl.proc.Parameter;
 import org.openbravo.idl.proc.Validator;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.text.ParseException;
 import java.math.BigDecimal;
 import java.util.Date;
 import org.apache.log4j.*;
import org.apache.log4j.Logger;
 //OBDAL
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Expression;
 import org.hibernate.criterion.Projections;
 import org.openbravo.dal.service.OBCriteria;
 import org.openbravo.dal.service.OBDal;
 import org.openbravo.scheduling.ProcessBundle;
 import org.openbravo.scheduling.ProcessLogger;
 import org.openbravo.service.db.DalBaseProcess;
 import org.quartz.JobExecutionException;
 
 import org.openbravo.base.exception.OBException;
 import org.openbravo.base.provider.OBProvider;
 import org.openbravo.base.structure.BaseOBObject;
 import org.openbravo.dal.service.OBDal;
 import org.openbravo.erpCommon.utility.Utility;
 import org.openbravo.idl.proc.Value;
 import org.openbravo.module.idljava.proc.IdlServiceJava;
 
 import com.gai.sizeCodeGenerate.data.process.ScgSize;
 import com.gai.sizeCodeGenerate.data.process.SCGJenisProduk;
 import com.gai.sizeCodeGenerate.data.process.SCGMgroupKabel;
 import com.gai.sizeCodeGenerate.data.process.ScgWarna;
 import com.gai.sizeCodeGenerate.data.process.SCGKonduktor;
 import com.gai.sizeCodeGenerate.data.process.ScgSatuan;
 import org.openbravo.database.ConnectionProvider;

 /**
 *
 * @author Tias Ade Putra - GAI
 */
public class SizeUkuranProcess extends IdlServiceJava {
    
 ScgSize size = null;
 String flag = "";
 private static Logger log4j=Logger.getLogger(SizeUkuranProcess.class);

 public String getEntityName() {
 return "Upload Attribute 6";
 }
 
 public Parameter[] getParameters() {
 
     return new Parameter[] {
//Header
	 new Parameter("Organization", Parameter.STRING), //0
	 new Parameter("JenisProduk",Parameter.STRING), //1         
	 new Parameter("GrupProduk",Parameter.STRING), //2
         new Parameter("isphasa", Parameter.STRING), //3
         new Parameter("phasaamount", Parameter.STRING), //4 
	 new Parameter("sizephasa", Parameter.STRING), //5
	 new Parameter("isground", Parameter.STRING), //6
	 new Parameter("phasaground", Parameter.STRING), //7
         new Parameter("lebar", Parameter.STRING), //8
         new Parameter("tebal", Parameter.STRING), //9
         new Parameter("warna", Parameter.STRING), //10
	 new Parameter("isnetral", Parameter.STRING), //11
         new Parameter("netralamount", Parameter.STRING), //12
         new Parameter("sizenetral", Parameter.STRING), //13
	 new Parameter("islighting", Parameter.STRING), //14
         new Parameter("lightingamount", Parameter.STRING), //15
         new Parameter("sizelighting", Parameter.STRING), //16
         new Parameter("diameter", Parameter.STRING), //17
         new Parameter("konduktor", Parameter.STRING), //16
         new Parameter("satuan", Parameter.STRING), //19

	
         
         
	};
 }
	

 protected Object[] validateProcess(Validator validator, String... values) throws Exception {
 
     
 validator.checkOrganization(values[0]); //0
 validator.checkString(values[1], 255);//1
 validator.checkString(values[2], 255);//2
 validator.checkString(values[3],1);//3
 validator.checkBigDecimal(values[4]);//4
 validator.checkBigDecimal(values[5]);//5
 validator.checkString(values[6],1);//6
 validator.checkBigDecimal(values[7]);//7
 validator.checkBigDecimal(values[8]);//8
 validator.checkBigDecimal(values[9]);//9
 validator.checkString(values[10], 255);//10
 validator.checkString(values[11],1);//11
 validator.checkBigDecimal(values[12]);//12
 validator.checkBigDecimal(values[13]);//13
 validator.checkString(values[14],1);//14
 validator.checkBigDecimal(values[15]);//15
 validator.checkBigDecimal(values[16]);//16
 validator.checkBigDecimal(values[17]);//17
 validator.checkString(values[18], 255);//18
 validator.checkString(values[19], 255);//19
 return values;
 }


 public BaseOBObject internalProcess(Object... values) throws Exception {
	 return createUkuranSize((String) values[0], (String) values[1], (String) values[2],
                 (String) values[3],(String) values[4],(String) values[5],(String) values[6],(String) values[7],
                 (String) values[8],(String) values[9],(String) values[10],(String) values[11],(String) values[12],
                 (String) values[13],(String) values[14],(String) values[15],(String) values[16],(String) values[17],(String) values[18],(String) values[19]);
 }


 public BaseOBObject createUkuranSize(
		        String Organization, //0
			String JenisProduk, //1
                        String GrupProduk, //2
                        String isphasa,//3
                        String phasaamount,//4
                        String sizephasa,//5
                        
                        String isground,//6
                        String phasaground,//7
                         String lebar,//8
                         String tebal,//9   
                         String warna,//10
                         String isnetral,//11
                         String netralamount,//12
                         String sizenetral,//13
                         String islighting,//14
                         String lightingamount,//15
                         String sizelighting,//16     
                         String diameter,//17                        
                         String konduktor,//18
                         String satuan
			)
 throws Exception {
        
	
           
     
	size = OBProvider.getInstance().get(ScgSize.class);	
       
        
        
        size.setOrganization(rowOrganization);
        size.setActive(true);
        size.setSCGJenisProduk(findDALInstance(false, SCGJenisProduk.class, new Value(SCGJenisProduk.PROPERTY_NAME,JenisProduk.equals(null) ? "":JenisProduk )));
	size.setGrupKabel(GrupProduk);
        size.setPhasa((isphasa.toUpperCase().equals("Y"))?true:false); 
    
        size.setPhasaamt((phasaamount.equals("") || phasaamount== null || phasaamount.equals("0"))?null:new BigDecimal(phasaamount));
        size.setSizephasa((sizephasa.equals("") || sizephasa == null || sizephasa.equals("0"))?null:new BigDecimal(sizephasa));        
        
        size.setGround((isground.toUpperCase().equals("Y"))?true:false);
        size.setPhasaGround((phasaground.equals("") || phasaground == null || phasaground.equals("0"))?null:new BigDecimal(phasaground));
        
        size.setWarna(findDALInstance(false, ScgWarna.class, new Value(ScgWarna.PROPERTY_NAMA, warna)));
        size.setNetral((isnetral.toUpperCase().equals("Y"))?true:false);
        size.setNetralamt((netralamount.equals("") || netralamount == null || netralamount.equals("0"))?null : new BigDecimal(netralamount));
       
        size.setSizenetral((sizenetral.equals("") || sizenetral == null || sizenetral.equals("0"))?null:new BigDecimal(sizenetral));
        
        size.setLighting((islighting.toUpperCase().equals("Y"))?true:false);
        size.setLightingamt((lightingamount.equals("") || lightingamount == null || lightingamount.equals("0") )?null:new BigDecimal(lightingamount));
                      
        size.setSizelighting((sizelighting.equals("") || sizelighting == null || sizelighting.equals("0"))?null:new BigDecimal(sizelighting));
        size.setDiameter(diameter);
        size.setTebal((tebal.equals("") || tebal == null || tebal.equals("0"))?null:new BigDecimal(tebal));
        size.setLebar((lebar.equals("") || lebar == null || lebar.equals("0"))?null:new BigDecimal(lebar));
       
        size.setConductor(findDALInstance(false, SCGKonduktor.class, new Value(SCGKonduktor.PROPERTY_NAMA, konduktor.toString())));
        size.setSatuan(findDALInstance(false, ScgSatuan.class, new Value(ScgSatuan.PROPERTY_KODE, satuan.toString())));
        
        ScgSatuan data_uom = findDALInstance(false, ScgSatuan.class, new Value(ScgSatuan.PROPERTY_KODE, satuan.toString()));
        String uom = data_uom.getNama();
        
        
        log4j.error("test : "+uom);  
        
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
    if ( (phasaamount.equals("1") || (phasaamount==null || phasaamount =="") || phasaamount.equals("0") ) || (sizephasa==null || sizephasa == "") || sizephasa.equals("0"))  {
	  kaliP = "";
	  phasaamount="";
    }
    else {
      kaliP = " x ";
    }
    

    //netral
    if ( (netralamount.equals("1") || (netralamount==null || netralamount =="") || netralamount.equals("0") ) || (sizenetral==null || sizenetral == "") || sizenetral.equals("0") || sizenetral.equals("0") ) {
      kaliN = "";
	  netralamount ="";
    }
    else {
      kaliN = " x ";
    }


    //lighting
    if ( (lightingamount.equals("1") || (lightingamount==null || lightingamount =="") || lightingamount.equals("0") ) || (sizelighting==null || sizelighting == "") || sizelighting.equals("0")) {
      kaliL = "";
	  lightingamount="";
    }
    else {
	  kaliL = " x ";
    }

    //FC RC
    //if ((data_diameter!=null) || (data_diameter!="") && (((data_tebal==null) ||(data_tebal.equals("") )) ||((data_lebar ==null) ||(data_lebar.equals("") )))){
    //  kaliFC = "";
    //}


    if(((tebal ==null) ||(tebal =="" )) ||((lebar ==null) ||(lebar =="" ) || lebar.equals("0") || tebal.equals("0"))){
      kaliFC="";
      tebal="";
      lebar="";
    }else{
      kaliFC=" x ";

    }







    //add plus
    //netral
    if ( ((netralamount == null || netralamount == "" || netralamount.equals("0")) && (sizenetral == null || sizenetral == "" || sizenetral.equals("0"))) || isnetral.equals("N") && isphasa.equals("N")) {
      plusNetral = "";
    }else {
	  plusNetral = " + ";
	}

    //lighting
    if ( ((lightingamount == null || lightingamount == "" || lightingamount.equals("0")) && (sizelighting == null || sizelighting == "" || sizelighting.equals("0"))) || isphasa.equals("N") && isnetral.equals("N") && islighting.equals("N") ) {
      plusLighting = "";
    }
	else {
	 plusLighting = " + ";
	}

 //add per

     if((phasaground == null) || (phasaground == "") || phasaground.equals("0")){
        phasaground = "" ;
        perphasa = ""; 
      }else{
        perphasa =" / ";
      }



   

    //finalisasi
    if (isphasa.equals("N")) {
      phasaamount ="";
      sizephasa ="";
	    kaliP="";
      phasaground ="";
    }
    if (isnetral.equals("N")) {
      netralamount ="";
      sizenetral ="";
	  kaliN="";
	  plusNetral = "";
    phasaground ="";
    }
    if (islighting.equals("N")) {
        lightingamount ="";
        sizelighting ="";
	      kaliL="";
	       plusLighting = "";
    phasaground ="";
    }

    if (isphasa.equals("N") && isnetral.equals("N") && islighting.equals("N")) {
      resultBuilder="";
    }


   
    resultBuilder = diameter+tebal+kaliFC+lebar+phasaamount+kaliP+sizephasa+perphasa+phasaground+plusNetral+netralamount+kaliN+sizenetral+pernetral+phasaground+plusLighting+lightingamount+kaliL+sizelighting+perlighting+phasaground;

	String temp = resultBuilder.substring(0,1);
	
  if (temp.equals(" + ") || temp.equals(" x ")) {
		resultBuilder = resultBuilder.substring(1,resultBuilder.length());
	}

   

  
        
        size.setName(resultBuilder+ " "+satuan.toString()+" "+konduktor);
        

					
                                
                                OBDal.getInstance().save(size);
		                OBDal.getInstance().flush();
								
 OBDal.getInstance().commitAndClose();
 
 return size;
 }
    
}
