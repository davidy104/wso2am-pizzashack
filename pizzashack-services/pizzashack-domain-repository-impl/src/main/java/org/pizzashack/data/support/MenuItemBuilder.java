package org.pizzashack.data.support;

import org.pizzashack.data.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemBuilder extends EntityBuilder<MenuItem> {

	@Override
	void initProduct() {
	}

	public MenuItemBuilder create(String name, String description,
			String price, String icon) {
		this.product = MenuItem.getBuilder(name, description, price, icon)
				.build();
		return this;
	}

	@Override
	MenuItem assembleProduct() {
		return this.product;
	}

}
