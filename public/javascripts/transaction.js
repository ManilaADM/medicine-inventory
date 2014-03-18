var isOpen = false;
var numberOfMedicineFields = 3;
//if window is resized then reposition the overlay box
$(window).bind('resize',showOverlayBox);

$( document ).ready(function() {
	$("#employeeNameId").autocomplete({
	    source: employeeNamesObj,
	    minLength: 0
	})
	.focus(function() {
	    $(this).autocomplete('search', $(this).val())
	});
	
	var medBrandAndGenericName = [];
	$.each(medicineJsonObj, function(key,value){
		var genericName = value.genericName;
		medBrandAndGenericName.push({ "value" : key, "label": '' + key + ' ('+genericName+')' });
		
	});
		
	for(i = 0; i<numberOfMedicineFields;i++)
	{
		updateMedicineFields(medBrandAndGenericName, i);
	}
	
	for(j = 0; j<numberOfMedicineFields;j++)
	{
		var fieldValue = $('#medicineInput' + j).val();
		updateMedicineQty(fieldValue, '#medicineQty' +j);
		updateTooltip(j, fieldValue);
	}
	
	if(transactionFormHasError === true)
	{
		$.each(errorFieldIds, function(index, value) {
			var field = $('#' + value);
			if (!field.hasClass('overlayError')) {
				field.addClass('overlayError');
			}
		});
		
		displayOverlay('.overlayBox');
	}
});

function updateMedicineFields(medBrandAndGenericName, index)
{
	$('#medicineInput'+ index).autocomplete({
	    source: medBrandAndGenericName,
	    minLength: 0,
	    select: function (event, ui) {
	        var value = ui.item.value;
	    	updateMedicineQty(value, '#medicineQty' + index);
	    	updateTooltip(index, value);
	    	var medicineId = medicineJsonObj[value].idAsString;
	    	$('#medicineId' + index).val(medicineId);
	   }
	})
	.focus(function() {
	    $(this).autocomplete('search', $(this).val())
	})
	.blur(function() {
		var medicineInputField = $('#medicineInput'+ index).val();
		var selectedMedicine = medicineJsonObj[medicineInputField];
		if(!selectedMedicine)
		{
			$('#medicineTooltip' + index).attr('title', '');
			updateMedicineQty('', '#medicineQty' + index);
			$('#medicineId' + index).val('');
		}
	});
}

//function to display the box

function showOverlayBox(id) {
	//if box is not set to open then don't do anything
	if( isOpen == false ) return;
	// set the properties of the overlay box, the left and top positions
		$(id).css({
		display:'block',
		left:( $(window).width() - $(id).width() )/2,
		top:( $(window).height() - $(id).height() )/2 -20,
		position:'absolute'
	});
	// set the window background for the overlay. i.e the body becomes darker
	$('.bgCover').css({
		display:'block',
		width: $(window).width(),
		height:$(window).height(),
	});
}

function slideToggleDiv(id, displayState) {
	if($(id).css('display') == displayState){
		$(id).slideToggle() 
	}
}

function displayOverlay(id) {
	
	//close open msg divs, if any
	slideToggleDiv('#txnErrorAlert', 'block');
	slideToggleDiv('#txnMsg', 'block');
	
	//set status to open
	isOpen = true;
	showOverlayBox(id);
	$('.bgCover').css({opacity:0}).animate( {opacity:0.5, backgroundColor:'#000'} );
	// dont follow the link : so return false.
	return false;
}

function closeOverlay(id) {
	//set status to closed
	isOpen = false;
	$(id).css( 'display', 'none' );
	
	resetOverlay();
	
	// now animate the background to fade out to opacity 0
	// and then hide it after the animation is complete.
	$('.bgCover').animate( {opacity:0}, null, null, function() { $(this).hide(); } );
}

function resetOverlay() {
	var errorMsg = $('#errorMsg');
	errorMsg.html('');
	
	var empField = $('#employeeNameId');
	empField.val('');
	if (empField.hasClass('overlayError')) {
		empField.removeClass('overlayError');
	}
	
	for(j = 0; j<numberOfMedicineFields;j++)
	{
		var field = $('#medicineInput' + j);
		field.val('');
		$('#medicineTooltip' + j).attr('title','');
		if (field.hasClass('overlayError')) {
			field.removeClass('overlayError');
		}
	}
}

function updateMedicineQty(medicineBrandName, selectMedicineQtyId) {
	var options;
	if(medicineBrandName) {
		var medicine = medicineJsonObj[medicineBrandName];
		if(medicine)
		{
			if(medicine.quantifiable !== true) {
				options = '<option value="1">1</option>';
			}
			else {
				var dailyQtyLimitPerUser = medicineJsonObj[medicineBrandName].dailyQtyLimitPerUser;
				var medicineCount = medicineJsonObj[medicineBrandName].count;
				var maxQty = dailyQtyLimitPerUser;
				if(medicineCount < dailyQtyLimitPerUser)
				{
					maxQty = medicineCount;
				}
				
				for(var i = 0; i <= maxQty; i++)
				{
					options = options + '<option value="'+i+'">'+i+'</option>';
				}
			}
		}
		else {
			options = '<option value="0">0</option>';
		}
		
	}
	else {
		options = '<option value="0">0</option>';
	}
	
	$(selectMedicineQtyId).html(options);
}

function updateTooltip(index, fieldValue) {
	if(fieldValue) {
	  var medicine = medicineJsonObj[fieldValue];
	  if(medicine) {
		var medicineDesc = medicine.description;
		var medicineGenName = medicine.genericName;
		$('#medicineTooltip' + index).attr('title', fieldValue + ", " + medicineGenName + ", " + medicineDesc );
	  }
	}
}
