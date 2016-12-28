package cs4347.hibernateProject.ecomm.services.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import cs4347.hibernateProject.ecomm.entity.Customer;
import cs4347.hibernateProject.ecomm.services.CustomerPersistenceService;
import cs4347.hibernateProject.ecomm.util.DAOException;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService
{
	private EntityManager em;

	public CustomerPersistenceServiceImpl(EntityManager em)
	{
		this.em = em;
	}
	
	/**
	 * The create method must throw a DAOException if the 
	 * given Customer has a non-null ID.
	 * @throws DAOException if the given Customer has a non-null id.
	 */
	@Override
	public Customer create(Customer customer) throws SQLException, DAOException
	{
		if(customer.getId() != null){
			throw new DAOException("Trying to create Customer with non-NULL ID");
		}
		em.getTransaction().begin();
		em.persist(customer);
		em.getTransaction().commit();
		return customer;
	}

	@Override
	public Customer retrieve(Long id) 
	{
		em.getTransaction().begin();
		Customer customer = em.find(Customer.class, id);
		em.getTransaction().commit();
		return customer;
	}

	@Override
	public Customer update(Customer c1) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		Customer customer = em.find(Customer.class, c1.getId());
		customer = c1;
		em.getTransaction().commit();
		return customer;
	}

	@Override
	public void delete(Long id) throws SQLException, DAOException
	{
		em.getTransaction().begin();
		Customer customer = em.find(Customer.class, id);
		em.remove(customer);
		em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> retrieveByZipCode(String zipCode) throws SQLException, DAOException
	{
		List<Customer> cust_list = em.createQuery(
				"from Customer as c where c.address.zipcode = :zip")
				.setParameter("zip", zipCode)
				.getResultList();
		return cust_list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> retrieveByDOB(Date startDate, Date endDate) throws SQLException, DAOException
	{
		List<Customer> cust_list = em.createQuery(
				"from Customer where dob between :start and :end")
				.setParameter("start", startDate)
				.setParameter("end", endDate)
				.getResultList();
		return cust_list;
	}
}
