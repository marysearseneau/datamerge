package com.mariner.datamerge.parse;

import java.text.SimpleDateFormat;

/**
 * The Constants interface holds a number of static variables used by parsers.
 * These field names are common between all underlying technology(Csv, Xml, Json) records.
 * 
 * @author Maryse Arseneau
 *
 */
public interface Constants {

	/**
	 * Generalized date format used when parsing and writing request time fields
	 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");

	/**
	 * Unified Client Address Field Name
	 */
	public static final String CLIENT_ADDRESS_NAME = "client-address";

	/**
	 * Unified Client GUID Field Name
	 */
	public static final String CLIENT_GUID_NAME = "client-guid";

	/**
	 * Unified Request Time Field Name
	 */
	public static final String REQUEST_TIME_NAME = "request-time";

	/**
	 * Unified Service GUID Field Name
	 */
	public static final String SERVICE_GUID_NAME = "service-guid";

	/**
	 * Unified Retries Request Field Name
	 */
	public static final String RETRIES_REQUEST_NAME = "retries-request";

	/**
	 * Unified Packets Requested Field Name
	 */
	public static final String PACKETS_REQUESTED_NAME = "packets-requested";

	/**
	 * Unified Packets Serviced Field Name
	 */
	public static final String PACKETS_SERVICED_NAME = "packets-serviced";

	/**
	 * Unified Max Hole Size Field Name
	 */
	public static final String MAX_HOLE_SIZE_NAME = "max-hole-size";
}
