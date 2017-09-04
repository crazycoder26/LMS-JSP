<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	AdminService adminService = new AdminService();
	String searchString = request.getParameter("searchString");
	List<BookLoans> loans = new ArrayList<>();
	if(request.getAttribute("loans")!=null){
		loans = (List<BookLoans>)request.getAttribute("loans");
	}else{
		loans = adminService.getAllBookLoans(1);
	}
	int totalPages = adminService.getAuthorsCount(searchString);
	if(totalPages%10 > 0){
		totalPages = totalPages/10 +1;
	}else{
		totalPages = totalPages/10;
	}
%>


<h3 algin = "center">List of borrowers with dues pending:</h3>
<%
	if (request.getAttribute("confMessage") != null) {
		out.println(request.getAttribute("confMessage"));
	}
%>

<nav aria-label="Page navigation">
  <ul class="pagination">
    <li>
      <a href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <%for(int i=1; i<=totalPages; i++){ %>
    	<li><a href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li>
    <%} %>
    <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>
<form action="searchAuthors" method="post">
	<div class="input-group">
	  <input type="text" class="form-control" placeholder="Book Id, Branch Id, card No" aria-describedby="basic-addon1" name="searchString">
	  <button type="submit">Search!</button>
	</div>
</form>

<table class="table table-striped">
	<tr>
		<th>#</th>
		<th>Book Title</th>
		<th>Card Number</th>
		<th>Date Out</th>
		<th>Due Date</th>
		<th>Date In</th>
		<th>Override</th>
		<th>Delete</th>
	</tr>
	<%
		for (BookLoans l : loans) {
	%>
	<tr>
		<td><%=loans.indexOf(l) + 1%></td>
		<td><%=l.getBook().getTitle()%></td>
		<td><%=l.getCardNo()%></td>
		<td><%=l.getDateOut()%></td>
		<td><%=l.getDueDate()%></td>
		<td><%=l.getDateIn()%></td>
		<td><button href="overrideloans.jsp?dateOut=<%=l.getDateOut()%>"
				class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editAuthorModal">Override</button></td>
		<td><button
				onclick="javascript:location.href='deleteAuthor?authorId=<%=l.getDueDate()%>'"
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