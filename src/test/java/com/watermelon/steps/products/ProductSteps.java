package com.watermelon.steps.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

import java.util.List;

import com.google.inject.Inject;
import com.watermelon.pages.InventoryItem;
import com.watermelon.pages.ProductPage;
import com.watermelon.pages.ProductPage.SORT_METHOD;
import com.watermelon.steps.BaseSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductSteps extends BaseSteps {

	public static final String WRONG_LOGIN = "login.wrong";
	public static final String LOCKEDOUT_LOGIN = "missing.domain";

	@Inject
	private ProductPage productPage;

	public ProductSteps() {
		super();
	}

	@Then("the number of products presented is {int}")
	public void the_number_of_products_is(int count) {
		int actualCount = productPage.getItems().size();
		assertThat(actualCount).withFailMessage("The number of item does not match").isEqualTo(count);
	}

	@And("I sort the items for {string}")
	public void i_sort_the_items(String method) {
		SORT_METHOD sortMethod = SORT_METHOD.valueOf(method.toUpperCase());
		String currentSortMethod = productPage.sortItemsBy(sortMethod);
		assertThat(currentSortMethod).withFailMessage("The filter element did not update").isEqualTo(sortMethod.label.toUpperCase());

	}

	@And("I land on the Products page")
	public void i_land_on_products_page() {
		assertThat(productPage).withFailMessage("Products page did not show").returns(true,
				from(ProductPage::isLoaded));

	}

	@Then("the items are correctly sorted for {string}")
	public void the_items_are_sorted(String method) {
		log.debug("sorted!");
		List<InventoryItem> actualItems = productPage.getItems();
		List<InventoryItem> expectedItems = productPage.getItems(method);
		assertThat(actualItems).withFailMessage("Products were not correctly ordered")
				.isEqualTo(expectedItems);
	}

}