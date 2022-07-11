package com.watermelon.steps;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.extern.slf4j.Slf4j;

@CucumberOptions(
		features = "classpath:features", 
		glue = { "com.watermelon.core.di.injectors", "com.watermelon.steps" }, 
		monochrome = true, 
		plugin = { "pretty", "html:TestReports/report.html",
				"json:TestReports/cucumber.json", "junit:TestReports/cucumber.xml" }
		)
@Slf4j
public class CucumberRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		log.debug("Scenarios");
		return super.scenarios();
	}
}
