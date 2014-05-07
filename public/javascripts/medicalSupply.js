var isOpen = false;
//if window is resized then reposition the overlay box
$(window).bind('resize',showOverlayBox);

$(document).ready(function(){
    $('.addMedicalSupply').click(function(){
    	openOverlay();
    });
    
    $('.editMedicalSupply').click(function(){
    	openOverlay();
    });
    
    $('#selectAll').click(function() {
    	$('.selectMedEntry').prop('checked', this.checked);
    });
    
    $(".selectMedEntry").click(function(){
    	if($(".selectMedEntry").length == $(".selectMedEntry:checked").length) {
    		$("#selectall").prop("checked", "checked");
    	} else {
    	$("#selectall").removeProp("checked");
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
	
	//resetOverlay();
	
	// now animate the background to fade out to opacity 0
	// and then hide it after the animation is complete.
	$('.bgCover').animate( {opacity:0}, null, null, function() { $(this).hide(); } );
}
