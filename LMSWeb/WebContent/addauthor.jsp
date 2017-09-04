<%@include file="include.html"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%
	AdminService service = new AdminService();
	List<Book> books = service.getAllBooks(1);
%>
<div>
	<center><form action="addAuthor">
		<h1>Enter The Author Name:</h1> <input type="text" name="authorName"><br />
		
   		<h1> Select The Book You Want To Associate</h1>
       <center><select multiple name = "bookvalues">
        <%  for(Book b : books){ %>
        	<% Integer bookId = b.getBookId(); %>
        	<% String bookTitle = b.getTitle(); %>
        	<% String titleId = bookId +","+ bookTitle; %>
            <option value="<%=b.getBookId() + "," + b.getTitle()%>"><%=b.getTitle()%></option>
            
        <%}%>
        </select></center> 

		
		<button class="btn btn-sm btn-primary" type="submit">Add Author</button>
	</form></center>
</div>