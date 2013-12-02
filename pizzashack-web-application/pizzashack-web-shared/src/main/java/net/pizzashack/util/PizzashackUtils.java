package net.pizzashack.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PizzashackUtils {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackUtils.class);

	public static String convertToString(InputStream is) {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(is, writer, "utf-8");
		} catch (IOException e) {
			LOGGER.debug("convertToString errors:{}", e);
			return null;
		}
		return writer.toString();
	}
}
