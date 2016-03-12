/*
 *************************************************************************
 * The contents of this file are subject to the Openbravo  Public  License
 * Version  1.1  (the  "License"),  being   the  Mozilla   Public  License
 * Version 1.1  with a permitted attribution clause; you may not  use this
 * file except in compliance with the License. You  may  obtain  a copy of
 * the License at http://www.openbravo.com/legal/license.html 
 * Software distributed under the License  is  distributed  on  an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific  language  governing  rights  and  limitations
 * under the License. 
 * The Original Code is Openbravo ERP. 
 * The Initial Developer of the Original Code is Openbravo SLU 
 * All portions are Copyright (C) 2011 Openbravo SLU 
 * All Rights Reserved. 
 * Contributor(s):  __________
 ************************************************************************
 */
package com.gai.sutrado.sellingprice.erpCommon.ad_process;


import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.math.*;
import java.text.DecimalFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.openbravo.base.exception.OBException;
import org.openbravo.client.kernel.BaseActionHandler;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBCriteria;
import org.openbravo.dal.service.OBDal;
import org.openbravo.dal.service.OBQuery;
import org.openbravo.dal.core.DalUtil;
import org.openbravo.dal.service.OBDao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openbravo.base.session.OBPropertiesProvider;
import org.openbravo.base.structure.BaseOBObject;

import org.openbravo.data.FieldProvider;
import org.openbravo.database.ConnectionProvider;
import org.openbravo.erpCommon.utility.CashVATUtil;
import org.openbravo.erpCommon.utility.FieldProviderFactory;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.erpCommon.utility.Utility;

import com.gai.sutrado.sellingprice.data.GspMasterPrice;
import org.openbravo.model.common.currency.ConversionRate;

/**
 * kumpulkan seluruh ID record yg terpilih dan kembalikan nilai hasilnya.
 */
public class ShowPriceActionHandler extends BaseActionHandler {

  protected JSONObject execute(Map<String, Object> parameters, String data) {
    try {

      // get the data as json
      final JSONObject jsonData = new JSONObject(data);
      final JSONArray listIds = jsonData.getJSONArray("ids");




      StringBuilder whereClause = new StringBuilder();
      whereClause.append(" as f");
      whereClause.append(" where f.tanggal=(SELECT MAX(ff.tanggal) FROM gsp_master_price ff WHERE ff.materialType= f.materialType)");
      whereClause.append(" ORDER BY materialtype ASC");


      final OBQuery<GspMasterPrice> obc = OBDal.getInstance().createQuery(GspMasterPrice.class,
          whereClause.toString());



      DecimalFormat df = new DecimalFormat("#,##0.######");


          String daftarId = "";
          String lmea2c = "";
          String lmea3c = "";
          String lmecu = "";
          String premiuma2c = "";
          String premiuma3c = "";
          String premiumcu = "";
          String kursUsd = "";
          String ratepembagi = "";
            
            for (GspMasterPrice gspmaster : obc.list()) {
           
            GspMasterPrice gspmasterprice = OBDal.getInstance().get(GspMasterPrice.class, gspmaster.getId());
            
            if(!gspmasterprice.getLMEAAC().toString().equals("0") && !gspmasterprice.getPremiumAAC().toString().equals("0")){
               
                 lmea2c = df.format(gspmasterprice.getLMEAAC()).toString();
                 premiuma2c = df.format(gspmasterprice.getPremiumAAC()).toString();
            }
            if(!gspmasterprice.getLMEAAAC().toString().equals("0") && !gspmasterprice.getPremiumAAAC().toString().equals("0")){
               
                 lmea3c = df.format(gspmasterprice.getLMEAAAC()).toString();
                 premiuma3c = df.format(gspmasterprice.getPremiumAAAC()).toString();
            }
           if(!gspmasterprice.getLMECopper().toString().equals("0") && !gspmasterprice.getPremiumCopper().toString().equals("0")){
               
                 lmecu = df.format(gspmasterprice.getLMECopper()).toString();
                 premiumcu = df.format(gspmasterprice.getPremiumCopper()).toString();
            }

        
      }


            StringBuilder whereClauses = new StringBuilder();
      whereClauses.append(" as f");
      whereClauses.append(" where f.validFromDate=(SELECT MAX(ff.validFromDate) FROM CurrencyConversionRate ff  WHERE ff.currency='100' AND ff.toCurrency='303')");
      whereClauses.append(" ORDER BY currency ASC");

        final OBQuery<ConversionRate> obd = OBDal.getInstance().createQuery(ConversionRate.class,
          whereClauses.toString());

      for (ConversionRate conv : obd.list()) {

          kursUsd = df.format(conv.getMultipleRateBy()).toString();
          ratepembagi = df.format(conv.getDivideRateBy()).toString();
      }

       /*  daftarId = daftarId+"LME AAC&nbsp;&nbsp;&nbsp;: "+lmea2c+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
        "Premium AAC&nbsp;&nbsp;: "+premiuma2c+"<br/>"+"LME AAAC : "+lmea3c+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
        "Premium AAAC : "+premiuma3c+"<br/>"+"LME CU&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: "+lmecu+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
        "Premium CU&nbsp;&nbsp;&nbsp;&nbsp;: "+premiumcu+"<br/>"; */


  daftarId = daftarId+"<table>"
  +"<tr>"
    +"<td>LME AAC</td>"
    +"<td>:</td>"
    +"<td>"+lmea2c+"</td>" 
    +"<td>Premium AAC</td>"
    +"<td>:</td>"
    +"<td>"+premiuma2c+"</td>"
  +"</tr>"
  +"<tr>"
    +"<td>LME AAAC</td>"
    +"<td>:</td>"
    +"<td>"+lmea3c+"</td>" 
    +"<td>Premium AAAC</td>"
    +"<td>:</td>"
    +"<td>"+premiuma3c+"</td>"
  +"</tr>"
  +"<tr>"
  +"<td>LME Cu</td>"
    +"<td>:</td>" 
    +"<td>"+lmecu+"</td>"
    +"<td>Premium Cu</td>"
    +"<td>:</td>"
    +"<td>"+premiumcu+"</td>"
  +"</tr>"
  +"<tr>"
  +"<td>Kurs USD</td>"
    +"<td>:</td>" 
    +"<td>"+kursUsd+"</td>"
    +"<td>Devide Rate</td>"
    +"<td>:</td>"
    +"<td>"+ratepembagi+"</td>"
  +"</tr>"
+"</table>";





      JSONObject json = new JSONObject();
      json.put("idrecord", daftarId);

      // and return it
      return json;
    } catch (Exception e) {
      throw new OBException(e);
    }
  }
}
