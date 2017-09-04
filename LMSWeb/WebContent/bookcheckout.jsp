<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%
	LibrarianService LibService = new LibrarianService();
	String values = request.getParameter("values");
	String[] ids = values.split(",");
	Integer cardNo = Integer.parseInt(ids[0].trim());
	Integer branchId = Integer.parseInt(ids[1].trim());
	List<Book> books = new ArrayList<>(); 
	if(request.getAttribute("books")!=null){
		books = (List<Book>)request.getAttribute("books");
	}else{
		books = LibService.getAllBooksWithBranch(1, branchId);
	}
	int totalPages = 1; //Libservice.getPublishersCount();
	if(totalPages%10 > 0){
		totalPages = totalPages/10 +1;
	}else{
		totalPages = totalPages/10;
	}
%>


<h3>List of books at  LMS:</h3>
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
	  <input type="text" class="form-control" placeholder="Publishers Name" aria-describedby="basic-addon1" name="searchString">
	  <button type="submit">Search!</button>
	</div>
</form>

<table class="table table-striped">
	<tr>
		<th>#</th>
		<th>Book Title</th>
		<th>Check out</th>
	</tr>
	<%
		for (Book b : books) {
	%>
	<tr>
		<td><%=books.indexOf(b) + 1%></td>
		<td><%=b.getTitle()%></td>
		<%-- <td><button href="editcopies.jsp?bookId=<%=b.getBookId()%>&branchId <%=branchId%>"
				class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editAuthorModal">checkout</button></td> --%>
		 <td><button
				onclick="javascript:location.href='checkout?bvalues=<%=b.getBookId()+","+cardNo+","+branchId%>'"
				class="btn btn-sm btn-primary">check-out</button></td> 
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