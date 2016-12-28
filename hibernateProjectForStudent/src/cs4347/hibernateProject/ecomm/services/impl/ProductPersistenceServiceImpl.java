package cs4347.hibernateProject.ecomm.services.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import cs4347.hibernateProject.ecomm.entity.Product;
import cs4347.hibernateProject.ecomm.services.ProductPersistenceService;
import cs4347.hibernateProject.ecomm.util.DAOException;

public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	private EntityManager em;

	public ProductPersistenceServiceImpl(EntityManager em)
	{
		this.em = em;
	}
	
	/**
	 * The create method must throw a DAOException if the 
	 * given Product has a non-null ID. The create method must 
	 * return the same Product with the ID attribute set to the
	 * value set by the application's auto-increment primary key column. 
	 * @throws DAOException if the given Purchase has a non-null id.
	 */
	@Override
	public Product create(Product product) throws SQLException, DAOException
	{
		if(product.getId() != null){
			throw new DAOException("Trying to create Product with non-NULL ID");
		}
		em.getTransaction().begin();
		em.persist(product);
		em.getTransaction().commit();
		
		return product;
	}
	
	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	@Override
	public Product retrieve(Long id) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		Product product = em.find(Product.class, id);
		em.getTransaction().commit();
		
		return product;
	}
	
	/**
	 * The update method must throw DAOException if the provided 
	 * Product has a NULL id. 
	 */
	@Override
	public Product update(Product p) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		Product product = em.find(Product.class, p.getId());
		product = p;
		em.getTransaction().commit();
		
		return product;
	}

	/**
	 * The update method must throw DAOException if the provided 
	 * ID is null. 
	 */
	@Override
	public void delete(Long id) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		Product product = em.find(Product.class, id);
		em.remove(product);
		em.getTransaction().commit();
	}

	/**
	 * Retrieve a product by its unique UPC
	 */
	@Override
	public Product retrieveByUPC(String upc) throws SQLException, DAOException
	{
		Product product =  (Product) em.createQuery("from Product where prodUPC = :upc")
				.setParameter("upc", upc).getResultList().toArray()[0];
		return product;
	}
	
	/**
	 * Retrieve products in the given category
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> retrieveByCategory(int category) throws SQLException, DAOException
	{	
		List<Product> prod_list = em.createQuery(
				"from Product where prodcategory = :cat")
				.setParameter("cat", category)
				.getResultList();
		return prod_list;
	}

}
