package org.pizzashack.ds;

import java.util.List;

import org.pizzashack.data.MenuItemDTO;

/**
 * 
 * @author david
 * 
 */
public interface PizzaMenuDS {

	MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) throws Exception;

	List<MenuItemDTO> getAllMenuItems() throws Exception;
}
