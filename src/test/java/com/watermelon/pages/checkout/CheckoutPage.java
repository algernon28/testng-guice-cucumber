package com.watermelon.pages.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;
import com.watermelon.pages.SauceLabsPage;

public abstract class CheckoutPage extends SauceLabsPage {

	@Inject
	private CheckoutButtons checkoutButtons;

	@Inject
	public CheckoutPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
	}

	public void continueCheckout() {
		checkoutButtons.continueCheckout();
	}

	public void cancelCheckout() {
		checkoutButtons.cancelCheckout();
	}
}
