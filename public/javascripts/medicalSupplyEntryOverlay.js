$(document).ready(function(){    
    $('#quantifiable').change(function() {
        if($(this).is(":checked")) {
        	$('#count').val('');
        	$('#dailyQtyLimitPerUser').val('');
        	$('#dailyQtyLimitPerUser').prop('disabled', false);
        	$('#notificationAlertCount').val('');
        	$('#notificationAlertCount').prop('disabled', false);
        }
        else {
        	$('#count').val('1');
        	$('#dailyQtyLimitPerUser').val('0');
        	$('#dailyQtyLimitPerUser').prop('disabled', true);
        	$('#notificationAlertCount').val('0');
        	$('#notificationAlertCount').prop('disabled', true);
        }        
    });
    
    $('#count').change(function() {
    	var firstVal = $('#count').val().charAt(0);
    	if (firstVal == '0') {
    		$('#available').prop('checked', false);
    	}
    });
})
