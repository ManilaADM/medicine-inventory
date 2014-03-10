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
	
	var medOptions;
	
	var medBrandAndGenericName = [];
	$.each(medicineJsonObj, function(key,value){
		var genericName = value.genericName;
		medBrandAndGenericName.push({ "value" : key, "label": '' + key + ' ('+genericName+')' });
		
	});
		
	for(i = 0; i<numberOfMedicineFields;i++)
	{
		updateMedicineFields(medBrandAndGenericName, i);
	}
	
	$('#transactionForm').submit(function(event) {
		cleanTransactionValidation();
		event.preventDefault();
		if (isValid()) {
			proceedSavingTransaction('#transactionForm');
		}
	});
});

function updateMedicineFields(medBrandAndGenericName, index)
{
	$('#medicineInput'+ index).autocomplete({
	    source: medBrandAndGenericName,
	    minLength: 0,
	    select: function (event, ui) {
	        var value = ui.item.value;
	    	updateMedicineQty(value, '#medicineQty' + index);
	    	var medicineDesc = medicineJsonObj[value].description;
	    	var medicineGenName = medicineJsonObj[value].genericName;
	    	var medicineId = medicineJsonObj[value].idAsString;
	    	$('#medicineTooltip' + index).attr('title', value + ", " + medicineGenName + ", " + medicineDesc );
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

function showOverlayBox() {
	//if box is not set to open then don't do anything
	if( isOpen == false ) return;
	// set the properties of the overlay box, the left and top positions
	$('.overlayBox').css({
		display:'block',
		left:( $(window).width() - $('.overlayBox').width() )/2,
		top:( $(window).height() - $('.overlayBox').height() )/2 -20,
		position:'absolute'
	});
	// set the window background for the overlay. i.e the body becomes darker
	$('.bgCover').css({
		display:'block',
		width: $(window).width(),
		height:$(window).height(),
	});
	cleanTransactionValidation();
}


function displayOverlay() {
	//set status to open
	isOpen = true;
	showOverlayBox();
	$('.bgCover').css({opacity:0}).animate( {opacity:0.5, backgroundColor:'#000'} );
	// dont follow the link : so return false.
	return false;
}

function closeOverlay() {
	//set status to closed
	isOpen = false;
	$('.overlayBox').css( 'display', 'none' );
	// now animate the background to fade out to opacity 0
	// and then hide it after the animation is complete.
	$('.bgCover').animate( {opacity:0}, null, null, function() { $(this).hide(); } );
}

function updateMedicineQty(medicineBrandName, selectMedicineQtyId) {
	var options;
	if(medicineBrandName) {
		var medicine = medicineJsonObj[medicineBrandName];
		if(medicine.quantifiable !== true) {
			options = '<option value="1">1</option>';
		}
		else {
			var maxQty = medicineJsonObj[medicineBrandName].dailyQtyLimitPerUser;
			for(i = 0; i< maxQty; i++)
			{
				options = options + '<option value="'+i+'">'+i+'</option>';
			}
		}
	}
	else {
		options = '<option value="0">0</option>';
	}
	
	$(selectMedicineQtyId).html(options);
}

//clear previous error message and styles on invalid elements
function cleanTransactionValidation() {
	$('#errorMsg').empty();
	$('input').removeClass('overlayError');
}

function isValid() {
	var errorMsgList = [];
	var isValid = false;
	
	validateEmployeeName($('#employeeNameId'), errorMsgList);
	validateMedicineOption($('.medicineTransactions').children(), errorMsgList);
	
	if (errorMsgList.length > 0) {
		$('#errorMsg').addClass('error');
		$.each(errorMsgList, function(index, value) {
			$('#errorMsg').append(value);
		});
	}
	else {
		isValid = true;
	}
	
	return isValid;
}

function validateEmployeeName(empField, errorMsgList) {
	if ($(empField).val() == "") {
		$(empField).addClass('overlayError');
		errorMsgList.push("Please enter Employee Name<br/>");
	}
	return errorMsgList;
}

function validateMedicineOption(options, errorMsgList) {
	var noMedicineInp = false;
	var medicineList = [];
	var duplicateMedIndex = [];
	
	$.each(options, function(medOptIndex, medOptValue) {
		var med = $(medOptValue).find('#medicineInput'+medOptIndex);
		var medicineName = $(med).val();
		var medicineQty = $(medOptValue).find('#medicineQty'+medOptIndex).val()
		medicineList.push(medicineName);
		
		// validate if no medicine name was selected but with medicine quantity
		if (medicineQty > 0 && medicineName == "") {
			$(med).addClass('overlayError');
			noMedicineInp = true;
		}
		// validate if duplicate medicine name were selected
		// get array containing indexes of duplicate medicine
		// merge found indexes to array of duplicateMedIndex
		var duplicateMed = getDuplicateMedIndexes(medicineList, medicineName);
		if (duplicateMed.length > 0) {
			$.merge(duplicateMedIndex, duplicateMed);
		}
	});
	
	if (noMedicineInp == true) {
		errorMsgList.push("Please select medical supply<br/>");
	}
	if (duplicateMedIndex.length > 0) {
		styleDuplicateMedicine(options, duplicateMedIndex.sort());
		errorMsgList.push("Dupe medical supplies");
	}

	return errorMsgList;
}

function getDuplicateMedIndexes(medicineList, medicineName) {
	var indexes = [];
	var numOccurences;
	// find medicine name's number of occurrence
	numOccurences = $.grep(medicineList, function (value) {
		// disregard blank/no option selected
		if (medicineName != "") {
			return value === medicineName;
		}
	}).length;
	// if more than 1 occurrence, search for the element indexes of all occurrences
	if (numOccurences > 1) {
		$.each (medicineList, function(listIndex, listValue) {
			var index = $.inArray(medicineName, medicineList, listIndex);
			// if found, check if index is present in indexes already
			// else store it in array of indexes
			if (index > -1 && ($.inArray(index, indexes) == -1)) {
				indexes.push(index);
			}
		});
	}
	return indexes;
}

function styleDuplicateMedicine(options, duplicateMedIndex) {
	// loop through the array containing the duplicate medicine index
	// use the indexes for identifying input ids
	$.each(duplicateMedIndex, function(dupeIndex, dupeValue) {
		var med = options.find('#medicineInput'+dupeValue);
		if (!$(med).hasClass('overlayError')) {
			$(med).addClass('overlayError');
		}
	});
}

function proceedSavingTransaction(form) {
	$.ajax({
		url : $(form).attr("action"),
		type: "POST",
		data : $(form).serialize(),
		success: function()
		{			
			closeOverlay();
		},
		error: function(xhr, status, error)
		{
			alert("fail" + error);
		}
	});
	// add logic to prevent more than one submit
}