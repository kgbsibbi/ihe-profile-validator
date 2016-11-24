$(document).ready(function(){
	$("#validatebutton").attr('disabled', true);
	
	$("#browsebutton").on('click', function(){
		$("#fileinput").trigger('click');
	});
	$("#filename").on('click', function(){
		
		$("#fileinput").trigger('click');
	});
	$("#fileinput").on('change',function(){
		  $("#filename").val($("#fileinput").val().replace(/C:\\fakepath\\/i, ''));
		  if($("#filename").val().length > 0){
			  $("#validatebutton").attr('disabled', false);
		  }else {
			  $("#validatebutton").attr('disabled', true);
		  }
	});
});