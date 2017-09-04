package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;;

public class BranchDAO extends BaseDAO<LibraryBranch> {

	public BranchDAO(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}
	
	public void addBranch(LibraryBranch branch) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("insert into tbl_library_branch (branchName, branchAddress) values (?, ?)", 
				new Object[]{branch.getBranchName(), branch.getBranchAddress()});
	}
	
	public void updateBranch(LibraryBranch branch) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?", 
				new Object[]{branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
	}
	
	public void deleteBranch(LibraryBranch branch) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		save("delete from tbl_library_branch where branchId = ?", new Object[]{branch.getBranchId()});
	}

	public List<LibraryBranch> readAllBranches(int pageNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return readAll("select * from tbl_library_branch", null);
	}

	public LibraryBranch readBranchByPK(Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<LibraryBranch> branches =  readAll("select * from tbl_library_branch where branchId = ?", new Object[]{branchId});
		if(!branches.isEmpty()){
			return branches.get(0);
		}
		return null;
	}
	
	@Override
	public List<LibraryBranch> extractData(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<LibraryBranch> branches = new ArrayList<>();
		while(rs.next()){
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchId(rs.getInt("branchId"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branches.add(branch);
		}
		return branches;
	}

	@Override
	public List<LibraryBranch> extractDataFirstLevel(ResultSet rs)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

