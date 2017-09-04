package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.ConnectionUtil;

public class BookDAO extends BaseDAO<Book>{
	
	public BookDAO(Connection connection) {
		super(connection);
	}
	 
	public void addBook(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("insert into tbl_book (bookName) values (?)", new Object[]{book.getTitle()});
	}
	
	public void updateBook(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("update tbl_book set title = ? where bookId = ?", new Object[] {book.getTitle(), book.getBookId()});
	}
	
	public void deleteBook(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("delete from tbl_book where bookId = ?", new Object[]{book.getBookId()});
	}

	public List<Book> readAllBooks(int pageNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return readAll("select * from tbl_book", null);
	}

	public List<Book> readAllbooksWithBranch(int pageNo, Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		BookCopies bc = new BookCopies();
		BookCopiesDAO bcDao = new BookCopiesDAO(conn);
		bc = bcDao.getAllCopiesId(branchId);
		return readAll("select * from tbl_book where bookId = ?", new Object[]{bc.getBookId()});

	}
	
	

	public Integer getCountOfBooks() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return getCount("select count(*) COUNT from tbl_book", null);
	}
	
	// Came from admin service getbookBYPk method go back there//
	public Book readBookByPK(Integer bookId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<Book> books =  readAll("select * from tbl_book where bookId = ?", new Object[]{bookId});
		if(!books.isEmpty()){
			return books.get(0);
		}
		return null;
	}
	

	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		GenreDAO gDao = new GenreDAO(conn);
		PublisherDAO pDao = new PublisherDAO(conn);
		BookCopiesDAO bcDao = new BookCopiesDAO(conn);
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setAuthors(adao.readAllFirstLevel("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[]{b.getBookId()}));
			Publisher pub = new Publisher();
			pub.setPublisherId(rs.getInt("pubId"));
			b.setPublisher(pub);
			//b.setPublisher(pdao.readAll("select * from tbl_publisher where publisherId = ?", new Object[]{rs.getInt("pubId")});
			//b.setNoCopies(bcDao.readAllFirstLevel("select noOfCopies from tbl_book_copies where bookId IN(select bookId from tbl_book where tbl_book.bookId = tbl_book_copies.bookId and branchId = ?)", vals));
			b.setGenres((List<Genre>) gDao.readAllFirstLevel("select * from tbl_genre where genre_id IN(select genre_id from tbl_book_genres where bookId = ?);", new Object[]{b.getBookId()}));
			books.add(b);
		}
		return books;
	}
	
	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}
		return books;
	}
	
	public Integer addBookWithID(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return saveWithID("insert into tbl_book(title, pubId) values (?, ?)", new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
	}
	
	public void addBookAuthors(Integer bookId, Integer authorId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		save("insert into tbl_book_authors values (?, ?)", new Object[] {bookId, authorId});
		
	}

	public void addBookGenres(Integer bookId, Integer genreId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		save("insert into tbl_book_genres values (?, ?)", new Object[] {genreId,bookId});
		
	}

	public void updateBookPublisher(Integer bookId, Integer publisherId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		save("update tbl_book set pubId = ? where bookId = ?", new Object[]{publisherId,bookId});
	}
	
	public List<Book> readBooksByName(String bookName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		bookName = "%"+bookName+"%";
		return readAll("select * from tbl_book where title LIKE  ?", new Object[]{bookName});
	}
	
}
