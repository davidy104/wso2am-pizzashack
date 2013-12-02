package net.pizzashack.camel.mappings;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import net.pizzashack.data.Pizza;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Message;
import org.apache.camel.util.IOHelper;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class PizzaListTransformer
    implements Expression {

  @SuppressWarnings("unchecked")
  @Override
  public <T> T evaluate(Exchange exchange, Class<T> type) {
    Message in = exchange.getIn();
    List<Pizza> pizzaList = null;
    InputStream respStream = in.getBody(InputStream.class);
    Type listType = new TypeToken<List<Pizza>>() {
    }.getType();
    Gson gson = new Gson();
    BufferedReader reader = IOHelper.buffered(new InputStreamReader(respStream));
    pizzaList = gson.fromJson(reader, listType);
    return (T) pizzaList;
  }

}
