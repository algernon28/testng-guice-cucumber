package com.watermelon.steps.pages;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.google.inject.Inject;

import io.cucumber.guice.ScenarioScoped;

@ScenarioScoped
public class LoginPage extends SauceLabsPage {

	@FindBy(className = "login_logo")
	private WebElement imgLoginLogo;

	@FindBy(xpath = "//div[@class='bot_column']")
	private WebElement imgBot;

	@FindBy(xpath = "//input[@data-test='username']")
	private WebElement txtUsername;

	@FindBy(xpath = "//input[@data-test='password']")
	private WebElement txtPassword;

	@FindBy(xpath = "//input[@data-test='login-button']")
	private WebElement btnLogin;

	@FindBy(xpath = "//h3[@data-test='error']")
	private WebElement errorMessage;

	@Inject
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void submit() {
		waitUntilVisible(btnLogin).click();
	}

	public void login(String username, String password) {
		clearAll(txtUsername, txtPassword);
		type(txtUsername, username);
		type(txtPassword, password);
		submit();
	}

	public void clearFields() {
		clearAll(txtUsername);
		this.txtUsername.clear();
		this.txtPassword.clear();
	}

	public Optional<String> errorMessage() {
		try {
			waitUntilVisible(errorMessage);
		} catch (Exception e) {
			return Optional.empty();
		}
		return Optional.of(errorMessage.getText());
	}

	@Override
	public WebElement title() {
		waitUntilVisible(imgBot);
		return imgBot;
	}

}
