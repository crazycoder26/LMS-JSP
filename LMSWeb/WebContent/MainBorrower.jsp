<%@include file="include.html"%>

<div>
	<center><h1>Welcome to the borrowers menu</h1></center>
		<center><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0q4B6rQAJR1bDj3m9f6E075GCsvsUYp1etifxQE-rnzp_2Pk2TQ" alt="Mountain View" style="width:400px;height:200px;"></center>
	
	<form action="validateBorrower">
		Enter Card Number: <input type="text" name="cardNo" id = "testId"><br />
		<button type="submit">validate</button>
	</form>
	<%
	if (request.getAttribute("confMessage") != null) {
		out.println(request.getAttribute("confMessage"));
	}
%>	
</div>

