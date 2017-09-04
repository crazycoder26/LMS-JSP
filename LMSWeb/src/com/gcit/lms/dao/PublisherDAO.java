package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection connection) {
		super(connection);
	}
	
	public void addPublisher(Publisher publisher) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values(?, ?, ?)", new Object[]{publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()});	
	}

	public void updatePublisher(Publisher publisher) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?", 
				new Object[]{publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId()});
	}
	
	public void deletePublisher(Publisher publisher) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("delete from tbl_publisher where publisherId = ?", new Object[]{publisher.getPublisherId()});
	}
	
	public List<Publisher> readAllPublishers(Integer pageNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return readAll("select * from tbl_publisher", null);
		
	}
	
	public Integer getCountOfPublishers() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return getCount("select count(*) COUNT from tbl_publisher", null);
	}
	
	public Publisher readPublisherByPK(Integer publisherId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<Publisher> publishers =  readAll("select * from tbl_publisher where publisherId = ?", new Object[]{publisherId});
		if(!publishers.isEmpty()){
			return publishers.get(0);
		}
		return null;
	}
	
	public Publisher readPublisherByID(Integer PubId) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		@SuppressWarnings("unchecked")
		List<Publisher> listPublisher = (List<Publisher>) readAll("select * from tbl_publisher where publisherId = ?", new Object[] { PubId });
		if (listPublisher != null && !listPublisher.isEmpty()) {
			return listPublisher.get(0);
		}
		return null;
	}
	
	
	@Override
	public List<Publisher> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Publisher> publishers = new ArrayList<>();
		BookDAO bookDao = new BookDAO(conn);
		while(rs.next()){
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			//publisher.setBooks(bookDao.readAll("select * from tbl_book where pubId = ?", new Object[]{rs.getInt("pubId")}));
			publishers.add(publisher);
		}
		return publishers;
	}

	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Publisher> readPublishersByName(String publisherName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		publisherName = "%"+publisherName+"%";
		return readAll("select * from tbl_publisher where publisherName LIKE  ?", new Object[]{publisherName});
	}
}