var isOpen = false;
//if window is resized then reposition the overlay box
$(window).bind('resize',showOverlayBox);

$(document).ready(function(){
    $('.addMedicalSupply').click(function(){
    	openOverlay();
    });
    
    $('.editMedicalSupply').click(function(){
    	openOverlay();
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
    
    $('#selectAll').click(function() {
    	$('.selectMedEntry').prop('checked', this.checked);
    });
    
    $(".selectMedEntry").click(function(){
    	if($(".selectMedEntry").length == $(".selectMedEntry:checked").length) {
    		$("#selectAll").prop("checked", true);
    	} else {
    		$("#selectAll").prop("checked", false);
    	}
    });
})

//TODO: overlay behaviors are the same, refactor to minimize code duplicate

function openOverlay()
{
	displayOverlay('.overlayBox');
}

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
	
	resetOverlayContent();

	// now animate the background to fade out to opacity 0
	// and then hide it after the animation is complete.
	$('.bgCover').animate( {opacity:0}, null, null, function() { $(this).hide(); } );
}

function resetOverlayContent() {
	var errorMsg = $('#errorMsg');
	errorMsg.html('');
	
	$('#available').prop('checked', true);
	$('#quantifiable').prop('checked', true);

	$("div.overlay-content input[type=text], input[type=hidden]").each(function(){
		$(this).val('');
		$(this).prop('disabled', false);
		if ($(this).hasClass('overlayError')) {
			$(this).removeClass('overlayError');
		}
	});
}
