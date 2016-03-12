package com.gai.sequence.actionHandler;

import javax.enterprise.event.Observes;

import org.apache.log4j.Logger;
import org.openbravo.base.model.Entity;
import org.openbravo.base.model.ModelProvider;
import org.openbravo.base.model.Property;
import org.openbravo.client.kernel.RequestContext;
import org.openbravo.client.kernel.event.EntityNewEvent;
import org.openbravo.client.kernel.event.EntityPersistenceEvent;
import org.openbravo.client.kernel.event.EntityPersistenceEventObserver;
import org.openbravo.client.kernel.event.EntityUpdateEvent;
import org.openbravo.dal.service.OBDal;
import org.openbravo.erpCommon.utility.Utility;
import org.openbravo.model.common.enterprise.DocumentType;
import org.openbravo.model.materialmgmt.transaction.InventoryCount;
import org.openbravo.service.db.DalConnectionProvider;


public class GinHandlerInventory extends EntityPersistenceEventObserver {

	public void onUpdate(@Observes EntityUpdateEvent event) {
		handleEvent(event);
	}

	public void onSave(@Observes EntityNewEvent event) {
		handleEvent(event);
	}

	private void handleEvent(EntityPersistenceEvent event) {
		if (!isValidEvent(event)) return;
		if (event.getTargetInstance().getEntity() != imEntity) return;

		String getWindowId = RequestContext.get().getRequestParameter("windowId");
		
		if (getWindowId==null || getWindowId=="" || getWindowId.length()==0) {
			getWindowId = "backgroundProcess";
		}
		//cek apakah update terjadi di background process?
		if (getWindowId.equals("backgroundProcess")) {
			log4j.info("Window = "+getWindowId+"Tidak melakukan handler karena update berjalan di background process");
			//tidak melakukan apapun karena, proses update tidak terjadi di area yg memiliki window
		}
		else {
			log4j.info("Menjalankan Handler");
			Entity entity = imEntity;
			boolean isUpdate = (event instanceof EntityUpdateEvent);
			String documentNo = (String) event.getCurrentState(imDocumentNoProperty);
			String prevDocumentNo = (isUpdate) ? (String) ((EntityUpdateEvent) event).getPreviousState(imDocumentNoProperty) : null;
				/*boolean processed = false;
			Object oProcessed = (imgcrRequisition == null ? false : event.getCurrentState(imgcrRequisition));
			if (oProcessed instanceof String) {
				processed = "Y".equals(oProcessed.toString());
			} else if (oProcessed instanceof Boolean) {
				processed = (Boolean) oProcessed;
			}*/
			log4j.info("isUpdate = " + isUpdate);
			log4j.info("documentNo = " + documentNo);
			log4j.info("prevDocumentNo = " + prevDocumentNo);
			//log4j.info("processed = " + processed);
			//if (!processed || !isUpdate) {
			if (!isUpdate) {
				final DocumentType docType = null;
				final DocumentType gaiDocTypeTarget = (imgaiDocTypeTargetProperty == null ? null : (DocumentType) event.getCurrentState(imgaiDocTypeTargetProperty));
				final DocumentType gaiDocType = (imgaiDocTypeProperty == null ? null : (DocumentType) event.getCurrentState(imgaiDocTypeProperty));
				final String docTypeId = docType != null ? docType.getId() : "";
				final String gaiDocTypeTargetId = gaiDocTypeTarget != null ? gaiDocTypeTarget.getId() : "";
				final String gaiDocTypeId = gaiDocType != null ? gaiDocType.getId() : "";
				String windowId = RequestContext.get().getRequestParameter("windowId");
				if (windowId == null) windowId = "";
				log4j.info("docTypeId = " + docTypeId);
				log4j.info("gaiDocTypeTargetId = " + gaiDocTypeTargetId);
				log4j.info("gaiDocTypeId = " + gaiDocTypeId);

				boolean forceUpdate = true;
				if (getWindowId.equals("BBDDA551A006470F94B58509B6D7878B")) { //other goods shipment
					log4j.info("Conditional 1(other goods shipment)");
					Utility.getDocumentNo(OBDal.getInstance().getConnection(false), new DalConnectionProvider(false), RequestContext.get().getVariablesSecureApp(), windowId, entity.getTableName(), docTypeId, gaiDocTypeTargetId, false, true);
				}
				else if (getWindowId.equals("5CF189865D1F4CB185183C2B1D8BDB66")) { //other goods receipt
					log4j.info("Conditional 1(other goods receipt)");
					Utility.getDocumentNo(OBDal.getInstance().getConnection(false), new DalConnectionProvider(false), RequestContext.get().getVariablesSecureApp(), windowId, entity.getTableName(), docTypeId, gaiDocTypeId, false, true);
				}
				else if (getWindowId.equals("backgroundProcess")) {
					log4j.info("Conditional 2(backgroundProcess)");
					//tidak melakukan apapun karena, proses update tidak terjadi di area yg memiliki window
				}
				else {//update terjadi di window selain kondisi ke 1 {Other Goods Shipment & Receipt}
					log4j.info("Conditional 3(other goods receipt, other goods shipment)");
					documentNo = (forceUpdate) ? Utility.getDocumentNo(OBDal.getInstance().getConnection(false), new DalConnectionProvider(false), RequestContext.get().getVariablesSecureApp(), windowId, entity.getTableName(), docTypeId, gaiDocTypeTargetId, false, true) : prevDocumentNo;
					documentNo = (forceUpdate) ? Utility.getDocumentNo(OBDal.getInstance().getConnection(false), new DalConnectionProvider(false), RequestContext.get().getVariablesSecureApp(), windowId, entity.getTableName(), docTypeId, gaiDocTypeId, false, true) : prevDocumentNo;
				}
				log4j.info("new documentNo = " + documentNo);
				event.setCurrentState(imDocumentNoProperty, documentNo);
				log4j.info("event = " + event);
				log4j.info("getWindowId = " + getWindowId);
			}
		}
	}

	@Override
	protected synchronized Entity[] getObservedEntities() {
		return entities;
	}

	private static final Entity imEntity = ModelProvider.getInstance().getEntity(InventoryCount.ENTITY_NAME);
	private static final Property imDocumentNoProperty = imEntity.getProperty(InventoryCount.PROPERTY_GINDOCUMENTNO);
	private static final Property imgaiDocTypeProperty = imEntity.getProperty(InventoryCount.PROPERTY_GINDOCTYPEOR); //other goods receipt
	private static final Property imgaiDocTypeTargetProperty = imEntity.getProperty(InventoryCount.PROPERTY_GINDOCTYPEOS); //other goods shipment
	private static final Entity[] entities = { imEntity };

	private static final Logger log4j = Logger.getLogger(GinHandlerInventory.class);

}
