<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%
	AdminService service = new AdminService();
	Author author = service.getAuthorByPK(Integer.parseInt(request.getParameter("authorId")));
	List<Book> books = author.getBooks();
	List<Book> bookList = service.getAllBooks(1);
%>


			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Author</h4>
			</div>
			<form action="editAuthor" method="post">
				<div class="modal-body">
					<h3>Enter details to Edit Author:</h3>
					Enter Author Name: <input type="text" name="authorName"
						value="<%=author.getAuthorName()%>"><br /> <input
						type="hidden" name="authorId" value="<%=author.getAuthorId()%>">
						
					<h3> Select The Book You Want To remove</h3>
			       	<select dropdown multiple name = "authorvalues">
			        <%  for(Book b : books){ %>
			            <option value="<%=b.getBookId() + "," + b.getTitle()%>"><%=b.getTitle()%></option>
			        <% } %>
			        </select>
			        
			        <h3> Select The new Book You Want To Add</h3>
			       	<select dropdown multiple name = "newauthorvalues">
			        <%  for(Book b : bookList){ %>
			            <option value="<%=b.getBookId() + "," + b.getTitle()%>"><%=b.getTitle()%></option>
			        <% } %>
			        </select>
			        
				</div>
				<div class="modal-footer">
						<button class="btn btn-primary" type="submit">Edit Author</button>
				</div>
			</form>
