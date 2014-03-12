$( document ).ready(function() {
	
	$('.logsTable').on('click','.returnBtn',function(){
		
		var data = $(this).closest('.logsTable tr');
  	    var id = data.find('.id').text();
		var med_id = data.find('.medSupId').text();
		var brandName = data.find('.medSupply').text();
		var qty = data.find('.quantity').text();
		 
		 $('#medSupToReturn').html(qty + " pc(s) of " +brandName+'?');
			 
	});
	
})