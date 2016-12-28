package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerDaoImpl implements CustomerDAO
{
	public CustomerDaoImpl (){
		
	}
	private static final String insertSQL = 
			"INSERT INTO CUSTOMER (firstName, lastName, gender, dob, email) VALUES (?, ?, ?, ?, ?);";
	private static final String selectbyIDSQL = 
			"SELECT FIRSTNAME, LASTNAME, GENDER, DOB, EMAIL"
				+ " FROM CUSTOMER WHERE ID = ?;";
	private static final String selectbyzipSQL = 
			"SELECT * FROM (ADDRESS INNER JOIN CREDITCARD"
					+ " ON ADDRESS.CUSTOMERID = CREDITCARD.CUSTOMERID"
					+ " INNER JOIN CUSTOMER"
					+ " ON ADDRESS.CUSTOMERID = CUSTOMER.ID) WHERE ADDRESS.ZIPCODE = ?;";
	private static final String selectbydobSQL = 
			"SELECT * FROM (ADDRESS INNER JOIN CREDITCARD"
					+ " ON ADDRESS.CUSTOMERID = CREDITCARD.CUSTOMERID"
					+ " INNER JOIN CUSTOMER"
					+ " ON ADDRESS.CUSTOMERID = CUSTOMER.ID) WHERE CUSTOMER.DOB BETWEEN ? AND ?;";
	private static final String deleteSQL = 
			"DELETE FROM CUSTOMER WHERE ID = ?;";
	private static final String updateSQL =
			"UPDATE CUSTOMER "
					+ "SET CUSTOMER.FIRSTNAME = ?, CUSTOMER.LASTNAME = ?, "
					+ "CUSTOMER.GENDER = ?, CUSTOMER.DOB = ?, CUSTOMER.EMAIL = ? "
					+ "WHERE ID = ?; ";
	/**
	 * The create method must throw a DAOException if the 
	 * given Customer has a non-null ID.The create method must 
	 * return the same Customer with the ID attribute set to the
	 * value set by the application's auto-increment primary key column. 
	 * @throws DAOException if the given Customer has a non-null id.
	 */
	@Override
	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException {
		// TODO test
		if(customer.getId() != null){
			throw new DAOException("Trying to create Customer with non-NULL ID");
		}
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, customer.getGender().toString());
			ps.setDate(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			int res = ps.executeUpdate();
			if(res != 1) {
				throw new DAOException("Create Did Not Update Expected Number Of Rows");
			}

			// REQUIREMENT: Copy the generated auto-increment primary key to the
			// customer ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			customer.setId((long) lastKey);
			return customer;
			
		}
		finally{
			
		}
	}
	
	/**
	 * The retrieve method must throw DAOException if the provided 
	 * ID is null. 
	 */
	@Override
	public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Test
		if(id == null){
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(selectbyIDSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			
			Customer cust = new Customer();
			cust.setFirstName(rs.getString("firstName"));
			cust.setLastName(rs.getString("lastName"));
			cust.setGender(rs.getString("gender").charAt(0));	// hacky, hopefully it works
			cust.setDob(rs.getDate("dob"));
			cust.setEmail(rs.getString("email"));
			cust.setId(id);
			return cust;
			

		}
		finally{
			
		}
	}
	
	/**
	 * The update method must throw DAOException if the provided 
	 * Customer has a NULL id. 
	 */
	@Override
	public int update(Connection connection, Customer customer) throws SQLException, DAOException {
		// TODO test
		if(customer.getId() == null){
			throw new DAOException("Trying to update Customer with NULL ID");
		}
		PreparedStatement ps = null;
		try{
			
			ps = connection.prepareStatement(updateSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, customer.getGender().toString());
			ps.setDate(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			ps.setLong(6, customer.getId());
			
			int rows = ps.executeUpdate();
			return rows;
		}
		finally{
			
		}
	}

	/**
	 * The delete method must throw DAOException if the provided 
	 * ID is null. 
	 * 
	 * Note the implementation of this method can 
	 * make use of CASCADE_DELETE option on the FK relations for
	 * Address, CreditCard, and Purchase. If the schema includes cascade delete,
	 * the Customer delete operation will automatically remove the associated
	 * Address, CreditCard, and Purchase rows for their tables. 
	 */
	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// TODO test
		if(id == null){
			throw new DAOException("Trying to delete Customer with NULL ID");
		}
		
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rows = ps.executeUpdate();
			return rows;
		}
		finally{
			
		}
	}

	/**
	 * Retrieve customers in the given address.zipcode
	 */
	@Override
	public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
		// TODO test
		if(zipCode == null){
			throw new DAOException("Trying to retrieve by NULL zipCode");
		}
		
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectbyzipSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, zipCode);
			ResultSet rs = ps.executeQuery();

			List<Customer> result = new ArrayList<Customer>();
			while (rs.next()) {
				Customer cust = new Customer();
				cust.setFirstName(rs.getString("firstName"));
				cust.setLastName(rs.getString("lastName"));
				cust.setGender(rs.getString("gender").charAt(0));	// hacky, hopefully it works
				cust.setDob(rs.getDate("dob"));
				cust.setEmail(rs.getString("email"));
				// address next
				Address addr = new Address();
				addr.setAddress1(rs.getString("address1"));
				addr.setAddress2(rs.getString("address2"));
				addr.setCity(rs.getString("city"));
				addr.setState(rs.getString("state"));
				addr.setZipcode(rs.getString("zipcode"));
				cust.setAddress(addr);
				// credit card now
				CreditCard cc = new CreditCard();
				cc.setName(rs.getString("name"));
				cc.setCcNumber(rs.getString("ccNumber"));
				cc.setExpDate(rs.getString("expDate"));
				cc.setSecurityCode(rs.getString("securityCode"));
				cust.setCreditCard(cc);
				result.add(cust);
			}
			return result;
		}
		finally {
			
		}
	}

	/**
	 * Retrieve customers with a DOB in the given start / end date range.
	 */
	@Override
	public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate)
			throws SQLException, DAOException {
		// TODO test
		if(startDate == null || endDate == null){
			throw new DAOException("Trying to retrieve by NULL Dates");
		}
		
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectbydobSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1, startDate);
			ps.setDate(2, endDate);
			ResultSet rs = ps.executeQuery();

			List<Customer> result = new ArrayList<Customer>();
			while (rs.next()) {
				Customer cust = new Customer();
				cust.setFirstName(rs.getString("firstName"));
				cust.setLastName(rs.getString("lastName"));
				cust.setGender(rs.getString("gender").charAt(0));	// hacky, hopefully it works
				cust.setDob(rs.getDate("dob"));
				cust.setEmail(rs.getString("email"));
				// address next
				Address addr = new Address();
				addr.setAddress1(rs.getString("address1"));
				addr.setAddress2(rs.getString("address2"));
				addr.setCity(rs.getString("city"));
				addr.setState(rs.getString("state"));
				addr.setZipcode(rs.getString("zipcode"));
				cust.setAddress(addr);
				// credit card now
				CreditCard cc = new CreditCard();
				cc.setName(rs.getString("name"));
				cc.setCcNumber(rs.getString("ccNumber"));
				cc.setExpDate(rs.getString("expDate"));
				cc.setSecurityCode(rs.getString("securityCode"));
				cust.setCreditCard(cc);
				result.add(cust);
			}
			return result;
		}
		finally {
			
		}
	}
	
}
