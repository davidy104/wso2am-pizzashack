package org.pizzashack.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CUSTOMER")
public class Customer
    implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "CUST_ID", insertable = false, updatable = false)
  @JsonExclude
  private Long custId;

  @Column(name = "CUSTOMER_NAME")
  private String customerName;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "CREDITCARD_NUMBER")
  private String creditCardNumber;

  @Column(name = "ADDRESS")
  private String address;

  @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST }, mappedBy = "customer")
  private List<Order> orders;

  public Long getCustId() {
    return custId;
  }

  public void setCustId(Long custId) {
    this.custId = custId;
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

  public void addOrder(Order order) {
    if (orders == null) {
      orders = new ArrayList<Order>();
    }
    orders.add(order);
  }

  public List<Order> getOrders() {
    return orders;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public static Builder getBuilder(String customerName,
      String creditCardNumber, String address) {
    return new Builder(customerName, creditCardNumber, address);
  }

  public static Builder getBuilder(String customerName,
      String creditCardNumber, String address, String email) {
    return new Builder(customerName, creditCardNumber, address, email);
  }

  public static Builder getBuilder(String customerName,
      String creditCardNumber) {
    return new Builder(customerName, creditCardNumber);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Customer other = (Customer) object;

    return new EqualsBuilder().append(this.getEmail(), other.getEmail())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(getEmail()).toHashCode();
  }

  public static class Builder {

    private Customer built;

    public Builder(String customerName, String creditCardNumber,
        String address) {
      built = new Customer();
      built.customerName = customerName;
      built.creditCardNumber = creditCardNumber;
      built.address = address;
    }

    public Builder(String customerName, String creditCardNumber,
        String address, String email) {
      built = new Customer();
      built.customerName = customerName;
      built.creditCardNumber = creditCardNumber;
      built.address = address;
      built.email = email;
    }

    public Builder(String customerName, String creditCardNumber) {
      built = new Customer();
      built.customerName = customerName;
      built.creditCardNumber = creditCardNumber;
    }

    public Customer build() {
      return built;
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
        .append("custId", custId).append("customerName", customerName)
        .append("email", email)
        .append("creditCardNumber", creditCardNumber)
        .append("address", address).toString();
  }

}
