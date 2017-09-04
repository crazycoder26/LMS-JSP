<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	AdminService adminService = new AdminService();
	List<Book> books = new ArrayList<>();
	if(request.getAttribute("books")!=null){
		books = (List<Book>)request.getAttribute("books");
	}else{
		books = adminService.getAllBooks(1);
	}
	System.out.print(books.size());
	int totalPages = request.getParameter("searchString") != null ? books.size() : adminService.getBooksCount();
	//int totalPages = books.size();
	if(totalPages%10 > 0){
		totalPages = totalPages/10 +1;
	}else{
		totalPages = totalPages/10;
	}
%>


<center><h1>List of Books in:</h1></center>
<%
	if (request.getAttribute("confMessage") != null) {
		out.println(request.getAttribute("confMessage"));
	}
%>

<center><form action="searchBooks" method="post">
	<div class="input-group">
	 <h1>Search by Book Name, author</h1><input type="text" class="form-control" placeholder="Book Name, Address" aria-describedby="basic-addon1" name="searchString">
	  <button type="submit">Search!</button>
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
    	<li><a href="pageBooks?pageNo=<%=i%>"><%=i%></a></li>
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
		<th>Book Name</th>
		<th>Authors Name</th> 
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<%
		for (Book b : books) {
	%>
	<tr>
		<td><%=books.indexOf(b) + 1%></td>
		<td><%=b.getTitle()%></td>
		<td>
			<select>
	        <%  for(Author a : b.getAuthors()){ %>
	            <option><%=a.getAuthorName()%></option> 
	        <%}%>
	        </select>
		</td>
		<td><button href="editbook.jsp?bookId=<%=b.getBookId()%>"   
				class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editAuthorModal">Edit</button></td>
		<td><button
				onclick="javascript:location.href='deletebook?bookId=<%=b.getBookId()%>'"
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