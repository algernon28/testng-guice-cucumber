package com.watermelon.tests.webelements;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.Getter;

/**
 * Wrap a table Webelement
 * 
 * @author AM
 *
 */
@Getter
public class WebTable {
	private final WebElement table;
	List<WebElement> rows;
	int numRows;
	int numColumns;
	boolean hasHeader;

	private List<String> columnNames;
	private Map<String, Integer> columnMap;
	private By rowsLocator;

	public static final By DEFAULT_ROWLOCATOR = By.tagName("tr");

	/**
	 * 
	 * @param webtable    the html table element
	 * @param hasHeader   {@literal true} if the table has a {@code th} header,
	 *                    {@literal false} otherwise
	 * @param rowsLocator the web locator used to identify the table rows, since
	 *                    {@code tr} is not always sufficient
	 */
	public WebTable(final WebElement webtable, boolean hasHeader, By rowsLocator) {
		this.table = webtable;
		parse();
		rows = webtable.findElements(rowsLocator);
		numRows = rows.size();
		numColumns = columnNames.size();
		this.rowsLocator = rowsLocator;
		this.hasHeader = hasHeader;
	}

	/**
	 *
	 * @param table the html table
	 */
	public WebTable(WebElement table, boolean hasHeader) {
		this(table, hasHeader, DEFAULT_ROWLOCATOR);
	}

	/**
	 * 
	 * @return the cells in the first row, if {@link #hasHeader} = true, otherwise
	 *         an {@link Optional#empty}
	 */
	public Optional<List<WebElement>> getHeader() {
		if (hasHeader) {
			return Optional.of(getColumnsByRow(rows.get(0)));
		}
		return Optional.empty();
	}

	private List<WebElement> getColumnsByRow(WebElement row) {
		return row.findElements(By.tagName("td"));
	}

	public WebElement getCell(String columnName, int rowNum) {
		return getColumnsByRow(rows.get(rowNum)).get(columnMap.get(columnName));

	}

	public WebElement getCell(int column, int rowNum) {
		return getColumnsByRow(rows.get(rowNum)).get(column);
	}

	/**
	 * <p>
	 * Commodity predicate, based on the name of the column and the content. Useful
	 * as template to call {@link #filterRows(Predicate)} <br/>
	 * E.g.: {@code filterRows(contentMatcher("date", "23/9/1999"))} will filter the
	 * cell in the "date" column with "23/9/1999"
	 * </p>
	 *
	 * @param col  the name of the column to filter for
	 * @param text the content to be matched with the cel in {@code col} position
	 * @return the filter predicate
	 */
	public Predicate<List<WebElement>> contentMatcher(String col, String text) {
		return cells -> cells.get(columnMap.get(col)).getText().equals(text);
	}

	/**
	 * @param filterFunction
	 *                       <p>
	 *                       the filter function. E.g.: in order to filter the rows
	 *                       below "Donald Duck" in the column "name", the predicate
	 *                       function could be
	 * 
	 *                       <pre>{@code Predicate<List<WebElement>> checkNome = (cells) -> cells.get(columnMap().get("name").getText().equals("Donald Duck");}</pre>
	 *                       </p>
	 * @return the filered rows, as lists of cells
	 */
	public List<List<WebElement>> filterRows(Predicate<List<WebElement>> filterFunction) {
		int rowsToSkip = (hasHeader) ? 0 : 1;
		return table.findElements(rowsLocator).stream().skip(rowsToSkip).map(tr -> tr.findElements(By.tagName("td")))
				.filter(filterFunction).toList();
	}

	private void parse() {
		this.columnNames = table.findElements(By.tagName("th")) // get table headers
				.stream().map(WebElement::getText).map(String::trim).toList();
		this.columnMap = IntStream.range(0, columnNames.size()).boxed()
				.collect(Collectors.toMap(columnNames::get, Function.identity()));

	}
}
