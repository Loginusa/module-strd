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

public class GINGroup extends SimpleCallout {

	@Override
	protected void execute(CalloutInfo info) throws ServletException {
		String strfactAcctGroupId = null;
		String stracctSchmId = info.getStringParameter("inpcAcctschemaId", null);
		String strfactAcctId = info.getStringParameter("inpfactAcctId", null);
		String strtableName = info.getStringParameter("GIN_Table_ID", null);
		String strinventoryId = info.getStringParameter("GIN_Record_ID", null);
		GINGroupData[] data = GINGroupData.select(this, strinventoryId, stracctSchmId);
		GINGroupData[] dataInventory = GINGroupData.SelectInventory(this, strinventoryId, stracctSchmId);
		
		List<Object> params = new ArrayList<Object>();
		Object temp = CallStoredProcedure.getInstance().call("GET_UUID", params, null);

		/*if (strtableName.equals("323")) {
			try {
			    strfactAcctGroupId = data[0].factAcctGroupId;  
			} catch (Exception e) {
				strfactAcctGroupId = temp.toString();				
			}			
		}	
		else*/ 
		if (strtableName.equals("321")) {
			try {
			    strfactAcctGroupId = dataInventory[0].factAcctGroupId;  
			} catch (Exception e) {
				strfactAcctGroupId = temp.toString();	
			}
		}

		info.addResult("inpfactAcctGroupId", strfactAcctGroupId);
	}

	private static final long serialVersionUID = 1L;

}