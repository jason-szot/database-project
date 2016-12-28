package cs4347.hibernateProject.ecomm.services.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import cs4347.hibernateProject.ecomm.entity.Purchase;
import cs4347.hibernateProject.ecomm.services.PurchasePersistenceService;
import cs4347.hibernateProject.ecomm.services.PurchaseSummary;
import cs4347.hibernateProject.ecomm.util.DAOException;

public class PurchasePersistenceServiceImpl implements PurchasePersistenceService
{
	private EntityManager em;

	public PurchasePersistenceServiceImpl(EntityManager em)
	{
		this.em = em;
	}
	
	@Override
	public Purchase create(Purchase purchase) throws SQLException, DAOException
	{
	
	//	if(purchase.getId() != null){
	//		throw new DAOException("Trying to create Purchase with non-NULL ID");
	//	}
	
		em.getTransaction().begin();
		
		em.persist(purchase);
		
		em.getTransaction().commit();
		
		
		return purchase;
	
	}

	@Override
	public Purchase retrieve(Long id) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		
		Purchase result = em.find(Purchase.class, id);
		
		//em.persist(result);
		
		em.getTransaction().commit();
		
		//em.close();
		
		return result;
	}

	@Override
	public Purchase update(Purchase purchase) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		Purchase update = em.find(Purchase.class, purchase.getId());
		update = purchase;
		em.getTransaction().commit();
		
		
		return update;
	}

	@Override
	public void delete(Long id) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		
		Purchase purchase = em.find(Purchase.class, id);
		
		em.remove(purchase);
		
		em.getTransaction().commit();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Purchase> retrieveForCustomerID(Long customerID) throws SQLException, DAOException
	{
		//list to return
		
		
		
		
		List<Purchase> purchases = em.createQuery(
				"from Purchase as p where p.id = :id")
				.setParameter("id", customerID)
				.getResultList();
		//em.persist(purchases);
		
		//em.getTransaction().commit();
		
		
		
		return purchases;
	}

	@Override
	public PurchaseSummary retrievePurchaseSummary(Long customerID) throws SQLException, DAOException
	{
		//PurchaseSummary ps = new PurchaseSummary();
		
	//	em.getTransaction().begin();
		
		//Purchase purc = em.find(Purchase.class, customerID);
		
		
		
	 double max = (double) em.createQuery("select max(purchaseAmount) from Purchase as p where p.customer.id = :id")
		.setParameter("id", customerID).getSingleResult();
		
	 double min = (double) em.createQuery("select min(purchaseAmount) from Purchase as p where p.customer.id = :id")
				.setParameter("id", customerID).getSingleResult();

	 double avg = (double) em.createQuery("select avg(purchaseAmount) from Purchase as p where p.customer.id = :id")
				.setParameter("id", customerID).getSingleResult();

	 PurchaseSummary ps =  new PurchaseSummary(min, max, avg);
	 
	// em.getTransaction().commit();
	 
		return ps;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Purchase> retrieveForProductID(Long productID) throws SQLException, DAOException
	{
		
		List<Purchase> purch_list = em.createQuery(
				"from Purchase as p where p.id = :id")
				.setParameter("id", productID)
				.getResultList();
		

		return purch_list;
	}
}
