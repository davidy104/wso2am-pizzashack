package org.pizzashack.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_MENU_ITEM")
public class MenuItem
    implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "MENU_ITEM_ID", insertable = false, updatable = false)
  private Long menuItemId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "PRICE")
  private String price;

  @Column(name = "ICON")
  private String icon;

  public Long getMenuItemId() {
    return menuItemId;
  }

  public void setMenuItemId(Long menuItemId) {
    this.menuItemId = menuItemId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public static Builder getBuilder(String name, String description,
      String price, String icon) {
    return new Builder(name, description, price, icon);
  }

  public static class Builder {
    private MenuItem built;

    public Builder(String name, String description, String price,
        String icon) {
      built = new MenuItem();
      built.name = name;
      built.description = description;
      built.price = price;
      built.icon = icon;
    }

    public MenuItem build() {
      return built;
    }
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
