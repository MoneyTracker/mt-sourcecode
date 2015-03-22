package com.maqs.moneytracker.common;

import com.maqs.moneytracker.common.paging.spec.PropertySpec;

/**
 * The place to hold the constants.
 * @author maq
 *
 */
public final class Constansts {
	/**
	 * Constructor is private, just to avoid instantiation.
	 */
	private Constansts() {
	}
	/*
	 * Constants are defined here...
	 */
	/**
	 * Empty string.
	 */
	public static final String EMPTY_STRING = "";
	
	/**
	 * Space.
	 */
	public static final String SPACE = " ";
	
	/**
	 * Comma.
	 */
	public static final String COMMA = ",";

	/**
	 * Open square bracket.
	 */
	public static final String OPEN_BRACKET = "[";
	
	/**
	 * Close square bracket.
	 */
	public static final String CLOSE_BRACKET = "]";
	/**
	 * Percentage.
	 */
	public static final String PERCENTAGE = "%";
	/*
	 * End of Constants
	 */

	/*
	 * Actions
	 */
	public static final String HOME = "Home";

	public static final String TRANSACTION = "Transactions";

	public static final String APP_IMAGE = "Money";
	
	public static final String DELETE_IMAGE = "Delete";
	
	public static final String SAVE_IMAGE = "Save";

	public static final String TRANSACTION_BY_CAT = "Transactions By Category";

	public static final String DOLLAR = "Dollar";
	
	public static final int DEFAULT_FORM_FIELD_WIDTH = 200;

	public static final double DEFAULT_FORM_FIELD_HEIGHT = 35;
	
	public static final String[] MONTHS = {
		"Jan", "Feb", "Mar",
		"Apr", "May", "Jun",
		"Jul", "Aug", "Sep",
		"Oct", "Nov", "Dec"
	};

	public static final double VIEWPORT_HEIGHT = 999;

	public static final String HYPHEN = "-";

	public static final String USER_ID = "userId";
}
