package net.pizzashack.ds;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.core.MediaType;

import net.pizzashack.config.Wso2amConfig;
import net.pizzashack.data.Token;
import net.pizzashack.util.PizzashackJSONTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;

@Component
public class JerseyClientSupport {

	protected Client client;

	public static String AUTH_HEADER = "Authorization";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JerseyClientSupport.class);

	@Autowired
	protected Wso2amConfig wso2apiConfig;

	@PostConstruct
	private void init() {
		com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		client = Client.create(config);
		client.setConnectTimeout(10000);
		client.setReadTimeout(10000);
		client.addFilter(new LoggingFilter(System.out));
	}

	public Token getToken(String username, String password) throws Exception {
		String applicationToken = wso2apiConfig.getConsumerKey() + ":"
				+ wso2apiConfig.getConsumerSecret();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		applicationToken = "Basic "
				+ base64Encoder.encode(applicationToken.getBytes()).trim();

		LOGGER.debug("applicationToken:{}", applicationToken);

		String payload = "grant_type=password&username=" + username
				+ "&password=" + password;

		WebResource webResource = client.resource(wso2apiConfig.getTokenUrl());

		ClientResponse response = webResource
				.header("Authorization", applicationToken)
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, payload);

		LOGGER.debug("respStatus: ", response.getStatus());

		if (response.getStatus() != 200) {
			return null;
		}
		String responseStr = getResponsePayload(response);
		LOGGER.debug("token json: {}", responseStr);
		return PizzashackJSONTransformer.getAccessToken(responseStr);

	}

	protected String getResponsePayload(ClientResponse response)
			throws IOException {
		StringBuffer buffer = new StringBuffer();
		InputStream in = null;
		try {
			if (response.getEntityInputStream() != null) {
				in = response.getEntityInputStream();
				int length;
				byte[] tmp = new byte[2048];
				while ((length = in.read(tmp)) != -1) {
					buffer.append(new String(tmp, 0, length));
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return buffer.toString();
	}

	@PreDestroy
	public void cleanup() {
		client.destroy();
	}

}
