<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	AdminService adminService = new AdminService();
	List<Borrower> borrowers = new ArrayList<>();
	if(request.getAttribute("borrowers")!=null){
		borrowers = (List<Borrower>)request.getAttribute("borrowers");
	}else{
		borrowers = adminService.getAllBorrowers(1);
	}
	int totalPages = adminService.getBorrowersCount();
	if(totalPages%10 > 0){
		totalPages = totalPages/10 +1;
	}else{
		totalPages = totalPages/10;
	}
%>


<center><h1>List of Authors and Books they have written</h1></center>
<%
	if (request.getAttribute("confMessage") != null) {
		out.println(request.getAttribute("confMessage"));
	}
%>

<center><form action="searchBorrower" method="post">
	<div class="input-group">
	  <h3>Search Borrower by Name, Id or Address:</h3><input type="text" class="form-control" placeholder="Borrower Name, Address" aria-describedby="basic-addon1" name="searchString">
	  <center><button class="btn btn-sm btn-primary" type="submit">Search!</button></center>
	</div>
</form></center>

<nav aria-label="Page navigation">
  <ul class="pagination">
    <li>
      <a href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <%for(int i=1; i<=totalPages; i++){ %>
    	<li><a href="pageBorrowers?pageNo=<%=i%>"><%=i%></a></li>
    <%} %>
    <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>


<table class="table table-striped">
	<tr>
		<th>#</th>
		<!-- <th>Card Number</th> -->
		<th>Borrower Name</th>
		<th>Address</th>
		<th>Phone No.</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<%
		for (Borrower bw : borrowers) {
	%>
	<tr>
		<td><%=borrowers.indexOf(bw) + 1%></td>
		<%-- <td><%=bw.getCardNo()%></td> --%>
		<td><%=bw.getName()%></td>
		<td><%=bw.getAddress()%></td>
		<td><%=bw.getPhone()%></td>
		<td><button href="editborrower.jsp?cardno=<%=bw.getCardNo()%>"
				class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editAuthorModal">Edit</button></td>
		<td><button
				onclick="javascript:location.href='deleteBorrower?cardno=<%=bw.getCardNo()%>'"
				class="btn btn-sm btn-danger">Delete</button></td>
	</tr>
	<%
		}
	%>
</table>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" id="editAuthorModal">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
		</div>
	</div>
</div>