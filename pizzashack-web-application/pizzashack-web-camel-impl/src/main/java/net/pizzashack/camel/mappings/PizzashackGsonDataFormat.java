package net.pizzashack.camel.mappings;

import java.util.List;

import net.pizzashack.data.Pizza;
import net.pizzashack.data.Token;
import net.pizzashack.data.dto.OrderDto;

import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.spi.DataFormat;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingPolicy;

public class PizzashackGsonDataFormat {

	private List<ExclusionStrategy> exclusionStrategies;

	private GsonDataFormat orderGsonDataFormat;

	private GsonDataFormat tokenGsonDataFormat;

	private GsonDataFormat pizzaGsonDataFormat;

	private GsonDataFormat pizzaListDataFormat;

	private GsonDataFormat orderListDataFormat;

	public PizzashackGsonDataFormat(List<ExclusionStrategy> exclusionStrategies) {
		this.exclusionStrategies = exclusionStrategies;
	}

	public void initialize() {
		orderGsonDataFormat = new GsonDataFormat();
		orderGsonDataFormat.setUnmarshalType(OrderDto.class);
		orderGsonDataFormat.setExclusionStrategies(exclusionStrategies);
		orderGsonDataFormat.setPrettyPrinting(true);

		tokenGsonDataFormat = new GsonDataFormat();
		tokenGsonDataFormat.setUnmarshalType(Token.class);
		tokenGsonDataFormat
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		tokenGsonDataFormat.setExclusionStrategies(exclusionStrategies);
		tokenGsonDataFormat.setPrettyPrinting(true);

		pizzaGsonDataFormat = new GsonDataFormat();
		pizzaGsonDataFormat.setUnmarshalType(Pizza.class);
		pizzaGsonDataFormat.setExclusionStrategies(exclusionStrategies);
		pizzaGsonDataFormat.setPrettyPrinting(true);

		pizzaListDataFormat = new PizzaListDataFormat();
		orderListDataFormat = new OrderListDataFormat();
	}

	public GsonDataFormat getOrderGsonDataFormat() {
		return orderGsonDataFormat;
	}

	public GsonDataFormat getTokenGsonDataFormat() {
		return tokenGsonDataFormat;
	}

	public GsonDataFormat getPizzaGsonDataFormat() {
		return pizzaGsonDataFormat;
	}

	public DataFormat getPizzaListDataFormat() {
		return pizzaListDataFormat;
	}

	public GsonDataFormat getOrderListDataFormat() {
		return orderListDataFormat;
	}

}
