
package com.gai.master.bom.erpCommon.ad_process;


import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.utility.OBError;
import org.openbravo.model.common.plm.Product;
import org.openbravo.model.common.enterprise.Organization;
import org.openbravo.scheduling.ProcessBundle;
import org.openbravo.service.db.DalBaseProcess;

/**
*Author : Mochamad Fachmi Rizal
*/

public class ReactiveBom extends DalBaseProcess {

	public void doExecute(ProcessBundle bundle) throws Exception {
		try {

      // retrieve the parameters from the bundle
			final String productId = (String) bundle.getParams().get("M_Product_ID");
			final String confirmation = (String) bundle.getParams().get("confirmation");
			final String organizationId = (String) bundle.getParams().get("adOrgId");
			final String tabId = (String) bundle.getParams().get("tabId"); 
      // implement your process here

			if (confirmation.equals("N")) {
				final OBError msg = new OBError();
				msg.setType("Warning");
				msg.setMessage("Canceled");
				msg.setTitle("Reactive");
				bundle.setResult(msg);
			}
			else {

      // Show a result
				final StringBuilder sb = new StringBuilder();
				sb.append("Read information:<br/>");
				if (productId != null) {
					final Product product = OBDal.getInstance().get(Product.class, productId);
					sb.append("Product: " + product.getSearchKey() + "<br/>");
					product.setBOMVerified(false);

					OBDal.getInstance().save(product);
					OBDal.getInstance().flush();
					OBDal.getInstance().commitAndClose();
				}
				else {
					throw new Exception("Product id is null");
				}
				sb.append("Reactivated ! <br/>");

      // OBError is also used for successful results
				final OBError msg = new OBError();
				msg.setType("Success");
				msg.setTitle("Reactive record");
				msg.setMessage(sb.toString());

				bundle.setResult(msg);
			}
		} catch (final Exception e) {
			e.printStackTrace(System.err);
			final OBError msg = new OBError();
			msg.setType("Error");
			msg.setMessage(e.getMessage());
			msg.setTitle("Error occurred");
			bundle.setResult(msg);
		}
	}
}