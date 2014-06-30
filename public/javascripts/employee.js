var isOpen = false;
//if window is resized then reposition the overlay box
$(window).bind('resize',showOverlayBox);

$(document).ready(function(){
    $('.addEmployee').click(function(){
    	openOverlay();
    	$('#editEmployee').css('display', 'none');
    	$('#addEmployee').css('display', '');
    });
    
    $('.update').click(function(){
    	openOverlay();
    	$('#addEmployee').css('display', 'none');
    	$('#editEmployee').css('display', '');
    	var data = $(this).closest('.dataContainer tr');
		$('#oid').val(data.find('.id').text());
		$('#employeeCode').val(data.find('.employeeCode').text());
		$('#firstName').val(data.find('.firstName').text());
		$('#lastName').val(data.find('.lastName').text());
		$('#gender').val(data.find('.gender').text());
		$('#team').val(data.find('.team').text());
		$('#category').val(data.find('.category').text());
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
		position:'fixed'
	});
	// set the window background for the overlay. i.e the body becomes darker
	$('.bgCover').css({
		display:'block',
		position:'fixed'
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
