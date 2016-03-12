(function () {
  var buttonPriceId = {
      action: function(){
        //mulai aksi
        var callback, ids = [], i, view = this.view, grid = view.viewGrid, selectedRecords = grid.getSelectedRecords();
        // koleksi semua id (ids)
        //for (i = 0; i < selectedRecords.length; i++) {
          ids.push("");
        //}
        
        // define the callback function which shows the result to the user
        callback = function(rpcResponse, data, rpcRequest) {
          isc.say(OB.I18N.getLabel('GSP_INFO_PRICE_LME_PREMIUM', [data.idrecord]));
        }
        
        // and call the server
        OB.RemoteCallManager.call('com.gai.sutrado.sellingprice.erpCommon.ad_process.ShowPriceActionHandler', {ids: ids}, {}, callback);

      },
      buttonType: 'gsp_price_id',
      prompt: OB.I18N.getLabel('GSP_Price_Show'),
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
  OB.ToolbarRegistry.registerButton(buttonPriceId.buttonType, isc.OBToolbarIconButton, buttonPriceId, 110, null);
}());