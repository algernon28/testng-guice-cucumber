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
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;

import com.google.inject.Inject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductPage extends SauceLabsPage {

	public static String PROBLEM_SOURCE_IMAGEFILE = "/static/media/sl-404.168b1cce.jpg";

	@Inject
	public ProductPage(WebDriver driver) {
		super(driver);
	}

	@FindBys({ @FindBy(xpath = "//span[@class='title']"), @FindBy(xpath = "//span[normalize-space()='Products']") })
	private WebElement title;

	@FindBy(className = "inventory_item")
	private List<WebElement> items;

	@FindBy(className = "product_sort_container")
	private WebElement sorter;

	public enum SORT_METHOD {
		AZ("Name (A to Z)"), ZA("Name (Z to A)"), LOHI("Price (low to high)"), HILO("Price (high to low)");

		public String label;

		private SORT_METHOD(String label) {
			this.label = label;
		}
	}

	public List<InventoryItem> getItems() {
		return items.stream().map(el -> new InventoryItem(el)).toList();
	}

	/**
	 * 
	 * @param method the sort method
	 * @return the text of the active option
	 */
	public String sortBy(SORT_METHOD method) {
		waitUntilVisible(sorter);
		Select filter = new Select(sorter);
		filter.selectByVisibleText(method.label);
		WebElement activeOption = driver.findElement(By.className("active_option"));
		return activeOption.getText();
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

		private ByChained btnAdd = new ByChained(By.tagName("button"),
				By.className("btn btn_primary btn_small btn_inventory"));

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

		private String addOrRemove(String buttonText) {
			WebElement button = item.findElement(btnAdd);
			waitUntilVisible(button);
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
}
