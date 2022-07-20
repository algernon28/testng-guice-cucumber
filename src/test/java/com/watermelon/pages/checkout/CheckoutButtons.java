package com.watermelon.pages.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.watermelon.pages.SauceLabsSection;

public class CheckoutButtons extends SauceLabsSection {
	@FindBy(className = "checkout_buttons")
	@CacheLookup
	private WebElement title;

	@FindBy(id = "continue")
	@CacheLookup
	private WebElement btnContinue;

	@FindBy(id = "cancel")
	@CacheLookup
	private WebElement btnCancel;

	public CheckoutButtons(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
	}

	public void continueCheckout() {
		waitUntilVisible(btnContinue);
		btnContinue.click();
	}

	public void cancelCheckout() {
		waitUntilVisible(btnCancel);
		btnCancel.click();
	}

	@Override
	public WebElement getTitle() {
		waitUntilVisible(title);
		return title;
	}

	@Override
	public boolean isLoaded() {
		WebElement pageTitle = getTitle();
		waitUntilVisible(pageTitle);
		return pageTitle.isDisplayed();
	}

}
