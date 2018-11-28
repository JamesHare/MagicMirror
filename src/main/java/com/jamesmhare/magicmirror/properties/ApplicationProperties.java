package com.jamesmhare.magicmirror.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This class serves as a helper class to retrieve properties set in the
 * config.properties file.
 * 
 * @author James Hare
 */
public class ApplicationProperties {
	private Properties properties;
	private InputStream input;
	private static final Logger LOGGER = Logger.getLogger(ApplicationProperties.class);

	public ApplicationProperties() {
		properties = new Properties();
		input = null;
	}

	/**
	 * Returns the value associated with the given key from the properties file.
	 * 
	 * @param key = A {@link String} specifying the key.
	 * @return the value associated with the key.
	 */
	public String getProperty(String key) {
		StringBuilder value = new StringBuilder("");
		try {
			input = new FileInputStream("../src/main/resources/properties/config.properties");
			properties.load(input);
			value.append(properties.getProperty(key));
		} catch (IOException exception) {
			LOGGER.error(exception.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException exception) {
					LOGGER.error(exception.getMessage());
				}
			}
		}
		return value.toString();
	}

}
