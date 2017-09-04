package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

public class AuthorDAO extends BaseDAO<Author>{
	
	public AuthorDAO(Connection connection) {
		super(connection);
	}

	public void addAuthor(Author author) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Integer authorId = saveWithID("insert into tbl_author (authorName) values (?)", new Object[]{author.getAuthorName()});
		List<Book> books = author.getBooks();
		for(Book b : books){
			save("insert into tbl_book_authors (bookId, authorId) values (?, ?)", new Object[]{b.getBookId(), authorId});
		}
	}
	
	public void updateAuthor(Author author) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("update tbl_author set authorName = ? where authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
		List<Book> books = author.getBooks();
		for(Book b : books){
			save("insert into tbl_book_authors (bookId, authorId) values (?, ?)", new Object[]{b.getBookId(), author.getAuthorId()});
		}
	}
	
	public void deleteAuthor(Author author) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("delete from tbl_author where authorId = ?", new Object[]{author.getAuthorId()});
	}
	
	public void deleteBookAuthor(Integer bookId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("delete from tbl_book_authors where bookId = ?", new Object[]{bookId});
	}
	
	public List<Author> readAllAuthors(Integer pageNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return readAll("select distinct * from tbl_author", null);
	}
	
	public Integer getCountOfAuthors(String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		if(searchString!=null && !searchString.isEmpty()){
			searchString = "%"+searchString+"%";
			return getCount("select count(*) COUNT from tbl_author where authorName like ?", new Object[]{searchString});
		}else{
			return getCount("select count(*) COUNT from tbl_author", null);
		}
	}
	
	public Author readAuthorByPK(Integer authorId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<Author> authors =  readAll("select * from tbl_author where authorId = ?", new Object[]{authorId});
		if(!authors.isEmpty()){
			return authors.get(0);
		}
		return null;
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			a.setBooks(bdao.readAllFirstLevel("select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)", new Object[]{a.getAuthorId()}));
			authors.add(a);
		}
		return authors;
	}
	
	@Override
	public List<Author> extractDataFirstLevel(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}

	public List<Author> readAuthorsByName(String authorName, Integer pageNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		authorName = "%"+authorName+"%";
		return readAll("select * from tbl_author where authorName LIKE  ?", new Object[]{authorName});
	}
	

}
