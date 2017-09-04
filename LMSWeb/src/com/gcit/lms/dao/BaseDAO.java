package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO<T> {
	
	protected static Connection conn = null;
	
	private int pageNo = 1;
	
	private int pageSize = 10;
	
	public BaseDAO(Connection connection){
		this.conn = connection;
	}
	
	
	public void save(String sql, Object[] vals) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		pstmt.executeUpdate();
	}
	
	public Integer saveWithID(String sql, Object[] vals) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		if(vals!=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if(rs.next()){
			return rs.getInt(1);
		}
		return null;
	}
	
	public List<T> readAll(String sql, Object[] vals) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		sql+= " LIMIT "+(getPageNo()-1) * getPageSize()+" , "+getPageSize();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		return extractData(pstmt.executeQuery());
	}
	
	public Integer getCount(String sql, Object[] vals) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		}
		return null;
	}

	public abstract List<T> extractData(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	
	public List<T> readAllFirstLevel(String sql, Object[] vals) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if(vals!=null){
			int count = 1;
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		return extractDataFirstLevel(pstmt.executeQuery());
	}

	public abstract List<T> extractDataFirstLevel(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;


	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}


	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}


	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
