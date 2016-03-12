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

public class GINAccount extends SimpleCallout {

	@Override
	protected void execute(CalloutInfo info) throws ServletException {
		String strValue = null;
		String strName = null;
		String strelementvalueId = info.getStringParameter("inpaccountId", null);
		String strtableName = info.getStringParameter("GIN_Table_ID", null);
		GINAccountData[] data = GINAccountData.select(this, strelementvalueId);
		
		
		if (strtableName.equals("321")) {
			strValue = data[0].value;  
			strName = data[0].name;
		}

		info.addResult("inpacctvalue", strValue);
		info.addResult("inpacctdescription", strName);
	}

	private static final long serialVersionUID = 1L;

}