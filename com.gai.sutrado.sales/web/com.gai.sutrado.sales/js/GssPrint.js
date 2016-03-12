(function () {
 
    var btPopGssHeaderPrint = {
      action: function(){
        var callback, ids = [], i, view = this.view, grid = view.viewGrid, selectedRecords = grid.getSelectedRecords();
        var string_id="",postParams = [];
        for (i = 0; i < selectedRecords.length; i++) {
          string_id= string_id +"'"+selectedRecords[i].id+"'"+",";
        }

        if (string_id.length > 0 ) {
          string_id = string_id.substring(0,string_id.length-1);
        }

        console.log(string_id);

        postParams['Command'] = 'PRINT_ORDER_PRODUKSI';
        postParams['inpcOrderId'] = string_id;//inpmMovementId:647C5E777018432CA933C34A13F30E93
        OB.Utilities.openProcessPopup(OB.Application.contextUrl + '/com.gai.sutrado.sales.erpCommon.ad_reports/GssPrint.html', false, postParams, 200, 320);
        
      },
      buttonType: 'gss_printorderproduksi',
      prompt: OB.I18N.getLabel('Print Order For Production'),
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
  //OB.ToolbarRegistry.registerButton(buttonProps.buttonType, isc.OBToolbarIconButton, buttonProps, 100, '259');
  //OB.ToolbarRegistry.registerButton(nack.buttonType, isc.OBToolbarIconButton, nack, 100, '263');
  OB.ToolbarRegistry.registerButton(btPopGssHeaderPrint.buttonType, isc.OBToolbarIconButton, btPopGssHeaderPrint, 101, '186');
  
}());