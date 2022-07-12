package com.watermelon.steps.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.watermelon.core.WebPage;

public class SideMenu extends WebPage {

	@FindBy(id = "react-burger-cross-btn")
	@CacheLookup
	private WebElement closeMenu;

	@FindBy(id = "inventory_sidebar_link")
	@CacheLookup
	private WebElement allItems;

	@FindBy(id = "about_sidebar_link")
	@CacheLookup
	private WebElement about;

	@FindBy(id = "logout_sidebar_link")
	@CacheLookup
	private WebElement logout;

	@FindBy(id = "reset_sidebar_link")
	@CacheLookup
	private WebElement resetAppState;

	public SideMenu(WebDriver driver) {
		super(driver);
	}

	@Override
	public WebElement title() {
		return allItems;
	}

}
