package com.watermelon.pages;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.inject.Inject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductPage extends SauceLabsPage {

	public static String PROBLEM_SOURCE_IMAGEFILE = "/static/media/sl-404.168b1cce.jpg";

	@Inject
	public ProductPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
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
		return items.stream().map(el -> new InventoryItem(el)).unordered().toList();
	}

	public List<InventoryItem> getItems(SORT_METHOD method) {
		Comparator<InventoryItem> compByName = Comparator.comparing(InventoryItem::getName);
		Comparator<InventoryItem> compByPrice = Comparator.comparing(InventoryItem::getPrice);

		List<InventoryItem> result = List.of();
		switch (method) {
		case AZ -> result = items.stream().map(el -> new InventoryItem(el)).sorted(compByName).toList();
		case ZA -> result = items.stream().map(el -> new InventoryItem(el)).sorted(compByName.reversed()).toList();
		case LOHI -> result = items.stream().map(el -> new InventoryItem(el)).sorted(compByPrice).toList();
		case HILO -> result = items.stream().map(el -> new InventoryItem(el)).sorted(compByPrice.reversed()).toList();
		}
		return result;
	}

	/**
	 * 
	 * @param method the sort method
	 * @return the text of the active option
	 */
	public String sortItemsBy(SORT_METHOD method) {
		waitUntilVisible(sorter);
		Select filter = new Select(sorter);
		filter.selectByVisibleText(method.label);
		WebElement activeOption = driver.findElement(By.className("active_option"));
		return activeOption.getText();
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
