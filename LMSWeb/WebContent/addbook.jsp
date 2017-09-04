<%@include file="include.html"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%
	AdminService service = new AdminService();
	List<Publisher> publishers = service.getAllPublishers(1);
	List<Genre> genres = service.getAllGenres();
	List<Author> authors = service.getAllAuthors(1);
%>
<div align = "center">
	<form action="addBook" method = "post">
		<h1>Enter Book Title:</h1> <input type="text" name="title"><br />
		
		<h1> Select The Publisher You Want To Associate</h1>
       	<center><select name = "publishervalues">
        <%  for(Publisher p : publishers){ %>
            <option value="<%=p.getPublisherId() + "," + p.getPublisherName()%>"><%=p.getPublisherName()%></option>
        <% } %>
        </select></center> 
        
        <h1> Select The Genre You Want To Associate</h1>
       	<center><select multiple name = "genrevalues">
        <%  for(Genre g : genres){ %>
            <option value="<%=g.getGenreId() + "," + g.getGenreName()%>"><%=g.getGenreName()%></option>
        <% } %>
        </select></center> 
        
        <h1> Select The Author You Want To Associate</h1>
       	<center><select multiple name = "authorvalues">
        <%  for(Author a : authors){ %>
            <option value="<%=a.getAuthorId() + "," + a.getAuthorName()%>"><%=a.getAuthorName()%></option>
        <% } %>
        </select></center> 
        
        <button class="btn btn-sm btn-primary type="submit">Add Book</button>
	</form>
</div>