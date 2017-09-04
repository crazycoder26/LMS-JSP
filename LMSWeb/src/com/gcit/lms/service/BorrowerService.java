package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

public class BorrowerService {
	
	ConnectionUtil connUtil = new ConnectionUtil();
	
	public String getBorrowerName(Integer cardNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO brDao = new BorrowerDAO(conn);
			Borrower borrower = brDao.readBorrowerByPK(cardNo);
			if(borrower.getName() != null){
				return borrower.getName();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<BookLoans> getAllBookWithLoans(Integer pageNo, Integer cardNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookLoansDAO blDao = new BookLoansDAO(conn);
			return blDao.readAllbooksWithCardNo(pageNo, cardNo);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public void checkOut(Borrower borrower, Book book, LibraryBranch branch) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			long millis = System.currentTimeMillis();  
		    Date dateOut = new Date(millis);
		    long ltime = dateOut.getTime()+5*24*60*60*1000;
		    Date dueDate = new Date(ltime);
//			save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) values (?, ?, ?, ?, ?, ?)"
//					,new Object[]{book.getBookId(), branch.getBranchId(), borrower.getCardNo(), dateOut,dueDate, null});
		    BookLoansDAO blDao = new BookLoansDAO(conn);
		    BookLoans bookLoans = new BookLoans();
		    bookLoans.setBookId(book.getBookId());
		    bookLoans.setBranchId(branch.getBranchId());
		    bookLoans.setCardNo(borrower.getCardNo());
		    bookLoans.setDateOut(dateOut);
		    bookLoans.setDueDate(dueDate);
		    bookLoans.setDateIn(null);
		    blDao.addLoans(bookLoans);
		  
			BookCopiesDAO bcDao = new BookCopiesDAO(conn);
			BookCopies bookCopies = new BookCopies();
			bookCopies.setBookId(book.getBookId());
			bookCopies.setBranchId(branch.getBranchId());
			//bookCopies.setNoOfCopies(bookCopies.getNoOfCopies() - 1);
			bcDao.updateBookCopiesOut(bookCopies);
			conn.commit();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void checkIn(Borrower borrower, Book book, LibraryBranch branch) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			long millis = System.currentTimeMillis();  
		    Date dateIn = new Date(millis);
//			save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) values (?, ?, ?, ?, ?, ?)"
//					,new Object[]{book.getBookId(), branch.getBranchId(), borrower.getCardNo(), dateOut,dueDate, null});
		    BookLoansDAO blDao = new BookLoansDAO(conn);
		    BookLoans bookLoans = new BookLoans();
		    bookLoans.setBookId(book.getBookId());
		    bookLoans.setBranchId(branch.getBranchId());
		    bookLoans.setCardNo(borrower.getCardNo());
		    bookLoans.setDateOut(bookLoans.getDateOut());
		    bookLoans.setDueDate(bookLoans.getDueDate());
		    bookLoans.setDateIn(dateIn);
		    blDao.updateLoans(bookLoans);
		  
			BookCopiesDAO bcDao = new BookCopiesDAO(conn);
			BookCopies bookCopies = new BookCopies();
			bookCopies.setBookId(book.getBookId());
			bookCopies.setBranchId(branch.getBranchId());
			//bookCopies.setNoOfCopies(bookCopies.getNoOfCopies() + 1);
			bcDao.updateBookCopiesIn(bookCopies);
			conn.commit();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
}
