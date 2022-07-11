package com.watermelon.steps.products;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import com.watermelon.steps.BaseSteps;
import com.watermelon.steps.pages.ProductPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductSteps extends BaseSteps {
	@Inject
	private ProductPage productPage;
	
	public ProductSteps() {
		super();
	}
	@And("I land on the Products page")
	public void i_land_on_products_page() {
		log.debug("I land on products page");
		assertThat(productPage.title()).as("Products page did not show").satisfies(v -> v.isDisplayed());	
	}
	
	@Then("the number of products presented is {int}")
	public void the_number_of_products_presented_is(int num) {
		int productsNum = productPage.getItems().size();
		assertThat(productsNum).as("The number of items doesn't match").isEqualTo(num);
	}
}
