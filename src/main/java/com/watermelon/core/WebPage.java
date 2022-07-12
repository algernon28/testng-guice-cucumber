package com.watermelon.core;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;
import com.watermelon.core.di.Configuration;

public abstract class WebPage {

	protected WebDriver driver;

	@Inject
	protected WebDriverWait wait;

	@Inject
	protected JavascriptExecutor jsExecutor;

	@Inject
	protected Configuration configuration;

	protected WebPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		if (!this.isLoaded()) {
			String msg = String.format("Could not load page %s", getClass().getSimpleName());
			throw new PageNotLoadedException(msg);
		}
	}

	public abstract WebElement title();

	public boolean isLoaded() {
		WebElement pageTitle = title();
		waitUntilVisible(pageTitle);
		return pageTitle.isDisplayed();
	}

	public void navigateTo(URL url) {
		driver.navigate().to(url);
	}

	public void navigateTo(String url) {
		driver.navigate().to(url);
	}

	public WebElement waitUntilVisible(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	protected void click(WebElement element) {
		waitUntilVisible(element).click();
	}

	protected void clear(WebElement element) {
		waitUntilVisible(element).clear();
	}

	protected void clearAll(List<WebElement> elements) {
		elements.stream().forEach(e -> e.clear());
	}

	protected void clearAll(WebElement... elements) {
		clearAll(Arrays.asList(elements));
	}

	protected void type(WebElement element, String value) {
		clear(element);
		waitUntilVisible(element).sendKeys(value);
	}

	protected void addText(WebElement element, String value) {
		waitUntilVisible(element).sendKeys(value);
	}
}
