package com.watermelon.steps.pages;

import org.openqa.selenium.WebDriver;

import com.google.inject.Inject;
import com.watermelon.core.WebPage;

public abstract class SauceLabsPage extends WebPage {
	@Inject
	protected Header header;

	public SauceLabsPage(WebDriver driver) {
		super(driver);
	}

}
