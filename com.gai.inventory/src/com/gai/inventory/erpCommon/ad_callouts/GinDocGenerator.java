package com.gai.inventory.erpCommon.ad_callouts;

import javax.servlet.ServletException;

import org.openbravo.erpCommon.ad_callouts.SimpleCallout;
import org.openbravo.model.common.plm.Product;

import org.apache.log4j.Logger;

public class GinDocGenerator extends SimpleCallout {

	@Override
	protected void execute(CalloutInfo info) throws ServletException {
		String currentNext = "0";
		String strDocTypeId = info.getStringParameter("inpemGinDoctypeorId", null);
		String strDocTypeTrxId = info.getStringParameter("inpemGinDoctypeosId", null);
		String strDocTypesobId = info.getStringParameter("inpemGinDoctypesobId", null);
		String strTabId = info.getStringParameter("inpTabId", null);
		log4j.debug("strTabId : "+strTabId);

		//baru inpdocumentno
		//String oldProductId = info.getStringParameter("inpmProductId", null);
		//String oldDocumentValueProd = "";
		//String oldDocumentNameProd = "";
		//String documentValueProd = "";

		//String oldPartnerId = info.getStringParameter("inpcBpartnerId", null);
		//String oldDocumentValuePartner = "";
		//String oldDocumentNamePartner = "";
		//String documentValuePartner = "";


		//other Goods Shipment
		String oldInventoryId = info.getStringParameter("inpmInventoryId", null);
		log4j.debug("oldInventoryId : "+oldInventoryId);
		String oldDocumentValueShipment = "";
		String oldDocumentNameShipment = "";
		String documentValueShipment = "";

		//other Goods Receipt
		//String oldNameId = info.getStringParameter("inpmInventoryId", null);
		String oldDocumentValueReceipt = "";
		String oldDocumentNameReceipt = "";
		String documentValueReceipt = "";

		//end baru
		GinDocGeneratorData[] data;
		GinDocGeneratorData[] data1;
		GinDocGeneratorData[] data2;
		String strTabName;
		
		strTabName = GinDocGeneratorData.selectTab(this, strTabId);
		data = GinDocGeneratorData.selectDoc(this, strDocTypeId);
		data1 = GinDocGeneratorData.selectDoc(this, strDocTypeTrxId);
		data2 = GinDocGeneratorData.selectDoc(this, strDocTypesobId);

		if (data != null && data.length > 0) {
			if (data[0].isdocnocontrolled.equals("Y")) {
				currentNext = "<" + data[0].currentnext + ">";
				currentNext = data[0].prefix+data[0].currentnext;

				//documentValueProd = data[0].name; //baru
				//documentValuePartner = data[0].name; //baru
				documentValueShipment = data[0].name; //baru
				documentValueReceipt = data[0].name; //baru
			}
		}

		if (data1 != null && data1.length > 0) {
			if (data1[0].isdocnocontrolled.equals("Y")) {
				currentNext = "<" + data1[0].currentnext + ">";
				currentNext = data1[0].prefix+data1[0].currentnext;
				//documentValueProd = data1[0].name; //baru
				//documentValuePartner = data1[0].name; //baru
				documentValueShipment = data1[0].name; //baru
				documentValueReceipt = data1[0].name; //baru
			}
		}
		if (data2 != null && data2.length > 0) {
			if (data2[0].isdocnocontrolled.equals("Y")) {
				currentNext = "<" + data2[0].currentnext + ">";
				currentNext = data2[0].prefix+data2[0].currentnext;
				//documentValueProd = data2[0].name; //baru
				//documentValuePartner = data2[0].name; //baru
				documentValueShipment = data2[0].name; //baru
				documentValueReceipt = data2[0].name; //baru
			}
		}
		//Product
		/*if (strTabName.equals("Product"))	{
			data = GinDocGeneratorData.selectProduct(this, oldProductId);
			if (data != null && data.length > 0) {
				oldDocumentValueProd = data[0].kode;
			}

			cek bila name dari document lama sama dengan name document baru, bila sama maka itu termasuk update, sehingga tidak memperbaharui yg baru

			if ((oldDocumentNameProd.equals(documentValueProd)) ) {
				info.addResult("inpvalue",oldDocumentValueProd);
			}
			else {
				info.addResult("inpvalue", currentNext);
			}

		} else if (strTabName.equals("Business Partner")){
			data = GinDocGeneratorData.selectPartner(this, oldPartnerId);
			if (data != null && data.length > 0) {
				oldDocumentValuePartner = data[0].kode;
			}

			if ((oldDocumentNamePartner.equals(documentValuePartner)) ) {
				info.addResult("inpvalue",oldDocumentValuePartner);
			}
			else {
				info.addResult("inpvalue", currentNext);
			}
		} else */ 
		if (strTabName.equals("Other Goods Shipment")){
			log4j.debug("Masuk OTS");
			data = GinDocGeneratorData.selectOtherShipment(this, oldInventoryId);
			if (data != null && data.length > 0) {
				oldDocumentValueShipment = data[0].kode;
				oldDocumentNameShipment = data[0].name;
			}

			if ((oldDocumentNameShipment.equals(documentValueShipment)) ) {
				info.addResult("inpemGinDocumentno",oldDocumentValueShipment);
				info.addResult("inpname",oldDocumentNameShipment);
			}
			else {
				info.addResult("inpemGinDocumentno", currentNext);
			}
		}else if (strTabName.equals("Stok Opname Bulanan")){
			log4j.debug("Masuk SOB");
			data = GinDocGeneratorData.selectStokOpname(this, oldInventoryId);
			if (data != null && data.length > 0) {
				oldDocumentValueShipment = data[0].kode;
				oldDocumentNameShipment = data[0].name;
			}

			if ((oldDocumentNameShipment.equals(documentValueShipment)) ) {
				info.addResult("inpemGinDocumentno",oldDocumentValueShipment);
				info.addResult("inpname",oldDocumentNameShipment);
			}
			else {
				info.addResult("inpemGinDocumentno", currentNext);
			}
		} else if (strTabName.equals("Other Goods Receipt")){
			log4j.debug("Masuk OTR");
			data = GinDocGeneratorData.selectOtherReceipt(this, oldInventoryId);
			if (data != null && data.length > 0) {
				oldDocumentValueReceipt = data[0].kode;
				oldDocumentNameReceipt = data[0].name;
			}

			if ((oldDocumentNameReceipt.equals(documentValueReceipt)) ) {
				info.addResult("inpemGinDocumentno",oldDocumentValueReceipt);
				info.addResult("inpname",oldDocumentNameReceipt);
			}
			else {
				info.addResult("inpemGinDocumentno", currentNext);
			}
		}
	}

	private static final long serialVersionUID = 1L;

}
