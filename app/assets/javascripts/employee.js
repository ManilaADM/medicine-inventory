$(document).ready(function(){
	$('body').on('click','.update',function(){
		var data = $(this).closest('.dataContainer tr');
		$('#id').val(data.find('.id').text());
		$('#employeeCode').val(data.find('.employeeCode').text());
		$('#firstName').val(data.find('.firstName').text());
		$('#lastName').val(data.find('.lastName').text());
	});
});