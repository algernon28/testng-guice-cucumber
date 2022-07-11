package com.watermelon.core;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Guice;

import com.google.inject.Inject;
import com.watermelon.core.di.ConfigurationModule;
import com.watermelon.core.di.DriverManager;
import com.watermelon.core.di.DriverManagerModule;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@Guice(modules = {DriverManagerModule.class, ConfigurationModule.class})
public class Hooks extends AbstractTestNGCucumberTests {
	@Inject
	WebDriver driver;

	@Before
	public void setContext() {
	}

	@After
	public void tearDown() {
		if (Optional.ofNullable(driver).isPresent()) {
			driver.quit();
			DriverManager.removeDriver();
		}
	}

}
