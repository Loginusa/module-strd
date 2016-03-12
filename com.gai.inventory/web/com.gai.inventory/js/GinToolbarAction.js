(function () {   
	  //printtoolbar
	  var buttonToolbarPrint = {
		  action: function(){
			var callback, ids = [], i, view = this.view, grid = view.viewGrid, selectedRecords = grid.getSelectedRecords(), idtab = view.tabId ;
			var string_id="",postParams = [];
			var command = "";
			for (i = 0; i < selectedRecords.length; i++) {
			  string_id= string_id +"'"+selectedRecords[i].id+"'"+",";
			}

			if (string_id.length > 0 ) {
			  string_id = string_id.substring(0,string_id.length-1);
			}

			var r = confirm("[Confirmation] Print this record ?");
			if (r == true) {
			
				if (idtab === '7AA42294DB5A46428413F08AE357AFAB' ) {  //Fund Delivery Confirmation
					command = 'PRINT_OGS';
					//alert('Cashflow Projection > ' +' <br>*Record : '+string_id+" ("+command+")");
					} else if (idtab === 'E82814480E0043DEB2E6363E71F3A988' ) {
					command = 'PRINT_OGR';

				}
				
			postParams['Command'] = command;
			postParams['inpRecordId'] = string_id;
			OB.Utilities.openProcessPopup(OB.Application.contextUrl + '/com.gai.inventory.erpCommon.ad_reports/ToolbarPrint.html', false, postParams, 200, 320);
	
			} else {
				//do nothing
			}			

		  },
		  buttonType: 'gin_toolbarprint',
		  prompt: 'Inventory Print Confirmation',
		  updateState: function(){
			  var view = this.view, form = view.viewForm, grid = view.viewGrid, selectedRecords = grid.getSelectedRecords();
			  if (view.isShowingForm && form.isNew) {
				this.setDisabled(true);
			  } else if (view.isEditingGrid && grid.getEditForm().isNew) {
				this.setDisabled(true);
			  } else {
				this.setDisabled(selectedRecords.length === 0);
			  }
		  }
		};
		
		
  // register the button for the sales order tab
  // the first parameter is a unique identification so that one button can not be registered multiple times.
  //register button print
  OB.ToolbarRegistry.registerButton(buttonToolbarPrint.buttonType, isc.OBToolbarIconButton, buttonToolbarPrint, 100, ['7AA42294DB5A46428413F08AE357AFAB','E82814480E0043DEB2E6363E71F3A988']);
}());
