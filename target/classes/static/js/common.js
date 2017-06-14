$(document).ready(function(){
	if($('#message').val()){
		var status=$('#status').val();
		if(status){
			new PNotify({
			    title: 'Success!',
			    text: $('#message').val(),
			    type: status
			});
		}else{
			new PNotify({
			    title: 'Alert!',
			    text: $('#message').val()
			   });
		}
	}
});