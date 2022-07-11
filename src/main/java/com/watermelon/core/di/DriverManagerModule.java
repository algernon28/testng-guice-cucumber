package com.watermelon.core.di;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Makes Make {@link DriverManager} available to Guice
 *
 * @author AM
 *
 */

public class DriverManagerModule extends AbstractModule {

	private static final Logger logger = LoggerFactory.getLogger(DriverManagerModule.class);

	private static final Duration DefaultTimeout = Duration.ofSeconds(30);
	private static final Duration DefaultTimeoutInterval = Duration.ofMillis(500);

	@Override
	protected void configure() {
		logger.debug("Configuring {}", getClass().getSimpleName());
		bind(WebDriver.class).toProvider(DriverManager.class);
	}

	@Provides
	public JavascriptExecutor getJavascriptExecutor(WebDriver driver) {
		return (JavascriptExecutor) driver;
	}
	
	@Provides
	public Actions getActions(WebDriver driver) {
		return new Actions(driver);
	}
	
	@Provides
	public WebDriverWait getWait(WebDriver driver) {
		return new WebDriverWait(driver, DefaultTimeout, DefaultTimeoutInterval);
		//return new WebDriverWait(driver, DefaultTimeout.getSeconds(), DefaultTimeoutInterval.getSeconds());
	}
}
