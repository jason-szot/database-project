package cs4347.hibernateProject.ecomm.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
@Table(name="Customer", catalog="simple_company", uniqueConstraints={} )
public class Customer implements Serializable
{
	
	
	private Long id;
	private String firstName;
	private String lastName;
	private Character gender;
	private Date dob;
	private String email;
	private Address address;
	private CreditCard creditCard;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cust_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	@Column(name="firstName", length = 100, nullable = false)
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	@Column(name="lastName", length = 100, nullable = false)
	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	@Column(name="gender", nullable=false)
	public Character getGender()
	{
		return gender;
	}

	public void setGender(Character gender)
	{
		this.gender = gender;
	}

	@Column(name="dob", nullable=false)
	public Date getDob()
	{
		return dob;
	}

	public void setDob(Date dob)
	{
		this.dob = dob;
	}

	@Column(name="email", nullable=false)
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	@OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinColumn(name="addr_id", nullable=true, insertable=true, unique=true)
	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	@OneToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name="cc_id", nullable=true, insertable=true, unique=true)
	public CreditCard getCreditCard()
	{
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard)
	{
		this.creditCard = creditCard;
	}
}
