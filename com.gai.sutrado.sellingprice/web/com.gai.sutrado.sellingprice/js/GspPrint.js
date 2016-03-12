(function () {
  /*var buttonProps = {
      action: function(){
        alert('You clicked me!');
      },
      buttonType: 'gaitest_print',
      prompt: OB.I18N.getLabel('GAITEST_Print'),
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

    var nack = {
      action: function(){
        alert('Hehe!');
      },
      buttonType: 'gaitest_print_n',
      prompt: "Nckhl"/*OB.I18N.getLabel('GAITEST_Print'),
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
    };*/

    var btPopGspHeaderPrint = {
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

        postParams['Command'] = 'PRINT_SELLING_PRICE';
        postParams['inpgspSellingPriceId'] = string_id;//inpmMovementId:647C5E777018432CA933C34A13F30E93
        OB.Utilities.openProcessPopup(OB.Application.contextUrl + '/com.gai.sutrado.sellingprice.erpCommon.ad_reports/GspPrint.html', false, postParams, 200, 320);
        
      },
      buttonType: 'gsp_printsellingprice',
      prompt: OB.I18N.getLabel('Print Net Price Header'),
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

    var btPopGspLinePrint = {
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

        postParams['Command'] = 'PRINT_SELLPRICE_LINE';
        postParams['inpgspSellingPriceId'] = string_id;//inpmMovementId:647C5E777018432CA933C34A13F30E93
        OB.Utilities.openProcessPopup(OB.Application.contextUrl + '/com.gai.sutrado.sellingprice.erpCommon.ad_reports/GspPrint.html', false, postParams, 200, 320);
        
      },
      buttonType: 'gsp_printsellingpriceline',
      prompt: "Print For Net Price Line",//OB.I18N.getLabel('Print For Selling Price Line'),
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

    var btPopGspPrintPriceList = {
      action: function(){
        var callback, ids = [], i, view = this.view, grid = view.viewGrid, selectedRecords = grid.getSelectedRecords();
        var string_id="",postParams = [];
        var header_id="";

        //dapatkan header 
        if (view.parentView != null) {
          var parentGrid = view.parentView.viewGrid , selectedParentRecord = parentGrid.getSelectedRecord();
          header_id=selectedParentRecord.id;

        
          for (i = 0; i < selectedRecords.length; i++) {
            string_id= string_id +"'"+selectedRecords[i].id+"'"+",";
          }
          if (string_id.length > 0 ) {
            string_id = string_id.substring(0,string_id.length-1);
          }
          console.log(string_id);

          postParams['Command'] = 'PRINT_PRICELIST';
          postParams['inpgspSellingPriceId'] = string_id;//inpmMovementId:647C5E777018432CA933C34A13F30E93
          postParams['inpgspSellingPriceHeaderId'] = header_id;
          
          OB.Utilities.openProcessPopup(OB.Application.contextUrl + '/com.gai.sutrado.sellingprice.erpCommon.ad_reports/GspPrint.html', false, postParams, 200, 320);
        } else {
          isc.say("Harap Pilih Header Terlebih dahulu !");
        }    
      },
      buttonType: 'gsp_printpricelist',
      prompt: "Print Price List",//OB.I18N.getLabel('Print For Selling Price Line'),
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
  OB.ToolbarRegistry.registerButton(btPopGspPrintPriceList.buttonType, isc.OBToolbarIconButton, btPopGspPrintPriceList, 110, '9945E8CABD644589AA7702BE35420BA8');
  OB.ToolbarRegistry.registerButton(btPopGspHeaderPrint.buttonType, isc.OBToolbarIconButton, btPopGspHeaderPrint, 101, '6F7BABCDF9CF4E79B1B4C08DE36432B7');
  OB.ToolbarRegistry.registerButton(btPopGspLinePrint.buttonType, isc.OBToolbarIconButton, btPopGspLinePrint, 111, '6F7BABCDF9CF4E79B1B4C08DE36432B7');
  
  
  
}());