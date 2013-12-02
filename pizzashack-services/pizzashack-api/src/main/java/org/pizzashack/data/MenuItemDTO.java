package org.pizzashack.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class MenuItemDTO implements Serializable {
	private Long menuItemId;
	private String name;
	private String description;
	private String price;
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
		private MenuItemDTO built;

		public Builder(String name, String description, String price,
				String icon) {
			built = new MenuItemDTO();
			built.name = name;
			built.description = description;
			built.price = price;
			built.icon = icon;
		}

		public MenuItemDTO build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
