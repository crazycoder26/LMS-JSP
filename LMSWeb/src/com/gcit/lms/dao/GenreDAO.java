package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO<Genre>{

	public GenreDAO(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	public void addGenre(Genre genre) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("insert into tbl_genre (genre_name) values(?)", new Object[]{genre.getGenreName()});
	}
	
	public void updateGenre(Genre genre) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("update tbl_genre set genre_name = ? where genre_id  = ?", new Object[]{genre.getGenreName(), genre.getGenreId()});
	}
	
	public void deleteGenre(Genre genre) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("delete from tbl_genre where genre_id = ?", new Object[]{genre.getGenreId()});
	}
	
	public List<Genre> readAllGenres() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return readAll("select * from tbl_genre", null);
	}
	
	public Genre readGenreByID(Integer genreID) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		@SuppressWarnings("unchecked")
		List<Genre> listGenre = (List<Genre>) readAll("select * from tbl_genre where genre_id = ?", new Object[] { genreID });
		if (listGenre != null && !listGenre.isEmpty()) {
			return listGenre.get(0);
		}
		return null;
	}
	
	@Override
	public List<Genre> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<>();
		while(rs.next()){
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genres.add(genre);
		}
		return genres;
	}

	@Override
	public List<Genre> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<>();
		while(rs.next()){
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			
			
		}
		return null;
	}

	
}

