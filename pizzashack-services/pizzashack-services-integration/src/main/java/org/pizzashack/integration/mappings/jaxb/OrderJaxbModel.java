package org.pizzashack.integration.mappings.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderJaxbModel implements Serializable {

	@XmlElement(name = "pizzaType", required = true)
	private String pizzaType;

	private Integer quantity;

	private String customerName;

	private String delivered;

	public String getPizzaType() {
		return pizzaType;
	}

	public void setPizzaType(String pizzaType) {
		this.pizzaType = pizzaType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDelivered() {
		return delivered;
	}

	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}

	public void updateAsDelivered() {
		this.delivered = "Y";
	}

	public static Builder getBuilder(String pizzaType, int quantity,
			String customerName, String delivered) {
		return new Builder(pizzaType, quantity, customerName, delivered);
	}

	public static class Builder {

		private OrderJaxbModel built;

		public Builder(String pizzaType, int quantity, String customerName,
				String delivered) {
			built = new OrderJaxbModel();
			built.pizzaType = pizzaType;
			built.quantity = quantity;
			built.customerName = customerName;
			built.delivered = delivered;
		}

		public OrderJaxbModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
