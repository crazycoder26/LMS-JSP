<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%
	AdminService service = new AdminService();
	Publisher publisher = service.getPublisherByPK(Integer.parseInt(request.getParameter("publisherId")));
%>


			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Edit Publisher</h4>
			</div>
			<form action="editPublisher" method="post">
				<div class="modal-body">
					<h3>Enter details to Edit Publisher:</h3>
					
					Enter Publisher Name: <input type="text" name="publisherName"
						value="<%=publisher.getPublisherName()%>"><br /> <input
						type="hidden" name="publisherId" value="<%=publisher.getPublisherId()%>">
					
					Enter Publisher Address: <input type="text" name="publisherAddress"
						value="<%=publisher.getPublisherAddress()%>"><br /> <input
						type="hidden" name="publisherAddress" value="<%=publisher.getPublisherId()%>">
					
					Enter Publisher Phone: <input type="text" name="publisherPhone"
						value="<%=publisher.getPublisherPhone()%>"><br /> <input
						type="hidden" name="publisherPhone" value="<%=publisher.getPublisherId()%>">
					
				</div>
				<div class="modal-footer">
					<button  class="btn btn-primary" type="submit">Edit publisher</button>
				</div>
			</form>