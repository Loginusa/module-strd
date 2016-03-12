package com.gai.inventory.erpCommon.ad_callouts;
 
import java.util.ArrayList;
import java.util.List;

import org.openbravo.dal.core.DalUtil;
import org.openbravo.dal.core.OBContext;
import org.openbravo.dal.service.OBDal;
import org.openbravo.base.exception.OBException;
import org.openbravo.base.structure.BaseOBObject;

import javax.servlet.ServletException;
import org.openbravo.erpCommon.ad_callouts.SimpleCallout;
import org.openbravo.service.db.CallStoredProcedure;

public class GINInventory  extends SimpleCallout {
 
  //private static final long serialVersionUID = 1L;
 
  @Override
  protected void execute(CalloutInfo info) throws ServletException {
   String strTab = info.getStringParameter("inpTabId", null);

   if (strTab.equals("1CB6CAB9111C48A4AA4616363E8CA96B")) { //Other Goods Shipment
      info.addResult("inpqtycount", "0");
   } else if (strTab.equals("EDE88C405ED242878A64F7577B88E3E1")) { //Other Goods Receipt
   	  info.addResult("inpqtybook", "0");
   }

  }

  private static final long serialVersionUID = 1L;
}
