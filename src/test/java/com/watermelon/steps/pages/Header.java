package com.watermelon.steps.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.watermelon.core.WebPage;

public class Header extends WebPage {

	@FindBy(id = "react-burger-menu-btn")
	@CacheLookup
	private WebElement openMenu;

	@FindBy(className = "shopping_cart_link")
	@CacheLookup
	private WebElement shoppingCart;

	@FindBy(className = "shopping_cart_badge")
	private WebElement cartBadge;

	@FindBy(className = "app_logo")
	private WebElement appLogo;

	public Header(WebDriver driver) {
		super(driver);
	}

	public void openShoppingCart() {
		waitUntilVisible(shoppingCart);
		shoppingCart.click();
	}

	public int getBadgeCount() {
		waitUntilVisible(cartBadge);
		String count = cartBadge.getText();
		return Integer.parseInt(count);
	}

	@Override
	public WebElement title() {
		waitUntilVisible(appLogo);
		return appLogo;
	}

}
