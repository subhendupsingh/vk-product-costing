$(document).ready(function(){
	
});

function deleteWarehouse(code){
	$.ajax({
		  url: "http://localhost:8080/warehouses/delete",
		  type: "post", //send it through get method
		  data: { 
		    code: code
		  },
		  success: function(response) {
			  window.location.href="http://localhost:8080/warehouses";
		  },
		  error: function(xhr) {
		    //Do Something to handle error
		  }
		});
}
