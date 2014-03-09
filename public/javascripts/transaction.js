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
		medBrandAndGenericName.push({ "value" : key, "label": '<' + key + '> (<'+genericName+'>)' });
		
	});
		
	for(i = 0; i<numberOfMedicineFields;i++)
	{
		updateMedicineFields(medBrandAndGenericName, i);
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
	   }
	})
	.focus(function() {
	    $(this).autocomplete('search', $(this).val())
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
	if(medicineBrandName)
	{
		var maxQty = medicineJsonObj[medicineBrandName].dailyQtyLimitPerUser;
		for(i = 0; i< maxQty; i++)
		{
			options = options + '<option value="'+i+'">'+i+'</option>';
		}
	}
	else {
		options = '<option value="0">0</option>';
	}
	
	$(selectMedicineQtyId).html(options);
}