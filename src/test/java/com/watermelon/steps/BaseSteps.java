package com.watermelon.steps;

import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;
import com.watermelon.core.di.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseSteps {

	public static final String BASE_MESSAGENAME = "messages";

	@Inject
	protected WebDriver driver;

	@Inject
	protected JavascriptExecutor jsExecutor;

	@Inject
	protected WebDriverWait waiter;

	@Inject
	protected Actions actions;

	protected Predicate<WebElement> checkElement;

	@Inject
	protected Configuration config;

	@Inject
	protected ResourceBundle bundle;

	protected BaseSteps() {
		// log.debug("BaseTest");
		checkElement = (element) -> (Boolean) ((JavascriptExecutor) driver)
				.executeScript("return arguments[0].validity.valid;", element);
	}
}
