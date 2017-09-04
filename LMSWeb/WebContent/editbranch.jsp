<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%
	LibrarianService service = new LibrarianService(); 
	LibraryBranch branch = service.getBranchPK(Integer.parseInt(request.getParameter("branchId")));
%>


			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Branch</h4>
			</div>
			<form action="editBranch" method="post">
				<div class="modal-body">
					<h3>Enter details to Edit Borrower:</h3>
					Enter Branch Name: <input type="text" name="branchName"
						value="<%=branch.getBranchName()%>"><br /> <input
						type="hidden" name="branchId" value="<%=branch.getBranchId()%>">
					
					Enter Branch Address: <input type="text" name="branchAddress"
						value="<%=branch.getBranchAddress()%>"><br /> <input
						type="hidden" name="branchId" value="<%=branch.getBranchId()%>">
					
					<button type="submit">Edit Branch</button>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary">Save changes</button>
				</div>
			</form>
