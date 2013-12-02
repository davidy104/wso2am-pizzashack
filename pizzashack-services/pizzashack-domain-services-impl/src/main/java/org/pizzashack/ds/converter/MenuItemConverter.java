package org.pizzashack.ds.converter;

import org.pizzashack.ConvertException;
import org.pizzashack.data.MenuItem;
import org.pizzashack.data.MenuItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("menuItemConverter")
public class MenuItemConverter implements
		GeneralConverter<MenuItemDTO, MenuItem> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MenuItemConverter.class);

	@Override
	public MenuItemDTO convertFrom(MenuItem model,
			Object... additionalSourceObj) throws ConvertException {
		LOGGER.debug("convert to DTO start:{}", model);
		MenuItemDTO result = MenuItemDTO.getBuilder(model.getName(),
				model.getDescription(), model.getPrice(), model.getIcon())
				.build();
		result.setMenuItemId(model.getMenuItemId());
		LOGGER.debug("convert to DTO end:{}", result);
		return result;
	}

	@Override
	public MenuItem convertTo(MenuItemDTO dto, Object... additionalSourceObj)
			throws ConvertException {
		LOGGER.debug("convert to Model start:{}", dto);
		MenuItem item = MenuItem.getBuilder(dto.getName(),
				dto.getDescription(), dto.getPrice(), dto.getIcon()).build();
		if (dto.getMenuItemId() != null) {
			item.setMenuItemId(dto.getMenuItemId());
		}
		LOGGER.debug("convert to Model end:{}", item);
		return item;
	}

}
