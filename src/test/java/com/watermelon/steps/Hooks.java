package com.watermelon.steps;

import java.net.MalformedURLException;
import java.util.Optional;

import org.openqa.selenium.WebDriver;

import com.google.inject.Inject;
import com.watermelon.core.di.Configuration;
import com.watermelon.core.di.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.testng.AbstractTestNGCucumberTests;

public class Hooks extends AbstractTestNGCucumberTests {
	@Inject
	WebDriver driver;
	
	@Inject
	Configuration config;

	@Before
	public void startUp() throws MalformedURLException {
		driver.navigate().to(config.getServer().getURL());		
	}

	@After
	public void tearDown() {
		if (Optional.ofNullable(driver).isPresent()) {
			driver.quit();
			DriverManager.removeDriver();
		}
	}

}
