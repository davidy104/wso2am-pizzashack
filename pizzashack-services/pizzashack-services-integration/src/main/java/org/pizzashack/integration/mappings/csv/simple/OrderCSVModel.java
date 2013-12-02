package org.pizzashack.integration.mappings.csv.simple;

import java.io.Serializable;
import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.commons.lang3.builder.ToStringBuilder;

@CsvRecord(separator = "\\|", crlf = "MAC")
public class OrderCSVModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@DataField(pos = 6)
	private Long orderId;

	@DataField(pos = 7)
	private String pizzaType;

	@DataField(pos = 8)
	private Integer quantity;

	@DataField(pos = 9)
	private String delivered;

	@DataField(pos = 10, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderTime;

	public void updateDelivered(String delivered) {
		this.delivered = delivered;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

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
			String delivered, Date orderTime) {
		return new Builder(pizzaType, quantity, delivered, orderTime);
	}

	public static class Builder {

		private OrderCSVModel built;

		public Builder(String pizzaType, int quantity, String delivered,
				Date orderTime) {
			built = new OrderCSVModel();
			built.pizzaType = pizzaType;
			built.quantity = quantity;
			built.delivered = delivered;
			built.orderTime = orderTime;
		}

		public OrderCSVModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
