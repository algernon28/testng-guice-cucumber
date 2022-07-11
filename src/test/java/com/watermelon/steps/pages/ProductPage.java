package com.watermelon.steps.pages;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.google.inject.Inject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductPage extends BasePage {

	public static String PROBLEM_SOURCE_IMAGEFILE = "/static/media/sl-404.168b1cce.jpg";

	@Inject
	public ProductPage(WebDriver driver) {
		super(driver);
	}

	@FindBys({ @FindBy(xpath = "//span[@class='title']"), @FindBy(xpath = "//span[normalize-space()='Products']") })
	private WebElement title;

	@FindBy(className = "inventory_item")
	private List<WebElement> items;
	
	public List<InventoryItem> getItems(){
		return items.stream().map(el -> new InventoryItem(el)).toList();		
	}

	@Override
	public WebElement title() {
		waitUntilVisible(title);
		return title;
	}

	public class InventoryItem {

		private WebElement item;

		private By image = By.tagName("img");

		private By name = By.className("inventory_item_name");

		private By description = By.className("inventory_item_desc");

		private By price = By.className("inventory_item_price");

		private By btnAdd = By.className("button");

		public InventoryItem(WebElement item) {
			this.item = item;
			waitUntilVisible(item);
		}

		public String getImageSrcFileName() {
			return item.findElement(image).getAttribute("src");
		}

		public String getDescription() {
			return item.findElement(description).getText();
		}

		public double getPrice() throws ParseException {
			String lblPrice = item.findElement(price).getText();
			log.debug("lblPrice = {}", lblPrice);
			double result = NumberFormat.getCurrencyInstance(Locale.US).parse(lblPrice).doubleValue();
			log.debug("price = {}", result);
			return result;
		}

		public String getName() {			
			return item.findElement(name).getText();
		}
		
		public WebElement getBtnAdd() {
			return item.findElement(btnAdd);
		}

	}
}
