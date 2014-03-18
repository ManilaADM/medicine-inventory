$( document ).ready(function() {
	
    function showDialogBox(actionCall, retBtn) {
		$(".retConfOverlayBox").dialog({
			draggable: true, 
			resizable: false, 
			closeText: "close",
			height:150, width:400, modal: true,  show: {effect: 'fade', duration: 500},
			hide: {effect: 'fade', duration: 500}, 
			open: function(event, ui)
			{
				if($('#txnErrorAlert').css('display') == 'block'){
					$('#txnErrorAlert').slideToggle() 
				}
			},
			buttons: {
				"OK": function() {
					actionCall(retBtn);
					$(this).dialog("close");
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
		});
	};
	
	$('.logsTable').on('click','.returnBtn',function() {
		
		var data = $(this).closest('.logsTable tr');
  	    var txnId = data.find('.id').text();
		var medId = data.find('.medSupId').text();
		var brandName = data.find('.medSupply').text();
		var qty = data.find('.quantity').text();
		
		var executeReturn = function(retButton){
			jsRoutes.controllers.TransactionController.returnMedSupply(txnId, medId).ajax({
			        success: function(data) {
			        	 //alert('success action call thru js! meds> ' + brandName);
			        	 retButton.attr('disabled',true);
			        },
			        error: function() {
			          if($('#txnErrorAlert').css('display') == 'none') {
			        	  $('#txnErrorAlert').slideToggle();
			          }
			        }
			});
	    }
		
		$('#medSupToReturn').html(qty + " pc(s) of " + brandName +'?');
		showDialogBox(executeReturn, $(this));
	
	}); // end logs table click
})