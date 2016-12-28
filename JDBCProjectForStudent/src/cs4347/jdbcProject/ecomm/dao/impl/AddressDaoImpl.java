package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class AddressDaoImpl implements AddressDAO
{
	private static final String insertSQL = 
			"INSERT INTO ADDRESS (address1, address2, city, state, zipcode, customerID) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String selectSQL = 
			"SELECT address1, address2, city, state, zipcode FROM ADDRESS WHERE customerID = ?;";
	private static final String deleteSQL = 
			"DELETE FROM ADDRESS WHERE customerID = ?;";
	@Override
	public Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException {
		// TODO test
		
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, address.getAddress1());
			ps.setString(2, address.getAddress2());
			ps.setString(3, address.getCity());
			ps.setString(4, address.getState());
			ps.setString(5, address.getZipcode());
			ps.setLong(6, customerID);
					
			int res = ps.executeUpdate();
			if(res != 1) {
				throw new DAOException("Create Did Not Update Expected Number Of Rows");
			}
			
			return address;
		}
		finally{
			
		}
	}

	@Override
	public Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		// TODO test
		if (customerID == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}
		
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(selectSQL);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			Address addr = new Address();
			addr.setAddress1(rs.getString("address1"));
			addr.setAddress2(rs.getString("address2"));
			addr.setCity(rs.getString("city"));
			addr.setState(rs.getString("state"));
			addr.setZipcode(rs.getString("zipcode"));
			return addr;
		}
		finally{
			
		}
	}

	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		if (customerID == null) {
			throw new DAOException("Trying to delete Customer with NULL ID");
		}
		
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, customerID);

			ps.executeUpdate();
			return;
		}
		finally{
			
		}
		
	}

}
