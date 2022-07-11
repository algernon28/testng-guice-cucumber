package com.watermelon.steps.login;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Optional;

import com.google.inject.Inject;
import com.watermelon.steps.BaseSteps;
import com.watermelon.steps.pages.LoginPage;
import com.watermelon.steps.pages.ProductPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginSteps extends BaseSteps {

	public static final String WRONG_LOGIN = "login.wrong";
	public static final String LOCKEDOUT_LOGIN = "missing.domain";

	private LoginPage loginPage;
	
	private ProductPage productPage;
	
	 
	@Inject
	public LoginSteps(LoginPage loginPage, ProductPage productPage) {
		super();
		this.loginPage = loginPage;
		this.productPage = productPage;
	}

	@Given("I am on the login page")
	public void i_land_on_the_login_page() throws Throwable {
		driver.navigate().to(config.getServer().getURL());
		// assertThat(loginPage.)
		log.debug("Driver: {}", driver);
		log.debug("{}", driver.getCurrentUrl());
		assertThat(loginPage.title()).as("Login page did not show").satisfies(v -> v.isDisplayed());

	}

	@When("I enter username as {string} and password as {string}")
	public void i_enter_credentials(String username, String password) throws Throwable {
		log.debug("I enter credentials: {}/{}", username, password);
		loginPage.login(username, password);		
	}

	@Then("I expect validation message as {string} is displayed")
	public void validation_message(String toast) throws Throwable {
		log.debug("I expect message: {}", toast);
		Optional<String> errorMessage = loginPage.errorMessage();
		assertThat(errorMessage).as("No error messages").isPresent();
		assertThat(errorMessage).as("Wrong error message").hasValue(toast);
	}
	
	@Given("I am logged as user")
	public void i_am_logged_as_user(DataTable data) throws Throwable {
		Map<String, String> credentials = data.entries().stream().findFirst().get();
		String username = credentials.get("username");
		String password = credentials.get("password");
		i_land_on_the_login_page();
		i_enter_credentials(username, password);
	}

}