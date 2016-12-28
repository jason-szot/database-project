package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.util.DAOException;


public class CreditCardDaoImpl implements CreditCardDAO
{
	
	private static final String insertSQL = 
			"INSERT INTO CREDITCARD (name, ccNumber, expDate, securityCode, customerID) VALUES (?, ?, ?, ?, ?);";
	private static final String selectSQL = 
			"SELECT name, ccNumber, expDate, securityCode FROM CREDITCARD WHERE customerID = ?;";
	private static final String deleteSQL = 
			"DELETE FROM CREDITCARD WHERE customerID = ?;";

	@Override
	public CreditCard create(Connection connection, CreditCard creditCard, Long customerID)
			throws SQLException, DAOException {
		// TODO test
		
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, creditCard.getName());
			ps.setString(2, creditCard.getCcNumber());
			ps.setString(3, creditCard.getExpDate());
			ps.setString(4, creditCard.getSecurityCode());
			ps.setLong(5, customerID);
			
			int res = ps.executeUpdate();
			if(res != 1) {
				throw new DAOException("Create Did Not Update Expected Number Of Rows");
			}
			return creditCard;
		}
		finally{
			
		}
	}

	@Override
	public CreditCard retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		// TODO test
		if (customerID == null) {
			throw new DAOException("Trying to retrieve creditcard with NULL customerID");
		}
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(selectSQL);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			CreditCard cc = new CreditCard();
			cc.setName(rs.getString("name"));
			cc.setCcNumber(rs.getString("ccNumber"));
			cc.setExpDate(rs.getString("expDate"));
			cc.setSecurityCode(rs.getString("securityCode"));
			return cc;
		}
		finally{
			
		}
	}

	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		// TODO test
		
		if (customerID == null) {
			throw new DAOException("Trying to delete creditcard with NULL customerID");
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
