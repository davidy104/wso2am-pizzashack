package org.pizzashack.ds.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.pizzashack.data.MenuItem;
import org.pizzashack.data.MenuItemDTO;
import org.pizzashack.data.repository.MenuItemRepository;
import org.pizzashack.ds.PizzaMenuDS;
import org.pizzashack.ds.converter.MenuItemConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("pizzaMenuDS")
@Transactional(readOnly = true)
public class PizzaMenuDSImpl implements PizzaMenuDS {
	@Resource
	private MenuItemRepository menuItemRepository;

	@Resource
	private MenuItemConverter menuItemConverter;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzaMenuDSImpl.class);

	@Override
	@Transactional(readOnly = false)
	public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) throws Exception {
		LOGGER.debug("createMenuItem start:{}", menuItemDTO);
		MenuItem menuItem = menuItemConverter.convertTo(menuItemDTO);
		MenuItemDTO result = null;
		menuItem = menuItemRepository.save(menuItem);
		result = this.menuItemConverter.convertFrom(menuItem);
		LOGGER.debug("createMenuItem end:{}", result);
		return result;
	}

	@Override
	public List<MenuItemDTO> getAllMenuItems() throws Exception {
		LOGGER.debug("getAllMenuItems start:{}");
		List<MenuItemDTO> dtoList = null;
		List<MenuItem> menuItemList = menuItemRepository.findAll();
		if (menuItemList != null && menuItemList.size() > 0) {
			dtoList = new ArrayList<MenuItemDTO>();
			for (MenuItem menuItem : menuItemList) {
				dtoList.add(menuItemConverter.convertFrom(menuItem));
			}
		}

		return dtoList;
	}

}
