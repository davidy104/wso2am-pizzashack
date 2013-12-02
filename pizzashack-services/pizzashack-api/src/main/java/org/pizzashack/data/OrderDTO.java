package org.pizzashack.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class OrderDTO implements Serializable {

	private Long orderId;

	private String pizzaType;

	private int quantity;

	private Long custId;

	private String customerName;

	private String customerEmail;

	private String creditCardNumber;

	private String address;

	private String delivered = "N";

	private String orderTime;

	public String getPizzaType() {
		return pizzaType;
	}

	public void setPizzaType(String pizzaType) {
		this.pizzaType = pizzaType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDelivered() {
		return delivered;
	}

	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public static Builder getBuilder(Long orderId, String pizzaType,
			int quantity, String customerName, String address,
			String creditCardNumber, String customerEmail, String delivered) {
		return new Builder(orderId, pizzaType, quantity, customerName, address,
				creditCardNumber, customerEmail, delivered);
	}

	public static Builder getBuilder(String pizzaType, int quantity,
			String customerName, String address, String creditCardNumber,
			String customerEmail) {
		return new Builder(pizzaType, quantity, customerName, address,
				creditCardNumber, customerEmail);
	}

	public static class Builder {

		private OrderDTO built;

		public Builder(Long orderId, String pizzaType, int quantity,
				String customerName, String address, String creditCardNumber,
				String customerEmail, String delivered) {
			built = new OrderDTO();
			built.orderId = orderId;
			built.pizzaType = pizzaType;
			built.quantity = quantity;
			built.customerName = customerName;
			built.address = address;
			built.creditCardNumber = creditCardNumber;
			built.delivered = delivered;
			built.customerEmail = customerEmail;
		}

		public Builder(String pizzaType, int quantity, String customerName,
				String address, String creditCardNumber, String customerEmail) {
			built = new OrderDTO();
			built.pizzaType = pizzaType;
			built.quantity = quantity;
			built.customerName = customerName;
			built.address = address;
			built.creditCardNumber = creditCardNumber;
			built.customerEmail = customerEmail;
		}

		public OrderDTO build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
