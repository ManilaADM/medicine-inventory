$(document).ready(function(){
	
	//Update for Employee
	$('body').on('click','.update',function(){
		var data = $(this).closest('.dataContainer tr');
		$('#oid').val(data.find('.id').text());
		$('#employeeCode').val(data.find('.employeeCode').text());
		$('#firstName').val(data.find('.firstName').text());
		$('#lastName').val(data.find('.lastName').text());
		$('#gender').val(data.find('.gender').text());
		$('#team').val(data.find('.team').text());
		$('#category').val(data.find('.category').text());
	});
	

	//Update for Medicine
	$('body').on('click','.update',function(){
		var data = $(this).closest('.dataContainer tr');
		$('#oid').val(data.find('.id').text());
		$('#brandName').val(data.find('.brandName').text());
		$('#genericName').val(data.find('.genericName').text());
		$('#description').val(data.find('.description').text());
		$('#count').val(data.find('.count').text());
		$('#notificationAlertCount').val(data.find('.notificationAlertCount').text());
		$('#dailyQtyLimitPerUser').val(data.find('.dailyQtyLimitPerUser').text());
		$('#available').prop('checked', (data.find('.available').text()==="true"));
		$('#quantifiable').prop('checked',(data.find('.quantifiable').text()==="true"));
	});
});