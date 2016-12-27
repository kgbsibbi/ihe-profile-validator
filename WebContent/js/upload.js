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
		  var filenameFilter = /\.(xml)$/i;
		  if($("#filename").val().length > 0 ){
			  if($("#filename").val().match(filenameFilter))
				  $("#validatebutton").attr('disabled', false);
			  else
				  alert("Select xml file");
		  }else {
			  $("#validatebutton").attr('disabled', true);
		  }
	});
});