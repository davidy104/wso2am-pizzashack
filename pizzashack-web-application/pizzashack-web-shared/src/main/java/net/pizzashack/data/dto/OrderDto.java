package net.pizzashack.data.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class OrderDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String address;

	@NotEmpty
	private String pizzaType;

	@NotEmpty
	private String customerName;

	@NotEmpty
	private String customerEmail;

	private Integer quantity;

	@NotEmpty
	private String creditCardNumber;

	private String delivered;

	private Long orderId;

	private Long custId;

	public OrderDto() {

	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public OrderDto(String address, String pizzaType, String customerName,
			String customerEmail, String quantity, String creditCardNumber) {
		this.address = address;
		this.pizzaType = pizzaType;
		this.customerEmail = customerEmail;
		this.customerName = customerName;
		this.quantity = Integer.valueOf(quantity).intValue();
		this.creditCardNumber = creditCardNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPizzaType() {
		return pizzaType;
	}

	public void setPizzaType(String pizzaType) {
		this.pizzaType = pizzaType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getDelivered() {
		return delivered;
	}

	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}

	public static Builder getBuilder(String address, String pizzaType,
			String customerName, String customerID, Integer quantity,
			String creditCardNumber) {
		return new Builder(address, pizzaType, customerName, customerID,
				quantity, creditCardNumber);
	}

	public static class Builder {

		private OrderDto built;

		public Builder(String address, String pizzaType, String customerName,
				String customerEmail, Integer quantity, String creditCardNumber) {
			built = new OrderDto();
			built.address = address;
			built.pizzaType = pizzaType;
			built.customerName = customerName;
			built.customerEmail = customerEmail;
			built.quantity = quantity;
			built.creditCardNumber = creditCardNumber;
		}

		public OrderDto build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
