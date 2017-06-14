/**
 * 
 */
var cartonNo='0';
var cartonId;
$(document).ready(function() {
	var messageList = $("#messages");

	var getMessageString = function(message) {
		if(message.content && message.content!='failure'){
			var asin=message.content.substr(0,message.content.indexOf(","));
			return "<li class='list-group-item'>"+asin + "</li>";
		}else{
			alert('Failed to save, scan again!');
		}
	};

	var socket = new SockJS('/guestbook');
	var stompClient = Stomp.over(socket);
	stompClient.connect({ }, function(frame) {
		// subscribe to the /topic/entries endpoint which feeds newly added messages
		stompClient.subscribe('/topic/entries', function(data) {
			// when a message is received add it to the end of the list
			var body = data.body;
			var message = JSON.parse(body);
			messageList.append(getMessageString(message));
		});
	});

	$("#asintext").keypress(function(e) {
		// send the message
		var key = e.which;
		if(key == 13){
			if($("#asintext").val()){
				stompClient.send("/app/guestbook", {}, $("#asintext").val()+","+cartonId);
				$("#asintext").val("");
				$("#asintext").focus();
				return false;
			}
		}
	});

	$("#open-carton").on("click",function(){
		var po=$("#po").val();
		var pk=$("#pk").val();
		$.ajax({
			url: "/cartons",
			type: "post", 
			data: { 
				po: po,
				pk: pk
			},
			success: function(response) {
				if(response && response!='failure'){
					var res=JSON.parse(response);
					cartonNo=res.number;
					cartonId=res.id;
					$('#cartonModal').modal({backdrop: 'static', keyboard: false});
				}else{
					alert('Error opening the carton!');
				}
			},
			error: function(xhr) {
				//Do Something to handle error
			}
		});
	});

	$('#saveandclosecarton').on('click',function(){
		var length=$('#length').val();
		var width=$('#width').val();
		var height=$('#height').val();
		var weight=$('#weight').val();
		var po=$("#po").val();
		var pk=$("#pk").val();
		if(!length || !width || !height || !weight){
			new PNotify({
				title: 'Failure',
				text: 'Length, Width, Height & Weight are mandatory to close the carton!',
				type: 'failure'
			});

			return false;
		}

		length=parseFloat(length);
		width=parseFloat(width);
		height=parseFloat(height);
		weight=parseFloat(weight);

		if(length<=0 || width<=0 || height<=0 || weight<=0){
			new PNotify({
				title: 'Failure',
				text: 'Length, Width, Height & Weight must be greater than zero!',
				type: 'failure'
			});

			return false;
		}


		$.ajax({
			url: "/cartons/close",
			type: "post", 
			data: { 
				length:length,
				width:width,
				height:height,
				weight:weight,
				id:cartonId
			},
			success: function(response) {
				if(response){
					if(response=='success'){
						window.location.href="/cartons?po="+po+"&pk="+pk;
						new PNotify({
							title: 'You did it!',
							text: 'Carton successfully closed!',
							type: 'success'
						});
					}else{
						new PNotify({
							title: 'Failure',
							text: 'Error closing the carton!',
							type: 'failure'
						});
					}
				}
			},
			error: function(xhr) {
				//Do Something to handle error
			}
		});


	});
});
$("#savecarton").on('click',function(){
	var po=$("#po").val();
	var pk=$("#pk").val();
	window.location.href="/cartons?po="+po+"&pk="+pk;
});

$("#closepicklist").on('click',function(){
	var pk=$("#pk").val();
	var po=$("#po").val();
	$.ajax({
		url: "/picklist/close",
		type: "post", 
		data: { 
			pk:pk
		},
		success: function(response) {
			if(response){
				if(response=='success'){
					new PNotify({
						title: 'You did it!',
						text: 'Picklist successfully closed!',
						type: 'success'
					});
					setTimeout(function(){
						window.location.href="/picklist?po="+po;	
					},500)
				}else{
					$('#message').val(response);
					$('#errorModal').modal('show');

				}
			}
		},
		error: function(xhr) {
		}
	});
});

$('#markNotFound').on('click',function(){
	var sku=$('#notfoundsku').val();
	var pk=$("#pk").val();
	var qty=$("#notfoundskuqty").val();
	$.ajax({
		url: "/picklist/notfound",
		type: "post", 
		data: { 
			pk:pk,
			sku:sku,
			quantity:qty
		},
		success: function(response) {
			if(response){
				if(response=='success'){
					
				}else{
					$('#message').val(response);
					$('#errorModal').modal('show');

				}
			}
		},
		error: function(xhr) {
		}
	});
});

$('#cartonModal').on('show.bs.modal', function (event) {
	var button = $(event.relatedTarget); // Button that triggered the modal
	var modal = $(this);
	$("#messages").empty();
	modal.find('.modal-title').text("Carton Number "+cartonNo+" (ID: "+cartonId+")");
	$('#asintext').focus();
})

$('#errorModal').on('show.bs.modal', function (event) {
	var modal = $(this);
	modal.find('.modal-title').text('Problems');
	modal.find('.modal-body').html($('#message').val());
})

function reopenCarton(id){
	$.ajax({
		url: "/cartons/reopen",
		type: "post", 
		data: { 
			id:id
		},
		success: function(response) {
			if(response && response!='failure'){
				var res=JSON.parse(response);
				cartonNo=res.number;
				cartonId=res.id;
				$('#length').val(res.length);
				$('#width').val(res.width);
				$('#height').val(res.height);
				$('#weight').val(res.weight);
				$('#cartonModal').modal('show');
			}else{
				alert('Error opening the carton!');
			}
		},
		error: function(xhr) {
			//Do Something to handle error
		}
	});
}