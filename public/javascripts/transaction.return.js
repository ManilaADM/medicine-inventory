$( document ).ready(function() {
	
    function showDialogBox(actionCall, retBtn) {
	  
		$(".retConfOverlayBox").dialog({
			  draggable: true,
			  resizable: false,
			  closeText: "close",
		      height:150,
		      width:400,
		      modal: true,
		      show: {effect: 'fade', duration: 500},
		      hide: {effect: 'fade', duration: 500},
		      buttons: {
		        "OK": function() {
		        	actionCall(retBtn);
		        	$( this ).dialog( "close" );
		        },
		        Cancel: function() {
		          $( this ).dialog( "close" );
		        }
		      }
		    });
	};
	
	$('.logsTable').on('click','.returnBtn',function(){
		
		var data = $(this).closest('.logsTable tr');
  	    var txnId = data.find('.id').text();
		var med_id = data.find('.medSupId').text();
		var brandName = data.find('.medSupply').text();
		var qty = data.find('.quantity').text();
		
		var executeReturn = function(retButton){
			jsRoutes.controllers.TransactionController.returnMedSupply().ajax({
			        success: function(data) {
			        	 alert('success action call thru js! meds> ' + brandName);
			        	 retButton.attr('disabled',true);
			        },
			        error: function() {
			          alert("Error!")
			        }
			});
	    }
		
		$('#medSupToReturn').html(qty + " pc(s) of " + brandName +'?');

		showDialogBox(executeReturn, $(this));
		
		/*$(".retConfOverlayBox").dialog({
		  draggable: true,
		  resizable: false,
		  closeText: "close",
	      height:150,
	      width:400,
	      modal: true,
	      show: {effect: 'fade', duration: 500},
	      hide: {effect: 'fade', duration: 500},
	      buttons: {
	        "OK": function() {
	        	executeReturn();
	        	$( this ).dialog( "close" );
	        },
	        Cancel: function() {
	          $( this ).dialog( "close" );
	        }
	      }
	    });*/
		
	
	}); // end logs table click
})