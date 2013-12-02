package org.pizzashack.integration.mappings.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class CustomerJaxbModel implements Serializable {

	@XmlElement(name = "customerName", required = true)
	private String customerName;

	@XmlElement(name = "email", required = true)
	private String email;

	private String address;

	@XmlElementWrapper(name = "orders")
	@XmlElement(name = "order", required = false)
	private List<OrderJaxbModel> orders;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void addOrder(OrderJaxbModel order) {
		if (orders == null) {
			orders = new ArrayList<OrderJaxbModel>();
		}
		orders.add(order);
	}

	public List<OrderJaxbModel> getOrders() {
		return orders;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static Builder getBuilder(String customerName, String address) {
		return new Builder(customerName, address);
	}

	public static Builder getBuilder(String customerName, String address,
			String email) {
		return new Builder(customerName, address, email);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		CustomerJaxbModel other = (CustomerJaxbModel) object;

		return new EqualsBuilder().append(this.getEmail(), other.getEmail())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getEmail()).toHashCode();
	}

	public static class Builder {

		private CustomerJaxbModel built;

		public Builder(String customerName, String address) {
			built = new CustomerJaxbModel();
			built.customerName = customerName;
			built.address = address;
		}

		public Builder(String customerName, String address, String email) {
			built = new CustomerJaxbModel();
			built.customerName = customerName;
			built.address = address;
			built.email = email;
		}

		public CustomerJaxbModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
