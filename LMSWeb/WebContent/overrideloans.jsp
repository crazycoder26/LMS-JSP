<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="java.sql.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import= "java.time.format.DateTimeFormatter"%>
<%@page import="org.joda.time.format.DateTimeFormat"%>
<%
	AdminService service = new AdminService();
	/* Author author = service.getAuthorByPK(Integer.parseInt(request.getParameter("authorId"))); */

	DateTime dateOut = DateTime.parse(request.getParameter("dateOut"));
	
	BookLoans loans = service.getBooKLoansByPK(dateOut);
	System.out.print("date out" + loans.getDateOut());
	
	
	 
	
%>


			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Loans</h4>
			</div>
			<form action="editOverride" method="post">
				<div class="modal-body">
					<h3>Enter details to override:</h3>
					
					Enter Due Date: <input type="text" name="dueDate"
						value="<%=loans.getDueDate()%>"><br /> <input
						type="hidden" name="dueDate" value="<%=loans.getDueDate()%>">
					
					 <input
						type="hidden" name="bookId" value="<%=loans.getBookId()%>">
					
					 <input
						type="hidden" name="branchId" value="<%=loans.getBranchId()%>">
					
					 <input
						type="hidden" name="cardNo" value="<%=loans.getCardNo()%>">
					
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" type="submit">Override DueDate</button>
				</div>
			</form>