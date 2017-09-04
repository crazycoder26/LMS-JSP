package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

public class LibrarianService {
	
	ConnectionUtil connUtil = new ConnectionUtil();
	
	public List<LibraryBranch> getAllBranches(int pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO brDao = new BranchDAO(conn);
			return brDao.readAllBranches(pageNo);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public BookCopies getBookCopiesById(Integer bookId, Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookCopiesDAO bcDao = new BookCopiesDAO(conn);
			return bcDao.getAllCopiesbId(bookId,branchId);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public String getBorrowerName(Integer cardNo) throws SQLException{
		System.out.println(cardNo);
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO brDao = new BorrowerDAO(conn);
			Borrower borrower = brDao.readBorrowerByPK(cardNo);
			
			if(borrower != null){
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
	
	public List<Book> getAllBooksWithBranch(Integer pageNo, Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bDao = new BookDAO(conn);
			return bDao.readAllbooksWithBranch(pageNo, branchId);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	
	
	public LibraryBranch getBranchPK(Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO brDao = new BranchDAO(conn);
			return brDao.readBranchByPK(branchId);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public void addBranch(LibraryBranch branch) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO brDao = new BranchDAO(conn);
			if(branch.getBranchId()!=null){
				brDao.updateBranch(branch);
			}else{
				brDao.addBranch(branch);
			}
			conn.commit();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		
	}
	
	public void updateCopies(BookCopies copies) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookCopiesDAO bcDao = new BookCopiesDAO(conn);
			if(copies.getBookId() != null){
				bcDao.updateBookCopies(copies);
			}else{
				bcDao.addBookCopies(copies);
			}
			conn.commit();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}	
}
