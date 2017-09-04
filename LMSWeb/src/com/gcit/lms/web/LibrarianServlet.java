package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class LibrarianServlet
 */
@WebServlet({"/LibrarianServlet", "/editCopies", "/editBranch"})
public class LibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibrarianServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),request.getRequestURI().length());
		LibrarianService service = new LibrarianService();
		String forwardPath = "";
		String message = "";
		switch (reqUrl) {
		case "/editCopies":
			editcopies(request, response, service, message);
			forwardPath = "/librarian.jsp";
			break;
		case "/editBranch":
			editBranch(request, response, service, message);
			forwardPath = "/librarian.jsp";
			break;
		default:
			break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}
	
	
	private void editcopies(HttpServletRequest request, HttpServletResponse response, LibrarianService service,
			String message) throws ServletException, IOException {
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			Integer noCopies = Integer.parseInt(request.getParameter("copies"));
			BookCopies copies = new BookCopies();
			copies.setBookId(bookId);
			copies.setBranchId(branchId);
			copies.setNoOfCopies(noCopies);
			try {
				service.updateCopies(copies);
				message = "Sucessfully added copies";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Unsuccessful: did not add copies";
			}
			request.setAttribute("confMessage", message);
		}
	
	private void editBranch(HttpServletRequest request, HttpServletResponse response, LibrarianService service,
			String message) throws ServletException, IOException {
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			String branchName = request.getParameter("branchName");
			String branchAddress = request.getParameter("branchAddress");
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchId(branchId);
			branch.setBranchName(branchName);
			branch.setBranchAddress(branchAddress);
			if(branchId!=null){
				branch.setBranchId(branchId);
			}
			if(!branchName.isEmpty() && branchName.length() <45 && !branchAddress.isEmpty() && branchAddress.length() < 45){
				try {
					service.addBranch(branch);
					message = "Branch edited Sucessfully";
				} catch (SQLException e) {
					e.printStackTrace();
					message = "Branch edit failed";
				}
			}else{
				//add code to reply with error message.
			}
			request.setAttribute("confMessage", message);
		}
	

}
