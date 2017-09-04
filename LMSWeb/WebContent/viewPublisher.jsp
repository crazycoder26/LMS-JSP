<%@include file="include.html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
	AdminService adminService = new AdminService();
	List<Publisher> publishers = new ArrayList<>(); 
	if(request.getAttribute("publishers")!=null){
		publishers = (List<Publisher>)request.getAttribute("publishers");
	}else{
		publishers = adminService.getAllPublishers(1);
	}
	int totalPages = request.getParameter("searchString") == null ? adminService.getPublishersCount() : publishers.size();
	if(totalPages%10 > 0){
		totalPages = totalPages/10 +1;
	}else{
		totalPages = totalPages/10;
	}
%>


<h3>List of Publishers in LMS:</h3>
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
		<th>Publisher Name</th>
		<th>Publisher Address</th>
		<th>Publisher Phone</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<%
		for (Publisher p : publishers) {
	%>
	<tr>
		<td><%=publishers.indexOf(p) + 1%></td>
		<td><%=p.getPublisherName()%></td>
		<td><%=p.getPublisherAddress()%></td>	
		<td><%=p.getPublisherPhone()%></td>	
		<td><button href="editpublisher.jsp?publisherId=<%=p.getPublisherId()%>"
				class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editAuthorModal">Edit</button></td>
		<td><button
				onclick="javascript:location.href='deletePublisher?publisherId=<%=p.getPublisherId()%>'"
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