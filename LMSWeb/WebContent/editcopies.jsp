<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="com.gcit.lms.entity.BookCopies"%>
<%
	LibrarianService lbService = new LibrarianService();
	String values = request.getParameter("values");
	String[] ids = values.split(",");
	
	BookCopies copies = lbService.getBookCopiesById(Integer.parseInt(ids[0]),Integer.parseInt(ids[1]));
%>


			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Borrower</h4>
			</div>
			<form action="editCopies" method="post">
				<div class="modal-body">
					<h3>Enter details to Edit Copies:</h3>
					Enter The Number of Copies: <input type="text" name="copies"
						value="<%=copies.getNoOfCopies()%>"><br /> <input
						type="hidden" name="bookId" value="<%=copies.getBookId()%>" >
						<input type="hidden" name="branchId" value="<%=copies.getBranchId()%>">
					
					<button type="submit">Edit Copies</button>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary">Save changes</button>
				</div>
			</form>
