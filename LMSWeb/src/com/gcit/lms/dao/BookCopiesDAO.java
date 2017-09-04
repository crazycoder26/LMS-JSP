package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;

public class BookCopiesDAO extends BaseDAO<BookCopies>{
	
	public BookCopiesDAO(Connection connection){
		super(connection);
	}
	
	public void addBookCopies(BookCopies bookCopies) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?, ?, ?)", new Object[]{bookCopies.getBookId(), bookCopies.getBranchId(), bookCopies.getNoOfCopies()});
	}
	
	public void updateBookCopiesOut(BookCopies bookCopies) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("update tbl_book_copies set noOfCopies = noOfCopies - 1 where bookId = ? and branchId = ?", 
				new Object[]{bookCopies.getBookId(), bookCopies.getBranchId()});
	}
	
	public void updateBookCopiesIn(BookCopies bookCopies) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("update tbl_book_copies set noOfCopies = noOfCopies + 1 where bookId = ? and branchId = ?", 
				new Object[]{bookCopies.getBookId(), bookCopies.getBranchId()});
	}
	
	public void updateBookCopies(BookCopies bookCopies) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?", 
				new Object[]{bookCopies.getNoOfCopies(),bookCopies.getBookId(), bookCopies.getBranchId()});
	}
	
	public BookCopies getAllCopiesId(Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<BookCopies> bookCopies =  readAll("select * from tbl_book_copies where branchId = ?", 
										new Object[]{branchId});
		if(!bookCopies.isEmpty()){
			return bookCopies.get(0);
		}
		return null;
	}
	
	public BookCopies getAllCopiesbId(Integer bookId, Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<BookCopies> bookCopies =  readAll("select * from tbl_book_copies where bookId = ? and branchId = ?", 
										new Object[]{bookId, branchId});
		if(!bookCopies.isEmpty()){
			return bookCopies.get(0);
		}
		return null;
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<BookCopies> copiesList = new ArrayList<>();
		while(rs.next()){
			BookCopies bCopies = new BookCopies();
			bCopies.setBookId(rs.getInt("bookId"));
			bCopies.setBranchId(rs.getInt("branchId"));
			bCopies.setNoOfCopies(rs.getInt("noOfCopies"));
			copiesList.add(bCopies);
		}
		return copiesList;
	}

	@Override
	public List<BookCopies> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return null;
	}
	
	
}

