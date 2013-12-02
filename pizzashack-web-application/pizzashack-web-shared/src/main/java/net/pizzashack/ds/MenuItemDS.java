package net.pizzashack.ds;

import java.util.List;

import net.pizzashack.data.Pizza;

public interface MenuItemDS {
	List<Pizza> getAvailablePizzaList(String accessToken) throws Exception;
}
