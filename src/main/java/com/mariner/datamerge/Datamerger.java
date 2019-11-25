package com.mariner.datamerge;

import static com.mariner.datamerge.parse.Constants.CLIENT_ADDRESS_NAME;
import static com.mariner.datamerge.parse.Constants.CLIENT_GUID_NAME;
import static com.mariner.datamerge.parse.Constants.DATE_FORMAT;
import static com.mariner.datamerge.parse.Constants.MAX_HOLE_SIZE_NAME;
import static com.mariner.datamerge.parse.Constants.PACKETS_REQUESTED_NAME;
import static com.mariner.datamerge.parse.Constants.PACKETS_SERVICED_NAME;
import static com.mariner.datamerge.parse.Constants.REQUEST_TIME_NAME;
import static com.mariner.datamerge.parse.Constants.RETRIES_REQUEST_NAME;
import static com.mariner.datamerge.parse.Constants.SERVICE_GUID_NAME;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.mariner.datamerge.model.DataRecord;
import com.mariner.datamerge.parse.CsvRecordParser;
import com.mariner.datamerge.parse.JsonRecordParser;
import com.mariner.datamerge.parse.RecordParser;
import com.mariner.datamerge.parse.XmlRecordParser;

/**
 * The Datamerger class is the entry point of this project. It is responsible
 * for loading a list of <code>RecordParser</code> objects during construction.
 * During processing, all records will be filtered, sorted by request time and
 * written out to a final report file called 'final-report.csv'.
 * 
 * Additionally, a tally of Service Guid instances is counted and written to the
 * console.
 * 
 * @author Maryse Arseneau
 *
 */
public final class Datamerger {

	/**
	 * The path of the final CSV report to be rendered
	 */
	private static final String FINAL_REPORT_PATH = "final-report.csv";

	/**
	 * The list of RecordParser instances that this class will merge and process
	 */
	private List<RecordParser> parsers = new ArrayList<>();

	/**
	 * Constructor that takes in an array of RecordParser objects
	 * 
	 * @param parsers The array of RecordParser object
	 */
	public Datamerger(RecordParser... parsers) {
		for (RecordParser p : parsers) {
			this.parsers.add(p);
		}
	}

	/**
	 * Loop through the list of RecordParsers, merging all record elements into a
	 * single sorted list ( by request time ascending ). Generate a simple console
	 * report of a running count of Service Guids.
	 */
	public void process() {
		List<DataRecord> records = new ArrayList<>();
		// Add all records from all parsers into a single list
		for (RecordParser p : parsers) {
			records.addAll(p.parse());
		}
		// Sort all records by request time ascending using the sort/Comparator
		// interface
		records.sort(new Comparator<DataRecord>() {
			@Override
			public int compare(DataRecord first, DataRecord second) {
				return first.getRequestTime().compareTo(second.getRequestTime());
			}
		});

		// Generate final report and console summary
		generateFinalReportAndSummary(records);
	}

	/**
	 * Generate a CSV report of records to the path defined in FINAL_REPORT_PATH.
	 * This method assumes that the List of DataRecord objects has already been
	 * sorted.
	 * 
	 * Output a running count of all unique Service Guids to the console.
	 * 
	 * @param records The list of records to be sorted
	 */
	private void generateFinalReportAndSummary(List<DataRecord> records) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FINAL_REPORT_PATH));
				CSVPrinter csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader(CLIENT_ADDRESS_NAME, CLIENT_GUID_NAME, REQUEST_TIME_NAME,
								SERVICE_GUID_NAME, RETRIES_REQUEST_NAME, PACKETS_REQUESTED_NAME, PACKETS_SERVICED_NAME,
								MAX_HOLE_SIZE_NAME));) {
			// a map to hold the service GUID counts
			Map<String, Integer> serviceGuidCounts = new HashMap<>();

			for (DataRecord r : records) {
				// print a DataRecord out to the CSV file
				csvPrinter.printRecord(r.getClientAddress(), r.getClientGuid(), DATE_FORMAT.format(r.getRequestTime()),
						r.getServiceGuid(), r.getRetriesRequest(), r.getPacketsRequested(), r.getPacketsServiced(),
						r.getMaxHoleSize());

				// Add to the running service GUID counter
				final String serviceGuid = r.getServiceGuid();
				if (serviceGuidCounts.containsKey(serviceGuid)) {
					Integer count = serviceGuidCounts.get(serviceGuid);
					serviceGuidCounts.put(serviceGuid, count + 1);
				} else {
					serviceGuidCounts.put(serviceGuid, 1);
				}
			}
			csvPrinter.flush();
			System.out.println("Final report has been rendered to " + FINAL_REPORT_PATH);
			System.out.println();
			// Display Service Guid Summary
			System.out.println("----- Service Guid Summary -----");
			for (Map.Entry<String, Integer> entry : serviceGuidCounts.entrySet()) {
				String serviceGuid = entry.getKey();
				Object count = entry.getValue();
				System.out.println(String.format("%s: %s", serviceGuid, count));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		// Initialize the Datamerger with CSV, JSON and XML Parsers
		Datamerger merger = new Datamerger(new CsvRecordParser("src/main/resources/reports.csv"),
				new JsonRecordParser("src/main/resources/reports.json"),
				new XmlRecordParser("src/main/resources/reports.xml"));
		merger.process();
	}

}
