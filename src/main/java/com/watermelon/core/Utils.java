package com.watermelon.core;

import java.io.InputStream;
import java.util.Optional;

import org.testng.xml.XmlTest;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

import io.github.bonigarcia.wdm.config.Architecture;
import io.github.bonigarcia.wdm.config.OperatingSystem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
	public static final String DEFAULT_LANG = "en";
	public static final Architecture DEFAULT_ARCH = Architecture.X64;
	public static final OperatingSystem DEFAULT_OS = OperatingSystem.WIN;
	public static final String DEFAULT_COUNTRY = "US";
	public static final String DEFAULT_BROWSER = "Chrome";
	public static final String DEFAULT_VERSION = "latest";

	private Utils() {

	}

	public static <T> T loadYaml(Class<T> clazz, String fileName) {
		Constructor constructor = new Constructor(clazz);
		PropertyUtils props = new PropertyUtils();
		props.setSkipMissingProperties(true);
		Representer rep = new Representer();
		rep.setPropertyUtils(props);
		Yaml yamlConfig = new Yaml(constructor, rep);
		InputStream is = clazz.getClassLoader().getResourceAsStream(fileName);
		return yamlConfig.load(is);
	}

	public static Optional<String> lookupParameter(String key, String fallback, XmlTest context) {
		String param = context.getParameter(key);
		String result = (param != null) ? param : fallback;
		log.debug("Returning property: {}", result);
		return Optional.ofNullable(result);
	}

	public static Optional<String> lookupParameter(String key, XmlTest context) {
		return lookupParameter(key, null, context);
	}

	public static Optional<String> lookupProperty(String key, String fallback) {
		String result = (System.getProperty(key) != null) ? System.getProperty(key) : fallback;
		log.debug("Returning property: {}", result);
		return Optional.ofNullable(result);
	}
}
