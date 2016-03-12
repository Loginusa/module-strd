package com.gai.master.erpCommon.ad_callouts;

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

public class GMMDepartementUser extends SimpleCallout {

	@Override
	protected void execute(CalloutInfo info) throws ServletException {
		String strRequisition = info.getStringParameter("inpmRequisitionId", null);
		String strUserId = info.getStringParameter("inpadUserId", null);		
		String strDept = null;
		GMMDepartementUserData[] data = GMMDepartementUserData.select(this, strUserId);
		
		if (strUserId != null) {
			strDept = data[0].deptid;
		}

		info.addResult("inpemGmmDepartementId", strDept);
	}

	private static final long serialVersionUID = 1L;

}