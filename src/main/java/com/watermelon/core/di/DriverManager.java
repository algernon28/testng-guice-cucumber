package com.watermelon.core.di;

import static com.watermelon.core.Utils.DEFAULT_BROWSER;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.xml.XmlTest;

import com.google.inject.Provider;
import com.watermelon.core.Utils;

import io.cucumber.guice.ScenarioScoped;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Architecture;
import io.github.bonigarcia.wdm.config.Config;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;

@ScenarioScoped
public class DriverManager implements Provider<WebDriver> {
	private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);

	private static final ThreadLocal<WebDriver> DRIVERPOOL = new ThreadLocal<>();

	public DriverManager() {
		XmlTest context = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest();
		Optional<String> myArchitecture = Utils.lookupParameter("architecture", context);
		Optional<String> myOS = Utils.lookupParameter("platform", context);
		Optional<String> myVersion = Utils.lookupParameter("version", context);
		Optional<String> myLanguage = Utils.lookupParameter("language", context);
		Optional<String> myCountry = Utils.lookupParameter("country", context);
		String myBrowser = Utils.lookupParameter("browser", context).orElse(DEFAULT_BROWSER);
		DriverManagerType browser = DriverManagerType.valueOf(myBrowser.toUpperCase());
		WebDriverManager wdm = WebDriverManager.getInstance();
		Config wdmConfig = wdm.config();
		if (myArchitecture.isPresent()) {
			wdmConfig.setArchitecture(Architecture.valueOf(myArchitecture.get()));
		}

		if (myOS.isPresent()) {
			wdmConfig.setOs(myOS.get());
		}
		WebDriver driver;
		String arg;
		switch (browser) {
		case CHROME, CHROMIUM:
			if (myVersion.isPresent()) {
				wdmConfig.setChromeDriverVersion(myVersion.get());
			} 
			ChromeOptions chOptions = new ChromeOptions();
			arg = String.format("--lang=%s_%s", myLanguage, myCountry);
			chOptions.addArguments(arg).setAcceptInsecureCerts(true).setCapability(ChromeOptions.CAPABILITY, chOptions);
			wdm.clearResolutionCache();
			driver = wdm.capabilities(chOptions).create();
			logger.debug("ChromeDriver built: {}", driver);
			break;
		case FIREFOX:
			if (myVersion.isPresent()) {
				wdmConfig.setFirefoxVersion(myVersion.get());
			}
			ProfilesIni allProfiles = new ProfilesIni();
			FirefoxProfile profile = allProfiles.getProfile("default");
			FirefoxOptions ffOptions = new FirefoxOptions();
			ffOptions.addPreference("intl.accept_languages", myLanguage);
			ffOptions.setProfile(profile).setAcceptInsecureCerts(true);
			driver = wdm.gitHubToken("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")
					// can't make it work with FirefoxOptions yet, commenting for now...
					// .capabilities(ffOptions)
					.create();
			logger.debug("FireFoxDriver built: {}", driver);
			break;
		case EDGE:
			if (myVersion.isPresent()) {
				wdmConfig.setEdgeDriverVersion(myVersion.get());
			}
			EdgeOptions eOptions = new EdgeOptions();
			arg = String.format("--lang=%s", myLanguage);
			eOptions.addArguments(arg).setAcceptInsecureCerts(true);
			driver = wdm.capabilities(eOptions).create();
			logger.debug("EdgeDriver built: {}", driver);
			break;
		default:
			String msg = String.format("Browser type [%s] not recognised", browser);
			throw new WebDriverManagerException(msg);
		}
		DRIVERPOOL.set(driver);
	}

	/**
	 * Remove current driver from the threadpool. Delegate method to comply with
	 * Sonar {@code java:S5164} rule.
	 */
	public static void removeDriver() {
		DRIVERPOOL.remove();
	}

	/**
	 * 
	 * @return the Selenium {@link WebDriver} instance
	 */
	public WebDriver get() {
		return DRIVERPOOL.get();
	}

}
