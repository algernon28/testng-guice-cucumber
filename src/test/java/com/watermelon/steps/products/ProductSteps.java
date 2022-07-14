package com.watermelon.steps.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.testng.Assert.fail;

import java.util.List;

import com.google.inject.Inject;
import com.watermelon.pages.InventoryItem;
import com.watermelon.pages.ProductPage;
import com.watermelon.pages.ProductPage.SORT_METHOD;
import com.watermelon.steps.BaseSteps;

import io.cucumber.datatable.DataTable;
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
		assertThat(actualCount).isEqualTo(count);
	}

	@And("I sort the items")
	public void i_sort_the_items(DataTable data) {
		List<InventoryItem> items = productPage.getItems();
		data.entries().stream().forEach(val -> {
			String filter = val.get("sort value").toUpperCase();
			// String items.get(0).getName()
			SORT_METHOD method = SORT_METHOD.valueOf(filter);
			productPage.sortItemsBy(method);

		});

	}

	@And("I land on the Products page")
	public void i_land_on_products_page() {
		assertThat(productPage).as("Products page did not show").returns(true, from(ProductPage::isLoaded));

	}

	@Then("the items are correctly sorted")
	public void the_items_are_sorted() {
		log.debug("sorted!");

	}

}