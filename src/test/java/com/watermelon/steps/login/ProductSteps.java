package com.watermelon.steps.login;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.google.inject.Inject;
import com.watermelon.steps.BaseSteps;
import com.watermelon.steps.pages.ProductPage;
import com.watermelon.steps.pages.ProductPage.InventoryItem;
import com.watermelon.steps.pages.ProductPage.SORT_METHOD;

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

	public ProductSteps(ProductPage productPage) {
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
			String filter = val.get("sort text");
			// String items.get(0).getName()
			productPage.sortBy(SORT_METHOD.valueOf(filter));

		});
	}

	@Then("the items are correctly sorted")
	public void the_items_are_sorted() {

	}

}