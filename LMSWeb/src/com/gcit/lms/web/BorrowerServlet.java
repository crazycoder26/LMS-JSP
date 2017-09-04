package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class BorrowerServlet
 */
@WebServlet({"/BorrowerServlet", "/validateBorrower", "/checkout", "/checkin"})
public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),request.getRequestURI().length());
		BorrowerService service = new BorrowerService();
		String message = "";
		String forwardPath = "";
		switch (reqUrl) {
		case "/validateBorrower":
			try {
				message = validateBorrower(request, service, message);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if("Continue to checkout".equals(message)){
				forwardPath = "/checkOutOptions.jsp";
			}else{
				forwardPath = "/MainBorrower.jsp";
			}
			//thtat should work// you it will //
			break;
		case "/checkout":
			message = checkOutBook(request, service, message);
			forwardPath = "/MainBorrower.jsp";
			break;
		case "/checkin":
			message = checkIn(request, service, message);
			forwardPath = "/MainBorrower.jsp";
			break;
		default:
			break;
		}
		request.setAttribute("confMessage", message);
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}
	
	
	public String checkOutBook(HttpServletRequest request, BorrowerService service, String message){
		String bvalues = request.getParameter("bvalues");
		String[] ids = bvalues.split(",");
		Integer bookId = Integer.parseInt(ids[0]);
		Integer cardNo = Integer.parseInt(ids[1]);
		Integer branchId = Integer.parseInt(ids[2]);
		Borrower borrower = new Borrower(); borrower.setCardNo(cardNo);
		LibraryBranch branch = new LibraryBranch(); branch.setBranchId(branchId);
		Book book = new Book(); book.setBookId(bookId);
		try {
			service.checkOut(borrower, book, branch);
			message = "Book check out successful";
		} catch (SQLException e) {
			e.printStackTrace();
			message = "Check out failed";
		}
		return message;
	}
	
	public String checkIn(HttpServletRequest request, BorrowerService service, String message){
		String bvalues = request.getParameter("values");
		String[] ids = bvalues.split(",");
		Integer bookId = Integer.parseInt(ids[0]);
		Integer cardNo = Integer.parseInt(ids[1]);
		Integer branchId = Integer.parseInt(ids[2]);
		Borrower borrower = new Borrower(); borrower.setCardNo(cardNo);
		LibraryBranch branch = new LibraryBranch(); branch.setBranchId(branchId);
		Book book = new Book(); book.setBookId(bookId);
		try {
			service.checkIn(borrower, book, branch);
			message = "Book check In successful";
		} catch (SQLException e) {
			e.printStackTrace();
			message = "Check In failed";
		}
		return message;
	}
	
	public String validateBorrower(HttpServletRequest request, BorrowerService service, String message) throws SQLException{
		Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
		LibrarianService lservice = new LibrarianService();
		String name = lservice.getBorrowerName(cardNo);
		if(name == null || name.length() == 0){
			message = "Please register to checkout";
		}else{
			message = "Continue to checkout";
			request.setAttribute("cardNo", cardNo);
			RequestDispatcher rd = request.getRequestDispatcher("/checkOutOptions.jsp");
		}
		return message;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
