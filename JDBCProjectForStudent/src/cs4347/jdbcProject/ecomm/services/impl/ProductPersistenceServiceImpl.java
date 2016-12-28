package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.services.ProductPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;
import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;

import java.sql.Connection;


public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	private DataSource dataSource;

	public ProductPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	
	/**
	 * The create method must throw a DAOException if the 
	 * given Product has a non-null ID. The create method must 
	 * return the same Product with the ID attribute set to the
	 * value set by the application's auto-increment primary key column. 
	 * @throws DAOException if the given Purchase has a non-null id.
	 */
	public Product create(Product product) throws SQLException, DAOException
	{
		ProductDAO productDao = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try{
			connection.setAutoCommit(false);
			Product prod = productDao.create(connection, product);
			
			connection.commit();
			return prod;
		}
		catch(Exception ex){
			connection.rollback();
			throw ex;
		}
		finally{
			if(connection != null){
				connection.setAutoCommit(true);
			}
			if(connection != null && !connection.isClosed()){
				connection.close();
			}
		}
	}
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	public Product retrieve(Long id) throws SQLException, DAOException
	{
		ProductDAO productDao = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try{
			connection.setAutoCommit(false);
			Product prod = productDao.retrieve(connection, id);
			
			connection.commit();
			return prod;
		}
		catch(Exception ex){
			connection.rollback();
			throw ex;
		}
		finally{
			if(connection != null){
				connection.setAutoCommit(true);
			}
			if(connection != null && !connection.isClosed()){
				connection.close();
			}
		}
	}
	
	/**
	 * The update method must throw DAOException if the provided 
	 * Product has a NULL id. 
	 */
	public int update(Product product) throws SQLException, DAOException{
		
		ProductDAO productDao = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		try{
			connection.setAutoCommit(false);
			int rows = productDao.update(connection, product);
			connection.commit();
			return rows;
		}
		catch(Exception ex){
			connection.rollback();
			throw ex;
		}
		finally{
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		
	}
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	public int delete(Long id) throws SQLException, DAOException
	{
		ProductDAO productDao = new ProductDaoImpl();

		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			int rows = productDao.delete(connection, id);

			connection.commit();
			return rows;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		
	
	}
	
	/**
	 * Retrieve a product by its unique UPC
	 */
	public Product retrieveByUPC(String upc) throws SQLException, DAOException
	{
		ProductDAO productDao = new ProductDaoImpl();

		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false);
			Product prods = productDao.retrieveByUPC(connection, upc);
			return prods;

		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		
	
	}

	/**
	 * Retrieve products in the given category
	 */
	public List<Product> retrieveByCategory(int category) throws SQLException, DAOException
	{
		// TODO Auto-generated method stub
			ProductDAO productDao = new ProductDaoImpl();
	
			Connection connection = dataSource.getConnection();
			try {
				connection.setAutoCommit(false);
				List<Product> prod = productDao.retrieveByCategory(connection, category);
				return prod;
	
				
			}
			catch (Exception ex) {
				connection.rollback();
				throw ex;
			}
			finally {
				if (connection != null) {
					connection.setAutoCommit(true);
				}
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			}
	}

		
	

}
