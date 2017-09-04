<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%
	BorrowerService service = new BorrowerService();
	List<BookLoans> loans = new ArrayList<>();
    Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
	if(request.getAttribute("branches")!=null){
		loans = (List<BookLoans>)request.getAttribute("loans");
	}else{
		loans = service.getAllBookWithLoans(1, cardNo);
	}
	int totalPages = 1; //Libservice.getPublishersCount();
	if(totalPages%10 > 0){
		totalPages = totalPages/10 +1;
	}else{
		totalPages = totalPages/10;
	}
%>


<h3>List of loans for the user:</h3>
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
    	<li><a href="pagePublishers?pageNo=<%=i%>"><%=i%></a></li>
    <%} %>
    <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>

<form action="searchPublishers" method="post">
	<div class="input-group">
	  <input type="text" class="form-control" placeholder="Book Title" aria-describedby="basic-addon1" name="searchString">
	  <button type="submit">Search!</button>
	</div>
</form>

<table class="table table-striped">
	<tr>
		<th>#</th>
		<th>Book Title</th>
		<th>Card No</th>
		<th>Date Out</th>
		<th>Due Date</th>
		<th>Date In</th>
		<th>Action</th>
	</tr>
	<%
		for (BookLoans bl : loans) {
	%>
	<tr>
		<td><%=loans.indexOf(bl) + 1%></td>
		<td><%=bl.getBook().getTitle()%></td>
		<td><%=bl.getCardNo()%></td>
		<td><%=bl.getDateOut()%></td>
		<td><%=bl.getDueDate()%></td>
		<td><%=bl.getDateIn()%></td>
		<td><button type = "button" onclick="javascript:location.href='checkin?values=<%=bl.getBookId() +","+cardNo + "," +bl.getBranchId()%>'"
				class="btn btn-sm btn-primary">Check-In</button></td>
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