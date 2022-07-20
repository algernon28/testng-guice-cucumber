package com.watermelon.steps.checkout;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;

import com.google.inject.Inject;
import com.watermelon.pages.CartPage;
import com.watermelon.pages.SauceItem;
import com.watermelon.pages.products.InventoryItem;
import com.watermelon.steps.BaseSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Then;

public class CheckoutSteps extends BaseSteps {
	private CartPage cartPage;

	@Inject
	public CheckoutSteps(CartPage cartPage) {
		super();
		this.cartPage = cartPage;
	}

	@Then("the cart is empty")
	public void the_cart_is_empty() {
		then(cartPage.getItems()).isEmpty();
	}

	@And("I continue shopping")
	public void i_continue_shopping() {
		cartPage.continueShopping();
	}
	
	@And("the items in the cart are {int}")
	public void items_in_the_cart_are(int value) {
		int actual = cartPage.getItems().size();
		then(actual).isEqualTo(value);
	}
	
	@But("I remove {string}")
	public void i_remove(String items) {
		List<String> entries = List.of(items.split(","));
		List<SauceItem> products = cartPage.getItems().stream().filter(p -> entries.contains(p.getName()))
				.toList();
		products.forEach(SauceItem::remove);
	}
}
