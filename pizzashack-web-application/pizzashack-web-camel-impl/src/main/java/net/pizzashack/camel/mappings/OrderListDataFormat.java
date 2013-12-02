package net.pizzashack.camel.mappings;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;

import net.pizzashack.data.dto.OrderDto;

import org.apache.camel.Exchange;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.util.IOHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OrderListDataFormat extends GsonDataFormat {

	@Override
	public void marshal(Exchange exchange, Object graph, OutputStream stream)
			throws Exception {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(graph);
		stream.write(jsonStr.getBytes());
	}

	@Override
	public Object unmarshal(Exchange exchange, InputStream stream)
			throws Exception {
		List<OrderDto> orderList = null;
		Type listType = new TypeToken<List<OrderDto>>() {
		}.getType();
		Gson gson = new Gson();
		BufferedReader reader = IOHelper
				.buffered(new InputStreamReader(stream));
		orderList = gson.fromJson(reader, listType);
		return orderList;
	}

}
