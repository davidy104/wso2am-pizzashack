package net.pizzashack.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Pizza {
	private String name;
	private String description;
	private String imageUrl;
	private double price;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static Builder getBuilder(String name, String description,
			String imageUrl, double price) {
		return new Builder(name, description, imageUrl, price);
	}

	public static class Builder {

		private Pizza built;

		public Builder(String name, String description, String imageUrl,
				double price) {
			built = new Pizza();
			built.name = name;
			built.description = description;
			built.imageUrl = imageUrl;
			built.price = price;
		}

		public Pizza build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
