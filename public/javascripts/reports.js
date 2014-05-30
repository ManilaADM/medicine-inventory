$(document).ready(function(){
	
	//Search
	$('.searchDate .textbox').on({
	    focus:function(){                   
	    	if(this.value == 'MM/DD/YYYY') this.value = '';
	    },
	    blur:function(){
	    	if(this.value == '') this.value = 'MM/DD/YYYY';
	    }
	})
});