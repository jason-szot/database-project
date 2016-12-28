package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Statement;
import java.util.List;
//import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO
{
	
	
	/**////////////////////////////////////////*/
	private static final String insertSQL = 
			"INSERT INTO PRODUCT (prodName, prodDescription, prodCategory, prodUPC) VALUES (?, ?, ?, ?);";
	/**
	 * The create method must throw a DAOException if the 
	 * given Product has a non-null ID. The create method must 
	 * return the same Product with the ID attribute set to the
	 * value set by the application's auto-increment primary key column. 
	 * @throws DAOException if the given Product has a non-null id.
	 */
	public Product create(Connection connection, Product product) throws SQLException, DAOException
	{
		if (product.getId() != null) {
			throw new DAOException("Trying to insert Product with NON-NULL ID");
		}
		
		PreparedStatement ps = null;
		try{
			//ps = connection.preparedStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,  product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3,  product.getProdCategory());
			ps.setString(4,  product.getProdUPC());
			ps.executeUpdate();
			
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			product.setId((long) lastKey);
			
			return product;
		}
		finally {
			
		}
	}
	
	
	

	
	final static String selectQuery = "SELECT id, prodName, prodDescription, prodCategory, prodUPC "
	        + "FROM product where id = ?";
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException
	{
		if (id == null) {
			throw new DAOException("Trying to retrieve Product with NULL ID");
		}

		//Connection connection = dataSource.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Product prod = new Product();
			prod.setId(rs.getLong("id"));
			prod.setProdName(rs.getString("prodName"));
			prod.setProdDescription(rs.getString("prodDescription"));
			prod.setProdCategory(rs.getInt("prodCategory"));
			prod.setProdUPC(rs.getString("prodUPC"));
			return prod;
		}
		finally {
			
		}
	}
	
	
	
	
	
	
	final static String updateSQL = "UPDATE product SET prodName = ?, prodDescription = ?, prodCategory = ?, prodUPC = ? "
	        + "WHERE id = ?;";
	/**
	 * The update method must throw DAOException if the provided 
	 * Product has a NULL id. 
	 */
	public int update(Connection connection, Product product) throws SQLException, DAOException
	{
		
		if (product.getId() == null) {
			throw new DAOException("Trying to update Product with NULL ID");
		}

		//Connection connection = dataSource.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
			ps.setLong(5, product.getId());
			
			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			
		}
		
	}
	
	
	
	
	final static String deleteSQL = "DELETE FROM PRODUCT WHERE id = ?;";
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	public int delete(Connection connection, Long id) throws SQLException, DAOException
	{
	
	
		if (id == null) {
			throw new DAOException("Trying to delete Product with NULL ID");
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
	
	
	
	final static String selectQuery2 = 
			"SELECT id, prodName, prodDescription, prodCategory, prodUPC "
			+ "FROM product where prodCategory = ?";
	/**
	 * Retrieve products in the given product category
	 */
	public List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException
	{
		//Connection connection = dataSource.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectQuery2);
			ps.setInt(1, category);
			ResultSet rs = ps.executeQuery();

			List<Product> result = new ArrayList<Product>();
			while (rs.next()) {
				Product prod = new Product();
				prod.setId(rs.getLong("id"));
				prod.setProdName(rs.getString("prodName"));
				prod.setProdDescription(rs.getString("prodDescription"));
				prod.setProdCategory(rs.getInt("prodCategory"));
				prod.setProdUPC(rs.getString("prodUPC"));
				result.add(prod);
			}
			return result;
		}
		finally {
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	final static String selectQuery3 = "SELECT id, prodName, prodDescription, prodCategory, prodUPC "
	        + "FROM product where prodUPC = ?";
	/**
	 * Retrieve the product with the given UPC. UPC is unique across all product. 
	 */
	public Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException
	{
		
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectQuery3);
			ps.setString(1, upc);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Product prod = new Product();
			prod.setId(rs.getLong("id"));
			prod.setProdName(rs.getString("prodName"));
			prod.setProdDescription(rs.getString("prodDescription"));
			prod.setProdCategory(rs.getInt("prodCategory"));
			prod.setProdUPC(rs.getString("prodUPC"));
			return prod;
		}
		finally {
			
		}
	}
	
	
		
	
}
