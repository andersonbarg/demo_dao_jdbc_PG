package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department department) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(
				"INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, department.getName());
			
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No row affected.");
			}
		}
		catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
			ps.setString(1, department.getName());
			ps.setInt(2, department.getId());
			
			ps.executeUpdate();
		
		}
		catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
		}
		catch (SQLException e){
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(
				"SELECT * FROM department "
				+ "WHERE Id = ?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;
			}
			return null;			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
			rs = ps.executeQuery();
			List<Department> list = new ArrayList<Department>();
			
			while (rs.next()) {
				Department dep = instantiateDepartment(rs);
				list.add(dep);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

}
