package com.gcit.lms.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;
//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({"/addAuthor", "/editAuthor", "/deleteAuthor", "/pageAuthors", "/searchAuthors", "/addPublisher", "/editPublisher", "/deletePublisher",
	"/editBook", "/deletebook", "/editBorrower", "/deleteBorrower", "/addBorrower", "/editOverride","/pagePublishers", "/pageBorrowers", "/searchPublishers",
	"/searchBorrower", "/addBook", "/pageBooks", "/searchBooks"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),request.getRequestURI().length());
		AdminService service = new AdminService();
		String forwardPath =  "";    // when any of this case executes I forward back to viewauthors.jsp but when I add publisher I wanna forward back to viewpublisher 
		String message = "";
		Integer pageNo;
		switch (reqUrl) {
		case "/addAuthor":
			message = addAuthor(request, service, message);
			forwardPath = "/viewauthors.jsp";//thtat should work// you it will //
			break;
		case "/deleteAuthor":
			deleteAuthor(request, service);
			break;
		case "/pageAuthors":
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				request.setAttribute("authors", service.getAllAuthors(pageNo));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/viewauthors.jsp";
			break;
		case "/addPublisher":
			message = addPublisher(request, service, message);
			forwardPath =  "/viewPublisher.jsp"; // got it// 
			break;
		case "/addBorrower":
			addBorrower(request, service, message);
			forwardPath =  "/viewBorrower.jsp";
			break;
		case "/deletePublisher":
			deletePublisher(request, service);
			forwardPath =  "/viewPublisher.jsp";
			break;
		
		case "/pagePublishers":
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				request.setAttribute("publishers", service.getAllPublishers(pageNo));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/viewPublisher.jsp";
			break;
			
		case "/pageBooks":
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				try {
					request.setAttribute("books", service.getAllBooks(pageNo));
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/viewbooks.jsp";
			break;	
			
		case "/pageBorrower":
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				request.setAttribute("borrowers", service.getAllBorrowers(pageNo));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/viewBorrower.jsp";
			break;
			
		case "/deletebook":
			deleteBook(request, service);
			forwardPath = "/viewbooks.jsp";
			break;
		case "/deleteBorrower":
			deleteBorrower(request, service);
			forwardPath = "/viewBorrower.jsp";
			break;
		default:
			break;
		}
		
		request.setAttribute("confMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
		
	}

	private void deleteAuthor(HttpServletRequest request, AdminService service) {
		Integer authorId = Integer.parseInt(request.getParameter("authorId"));
		Author author = new Author();
		author.setAuthorId(authorId);
		try {
			service.deleteAuthor(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteBorrower(HttpServletRequest request, AdminService service) {
		Integer cardNo = Integer.parseInt(request.getParameter("cardno"));
		Borrower borrower = new Borrower();
		borrower.setCardNo(cardNo);
		try {
			service.deleteBorrower(borrower);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteBook(HttpServletRequest request, AdminService service) {
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Book book = new Book();
		book.setBookId(bookId);
		try {
			service.deleteBook(book);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deletePublisher(HttpServletRequest request, AdminService service) {
		Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);
		try {
			service.deletePublisher(publisher);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String addAuthor(HttpServletRequest request, AdminService service, String message) {
		String authorName = request.getParameter("authorName");
		String[] values  = request.getParameterValues("bookvalues");
		List<Book> books = new ArrayList<>();
		for(String value : values){
			String[] bookvalues = value.split(",");
			Integer bookId = Integer.parseInt(bookvalues[0].trim());
			String title = bookvalues[1].trim();
			Book book = new Book();
			book.setBookId(bookId);
			book.setTitle(title);
			books.add(book);
		}
		
		Author author = new Author();
		author.setAuthorName(authorName);
		author.setBooks(books);
		if(!authorName.isEmpty() && authorName.length() <45){
			try {
				service.addAuthor(author);
				message = "Author added Sucessfully"; 
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Author add failed";
			}
		}else{
			//add code to reply with error message.
		}
		Integer authorId = author.getAuthorId();
		if(authorId!=null){
			author.setAuthorId(authorId);
		}
		if(!authorName.isEmpty() && authorName.length() <45){
			try {
				service.addAuthor(author);
				message = "Author edited Sucessfully";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Author edit failed";
			}
		}else{
			//add code to reply with error message.
		}
		return message;
	}
	
	private void addBook(HttpServletRequest request,
			HttpServletResponse response, AdminService service, String Message) throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		
		Book book = new Book();
		String[] authors, genres,publisher;
		String title, publisherData = null;
		
		book.setTitle(request.getParameter("title"));
		try{
			title = request.getParameter("title");
		}catch (NullPointerException e){
			return ;
		}
		
		try {
			authors = request.getParameterValues("authorvalues");
			genres = request.getParameterValues("genrevalues");
			publisherData = request.getParameter("publishervalues");
			publisher = publisherData.split(",");
		}catch(NullPointerException e){
			return;
		}
	
		service = new AdminService();
		List<Author> authorList = new ArrayList<>();
		List<Genre> genreList = new ArrayList<>();
		Publisher publisherSelected;
		
		try {
			for(String author: authors) {
				String[] temp = author.split(",");
				Author a = service.getAuthorByPK(Integer.parseInt(temp[0]));
				authorList.add(a);
			}
			for(String genre: genres) {
				String[] temp = genre.split(",");
				Genre g = service.getGenreById(Integer.parseInt(temp[0]));
				genreList.add(g);
			}
			book.setAuthors(authorList);
			book.setGenres(genreList);
			book.setTitle(title);
			if(!publisherData.isEmpty()) {
				publisherSelected = service.getPublisherById(Integer.parseInt(publisher[0]));
				book.setPublisher(publisherSelected);
			}else
				book.setPublisher(null);
			
			try {
				service.saveBook(book);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			//System.out.println("Adding book failed.");
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/viewbooks.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private String addBorrower(HttpServletRequest request, AdminService service, String message) {
		String borrowerName = request.getParameter("borrowerName");
		String borrowerAddress = request.getParameter("borrowerAddress");
		String borrowerPhone = request.getParameter("borrowerPhone");
		Borrower borrower = new Borrower();
		borrower.setName(borrowerName);
		borrower.setAddress(borrowerAddress);
		borrower.setPhone(borrowerPhone);
		
		if(!borrowerName.isEmpty() && borrowerName.length() <45){
			try {
				service.addBorrower(borrower);
				message = "Borrower added Sucessfully";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Borrower add failed";
			}
		}else{
			//add code to reply with error message.
		}
		return message;
	}

	private String addPublisher(HttpServletRequest request, AdminService service, String message) {
		String publisherName = request.getParameter("publisherName");
		String publisherAddress = request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");
		Publisher publisher = new Publisher();
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);
		
		if(!publisherName.isEmpty() && publisherName.length() <45){
			try {
				service.addPublisher(publisher);
				message = "Publisher added Sucessfully";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Publisher add failed";
			}
		}else{
			//add code to reply with error message.
		}
		return message;
	}
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),request.getRequestURI().length());
		AdminService service = new AdminService();
		String forwardPath = "";
		String message = "";
		String searchString = "";
		Boolean inFncAjax = Boolean.FALSE;
		switch (reqUrl) {
		case "/editAuthor":
			try {
				editAuthor(request, response, service, message);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			forwardPath = "/viewauthors.jsp";
			break;
		case "/addBook":
			try {
				addBook(request, response, service, message);
			} catch (InstantiationException | IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			forwardPath = "/viewbooks.jsp";//thtat should work// you it will //
			break;
		case "/searchAuthors":
			try {
				searchAuthors(request, response, service, message);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/viewauthors.jsp";
			inFncAjax = Boolean.TRUE;
			break;
		case "/searchBooks":
			searchString = request.getParameter("searchString");
			
			try {
				request.setAttribute("books", service.getBooksByName(searchString));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/viewbooks.jsp";
			break;
//			<------------------------ Publisher post action-------------------------------------->
		case "/editPublisher":
			editPublisher(request, response, service, message);
			forwardPath = "/viewPublisher.jsp";
			break;
		case "/searchPublishers":
			searchString = request.getParameter("searchString");
			try {
				request.setAttribute("publishers", service.getPublishersByName(searchString));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/viewPublisher.jsp";
			break;
//          <------------------------ Book post action-------------------------------------->	
		case "/editBook":
			try {
				editBook(request, response, service, message);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/viewbooks.jsp"; 
			break;
//          <------------------------ Borrower post action-------------------------------------->	
		case "/editBorrower":
			editBorrower(request, response, service, message);
			forwardPath = "/viewBorrower.jsp"; 
			break;
		case "/searchBorrower":
			searchString = request.getParameter("searchString");
			try {
				request.setAttribute("borrowers", service.getBorrowersByName(searchString));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/viewBorrower.jsp";
			break;
		case "/editOverride":
			try {
				editLoans(request, response, service, message);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			forwardPath = "/override.jsp";
		default:
			break;
		}
		if(!inFncAjax){
			RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
			rd.forward(request, response);
		}
		
	}
	
// <------------------------------- Author Post Methods ------------------------------->	
	
	private void searchAuthors(HttpServletRequest request, HttpServletResponse response, AdminService service,
			String message) throws SQLException{
		String searchString = request.getParameter("searchString");;
		Integer pageNo = 1;
		if(request.getParameter("pageNo") != null){
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		List<Author> authors = new ArrayList<>();
		Integer totalPages = service.getAuthorsCount(searchString);
		if(totalPages % 10 > 0){
			totalPages = totalPages / 10 + 1;
		}else{
			totalPages = totalPages / 10;
		}
		try{
			if(searchString != null && !searchString.isEmpty()){
				authors = service.getAuthorsByName(searchString, pageNo);
			}else{
				authors = service.getAllAuthors(pageNo);
				searchString = "";
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<center>");
			sb.append("<h3>Search book by Name or Address:</h3>");
			sb.append("<div class='input-group'><input type='text' class='form-control' placeholder='Author Name' aria-describedby='basic-addon1' name='searchString'"
					+ " id='searchString' value='"+searchString+"' oninput='searchAuthors(1)'>");
			sb.append("</div>");
			sb.append("</center>");

			sb.append("<nav aria-label='Page navigation'><ul class='pagination'><li><a href='#' aria-label='Previous'> <spanaria-hidden='true'>&laquo;</span></a></li>");
			for (int i = 1; i <= totalPages; i++) {
				sb.append("<li><a onclick='searchAuthors("+i+")'>"+i+"</a></li>");
			}
			sb.append("<li><a href='#' aria-label='Next'> <span aria-hidden='true'>&raquo;</span></a></li></ul>");
			
			
			sb.append("<table class='table table-striped' id='authorsTable'>");
			sb.append("<tr><th>#</th><th>Author Name</th><th>Book Name</th><th>Edit</th><th>Delete</th></tr>");
			for (Author a : authors) {
				sb.append("<tr><td>"+(authors.indexOf(a)+1)+"</td>");
				sb.append("<td>"+a.getAuthorName()+"</td>");
				sb.append("<td>");
				sb.append("<select>");
				for(Book b : a.getBooks()){
					sb.append("<option class = 'dropdown'>" + b.getTitle() + "</option>");
				}
				sb.append("</select>");
				sb.append("</td>");
				sb.append("<td><button href='editauthor.jsp?authorId="+a.getAuthorId()+"' class='btn btn-sm btn-primary' data-toggle='modal' data-target='#editAuthorModal'>Edit</button></td>");
				sb.append("<td><button onclick='javascript:location.href='deleteAuthor?authorId="+a.getAuthorId()+"' class='btn btn-sm btn-danger'>Delete</button></td></tr>");
			}
			sb.append("</table>");
			response.getWriter().write(sb.toString());
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void editAuthor(HttpServletRequest request, HttpServletResponse response, AdminService service,
		String message) throws ServletException, IOException, SQLException {
		String authorName = request.getParameter("authorName");
		Integer authorId = Integer.parseInt(request.getParameter("authorId"));
		String[] removeValues = request.getParameterValues("authorvalues");
		String[] addValues = request.getParameterValues("newauthorvalues");
		
		Author author = new Author();
		List<Book> books = author.getBooks();
		
		if(  null != removeValues){
			for(String remove : removeValues){
				String[] rvalues = remove.split(",");
				Book b = new Book();
				Integer bookId = Integer.parseInt(rvalues[0]);
				String title = rvalues[1];
				b.setBookId(bookId);
				b.setTitle(title);
//				for(Book bk : books){
//					if(bk.equals(b)){
//						books.remove(bk);
//					}  
//				}
				service.deleteBookAuthor(bookId);
			}
		}
		
		if(null != addValues){
			for(String add : addValues){
				String[] avalues = add.split(",");
				Book b = new Book();
				Integer bookId = Integer.parseInt(avalues[0]);
				System.out.println(bookId);
				String title = avalues[1];
				System.out.println(title);
				b.setBookId(bookId);
				b.setTitle(title);
				if(null == books){
					books = new ArrayList<>();
					books.add(b);
				}else{
					books.add(b);
				}
			}
		}
		if(!books.isEmpty()){
			author.setBooks(books);
		}
		author.setAuthorName(authorName);
		if(authorId!=null){
			author.setAuthorId(authorId);
		}
		if(!authorName.isEmpty() && authorName.length() <45){
			try {
				service.addAuthor(author);
				message = "Author edited Sucessfully";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Author edit failed";
			}
		}else{
			//add code to reply with error message.
		}
		request.setAttribute("confMessage", message);
	}
	
	

	private void editLoans(HttpServletRequest request, HttpServletResponse response, AdminService service,
			String message) throws ServletException, IOException, ParseException {
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
			
			DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.from(f.parse(request.getParameter("dueDate")));
			Date dueDate = java.sql.Date.valueOf(date);
			BookLoans loans = new BookLoans();
			loans.setBookId(bookId);
			loans.setBranchId(branchId);
			loans.setCardNo(cardNo);
			loans.setDueDate(dueDate);
			if(dueDate!=null){
				loans.setDueDate(dueDate);
			}
			if(dueDate != null){
				try {
					service.overrideDate(loans);
					message = "Date overriden Sucessfully";
				} catch (SQLException e) {
					e.printStackTrace();
					message = "Date overriding failed";
				}
			}else{
				//add code to reply with error message.
			}
			request.setAttribute("confMessage", message);
		}
	
	private void editBorrower(HttpServletRequest request, HttpServletResponse response, AdminService service,
			String message) throws ServletException, IOException {
			String borrowerName = request.getParameter("borrowerName");
			String borrowerAddress = request.getParameter("borrowerAddress");
			String borrowerPhone = request.getParameter("borrowerPhone");
			Integer cardNo = Integer.parseInt(request.getParameter("cardno"));
			Borrower borrower = new Borrower();
			borrower.setName(borrowerName);
			borrower.setAddress(borrowerAddress);
			borrower.setPhone(borrowerPhone);
			if(cardNo!=null){
				borrower.setCardNo(cardNo);
			}
			if(!borrowerName.isEmpty() && borrowerName.length() <45){
				try {
					service.addBorrower(borrower);
					message = "Borrower edited Sucessfully";
				} catch (SQLException e) {
					e.printStackTrace();
					message = "Borrower edit failed";
				}
			}else{
				//add code to reply with error message.
			}
			request.setAttribute("confMessage", message);
		}
	
	private void editBook(HttpServletRequest request, HttpServletResponse response, AdminService service,
			String message) throws ServletException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
			String bookName = request.getParameter("bookName");
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			Book book = new Book();
			book.setTitle(bookName);
			if(bookId!=null){
				book.setBookId(bookId);
			}
			if(!bookName.isEmpty() && bookName.length() <45){
				try {
					service.addBook(book);
					message = "Book edited Sucessfully";
				} catch (SQLException e) {
					e.printStackTrace();
					message = "Book edit failed";
				}
			}else{
				//add code to reply with error message.
			}
			request.setAttribute("confMessage", message);
	}
	
	private void editPublisher(HttpServletRequest request, HttpServletResponse response, AdminService service,
			String message) throws ServletException, IOException {
			String publisherName = request.getParameter("publisherName");
			String publisherAddress = request.getParameter("publisherAddress");
			String publisherPhone = request.getParameter("publisherPhone");
			Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
			Publisher publisher = new Publisher();
			publisher.setPublisherName(publisherName);
			publisher.setPublisherAddress(publisherAddress);
			publisher.setPublisherPhone(publisherPhone);
			if(publisherId!=null){
				publisher.setPublisherId(publisherId);
			}
			if(!publisherName.isEmpty() && publisherName.length() <45){
				try {
					service.addPublisher(publisher);
					message = "Publisher edited Sucessfully";
				} catch (SQLException e) {
					e.printStackTrace();
					message = "Publisher edit failed";
				}
			}else{
				//add code to reply with error message.
			}
			request.setAttribute("confMessage", message);
		}
	
	// <-----------------------Publisher Code--------------------------------------> //
	

	
	

}
