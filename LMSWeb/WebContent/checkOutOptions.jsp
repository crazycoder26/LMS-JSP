<%@include file="include.html"%>
<% Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));%>
<table>
		<tr align ="center">
			<td><a href="checkin.jsp?cardNo=<%=cardNo%>">Check In</a></td>
		</tr>
		<tr align ="center">
			<td><a href="checkout.jsp?cardNo=<%=cardNo%>">Check Out</a></td>
		</tr>
		
	</table>