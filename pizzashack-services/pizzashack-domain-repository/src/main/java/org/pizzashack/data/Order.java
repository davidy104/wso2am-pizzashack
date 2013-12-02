package org.pizzashack.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_ORDER")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID", insertable = false, updatable = false)
	@JsonExclude
	private Long orderId;

	@Column(name = "PIZZA_TYPE")
	private String pizzaType;

	@Column(name = "QUANTITY")
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUST_ID", referencedColumnName = "CUST_ID")
	private Customer customer;

	@Column(name = "DELIVERED")
	private String delivered = "N";

	@Column(name = "ORDER_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderTime;

	public void update(String pizzaType, Integer quantity, String delivered) {
		if (!StringUtils.isEmpty(pizzaType)) {
			this.pizzaType = pizzaType;
		}
		if (!StringUtils.isEmpty(delivered)) {
			this.delivered = delivered;
		}
		if (quantity != null) {
			this.quantity = quantity;
		}
	}

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public static Builder getBuilder(String pizzaType, int quantity,
			Customer customer, String delivered) {
		return new Builder(pizzaType, quantity, customer, delivered);
	}

	public static class Builder {

		private Order built;

		public Builder(String pizzaType, int quantity, Customer customer,
				String delivered) {
			built = new Order();
			built.pizzaType = pizzaType;
			built.quantity = quantity;
			built.customer = customer;
			built.delivered = delivered;
			built.orderTime = new Date();
		}

		public Order build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("orderId", orderId).append("pizzaType", pizzaType)
				.append("quantity", quantity).append("delivered", delivered)
				.append("orderTime", orderTime).toString();
	}

}
