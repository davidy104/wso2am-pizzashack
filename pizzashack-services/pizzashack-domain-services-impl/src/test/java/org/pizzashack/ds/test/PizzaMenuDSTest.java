package org.pizzashack.ds.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pizzashack.data.MenuItemDTO;
import org.pizzashack.ds.PizzaMenuDS;
import org.pizzashack.ds.config.InfrastructureContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InfrastructureContextConfiguration.class })
public class PizzaMenuDSTest {

	@Autowired
	private PizzaMenuDS pizzaMenuDs;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzaMenuDSTest.class);

	@Test
	public void testGetAllMenus() throws Exception {
		List<MenuItemDTO> menuList = pizzaMenuDs.getAllMenuItems();
		assertNotNull(menuList);
		for (MenuItemDTO menuItem : menuList) {
			LOGGER.debug("menuItem:{}", menuItem);
		}
	}

}
