package org.pizzashack.integration.mappings.csv.simple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.OneToMany;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@CsvRecord(separator = "\\|", crlf = "MAC")
public class CustomerCSVModel
    implements Serializable {

  @DataField(pos = 1)
  private Long custId;

  @DataField(pos = 2)
  private String customerName;

  @DataField(pos = 3)
  private String email;

  @DataField(pos = 4)
  private String creditCardNumber;

  @DataField(pos = 5)
  private String address;

  @OneToMany
  private List<OrderCSVModel> orders;

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

  public void addOrder(OrderCSVModel order) {
    if (orders == null) {
      orders = new ArrayList<OrderCSVModel>();
    }
    orders.add(order);
  }

  public List<OrderCSVModel> getOrders() {
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
    CustomerCSVModel other = (CustomerCSVModel) object;

    return new EqualsBuilder().append(this.getEmail(), other.getEmail())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(getEmail()).toHashCode();
  }

  public static class Builder {

    private CustomerCSVModel built;

    public Builder(String customerName, String creditCardNumber,
        String address) {
      built = new CustomerCSVModel();
      built.customerName = customerName;
      built.creditCardNumber = creditCardNumber;
      built.address = address;
    }

    public Builder(String customerName, String creditCardNumber,
        String address, String email) {
      built = new CustomerCSVModel();
      built.customerName = customerName;
      built.creditCardNumber = creditCardNumber;
      built.address = address;
      built.email = email;
    }

    public Builder(String customerName, String creditCardNumber) {
      built = new CustomerCSVModel();
      built.customerName = customerName;
      built.creditCardNumber = creditCardNumber;
    }

    public CustomerCSVModel build() {
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
