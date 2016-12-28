package cs4347.hibernateProject.ecomm.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="purchase", catalog="simple_company", uniqueConstraints={})
public class Purchase implements Serializable
{
	private Long id;
	private Date purchaseDate;
	private double purchaseAmount;
	private Customer customer;
	private Product product;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="purch_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Column(name="purchasedate", nullable=false)
	public Date getPurchaseDate()
	{
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}

	@Column(name="purchaseamount", nullable=false)
	public double getPurchaseAmount()
	{
		return purchaseAmount;
	}

	public void setPurchaseAmount(double purchaseAmount)
	{
		this.purchaseAmount = purchaseAmount;
	}

	@OneToOne(cascade={}, fetch=FetchType.EAGER)
	@JoinColumn(name="cust_id", nullable = false, insertable=true, updatable=true)
	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	@OneToOne(cascade={}, fetch=FetchType.LAZY)
	@JoinColumn(name="prod_id", nullable = false, insertable=true, updatable=true)
	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

}
