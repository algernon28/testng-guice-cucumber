package com.watermelon.core.assertions;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;

public class WatermelonAssertions extends Assertions {
	public static WebElementAssert assertThat(WebElement actual) {
		return new WebElementAssert(actual);
	}
}
