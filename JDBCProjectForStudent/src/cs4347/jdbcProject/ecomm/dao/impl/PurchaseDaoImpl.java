package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class PurchaseDaoImpl implements PurchaseDAO
{
	@Override
	public Purchase create(Connection connection, Purchase purchase) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		

		 final String insertSQL = 
				"INSERT INTO PURCHASE (productID, customerID, purchaseDate, purchaseAmount) VALUES (?, ?, ?, ?);";

		
			if (purchase.getId() != null) {
				throw new DAOException("Trying to insert Purchase with NON-NULL ID");
			}

		//	connection = dataSource.getConnection();
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
				//ps.setLong(1, purchase.getId());
				//ps,setNull(1, purchase.getId());
				ps.setLong(1, purchase.getProductID());
				ps.setLong(2, purchase.getCustomerID());
				
				ps.setDate(3, purchase.getPurchaseDate());
				ps.setDouble(4, purchase.getPurchaseAmount());
				//ps.setObject(5, purchase.getId());
				// Set other statement attributes here...
				
				int res = ps.executeUpdate();
				if(res != 1) {
					throw new DAOException("Create Did Not Update Expected Number Of Rows");
				}
				
				ResultSet keyRS = ps.getGeneratedKeys();
				keyRS.next();
				int lastKey = keyRS.getInt(1);
				purchase.setId((long) lastKey);
				
				return purchase;
			}
			finally {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				
			
				}
			
		//return null;
	}

	@Override
	public Purchase retrieve(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		if (id == null) {
			throw new DAOException("Trying to retrieve Purchase with NULL ID");
		}


		final String selectSQL = "SELECT customerID, productID, purchaseDate, purchaseAmount"
		        + " FROM purchase where id = ?";
		
		//Connection connection = dataSource.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectSQL,Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Purchase purchase = new Purchase();
			purchase.setCustomerID(rs.getLong("customerID"));
			purchase.setPurchaseDate(rs.getDate("purchaseDate"));
			purchase.setPurchaseAmount(rs.getInt("purchaseAmount"));
			purchase.setProductID(rs.getLong("productID"));
			purchase.setId(id);
			return purchase;
		}
		finally {
			
		}
		//return null;
	}

	@Override
	public int update(Connection connection, Purchase purchase) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		final String updateSQL = "UPDATE purchase SET customerID = ?, purchaseDate = ?, purchaseAmount = ?, productID = ? "
		        + " WHERE id = ?;";
		/**
		 * The update method must throw DAOException if the provided 
		 * Purchase has a NULL id. 
		 */
			if (purchase.getId() == null) {
				throw new DAOException("Trying to update Product with NULL ID");
			}

			//Connection connection = dataSource.getConnection();
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement(updateSQL);
				ps.setLong(1, purchase.getCustomerID());
				ps.setDate(2, purchase.getPurchaseDate());
				ps.setDouble(3, purchase.getPurchaseAmount());
				ps.setLong(4, purchase.getProductID());
				ps.setLong(5,  purchase.getId());
				
				int rows = ps.executeUpdate();
				return rows;
			}
			finally {
				
			}
				
			
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub


		final String deleteSQL = "DELETE FROM PURCHASE WHERE id = ?;";
		
		if (id == null) {
			throw new DAOException("Trying to delete Purchase with NULL ID");
		}
	
		//Connection connection = dataSource.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);
	
			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			
		}
		
	}
		//return 0;
	

	@Override
	public List<Purchase> retrieveForCustomerID(Connection connection, Long customerID)
			throws SQLException, DAOException {
		// TODO Auto-generated method stub
		final String selectSQL = "SELECT id, productID, purchaseDate, purchaseAmount"
		        + " FROM purchase where customerID = ?;";
		
		
		if (customerID == null) {
			throw new DAOException("Trying to retrieve Purchase with Customer ID");
		}
		
		//Connection connection = dataSource.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectSQL,Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			// list to hold purchases
			List<Purchase> matches = new ArrayList<Purchase>();
			
			// get all of them
			while(rs.next()){
			
			Purchase purchase = new Purchase();
			purchase.setCustomerID(customerID);
			purchase.setPurchaseDate(rs.getDate("purchaseDate"));
			purchase.setPurchaseAmount(rs.getInt("purchaseAmount"));
			purchase.setProductID(rs.getLong("productID"));
			purchase.setId(rs.getLong("ID"));
			
			matches.add(purchase);
			}
			
			return matches;
		}
		
		
		
		finally {
			
		}
		

	}

	@Override
	public List<Purchase> retrieveForProductID(Connection connection, Long productID)
			throws SQLException, DAOException {
		// TODO Auto-generated method stub
		final String selectSQL = "SELECT id, customerID, purchaseDate, purchaseAmount"
		        + " FROM purchase where productID = ?";
		
		
		if (productID == null) {
			throw new DAOException("Trying to retrieve Purchase with Product ID");
		}
		
		//Connection connection = dataSource.getConnection();
		PreparedStatement ps = null;
		try {
			// list to hold purchases
			List<Purchase> matches = new ArrayList<Purchase>();
			ps = connection.prepareStatement(selectSQL,Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, productID);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return matches;
			}
			
			// get all of them
			while(rs.next()){
			
			Purchase purchase = new Purchase();
			purchase.setCustomerID(rs.getLong("customerID"));
			purchase.setPurchaseDate(rs.getDate("purchaseDate"));
			purchase.setPurchaseAmount(rs.getInt("purchaseAmount"));
			purchase.setId(rs.getLong("id"));
			
			matches.add(purchase);
			}
			
			return matches;
		}
		
		
		
		finally {
			
		}
		
		
	}

	@Override
	public PurchaseSummary retrievePurchaseSummary(Connection connection, Long customerID)
			throws SQLException, DAOException {
		// TODO Auto-generated method stub
		/*
		 * minPurchase
		 * maxPurchase
		 * avgPurchase
		 */
		final String pselectSQL = "SELECT purchaseAmount, min(purchaseAmount) as min,"
		+ "max(purchaseAmount) as max,"
		+ " avg(purchaseAmount) as avg"
		+ " FROM purchase"
		+ " where customerID = ?;";
		PreparedStatement ps = null;
		
		try{
			ps = connection.prepareStatement(pselectSQL,Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			
			PurchaseSummary summ = new PurchaseSummary();
			summ.avgPurchase = rs.getFloat("avg");
			summ.maxPurchase = rs.getFloat("max");
			summ.minPurchase = rs.getFloat("min");
			return summ;
		}
		finally{
			
		}
	}
	
}

	


