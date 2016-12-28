package cs4347.hibernateProject.ecomm.entity;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="Address", catalog="simple_company", uniqueConstraints={})
public class Address implements Serializable
{
	private Long id;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="addr_id", unique=true, nullable=false, insertable=true, updatable=true)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Column(name="address1", length=100, nullable=false)
	public String getAddress1()
	{
		return address1;
	}

	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}

	@Column(name="address2", length=100, nullable=true)
	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	@Column(name="city", length=100, nullable=false)
	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	@Column(name="state", length=100, nullable=false)
	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	@Column(name="zipcode", length=100, nullable=false)
	public String getZipcode()
	{
		return zipcode;
	}

	public void setZipcode(String zipcode)
	{
		this.zipcode = zipcode;
	}

}
