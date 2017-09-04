<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	LibrarianService LibService = new LibrarianService();
	List<LibraryBranch> branches = new ArrayList<>(); 
	if(request.getAttribute("branches")!=null){
		branches = (List<LibraryBranch>)request.getAttribute("branches");
	}else{
		branches = LibService.getAllBranches(1);
	}
	int totalPages = 1; //Libservice.getPublishersCount();
	if(totalPages%10 > 0){
		totalPages = totalPages/10 +1;
	}else{
		totalPages = totalPages/10;
	}
%>


<h3>List of Branches:</h3>
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
	  <input type="text" class="form-control" placeholder="Branch Name" aria-describedby="basic-addon1" name="searchString">
	  <button type="submit">Search!</button>
	</div>
</form>

<table class="table table-striped">
	<tr>
		<th>#</th>
		<th>Branch Name</th>
		<th>Branch Address</th>
		<th>Action</th>
		<th>Action</th>
	</tr>
	<%
		for (LibraryBranch lb : branches) {
	%>
	<tr>
		<td><%=branches.indexOf(lb) + 1%></td>
		<td><%=lb.getBranchName()%></td>
		<td><%=lb.getBranchAddress()%></td>	
		<td><button type = "button" onclick="javascript:location.href='branchbooks.jsp?branchId=<%=lb.getBranchId()%>'"
				class="btn btn-sm btn-primary">Choose-Branch</button></td>
		 <td><button href="editbranch.jsp?branchId=<%=lb.getBranchId()%>"
				class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editAuthorModal">Update</button></td>
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