<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%
	/* i was here and went to adminservice  */
	AdminService service = new AdminService();
	Book book = service.getBookByPK(Integer.parseInt(request.getParameter("bookId")));
	List<Book> books = service.getAllBooks(1);
 %>

<!-- I came form viewbooks.jsp should go back to the same -->
<!-- Going to admin servlet -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Book</h4>
			</div>
			<form action="editBook" method="post">
				<div class="modal-body">
					<h3>Enter details to Edit Book:</h3>
					Enter Book Name: <input type="text" name="bookName"
						value="<%=book.getTitle()%>"><br /> <input
						type="hidden" name="bookId" value="<%=book.getBookId()%>">
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" type="submit">Edit Book</button>
				</div>
			</form>
			
