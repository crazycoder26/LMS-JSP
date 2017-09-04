<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<%@include file="include.html"%>

<script>
	function searchAuthors(pageNo) {
		$.ajax({
			method : "POST",
			url : "searchAuthors",
			data : {
				searchString : $('#searchString').val(),
				pageNo: pageNo
			}
		}).done(function(data) {
			$('#authorsListDiv').html(data);
			var strSearch = $("#searchString").val(); 
			$("#searchString").focus().val('').val(strSearch);
		});
	}
</script>


<center><h1>List of Authors and Books they have written</h1></center>
<%
	if (request.getAttribute("confMessage") != null) {
		out.println(request.getAttribute("confMessage"));
	}
%>

<div id="authorsListDiv">

</div>
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" id="editAuthorModal">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content"></div>
	</div>
</div>

<script>
$( document ).ready(function() {
	searchAuthors(1);
});
</script>