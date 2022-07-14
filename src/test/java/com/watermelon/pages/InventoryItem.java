package com.watermelon.pages;

import java.text.NumberFormat;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryItem {

	private WebElement item;

	private By image = By.tagName("img");

	private By name = By.className("inventory_item_name");

	private By description = By.className("inventory_item_desc");

	private By price = By.className("inventory_item_price");

	private ByChained btnAdd = new ByChained(By.tagName("button"));

	public InventoryItem(WebElement item) {
		this.item = item;
	}

	public String getImageSrcFileName() {
		return item.findElement(image).getAttribute("src");
	}

	public String getDescription() {
		return item.findElement(description).getText();
	}

	@SneakyThrows
	public double getPrice() {
		String lblPrice = item.findElement(price).getText();
		log.debug("lblPrice = {}", lblPrice);
		double result = NumberFormat.getCurrencyInstance(Locale.US).parse(lblPrice).doubleValue();
		log.debug("price = {}", result);
		return result;
	}

	public String getName() {
		return item.findElement(name).getText();
	}

	private String addOrRemove(String buttonText) {
		WebElement button = item.findElement(btnAdd);
		if (button.getText().equalsIgnoreCase(buttonText)) {
			button.click();
		}
		return button.getText();
	}

	/**
	 * Click the button to remove, if the label is "remove"
	 * 
	 * @return the button label
	 */
	public String add() {
		return addOrRemove("remove");
	}

	/**
	 * Click the button to remove, if the label is "add"
	 * 
	 * @return the button label
	 */
	public String remove() {
		return addOrRemove("add to cart");
	}

}

