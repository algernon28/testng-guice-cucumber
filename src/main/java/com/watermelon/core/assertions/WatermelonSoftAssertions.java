package com.watermelon.core.assertions;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;

public class WatermelonSoftAssertions extends SoftAssertions {
	public static WebElementAssert assertThat(WebElement actual) {
		return new WebElementAssert(actual);
	}
}
